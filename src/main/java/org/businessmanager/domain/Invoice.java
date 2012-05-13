package org.businessmanager.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Invoice extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long invoiceNumber;

	@Column
	private String invoiceNumberPresentation;

	@Column
	private Date invoiceDate;

	@Column
	private BigDecimal incoiceAmountNet;

	@Column
	private BigDecimal invoiceAmountGross;

	@Column
	private Boolean paid;

	@Column
	private Boolean itemsAreGrossValue;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
	private List<InvoicePayment> payments = new ArrayList<InvoicePayment>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
	private List<InvoiceLineItem> lineItems = new ArrayList<InvoiceLineItem>();

	@ManyToOne(targetEntity=Contact.class)
	private Contact contact;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceNumberPresentation() {
		return invoiceNumberPresentation;
	}

	public void setInvoiceNumberPresentation(String invoiceNumberPresentation) {
		this.invoiceNumberPresentation = invoiceNumberPresentation;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getIncoiceAmountNet() {
		return incoiceAmountNet;
	}

	public void setIncoiceAmountNet(BigDecimal incoiceAmountNet) {
		this.incoiceAmountNet = incoiceAmountNet;
	}

	public BigDecimal getInvoiceAmountGross() {
		return invoiceAmountGross;
	}

	public void setInvoiceAmountGross(BigDecimal invoiceAmountGross) {
		this.invoiceAmountGross = invoiceAmountGross;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Boolean getItemsAreGrossValue() {
		return itemsAreGrossValue;
	}

	public void setItemsAreGrossValue(Boolean itemsAreGrossValue) {
		this.itemsAreGrossValue = itemsAreGrossValue;
	}

	public List<InvoicePayment> getPayments() {
		return payments;
	}

	public void setPayments(List<InvoicePayment> payments) {
		this.payments = payments;
	}

	public List<InvoiceLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<InvoiceLineItem> lineItems) {
		this.lineItems = lineItems;
	}
	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((incoiceAmountNet == null) ? 0 : incoiceAmountNet.hashCode());
		result = prime
				* result
				+ ((invoiceAmountGross == null) ? 0 : invoiceAmountGross
						.hashCode());
		result = prime * result
				+ ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result
				+ ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime
				* result
				+ ((invoiceNumberPresentation == null) ? 0
						: invoiceNumberPresentation.hashCode());
		result = prime
				* result
				+ ((itemsAreGrossValue == null) ? 0 : itemsAreGrossValue
						.hashCode());
		result = prime * result + ((paid == null) ? 0 : paid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (incoiceAmountNet == null) {
			if (other.incoiceAmountNet != null)
				return false;
		} else if (!incoiceAmountNet.equals(other.incoiceAmountNet))
			return false;
		if (invoiceAmountGross == null) {
			if (other.invoiceAmountGross != null)
				return false;
		} else if (!invoiceAmountGross.equals(other.invoiceAmountGross))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (invoiceNumberPresentation == null) {
			if (other.invoiceNumberPresentation != null)
				return false;
		} else if (!invoiceNumberPresentation
				.equals(other.invoiceNumberPresentation))
			return false;
		if (itemsAreGrossValue == null) {
			if (other.itemsAreGrossValue != null)
				return false;
		} else if (!itemsAreGrossValue.equals(other.itemsAreGrossValue))
			return false;
		if (paid == null) {
			if (other.paid != null)
				return false;
		} else if (!paid.equals(other.paid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", invoiceNumber=" + invoiceNumber
				+ ", invoiceNumberPresentation=" + invoiceNumberPresentation
				+ ", invoiceDate=" + invoiceDate + ", incoiceAmountNet="
				+ incoiceAmountNet + ", invoiceAmountGross="
				+ invoiceAmountGross + ", paid=" + paid
				+ ", itemsAreGrossValue=" + itemsAreGrossValue + ", payments="
				+ payments + ", lineItems=" + lineItems + "]";
	}
}