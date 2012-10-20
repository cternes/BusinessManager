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
package org.businessmanager.web.controller.page.admin;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.businessmanager.domain.VatPercentage;
import org.businessmanager.service.VatPercentageService;
import org.businessmanager.web.controller.AbstractDialogController;
import org.businessmanager.web.controller.model.VatPercentageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("vatController")
@Scope("request")
public class VatController extends AbstractDialogController {

	@Autowired
	private VatPercentageService vatService;
	
	@Autowired
	private VatPercentageModel model;
	
	private BigDecimal vatPercentage;
	
	@PostConstruct
	public void init() {
		//initialize
	}
	
	public VatPercentageModel getModel() {
		if(model.getEntityList() == null){
			retrieveVatPercentages();
		}
		return model;
	}

	private void retrieveVatPercentages() {
		model.setEntityList(vatService.getVatPercentages());
	}
	
	public void save() {
		VatPercentage percentage = null;
		if(model.getSelectedEntity() == null) {
			percentage = new VatPercentage(vatPercentage);
		}
		else {
			percentage = model.getSelectedEntity();
			percentage.setPercentage(vatPercentage);
		}
		vatService.saveVatPercentage(percentage);
		
		retrieveVatPercentages();
		closeDialog();
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
	public void deleteVat() {
		if(model.getSelectedEntity() != null) {
			vatService.deleteVatPercentage(model.getSelectedEntity());
			retrieveVatPercentages();
		}
	}
	
	@Override
	public void showAddDialog() {
		vatPercentage = BigDecimal.ZERO;
		model.setSelectedEntity(null);
		super.showAddDialog();
	}
	
	@Override
	public void showUpdateDialog() {
		vatPercentage = model.getSelectedEntity().getPercentage();
		super.showUpdateDialog();
	}
	
}
