package org.businessmanager.startup.strategy;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.database.GenericDao;
import org.businessmanager.database.security.PermissionDao;
import org.businessmanager.database.security.RoleDao;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.Role_;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Christian Ternes
 *
 */
public class RoleStrategy extends AbstractStorageStrategy<Role> implements EntityStorageStrategy<Role> {

	@Autowired
	private RoleDao dao;

	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public GenericDao<Role> getDao() {
		return dao;
	}

	@Override
	public SingularAttribute<Role, ?> getAttributeKey() {
		return Role_.name;
	}

	@Override
	public Object getAttributeValue(Role source) {
		return source.getName();
	}

	@Override
	public Role updateEntity(Role source, Role target) {
		target.setName(source.getName());
		
		updateMembers(source, target);
		updatePermissions(source, target);
		
		return target;
	}

	private void updatePermissions(Role source, Role target) {
		target.getPermissions().clear();
		List<Permission> permissions = source.getPermissions();
		for (Permission permission : permissions) {
			Permission permissionFromDb = permissionDao.findPermissionByName(permission.getName());
			if(permissionFromDb != null) {
				target.getPermissions().add(permissionFromDb);
			}
		}
	}

	private void updateMembers(Role source, Role target) {
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
		return true;
	}
	
}
