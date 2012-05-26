package org.businessmanager.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("languageController")
@Scope("session")
public class LanguageController extends AbstractController {

	@Autowired
	private ApplicationSettingsService settingsService;

	private Locale localeForCurrentUser;

	public List<SelectItem> getAvailableLanguages() {
		Locale language = facesContext.getLocale();

		List<Locale> languages = getLanguagesFromDb();
		List<SelectItem> selectItemsList = new ArrayList<SelectItem>();
		for (Locale locale : languages) {
			selectItemsList.add(new SelectItem(locale.getLanguage(), locale
					.getDisplayLanguage(language)));
		}

		return selectItemsList;
	}

	private List<Locale> getLanguagesFromDb() {
		List<Locale> list = new ArrayList<Locale>();

		// TODO: retrieve supported languages from database
		list.add(Locale.GERMAN);
		list.add(Locale.ENGLISH);

		return list;
	}

	public void setLocale(Locale newLocale) {
		localeForCurrentUser = newLocale;
		facesContext.setLocale(newLocale);
	}

	public Locale getLocale() {
		if (localeForCurrentUser == null) {
			localeForCurrentUser = getLocaleForCurrentUser();
		}
		if (localeForCurrentUser != null) {
			return localeForCurrentUser;
		}

		return facesContext.getLocale();
	}

	private Locale getLocaleForCurrentUser() {
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		if (username != null) {
			String language = settingsService.getApplicationSettingValue(
					ApplicationSetting.Group.USER_PREFERENCS,
					ApplicationSettingsService.GENERAL_LANGUAGE, username);
			if (language != null) {
				return new Locale(language);
			}
		}
		return null;
	}
}
