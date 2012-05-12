package org.businessmanager.service;

import java.util.List;

import junit.framework.Assert;

import org.businessmanager.domain.Contact;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ContactServiceTest {

	@Autowired
	private ContactService contactService;
	
	@Test
	public void testAddContact() {
		String email = "mail@localhost";
		Contact contact = createContact("firstname", "lastname", email, "555-22-666");
		contactService.addContact(contact);
		
		List<Contact> contactList = contactService.getContacts();
		Assert.assertEquals(1, contactList.size());
		
		Assert.assertEquals(1, contactList.get(0).getEmailList().size());
		Assert.assertEquals(email, contactList.get(0).getEmailList().get(0).getEmail());
	}
	
	private Contact createContact(String firstname, String lastname, String email, String phone) {
		Contact contact = new Contact(firstname, lastname);
		
		Email emailObj = new Email();
		emailObj.setScope("private");
		emailObj.setValue(email);
		contact.getContactItemList().add(emailObj);
		
		Phone phoneObj = new Phone();
		phoneObj.setScope("private");
		phoneObj.setValue(phone);
		contact.getContactItemList().add(phoneObj);
		
		return contact;
	}
}
