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
package org.businessmanager.service;

import java.util.List;

import junit.framework.Assert;

import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem.Scope;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/*
@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional*/
public class ContactServiceTest {

	@Autowired
	private ContactService contactService;
	
	@Test
	public void testAddContact() {
		/*String email = "mail@localhost";
		Contact contact = createContact("firstname", "lastname", email, "555-22-666");
		contactService.saveContact(contact);
		
		List<Contact> contactList = contactService.getContacts();
		Assert.assertEquals(1, contactList.size());
		
		Assert.assertEquals(1, contactList.get(0).getEmailList().size());
		Assert.assertEquals(email, contactList.get(0).getEmailList().get(0).getEmail());
		
		
		Contact contact3 = contactList.get(0);
		List<Email> emailList = contact3.getEmailList();
		Email email2 = emailList.get(0);*/
		
//		contactService.deleteContactItemFromContact(email2);
	}
	
	private Contact createContact(String firstname, String lastname, String email, String phone) {
		Contact contact = new Contact(firstname, lastname);
		
		Email emailObj = new Email();
		emailObj.setScope(Scope.PRIVATE);
		emailObj.setValue(email);
		contact.getContactItemList().add(emailObj);
		
		Phone phoneObj = new Phone();
		phoneObj.setScope(Scope.PRIVATE);
		phoneObj.setValue(phone);
		contact.getContactItemList().add(phoneObj);
		
		return contact;
	}
}
