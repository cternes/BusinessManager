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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.businessmanager.domain.Contact;
import org.businessmanager.domain.Invoice;
import org.businessmanager.domain.InvoiceLineItem;
import org.businessmanager.domain.VatPercentage;
import org.businessmanager.domain.settings.ApplicationSetting.Group;
import org.businessmanager.service.ContactService;
import org.businessmanager.service.InvoiceService;
import org.businessmanager.service.VatPercentageService;
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
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private VatPercentageService vatService;
	
	private InvoiceBean bean = new InvoiceBean();
	private LineItemBean lineItemBean;
	private boolean showLineItemDialog;
	private String currency;
	private LineItemBean selectedLineItemBean;
	private boolean isLineItemEditMode = false;
	private boolean showSearchContactDialog;
	private List<Contact> contactSearchResults = new ArrayList<Contact>();
	private String contactSearchString;
	
	@PostConstruct
	public void init() {
		reattachInvoice();
		initInvoice();
		initLineItemBean();
		initDefaultCurrency();
	}

	private void initLineItemBean() {
		if(model.getSelectedEntity() == null) {
			lineItemBean = new LineItemBean(1, getDefaultVatPercentage());
		}
		else {
			lineItemBean = new LineItemBean(getNextLineItemPosition(), getDefaultVatPercentage());
		}
		
	}

	private void reattachInvoice() {
		// reattaching invoice to avoid LazyInitializationException
		if(model.getSelectedEntity() != null) {
			model.setSelectedEntity(invoiceService.getInvoiceById(model.getSelectedEntity().getId()));
		}
	}

	private void initInvoice() {
		Invoice selectedInvoice = model.getSelectedEntity();
		
		if (selectedInvoice != null) {
			bean.copyDataFromInvoice(selectedInvoice);
		}
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

	public void addLineItemToList() {
		if(!isLineItemEditMode) { //add new bean
			bean.getLineItems().add(lineItemBean);
		}
		
		lineItemBean = new LineItemBean(getNextLineItemPosition(), getDefaultVatPercentage());
	}
	
	private int getNextLineItemPosition() {
		return bean.getLineItems().size()+1;
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
			if(model.getSelectedEntity() != null) {
				invoice = model.getSelectedEntity();
			}
			
			fillInvoice(invoice);
			updateLineItemsInInvoice(invoice);
			invoiceService.saveInvoice(invoice);
			
			addMessage(FacesMessage.SEVERITY_INFO, "editinvoice_success_invoice_saved");
			
			model.refresh();
			return navigateBack();
		}
		return "#";
	}
	
	private void fillInvoice(Invoice invoice) {
		invoice.setInvoiceDate(DateUtil.convertDateToCalendar(bean.getInvoiceDate()));
		invoice.setInvoiceAmountGross(getTotalGrossPrice());
		invoice.setInvoiceAmountNet(getTotalNetPrice());
	}

	private boolean validateInvoice() {
		// TODO validate invoice here
		return true;
	}

	private Invoice createInvoice() {
		Invoice invoice = new Invoice(DateUtil.convertDateToCalendar(bean.getInvoiceDate()));
		
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
			addLineItemToList();
			
			//hide dialog
			closeLineItemDialog();
		}
	}

	private boolean validateLineItem() {
		// TODO fix issue with validation messages here
		boolean isValid = true;
		
		if(lineItemBean.getDescription() == null || lineItemBean.getDescription().isEmpty()) {
			addErrorMessage("desc", "editinvoice_error_no_description");
			isValid = false;
		}
		
		if(lineItemBean.getVatPercentage() != null) {
			if(lineItemBean.getVatPercentage().compareTo(new BigDecimal(100)) == 1) {
				addErrorMessage("vat", "editinvoice_error_too_high_vat");
				isValid = false;
			}
		}
		
		return isValid;
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
		
		return totalGrossPrice.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getTotalNetPrice() {
		BigDecimal totalNetPrice = BigDecimal.ZERO; 
		for (LineItemBean lineItem : bean.getLineItems()) {
			BigDecimal sumPrice = lineItem.getSumPriceNet();
			totalNetPrice = totalNetPrice.add(sumPrice);
		}
		
		return totalNetPrice.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getTotalVatAmount() {
		BigDecimal totalVatAmount = BigDecimal.ZERO; 
		for (LineItemBean lineItem : bean.getLineItems()) {
			BigDecimal amount = lineItem.getVatAmount();
			totalVatAmount = totalVatAmount.add(amount);
		}
		
		return totalVatAmount.setScale(2, RoundingMode.HALF_UP);
	}

	public LineItemBean getSelectedLineItemBean() {
		return selectedLineItemBean;
	}

	public void setSelectedLineItemBean(LineItemBean selectedLineItemBean) {
		this.selectedLineItemBean = selectedLineItemBean;
	}
	
	public void editLineItem() {
		lineItemBean = selectedLineItemBean;
		
		displayLineItemDialog();
		
		isLineItemEditMode = true;
	}

	public void removeLineItem() {
		if(selectedLineItemBean != null) {
			bean.getLineItems().remove(selectedLineItemBean);
		}
	}
	
	public void addLineItem() {
		isLineItemEditMode = false;
		
		initLineItemBean();
		
		displayLineItemDialog();
	}

	public boolean getShowSearchContactDialog() {
		return showSearchContactDialog;
	}

	public void displaySearchContactDialog() {
		showSearchContactDialog = true;
	}
	
	public void closeSearchContactDialog() {
		showSearchContactDialog = false;
	}
	
	public void searchContact() {
		System.out.println("---------------SEARCH----------------------");
		//FIXME: method never gets called, WHY??
		contactSearchResults = contactService.fullTextSearchContact(contactSearchString);
	}

	public List<Contact> getContactSearchResults() {
		return contactSearchResults;
	}

	public String getContactSearchString() {
		return contactSearchString;
	}

	public void setContactSearchString(String contactSearchString) {
		this.contactSearchString = contactSearchString;
	}
	
	public List<BigDecimal> getAvailableVatPercentages() {
		List<VatPercentage> vatPercentages = vatService.getVatPercentages();
		List<BigDecimal> resultList = new ArrayList<BigDecimal>();
		for (VatPercentage vatPercentage : vatPercentages) {
			resultList.add(vatPercentage.getPercentage());
		}
		return resultList;
	}

}
