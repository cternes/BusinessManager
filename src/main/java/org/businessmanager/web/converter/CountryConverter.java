package org.businessmanager.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.businessmanager.geodb.Country;

public class CountryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println(arg2);
		return Country.fromCountryCode("DE");
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null && arg2 instanceof Country) {
			return ((Country) arg2).getName();
		}
		return "";
	}

}
