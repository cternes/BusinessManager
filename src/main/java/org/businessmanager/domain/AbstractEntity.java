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

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class provides common properties for entities like mutationUser and mutationTime.
 * <p/>
 * All entity classes should extend this class. 
 * 
 * @author Christian Ternes
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements MutationTracked {

	@Column
	private String mutationUser;
	
	@Column
	private Calendar mutationTime;
	
	@Enumerated(EnumType.STRING)
	private MutationType mutationType;
	
	@Column
	private Calendar validFrom;
	
	@Column
	private Calendar validUntil;

	@Override
	public void setMutationUser(String mutationUser) {
		this.mutationUser = mutationUser;
	}

	@Override
	public String getMutationUser() {
		return mutationUser;
	}

	@Override
	public void setMutationTime(Calendar mutationTime) {
		this.mutationTime = mutationTime;
	}

	@Override
	public Calendar getMutationTime() {
		return mutationTime;
	}

	@Override
	public void setMutationType(MutationType mutationType) {
		this.mutationType = mutationType;
	}

	@Override
	public MutationType getMutationType() {
		return mutationType;
	}

	@Override
	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	@Override
	public Calendar getValidFrom() {
		return validFrom;
	}

	@Override
	public void setValidUntil(Calendar validUntil) {
		this.validUntil = validUntil;
	}

	@Override
	public Calendar getValidUntil() {
		return validUntil;
	}
	
}
