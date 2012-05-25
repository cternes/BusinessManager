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
package org.businessmanager.web.controller;

import javax.faces.application.FacesMessage.Severity;

import org.businessmanager.web.jsf.helper.FacesContextHelper;
import org.businessmanager.web.jsf.helper.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
	protected NavigationManager navigationManager;
	
	@Autowired
	protected FacesContextHelper facesContext;

	public void addMessage(Severity theSeverity, String theMsgKey) {
		facesContext.addMessage(theSeverity, theMsgKey);
	}
	
	public void addExtendedMessage(Severity theSeverity, String theMsgKey, String additionalText) {
		facesContext.addExtendedMessage(theSeverity, theMsgKey, additionalText);
	}
	
	public void addErrorMessage(String theMsgKey) {
		facesContext.addErrorMessage(null, theMsgKey);
	}
	
	public void addErrorMessage(String theClientId, String theMsgKey) {
		facesContext.addErrorMessage(theClientId, theMsgKey);
		facesContext.addErrorMessage(null, theMsgKey);
	}
}
