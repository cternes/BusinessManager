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

import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * A helper class to encapsulate the {@link FacesContext} class. This is only needed to enable unit testing with mock objects.
 *
 * @author Christian Ternes
 * 
 */
public interface FacesContextHelper {
	
	/**
	 * Retrieves the current {@link FacesContext}.
	 * 
	 * @return the current {@link FacesContext}
	 */
	public FacesContext getCurrentFacesContext();
	
	/**
	 * Retrieves a parameter from the current session map.
	 * 
	 * @param key the parameter key to search for
	 * @return the value of the parameter if found, null otherwise
	 */
	public String getSessionParam(String key);
	
	/**
	 * Sets a parameter value in the current session map.
	 * If there is already a parameter with the same key, it will be overridden.  
	 * 
	 * @param key the key of the parameter to set
	 * @param value the value of the parameter
	 */
	public void setSessionParam(String key, String value);
	
	/**
	 * Retrieves a parameter from the current request map.
	 * 
	 * @param key the parameter key to search for
	 * @return the value of the parameter if found, null otherwise
	 */
	public String getRequestParam(String key);
	
	/**
	 * Sets a parameter value in the current request map.
	 * If there is already a parameter with the same key, it will be overridden.  
	 * 
	 * @param key theKey the key of the parameter to set
	 * @param value the value of the parameter
	 */
	public void setRequestParam(String key, String value);
	
	/**
	 * Retrieves the locale from the current session. 
	 * 
	 * @return the current locale from the session
	 */
	public Locale getLocale();
	
	/**
	 * Passes a global facesMessage to the ui. The given message key will be translated into the users
	 * locale. 
	 * 
	 * @param severity the severity of the message
	 * @param msgKey the untranslated message key of the message
	 */
	public void addMessage(Severity severity, String msgKey);
	
	/**
	 * Passes a global facesMessage to the ui. The given message key will be translated into the users
	 * locale.
	 * 
	 * @param severity the severity of the message
	 * @param msgKey the untranslated message key of the message
	 * @param additionalInfo untranslated message containing additional info (e.g. user inputs)
	 */
	public void addExtendedMessage(Severity severity, String msgKey, String additionalInfo);
	
	
	/**
	 * Passes a non-global facesMessage to the ui. 
	 * The message will only be shown if there is a <pre>h:message</pre> or <pre>p:message</pre> tag for the given client id. 
	 * 
	 * @param clientId the client id of a component
	 * @param severity the severity of the message.
	 * @param msgKey the untranslated message key of the message
	 */
	public void addMessage(String clientId, Severity severity, String msgKey);
	
	/**
	 * Passes a global facesMessage to the ui. The given message key will be translated into the users
	 * locale. 
	 * The severity will be Error.
	 * 
	 * @param msgKey the untranslated message key of the message
	 */
	public void addErrorMessage(String msgKey);

	/**
	 * Passes a non-global facesMessage to the ui. 
	 * The message will only be shown if there is a <pre>h:message</pre> or <pre>p:message</pre> tag for the given client id. 
	 * The severity will be Error.
	 * 
	 * @param clientId the client id of a component
	 * @param msgKey the untranslated message key of the message
	 */
	public void addErrorMessage(String clientId, String msgKey);
}
