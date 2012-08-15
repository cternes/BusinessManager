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

	@Override
	public List<Invoice> getInvoices() {
		return invoiceDao.findAll();
	}

	@Override
	public Invoice saveInvoice(Invoice invoice) {
		Validate.notNull(invoice, "Parameter invoice must not be null!");
		
		if (invoice.getId() == null) {
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
		InvoiceActivityBean activityData = new InvoiceActivityBean(currentUser.getUsername(), modType, invoiceNumber);
		
		Activity activity = new Activity(currentUser.getId(), ActivityType.INVOICE);
		activity.setSourceId(sourceId);
		
		activity.setData(activityData.toJson());
		activityService.saveActivity(activity);
	}
	
}
