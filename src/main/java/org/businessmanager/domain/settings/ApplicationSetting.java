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
package org.businessmanager.domain.settings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.businessmanager.domain.AbstractEntity;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This entity represents an application setting. A setting consists of a key
 * and a value, both are strings. The key is mandatory. A setting can belong to
 * a user by setting the username. This is optional.
 * 
 * @author Christian Ternes
 * 
 */
@Entity
public final class ApplicationSetting extends AbstractEntity {

	public enum Group {
		USER_PREFERENCS, COMPANY_PREFERENCES;
		
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private Group paramGroup;

	@Column
	@NotEmpty
	private String paramKey;

	@Column
	private String paramValue;

	@Column
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public Group getParamGroup() {
		return paramGroup;
	}

	public void setParamGroup(Group paramGroup) {
		this.paramGroup = paramGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((paramKey == null) ? 0 : paramKey.hashCode());
		result = prime * result
				+ ((paramValue == null) ? 0 : paramValue.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationSetting other = (ApplicationSetting) obj;
		if (paramKey == null) {
			if (other.paramKey != null)
				return false;
		} else if (!paramKey.equals(other.paramKey))
			return false;
		if (paramValue == null) {
			if (other.paramValue != null)
				return false;
		} else if (!paramValue.equals(other.paramValue))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
