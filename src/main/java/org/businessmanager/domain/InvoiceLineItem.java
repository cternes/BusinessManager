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
	private BigDecimal sumPriceNet;
	
	@Column
	private BigDecimal sumPriceGross;
	
	@Column
	private BigDecimal vatPercentage;
	
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

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public BigDecimal getSumPriceNet() {
		return sumPriceNet;
	}

	public void setSumPriceNet(BigDecimal sumPriceNet) {
		this.sumPriceNet = sumPriceNet;
	}
	
	public void setSumPriceGross(BigDecimal sumPriceGross) {
		this.sumPriceGross = sumPriceGross;
	}

	public BigDecimal getSumPriceGross() {
		return sumPriceGross;
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
				+ ((sumPriceNet == null) ? 0 : sumPriceNet.hashCode());
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
		if (sumPriceNet == null) {
			if (other.sumPriceNet != null)
				return false;
		} else if (!sumPriceNet.equals(other.sumPriceNet))
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
				+ ", quantity=" + quantity + ", sumPrice=" + sumPriceNet
				+ ", vatPercentage=" + vatPercentage + "]";
	}

}
