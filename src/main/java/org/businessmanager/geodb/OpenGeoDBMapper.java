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
package org.businessmanager.geodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenGeoDBMapper {
	private Map<String, List<OpenGeoEntry>> zipCodeMap = new HashMap<String, List<OpenGeoEntry>>();
	private Map<String, List<OpenGeoEntry>> areaCodeMap = new HashMap<String, List<OpenGeoEntry>>();

	public void putZipCode(String zipCode, OpenGeoEntry openGeoEntry) {
		List<OpenGeoEntry> list = zipCodeMap.get(zipCode);
		if (list == null) {
			list = new ArrayList<OpenGeoEntry>();
			zipCodeMap.put(zipCode, list);
		}
		list.add(openGeoEntry);
	}

	public List<OpenGeoEntry> findByZipCode(String zipCode) {
		return zipCodeMap.get(zipCode);
	}

	public void putAreaCode(String areaCode, OpenGeoEntry openGeoEntry) {
		List<OpenGeoEntry> list = areaCodeMap.get(areaCode);
		if (list == null) {
			list = new ArrayList<OpenGeoEntry>();
			areaCodeMap.put(areaCode, list);
		}
		list.add(openGeoEntry);
	}

	public List<OpenGeoEntry> findByAreaCode(String areaCode) {
		return areaCodeMap.get(areaCode);
	}
}
