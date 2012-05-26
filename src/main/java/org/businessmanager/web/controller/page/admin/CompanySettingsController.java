package org.businessmanager.web.controller.page.admin;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.domain.settings.ApplicationSetting.Group;
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

	private CompanySettingsBean companySettings;
	
	@PostConstruct
	public void init() {
		List<ApplicationSetting> companySettingsList = settingsService.getApplicationSettingsByGroup(Group.COMPANY_PREFERENCES);
		companySettings = new CompanySettingsBean();
		companySettings.setSettings(companySettingsList);
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
}
