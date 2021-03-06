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

import java.util.Currency;
import java.util.List;

public interface OpenGeoDB {

	public List<OpenGeoEntry> findByZipCode(String country,
			String zipCode);

	public List<OpenGeoEntry> findByAreaCode(String country,
			String areaCode);

	public List<Country> getListOfCountries(String language);
	
	/**
	 * Retrieves a list of ISO currencies.
	 * 
	 * @param language
	 * @return a list of currencies
	 */
	public List<Currency> getListOfCurrencies(String language);
	
	/**
	 * Builds a new list of countries.
	 */
	public void refreshListOfCountries();
	
	/**
	 * Retrieves a country by its country code.
	 * 
	 * @param language the language in which to display the country name
	 * @param countryCode the country code of the country
	 * @return the {@link Country} if found or null
	 */
	public Country getCountryByCode(String language, String countryCode);
}
