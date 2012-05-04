package org.businessmanager.service.settings;

import java.util.List;

import org.businessmanager.database.settings.ApplicationSettingsDao;
import org.businessmanager.domain.settings.ApplicationSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Christian Ternes
 *
 */
@Service
public class ApplicationSettingsServiceImpl implements ApplicationSettingsService {

	@Autowired
	private ApplicationSettingsDao dao;
	
	@Override
	@Transactional(readOnly=true)
	public List<ApplicationSetting> getApplicationSettings() {
		return dao.findAll();
	}
	
	@Override
	@Transactional
	public String getApplicationSettingValue(String key) {
		ApplicationSetting setting = dao.getApplicationSettingByKey(key);
		if(setting != null) {
			return setting.getParamValue();
		}
		return null;
	}
	
	@Override
	@Transactional
	public void setApplicationSetting(String key, String value) {
		ApplicationSetting setting = dao.getApplicationSettingByKey(key);
		if(setting == null) {
			setting = new ApplicationSetting();
			setting.setParamKey(key);
			setting.setParamValue(value);
			dao.save(setting);
		}
		else {
			setting.setParamValue(value);
			dao.update(setting);
		}
	}
}
