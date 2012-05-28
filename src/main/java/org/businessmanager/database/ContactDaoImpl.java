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
package org.businessmanager.database;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.Validate;
import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem;
import org.businessmanager.domain.Contact_;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Fax;
import org.businessmanager.domain.Phone;
import org.businessmanager.domain.Website;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

/**
 * @author Christian Ternes
 * 
 */
@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact> implements
		ContactDao {

	@Override
	public List<Contact> findAll() {
		return findAll(Contact_.lastname, true);
	}

	@Override
	public Class<Contact> getPersistenceClass() {
		return Contact.class;
	}

	@Override
	public Email mergeEmail(Email email) {
		Validate.notNull(email);
		return getEntityManager().merge(email);
	}

	@Override
	public Phone mergePhone(Phone phone) {
		Validate.notNull(phone);
		return getEntityManager().merge(phone);
	}

	@Override
	public Fax mergeFax(Fax fax) {
		Validate.notNull(fax);
		return getEntityManager().merge(fax);
	}

	@Override
	public Website mergeWebsite(Website website) {
		Validate.notNull(website);
		return getEntityManager().merge(website);
	}

	@Override
	public void removeContactItem(Long id) {
		ContactItem contactItem = getEntityManager()
				.find(ContactItem.class, id);

		if (contactItem != null) {
			getEntityManager().remove(contactItem);
		}
	}

	@Override
	public List<Contact> fullTextSearchContact(String searchString) {
		EntityManager em = getEntityManager();
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(em);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Contact.class).get();
		org.apache.lucene.search.Query query = qb
				.keyword()
				.onFields("firstname", "lastname", "company",
						"contactItems.value").matching(searchString)
				.createQuery();

		// wrap Lucene query in a javax.persistence.Query
		javax.persistence.Query persistenceQuery = fullTextEntityManager
				.createFullTextQuery(query, Contact.class);

		// execute search
		List<?> result = persistenceQuery.getResultList();

		// avoid ClassCastException
		if(result == null) {
			return null;
		}
		
		return (List<Contact>) result;
	}
}
