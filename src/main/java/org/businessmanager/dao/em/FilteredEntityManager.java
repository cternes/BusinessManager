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
package org.businessmanager.dao.em;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;


/**
 * This {@link EntityManager} is a special one that retrieves only entities that are not flagged as deleted 
 * in the database.
 * 
 * This {@link EntityManager} can only manage {@link AbstractEntity}s.
 * 
 * @author Christian Ternes
 *
 */
public interface FilteredEntityManager extends EntityManager {

	/**
	 * Executes the standard method {@link EntityManager#createQuery(CriteriaQuery)} and disables filtering if flag
	 * is set to true. 
	 * 
	 * @param <T>
	 * @param criteriaQuery
	 * @param disableFiltering true disables filtering of entities
	 * @return
	 */
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery, boolean disableFiltering);
}
