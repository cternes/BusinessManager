package org.businessmanager.web.controller.page.contact;

import org.businessmanager.service.ContactService;
import org.businessmanager.web.controller.state.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Christian Ternes
 *
 */
@Component("contactManagementController")
@Scope("request")
public class ContactManagementController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactModel model;

	private void fetchContacts() {
		model.setEntityList(contactService.getContacts());
	}
	
	public ContactModel getModel() {
		if(model.getEntityList() == null) {
			fetchContacts();
		}
		return model;
	}
}
