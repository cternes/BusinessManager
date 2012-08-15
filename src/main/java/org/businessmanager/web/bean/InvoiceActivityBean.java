package org.businessmanager.web.bean;

import java.util.HashMap;
import java.util.Map;

import org.businessmanager.domain.ModificationType;
import org.businessmanager.util.JsonUtil;

public class InvoiceActivityBean extends AbstractActivityBean {

	private Long invoiceNumber;
	
	public InvoiceActivityBean() {
	}
	
	public InvoiceActivityBean(String username, ModificationType activity, Long invoiceNumber) {
		this.username = username;
		this.activity = activity;
		this.invoiceNumber = invoiceNumber;
	}

	@Override
	public String toJson() {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("username", username);
		dataMap.put("activity", activity.toString());
		dataMap.put("invoicenumber", invoiceNumber.toString());
		
		return JsonUtil.getInstance().writeJson(dataMap);
	}

	@Override
	public AbstractActivityBean fromJson(String json) {
		if(json != null) {
			Map<String, String> dataMap = JsonUtil.getInstance().readJson(json);
			if(dataMap != null) {
				username = dataMap.get("username");
				activity = ModificationType.valueOf(dataMap.get("activity"));
				invoiceNumber = Long.valueOf(dataMap.get("contactname"));
			}
		}
		
		return this;
	}
}
