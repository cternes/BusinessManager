package org.businessmanager.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * A thin wrapper around the Spring Security MD5 generator.
 * 
 * @author Christian Ternes
 *
 */
public final class MD5Generator {

	private static final PasswordEncoder ENCODER = new Md5PasswordEncoder();
	
	private MD5Generator () {
	}
	
	public static String encodePassword(String pwd, String username) {
	    return ENCODER.encodePassword(pwd, username);
	}
}
