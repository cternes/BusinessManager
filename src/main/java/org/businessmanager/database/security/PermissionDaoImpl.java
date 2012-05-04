package org.businessmanager.database.security;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.businessmanager.database.GenericDaoImpl;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Permission_;
import org.springframework.stereotype.Repository;

/**
 * @author Christian Ternes
 *
 */
@Repository
public class PermissionDaoImpl extends GenericDaoImpl<Permission> implements PermissionDao {

	@Override
	public List<Permission> findAll() {
		return findAll(Permission_.name, true);
	}
	
	@Override
	public Permission findPermissionByName(String name) {
		CriteriaBuilder queryBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Permission> query = queryBuilder.createQuery(Permission.class);
		Root<Permission> permissionQuery = query.from(Permission.class);
		query.select(permissionQuery).where(queryBuilder.equal(permissionQuery.get(Permission_.name), name));
		
		try {
			return getEntityManager().createQuery(query).setMaxResults(1).getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public Class<Permission> getPersistenceClass() {
		return Permission.class;
	}

}
