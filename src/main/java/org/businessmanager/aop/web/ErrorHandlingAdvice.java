package org.businessmanager.aop.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.businessmanager.aop.annotation.ErrorHandled;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This AOP advise intercepts at all public methods that are annotation with the annotation
 * {@link ErrorHandled}. The advice will catch all occuring errors and displays a general error message to the user.
 * 
 * @author Christian Ternes
 *
 */ 
@Component
@Aspect
public class ErrorHandlingAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut(value="execution(public * *(..))")
	public void anyPublicMethod() {	
	}
	
	@Around("anyPublicMethod() && @annotation(errorHandled)")
	public Object logErrorAndPostMessage(ProceedingJoinPoint joinPoint, ErrorHandled errorHandled) throws Throwable {
		Object result = null;
		try	{
			result = joinPoint.proceed();
		}
		catch (Exception e) {
			displayErrorMessageToUser();
			logger.error("An unrecoverable error occured. Error message was displayed to user. Error was: ",e);
		}
		return result;
	}

	private void displayErrorMessageToUser() {
		if(FacesContext.getCurrentInstance() != null) {
			String anErrorMessage = ResourceBundleProducer.getString("general_error_occurred");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, anErrorMessage, anErrorMessage));
		}
	}
	
	
}
