package org.businessmanager.web.controller.page.admin;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.domain.settings.ApplicationSetting.Group;
import org.businessmanager.geodb.OpenGeoDB;
import org.businessmanager.geodb.OpenGeoEntry;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.businessmanager.web.bean.CompanySettingsBean;
import org.businessmanager.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("companySettingsController")
@Scope("request")
public class CompanySettingsController extends AbstractController {

	@Autowired
	private ApplicationSettingsService settingsService;

	@Autowired
	private OpenGeoDB openGeoDB;
	
	private CompanySettingsBean companySettings;
	
	@PostConstruct
	public void init() {
		List<ApplicationSetting> companySettingsList = settingsService.getApplicationSettingsByGroup(Group.COMPANY_PREFERENCES);
		companySettings = new CompanySettingsBean();
		companySettings.setSettings(companySettingsList);
	}
	
	public CompanySettingsBean getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettingsBean companySettings) {
		this.companySettings = companySettings;
	}

	public void saveSettings() {
		if(companySettings != null) {
			Iterator<String> iter = companySettings.getSettingsMap().keySet().iterator();
			while(iter.hasNext()) {
				String key = iter.next();
				String value = companySettings.getSettingsMap().get(key);
				if(value != null) {
					settingsService.setApplicationSetting(ApplicationSetting.Group.COMPANY_PREFERENCES, key, value, null);
				}
			}
			
			addMessage(FacesMessage.SEVERITY_INFO, "settings_saved");
		}
	}
	
	public void findCity() {
		String zipCode = companySettings.getZip();
		if(zipCode == null || zipCode.length() < 3) {
			return;
		}
		
		List<OpenGeoEntry> findByZipCode = openGeoDB.findByZipCode("DE", zipCode);
		
		if(findByZipCode != null && findByZipCode.size() > 0) {
			companySettings.setCity(findByZipCode.get(0).getName());
		}
	}
}
