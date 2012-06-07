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
package org.businessmanager.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.TransactionStatus;

/**
 *
 * Generic DAO-Interface to support standard DAO methods (CRUD).
 *
 * @author Christian Ternes
 * 
 * @param <T> the entity class
 */
public interface GenericDao<T> {

	/**
	 * Stores an entity into the database.
	 * 
	 * @param domainObject the entity which should be stored
	 * @return the entity with an identifier from the database
	 */
	T save(T domainObject);
	
	/**
	 * Updates an existing entity in the database.
	 * 
	 * @param domainObject the entity which should be updated 
	 * @return the entity with an identifier from the database
	 */
	T update(T domainObject);
	
	/**
	 * Marks an existing entity as deleted in the database.
	 * 
	 * @param domainObject
	 */
	void remove(T domainObject);
	
	/**
	 * Deletes an existing entity from the database.
	 * 
	 * @param domainObject
	 */
	void removePhysical(T domainObject);
	
	/**
	 * Finds all entites in the database.
	 * 
	 * @return a list of entities from the database
	 */
	List<T> findAll();
	
	/**
	 * Finds all entites in the database and sort them by the given attribute. 
	 * 
	 * @param orderAttribute the sort attribute 
	 * @param orderAsc true will sort the entities ascending by the given attribute, false will sort descending
	 * @return a list of sorted entities from the database 
	 */
	List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc);
	
	List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults);
	
	List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults, Map<SingularAttribute<T, ?> , Object> filterAttributes);
	
	List<T> findAll(SingularAttribute<T, ?> orderAttribute, boolean orderAsc, int firstResult, int maxResults, Map<SingularAttribute<T, ?> , Object> filterAttributes, boolean enableLikeSearch);
	
	/**
	 * Finds all entities that match the given attribute. The attribute must match exactly.
	 * 
	 * @param key the attribute name which should match
	 * @param value the attribute value which should match
	 * @return a list of entities from the database that match the given attribute
	 */
	List<T> findByAttribute(SingularAttribute<T, ?> key, Object value);
	
	/**
	 * Finds all entities that match the given attributes. The attributes must match exactly.
	 * 
	 * @param theAttributes an attribute map
	 * @return a list of entities from the database that match the given attribute
	 */
	List<T> findByAttributes(Map<SingularAttribute<T, ?>, Object> theAttributes);
	
	/**
	 * Finds one entity in the database with the given identifier.
	 * 
	 * @param id the identifier of the entity which should be found
	 * @return an entity with the given identifier
	 */
	T findById(Long id);
	
	/**
	 * Finds the number of entities in the database.
	 * 
	 * @return the number of entities in the database.
	 */
	Long getCount();
	
	/**
	 * Finds the number of entities in the database that match the given filter attributes.
	 * The filter attributes can be match exactly or with like search if enabled.
	 * 
	 * @param filterAttributes a map of filter attributes
	 * @param enableLikeSearch true if the attributes must match exactly, false will enable like search
	 * @return the number of entities in the database that match the given attributes
	 */
	Long getCount(Map<SingularAttribute<T, ?>, Object> filterAttributes, boolean enableLikeSearch);
	
	/**
	 * Opens a new transaction for manual transaction management.
	 * 
	 * @return the new transaction
	 */
	TransactionStatus getTransaction();
	
	/**
	 * Commits a given transaction. All changes that happen in this transaction will be permanently stored in the datasbase. 
	 * 
	 * @param status the transaction
	 */
	void commit(TransactionStatus status);

	/**
	 * Rolls back a given transaction. All changes that happen in this transaction will be rolled back and therefore not be 
	 * stored in the database.
	 * 
	 * @param status the transaction
	 */
	void rollback(TransactionStatus status);
}
