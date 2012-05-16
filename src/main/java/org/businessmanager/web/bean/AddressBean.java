package org.businessmanager.web.bean;

import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;

public class AddressBean {
	private Long id;
	private String street;
	private String housenumber;
	private String city;
	private String zipCode;
	private String forAttentionOf;
	private String postOfficeBox;
	private AddressType scope;
	private String country;
	private Boolean isDefault = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getForAttentionOf() {
		return forAttentionOf;
	}

	public void setForAttentionOf(String forAttentionOf) {
		this.forAttentionOf = forAttentionOf;
	}

	public String getPostOfficeBox() {
		return postOfficeBox;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

	public AddressType getScope() {
		return scope;
	}

	public void setScope(AddressType scope) {
		this.scope = scope;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public AddressBean getMappedAddressBean(Address theAddress) {
		setId(theAddress.getId());
		setStreet(theAddress.getStreet());
		setHousenumber(theAddress.getHousenumber());
		setZipCode(theAddress.getZipCode());
		setCity(theAddress.getCity());
		setScope(theAddress.getScope());
		setForAttentionOf(theAddress.getForAttentionOf());
		setPostOfficeBox(theAddress.getPostOfficeBox());
		setCountry(theAddress.getCountry());
		setIsDefault(theAddress.getIsDefault());
		return this;
	}

	public Address getAddress(boolean isIDExisting) {
		Address anAddress = new Address();
		
		if(isIDExisting) {
			anAddress.setId(getId());
		}
		anAddress.setStreet(getStreet());
		anAddress.setHousenumber(getHousenumber());
		anAddress.setZipCode(getZipCode());
		anAddress.setCity(getCity());
		anAddress.setScope(getScope());
		anAddress.setForAttentionOf(getForAttentionOf());
		anAddress.setPostOfficeBox(getPostOfficeBox());
		anAddress.setCountry(getCountry());
		anAddress.setIsDefault(getIsDefault());
		return anAddress;
	}

	public void copyDataFromAddressBean(AddressBean theFromBean) {
		setHousenumber(theFromBean.getHousenumber());
		setCity(theFromBean.getCity());
		setZipCode(theFromBean.getZipCode());
		setStreet(theFromBean.getStreet());
		setScope(theFromBean.getScope());
		setId(theFromBean.getId());
		setForAttentionOf(theFromBean.getForAttentionOf());
		setPostOfficeBox(theFromBean.getPostOfficeBox());
		setCountry(theFromBean.getCountry());
		setIsDefault(theFromBean.getIsDefault());
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}
}
