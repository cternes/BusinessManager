package org.businessmanager.web.jsf.helper;

import org.springframework.security.core.context.SecurityContext;

/**
 * A helper class to encapsulate the {@link SecurityContext} class. This is only needed to enable unit testing with mock objects.
 *
 * @author Christian Ternes
 */
public interface SecurityContextHelper {

	public SecurityContext getCurrentSecurityContext();
	
	/**
	 * Retrieves the username of the current session or null if not authenticated. 
	 * 
	 * @return the username or null if not authenticated
	 */
	public String getUserName();
}
