package org.businessmanager.database.security;

import java.util.List;

import org.businessmanager.database.GenericDao;
import org.businessmanager.domain.security.Role;

/**
 * Manages the database access of the entity Role
 * 
 * @author Christian Ternes
 *
 */
public interface RoleDao extends GenericDao<Role> {

	public Role findRoleByName(String name);
	
	public List<Role> findRolesForUser(Long userId);
	
	public List<Role> findRolesForPermission(Long permissionId);
}
