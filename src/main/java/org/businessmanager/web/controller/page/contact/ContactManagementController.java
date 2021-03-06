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

import org.businessmanager.service.ContactService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactManagementController")
@Scope("request")
public class ContactManagementController extends AbstractController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactModel model;
	
	private String searchString = "";

	private void fetchContacts() {
		model.setEntityList(contactService.getContacts());
	}
	
	public ContactModel getModel() {
		if(model.getEntityList() == null) {
			fetchContacts();
		}
		return model;
	}
	
	public String navigateToContactView() {
		return navigationManager.getContactView();
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
