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

	/**
	 * Returns all application settings from the database.
	 * 
	 * @return a list of application settings
	 */
	public List<ApplicationSetting> getApplicationSettings();
	
	/**
	 * Returns the application setting value from the database for the given key.
	 * <i>Note:</i> All keys are defined in {@link ConfigurationUtil}.
	 * 
	 * @param key the setting key which should be searched
	 * @return the value of the setting or null if not existing
	 */
	public String getApplicationSettingValue(String key);
	
	/**
	 * Sets the application setting value in the database for the given key.
	 * If the setting does not exist in the database, it will be created.
	 * 
	 * @param key the key of the setting 
	 * @param value the value of the setting
	 */
	public void setApplicationSetting(String key, String value);
}
