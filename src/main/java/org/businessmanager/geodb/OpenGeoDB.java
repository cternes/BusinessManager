package org.businessmanager.geodb;

import java.util.List;

public interface OpenGeoDB {

	public List<OpenGeoEntry> findByZipCode(String country,
			String zipCode);

	public List<OpenGeoEntry> findByAreaCode(String country,
			String areaCode);

}