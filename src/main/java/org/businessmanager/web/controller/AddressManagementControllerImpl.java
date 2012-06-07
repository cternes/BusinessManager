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

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;
import org.businessmanager.geodb.Country;
import org.businessmanager.geodb.OpenGeoDB;
import org.businessmanager.geodb.OpenGeoEntry;
import org.businessmanager.i18n.ResourceBundleAccessor;
import org.businessmanager.service.AddressService;
import org.businessmanager.web.bean.AddressBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This controller manages the whole addressManagement component.
 * 
 * @author Christian Ternes
 *
 */
@Component("addressManagementController")
@Scope("view")
public class AddressManagementControllerImpl extends AbstractController implements AddressManagementController {

	private static final String CLIENT_ID_STREET = "addStreet";
	private static final String CLIENT_ID_NUMBER= "addNumber";
	private static final String CLIENT_ID_CITY = "addCity";
	private static final String CLIENT_ID_ZIP = "addZip";
	private static final String CLIENT_ID_COUNTRY = "addCountry";
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OpenGeoDB openGeoDB;
	
	private List<AddressBean> addressList = new ArrayList<AddressBean>();
	private List<AddressType> availableAddressTypes = new ArrayList<AddressType>();
	private AddressBean selectedAddress;
	private AddressBean addressToUpdate;
	private boolean showAddressAddDialog = false;
	private boolean showAddressUpdateDialog = false;
	
	private int maxAssigneableAddresses = 3; //DEFAULT is 3
	
	@Override
	public void initializeAddressComponent(Address address) {
		if(address != null) {
			List<Address> addressList = new ArrayList<Address>();
			addressList.add(address);
			initializeAddressComponent(addressList);
		}
	}
	
	@Override
	public void initializeAddressComponent(List<Address> list) {
		if(list != null) {
			for (Address address : list) {
				AddressBean bean = new AddressBean().getMappedAddressBean(address);
				addressList.add(bean);
			}
		}
	}
	
	private long createTempUniqueId(AddressBean theAddressBean) {
		return theAddressBean.hashCode()+System.currentTimeMillis();
	}
	
	public void addAddress() {
		if(!getIsMaximumReached()) {
			//create unique id and add address
			if(validateInput(addressToUpdate)) {
				addressToUpdate.setId(createTempUniqueId(addressToUpdate));
				removeDefaultFlag(addressToUpdate);
				addressList.add(addressToUpdate);
				addressToUpdate = new AddressBean();
				//hide add dialog
				showAddressAddDialog = false;
			}
		}
	}
	
	private void removeDefaultFlag(AddressBean addressBean) {
		if(addressBean.getIsDefault()) {
			for (AddressBean bean : addressList) {
				if(!bean.equals(addressBean)) {
					bean.setIsDefault(false);
				}
			}
		}
	}
	
	public void viewSelected() {
		if(selectedAddress == null) {
			addMessage(FacesMessage.SEVERITY_WARN, "no_data_selected");
		}
		else {
			if(addressToUpdate == null) {
				addressToUpdate = new AddressBean();
			}
			addressToUpdate.copyDataFromAddressBean(selectedAddress);
		}
	}
	
	public void deleteSelected() {
		if(selectedAddress == null) {
			addMessage(FacesMessage.SEVERITY_WARN, "no_data_selected");
		}
		else {
			addressList.remove(selectedAddress);
		}
	}
	
	public void updateAddress() {
		if(validateInput(addressToUpdate)) {
			AddressBean address = findAddressInList(addressToUpdate.getId());
			
			if(address != null) {
				removeDefaultFlag(address);
				address.copyDataFromAddressBean(addressToUpdate);
				showAddressUpdateDialog = false;
			}
			else {
				addMessage(FacesMessage.SEVERITY_WARN, "no_data_selected");
			}
		}
	}
	
	public List<AddressBean> getAddressList() {
		return addressList;
	}
	
