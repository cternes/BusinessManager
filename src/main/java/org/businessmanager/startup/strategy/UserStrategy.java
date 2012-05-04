package org.businessmanager.startup.strategy;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.database.GenericDao;
import org.businessmanager.database.security.UserDao;
import org.businessmanager.domain.security.User;
import org.businessmanager.domain.security.User_;
import org.businessmanager.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Christian Ternes
 *
 */
public class UserStrategy extends AbstractStorageStrategy<User> implements EntityStorageStrategy<User> {

	@Autowired
	private UserDao dao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public GenericDao<User> getDao() {
		return dao;
	}
	
	/**
	 * We have to override this method here, because we don't want to use the DAO to manage user objects.
	 * We want to use the {@link UserService} here, to handle the passwords correctly. 
	 */
	@Override
	protected void createOrUpdateObjects(List<User> userList) {
		for (User entity : userList) {
			List<User> result = getDao().findByAttribute(getAttributeKey(), getAttributeValue(entity));
			if(result.size() == 1) {
				User updatedEntity = updateEntity(entity, result.get(0));
				
				userService.updateUser(updatedEntity, true);
				doPostProcessing(updatedEntity);
			}
			else {
				userService.addUser(entity);
				doPostProcessing(entity);
			}
		}
	}

	@Override
	public SingularAttribute<User, ?> getAttributeKey() {
		return User_.username;
	}

	@Override
	public Object getAttributeValue(User source) {
		return source.getUsername();
	}

	@Override
	public User updateEntity(User source, User target) {
		target.setEmail(source.getEmail());
		target.setEnabled(source.getEnabled());
		target.setPassword(source.getPassword());
		target.setUsername(source.getUsername());
		
		return target;
	}


	@Override
	public boolean isEntitiesRemovedOnStartup() {
		return true;
	}

}
