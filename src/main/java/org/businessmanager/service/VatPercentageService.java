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
package org.businessmanager.service;

import java.util.List;

import org.businessmanager.domain.VatPercentage;

public interface VatPercentageService {

	/**
	 * Retrieves all available {@link VatPercentage}s from the database.
	 * 
	 * @return a list of {@link VatPercentage}s
	 */
	public List<VatPercentage> getVatPercentages();
	
	/**
	 * Save a {@link VatPercentage} in the database.
	 * 
	 * @param vatPercentage the {@link VatPercentage} which should be saved
	 * @return the {@link VatPercentage} with set id
	 */
	public VatPercentage saveVatPercentage(VatPercentage vatPercentage);

	/**
	 * Removes a {@link VatPercentage} from the database.
	 * 
	 * @param selectedEntity the {@link VatPercentage} which should be removed
	 */
	public void deleteVatPercentage(VatPercentage selectedEntity);
}
