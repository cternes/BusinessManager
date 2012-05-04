package org.businessmanager.web.jsf.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * A helper class to encapsulate the {@link SecurityContext} class. This is only needed to enable unit testing with mock objects.
 * 
 * @author Christian Ternes
 * @see SecurityContextHelper
 */
@Component
public class SecurityContextHelperImpl implements SecurityContextHelper {

	@Override
	public SecurityContext getCurrentSecurityContext() {
		return SecurityContextHolder.getContext();
	}

	/* (non-Javadoc)
	 * @see org.businessmanager.web.jsf.helper.SecurityContextHelper#getUserName()
	 */
	@Override
	public String getUserName() {
		Authentication anAuthentication = getCurrentSecurityContext().getAuthentication();
		if(anAuthentication != null) {
			return anAuthentication.getName();
		}
		return null;
	}

}
