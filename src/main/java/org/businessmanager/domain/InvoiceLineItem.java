package org.businessmanager.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class InvoiceLineItem extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private Integer posNo;
	
	@Column
	private String description;
	
	@Column
	private BigDecimal unitPrice;
	
	@Column
	private BigDecimal quantity;
	
	@Column
	private BigDecimal sumPrice;
	
	@Column
	private Boolean vatPercentage;
	
	@ManyToOne(targetEntity=Invoice.class)
	private Invoice invoice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPosNo() {
		return posNo;
	}

	public void setPosNo(Integer posNo) {
		this.posNo = posNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Boolean getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(Boolean vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((posNo == null) ? 0 : posNo.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result
				+ ((sumPrice == null) ? 0 : sumPrice.hashCode());
		result = prime * result
				+ ((unitPrice == null) ? 0 : unitPrice.hashCode());
		result = prime * result
				+ ((vatPercentage == null) ? 0 : vatPercentage.hashCode());
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
		InvoiceLineItem other = (InvoiceLineItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (posNo == null) {
			if (other.posNo != null)
				return false;
		} else if (!posNo.equals(other.posNo))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (sumPrice == null) {
			if (other.sumPrice != null)
				return false;
		} else if (!sumPrice.equals(other.sumPrice))
			return false;
		if (unitPrice == null) {
			if (other.unitPrice != null)
				return false;
		} else if (!unitPrice.equals(other.unitPrice))
			return false;
		if (vatPercentage == null) {
			if (other.vatPercentage != null)
				return false;
		} else if (!vatPercentage.equals(other.vatPercentage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceLineItem [id=" + id + ", posNo=" + posNo
				+ ", description=" + description + ", unitPrice=" + unitPrice
				+ ", quantity=" + quantity + ", sumPrice=" + sumPrice
				+ ", vatPercentage=" + vatPercentage + "]";
	}
}
