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

public class OpenGeoEntry {

	private String id = null;
	private String ags = null;
	private String ascii = null;
	private String name = null;
	private String lat = null;
	private String lon = null;
	private String amt = null;
	private String[] plz = null;
	private String vorwahl = null;
	private String einwohner = null;
	private String flaeche = null;
	private String kz = null;
	private String typ = null;
	private String level = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgs() {
		return ags;
	}

	public void setAgs(String ags) {
		this.ags = ags;
	}

	public String getAscii() {
		return ascii;
	}

	public void setAscii(String ascii) {
		this.ascii = ascii;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String[] getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz.split(",");
	}

	public void setPlz(String[] plz) {
		this.plz = plz;
	}

	public String getVorwahl() {
		return vorwahl;
	}

	public void setVorwahl(String vorwahl) {
		this.vorwahl = vorwahl;
	}

	public String getEinwohner() {
		return einwohner;
	}

	public void setEinwohner(String einwohner) {
		this.einwohner = einwohner;
	}

	public String getFlaeche() {
		return flaeche;
	}

	public void setFlaeche(String flaeche) {
		this.flaeche = flaeche;
	}

	public String getKz() {
		return kz;
	}

	public void setKz(String kz) {
		this.kz = kz;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public static OpenGeoEntry fromCSVLine(String line, String separator) {
		String[] values = line.split(separator);

		if (values.length < 10) {
			return null;
		}

		String plz = values[7];
		if ("".equals(plz))
			return null;

		OpenGeoEntry entry = new OpenGeoEntry();
		entry.id = values[0].trim();
		entry.ags = values[1].trim();
		entry.ascii = values[2].trim();
		entry.name = values[3].trim();
		entry.lat = values[4].trim();
		entry.lon = values[5].trim();
		entry.amt = values[6].trim();
		entry.plz = plz.split(",");
		entry.vorwahl = values[8].trim();
		entry.einwohner = values[9].trim();
		entry.flaeche = values[10].trim();
		entry.kz = values[11].trim();
		entry.typ = values[12].trim();
		entry.level = values[13].trim();

		return entry;
	}
}
