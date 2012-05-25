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
import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.User;

/**
 * This service manages roles.
 * 
 * @author Christian Ternes
 *
 */
public interface RoleService {

	public static final String DEFAULT_ROLE = "groupname_all_users";
	public static final String ADMIN_ROLE = "groupname_admin";
	
	/**
	 * Retrieves all {@link Role}s from the database.
	 * 
	 * @return  a list of {@link Role}s
	 */
	public List<Role> getRoles();
	
	/**
	 * Retrieves a list of {@link Role}s from the database and sorts them by the given attribute.
	 * 
	 * @param orderAttribute the sort attribute
	 * @param orderAsc true for Ascending, false for Descending
	 * @return a list of {@link Role}s
	 */
	public List<Role> getRoles(SingularAttribute<Role, ?> orderAttribute, boolean orderAsc);
	
	/**
	 * Adds a new {@link Role} to the database. The name must be unique for all roles.
	 * <p>
	 * If the there is already a role with the same name an {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param role the role which should be added
	 * @return the role with an id set
	 */
	public Role addRole(Role role);
	
	/**
	 * Updates a {@link Role} in the database. 
	 * 
	 * @param role the {@link Role} which should be updated
	 * @return the updated {@link Role}
	 */
	public Role updateRole(Role role);
	
	/**
	 * Deletes a {@link Role} from the database. 
	 * If the role was assigned to one or several {@link User}s the assignment will be removed.
	 * If the role was assigned to one or several {@link Permission}s the assignment will be removed.
	 * 
	 * @param id the role id from the role which should be deleted
	 */
	public void deleteRole(Long id);
	
	/**
	 * Deletes several {@link Role}s from the database.
	 * 
	 * @param roleIdList
	 * @see RoleService#deleteRole(long)
	 */
	public void deleteRoles(List<Long> roleIdList);
	
	/**
	 * Retrieves a {@link Role} by its id from the database.
	 * 
	 * @param id
	 * @return the found {@link Role}
	 */
	public Role getRoleById(Long id);
	
	/**
	 * Retrieves a role by its name from the database.
	 * 
	 * @param name
	 * @return the found {@link Role}
	 */
	public Role getRoleByName(String name);
	
	/**
	 * Assigns a list of users to the given role.
	 * <p>
	 * Every user that was previously assigned to the given role but is not anymore in the given
	 * userList will be removed from the given role.
	 * 
	 * @param userList the list of {@link User}s which should be assigned 
	 * @param role the {@link Role} to which the users should be assigned
	 */
	public void assignUsersToRole(List<User> userList, Role role);

	/**
	 * Assigns a list of permissions to the given {@link Role}.
	 * <p>
	 * Every permission that was previously assigned to the given role but is not anymore in the given
	 * permissionList will be removed from the given role.
	 * 
	 * @param permissionList the list of {@link Permission}s which should be assigned 
	 * @param role the {@link Role} to which the permissions should be assigned
	 */
	public void assignPermissionsToRole(List<Permission> permissionList, Role role);
	
	/**
	 * Retrieves the assigned {@link Role}s for a given {@link User}.
	 * 
	 * @param userId the id of the {@link User}
	 * @return a list of assigned {@link Role}s
	 */
	public List<Role> getRolesForUser(Long userId);
	
	/**
	 * Retrieves the assigned {@link Role}s for a given {@link Permission}.
	 * 
	 * @param permissionId the id of the {@link Permission}
	 * @return a list of assigned {@link Role}s
	 */
	public List<Role> getRolesForPermission(Long permissionId);
	
	/**
	 * Assigns the given {@link User} to the default {@link Role}.
	 * 
	 * @param user the {@link User} which should be assigned
	 */
	public void assignUserToDefaultRole(User user);

	/**
	 * Assigns the given {@link User} to the admin {@link Role}.
	 * 
	 * @param user the {@link User} which should be assigned
	 */
	public void assignUserToAdminRole(User user);

	/**
	 * Removes the given {@link User} from the admin {@link Role}.
	 * 
	 * @param user the {@link User} which should be removed
	 */
	public void removeUserFromAdminRole(User user);
		
	
}
