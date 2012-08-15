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
package org.businessmanager.web.controller.page.invoice;

import org.businessmanager.domain.Invoice;
import org.businessmanager.service.InvoiceService;
import org.businessmanager.util.DateUtil;
import org.businessmanager.web.bean.InvoiceBean;
import org.businessmanager.web.bean.LineItemBean;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("invoiceEditController")
@Scope("view")
public class InvoiceEditController extends AbstractController {

	@Autowired
	private InvoiceModel model;
	
	@Autowired
	private InvoiceService invoiceService;
	
	private InvoiceBean bean = new InvoiceBean();
	private LineItemBean lineItemBean = new LineItemBean(1);
	private boolean showLineItemDialog;
	
	public InvoiceBean getBean() {
		return bean;
	}

	public LineItemBean getLineItemBean() {
		return lineItemBean;
	}

	public void addLineItem() {
		bean.getLineItems().add(lineItemBean);
		
		int pos = bean.getLineItems().size()+1;
		lineItemBean = new LineItemBean(pos);
	}

	public boolean getShowLineItemDialog() {
		return showLineItemDialog;
	}
	
	public void closeLineItemDialog() {
		showLineItemDialog = false;
	}
	
	public void displayLineItemDialog() {
		showLineItemDialog = true;
	}
	
	public String saveInvoice() {
		if (validateInvoice()) {
			Invoice invoice = createInvoice();
			invoiceService.saveInvoice(invoice);
			
			model.refresh();
			return navigateBack();
		}
		return "#";
	}
	
	private boolean validateInvoice() {
		// TODO validate invoice here
		return true;
	}

	private Invoice createInvoice() {
		//TODO: create unique invoice number	
		Invoice invoice = new Invoice(1L, DateUtil.convertDateToCalendar(bean.getInvoiceDate()));
		
		//TODO: map fields from bean to invoice
		return invoice;
	}
	
	public void saveLineItem() {
		if(validateLineItem()) {
			addLineItem();
			
			//hide dialog
			closeLineItemDialog();
		}
	}

	private boolean validateLineItem() {
		// TODO add line item validation here
		return true;
	}
	
	public String navigateBack() {
		String backUrl = model.getBackUrl();
		if (backUrl != null) {
			model.setBackUrl(null); // clear back url
		}
		
		return navigationManager.getInvoicemanagement();
	}
}
