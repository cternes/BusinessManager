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
