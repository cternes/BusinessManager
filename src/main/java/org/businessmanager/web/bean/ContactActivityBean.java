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

import java.util.HashMap;
import java.util.Map;

import org.businessmanager.domain.ModificationType;
import org.businessmanager.util.JsonUtil;

public class ContactActivityBean extends AbstractActivityBean {

	private String contactName;
	
	public ContactActivityBean() {
	}
	
	public ContactActivityBean(String username, ModificationType activity, String contactName) {
		this.username = username;
		this.activity = activity;
		this.contactName = contactName;
	}
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String toJson() {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("username", username);
		dataMap.put("activity", activity.toString());
		dataMap.put("contactname", contactName);
		
		return JsonUtil.getInstance().writeJson(dataMap);
	}

	public ContactActivityBean fromJson(String json) {
		if(json != null) {
			Map<String, String> dataMap = JsonUtil.getInstance().readJson(json);
			if(dataMap != null) {
				username = dataMap.get("username");
				activity = ModificationType.valueOf(dataMap.get("activity"));
				contactName = dataMap.get("contactname");
			}
		}
		
		return this;
	}
	
}
