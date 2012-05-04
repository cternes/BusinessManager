package org.businessmanager.web.jsf.helper;

import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.faces.context.FacesContext;

import org.businessmanager.web.jsf.helper.ResourceBundleUTF8.UTF8Control;


public class ResourceBundleProducer {

	protected static final String BUNDLE_NAME = "MessageResources";
    protected static final Control UTF8_CONTROL = new UTF8Control();
	
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL);
	}
	
	public static String getString(String key) {
		if(FacesContext.getCurrentInstance() == null) { //just needed for unit tests
			return key;
		}
		return getResourceBundle().getString(key);
	}
}
