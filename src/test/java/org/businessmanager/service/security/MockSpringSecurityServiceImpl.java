package org.businessmanager.service.security;

import org.businessmanager.domain.security.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MockSpringSecurityServiceImpl implements SpringSecurityService {

	@Override
	public User getLoggedInUser() {
		return new User("anonymous", "s3cret");
	}

	@Override
	public Long getLoggedInUserId() {
		return 9999L;
	}

}
