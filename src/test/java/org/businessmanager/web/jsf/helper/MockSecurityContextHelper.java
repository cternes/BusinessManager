package org.businessmanager.web.jsf.helper;

import org.easymock.EasyMock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class MockSecurityContextHelper implements SecurityContextHelper {

	@Override
	public SecurityContext getCurrentSecurityContext() {
		SecurityContext mockSecurityContext = EasyMock.createMock(SecurityContext.class);
		Authentication authentication = EasyMock.createMock(Authentication.class);
		EasyMock.expect(mockSecurityContext.getAuthentication()).andReturn(authentication);
		EasyMock.replay(mockSecurityContext);
		
		return mockSecurityContext;
	}

	@Override
	public String getUserName() {
		return getCurrentSecurityContext().getAuthentication().getName();
	}

}
