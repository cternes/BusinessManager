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
