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
package org.businessmanager.web.controller.page.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.geodb.Country;
import org.businessmanager.geodb.OpenGeoDB;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.businessmanager.web.controller.AbstractPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("settingsController")
@Scope("request")
public class SettingsController extends AbstractPageController {

	@Autowired
	private ApplicationSettingsService service;
	
	@Autowired
	private OpenGeoDB openGeoService; 
	
	private Map<String, String> settingsMap = new HashMap<String, String>();
	
	@PostConstruct
	public void init() {
		List<ApplicationSetting> aApplicationSettingsList = service.getApplicationSettings();
		for (ApplicationSetting aApplicationSetting : aApplicationSettingsList) {
			getSettingsMap().put(aApplicationSetting.getParamKey(), aApplicationSetting.getParamValue());
		}
	}
	
	public void save() {
		if(settingsMap != null) {
			Iterator<String> anIterator = settingsMap.keySet().iterator();
			while(anIterator.hasNext()) {
				String aKey = anIterator.next();
				String aValue = settingsMap.get(aKey);
				if(aValue != null) {
					service.setApplicationSetting(aKey, aValue);
				}
			}
			
			openGeoService.refreshListOfCountries();
			
			addMessage(FacesMessage.SEVERITY_INFO, "settings_saved");
		}
	}
	
	public Map<String, String> getSettingsMap() {
		return settingsMap;
	}
	
	public List<SelectItem> getAvailableLanguages() {
		Locale language = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		
		String german = Locale.GERMAN.getDisplayLanguage(language);
		String english = Locale.ENGLISH.getDisplayLanguage(language);
		
		List<SelectItem> aSelectItemsList = new ArrayList<SelectItem>();
		aSelectItemsList.add(new SelectItem(Locale.GERMAN.getLanguage(), german));
		aSelectItemsList.add(new SelectItem(Locale.ENGLISH.getLanguage(), english));
		return aSelectItemsList;
	}
	
	public List<Country> getAvailableCountries() {
		String language = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		return openGeoService.getListOfCountries(language);
	}
}
