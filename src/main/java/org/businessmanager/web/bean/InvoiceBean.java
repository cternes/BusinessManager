package org.businessmanager.web.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
