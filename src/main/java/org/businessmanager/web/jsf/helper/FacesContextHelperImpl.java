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
package org.businessmanager.web.jsf.helper;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

/**
 * @author Christian Ternes
 * 
 */
@Component
public class FacesContextHelperImpl implements FacesContextHelper {

	@Override
	public FacesContext getCurrentFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Override
	public String getSessionParam(String key) {
		return (String) getCurrentFacesContext().getExternalContext()
				.getSessionMap().get(key);
	}

	@Override
	public void setSessionParam(String key, String value) {
		getCurrentFacesContext().getExternalContext().getSessionMap()
				.put(key, value);
	}

	@Override
	public Locale getLocale() {
		return getCurrentFacesContext().getViewRoot().getLocale();
	}

	@Override
	public void addMessage(Severity theSeverity, String theMsgKey) {
		addMessage(null, theSeverity, theMsgKey);
	}

	@Override
	public void addMessage(String clientId, Severity severity, String msgKey) {
		getCurrentFacesContext().addMessage(
				clientId,
				new FacesMessage(severity, ResourceBundleProducer
						.getString(msgKey), ResourceBundleProducer
						.getString(msgKey)));
	}

	@Override
	public void addErrorMessage(String msgKey) {
		addMessage(FacesMessage.SEVERITY_ERROR, msgKey);
	}

	@Override
	public void addErrorMessage(String theClientId, String theMsgKey) {
		addMessage(theClientId, FacesMessage.SEVERITY_ERROR, theMsgKey);
	}

	@Override
	public String getRequestParam(String key) {
		return getCurrentFacesContext().getExternalContext()
				.getRequestParameterMap().get(key);
	}

	@Override
	public void setRequestParam(String key, String value) {
		getCurrentFacesContext().getExternalContext().getRequestParameterMap()
				.put(key, value);
	}

	@Override
	public void addExtendedMessage(Severity severity, String msgKey,
			String additionalInfo) {
		getCurrentFacesContext().addMessage(
				null,
				new FacesMessage(severity, ResourceBundleProducer
						.getString(msgKey) + " " + additionalInfo,
						ResourceBundleProducer.getString(msgKey) + " "
								+ additionalInfo));

	}
	
	@Override
	public void setLocale(Locale locale) {
		getCurrentFacesContext().getViewRoot().setLocale(locale);
		
		String aViewId = getCurrentFacesContext().getViewRoot().getViewId();
		ViewHandler aHandler = getCurrentFacesContext().getApplication().getViewHandler();
		UIViewRoot aRoot = aHandler.createView(getCurrentFacesContext(), aViewId);
		aRoot.setLocale(locale);
		aRoot.setViewId(aViewId);
		getCurrentFacesContext().setViewRoot(aRoot);
	}

}
