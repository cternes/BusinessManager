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

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.EntityManager;

import org.businessmanager.domain.QContact;
import org.businessmanager.domain.QInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class StatisticDaoImpl implements StatisticDao {

	@Autowired
	@Qualifier("filteredEntityManager")
	private EntityManager entityManager;

	@Override
	public Long getNumberOfContacts() {
		JPAQuery query = new JPAQuery(entityManager);
		QContact contact = QContact.contact;
		return query.from(contact).count();
	}

	@Override
	public Long getNumberOfInvoices() {
		JPAQuery query = new JPAQuery(entityManager);
		QInvoice invoice = QInvoice.invoice;
		return query.from(invoice).count();
	}

	@Override
	public Map<Integer, Long> getNumberOfInvoicesByMonth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long getNumberOfUnpayedInvoices() {
		JPAQuery query = new JPAQuery(entityManager);
		QInvoice invoice = QInvoice.invoice;
		return query.from(invoice).where(invoice.paid.isFalse()).count();
	}

	@Override
	public Long getNumberOfPayedInvoices() {
		JPAQuery query = new JPAQuery(entityManager);
		QInvoice invoice = QInvoice.invoice;
		return query.from(invoice).where(invoice.paid.isTrue()).count();
	}

	@Override
	public BigDecimal getSumUnpayedInvoicesNet() {
//		JPAQuery query = new JPAQuery(entityManager);
//		QInvoice invoice = QInvoice.invoice;
//		List<BigDecimal> list = query.from(invoice).list(invoice.invoiceAmountNet.sum());
		throw new UnsupportedOperationException();
	}
	
	
}
