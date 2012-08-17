package org.businessmanager.web.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.businessmanager.domain.Invoice;
import org.businessmanager.domain.InvoiceLineItem;

public class InvoiceBean {

	private Long invoiceNumber;
	private Date invoiceDate = new Date();
	private List<LineItemBean> lineItems = new ArrayList<LineItemBean>();

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public List<LineItemBean> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItemBean> lineItems) {
		this.lineItems = lineItems;
	}

	public void copyDataFromInvoice(Invoice invoice) {
		if(invoice != null) {
			if(invoice.getInvoiceDate() != null) {
				setInvoiceDate(invoice.getInvoiceDate().getTime());
			}
			
			setInvoiceNumber(invoice.getInvoiceNumber());
			
			if(invoice.getLineItems() != null) {
				for (InvoiceLineItem lineItem : invoice.getLineItems()) {
					LineItemBean lineItemBean = new LineItemBean();
					lineItemBean.copyDataFromLineItem(lineItem);
					
					getLineItems().add(lineItemBean);
				}
			}
		}
	}
}
