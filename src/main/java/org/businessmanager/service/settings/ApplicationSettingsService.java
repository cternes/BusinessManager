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
package org.businessmanager.service.settings;

import java.util.List;

import org.businessmanager.domain.settings.ApplicationSetting;

/**
 * A service to manage the application settings.
 * <i>Note:</i> All keys are defined here.
 * 
 * @author Christian Ternes
 *
 */
public interface ApplicationSettingsService {

	public static final String GENERAL_LANGUAGE = "general.language";
	public static final String GENERAL_COUNTRY = "general.country";
	
	/**
	 * Returns all application settings from the database.
	 * 
	 * @return a list of application settings
	 */
	public List<ApplicationSetting> getApplicationSettings();
	
	/**
	 * Returns the user settings for the given username from the database.
	 * 
	 * @param username the name of the user 
	 * @return a list of application settings
	 */
	public List<ApplicationSetting> getApplicationSettingsByUsername(String username);
	
	/**
	 * Returns the application setting value from the database for the given key.
	 * <i>Note:</i> All keys are defined here.
	 * 
	 * @param key the setting key which should be searched
	 * @return the value of the setting or null if not existing
	 */
	public String getApplicationSettingValue(String key);
	
	/**
	 * Returns the application setting value from the database for the given key and username.
	 * If that application setting does not exist it returns the setting value without username if existing.
	 * 
	 * <i>Note:</i> All keys are defined here.
	 * 
	 * @param key the setting key which should be searched
	 * @param username the name of the user
	 * @return the value of the setting or null if not existing
	 */
	public String getApplicationSettingValue(String key, String username);
	
	/**
	 * Sets the application setting value in the database for the given key.
	 * If the setting does not exist in the database, it will be created.
	 * 
	 * @param key the key of the setting 
	 * @param value the value of the setting
	 */
	public void setApplicationSetting(String key, String value);
	
	/**
	 * Sets the application setting value in the database for the given key and username.
	 * If the setting does not exist in the database, it will be created.
	 * 
	 * @param key the key of the setting 
	 * @param value the value of the setting
	 * @param username the name of the user
	 */
	public void setApplicationSetting(String key, String value, String username);
}
