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
package org.businessmanager.database.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.User;
import org.businessmanager.domain.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * @author Christian Ternes
 *
 */
public class SpringSecurityDaoImpl extends JdbcDaoImpl {
 
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails	= super.loadUserByUsername(username);
		String password = userDetails.getPassword();
		boolean enabled = userDetails.isEnabled();
		boolean accountNonExpired = userDetails.isAccountNonExpired();
		boolean credentialsNonExpired = userDetails.isCredentialsNonExpired();
		boolean accountNonLocked = userDetails.isAccountNonLocked();
		Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
		
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(userDetails.getUsername(), password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		List<String> grantedRoles = retrieveRolesForUser(username);
		userDetailsImpl.setGrantedRoles(grantedRoles);
		
		return userDetailsImpl;
	}
	
	private List<String> retrieveRolesForUser(String username) {
		User user = userDao.findUserByName(username);
		
		List<String> roleList = new ArrayList<String>();
		List<Role> roles = roleDao.findRolesForUser(user.getId());
		for (Role role : roles) {
			roleList.add(role.getName());
		}
		return roleList;
	}
}
