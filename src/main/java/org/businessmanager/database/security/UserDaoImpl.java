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
package org.businessmanager.database.security;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.businessmanager.database.GenericDaoImpl;
import org.businessmanager.domain.MutationType;
import org.businessmanager.domain.security.QUser;
import org.businessmanager.domain.security.User;
import org.businessmanager.domain.security.User_;
import org.businessmanager.domain.settings.QApplicationSetting;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author Christian Ternes
 *
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	@Override
	public List<User> findAll() {
		return findAll(User_.username, true);
	}
	
	@Override
	public User findUserByName(String username) {
		CriteriaBuilder queryBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> query = queryBuilder.createQuery(User.class);
		Root<User> userQuery = query.from(User.class);
		query.select(userQuery).where(queryBuilder.equal(userQuery.get(User_.username), username));
		
		try {
			return getEntityManager().createQuery(query).getSingleResult();	
		}
		catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public User findUserByEmail(String email) {
		List<User> list = findByAttribute(User_.email, email);
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public Class<User> getPersistenceClass() {
		return User.class;
	}

	@Override
	public List<User> findByUsernameFragment(String usernameFragment) {
		JPAQuery query = new JPAQuery(getEntityManager());
		QUser user = QUser.user;
		
		return query.from(user).where(user.username.like(usernameFragment).and(user.mutationType.ne(MutationType.DELETE))).list(user);
	}
	
}
