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
package org.businessmanager.startup.strategy;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.database.GenericDao;
import org.businessmanager.database.security.PermissionDao;
import org.businessmanager.database.security.GroupDao;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.Group_;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Christian Ternes
 *
 */
public class GroupStrategy extends AbstractStorageStrategy<Group> implements EntityStorageStrategy<Group> {

	@Autowired
	private GroupDao dao;

	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public GenericDao<Group> getDao() {
		return dao;
	}

	@Override
	public SingularAttribute<Group, ?> getAttributeKey() {
		return Group_.name;
	}

	@Override
	public Object getAttributeValue(Group source) {
		return source.getName();
	}

	@Override
	public Group updateEntity(Group source, Group target) {
		target.setName(source.getName());
		
		updateMembers(source, target);
		updatePermissions(source, target);
		
		return target;
	}

	private void updatePermissions(Group source, Group target) {
		target.getPermissions().clear();
		List<Permission> permissions = source.getPermissions();
		for (Permission permission : permissions) {
			Permission permissionFromDb = permissionDao.findPermissionByName(permission.getName());
			if(permissionFromDb != null) {
				target.getPermissions().add(permissionFromDb);
			}
		}
	}

	private void updateMembers(Group source, Group target) {
		target.getMembers().clear();
		List<User> members = source.getMembers();
		for (User user : members) {
			User userFromDb = userService.getUserByName(user.getUsername());
			if(userFromDb != null) {
				target.getMembers().add(userFromDb);
			}
		}
	}

	@Override
	public boolean isEntitiesRemovedOnStartup() {
		return false;
	}
	
}
