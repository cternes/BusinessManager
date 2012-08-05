package org.businessmanager.web.controller.page.invoice;

import org.businessmanager.service.InvoiceService;
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
	
	private void fetchInvoices() {
		model.setEntityList(invoiceService.getInvoices());
	}
	
	public InvoiceModel getModel() {
		if(model.getEntityList() == null) {
			fetchInvoices();
		}
		return model;
	}
}
