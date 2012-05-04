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
	private static final String DUMMY							= "/views/admin/dummy1.jsf";

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

	public String getDummy() {
		return getNavigation(DUMMY);
	}
	
}
