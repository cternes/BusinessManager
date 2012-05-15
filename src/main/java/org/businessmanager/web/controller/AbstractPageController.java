package org.businessmanager.web.controller;

import javax.faces.application.FacesMessage.Severity;

import org.businessmanager.web.jsf.helper.FacesContextHelper;
import org.businessmanager.web.jsf.helper.NavigationHelper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractPageController {

	@Autowired
	protected NavigationHelper navigationHelper;
	
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
