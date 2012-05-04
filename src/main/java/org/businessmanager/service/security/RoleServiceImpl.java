package org.businessmanager.service.security;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.businessmanager.database.security.RoleDao;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.User;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Christian Ternes
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private RoleDao roleDao;
	
	@Transactional(readOnly=true)
	@Profiled
	@Override
	public List<Role> getRoles() {
		return roleDao.findAll();
	}

	@Transactional
	@Override
	public Role addRole(Role role) {
		if(role == null) {
			throw new IllegalArgumentException("Role is null");
		}
		
		if(roleExistsAlready(role.getName())) {
			logger.warn("Could not create role " + role.getName() + " because there is already a role with the same name. Aborting...");
			throw new IllegalArgumentException("Role with name " + role.getName() + " exists already.");
		}
		else {
			logger.debug("Adding role "+role.getName()+" to database.");
			return roleDao.save(role);
		}
	}

	private boolean roleExistsAlready(String name) {
		Role roleFromDb = getRoleByName(name);
		if(roleFromDb != null) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public Role updateRole(Role role) {
		if(role == null) {
			throw new IllegalArgumentException("Role is null");
		}
		logger.debug("Updating role "+role.getName()+" in database.");
		return roleDao.update(role);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Role getRoleById(long id) {
		return roleDao.findById(id);
	}

	@Transactional
	@Override
	public void deleteRole(long id) {
		Role role = getRoleById(id);
		if(role != null) {
			role.getMembers().clear();
			role.getPermissions().clear();
			
			logger.debug("Deleting role "+role.getName()+" from database.");
			roleDao.remove(role);
		}
	}

	@Transactional(readOnly=true)
	@Override
	public Role getRoleByName(String name) {
		return roleDao.findRoleByName(name);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Role> getRoles(SingularAttribute<Role, ?> orderAttribute, boolean orderAsc) {
		return roleDao.findAll(orderAttribute, orderAsc);
	}

	@Transactional
	@Override
	public void assignUsersToRole(List<User> userList, Role role) {
		role.setMembers(userList);
		updateRole(role);
	}
	
	@Transactional
	@Override
	public void assignPermissionsToRole(List<Permission> permissionList, Role role) {
		role.setPermissions(permissionList);
		updateRole(role);
	}
	
	@Transactional
	@Override
	public void deleteRoles(List<Long> roleIds) {
		if(roleIds != null) {
			for (Long roleId : roleIds) {
				deleteRole(roleId);
			}
		}
	}

	@Override
	public List<Role> getRolesForUser(Long userId) {
		if(userId == null) {
			throw new IllegalArgumentException("Param userId is null.");
		}
		return roleDao.findRolesForUser(userId);
	}

	@Override
	public List<Role> getRolesForPermission(Long permissionId) {
		return roleDao.findRolesForPermission(permissionId);
	}

}
