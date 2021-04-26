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
package org.businessmanager.dao;

import java.util.List;

import org.businessmanager.domain.VatPercentage;
import org.businessmanager.domain.VatPercentage_;
import org.springframework.stereotype.Repository;

@Repository
public class VatPercentageDaoImpl extends GenericDaoImpl<VatPercentage> implements VatPercentageDao {

	@Override
	public List<VatPercentage> findAll() {
		return findAll(VatPercentage_.percentage, true);
	}

	@Override
	public Class<VatPercentage> getPersistenceClass() {
		return VatPercentage.class;
	}

}