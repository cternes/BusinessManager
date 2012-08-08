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

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.businessmanager.dao.ContactDao;
import org.businessmanager.domain.Activity;
import org.businessmanager.domain.Activity.ActivityType;
import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Fax;
import org.businessmanager.domain.ModificationType;
import org.businessmanager.domain.Phone;
import org.businessmanager.domain.Website;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.SpringSecurityService;
import org.businessmanager.web.bean.ContactActivityBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private SpringSecurityService securityService;

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

}
