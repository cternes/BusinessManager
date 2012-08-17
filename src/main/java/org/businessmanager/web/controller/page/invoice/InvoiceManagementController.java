package org.businessmanager.web.controller.page.invoice;

import javax.annotation.PostConstruct;

import org.businessmanager.domain.settings.ApplicationSetting.Group;
import org.businessmanager.service.InvoiceService;
import org.businessmanager.service.settings.ApplicationSettingsService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("invoiceManagementController")
@Scope("request")
public class InvoiceManagementController extends AbstractController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceModel model;
	
	@Autowired
	private ApplicationSettingsService settingsService;

	private String currency;
	
	@PostConstruct
	public void init() {
		initDefaultCurrency();
	}
	
	private void initDefaultCurrency() {
		currency = settingsService.getApplicationSettingValue(Group.SYSTEM_PREFERENCES, ApplicationSettingsService.INVOICES_CURRENCY);
	}

	private void fetchInvoices() {
		model.setEntityList(invoiceService.getInvoices());
	}
	
	public InvoiceModel getModel() {
		if(model.getEntityList() == null) {
			fetchInvoices();
		}
		return model;
	}
	
	public String navigateToEditInvoice() {
		return navigationManager.getInvoiceEdit();
	}

	public String getCurrency() {
		return currency;
	}

}
