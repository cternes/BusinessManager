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
package org.businessmanager.aspect.dao;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.businessmanager.domain.TrackModifications;
import org.businessmanager.domain.ModificationType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This advice processes all DAO methods.
 *
 */
@Component
@Aspect
public class TrackModificationsAdvise {

	@Around("execution(* org.businessmanager.dao.*.save*(..))")
	public Object trackModificationsOnCreate(ProceedingJoinPoint joinPoint) throws Throwable {
		writeModificationInfo(joinPoint, ModificationType.CREATE);
		
		return joinPoint.proceed();
	}

	@Around("execution(* org.businessmanager.dao.*.update*(..))")
	public Object trackModificationsOnUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		writeModificationInfo(joinPoint, ModificationType.UPDATE);
		
		return joinPoint.proceed();
	}
	
	@Around("execution(* org.businessmanager.dao.*.remove*(..))")
	public Object trackModificationsOnDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		writeModificationInfo(joinPoint, ModificationType.DELETE);
		
		return joinPoint.proceed();
	}
	
	private void writeModificationInfo(ProceedingJoinPoint joinPoint, ModificationType type) {
		TrackModifications entity = getEntity(joinPoint);
		if(entity != null) {
			entity.setModificationUser(getUsername());
			entity.setModificationDate(Calendar.getInstance());
			entity.setModificationType(type);
		}
	}
	
	private TrackModifications getEntity(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if(args != null && args.length == 1) {
			if(args[0] instanceof TrackModifications) {
				return (TrackModifications) args[0];
			}
		}
		return null;
	}
	
	private String getUsername() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return "unknown";
	}
}
