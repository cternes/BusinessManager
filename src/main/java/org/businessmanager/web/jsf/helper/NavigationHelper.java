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
package org.businessmanager.web.jsf.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class contains the names of all pages used in the application.
 * For all navigation actions a getter of this class should be used to determine
 * the navigation target.
 * 
 * @author Christian Ternes
 *
 */
@Component("navigationHelper")
public class NavigationHelper {
	
	@Autowired(required=true)
	private FacesContextHelper facesContext;
	
	private String contextPath;
	private boolean isRedirect = true;
	private boolean isWithoutContext = true;
	
	private static final String ADMIN_HOME 						= "/views/admin/admin.jsf";
	private static final String CONTACTMANAGEMENT 				= "/views/contact/contactManagement.jsf";
	private static final String CONTACT_EDIT					= "/views/contact/editContact.jsf";
	private static final String CONTACT_VIEW					= "/views/contact/viewContact.jsf";

	public String getContextPath() {
		contextPath = facesContext.getCurrentFacesContext().getExternalContext().getRequestContextPath();
		return contextPath;
	}
	
	/**
	 * Retrieves the path to a jsf view. The default behaviour will return the jsf view with the application context as 
	 * prefix e.g. MyApplication/views/admin.jsf.
	 * 
	 * <p/>
	 * 
	 * If {@link NavigationHelper#isRedirect} is set to true, the faces redirect string will be added to the path
	 * e.g. MyApplication/views/admin.jsf?faces-redirect=true
	 * 
	 * <p/>
	 * 
	 * If {@link NavigationHelper#isWithoutContext} is set to true, the application context will be removed from the path
	 * e.g.  /views/admin.jsf
	 * 
	 * <p/>
	 * {@link NavigationHelper#isRedirect}=true and {@link NavigationHelper#isWithoutContext}=true is mostly needed by 
	 * jsf components such as <pre>&lt;h:commandButton&gt;</pre> or <pre>&lt;h:commandLink&gt;</pre> in order to redirect correctly.
	 * 
	 * 
	 * @param relativePath
	 * @return
	 */
	private String getNavigation(String relativePath) {
		String path = relativePath;
		if(isRedirect) {
			path = appendParamInitiator(path);
			path += "faces-redirect=true";
		}
		if(!isWithoutContext) {
			if(contextPath == null) {
				getContextPath();
			}
			path = contextPath + path;
		}

		return path;
	}

	private String appendParamInitiator(String path) {
		if(path.contains("?")) {
			return path + "&";
		}
		else {
			return path + "?";
		}
	}
	
	public String getAdminHome() {
		return getNavigation(ADMIN_HOME);
	}

	public String getEditContact() {
		return getNavigation(CONTACT_EDIT);
	}

	public String getContactmanagement() {
		return getNavigation(CONTACTMANAGEMENT);
	}

	public String getContactView() {
		return getNavigation(CONTACT_VIEW);
	}
	
}
