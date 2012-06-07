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
package org.businessmanager.i18n;

import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.faces.context.FacesContext;

import org.businessmanager.i18n.ResourceBundleUTF8.UTF8Control;

public class ResourceBundleAccessor {

	protected static final String BUNDLE_NAME = "MessageResources";
    protected static final Control UTF8_CONTROL = new UTF8Control();
	
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL);
	}
	
	public static String getString(String key) {
		if(FacesContext.getCurrentInstance() == null) {
			return key;
		}
		return getResourceBundle().getString(key);
	}
}
