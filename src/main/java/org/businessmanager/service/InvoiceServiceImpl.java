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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.businessmanager.dao.InvoiceDao;
import org.businessmanager.domain.Activity;
import org.businessmanager.domain.Activity.ActivityType;
import org.businessmanager.domain.Invoice;
import org.businessmanager.domain.ModificationType;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.SpringSecurityService;
import org.businessmanager.web.bean.InvoiceActivityBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private SpringSecurityService securityService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private InvoiceNumberGenerator invoiceNumberGenerator;

	@Override
	public List<Invoice> getInvoices() {
		return invoiceDao.findAll();
	}

	@Override
	public Invoice saveInvoice(Invoice invoice) {
		Validate.notNull(invoice, "Parameter invoice must not be null!");
		
		if (invoice.getId() == null) {
			invoice.setInvoiceNumber(invoiceNumberGenerator.getNextInvoiceNumber());
			invoice = invoiceDao.save(invoice);
			saveActivity(invoice.getId(), ModificationType.CREATE, invoice.getInvoiceNumber());
			
			return invoice;
		} else {
			invoice = invoiceDao.update(invoice);
			saveActivity(invoice.getId(), ModificationType.UPDATE, invoice.getInvoiceNumber());
			
			return invoice;
		}
	}
	
	private void saveActivity(Long sourceId, ModificationType modType, Long invoiceNumber) {
		User currentUser = securityService.getLoggedInUser();
		
		if(currentUser != null) {
			InvoiceActivityBean activityData = new InvoiceActivityBean(currentUser.getUsername(), modType, invoiceNumber);
			
			Activity activity = new Activity(currentUser.getId(), ActivityType.INVOICE);
			activity.setSourceId(sourceId);
			
			activity.setData(activityData.toJson());
			activityService.saveActivity(activity);
		}
	}

	@Override
	public Invoice getInvoiceById(Long id) {
		return invoiceDao.findById(id);
	}

	private Map<String, Object> createReplacementsForDocGen(Invoice invoice) {
		// TODO: Validate invoice (required fields)
		
		Map<String, Object> invoiceData = new HashMap<String, Object>();
		invoiceData.put("is_company", "BM");
		invoiceData.put("is_name", "Doe");
		invoiceData.put("is_fname", "John");
		invoiceData.put("is_street", "Frankfurter Straße 9");
		invoiceData.put("is_zip", "62147");
		invoiceData.put("is_city", "Frankfurt");
		invoiceData.put("is_phone", "01234567890");
		invoiceData.put("is_fax", "01478523698");
		invoiceData.put("is_email", "bm@bm.org");
		invoiceData.put("is_url", "www.bm.org");

		invoiceData.put("ie_company", invoice.getContact().getCompany());
		invoiceData.put("ie_name", invoice.getContact().getLastname());
		invoiceData.put("ie_fname", invoice.getContact().getFirstname());
		// TODO: allow different representations of street/house number (e.g. Frankfurter Allee 5 und 302 Smith Ave.)
		invoiceData.put("ie_street", invoice.getContact().getDefaultBillingAddress().getStreet() + " " + invoice.getContact().getDefaultBillingAddress().getHousenumber());
		invoiceData.put("ie_zip", invoice.getContact().getDefaultBillingAddress().getZipCode());
		invoiceData.put("ie_city", invoice.getContact().getDefaultBillingAddress().getCity());

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		// TODO: invoice vat value
		invoiceData.put("i_date", sdf.format(invoice.getInvoiceDate()));
		invoiceData.put("i_number", invoice.getInvoiceNumber().toString());
		invoiceData.put("i_net_amount", invoice.getInvoiceAmountNet().toPlainString());
		invoiceData.put("i_vat_amount_1", "0000");
		invoiceData.put("i_gross_amount", invoice.getInvoiceAmountGross().toPlainString());
		
		// TODO: invoice positions
		return invoiceData;
	}
}
