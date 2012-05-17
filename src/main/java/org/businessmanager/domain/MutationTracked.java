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
package org.businessmanager.domain;

import java.util.Calendar;

/**
 * This interface provides the tracking of mutation actions. 
 * Whenever a mutation action takes place, at least the setters of the properties:
 * mutationUser, mutationTime and mutationType 
 * should be called.
 * 
 * @author Christian Ternes
 *
 */
public interface MutationTracked {

	/**
	 * Sets the user who has made the mutation.
	 * 
	 * @param mutationUser
	 */
	public void setMutationUser(String mutationUser);

	/**
	 * Retrieves the user who has made the mutation.
	 * 
	 * @return
	 */
	public String getMutationUser();

	/**
	 * Sets the time of the mutation.
	 * 
	 * @param mutationTime
	 */
	public void setMutationTime(Calendar mutationTime);

	/**
	 * Retrieves the time of the mutation.
	 * 
	 * @return
	 */
	public Calendar getMutationTime();

	/**
	 * Sets the type of the mutation. 
	 * 
	 * @param mutationType
	 */
	public void setMutationType(MutationType mutationType);

	/**
	 * Retrieves the type of the mutation.
	 * 
	 * @return
	 */
	public MutationType getMutationType();

	/**
	 * Sets a time from which the mutation will be valid.
	 * 
	 * @param validFrom
	 */
	public void setValidFrom(Calendar validFrom);

	/**
	 * Retrieves the time from which the mutation will be valid.
	 * 
	 * @return
	 */
	public Calendar getValidFrom();

	/**
	 * Sets a time until the mutation will be valid.
	 * 
	 * @param validUntil
	 */
	public void setValidUntil(Calendar validUntil);

	/**
	 * Retrieves the time until the mutation is valid.
	 * 
	 * @return
	 */
	public Calendar getValidUntil();
}
