package org.businessmanager.startup.strategy;

import java.util.List;

/**
 * This interface contains a storage strategy for a concrete entity class.
 * This is usually be used to store a list of entities into a persistence container like a 
 * database.
 * 
 * @author Christian Ternes
 *
 * @param <T>
 */
public interface EntityStorageStrategy<T> {

	/**
	 * Stores the given list of entities in a persistence container.
	 * 
	 * @param entityList the list of entities which should be persisted
	 */
	public void storeEntities(List<T> entityList);

}
