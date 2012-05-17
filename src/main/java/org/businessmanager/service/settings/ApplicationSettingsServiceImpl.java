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
