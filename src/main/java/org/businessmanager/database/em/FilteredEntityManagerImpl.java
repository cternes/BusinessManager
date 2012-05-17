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
package org.businessmanager.database.em;

import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import org.businessmanager.domain.AbstractEntity_;
import org.businessmanager.domain.MutationType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


/**
 * This {@link EntityManager} is a special one that retrieves only entities that are not flagged as deleted 
 * in the database.
 * 
 * This {@link EntityManager} can only manage {@link AbstractEntity}s.
 * 
 * @author Christian Ternes
 *
 */
@Qualifier("filteredEntityManager")
@Repository
public class FilteredEntityManagerImpl implements FilteredEntityManager {

	@PersistenceContext(unitName="EntityManagerFactory")
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#persist(java.lang.Object)
	 */
	@Override
	public void persist(Object theEntity) {
		entityManager.persist(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#merge(java.lang.Object)
	 */
	@Override
	public <T> T merge(T theEntity) {
		return entityManager.merge(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object theEntity) {
		entityManager.remove(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T find(Class<T> theEntityClass, Object thePrimaryKey) {
		return entityManager.find(theEntityClass, thePrimaryKey);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> T find(Class<T> theEntityClass, Object thePrimaryKey, Map<String, Object> theProperties) {
		return entityManager.find(theEntityClass, thePrimaryKey, theProperties);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public <T> T find(Class<T> theEntityClass, Object thePrimaryKey, LockModeType theLockMode) {
		return entityManager.find(theEntityClass, thePrimaryKey, theLockMode);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T> T find(Class<T> theEntityClass, Object thePrimaryKey,
			LockModeType theLockMode, Map<String, Object> theProperties) {
		return entityManager.find(theEntityClass, thePrimaryKey, theLockMode, theProperties);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T getReference(Class<T> theEntityClass, Object thePrimaryKey) {
		return entityManager.getReference(theEntityClass, thePrimaryKey);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#flush()
	 */
	@Override
	public void flush() {
		entityManager.flush();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.FlushModeType)
	 */
	@Override
	public void setFlushMode(FlushModeType theFlushMode) {
		entityManager.setFlushMode(theFlushMode);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getFlushMode()
	 */
	@Override
	public FlushModeType getFlushMode() {
		return entityManager.getFlushMode();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public void lock(Object theEntity, LockModeType theLockMode) {
		entityManager.lock(theEntity, theLockMode);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public void lock(Object theEntity, LockModeType theLockMode,
			Map<String, Object> theProperties) {
		entityManager.lock(theEntity, theLockMode, theProperties);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object theEntity) {
		entityManager.refresh(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object, java.util.Map)
	 */
	@Override
	public void refresh(Object theEntity, Map<String, Object> theProperties) {
		entityManager.refresh(theEntity, theProperties);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public void refresh(Object theEntity, LockModeType theLockMode) {
		entityManager.refresh(theEntity, theLockMode);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public void refresh(Object theEntity, LockModeType theLockMode,
			Map<String, Object> theProperties) {
		entityManager.refresh(theEntity, theLockMode, theProperties);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#clear()
	 */
	@Override
	public void clear() {
		entityManager.clear();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#detach(java.lang.Object)
	 */
	@Override
	public void detach(Object theEntity) {
		entityManager.detach(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object theEntity) {
		return entityManager.contains(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getLockMode(java.lang.Object)
	 */
	@Override
	public LockModeType getLockMode(Object theEntity) {
		return entityManager.getLockMode(theEntity);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String thePropertyName, Object theValue) {
		entityManager.setProperty(thePropertyName, theValue);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return entityManager.getProperties();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String)
	 */
	@Override
	public Query createQuery(String theQlString) {
		return entityManager.createQuery(theQlString);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createQuery(javax.persistence.criteria.CriteriaQuery)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> theCriteriaQuery) {
		appendIsNotDeletedClause(theCriteriaQuery);
		
		return entityManager.createQuery(theCriteriaQuery);
	}
	
	/* (non-Javadoc)
	 * @see org.businessmanager.database.em.FilteredEntityManager#createQuery(javax.persistence.criteria.CriteriaQuery, boolean)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> theCriteriaQuery,	boolean theDisableFiltering) {
		if(theDisableFiltering) {
			return entityManager.createQuery(theCriteriaQuery);
		}
		return createQuery(theCriteriaQuery);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(String theQlString,
			Class<T> theResultClass) {
		return entityManager.createQuery(theQlString, theResultClass);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
	 */
	@Override
	public Query createNamedQuery(String theName) {
		return entityManager.createNamedQuery(theName);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> TypedQuery<T> createNamedQuery(String theName,
			Class<T> theResultClass) {
		return entityManager.createNamedQuery(theName, theResultClass);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String theSqlString) {
//		return entityManager.createNativeQuery(theSqlString);
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public Query createNativeQuery(String theSqlString, Class theResultClass) {
		return entityManager.createNativeQuery(theSqlString, theResultClass);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String theSqlString,
			String theResultSetMapping) {
		return entityManager.createNativeQuery(theSqlString, theResultSetMapping);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#joinTransaction()
	 */
	@Override
	public void joinTransaction() {
		entityManager.joinTransaction();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> theCls) {
		return entityManager.unwrap(theCls);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getDelegate()
	 */
	@Override
	public Object getDelegate() {
		return entityManager.getDelegate();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#close()
	 */
	@Override
	public void close() {
		entityManager.close();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return entityManager.isOpen();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getTransaction()
	 */
	@Override
	public EntityTransaction getTransaction() {
		return entityManager.getTransaction();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getEntityManagerFactory()
	 */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManager.getEntityManagerFactory();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getMetamodel()
	 */
	@Override
	public Metamodel getMetamodel() {
		return entityManager.getMetamodel();
	}

	/**
	 * @param <T>
	 * @param theCriteriaQuery
	 */
	private <T> void appendIsNotDeletedClause(CriteriaQuery<T> theCriteriaQuery) {
		Root<T> aRootQuery = null;
		Path<Object> aPath = null;
		Iterator<Root<?>> anIterator = theCriteriaQuery.getRoots().iterator();
		if(anIterator.hasNext()) {
			aRootQuery = (Root<T>) anIterator.next();
			aPath = aRootQuery.get(AbstractEntity_.mutationType.getName());
		}
		Predicate aPredicate = getCriteriaBuilder().notEqual(aPath, MutationType.DELETE);
		theCriteriaQuery.where(theCriteriaQuery.getRestriction(), aPredicate);
	}

}
