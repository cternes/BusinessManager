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
