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

@MappedSuperclass
public abstract class AbstractEntity implements TrackModifications {

	@Column
	private String modificationUser;
	
	@Column
	private Calendar modificationDate;
	
	@Enumerated(EnumType.STRING)
	private ModificationType modificationType;
	
	@Column
	private Calendar validFrom;
	
	@Column
	private Calendar validUntil;

	@Override
	public void setModificationUser(String modificationUser) {
		this.modificationUser = modificationUser;
	}

	@Override
	public String getModificationUser() {
		return modificationUser;
	}

	@Override
	public void setModificationDate(Calendar modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public Calendar getModificationDate() {
		return modificationDate;
	}

	@Override
	public void setModificationType(ModificationType modificationType) {
		this.modificationType = modificationType;
	}

	@Override
	public ModificationType getModificationType() {
		return modificationType;
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
