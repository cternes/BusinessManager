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
package org.businessmanager.dao.security;

import java.util.List;

import org.businessmanager.dao.GenericDao;
import org.businessmanager.domain.security.User;


/**
 * Manages the database access of the entity User.
 * 
 */
public interface UserDao extends GenericDao<User> {

	public User findUserByName(String username);
	
	public User findUserByEmail(String email);

	public List<User> findByUsernameFragment(String usernameFragment);

}
