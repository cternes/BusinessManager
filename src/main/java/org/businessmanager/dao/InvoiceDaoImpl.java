package org.businessmanager.dao;

import java.util.List;

import org.businessmanager.domain.Invoice;
import org.businessmanager.domain.Invoice_;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceDaoImpl extends GenericDaoImpl<Invoice> implements
		InvoiceDao {

	@Override
	public List<Invoice> findAll() {
		return findAll(Invoice_.invoiceNumber, true);
	}

	@Override
	public Class<Invoice> getPersistenceClass() {
		return Invoice.class;
	}

}
