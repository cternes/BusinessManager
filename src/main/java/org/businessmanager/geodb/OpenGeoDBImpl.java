package org.businessmanager.geodb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;

@Service
public class OpenGeoDBImpl implements OpenGeoDB {

	private Map<String, OpenGeoDBMapper> mappers = new HashMap<String, OpenGeoDBMapper>();

	public OpenGeoDBImpl() {
		init();
	}

	private void init() {
		InputStream rs = getClass().getClassLoader().getResourceAsStream("geodb.zip");
		ZipInputStream zipInputStream = new ZipInputStream(rs);
		ZipEntry ze;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					zipInputStream, "UTF-8"));

			while ((ze = zipInputStream.getNextEntry()) != null) {
				String lname = ze.getName().substring(0,
						ze.getName().lastIndexOf(".tab")).toLowerCase();

				String line;
				OpenGeoDBMapper mapper = new OpenGeoDBMapper();

				while ((line = reader.readLine()) != null) {

					if (line.length() > 0) {
						if (line.startsWith("#"))
							continue;
						OpenGeoEntry entry = OpenGeoEntry.fromCSVLine(line,
								"\t");

						if (entry != null) {
							String[] plz = entry.getPlz();

							for (String plzEntry : plz) {
								mapper.putZipCode(plzEntry.trim(), entry);
							}
							mapper.putAreaCode(entry.getVorwahl(), entry);
						}
					}

				}
				mappers.put(lname, mapper);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.businessmanager.geodb.OpenGeoDB#findByZipCode(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OpenGeoEntry> findByZipCode(String country, String zipCode) {
		Validate.notNull(country, "Parameter country must not be null!");
		Validate.notNull(zipCode, "Parameter zipCode must not be null!");
		
		List<OpenGeoEntry> result = null;

		OpenGeoDBMapper openGeoDBMapper = mappers.get(country.toLowerCase());
		if (openGeoDBMapper != null) {
			result = openGeoDBMapper.findByZipCode(zipCode);
		}
		
		if(result == null) {
			 result = new ArrayList<OpenGeoEntry>();
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.businessmanager.geodb.OpenGeoDB#findByAreaCode(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OpenGeoEntry> findByAreaCode(String country, String areaCode) {
		Validate.notNull(country, "Parameter country must not be null!");
		Validate.notNull(areaCode, "Parameter areaCode must not be null!");
		
		List<OpenGeoEntry> result = null;

		OpenGeoDBMapper openGeoDBMapper = mappers.get(country.toLowerCase());
		if (openGeoDBMapper != null) {
			result = openGeoDBMapper.findByAreaCode(areaCode);
		}
		
		if(result == null) {
			 result = new ArrayList<OpenGeoEntry>();
		}
		
		return result;
	}
}
