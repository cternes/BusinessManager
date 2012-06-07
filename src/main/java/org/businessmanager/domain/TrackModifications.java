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

public interface TrackModifications {

	public void setModificationUser(String user);

	public String getModificationUser();

	public void setModificationDate(Calendar modificationDate);

	public Calendar getModificationDate();

	public void setModificationType(ModificationType mutationType);

	public ModificationType getModificationType();

	public void setValidFrom(Calendar validFrom);

	public Calendar getValidFrom();

	public void setValidUntil(Calendar validUntil);

	public Calendar getValidUntil();
}
