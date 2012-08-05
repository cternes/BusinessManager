package org.businessmanager.service;

import java.util.List;

import org.businessmanager.dao.InvoiceDao;
import org.businessmanager.domain.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;

	@Override
	public List<Invoice> getInvoices() {
		return invoiceDao.findAll();
	}
	
	
}
