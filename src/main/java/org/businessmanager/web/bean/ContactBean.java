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
package org.businessmanager.web.bean;

import java.util.Date;

import org.businessmanager.domain.Contact;
import org.businessmanager.domain.Contact.Salutation;

public class ContactBean {

	private Salutation salutation;
	private String title;
	private String firstname;
	private String lastname;
	private String jobTitle;
	private String company;
	private Date birthday;
	private String notes;
	private String instantMessenger;
	private byte[] image;
	private String imageType;

	public Salutation getSalutation() {
		return salutation;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

	public void setInstantMessenger(String instantMessenger) {
		this.instantMessenger = instantMessenger;
	}

	public String getInstantMessenger() {
		return instantMessenger;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	public void copyDataFromContact(Contact contact) {
		if(contact.getBirthday() != null) {
			setBirthday(contact.getBirthday().getTime());
		}
		setCompany(contact.getCompany());
		setFirstname(contact.getFirstname());
		setInstantMessenger(contact.getInstantMessenger());
		setJobTitle(contact.getJobTitle());
		setLastname(contact.getLastname());
		setNotes(contact.getNotes());
		setSalutation(contact.getSalutation());
		setTitle(contact.getTitle());
		setImage(contact.getImage());
		setImageType(contact.getImageType());
	}

}
