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
package org.businessmanager.web.controller.page.contact;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.businessmanager.annotation.HandlesExceptions;
import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;
import org.businessmanager.domain.Contact;
import org.businessmanager.domain.Contact.Salutation;
import org.businessmanager.domain.ContactItem;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Fax;
import org.businessmanager.domain.Phone;
import org.businessmanager.domain.Website;
import org.businessmanager.service.ContactService;
import org.businessmanager.util.ImageUtil;
import org.businessmanager.web.bean.ContactBean;
import org.businessmanager.web.bean.ContactItemBean;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.AddressManagementController;
import org.businessmanager.web.controller.model.ContactModel;
import org.businessmanager.web.servlet.ImageServlet;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactEditController")
@Scope("view")
public class ContactEditController extends AbstractController {

	private static final int IMAGE_HEIGHT = 185;

	private static final int IMAGE_WIDTH = 140;

	private final Logger logger = LoggerFactory.getLogger(getClass());		
	
	@Autowired
	private ContactService contactService;

	@Autowired
	private ContactModel contactModel;

	@Autowired
	AddressManagementController addressController;
	
	private ContactBean bean = new ContactBean();
	private List<ContactItemBean> emailList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedEmail;
	private List<ContactItemBean> phoneList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedPhone;
	private List<ContactItemBean> faxList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedFax;
	private List<ContactItemBean> websiteList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedWebsite;
	private List<ContactItem> deletedItems = new ArrayList<ContactItem>();

	private List<org.businessmanager.domain.ContactItem.Scope> availableScopes = new ArrayList<org.businessmanager.domain.ContactItem.Scope>();
	private List<Salutation> avaliableSalutations = new ArrayList<Salutation>();

	@PostConstruct
	public void init() {
		initGeneral();
		initAddressManagement();
		initContactItems();

		availableScopes
				.add(org.businessmanager.domain.ContactItem.Scope.PRIVATE);
		availableScopes
				.add(org.businessmanager.domain.ContactItem.Scope.COMMERCIAL);
		availableScopes.add(org.businessmanager.domain.ContactItem.Scope.MISC);

		avaliableSalutations.add(Salutation.MR);
		avaliableSalutations.add(Salutation.MRS);
	}

	private void initGeneral() {
		Contact selectedEntity = contactModel.getSelectedEntity();
		if (selectedEntity != null) {
			bean.copyDataFromContact(selectedEntity);
		}
	}

	private void initContactItems() {
		Contact selectedEntity = contactModel.getSelectedEntity();

		emailList.add(new ContactItemBean(true));
		phoneList.add(new ContactItemBean(true));
		faxList.add(new ContactItemBean(true));
		websiteList.add(new ContactItemBean(true));

		if (selectedEntity != null) {
			if (selectedEntity.getEmailList().size() > 0) {
				emailList.clear();
				emailList.addAll(convertToContactItemBean(selectedEntity
						.getEmailList()));
			}

			if (selectedEntity.getPhoneList().size() > 0) {
				phoneList.clear();
				phoneList.addAll(convertToContactItemBean(selectedEntity
						.getPhoneList()));
			}

			if (selectedEntity.getFaxList().size() > 0) {
				faxList.clear();
				faxList.addAll(convertToContactItemBean(selectedEntity
						.getFaxList()));
			}

			if (selectedEntity.getWebsiteList().size() > 0) {
				websiteList.clear();
				websiteList.addAll(convertToContactItemBean(selectedEntity
						.getWebsiteList()));
			}
		}
	}

	private List<ContactItemBean> convertToContactItemBean(
			List<? extends ContactItem> contactItemList) {
		List<ContactItemBean> resultList = new ArrayList<ContactItemBean>();
		for (ContactItem contactItem : contactItemList) {
			ContactItemBean contactItemBean = new ContactItemBean();
			contactItemBean.copyDataFromContactItem(contactItem);
			resultList.add(contactItemBean);
		}
		return resultList;
	}

	private void initAddressManagement() {
		// init address management
		List<AddressType> aAvailableAddressTypeList = new ArrayList<AddressType>();
		aAvailableAddressTypeList.add(AddressType.BILLING);
		aAvailableAddressTypeList.add(AddressType.SHIPPING);
		addressController.setAvailableAddressTypes(aAvailableAddressTypeList);

		if (contactModel.getSelectedEntity() != null) {
			addressController.initializeAddressComponent(contactModel
					.getSelectedEntity().getAddresses());
		}
	}

