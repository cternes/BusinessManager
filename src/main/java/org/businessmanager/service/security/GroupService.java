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

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.User;

/**
 * This service manages groups.
 * 
 * @author Christian Ternes
 *
 */
public interface GroupService {

	public static final String DEFAULT_GROUP = "groupname_all_users";
	public static final String ADMIN_GROUP = "groupname_admin";
	
	/**
	 * Retrieves all {@link Group}s from the database.
	 * 
	 * @return  a list of {@link Group}s
	 */
	public List<Group> getGroups();
	
	/**
	 * Retrieves a list of {@link Group}s from the database and sorts them by the given attribute.
	 * 
	 * @param orderAttribute the sort attribute
	 * @param orderAsc true for Ascending, false for Descending
	 * @return a list of {@link Group}s
	 */
	public List<Group> getGroups(SingularAttribute<Group, ?> orderAttribute, boolean orderAsc);
	
	/**
	 * Adds a new {@link Group} to the database. The name must be unique for all {@link Group}s.
	 * <p>
	 * If the there is already a {@link Group} with the same name an {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param group the {@link Group} which should be added
	 * @return the {@link Group} with an id set
	 */
	public Group addGroup(Group group);
	
	/**
	 * Updates a {@link Group} in the database. 
	 * 
	 * @param group the {@link Group} which should be updated
	 * @return the updated {@link Group}
	 */
	public Group updateGroup(Group group);
	
	/**
	 * Deletes a {@link Group} from the database. 
	 * If the group was assigned to one or several {@link User}s the assignment will be removed.
	 * If the group was assigned to one or several {@link Permission}s the assignment will be removed.
	 * 
	 * @param id the id from the {@link Group} which should be deleted
	 */
	public void deleteGroup(Long id);
	
	/**
	 * Deletes several {@link Group}s from the database.
	 * 
	 * @param idList
	 * @see GroupService#deleteGroup(long)
	 */
	public void deleteGroups(List<Long> idList);
	
	/**
	 * Retrieves a {@link Group} by its id from the database.
	 * 
	 * @param id
	 * @return the found {@link Group}
	 */
	public Group getGroupById(Long id);
	
	/**
	 * Retrieves a group by its name from the database.
	 * 
	 * @param name
	 * @return the found {@link Group}
	 */
	public Group getGroupByName(String name);
	
	/**
	 * Assigns a list of users to the given {@link Group}.
	 * <p>
	 * Every user that was previously assigned to the given {@link Group} but is not anymore in the given
	 * userList will be removed from the given {@link Group}.
	 * 
	 * @param userList the list of {@link User}s which should be assigned 
	 * @param group the {@link Group} to which the users should be assigned
	 */
	public void assignUsersToGroup(List<User> userList, Group group);

	/**
	 * Assigns a list of permissions to the given {@link Group}.
	 * <p>
	 * Every permission that was previously assigned to the given group but is not anymore in the given
	 * permissionList will be removed from the given group.
	 * 
	 * @param permissionList the list of {@link Permission}s which should be assigned 
	 * @param group the {@link Group} to which the permissions should be assigned
	 */
	public void assignPermissionsToGroup(List<Permission> permissionList, Group group);
	
	/**
	 * Retrieves the assigned {@link Group}s for a given {@link User}.
	 * 
	 * @param userId the id of the {@link User}
	 * @return a list of assigned {@link Group}s
	 */
	public List<Group> getGroupsForUser(Long userId);
	
	/**
	 * Retrieves the assigned {@link Group}s for a given {@link Permission}.
	 * 
	 * @param permissionId the id of the {@link Permission}
	 * @return a list of assigned {@link Group}s
	 */
	public List<Group> getGroupsForPermission(Long permissionId);
	
	/**
	 * Assigns the given {@link User} to the default {@link Group}.
	 * 
	 * @param user the {@link User} which should be assigned
	 */
	public void assignUserToDefaultGroup(User user);

	/**
	 * Assigns the given {@link User} to the admin {@link Group}.
	 * 
	 * @param user the {@link User} which should be assigned
	 */
	public void assignUserToAdminGroup(User user);

	/**
	 * Removes the given {@link User} from the admin {@link Group}.
	 * 
	 * @param user the {@link User} which should be removed
	 */
	public void removeUserFromAdminGroup(User user);
		
	
}
