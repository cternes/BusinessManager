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

public interface SpringSecurityService {

	/**
	 * Retrieves the logged in {@link User} for the current session. 
	 * 
	 * @return the logged in {@link User}
	 */
	public User getLoggedInUser();
	
	/**
	 * Retrieves the user id of the current session. 
	 * 
	 * @return the id of the logged in user
	 */
	public Long getLoggedInUserId();
}
