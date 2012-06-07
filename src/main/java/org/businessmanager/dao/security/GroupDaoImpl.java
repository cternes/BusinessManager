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
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.Group_;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDaoImpl extends GenericDaoImpl<Group> implements GroupDao {

	@Override
	public List<Group> findAll() {
		return findAll(Group_.name, true);
	}
	
	@Override
	public Group findGroupByName(String name) {
		CriteriaBuilder queryBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Group> query = queryBuilder.createQuery(Group.class);
		Root<Group> groupQuery = query.from(Group.class);
		query.select(groupQuery).where(queryBuilder.equal(groupQuery.get(Group_.name), name));
		
		try {
			return getEntityManager().createQuery(query).getSingleResult();	
		}
		catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Group> findGroupsForUser(Long userId) {
		return findByAssignedEntity(Group_.members, userId);
	}

	@Override
	public List<Group> findGroupsForPermission(Long permissionId) {
		return findByAssignedEntity(Group_.permissions, permissionId);
	}

	@Override
	public Class<Group> getPersistenceClass() {
		return Group.class;
	}


}
