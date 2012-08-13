package org.businessmanager.web.controller.page.invoice;

import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("invoiceSidebarController")
@Scope("request")
public class InvoiceSidebarController extends AbstractController {

	@Autowired
	private InvoiceModel model;
	
	public String navigateToEditInvoice() {
		model.setSelectedEntity(null);
		return navigationManager.getInvoiceEdit();
	}
	
	public String navigateToAllInvoices() {
		return navigationManager.getInvoicemanagement();
	}
}
