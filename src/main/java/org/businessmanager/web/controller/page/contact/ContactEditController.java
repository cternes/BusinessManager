package org.businessmanager.web.controller.page.contact;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.businessmanager.aop.annotation.ErrorHandled;
import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Phone;
import org.businessmanager.service.ContactService;
import org.businessmanager.web.bean.ContactBean;
import org.businessmanager.web.bean.ContactItemBean;
import org.businessmanager.web.controller.AbstractPageController;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactEditController")
@Scope("view")
public class ContactEditController extends AbstractPageController {

	@Autowired
	private ContactService contactService;
	
	private ContactBean bean = new ContactBean();
	private List<ContactItemBean> emailList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedEmail;
	private List<ContactItemBean> phoneList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedPhone;
	private List<String> availableScopes = new ArrayList<String>();

	@PostConstruct
	public void init() {
		emailList.add(new ContactItemBean());
		phoneList.add(new ContactItemBean());
		
		availableScopes.add(ResourceBundleProducer.getString("scope_private"));
		availableScopes.add(ResourceBundleProducer.getString("scope_commercial"));
		availableScopes.add(ResourceBundleProducer.getString("scope_misc"));
	}
	
	@ErrorHandled
	public String saveContact() {
		if(validateInput()) {
			Contact contact = createContact();
			fillContact(contact);
			contactService.addContact(contact);
			
			addMessage(FacesMessage.SEVERITY_INFO, "editcontact_success_contact_saved");
			
			return navigationHelper.getContactmanagement();
		}
		
		return "#";
	}
	
	private void fillContact(Contact contact) {
		if(!StringUtils.isEmpty(bean.getCompany())) {
			contact.setCompany(bean.getCompany());
		}
		
		if(!StringUtils.isEmpty(bean.getJobTitle())) {
			contact.setJobTitle(bean.getJobTitle());
		}
		
		for (ContactItemBean contactItem : emailList) {
			Email email = new Email();
			addItemToContact(contact, contactItem, email);
		}
		
		for (ContactItemBean contactItem : phoneList) {
			Phone phone = new Phone();
			addItemToContact(contact, contactItem, phone);
		}
	}

	private void addItemToContact(Contact contact, ContactItemBean contactItem,	ContactItem item) {
		item.setScope(contactItem.getScope());
		item.setValue(contactItem.getValue());
		contact.getContactItemList().add(item);
	}
	
	private boolean validateInput() {
		boolean isValid = true;
		
		if(bean.getName() == null || bean.getName().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_WARN, "editcontact_warn_no_name");
			isValid = false;
		}
		
		//TODO: validate emails
		
		
		return isValid;
	}
	
	public ContactBean getBean() {
		return bean;
	}
	
	private Contact createContact() {
		String name = bean.getName();
		String[] splittedName = name.split(" ");
		
		Contact contact = null;
		if(splittedName.length == 1) {
			contact = new Contact(splittedName[0], "");
		} 
		else {
			contact = new Contact(splittedName[0], splittedName[1]);
		}
		
		return contact;
	}

	public List<ContactItemBean> getEmailList() {
		return emailList;
	}
	
	public void addEmail() {
		emailList.add(new ContactItemBean());
	}
	
	public void removeEmail() {
		if(selectedEmail != null) {
			emailList.remove(selectedEmail);
		}
	}

	public void setSelectedEmail(ContactItemBean selectedEmail) {
		this.selectedEmail = selectedEmail;
	}

	public ContactItemBean getSelectedEmail() {
		return selectedEmail;
	}
	
	public boolean getShowRemoveEmailButton() {
		if(emailList.size() > 1) {
			return true;
		}
		return false;
	}
	
	public List<String> getAvailableScopes() {
		return availableScopes;
	}

	public List<ContactItemBean> getPhoneList() {
		return phoneList;
	}
	
	public void addPhone() {
		phoneList.add(new ContactItemBean());
	}
	
	public void removePhone() {
		if(selectedPhone != null) {
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
		if(phoneList.size() > 1) {
			return true;
		}
		return false;
	}

}
