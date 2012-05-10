package org.businessmanager.service;

import java.util.List;

import org.businessmanager.database.ContactDao;
import org.businessmanager.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Christian Ternes
 *
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDao contactDao;
	
	@Override
	public Contact addContact(Contact contact) {
		return contactDao.save(contact);
	}

	@Override
	public List<Contact> getContacts() {
		return contactDao.findAll();
	}

}
