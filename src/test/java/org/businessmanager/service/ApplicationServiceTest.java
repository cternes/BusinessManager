package org.businessmanager.service;

import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
/*
@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional*/
public class ApplicationServiceTest {

	@Autowired
	private ApplicationSettingsService service;

	@Test
	public void testGetSettingForUsername() {
		/*
		String username = "admin";
		String language = service.getApplicationSettingValue(
				ApplicationSetting.Group.USER_PREFERENCS,
				ApplicationSettingsService.GENERAL_LANGUAGE, username);
		Assert.assertNull(language);

		// must return 'de', since there is no such setting for username
		service.setApplicationSetting(ApplicationSetting.Group.USER_PREFERENCS,
				ApplicationSettingsService.GENERAL_LANGUAGE, "de");
		language = service.getApplicationSettingValue(
				ApplicationSetting.Group.USER_PREFERENCS,
				ApplicationSettingsService.GENERAL_LANGUAGE, username);
		Assert.assertEquals("de", language);

		// must return 'en', since there is a setting for username
		service.setApplicationSetting(ApplicationSetting.Group.USER_PREFERENCS,
				ApplicationSettingsService.GENERAL_LANGUAGE, "en", username);
		language = service.getApplicationSettingValue(
				ApplicationSetting.Group.USER_PREFERENCS,
				ApplicationSettingsService.GENERAL_LANGUAGE, username);
		Assert.assertEquals("en", language);*/
	}
}
