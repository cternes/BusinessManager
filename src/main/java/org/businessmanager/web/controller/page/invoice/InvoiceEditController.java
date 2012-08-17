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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.businessmanager.domain.Invoice;
import org.businessmanager.domain.InvoiceLineItem;
import org.businessmanager.domain.settings.ApplicationSetting.Group;
import org.businessmanager.service.InvoiceService;
import org.businessmanager.service.settings.ApplicationSettingsService;
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
	
	@Autowired
	private ApplicationSettingsService settingsService;
	
	private InvoiceBean bean = new InvoiceBean();
	private LineItemBean lineItemBean;
	private boolean showLineItemDialog;
	private String currency;
	
	@PostConstruct
	public void init() {
		lineItemBean = new LineItemBean(1, getDefaultVatPercentage());
		
		initDefaultCurrency();
	}

	private void initDefaultCurrency() {
		currency = settingsService.getApplicationSettingValue(Group.SYSTEM_PREFERENCES, ApplicationSettingsService.INVOICES_CURRENCY);
	}
	
	public InvoiceBean getBean() {
		return bean;
	}

	public LineItemBean getLineItemBean() {
		return lineItemBean;
	}

	public void addLineItem() {
		bean.getLineItems().add(lineItemBean);
		
		int pos = bean.getLineItems().size()+1;
		lineItemBean = new LineItemBean(pos, getDefaultVatPercentage());
	}
	
	/**
	 * Retrieves the default VAT percentage from the system preferences.
	 * 
	 * @return
	 */
	private BigDecimal getDefaultVatPercentage() {
		String value = settingsService.getApplicationSettingValue(Group.SYSTEM_PREFERENCES, ApplicationSettingsService.INVOICES_VATPERCENTAGE);
		if(value != null && !value.isEmpty()) {
			return new BigDecimal(value);
		}
		return null;
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
			updateLineItemsInInvoice(invoice);
			invoiceService.saveInvoice(invoice);
			
			addMessage(FacesMessage.SEVERITY_INFO, "editcontact_success_contact_saved");
			
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
		Long invoiceNumber = Long.valueOf(UUID.randomUUID().hashCode());
		Invoice invoice = new Invoice(invoiceNumber, DateUtil.convertDateToCalendar(bean.getInvoiceDate()));
		
		//TODO: map fields from bean to invoice
		return invoice;
	}
	
	private void updateLineItemsInInvoice(Invoice invoice) {
		invoice.getLineItems().clear();

		List<InvoiceLineItem> lineItemList = createLineItems();
		for (InvoiceLineItem lineItem : lineItemList) {
			lineItem.setInvoice(invoice);
			invoice.getLineItems().add(lineItem);
		}
	}
	
	private List<InvoiceLineItem> createLineItems() {
		List<InvoiceLineItem> invoiceLineItemList = new ArrayList<InvoiceLineItem>();
		List<LineItemBean> lineItemBeanList = bean.getLineItems();
		for (LineItemBean lineItem : lineItemBeanList) {
			InvoiceLineItem invoiceLineItem = lineItem.getInvoiceLineItem();
			invoiceLineItemList.add(invoiceLineItem);
		}
		
		return invoiceLineItemList;
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

	public String getCurrency() {
		return currency;
	}
	
	public BigDecimal getTotalGrossPrice() {
		BigDecimal totalGrossPrice = BigDecimal.ZERO; 
		for (LineItemBean lineItem : bean.getLineItems()) {
			BigDecimal sumPrice = lineItem.getSumPriceGross();
			totalGrossPrice = totalGrossPrice.add(sumPrice);
		}
		
		return totalGrossPrice;
	}
}
