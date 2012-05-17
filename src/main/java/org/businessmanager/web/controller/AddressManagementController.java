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
package org.businessmanager.web.controller;

import java.util.List;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;
import org.businessmanager.geodb.Country;

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
	
	public List<Country> getAvailableCountries();
}
