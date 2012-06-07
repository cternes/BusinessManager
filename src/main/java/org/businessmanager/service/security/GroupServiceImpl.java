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
import org.businessmanager.dao.security.GroupDao;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.Group_;
import org.businessmanager.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private GroupDao groupDao;
	
	@Override
	public List<Group> getGroups() {
		return groupDao.findAll();
	}

	@Override
	public Group addGroup(Group group) {
		if(group == null) {
			throw new IllegalArgumentException("Group is null");
		}
		
		if(groupExistsAlready(group.getName())) {
			logger.warn("Could not create group " + group.getName() + " because there is already a group with the same name. Aborting...");
			throw new IllegalArgumentException("Group with name " + group.getName() + " exists already.");
		}
		else {
			logger.debug("Adding group "+group.getName()+" to database.");
			return groupDao.save(group);
		}
	}

	private boolean groupExistsAlready(String name) {
		Group groupFromDb = getGroupByName(name);
		if(groupFromDb != null) {
			return true;
		}
		return false;
	}

	@Override
	public Group updateGroup(Group group) {
		if(group == null) {
			throw new IllegalArgumentException("Group is null");
		}
		logger.debug("Updating group "+group.getName()+" in database.");
		return groupDao.update(group);
	}
	
	@Override
	public Group getGroupById(Long id) {
		return groupDao.findById(id);
	}

	@Override
	public void deleteGroup(Long id) {
		Group group = getGroupById(id);
		if(group != null) {
			group.getMembers().clear();
			group.getPermissions().clear();
			
			logger.debug("Deleting group "+group.getName()+" from database.");
			groupDao.remove(group);
		}
	}

	@Override
	public Group getGroupByName(String name) {
		return groupDao.findGroupByName(name);
	}
	
	public Group getGroupByMessagesKey(String messageKey) {
		List<Group> groups = groupDao.findByAttribute(Group_.messagesKey, messageKey);
		if(groups.size() > 0) {
			return groups.get(0);
		}
		return null;
	}

	@Override
	public List<Group> getGroups(SingularAttribute<Group, ?> orderAttribute, boolean orderAsc) {
		return groupDao.findAll(orderAttribute, orderAsc);
	}

	@Override
	public void assignUsersToGroup(List<User> userList, Group group) {
		group.setMembers(userList);
		updateGroup(group);
	}
	
	private void addUserToGroup(User user, Group group) {
		List<User> userList = group.getMembers();
		if(!containsUser(userList, user)) {
			userList.add(user);
		}
		assignUsersToGroup(userList, group);
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
	public void assignPermissionsToGroup(List<Permission> permissionList, Group group) {
		group.setPermissions(permissionList);
		updateGroup(group);
	}
	
	@Override
	public void deleteGroups(List<Long> idList) {
		if(idList != null) {
			for (Long id : idList) {
				deleteGroup(id);
			}
		}
	}

	@Override
	public List<Group> getGroupsForUser(Long userId) {
		if(userId == null) {
			throw new IllegalArgumentException("Param userId is null.");
		}
		return groupDao.findGroupsForUser(userId);
	}

	@Override
	public List<Group> getGroupsForPermission(Long permissionId) {
		return groupDao.findGroupsForPermission(permissionId);
	}

	@Override
	public void assignUserToDefaultGroup(User user) {
		assignUserToGroup(user, GroupService.DEFAULT_GROUP);
	}

	@Override
	public void assignUserToAdminGroup(User user) {
		assignUserToGroup(user, GroupService.ADMIN_GROUP);
	}
	
	private void assignUserToGroup(User user, String messageKey) {
		Group group = getGroupByMessagesKey(messageKey);
		if(group != null) {
			addUserToGroup(user, group);
		}
	}

	@Override
	public void removeUserFromAdminGroup(User user) {
		Group group = getGroupByMessagesKey(GroupService.ADMIN_GROUP);
		if(group != null) {
			removeUserFromGroup(user, group);
		}
	}

	private void removeUserFromGroup(User user, Group group) {
		List<User> userList = group.getMembers();
		List<User> removeList = new ArrayList<User>();
		for (User userInList : userList) {
			if(userInList.getId() != null && userInList.getId().equals(user.getId())) {
				removeList.add(userInList);
			}
		}
		userList.removeAll(removeList);
		assignUsersToGroup(userList, group);
	}

}
