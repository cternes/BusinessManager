/*******************************************************************************
 * Copyright 2012 Christian Ternes and Thorsten Volland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.businessmanager.dao.security;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.businessmanager.dao.GenericDaoImpl;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Permission_;
import org.springframework.stereotype.Repository;

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
