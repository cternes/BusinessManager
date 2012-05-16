package org.businessmanager.web.controller.state;

import org.businessmanager.domain.Address;
import org.primefaces.model.SelectableDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("conversation.access")
public class AddressModel extends AbstractModel<Address> implements SelectableDataModel<Address>{

	@Override
	public Address getRowData(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRowKey(Address arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