	@HandlesExceptions
	public String saveContact() {
		if (validateInput()) {

			Contact contact = createContact();
			if (contactModel.getSelectedEntity() != null) {
				contact = contactModel.getSelectedEntity();
			}

			fillContact(contact);
			updateAddressListInContact(contact);
			contactService.saveContact(contact);

			addMessage(FacesMessage.SEVERITY_INFO,
					"editcontact_success_contact_saved");

			contactModel.refresh();
			return navigateBack();
		}

		return "#";
	}

	private void updateAddressListInContact(Contact contact) {
		contact.getAddresses().clear();

		List<Address> assignedAddressList = addressController
				.getAssignedAddressList();
		for (Address address : assignedAddressList) {
			address.setContact(contact);
			contact.getAddresses().add(address);
		}
	}

	private void fillContact(Contact contact) {
		contact.setFirstname(bean.getFirstname());
		contact.setLastname(bean.getLastname());
		contact.setSalutation(bean.getSalutation());
		contact.setTitle(bean.getTitle());
		contact.setCompany(bean.getCompany());
		contact.setJobTitle(bean.getJobTitle());
		contact.setNotes(bean.getNotes());
		contact.setInstantMessenger(bean.getInstantMessenger());
		contact.setImage(bean.getImage());
		contact.setImageType(bean.getImageType());

		if (bean.getBirthday() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(bean.getBirthday());
			contact.setBirthday(cal);
		}

		for (ContactItemBean contactItem : emailList) {
			Email email = new Email();
			addItemToContact(contact, contactItem, email);
		}

		for (ContactItemBean contactItem : websiteList) {
			Website website = new Website();
			addItemToContact(contact, contactItem, website);
		}

		for (ContactItemBean contactItem : faxList) {
			Fax fax = new Fax();
			addItemToContact(contact, contactItem, fax);
		}

		for (ContactItemBean contactItem : phoneList) {
			Phone phone = new Phone();
			addItemToContact(contact, contactItem, phone);
		}

		for (ContactItem contactItem : deletedItems) {
			deleteContactItem(contact, contactItem);
		}
	}

	private void deleteContactItem(Contact contact, ContactItem contactItem) {
		if (contactItem.getId() != null) {
			contact.getContactItemList().remove(contactItem);
		}
	}

	private void addItemToContact(Contact contact, ContactItemBean contactItem,
			ContactItem item) {

		// only add contact item if value is given
		if (contactItem == null || StringUtils.isEmpty(contactItem.getValue())) {
			return;
		}

		item.setId(contactItem.getId());
		item.setIsDefault(contactItem.getIsDefault());
		item.setScope(contactItem.getScope());
		item.setValue(contactItem.getValue());
		item.setContact(contact);

		if (item.getId() != null) {
			// item = contactService.mergeContactItem(item);
			int indexToReplace = contact.getContactItemList().indexOf(item);
			if (indexToReplace >= 0) {
				contact.getContactItemList().set(indexToReplace, item);
			}
		} else {
			contact.getContactItemList().add(item);
		}
	}

	private boolean validateInput() {
		boolean isValid = true;

		if (bean.getFirstname() == null || bean.getFirstname().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_WARN,
					"editcontact_warn_no_firstname");
			isValid = false;
		}

