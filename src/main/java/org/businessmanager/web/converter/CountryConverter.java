package org.businessmanager.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.businessmanager.geodb.Country;
import org.springframework.stereotype.Component;

@Component("countryConverter")
public class CountryConverter implements Converter {

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
		return "";
	}

}
