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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public final class PasswordGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordGenerator.class);
	private static final PasswordEncoder ENCODER = new Md5PasswordEncoder();
	private static SecureRandom secureRandom;
	
	private PasswordGenerator () {
	}
	
	public static String encodePassword(String pwd, Long salt) {
	    return ENCODER.encodePassword(pwd, salt);
	}
	
	public static Long generateSalt() {
		if(secureRandom == null) {
			try {
				secureRandom = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				LOGGER.error("Could not initialize SecureRandom because the algorithm could not be found.");
			}
		}
		return Long.valueOf(secureRandom.nextLong());
	}
}
