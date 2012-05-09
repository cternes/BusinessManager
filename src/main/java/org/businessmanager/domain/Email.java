package org.businessmanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EMAIL")
public class Email extends ContactItem {

	public String getEmail() {
		return getValue();
	}
	
	public void setEmail(String email) {
		setValue(email);
	}
}
