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
package org.businessmanager.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.database.em.FilteredEntityManager;
import org.businessmanager.domain.AbstractEntity;
import org.businessmanager.domain.MutationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * Generic DAO-Implementation to support standard DAO methods (CRUD).
 *
 * @author Christian Ternes
 * @param <T> an entity class
 */
public abstract class GenericDaoImpl<T extends AbstractEntity> implements GenericDao<T>{

	@Autowired
	@Qualifier("filteredEntityManager")
	private EntityManager entityManager;
	
	@Autowired
	@Qualifier("TransactionManager")
	private PlatformTransactionManager txManager;
	
	public abstract Class<T> getPersistenceClass();
	
	@Override
	public T save(T domainObject) {
		entityManager.persist(domainObject);
		return domainObject;
	}
	
	@Override
	public T update(T domainObject) {
		entityManager.merge(domainObject);
		return domainObject;
	}
	
	@Override
	public void remove(T domainObject) {
		entityManager.merge(domainObject);
	}
	
	@Override
	public void removePhysical(T domainObject) {
		T entity = entityManager.merge(domainObject);
		entityManager.remove(entity);
	}
	
	@Override
	public List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc)  {
		return findAll(orderAttribute, orderAsc, -1, -1);
	}
	
	@Override
	public List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults) {
		return findAll(orderAttribute, orderAsc, firstResult, maxResults, null);
	}
	
	@Override
	public List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults, Map<SingularAttribute<T, ?> , Object> filterAttributes) {
		return findAll(orderAttribute, orderAsc, firstResult, maxResults, filterAttributes, false);
	}
	
	@Override
	public List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults, Map<SingularAttribute<T, ?> , Object> filterAttributes, boolean enableLikeSearch) {
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = queryBuilder.createQuery(getPersistenceClass());
		Root<T> rootQuery = criteriaQuery.from(getPersistenceClass());
		
		CriteriaQuery<T> select = criteriaQuery.select(rootQuery);
		
		List<Predicate> predicateList = createFilterList(filterAttributes, enableLikeSearch, queryBuilder, rootQuery);
		select.where(predicateList.toArray(new Predicate[0]));
		
		if(orderAsc) {
			criteriaQuery.orderBy(queryBuilder.asc(rootQuery.get(orderAttribute)));
		}
		else {
			criteriaQuery.orderBy(queryBuilder.desc(rootQuery.get(orderAttribute)));
		}
		
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		
		if(firstResult != -1) {
			typedQuery.setFirstResult(firstResult);
		}
		if(maxResults != -1) {
			typedQuery.setMaxResults(maxResults);
		}
		
		return typedQuery.getResultList();
	}

	@Override
	public T findById(Long id) {
		if(id == null) {
			return null;
		}
		
		//filter out deleted entities
		T entityFromDb = entityManager.find(getPersistenceClass(), id);
		if(entityFromDb instanceof AbstractEntity) {
			if(entityFromDb.getMutationType().equals(MutationType.DELETE)) {
				return null;
			}
		}
		
		return entityFromDb; 
	}
	
	@Override
	public List<T> findByAttribute(SingularAttribute<T, ?> key, Object value) {
		Map<SingularAttribute<T, ?>, Object> anAttributeMap = new HashMap<SingularAttribute<T, ?>, Object>();
		anAttributeMap.put(key, value);
		
		return findByAttributes(anAttributeMap);
	}
	
	@Override
	public List<T> findByAttributes(Map<SingularAttribute<T, ?>, Object> theAttributes) {
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = queryBuilder.createQuery(getPersistenceClass());
		Root<T> rootQuery = criteriaQuery.from(getPersistenceClass());
		CriteriaQuery<T> select = criteriaQuery.select(rootQuery);
		
		List<Predicate> aPredicateList = new ArrayList<Predicate>();
		Iterator<SingularAttribute<T, ?>> anIterator = theAttributes.keySet().iterator();
		while(anIterator.hasNext()) {
			SingularAttribute<T, ?> aKey = anIterator.next();
			Object aValue = theAttributes.get(aKey);
			Predicate aPredicate = queryBuilder.equal(rootQuery.get(aKey), aValue);
			aPredicateList.add(aPredicate);
		}
		
		select.where(aPredicateList.toArray(new Predicate[0]));
		TypedQuery<T> typedQuery = entityManager.createQuery(select);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public Long getCount() {
		return getCount(null, false);
	}
	
	@Override
	public Long getCount(Map<SingularAttribute<T, ?>, Object> filterAttributes, boolean enableLikeSearch) {
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = queryBuilder.createQuery(Long.class);
		Root<T> rootQuery = criteriaQuery.from(getPersistenceClass());
		CriteriaQuery<Long> select = criteriaQuery.select(queryBuilder.count(rootQuery));
		
		List<Predicate> predicateList = createFilterList(filterAttributes, enableLikeSearch, queryBuilder, rootQuery);
		criteriaQuery.where(predicateList.toArray(new Predicate[0]));
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(select);
		return typedQuery.getSingleResult();
	}
	
	private List<Predicate> createFilterList(Map<SingularAttribute<T, ?>, Object> filterAttributes, boolean enableLikeSearch,
			CriteriaBuilder queryBuilder, Root<T> rootQuery) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if(filterAttributes != null) {
			Iterator<SingularAttribute<T, ?>> iter = filterAttributes.keySet().iterator();
			while(iter.hasNext()) {
				SingularAttribute<T, ?> key = iter.next();
				Object value = filterAttributes.get(key);
				if(enableLikeSearch) {
					String searchKey = value.toString();
					if(!value.toString().contains("%")) {
						searchKey = "%"+value.toString()+"%";
					}
					
					Expression<String> lowerKey = queryBuilder.lower((Expression<String>) rootQuery.get(key));
					Predicate predicate = queryBuilder.like(lowerKey, searchKey.toLowerCase());
					predicateList.add(predicate);
				}
				else {
					Predicate predicate = queryBuilder.equal(rootQuery.get(key), value);
					predicateList.add(predicate);
				}
			}
		}
		return predicateList;
	}
	
	@Override
	public TransactionStatus getTransaction() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("CustomTransaction");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		return txManager.getTransaction(def);
	}
	
	@Override
	public void commit(TransactionStatus status) {
		txManager.commit(status);
	}
	
	@Override
	public void rollback(TransactionStatus status) {
		txManager.rollback(status);
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected FilteredEntityManager getFilteredEntityManager() {
		if(entityManager instanceof FilteredEntityManager) {
			return (FilteredEntityManager) entityManager;
		}
		else {
			throw new IllegalStateException("Current entity manager is not filtered.");
		}
	}
	
	/**
	 * Allow subclasses to inject their own entity manager.
	 * This is useful when multiple datasources/entity managers will be used.
	 * 
	 * @param em the entity manager
	 */
	protected void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
	
	public List<T> findByAssignedEntity(ListAttribute<T, ?> listAttribute, Long entityId) {
		CriteriaBuilder queryBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = queryBuilder.createQuery(getPersistenceClass());
		Root<T> rootQuery = criteriaQuery.from(getPersistenceClass());
		
		CriteriaQuery<T> select = criteriaQuery.select(rootQuery);
		Join memberJoin = rootQuery.join(listAttribute);
		
		Path nameField = memberJoin.get("id");
		Predicate nameEquals = queryBuilder.equal(nameField, entityId);
		criteriaQuery.where(nameEquals);
		
		TypedQuery<T> typedQuery = getEntityManager().createQuery(select);
		
		return typedQuery.getResultList();
	}
	
	public List<T> findByAssignedEntity(SetAttribute<T, ?> setAttribute, Long entityId) {
		CriteriaBuilder queryBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = queryBuilder.createQuery(getPersistenceClass());
		Root<T> rootQuery = criteriaQuery.from(getPersistenceClass());
		
		CriteriaQuery<T> select = criteriaQuery.select(rootQuery);
		Join memberJoin = rootQuery.join(setAttribute);
		
		Path nameField = memberJoin.get("id");
		Predicate nameEquals = queryBuilder.equal(nameField, entityId);
		criteriaQuery.where(nameEquals);
		
		TypedQuery<T> typedQuery = getEntityManager().createQuery(select);
		
		return typedQuery.getResultList();
	}
	
}
