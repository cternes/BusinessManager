package org.businessmanager.web.controller.page.invoice;

import org.businessmanager.web.bean.InvoiceBean;
import org.businessmanager.web.controller.AbstractController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("invoiceEditController")
@Scope("view")
public class InvoiceEditController extends AbstractController {

	private InvoiceBean bean = new InvoiceBean();

	public InvoiceBean getBean() {
		return bean;
	}

}
