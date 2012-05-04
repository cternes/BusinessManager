package org.businessmanager.database.security;

import org.businessmanager.database.GenericDao;
import org.businessmanager.domain.security.User;


/**
 * Manages the database access of the entity User.
 * 
 * @author Christian Ternes
 *
 */
public interface UserDao extends GenericDao<User> {

	public User findUserByName(String username);
	
	public User findUserByEmail(String email);

}
