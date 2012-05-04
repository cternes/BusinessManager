package org.businessmanager.service.security;

import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.domain.security.User;

/**
 * This service manages users.
 * 
 * @author Christian Ternes
 *
 */
public interface UserService {

	/**
	 * Retrieves all {@link User}s from the database.
	 * 
	 * @return a list of {@link User}s
	 */
	public List<User> getUsers();
	
	/**
	 * Retrieves all {@link User}s from the database and sorts them by the given orderAttribute.
	 *  
	 * @param orderAttribute the primary ordering attribute
	 * @param orderAsc determines if ordering should be ascending or descending
	 * @return a list of {@link User}s
	 */
	public List<User> getUsers(SingularAttribute<User, ?> orderAttribute, boolean orderAsc);
	
	/**
	 * Retrieves a filtered list of {@link User}s from the database.
	 * Can be used to fetch users in pages from the database and/or retrieve only
	 * users that matches the given filters. 
	 * 
	 * @param orderAttribute the sort attribute
	 * @param orderAsc true for Ascending, false for Descending
	 * @param firstResult the first result which should be retrieved, -1 to ignore
	 * @param maxResults the number of resuls which should be retrieved, -1 to ignore
	 * @param filters a key/value map of {@link SingularAttribute}s which must match 
	 * @param enableLikeSearch true enables like search of all attributes, false enables exact matches
	 * @return a list of {@link User}s that matches the given filters
	 */
	public List<User> getUsers(SingularAttribute<User, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults, Map<SingularAttribute<User, ?>, Object> filters, boolean enableLikeSearch);
	
	/**
	 * Adds a new {@link User} to the database. The username & email must be unique for all users.
	 * The password of the given user will be hashed with MD5 and replaces the original password field.
	 * <p>
	 * <b>Beware:</b> After this method is executed you cannot retrieve the original password in clear text,
	 * neither in the {@link User} class nor in the database.  
	 * <p>
	 * If the there is already a user with the same username an {@link DuplicateUserException} will be thrown.
	 * <p>
	 * If the there is already a user with the same email an {@link DuplicateEmailException} will be thrown.
	 * 
	 * @param user the {@link User} which should be added
	 * @return the {@link User} with an id set
	 */
	public User addUser(User user);
	
	/**
	 * Retrieves the {@link User} for the given username from the database. If the user does not exist, null is returned. 
	 * 
	 * @param username the username to search for
	 * @return the {@link User} if found or null if not found
	 */
	public User getUserByName(String username);
	
	/**
	 * Retrieves the {@link User} for the given email address from the database. If the user does not exist, null is returned. 
	 * 
	 * @param email the email to search for
	 * @return the {@link User} if found or null if not found
	 */
	public User getUserByEmail(String email);
	
	/**
	 * Updates the properties of an existing {@link User} in the database.
	 * 
	 * @param user the {@link User} to update
	 */
	public User updateUser(User user, boolean updatePassword);
	
	/**
	 * Deletes a {@link User} from the database.
	 * 
	 * @param userId the {@link User} with this userId should be deleted
	 */
	public void deleteUser(Long userId);
	
	/**
	 * Deletes a list of {@link User}s from the database.
	 * 
	 * @param userIdList a list of {@link User} ids which should be deleted
	 */
	public void deleteUsers(List<Long> userIdList);
	
	/**
	 * Retrieves a single {@link User} from the database by its id.
	 * 
	 * @param userId the id which should be searched
	 * @return a single user
	 */
	public User getUserById(Long userId);
	
	/**
	 * Retrieves the number of {@link User}s in the database.
	 * 
	 * @return the number of users
	 */
	public int getNumberOfUsers();
	
	/**
	 * Retrieves the number of {@link User}s in the database that matches the given
	 * filter attributes.
	 * 
	 * @param filterAttributes a key/value map of {@link SingularAttribute}s to filter the found users
	 * @param enableLikeSearch true enables like search, false enables exact match
	 * @return the number of users that matches the given filter attributes
	 */
	public int getNumberOfUsers(Map<SingularAttribute<User, ?>, Object> filterAttributes, boolean enableLikeSearch);
	
}