		if (bean.getLastname() == null || bean.getLastname().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_WARN,
					"editcontact_warn_no_lastname");
			isValid = false;
		}

		for (ContactItemBean item : getEmailList()) {
			if (item.getValue() != null
					&& !EmailValidator.getInstance().isValid(item.getValue())) {
				addExtendedMessage(FacesMessage.SEVERITY_WARN,
						"editcontact_warn_invalid_mail", "(" + item.getValue()
								+ ")");
				isValid = false;
			}
		}

		return isValid;
	}

	public ContactBean getBean() {
		return bean;
	}

	private Contact createContact() {
		Contact contact = new Contact(bean.getFirstname(), bean.getLastname());
		return contact;
	}

	public List<ContactItemBean> getEmailList() {
		return emailList;
	}

	public void addEmail() {
		emailList.add(new ContactItemBean());
	}

	public void removeEmail() {
		if (selectedEmail != null) {
			if (selectedEmail.getId() != null) {
				deletedItems.add(new Email(selectedEmail.getId(), selectedEmail
						.getScope(), selectedEmail.getValue(), selectedEmail
						.getIsDefault()));
			}
			emailList.remove(selectedEmail);

			if (selectedEmail.getIsDefault()) {
				ContactItemBean contactItemBean = emailList.get(0);
				if (contactItemBean != null) {
					contactItemBean.setIsDefault(true);
				}
			}
		}
	}

	public void setSelectedEmail(ContactItemBean selectedEmail) {
		this.selectedEmail = selectedEmail;
	}

	public ContactItemBean getSelectedEmail() {
		return selectedEmail;
	}

	public boolean getShowRemoveEmailButton() {
		if (emailList.size() > 1) {
			return true;
		}
		return false;
	}

	public List<org.businessmanager.domain.ContactItem.Scope> getAvailableScopes() {
		return availableScopes;
	}

	public List<ContactItemBean> getPhoneList() {
		return phoneList;
	}

	public void addPhone() {
		phoneList.add(new ContactItemBean());
	}

	public void removePhone() {
		if (selectedPhone != null) {
			if (selectedPhone.getId() != null) {
				deletedItems.add(new Phone(selectedPhone.getId(), selectedPhone
						.getScope(), selectedPhone.getValue(), selectedPhone
						.getIsDefault()));
			}
			phoneList.remove(selectedPhone);
		}
	}

	public void setSelectedPhone(ContactItemBean selectedPhone) {
		this.selectedPhone = selectedPhone;
	}

	public ContactItemBean getSelectedPhone() {
		return selectedPhone;
	}

	public boolean getShowRemovePhoneButton() {
		if (phoneList.size() > 1) {
			return true;
		}
		return false;
	}

	public List<Salutation> getAvailableSalutations() {
		return avaliableSalutations;
	}

	public List<ContactItemBean> getFaxList() {
		return faxList;
	}

	public void setSelectedFax(ContactItemBean selectedFax) {
		this.selectedFax = selectedFax;
	}

	public ContactItemBean getSelectedFax() {
		return selectedFax;
	}

	public List<ContactItemBean> getWebsiteList() {
		return websiteList;
	}

	public void setSelectedWebsite(ContactItemBean selectedWebsite) {
		this.selectedWebsite = selectedWebsite;
	}

	public ContactItemBean getSelectedWebsite() {
		return selectedWebsite;
	}

	public void addFax() {
		faxList.add(new ContactItemBean());
	}

	public void removeFax() {
		if (selectedFax != null) {
			if (selectedFax.getId() != null) {
				deletedItems.add(new Fax(selectedFax.getId(), selectedFax
						.getScope(), selectedFax.getValue(), selectedFax
						.getIsDefault()));
			}
			faxList.remove(selectedFax);
		}
	}

	public boolean getShowRemoveFaxButton() {
		if (faxList.size() > 1) {
			return true;
		}
		return false;
	}

	public void addWebsite() {
		websiteList.add(new ContactItemBean());
	}

	public void removeWebsite() {
		if (selectedWebsite != null) {
			if (selectedWebsite.getId() != null) {
				deletedItems.add(new Website(selectedWebsite.getId(),
						selectedWebsite.getScope(), selectedWebsite.getValue(),
						selectedWebsite.getIsDefault()));
			}
			websiteList.remove(selectedWebsite);
		}
	}

	public boolean getShowRemoveWebsiteButton() {
		if (websiteList.size() > 1) {
			return true;
		}
		return false;
	}

	public String navigateBack() {
		String backUrl = contactModel.getBackUrl();
		if (backUrl != null) {
			contactModel.setBackUrl(null); // clear back url
			return backUrl;
		}
		return navigationManager.getContactmanagement();
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			ByteArrayInputStream inputstream = (ByteArrayInputStream) event.getFile().getInputstream();
			byte[] bytes = ImageUtil.resize(inputstream, IMAGE_WIDTH, IMAGE_HEIGHT);
			bean.setImage(bytes);
			bean.setImageType(event.getFile().getContentType());
		} catch (IOException e) {
			logger.error("Could not process uploaded file. Error was: ", e);
		}
    }
	
	/**
	 * Thanks to a PrimeFaces bug, we can't just return a StreamedContent here when using 'View' Scope.
	 * <p>
	 * Instead we have to store the image as byte[] in the session map with a random UUID. That random UUID is passed to the {@link ImageServlet} 
	 * and there it will be retrieved from the session map and rendered as image. Thanks PrimeFaces :( 
	 * 
	 * @return a random UUID
	 */
	public String getImage() {
		if(bean.getImage() != null) {
			//create image
			
			String key = UUID.randomUUID().toString();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, bean.getImage());
			return key;
		}
		
		return null;
    }
	
	public boolean getHasImage() {
		if(bean.getImage() == null || bean.getImage().length == 0) {
			return false;
		}
		return true;
	}
}
