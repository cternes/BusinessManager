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
package org.businessmanager.service.security;

import org.businessmanager.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpringSecurityServiceImpl implements SpringSecurityService {

	@Autowired
	private UserService userService;
	
	@Override
	public User getLoggedInUser() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userService.getUserByName(username);
		}
		return null;
	}

	@Override
	public Long getLoggedInUserId() {
		User user = getLoggedInUser();
		if(user == null) {
			return -1L;
		}
		return user.getId();
	}
}
