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
package org.businessmanager.aop.database;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.businessmanager.domain.MutationTracked;
import org.businessmanager.domain.MutationType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This AOP advise intercepts when a DAO method save or update will be executed.
 * The advise enriches the entity by adding mutation information like mutation type, mutation time etc. 
 * 
 * @author Christian Ternes
 *
 */
@Component
@Aspect
public class MutationTrackingAdvise {

	/**
	 * Inserts mutation information into the given entiy while storing an entity.
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.businessmanager.database.*.save*(..))")
	public Object insertMutationInformationOnCreate(ProceedingJoinPoint joinPoint) throws Throwable {
		insertMutationInformation(joinPoint, MutationType.CREATE);
		
		return joinPoint.proceed();
	}

	/**
	 * Inserts mutation information into the given entiy while updating an entity.
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.businessmanager.database.*.update*(..))")
	public Object insertMutationInformationOnUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		insertMutationInformation(joinPoint, MutationType.UPDATE);
		
		return joinPoint.proceed();
	}
	
	/**
	 * Inserts mutation information into the given entiy while deleting an entity.
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.businessmanager.database.*.remove*(..))")
	public Object insertMutationInformationOnDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		insertMutationInformation(joinPoint, MutationType.DELETE);
		
		return joinPoint.proceed();
	}
	
	private void insertMutationInformation(ProceedingJoinPoint joinPoint, MutationType mutationType) {
		MutationTracked trackedEntity = getMutationTrackedEntity(joinPoint);
		if(trackedEntity != null) {
			trackedEntity.setMutationType(mutationType);
			trackedEntity.setMutationTime(Calendar.getInstance());
			trackedEntity.setMutationUser(getUsername());
		}
	}
	
	private MutationTracked getMutationTrackedEntity(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if(args != null && args.length == 1) {
			if(args[0] instanceof MutationTracked) {
				return (MutationTracked) args[0];
			}
		}
		return null;
	}
	
	private String getUsername() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return "unknownUser";
	}
}
