package org.businessmanager.service;

import org.businessmanager.domain.Address;

/**
 * @author Christian Ternes
 *
 */
public interface AddressService {

	/**
	 * Retrieves an {@link Address} from the database by its id.
	 * 
	 * @param id
	 * @return
	 */
	public Address getAddressById(Long id);
}
