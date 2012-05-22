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
package org.businessmanager.geodb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.poi.util.StringUtil;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class OpenGeoDBImpl implements OpenGeoDB {

	private Map<String, OpenGeoDBMapper> mappers = new HashMap<String, OpenGeoDBMapper>();
	private Map<String, List<Country>> countryMap = new HashMap<String, List<Country>>();

	@Autowired
	private ApplicationSettingsService settingsService;
	
	public OpenGeoDBImpl() {
		init();
	}

	private void init() {
		InputStream rs = getClass().getClassLoader().getResourceAsStream(
				"geodb.zip");
		ZipInputStream zipInputStream = new ZipInputStream(rs);
		ZipEntry ze;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					zipInputStream, "UTF-8"));

			while ((ze = zipInputStream.getNextEntry()) != null) {
				String lname = ze.getName()
						.substring(0, ze.getName().lastIndexOf(".tab"))
						.toLowerCase();

				String line;
				OpenGeoDBMapper mapper = new OpenGeoDBMapper();

				while ((line = reader.readLine()) != null) {

					if (line.length() > 0) {
						if (line.startsWith("#"))
							continue;
						OpenGeoEntry entry = OpenGeoEntry.fromCSVLine(line,
								"\t");

						if (entry != null) {
							String[] plz = entry.getPlz();

							for (String plzEntry : plz) {
								mapper.putZipCode(plzEntry.trim(), entry);
							}
							mapper.putAreaCode(entry.getVorwahl(), entry);
						}
					}

				}
				mappers.put(lname, mapper);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.businessmanager.geodb.OpenGeoDB#findByZipCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OpenGeoEntry> findByZipCode(String country, String zipCode) {
		Validate.notNull(country, "Parameter country must not be null!");
		Validate.notNull(zipCode, "Parameter zipCode must not be null!");

		List<OpenGeoEntry> result = null;

		OpenGeoDBMapper openGeoDBMapper = mappers.get(country.toLowerCase());
		if (openGeoDBMapper != null) {
			result = openGeoDBMapper.findByZipCode(zipCode);
		}

		if (result == null) {
			result = new ArrayList<OpenGeoEntry>();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.businessmanager.geodb.OpenGeoDB#findByAreaCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<OpenGeoEntry> findByAreaCode(String country, String areaCode) {
		Validate.notNull(country, "Parameter country must not be null!");
		Validate.notNull(areaCode, "Parameter areaCode must not be null!");

		List<OpenGeoEntry> result = null;

		OpenGeoDBMapper openGeoDBMapper = mappers.get(country.toLowerCase());
		if (openGeoDBMapper != null) {
			result = openGeoDBMapper.findByAreaCode(areaCode);
		}

		if (result == null) {
			result = new ArrayList<OpenGeoEntry>();
		}

		return result;
	}

	public List<Country> getListOfCountries(String language) {
		List<Country> countries = null;

		if (language == null) {
			countries = countryMap.get(Locale.getDefault().getLanguage());
		} else {
			countries = countryMap.get(language);
		}

		if (countries == null) {
			countries = new ArrayList<Country>();
		} else {
			return countries;
		}
		
		List<String> codes = new ArrayList<String>();
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			String code = locale.getCountry();
			
			// do not insert a country more than once
			if(codes.contains(code)) {
				continue;
			}
			
			String name = null;
			if (language != null) {
				name = locale
						.getDisplayCountry(Locale.forLanguageTag(language));
			} else {
				name = locale.getDisplayCountry();
			}

			if (!"".equals(code) && !"".equals(name)) {
				countries.add(new Country(code, name));
				codes.add(code);
			}
		}
		
		Collections.sort(countries, Country.getComparator());
		Country defaultCountry = findDefaultCountry(countries);
		if(defaultCountry != null) {
			countries.add(0, defaultCountry);
		}
		
		if (language == null) {
			countryMap.put(Locale.getDefault().getLanguage(), countries);
		} else {
			countryMap.put(language, countries);
		}
		
		return countries;
	}
	
	private Country findDefaultCountry(List<Country> countries) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String defaultCountry = settingsService.getApplicationSettingValue(ApplicationSettingsService.GENERAL_COUNTRY, username);
		if(!StringUtils.isEmpty(defaultCountry)) {
			for (Country country : countries) {
				if(defaultCountry.equals(country.getCode())) {
					return country;
				}
			}
		}
		return null;
	}

	@Override
	public void refreshListOfCountries() {
		countryMap.clear();
	}
}
