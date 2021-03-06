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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.businessmanager.domain.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="users")
public final class User extends AbstractEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50)
	@NotEmpty
	private String username;
	
	@Column(length=50)
	@NotEmpty
	private String password;
	
	@Column
	private boolean enabled = true;
	
	@Column(length=50)
	private String email;
	
	@Column
	private boolean mustChangePassword = true;
	
	@Column
	private Long salt;
	
	@Column
	private boolean isDefaultAdminUser = false;
	
	private transient List<Group> assignedGroups;
	private transient boolean isAdministrator;

	protected User() {
	}
	
	public User(String username, String password) {
		if(username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Param username is null or empty");
		}
		if(password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Param password is null or empty");
		}
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAssignedGroups(List<Group> assignedGroups) {
		this.assignedGroups = assignedGroups;
	}
	
	public List<Group> getAssignedGroups() {
		return assignedGroups;
	}
	
	public int getAssignedGroupSize() {
		return assignedGroups.size();
	}

	public boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", enabled="
				+ enabled + ", email=" + email + "]";
	}

	public Long getSalt() {
		return salt;
	}

	public void setSalt(Long salt) {
		this.salt = salt;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}
	
	public boolean getIsDefaultAdminUser() {
		return isDefaultAdminUser;
	}

	public void setDefaultAdminUser(boolean isDefaultAdminUser) {
		this.isDefaultAdminUser = isDefaultAdminUser;
	}

}
