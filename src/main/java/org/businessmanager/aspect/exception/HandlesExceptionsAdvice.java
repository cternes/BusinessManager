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
package org.businessmanager.aspect.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.businessmanager.annotation.HandlesExceptions;
import org.businessmanager.i18n.ResourceBundleAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This advice processes methods that are marked with the {@link HandlesExceptions} annotation.
 * <p>
 * If an error occurs a generic jsf message will be shown to the user.
 * </p>
 *
 */ 
@Component
@Aspect
public class HandlesExceptionsAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut(value="execution(public * *(..))")
	public void anyPublicMethod() {	
	}
	
	@Around("anyPublicMethod() && @annotation(handlesExceptions)")
	public Object handleError(ProceedingJoinPoint joinPoint, HandlesExceptions handlesExceptions) throws Throwable {
		Object result = null;
		try	{
			result = joinPoint.proceed();
		}
		catch (Exception e) {
			showErrorMessage();
			logger.error("An unexpected error occurred. Error was: ",e);
		}
		return result;
	}

	private void showErrorMessage() {
		if(FacesContext.getCurrentInstance() != null) {
			String errorMsg = ResourceBundleAccessor.getString("general_error_occurred");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, errorMsg));
		}
	}
	
}
