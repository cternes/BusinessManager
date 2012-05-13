package org.businessmanager.service;

import java.util.List;

import org.businessmanager.domain.Contact;

/**
 * @author Christian Ternes
 *
 */
public interface ContactService {

	/**
	 * Adds a new {@link Contact} to the database.
	 * 
	 * @param contact the {@link Contact} which should be created
	 * @return the {@link Contact} with set id
	 */
	public Contact addContact(Contact contact);
	
	/**
	 * Retrieves all available {@link Contact}s from the database.
	 * 
	 * @return a list of {@link Contact}s
	 */
	public List<Contact> getContacts();

}
