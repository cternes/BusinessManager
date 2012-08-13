/*******************************************************************************
 * Copyright 2012 Christian Ternes and Thorsten Volland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.businessmanager.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.VCardOutputter;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.BDay;
import net.fortuna.ical4j.vcard.property.Fn;
import net.fortuna.ical4j.vcard.property.N;
import net.fortuna.ical4j.vcard.property.Note;
import net.fortuna.ical4j.vcard.property.Org;
import net.fortuna.ical4j.vcard.property.ProdId;
import net.fortuna.ical4j.vcard.property.SortString;
import net.fortuna.ical4j.vcard.property.Title;
import net.fortuna.ical4j.vcard.property.Url;
import net.fortuna.ical4j.vcard.property.Version;

import org.apache.commons.lang3.Validate;
import org.businessmanager.dao.ContactDao;
import org.businessmanager.domain.Activity;
import org.businessmanager.domain.Activity.ActivityType;
import org.businessmanager.domain.Address;
import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem;
import org.businessmanager.domain.ContactItem.Scope;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Fax;
import org.businessmanager.domain.ModificationType;
import org.businessmanager.domain.Phone;
import org.businessmanager.domain.Website;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.SpringSecurityService;
import org.businessmanager.web.bean.ContactActivityBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private SpringSecurityService securityService;
	
	private VCardOutputter vcardWriter = new VCardOutputter();

	@Override
	public Contact saveContact(Contact contact) {
		Validate.notNull(contact, "Parameter contact must not be null!");
		
		if (contact.getId() == null) {
			contact = contactDao.save(contact);
			saveActivity(contact.getId(), ModificationType.CREATE, contact.getFullname());
			
			return contact;
		} else {
			contact = contactDao.update(contact);
			saveActivity(contact.getId(), ModificationType.UPDATE, contact.getFullname());
			
			return contact;
		}
	}

	@Override
	public List<Contact> getContacts() {
		return contactDao.findAll();
	}

	@Override
	public void deleteContact(Contact contact) {
		Validate.notNull(contact, "Parameter contact must not be null!");

		saveActivity(contact.getId(), ModificationType.DELETE, contact.getFullname());
		contactDao.remove(contact);
	}

	@Override
	public ContactItem mergeContactItem(ContactItem contactItem) {
		Validate.notNull(contactItem);

		if (contactItem instanceof Email) {
			return contactDao.mergeEmail((Email) contactItem);
		} else if (contactItem instanceof Phone) {
			return contactDao.mergePhone((Phone) contactItem);
		} else if (contactItem instanceof Fax) {
			return contactDao.mergeFax((Fax) contactItem);
		} else if (contactItem instanceof Website) {
			return contactDao.mergeWebsite((Website) contactItem);
		}
		return null;
	}

	@Override
	public void removeContactItem(Long id) {
		contactDao.removeContactItem(id);
	}
	
	@Override
	public Contact getContactById(Long id) {
		return contactDao.findById(id);
	}

	@Override
	public List<Contact> fullTextSearchContact(String searchString) {
		return contactDao.fullTextSearchContact(searchString);
	}
	
	private void saveActivity(Long sourceId, ModificationType modType, String contactName) {
		User currentUser = securityService.getLoggedInUser();
		ContactActivityBean activityData = new ContactActivityBean(currentUser.getUsername(), modType, contactName);
		
		Activity activity = new Activity(currentUser.getId(), ActivityType.CONTACT);
		activity.setSourceId(sourceId);
		
		activity.setData(activityData.toJson());
		activityService.saveActivity(activity);
	}

	@Override
	public OutputStream getAsVCard(Contact contact) {
		Validate.notNull(contact);
		
		List<Property> props = new ArrayList<Property>();
		props.add(new Version("2.1"));
		props.add(new N(contact.getLastname(), contact.getFirstname(), null, null, null));
		props.add(new Fn(contact.getFullname()));
		props.add(new SortString(contact.getLastname()));
		
		List<Email> emailList = contact.getEmailList();
		for (Email email : emailList) {
			if(Scope.PRIVATE.equals(email.getScope())) {
				props.add(new net.fortuna.ical4j.vcard.property.Email(Group.HOME, email.getEmail()));
			}
			else if(Scope.COMMERCIAL.equals(email.getScope())) {
				props.add(new net.fortuna.ical4j.vcard.property.Email(Group.WORK, email.getEmail()));
			}
		}
		
		if(contact.getBirthday() != null) {
			Date iCalDate = new Date(contact.getBirthday().getTime());
			props.add(new BDay(iCalDate));
		}
		
		List<Address> addressList = contact.getAddresses();
		for (Address address : addressList) {
			props.add(new net.fortuna.ical4j.vcard.property.Address(Group.WORK, address.getPostOfficeBox(), null, address.getStreet() + " " +address.getHousenumber(), address.getCity(), null, address.getZipCode(), address.getCountry(), Type.WORK));
		}
		
		if(contact.getCompany() != null) {
			props.add(new Org(contact.getCompany()));
		}
		
		List<Website> websiteList = contact.getWebsiteList();
		for (Website website : websiteList) {
			props.add(new Url(URI.create(website.getWebsite())));
		}
		
		if(contact.getJobTitle() != null) {
			props.add(new Title(contact.getJobTitle()));
		}
		
		if(contact.getNotes() != null) {
			props.add(new Note(contact.getNotes()));
		}
		
		props.add(new ProdId("Business Manager"));
		
		VCard vCard = new VCard(props);
		
		OutputStream out = new ByteArrayOutputStream();
		
		try {
			OutputStreamWriter writer = new OutputStreamWriter(out, Charset.forName("UTF-8"));
			vcardWriter.output(vCard, writer);
		} catch (IOException e) {
			logger.error("Could not write vcard to outputstream. Error was: ", e);
		} catch (ValidationException e) {
			logger.error("Found validation errors in vcard. Aborting.", e);
		}
		
		return out;
	}

}
