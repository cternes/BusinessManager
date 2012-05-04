package org.businessmanager.startup.provider;

import java.util.List;

/**
 * This interface provides a list of entities.
 * 
 * @author Christian Ternes
 *
 * @param <T> the entity class
 */
public interface EntityProvider<T> {

	public List<T> getEntityList();
}
