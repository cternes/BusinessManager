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

import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem;

/**
 * @author Christian Ternes
 *
 */
public interface ContactService {

	/**
	 * Save a {@link Contact} in the database.
	 * 
	 * @param contact the {@link Contact} which should be saved
	 * @return the {@link Contact} with set id
	 */
	public Contact saveContact(Contact contact);
	
	/**
	 * Retrieves all available {@link Contact}s from the database.
	 * 
	 * @return a list of {@link Contact}s
	 */
	public List<Contact> getContacts();

	/**
	 * Retrieves a {@link Contact} from the database by its id.
	 * 
	 * @param id the id of the {@link Contact}
	 * @return the {@link Contact} if found, null otherwise
	 */
	public Contact getContactById(Long id);
	
	/**
	 * Removes a {@link Contact} from the database.
	 * 
	 * @param contact the contact to remove
	 */
	public void deleteContact(Contact contact);
	
	/**
	 * Merges ContactItem with persisted instance
	 * 
	 * @param contactItem item to be merged
	 * @return merged ContactItem
	 */
	public ContactItem mergeContactItem(ContactItem contactItem);
	
	/**
	 * Removes ContactItem from persistence layer by id.
	 * 
	 * @param id the id of the item to be removed
	 */
	public void removeContactItem(Long id);
	
	public List<Contact> fullTextSearchContact(String searchString);
}