	public void setSelectedAddress(AddressBean selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

	public AddressBean getSelectedAddress() {
		return selectedAddress;
	}

	public void setAddressToUpdate(AddressBean addressToUpdate) {
		this.addressToUpdate = addressToUpdate;
	}

	public AddressBean getAddressToUpdate() {
		return addressToUpdate;
	}

	public void setShowAddressAddDialog(boolean showAddressAddDialog) {
		this.showAddressAddDialog = showAddressAddDialog;
		this.showAddressUpdateDialog = !showAddressAddDialog;
		addressToUpdate = new AddressBean();
	}

	public boolean isShowAddressAddDialog() {
		return showAddressAddDialog;
	}

	public void setShowAddressUpdateDialog(boolean showAddressUpdateDialog) {
		if(showAddressUpdateDialog) {
			if(selectedAddress != null) {
				this.showAddressUpdateDialog = showAddressUpdateDialog;
				this.showAddressAddDialog = !showAddressUpdateDialog;	
			}
		}
		else {
			this.showAddressUpdateDialog = showAddressUpdateDialog;
			this.showAddressAddDialog = !showAddressUpdateDialog;
		}
	}

	public boolean isShowAddressUpdateDialog() {
		return showAddressUpdateDialog;
	}

	@Override
	public List<Address> getAssignedAddressList() {
		List<Address> resultList = new ArrayList<Address>();
		for (AddressBean bean : addressList) {
			Address aAdresse = bean.getAddress(isIdExisting(bean.getId()));
			
			resultList.add(aAdresse);
		}
		return resultList;
	}

	@Override
	public void setMaxAssigneableAddresses(int maxAssigneableAddresses) {
		this.maxAssigneableAddresses = maxAssigneableAddresses;
	}
	
	public int getMaxAssigneableAddresses() {
		return maxAssigneableAddresses;
	}
	
	public boolean getIsMaximumReached() {
		if(maxAssigneableAddresses != -1 && addressList.size() == maxAssigneableAddresses) {
			return true;
		}
		return false;
	}
	
	void reset() {
		addressList = new ArrayList<AddressBean>();
		selectedAddress = null;
		addressToUpdate = new AddressBean();
		showAddressAddDialog = false;
		showAddressUpdateDialog = false;
		maxAssigneableAddresses = -1;
	}
	
	private boolean validateInput(AddressBean bean) {
		boolean isValid = true;
		String street = bean.getStreet();
		if(street == null || street.isEmpty()) {
			addErrorMessage(CLIENT_ID_STREET, "addressmanagement_error_empty_street");
			isValid = false;
		}
		
		String number = bean.getHousenumber();
		if(number == null || number.isEmpty()) {
			addErrorMessage(CLIENT_ID_NUMBER, "addressmanagement_error_empty_housenumber");
			isValid = false;
		}
		
		String zip = bean.getZipCode();
		if(zip == null || zip.isEmpty()) {
			addErrorMessage(CLIENT_ID_ZIP, "addressmanagement_error_empty_zipcode");
			isValid = false;
		}
		
		String city = bean.getCity();
		if(city == null || city.isEmpty()) {
			addErrorMessage(CLIENT_ID_CITY, "addressmanagement_error_empty_city");
			isValid = false;
		}
		
		Country country = bean.getCountry();
		if(country == null) {
			addErrorMessage(CLIENT_ID_COUNTRY, "addressmanagement_error_empty_country");
			isValid = false;
		}
		
		return isValid;
	}
	
	private AddressBean findAddressInList(Long addressId) {
		for (AddressBean aAddressBean : addressList) {
			if(aAddressBean.getId().equals(addressId)) {
				return aAddressBean;
			}
		}
		return null;
	}
	
	private boolean isIdExisting(Long addressId) {
		Address address = addressService.getAddressById(addressId);
		if(address != null) {
			return true;
		}
		return false;
	}
	
	public List<AddressType> getAvailableAddressTypeValues() {
		return availableAddressTypes;
	}
	
	@Override
	public void setAvailableAddressTypes(List<AddressType> availableAddressTypes) {
		this.availableAddressTypes = availableAddressTypes;
	}

    public String getPanelTitle() {
    	if(showAddressAddDialog) {
    		return ResourceBundleAccessor.getString("addressmanagement_address_new");
    	}
    	return ResourceBundleAccessor.getString("addressmanagement_update_address");
    }
    
    public void closePanel() {
    	this.showAddressAddDialog = false;
    	this.showAddressUpdateDialog = false;
    }
    
    public boolean isEditMode() {
    	if(showAddressAddDialog || showAddressUpdateDialog) {
    		return true;
    	}
    	return false;
    }

	@Override
	public void findCity() {
		String zipCode = addressToUpdate.getZipCode();
		if(zipCode == null || zipCode.length() < 3) {
			return;
		}
		
		List<OpenGeoEntry> findByZipCode = openGeoDB.findByZipCode("DE", zipCode);
		
		if(findByZipCode != null && findByZipCode.size() > 0) {
			addressToUpdate.setCity(findByZipCode.get(0).getName());
		}
	}
	
	public List<Country> getAvailableCountries() {
		return openGeoDB.getListOfCountries(facesContext.getLocale().getLanguage());
	}
    
}
