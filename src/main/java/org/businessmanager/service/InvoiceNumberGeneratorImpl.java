package org.businessmanager.service;

import org.businessmanager.dao.InvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceNumberGeneratorImpl implements InvoiceNumberGenerator {

	private static Long DEFAULT_INVOICE_NUMBER = 100000L;
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Override
	public synchronized Long getNextInvoiceNumber() {
		Long nextInvoiceNumber = invoiceDao.getMaxInvoiceNumber();
		if(nextInvoiceNumber == null) {
			return DEFAULT_INVOICE_NUMBER; //TODO: use application configuration property here
		}
		
		return nextInvoiceNumber + 1;
	}

}
