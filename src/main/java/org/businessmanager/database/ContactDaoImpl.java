package org.businessmanager.database;

import java.util.List;

import org.businessmanager.domain.Contact;
import org.businessmanager.domain.Contact_;
import org.springframework.stereotype.Repository;

/**
 * @author Christian Ternes
 *
 */
@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact> implements ContactDao {

	@Override
	public List<Contact> findAll() {
		return findAll(Contact_.lastname, true);
	}

	@Override
	public Class<Contact> getPersistenceClass() {
		return Contact.class;
	}

}
