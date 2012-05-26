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
package org.businessmanager.database.settings;

import java.util.List;

import org.businessmanager.database.GenericDao;
import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.domain.settings.ApplicationSetting.Group;

/**
 * Manages the database access of the entity ApplicationSetting.
 * 
 * @author Christian Ternes
 *
 */
public interface ApplicationSettingsDao extends GenericDao<ApplicationSetting> {

	/**
	 * Retrieves an {@link ApplicationSetting} by its key.
	 * 
	 * @param key the settings key
	 * @param username the name of the user, can be null
	 * @return the {@link ApplicationSetting} if found or null
	 */
	public ApplicationSetting getApplicationSettingByKey(Group group, String key, String username);
	
	public List<ApplicationSetting> getApplicationSettingsByGroup(Group group);
}
