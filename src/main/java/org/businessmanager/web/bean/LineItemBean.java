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
package org.businessmanager.web.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.businessmanager.domain.InvoiceLineItem;

public class LineItemBean {

	private Integer posNo;
	
	private String description;
	
	private BigDecimal unitPrice;
	
	private BigDecimal quantity = BigDecimal.ONE;
	
	private BigDecimal vatPercentage;
	
	public LineItemBean(int posNo, BigDecimal vatPercentage) {
		this.posNo = posNo;
		this.vatPercentage = vatPercentage;
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
	
	public BigDecimal getSumPriceNet() {
		if(unitPrice != null) {
			return quantity.multiply(unitPrice);
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getSumPriceGross() {
		if(unitPrice != null) {
			BigDecimal sumNet = quantity.multiply(unitPrice);
			return sumNet.add(getVatAmount());
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public BigDecimal getVatAmount() {
		if(unitPrice != null) {
			BigDecimal vatAmountUnit = unitPrice.multiply(vatPercentage.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
			return vatAmountUnit.multiply(quantity);
		}
		return BigDecimal.ZERO;
	}
	
	public InvoiceLineItem getInvoiceLineItem() {
		InvoiceLineItem lineItem = new InvoiceLineItem();
		lineItem.setDescription(getDescription());
		lineItem.setPosNo(getPosNo());
		lineItem.setQuantity(getQuantity());
		lineItem.setSumPriceGross(getSumPriceGross());
		lineItem.setSumPriceNet(getSumPriceNet());
		lineItem.setUnitPrice(getUnitPrice());
		lineItem.setVatPercentage(getVatPercentage());
		
		return lineItem;
	}
	
}
