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
package org.businessmanager.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;
import org.businessmanager.domain.security.Group;
import org.businessmanager.service.security.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("groupConverter")
public class GroupConverter implements Converter {

	@Autowired
	private GroupService groupService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		
		Long id = Long.parseLong(value);
		return groupService.getGroupById(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}
		if(value instanceof Group) {
			return ((Group) value).getDisplayName();
		}
		else {
            return value.toString();
        }
	}

}
