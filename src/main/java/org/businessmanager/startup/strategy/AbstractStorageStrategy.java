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
package org.businessmanager.startup.strategy;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.database.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 * This abstract class is the base class for all {@link EntityStorageStrategy}s.
 * It provides common functionality to persist entities.
 * 
 * <p>
 * It is derived from the TemplateMethodPattern. 
 * 
 * @author Christian Ternes
 *
 * @param <T> the entity class
 */
public abstract class AbstractStorageStrategy<T> implements EntityStorageStrategy<T>{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Transactional
	public void createInitialObjects(List<T> objectList, boolean removeObjects) {
		if(removeObjects) {
			removeAllObjects();
		}
		createObjects(objectList);
	}
	
	protected void createObjects(List<T> objectList) {
		for (T entity : objectList) {
			List<T> result = getDao().findByAttribute(getAttributeKey(), getAttributeValue(entity));
			if(result.size() == 0) {
				doPreProcessing(entity);
				entity = getDao().save(entity);
				doPostProcessing(entity);
			}
		}
	}

	protected void removeAllObjects() {
		List<T> list = getDao().findAll();
		for (T object : list) {
			getDao().remove(object);
		}
	}
	
	@Transactional
	@Override
	public void storeEntities(List<T> entityList) {
		if(logger.isDebugEnabled()) {
			logger.debug("Executing strategy "+getClass().getName()+"...");
		}
		createInitialObjects(entityList, isEntitiesRemovedOnStartup());
	}
	
	/**
	 * Retrieves a DAO which can persist the entity class. 
	 * 
	 * @return a DAO
	 */
	public abstract GenericDao<T> getDao();
	
	/**
	 * Retrieves the attribute which identifies an entity. 
	 * Entities will be retrieved by this attribute from the persistence container.
	 * 
	 * @return an attribute of the entity
	 */
	public abstract SingularAttribute<T, ?> getAttributeKey();
	
	/**
	 * Retrieves the attribute value which identifies an entity. 
	 * <p>
	 * This <b>MUST match</b> to the {@link AbstractStorageStrategy#getAttributeKey()} method.
	 * 
	 * @param source an entity object
	 * @return the attribute value
	 */
	public abstract Object getAttributeValue(T source);
	
	/**
	 * Copies the object attributes from source to target entity.
	 * 
	 * @param source the source entity from the persistence container
	 * @param target the target entity NOT from the persistence container
	 * @return the updated entity
	 */
	public abstract T updateEntity(T source, T target);
	
	/**
	 * Determines if all found entities should be removed when executing this strategy.
	 * 
	 * @return true to remove all entities, false otherwise
	 */
	public abstract boolean isEntitiesRemovedOnStartup();
	
	/**
	 * This is a hook to provide some pre-processing before persisting an entity.
	 * 
	 * @param source the entity to persist
	 */
	public void doPreProcessing(T source) {
		//default implementation is empty, can be overridden
	}
	
	/**
	 * This is a hook to provide some post-processing after an entity has been persisted.
	 * 
	 * @param source the already persisted entity 
	 */
	public void doPostProcessing(T source) {
		//default implementation is empty, can be overridden
	}

}
