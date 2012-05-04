package org.businessmanager.startup;

import java.util.Iterator;
import java.util.Map;

import org.businessmanager.startup.provider.EntityProvider;
import org.businessmanager.startup.strategy.EntityStorageStrategy;
import org.springframework.stereotype.Component;

/**
 * This manager holds a map of {@link EntityProvider}s with their corresponding
 * {@link EntityStorageStrategy}s.
 *  
 * <p>
 * 
 * The idea is that every {@link EntityStorageStrategy} is provided with entities from their 
 * {@link EntityProvider}. The map is usually be managed by spring config.
 * 
 * @author Christian Ternes
 *
 */
@Component
public class StorageManager {

	private boolean isStartupScriptEnabled;
	private Map<EntityProvider, EntityStorageStrategy> storageMap;
	
	public Map<EntityProvider, EntityStorageStrategy> getStorageMap() {
		return storageMap;
	}

	public void setStorageMap(Map<EntityProvider, EntityStorageStrategy> storageMap) {
		this.storageMap = storageMap;
	}

	public void createDefaultEntities() {
		if(storageMap != null) {
			Iterator<EntityProvider> keyIterator = storageMap.keySet().iterator();
			while(keyIterator.hasNext()) {
				EntityProvider provider = keyIterator.next();
				EntityStorageStrategy strategy = storageMap.get(provider);
				strategy.storeEntities(provider.getEntityList());
			}
		}
	}

	public void setStartupScriptEnabled(boolean isStartupScriptEnabled) {
		this.isStartupScriptEnabled = isStartupScriptEnabled;
	}

	public boolean isStartupScriptEnabled() {
		return isStartupScriptEnabled;
	}

}
