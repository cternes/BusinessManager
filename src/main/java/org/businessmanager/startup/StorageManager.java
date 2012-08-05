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
	
	@SuppressWarnings("rawtypes")
	private Map<EntityProvider, EntityStorageStrategy> storageMap;
	
	@SuppressWarnings("rawtypes")
	public Map<EntityProvider, EntityStorageStrategy> getStorageMap() {
		return storageMap;
	}

	@SuppressWarnings("rawtypes")
	public void setStorageMap(Map<EntityProvider, EntityStorageStrategy> storageMap) {
		this.storageMap = storageMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
