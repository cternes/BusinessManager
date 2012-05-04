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

	/**
	 * Retrieves all {@link Role}s from the database.
	 * 
	 * @return  a list of {@link Role}s
	 */
	public List<Role> getRoles();
	
	/**
	 * Retrieves a list of {@link Role}s from the database and sorts them by the given attribute.
	 * 
	 * @param theOrderAttribute the sort attribute
	 * @param theOrderAsc true for Ascending, false for Descending
	 * @return a list of {@link Role}s
	 */
	public List<Role> getRoles(SingularAttribute<Role, ?> theOrderAttribute, boolean theOrderAsc);
	
	/**
	 * Adds a new {@link Role} to the database. The name must be unique for all roles.
	 * <p>
	 * If the there is already a role with the same name an {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param theRole the role which should be added
	 * @return the role with an id set
	 */
	public Role addRole(Role theRole);
	
	/**
	 * Updates a {@link Role} in the database. 
	 * 
	 * @param theRole the {@link Role} which should be updated
	 * @return the updated {@link Role}
	 */
	public Role updateRole(Role theRole);
	
	/**
	 * Deletes a {@link Role} from the database. 
	 * If the role was assigned to one or several {@link User}s the assignment will be removed.
	 * If the role was assigned to one or several {@link Permission}s the assignment will be removed.
	 * 
	 * @param theId the role id from the role which should be deleted
	 */
	public void deleteRole(long theId);
	
	/**
	 * Deletes several {@link Role}s from the database.
	 * 
	 * @param theRoleIdList
	 * @see IRoleService#deleteRole(long)
	 */
	public void deleteRoles(List<Long> theRoleIdList);
	
	/**
	 * Retrieves a {@link Role} by its id from the database.
	 * 
	 * @param theId
	 * @return the found {@link Role}
	 */
	public Role getRoleById(long theId);
	
	/**
	 * Retrieves a role by its name from the database.
	 * 
	 * @param theName
	 * @return the found {@link Role}
	 */
	public Role getRoleByName(String theName);
	
	/**
	 * Assigns a list of users to the given role.
	 * <p>
	 * Every user that was previously assigned to the given role but is not anymore in the given
	 * userList will be removed from the given role.
	 * 
	 * @param theUserlist the list of {@link User}s which should be assigned 
	 * @param theRole the {@link Role} to which the users should be assigned
	 */
	public void assignUsersToRole(List<User> theUserlist, Role theRole);

	/**
	 * Assigns a list of permissions to the given {@link Role}.
	 * <p>
	 * Every permission that was previously assigned to the given role but is not anymore in the given
	 * permissionList will be removed from the given role.
	 * 
	 * @param thePermissionList the list of {@link Permission}s which should be assigned 
	 * @param theRole the {@link Role} to which the permissions should be assigned
	 */
	public void assignPermissionsToRole(List<Permission> thePermissionList, Role theRole);
	
	/**
	 * Retrieves the assigned {@link Role}s for a given {@link User}.
	 * 
	 * @param theUserId the id of the {@link User}
	 * @return a list of assigned {@link Role}s
	 */
	public List<Role> getRolesForUser(Long theUserId);
	
	/**
	 * Retrieves the assigned {@link Role}s for a given {@link Permission}.
	 * 
	 * @param thePermissionId the id of the {@link Permission}
	 * @return a list of assigned {@link Role}s
	 */
	public List<Role> getRolesForPermission(Long thePermissionId);
		
	
}
