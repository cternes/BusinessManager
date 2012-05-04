package org.businessmanager.database.security;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.businessmanager.database.GenericDaoImpl;
import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.Role_;
import org.springframework.stereotype.Repository;


/**
 * @author Christian Ternes
 *
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

	@Override
	public List<Role> findAll() {
		return findAll(Role_.name, true);
	}
	
	@Override
	public Role findRoleByName(String name) {
		CriteriaBuilder queryBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Role> query = queryBuilder.createQuery(Role.class);
		Root<Role> roleQuery = query.from(Role.class);
		query.select(roleQuery).where(queryBuilder.equal(roleQuery.get(Role_.name), name));
		
		try {
			return getEntityManager().createQuery(query).getSingleResult();	
		}
		catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Role> findRolesForUser(Long userId) {
		return findByAssignedEntity(Role_.members, userId);
	}

	@Override
	public List<Role> findRolesForPermission(Long permissionId) {
		return findByAssignedEntity(Role_.permissions, permissionId);
	}

	@Override
	public Class<Role> getPersistenceClass() {
		return Role.class;
	}


}
