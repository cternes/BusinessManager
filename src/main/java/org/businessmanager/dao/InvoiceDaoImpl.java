package org.businessmanager.dao;

import java.util.List;

import org.businessmanager.domain.Invoice;
import org.businessmanager.domain.Invoice_;
import org.businessmanager.domain.QInvoice;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

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

	@Override
	public Long getMaxInvoiceNumber() {
		JPAQuery query = new JPAQuery(getEntityManager());
		QInvoice invoice = QInvoice.invoice;
		return query.from(invoice).singleResult(invoice.invoiceNumber.max());
	}

}
