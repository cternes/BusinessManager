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
