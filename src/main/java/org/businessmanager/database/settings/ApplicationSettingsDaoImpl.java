package org.businessmanager.database.settings;

import java.util.List;

import org.businessmanager.database.GenericDaoImpl;
import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.domain.settings.ApplicationSetting_;
import org.springframework.stereotype.Repository;

/**
 * @author Christian Ternes
 *
 */
@Repository
public class ApplicationSettingsDaoImpl extends GenericDaoImpl<ApplicationSetting> implements ApplicationSettingsDao {

	@Override
	public List<ApplicationSetting> findAll() {
		return findAll(ApplicationSetting_.id, true);
	}

	@Override
	public Class<ApplicationSetting> getPersistenceClass() {
		return ApplicationSetting.class;
	}
	
	@Override 
	public ApplicationSetting getApplicationSettingByKey(String key) {
		List<ApplicationSetting> list = findByAttribute(ApplicationSetting_.paramKey, key);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
