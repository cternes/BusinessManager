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

import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.businessmanager.database.security.UserDao;
import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.User;
import org.businessmanager.error.DuplicateUserException;
import org.businessmanager.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Christian Ternes
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public List<User> getUsers() {
		List<User> userList = userDao.findAll();
		fetchRolesForUsers(userList);
		return userList;
	}
	
	private List<User> fetchRolesForUsers(List<User> userList) {
		for (User user : userList) {
			user.setAssignedRoles(roleService.getRolesForUser(user.getId()));
		}
		return userList;
	}

	@Override
	public User addUser(User user) {
		if(user == null) {
			throw new IllegalArgumentException("User is null");
		}
		
		if(usernameExistsAlready(user.getUsername())) {
			logger.warn("Could not create user " + user.getUsername() + " because there is already a user with the same username. Aborting...");
			throw new DuplicateUserException("User with username " + user.getUsername() + " exists already.");
		}
		else if(emailExistsAlready(user.getEmail())) {
			logger.warn("Could not create user " + user.getUsername() + " because there is already a user with the same email. Aborting...");
			throw new DuplicateUserException("User with email " + user.getEmail() + " exists already.");
		}
		else {
			user.setSalt(PasswordGenerator.generateSalt());
			
			String encodedPwd = PasswordGenerator.encodePassword(user.getPassword(), user.getSalt());
			user.setPassword(encodedPwd);
			
			logger.debug("Saving user "+user.getUsername()+" to database.");
			return userDao.save(user);
		}
	}

	private boolean emailExistsAlready(String email) {
		if(email != null && !email.isEmpty()) {
			User userFromDb = getUserByEmail(email);
			if(userFromDb != null) {
				return true;
			}
		}
		return false;
	}

	private boolean usernameExistsAlready(String username) {
		User userFromDb = getUserByName(username);
		if(userFromDb != null) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserByName(String username) {
		User user = userDao.findUserByName(username);
		return user;
	}

	@Override
	public User updateUser(User user, boolean updatePassword) {
		if(user == null) {
			throw new IllegalArgumentException("User is null");
		}
		
		if(updatePassword) {
			String encodedPwd = PasswordGenerator.encodePassword(user.getPassword(), user.getSalt());
			user.setPassword(encodedPwd);
		}
		
		logger.debug("Modifying user "+user.getUsername()+" in database.");
		return userDao.update(user);
	}
	
	@Override
	public User getUserById(Long productId) {
		User user = userDao.findById(productId);
		return user;
	}

	@Override
	public void deleteUser(Long userId) {
		User user = getUserById(userId);
		if(user != null) {
			removeUserFromRoles(user, roleService.getRolesForUser(userId));
			
			logger.debug("Deleting user "+user.getUsername()+" from database.");
			userDao.remove(user);
		}
	}

	private void removeUserFromRoles(User user, List<Role> roles) {
		for (Role role : roles) {
			role.getMembers().remove(user);
			roleService.updateRole(role);
		}
	}

	@Override
	public List<User> getUsers(SingularAttribute<User, ?> orderAttribute, boolean orderAsc) {
		List<User> userList = userDao.findAll(orderAttribute, orderAsc);
		fetchRolesForUsers(userList);
		return userList;
	}

	@Override
	public List<User> getUsers(SingularAttribute<User, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults, Map<SingularAttribute<User, ?>, Object> filters, boolean enableLikeSearch) {
		List<User> userList = userDao.findAll(orderAttribute, orderAsc, firstResult, maxResults, filters, enableLikeSearch);
		fetchRolesForUsers(userList);
		return userList;
	}
	
	@Override
	public int getNumberOfUsers() {
		return getNumberOfUsers(null, false);
	}

	@Override
	public int getNumberOfUsers(Map<SingularAttribute<User, ?>, Object> filterAttributes, boolean enableLikeSearch) {
		Long number = userDao.getCount(filterAttributes, enableLikeSearch);
		return Integer.parseInt(number.toString());
	}

	@Override
	public void deleteUsers(List<Long> userIds) {
		if(userIds != null) {
			for (Long userId : userIds) {
				deleteUser(userId);
			}
		}
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userDao.findUserByEmail(email);
		return user;
	}

}
