package org.businessmanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FAX")
public class Fax extends ContactItem {

	public String getFax() {
		return getValue();
	}
	
	public void setFax(String fax) {
		setValue(fax);
	}
}
