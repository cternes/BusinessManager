package org.businessmanager.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;
import org.businessmanager.geodb.OpenGeoDB;
import org.businessmanager.geodb.OpenGeoEntry;
import org.businessmanager.service.AddressService;
import org.businessmanager.web.bean.AddressBean;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
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
public class AddressManagementControllerImpl extends AbstractPageController implements AddressManagementController {

	private static final String CLIENT_ID_STREET = "addStreet";
	private static final String CLIENT_ID_NUMBER= "addNumber";
	private static final String CLIENT_ID_CITY = "addCity";
	private static final String CLIENT_ID_ZIP = "addZip";
	
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
	public void initializeAddressComponent(Address theAdresse) {
		if(theAdresse != null) {
			List<Address> aAddressList = new ArrayList<Address>();
			aAddressList.add(theAdresse);
			initializeAddressComponent(aAddressList);
		}
	}
	
	@Override
	public void initializeAddressComponent(List<Address> theAddressList) {
		if(theAddressList != null) {
			for (Address anAdresse : theAddressList) {
				AddressBean anAddressBean = new AddressBean().getMappedAddressBean(anAdresse);
				addressList.add(anAddressBean);
			}
		}
	}
	
	/**
	 * This id must only be unique for the current user and the current view.
	 * This id will never be transmitted to the database.
	 * 
	 * Therefore current millis should be sufficient here.
	 * 
	 */
	private long createUniqueId(AddressBean theAddressBean) {
		return theAddressBean.hashCode()+System.currentTimeMillis();
	}
	
	public void addAddress() {
		if(!getIsMaximumReached()) {
			//create unique id and add address
			if(validateInput(addressToUpdate)) {
				addressToUpdate.setId(createUniqueId(addressToUpdate));
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
			AddressBean anAddress = findAddressInList(addressToUpdate.getId());
			
			if(anAddress != null) {
				removeDefaultFlag(anAddress);
				anAddress.copyDataFromAddressBean(addressToUpdate);
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
		List<Address> aResultList = new ArrayList<Address>();
		for (AddressBean aBean : addressList) {
			Address aAdresse = aBean.getAddress(isIdExisting(aBean.getId()));
			
			aResultList.add(aAdresse);
		}
		return aResultList;
	}

	@Override
	public void setMaxAssigneableAddresses(int theMaxAssigneableAddresses) {
		this.maxAssigneableAddresses = theMaxAssigneableAddresses;
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
	
	/**
	 * Reset the controller state, just for unit tests.
	 */
	void reset() {
		addressList = new ArrayList<AddressBean>();
		selectedAddress = null;
		addressToUpdate = new AddressBean();
		showAddressAddDialog = false;
		showAddressUpdateDialog = false;
		maxAssigneableAddresses = -1;
	}
	
	private boolean validateInput(AddressBean theBean) {
		boolean isValid = true;
		String street = theBean.getStreet();
		if(street == null || street.isEmpty()) {
			addErrorMessage(CLIENT_ID_STREET, "addressmanagement_error_empty_street");
			isValid = false;
		}
		
		String number = theBean.getHousenumber();
		if(number == null || number.isEmpty()) {
			addErrorMessage(CLIENT_ID_NUMBER, "addressmanagement_error_empty_housenumber");
			isValid = false;
		}
		
		String zip = theBean.getZipCode();
		if(zip == null || zip.isEmpty()) {
			addErrorMessage(CLIENT_ID_ZIP, "addressmanagement_error_empty_zipcode");
			isValid = false;
		}
		
		String city = theBean.getCity();
		if(city == null || city.isEmpty()) {
			addErrorMessage(CLIENT_ID_CITY, "addressmanagement_error_empty_city");
			isValid = false;
		}
		return isValid;
	}
	
	private AddressBean findAddressInList(Long theId) {
		for (AddressBean aAddressBean : addressList) {
			if(aAddressBean.getId().equals(theId)) {
				return aAddressBean;
			}
		}
		return null;
	}
	
	private boolean isIdExisting(Long theId) {
		Address aAdresseById = addressService.getAddressById(theId);
		if(aAdresseById != null) {
			return true;
		}
		return false;
	}
	
	public List<AddressType> getAvailableAddressTypeValues() {
//		List<String> aRemoveList = getUsedAddressTypes();
//		if(availableAddressTypes == null || availableAddressTypes.size() == 0) {
//			String[] aFilteredArray = ListHelper.filterArray(AddressType.values(), aRemoveList, AddressType.class);
//			return getCurrentAddressType(aFilteredArray);
//		}
//		String[] aFilteredArray = ListHelper.filterArray(availableAddressTypes.toArray(new AddressType[0]), aRemoveList, AddressType.class);
//		return getCurrentAddressType(aFilteredArray);
		return availableAddressTypes;
	}
	
	private AddressType[] getCurrentAddressType(AddressType[] theAvailableTypes) {
		if(theAvailableTypes.length == 0) {
			if(addressToUpdate != null) {
				AddressType[] addressTypes = new AddressType[1];
				addressTypes[0] = addressToUpdate.getScope();
				return addressTypes; 
			}
		}
		return theAvailableTypes;
	}

	private List<AddressType> getUsedAddressTypes() {
		List<AddressType> aRemoveList = new ArrayList<AddressType>();
		for (AddressBean aBean : addressList) {
			AddressType aType = aBean.getScope();
			aRemoveList.add(aType);
		}
		return aRemoveList;
	}

	/* (non-Javadoc)
	 * @see ch.vqf.cp.web.controller.IAddressManagementController#setAvailableAddressTypes(java.util.List)
	 */
	@Override
	public void setAvailableAddressTypes(List<AddressType> theAvailableAddressTypes) {
		this.availableAddressTypes = theAvailableAddressTypes;
	}

    public String getPanelTitle() {
    	if(showAddressAddDialog) {
    		return ResourceBundleProducer.getString("addressmanagement_address_new");
    	}
    	return ResourceBundleProducer.getString("addressmanagement_update_address");
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
    
}