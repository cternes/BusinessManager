package org.businessmanager.web.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.businessmanager.geodb.Country;
import org.businessmanager.geodb.OpenGeoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("countryConverter")
public class CountryConverter implements Converter {

	@Autowired
	private OpenGeoDB openGeoDb;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Country.fromCountryCode(value);
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
