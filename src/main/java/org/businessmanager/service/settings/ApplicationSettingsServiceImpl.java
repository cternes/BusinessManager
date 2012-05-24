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

import org.businessmanager.database.settings.ApplicationSettingsDao;
import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.domain.settings.ApplicationSetting_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Christian Ternes
 *
 */
@Service
@Transactional
public class ApplicationSettingsServiceImpl implements ApplicationSettingsService {

	@Autowired
	private ApplicationSettingsDao dao;
	
	@Override
	public List<ApplicationSetting> getApplicationSettings() {
		return dao.findAll();
	}
	
	@Override
	public String getApplicationSettingValue(String key) {
		return getApplicationSettingValue(key, null);
	}
	
	@Override
	public String getApplicationSettingValue(String key, String username) {
		ApplicationSetting setting = dao.getApplicationSettingByKey(key, username);

		//try to find general setting if setting was not found for username
		if(setting == null && username != null) {
			setting = dao.getApplicationSettingByKey(key, null);
		}
		
		if(setting != null) {
			return setting.getParamValue();
		}
		return null;
	}
	
	@Override
	public void setApplicationSetting(String key, String value) {
		setApplicationSetting(key, value, null);
	}

	@Override
	public List<ApplicationSetting> getApplicationSettingsByUsername(String username) {
		return dao.findByAttribute(ApplicationSetting_.username, username);
	}

	@Override
	public void setApplicationSetting(String key, String value, String username) {
		ApplicationSetting setting = dao.getApplicationSettingByKey(key, username);
		if(setting == null) {
			setting = new ApplicationSetting();
			setting.setParamKey(key);
			setting.setParamValue(value);
			setting.setUsername(username);
			dao.save(setting);
		}
		else {
			setting.setParamValue(value);
			dao.update(setting);
		}
	}

}
