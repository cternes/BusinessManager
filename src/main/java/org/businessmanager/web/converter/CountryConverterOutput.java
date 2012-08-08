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
package org.businessmanager.web.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.businessmanager.geodb.Country;
import org.businessmanager.geodb.OpenGeoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link Country} object into a localized country name. 
 *
 */
@Component("countryConverterOutput")
public class CountryConverterOutput implements Converter {

	@Autowired
	private OpenGeoDB openGeoDb;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value; //do nothing
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
            return "";
        }
		if (value instanceof Country) {
			return ((Country) value).getName();
		}
		else if (value instanceof String) {
			Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
			Country country = openGeoDb.getCountryByCode(locale.getLanguage(), (String) value);
			if(country != null) {
				return country.getName();
			}
		}
		return "";
	}

}
