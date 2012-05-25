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

import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.state.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Christian Ternes
 *
 */
@Component("contactSidebarController")
@Scope("request")
public class ContactSidebarController extends AbstractController {

	@Autowired
	private ContactModel model;
	
	public String navigateToEditContact() {
		model.setSelectedEntity(null);
		return navigationManager.getEditContact();
	}
	
	public String navigateToAllContacts() {
		return navigationManager.getContactmanagement();
	}
}
