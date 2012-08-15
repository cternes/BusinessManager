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
package org.businessmanager.web.bean;

import org.businessmanager.domain.ModificationType;

public abstract class AbstractActivityBean {
	
	protected String username;
	protected ModificationType activity;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ModificationType getActivity() {
		return activity;
	}
	public void setActivity(ModificationType activity) {
		this.activity = activity;
	}

	public abstract String toJson();
	
	public abstract AbstractActivityBean fromJson(String json);
}
