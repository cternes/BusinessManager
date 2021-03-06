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
package org.businessmanager.domain;

import org.businessmanager.i18n.ResourceBundleAccessor;

/**
 * This enum represents the available modification types.
 * 
 * @author Christian Ternes
 *
 */
public enum ModificationType {

	CREATE("modtype_create")
	, UPDATE("modtype_update")
	, DELETE("modtype_delete");
	
	private String label;
	
	private ModificationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return ResourceBundleAccessor.getString(label);
	}
}
