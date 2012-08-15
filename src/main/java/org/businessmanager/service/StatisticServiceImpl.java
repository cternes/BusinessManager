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

import java.math.BigDecimal;
import java.util.Map;

import org.businessmanager.dao.StatisticDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private StatisticDao dao;
	
	@Override
	public Long getNumberOfContacts() {
		return dao.getNumberOfContacts();
	}

	@Override
	public Long getNumberOfInvoices() {
		return dao.getNumberOfInvoices();
	}

	@Override
	public Map<Integer, Long> getNumberOfInvoicesByMonth() {
		return dao.getNumberOfInvoicesByMonth();
	}

	@Override
	public Long getNumberOfUnpayedInvoices() {
		return dao.getNumberOfUnpayedInvoices();
	}

	@Override
	public Long getNumberOfPayedInvoices() {
		return dao.getNumberOfPayedInvoices();
	}

	@Override
	public BigDecimal getSumUnpayedInvoicesNet() {
		return dao.getSumUnpayedInvoicesNet();
	}

}