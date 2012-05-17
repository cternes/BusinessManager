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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.businessmanager.domain.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This entity represents an application setting.
 * A setting consists of a key and a value, both are strings. The key is mandatory.
 * 
 * @author Christian Ternes
 *
 */
@Entity
public final class ApplicationSetting extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	@NotEmpty
	private String paramKey;
	
	@Column
	private String paramValue;
	
	@Column
	private String context;

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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((paramKey == null) ? 0 : paramKey.hashCode());
		result = prime * result + ((paramValue == null) ? 0 : paramValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ApplicationSetting other = (ApplicationSetting) obj;
		if (context == null) {
			if (other.context != null) {
				return false;
			}
		}
		else if (!context.equals(other.context)) {
			return false;
		}
		if (paramKey == null) {
			if (other.paramKey != null) {
				return false;
			}
		}
		else if (!paramKey.equals(other.paramKey)) {
			return false;
		}
		if (paramValue == null) {
			if (other.paramValue != null) {
				return false;
			}
		}
		else if (!paramValue.equals(other.paramValue)) {
			return false;
		}
		return true;
	}
	
}
