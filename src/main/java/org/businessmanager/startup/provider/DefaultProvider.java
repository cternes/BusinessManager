package org.businessmanager.startup.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * This provider is the default implementation of the {@link EntityProvider} interface.
 * This provider will be usually be initialized as spring bean and filled with entities by spring config. 
 * 
 * @author Christian Ternes
 *
 * @param <T> the entity class
 */
public class DefaultProvider<T> implements EntityProvider<T> {

	private List<T> objectList = new ArrayList<T>();
	
	public List<T> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}

	@Override
	public List<T> getEntityList() {
		return objectList;
	}
}
