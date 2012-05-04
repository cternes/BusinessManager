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
