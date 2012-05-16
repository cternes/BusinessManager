package org.businessmanager.database;

import java.util.List;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address_;
import org.springframework.stereotype.Repository;

/**
 * @author Christian Ternes
 *
 */
@Repository
public class AddressDaoImpl extends GenericDaoImpl<Address> implements AddressDao {

	@Override
	public List<Address> findAll() {
		return findAll(Address_.id, true);
	}

	@Override
	public Class<Address> getPersistenceClass() {
		return Address.class;
	}

}
