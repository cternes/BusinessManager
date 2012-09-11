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

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonUtil {

	private static JsonUtil INSTANCE = new JsonUtil();
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ObjectMapper mapper = new ObjectMapper(); 
	
	private JsonUtil() {
	}
	
	public static JsonUtil getInstance() {
		return INSTANCE;
	}
	
	public String writeJson(Map<String, String> dataMap) {
		StringWriter stringWriter = new StringWriter();
		try {
			mapper.writeValue(stringWriter, dataMap);
			return stringWriter.getBuffer().toString();
		} catch (JsonGenerationException e) {
			logger.error("Could not create json string. Error was: ", e);
		} catch (JsonMappingException e) {
			logger.error("Could not create json mapping. Error was: ", e);
		} catch (IOException e) {
			logger.error("Could not write json string. Error was: ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> readJson(String json) {
		try {
			return mapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			logger.error("Could not parse json string. Error was: ", e);
		} catch (JsonMappingException e) {
			logger.error("Could not create json mapping. Error was: ", e);
		} catch (IOException e) {
			logger.error("Could not read json string. Error was: ", e);
		}
		return null;
	}
}
