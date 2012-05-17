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
