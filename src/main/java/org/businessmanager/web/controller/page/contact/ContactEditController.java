package org.businessmanager.web.controller.page.contact;

import javax.faces.application.FacesMessage;

import org.businessmanager.domain.Contact;
import org.businessmanager.service.ContactService;
import org.businessmanager.web.bean.ContactBean;
import org.businessmanager.web.controller.AbstractPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactEditController")
@Scope("request")
public class ContactEditController extends AbstractPageController {

	@Autowired
	private ContactService contactService;
	
	private ContactBean bean = new ContactBean();

	public String saveContact() {
		if(bean.getName() != null && !bean.getName().isEmpty()) {
			Contact contact = createContact();
			contactService.addContact(contact);
			
			addMessage(FacesMessage.SEVERITY_INFO, "editcontact_success_contact_saved");
			
			return navigationHelper.getContactmanagement();
		}
		addMessage(FacesMessage.SEVERITY_WARN, "editcontact_warn_no_name");
		return "#";
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
	
}
