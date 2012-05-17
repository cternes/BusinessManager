package org.businessmanager.web.controller;

import java.util.List;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;

/**
 * This interface should be used to programmatically interact with the addressManagement component.
 * 
 * @author Christian Ternes
 *
 */
public interface AddressManagementController {

	/**
	 * Initializes the addressManagement component with the given address.
	 * 
	 * @param theAddress an {@link Address} which will be listed in the component
	 */
	public void initializeAddressComponent(Address theAddress);
	
	/**
	 * Initializes the addressManagement component with the given list of addresses.
	 * 
	 * @param theAddressList a list of {@link Address} which will be listed in the component
	 */
	public void initializeAddressComponent(List<Address> theAddressList);
	
	/**
	 * Retrieves a list of {@link Address}, which were managed in the component.
	 * 
	 * @return a list of {@link Address}
	 */
	public List<Address> getAssignedAddressList();
	
	/**
	 * Restricts the number of assigneable addresses to the given number.
	 * 
	 * @param theMaxAssigneableAddresses the maximum number of assigneable addresses
	 */
	public void setMaxAssigneableAddresses(int theMaxAssigneableAddresses);
	
	/**
	 * Restricts the number of available address types.
	 * 
	 * @param theAvailableAddressTypes the available address types
	 */
	public void setAvailableAddressTypes(List<AddressType> theAvailableAddressTypes);
	
	public boolean isEditMode();
	
	public void findCity();
}
