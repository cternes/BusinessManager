package org.businessmanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PHONE")
public class Phone extends ContactItem {

	public String getPhone() {
		return getValue();
	}
	
	public void setPhone(String phone) {
		setValue(phone);
	}
}
