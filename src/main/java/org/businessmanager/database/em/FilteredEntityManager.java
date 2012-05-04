package org.businessmanager.database.em;

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
