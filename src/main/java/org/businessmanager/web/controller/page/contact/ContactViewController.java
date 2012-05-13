package org.businessmanager.web.controller.page.contact;

import org.businessmanager.web.controller.AbstractPageController;
import org.businessmanager.web.controller.state.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactViewController")
@Scope("request")
public class ContactViewController extends AbstractPageController {

	@Autowired
	private ContactModel model;

	public void setModel(ContactModel model) {
		this.model = model;
	}

	public ContactModel getModel() {
		return model;
	} 
}
