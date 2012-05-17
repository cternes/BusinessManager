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

import java.util.ArrayList;
import java.util.List;

import org.businessmanager.web.bean.ContactItemBean;
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
