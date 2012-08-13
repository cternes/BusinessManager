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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.businessmanager.annotation.HandlesExceptions;
import org.businessmanager.domain.Contact;
import org.businessmanager.service.ContactService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.ContactModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactViewController")
@Scope("request")
public class ContactViewController extends AbstractController {

	@Autowired
	private ContactModel model;
	
	@Autowired
	private ContactService contactService;

	private StreamedContent vcard; 
	
	@PostConstruct
	public void init() {
		//refreshing entity here, to prevent lazyInitException
		if(model.getSelectedEntity() != null) {
			Contact contactFromDb = contactService.getContactById(model.getSelectedEntity().getId());
			model.setSelectedEntity(contactFromDb);
		}
	}
	
	public void setModel(ContactModel model) {
		this.model = model;
	}

	public ContactModel getModel() {
		return model;
	}
	
	private boolean renderList(List<?> list) {
		if(list.size() > 0) {
			return true;
		}
		return false;
	}
	
	public boolean getRenderEmail() {
		return renderList(model.getSelectedEntity().getEmailList());
	}
	
	public boolean getRenderFax() {
		return renderList(model.getSelectedEntity().getFaxList());
	}
	
	public boolean getRenderWebsite() {
		return renderList(model.getSelectedEntity().getWebsiteList());
	}
	
	public boolean getRenderPhone() {
		return renderList(model.getSelectedEntity().getPhoneList());
	}
	
	public boolean getRenderAddress() {
		return renderList(model.getSelectedEntity().getAddresses());
	}
	
	public boolean getRenderMisc() {
		Contact selectedEntity = model.getSelectedEntity();
		if(selectedEntity.getBirthday() != null || selectedEntity.getNotes() != null || selectedEntity.getInstantMessenger() != null) {
			return true;
		}
		return false;
	}
	
	public String navigateToEditContact() {
		model.setBackUrl(navigationManager.getContactView());
		
		return navigationManager.getEditContact();
	}
	
	@HandlesExceptions
	public String deleteContact() {
		if(model.getSelectedEntity() != null) {
			contactService.deleteContact(model.getSelectedEntity());
			model.refresh();
			
			addMessage(FacesMessage.SEVERITY_INFO, "editcontact_success_contact_deleted");
			return navigationManager.getContactmanagement();
		}
		return "#";
	}
	
	public String getImage() {
		Contact selectedEntity = model.getSelectedEntity();
		if(selectedEntity.getImage() != null) {
			//create image
			
			String key = UUID.randomUUID().toString();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, selectedEntity.getImage());
			return key;
		}
		
		return null;
    }
	
	public boolean getHasImage() {
		Contact selectedEntity = model.getSelectedEntity();
		if(selectedEntity.getImage() == null || selectedEntity.getImage().length == 0) {
			return false;
		}
		return true;
	}
	
	public StreamedContent getVcard() {
		if(vcard == null) {
			createVcard();
		}
		return vcard;
	}

	private void createVcard() {
		Contact contact = model.getSelectedEntity();
		OutputStream out = contactService.getAsVCard(contact);
		
		InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
		vcard = new DefaultStreamedContent(inputStream, "", contact.getFirstname()+"-"+contact.getLastname()+".vcf");
	}
}
