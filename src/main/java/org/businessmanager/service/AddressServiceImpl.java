package org.businessmanager.service;

import org.businessmanager.database.AddressDao;
import org.businessmanager.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service to manage {@link Address}s. 
 * 
 * @author Christian Ternes
 *
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao dao;
	
	@Override
	public Address getAddressById(Long id) {
		return dao.findById(id);
	}

}
