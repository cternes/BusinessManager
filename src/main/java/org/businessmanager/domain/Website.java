package org.businessmanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WEBSITE")
public class Website extends ContactItem {

	public String getWebsite() {
		return getValue();
	}
	
	public void setWebsite(String website) {
		setValue(website);
	}
}
