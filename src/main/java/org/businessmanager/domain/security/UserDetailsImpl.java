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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl extends org.springframework.security.core.userdetails.User implements UserDetails {

	private static final long serialVersionUID = -4357117951117858284L;

	private List<String> grantedGroups = new ArrayList<String>();
	private Long salt;
	
	public UserDetailsImpl(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	public void addGrantedGroup(String group) {
		this.grantedGroups.add(group);
	}
	
	public List<String> getGrantedGroups() {
		return grantedGroups;
	}
	
	public void setGrantedGroups(List<String> groups) {
		this.grantedGroups = groups;
	}

	public Long getSalt() {
		return salt;
	}

	public void setSalt(Long salt) {
		this.salt = salt;
	}
	
}
