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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * This controller will be executed <b>once on application startup</b>.
 * This is usually be used to setup databases or initialize environment variables.
 * 
 * @author Christian Ternes
 *
 */
@Component
public class StartupManager implements ApplicationListener<ContextRefreshedEvent> {

	private final Log logger = LogFactory.getLog(getClass());
	
	private static boolean IS_STARTUP = true; /*needs to be static*/
	
	@Autowired
	private StorageManager storageManager;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(IS_STARTUP) {
			IS_STARTUP = false;
			
			//setup database if enabled
			if(storageManager.isStartupScriptEnabled()) {
				logger.info("Setting up database & environment...");
				
				setupDatabase();
				
				logger.info("Setup completed.");
			}
		}
	}
	
	public void setupDatabase() {
		storageManager.createDefaultEntities();
	}
	
}
