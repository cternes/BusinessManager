package org.businessmanager.database.security;

import org.businessmanager.database.GenericDao;
import org.businessmanager.domain.security.Permission;

/**
 * Manages the database access of the entity Permission.
 * 
 * @author Christian Ternes
 *
 */
public interface PermissionDao extends GenericDao<Permission> {

	public Permission findPermissionByName(String theName);
}
