package org.businessmanager.database.settings;

import org.businessmanager.database.GenericDao;
import org.businessmanager.domain.settings.ApplicationSetting;

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
	 * @return the {@link ApplicationSetting} if found or null
	 */
	public ApplicationSetting getApplicationSettingByKey(String key);
}
