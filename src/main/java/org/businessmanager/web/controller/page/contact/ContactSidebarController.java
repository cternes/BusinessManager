package org.businessmanager.web.controller.page.contact;

import org.businessmanager.web.controller.AbstractPageController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Christian Ternes
 *
 */
@Component("contactSidebarController")
@Scope("request")
public class ContactSidebarController extends AbstractPageController {

	public String navigateToEditContact() {
		return navigationHelper.getEditContact();
	}
	
	public String navigateToAllContacts() {
		return navigationHelper.getContactmanagement();
	}
}
