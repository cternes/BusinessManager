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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.businessmanager.database.security.RoleDao;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.Role_;
import org.businessmanager.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Christian Ternes
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> getRoles() {
		return roleDao.findAll();
	}

	@Override
	public Role addRole(Role role) {
		if(role == null) {
			throw new IllegalArgumentException("Role is null");
		}
		
		if(roleExistsAlready(role.getName())) {
			logger.warn("Could not create role " + role.getName() + " because there is already a role with the same name. Aborting...");
			throw new IllegalArgumentException("Role with name " + role.getName() + " exists already.");
		}
		else {
			logger.debug("Adding role "+role.getName()+" to database.");
			return roleDao.save(role);
		}
	}

	private boolean roleExistsAlready(String name) {
		Role roleFromDb = getRoleByName(name);
		if(roleFromDb != null) {
			return true;
		}
		return false;
	}

	@Override
	public Role updateRole(Role role) {
		if(role == null) {
			throw new IllegalArgumentException("Role is null");
		}
		logger.debug("Updating role "+role.getName()+" in database.");
		return roleDao.update(role);
	}
	
	@Override
	public Role getRoleById(Long id) {
		return roleDao.findById(id);
	}

	@Override
	public void deleteRole(Long id) {
		Role role = getRoleById(id);
		if(role != null) {
			role.getMembers().clear();
			role.getPermissions().clear();
			
			logger.debug("Deleting role "+role.getName()+" from database.");
			roleDao.remove(role);
		}
	}

	@Override
	public Role getRoleByName(String name) {
		return roleDao.findRoleByName(name);
	}
	
	public Role getRoleByMessagesKey(String messageKey) {
		List<Role> roles = roleDao.findByAttribute(Role_.messagesKey, messageKey);
		if(roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}

	@Override
	public List<Role> getRoles(SingularAttribute<Role, ?> orderAttribute, boolean orderAsc) {
		return roleDao.findAll(orderAttribute, orderAsc);
	}

	@Override
	public void assignUsersToRole(List<User> userList, Role role) {
		role.setMembers(userList);
		updateRole(role);
	}
	
	private void addUserToRole(User user, Role role) {
		List<User> userList = role.getMembers();
		if(!containsUser(userList, user)) {
			userList.add(user);
		}
		assignUsersToRole(userList, role);
	}
	
	private boolean containsUser(List<User> userList, User newUser) {
		for (User user : userList) {
			if(user.getId() != null && user.getId().equals(newUser.getId())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void assignPermissionsToRole(List<Permission> permissionList, Role role) {
		role.setPermissions(permissionList);
		updateRole(role);
	}
	
	@Override
	public void deleteRoles(List<Long> roleIds) {
		if(roleIds != null) {
			for (Long roleId : roleIds) {
				deleteRole(roleId);
			}
		}
	}

	@Override
	public List<Role> getRolesForUser(Long userId) {
		if(userId == null) {
			throw new IllegalArgumentException("Param userId is null.");
		}
		return roleDao.findRolesForUser(userId);
	}

	@Override
	public List<Role> getRolesForPermission(Long permissionId) {
		return roleDao.findRolesForPermission(permissionId);
	}

	@Override
	public void assignUserToDefaultRole(User user) {
		assignUserToRole(user, RoleService.DEFAULT_ROLE);
	}

	@Override
	public void assignUserToAdminRole(User user) {
		assignUserToRole(user, RoleService.ADMIN_ROLE);
	}
	
	private void assignUserToRole(User user, String roleMessageKey) {
		Role role = getRoleByMessagesKey(roleMessageKey);
		if(role != null) {
			addUserToRole(user, role);
		}
	}

	@Override
	public void removeUserFromAdminRole(User user) {
		Role role = getRoleByMessagesKey(RoleService.ADMIN_ROLE);
		if(role != null) {
			removeUserFromRole(user, role);
		}
	}

	private void removeUserFromRole(User user, Role role) {
		List<User> userList = role.getMembers();
		List<User> removeList = new ArrayList<User>();
		for (User userInList : userList) {
			if(userInList.getId() != null && userInList.getId().equals(user.getId())) {
				removeList.add(userInList);
			}
		}
		userList.removeAll(removeList);
		assignUsersToRole(userList, role);
	}

}
