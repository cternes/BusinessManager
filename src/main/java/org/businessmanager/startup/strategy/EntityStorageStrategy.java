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
