package org.businessmanager.service;

import java.util.List;

import junit.framework.Assert;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Contact;
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
public class AddressServiceTest {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ContactService contactService;
	
	@Test
	public void testFindById() {
		Contact contact = new Contact("address", "test");
		Address address = new Address();
		address.setStreet("test");
		address.setHousenumber("23");
		contact.getAddresses().add(address);
		contact = contactService.saveContact(contact);
		
		List<Contact> contactList = contactService.getContacts();
		Assert.assertEquals(contact.getId(), contactList.get(0).getId());
		Assert.assertEquals(1, contact.getAddresses().size());
		
		Long addressId = contact.getAddresses().get(0).getId();
		System.out.println("AddressId: "+addressId);
		
		Assert.assertNotNull(addressService.getAddressById(addressId));
	}
}
