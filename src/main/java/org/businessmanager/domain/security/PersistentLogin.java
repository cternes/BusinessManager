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
package org.businessmanager.domain.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.businessmanager.domain.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author Christian Ternes
 *
 */
@Entity(name="persistent_logins")
public final class PersistentLogin extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=64)
	@NotEmpty
	private String username;
	
	@Column(length=64)
	@NotEmpty
	private String series;
	
	@Column(length=64)
	@NotEmpty
	private String token;
	
	@Column
	@NotEmpty
	private Date last_used;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastUsed() {
		return last_used;
	}

	public void setLastUsed(Date last_used) {
		this.last_used = last_used;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (last_used == null ? 0 : last_used.hashCode());
		result = prime * result + (series == null ? 0 : series.hashCode());
		result = prime * result + (token == null ? 0 : token.hashCode());
		result = prime * result + (username == null ? 0 : username.hashCode());
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
		PersistentLogin other = (PersistentLogin) obj;
		if (last_used == null) {
			if (other.last_used != null) {
				return false;
			}
		}
		else if (!last_used.equals(other.last_used)) {
			return false;
		}
		if (series == null) {
			if (other.series != null) {
				return false;
			}
		}
		else if (!series.equals(other.series)) {
			return false;
		}
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		}
		else if (!token.equals(other.token)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		}
		else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
}
