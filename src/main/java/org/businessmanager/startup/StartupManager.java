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
package org.businessmanager.startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Setup environment and other stuff here
 * 
 */
@Component
public class StartupManager implements ApplicationListener<ContextRefreshedEvent> {

	private final Log logger = LogFactory.getLog(getClass());
	
	private static boolean STARTUP = true;
	
	@Autowired
	private StorageManager storageManager;
	
	@Autowired
	private ApplicationSettingsService settingsService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(STARTUP) {
			STARTUP = false;
			
			if(storageManager.isStartupScriptEnabled() && isFirstStartOfApplication()) {
				logger.info("Setup application...");
				
				setupDatabase();
				writeStartupFlag();
				
				logger.info("Setup done.");
			}
			else {
				logger.info("Skipping application setup");
			}
		}
	}
	
	private void writeStartupFlag() {
		settingsService.setApplicationSetting(ApplicationSetting.Group.SYSTEM_PREFERENCES, ApplicationSettingsService.GENERAL_FIRST_STARTUP, "false");
	}

	private boolean isFirstStartOfApplication() {
		String isFirstStartup = settingsService.getApplicationSettingValue(ApplicationSetting.Group.SYSTEM_PREFERENCES, ApplicationSettingsService.GENERAL_FIRST_STARTUP);
		if(isFirstStartup == null || "true".equalsIgnoreCase(isFirstStartup)) {
			return true;
		}
		return false;
	}
	
	public void setupDatabase() {
		storageManager.createDefaultEntities();
	}
	
}
