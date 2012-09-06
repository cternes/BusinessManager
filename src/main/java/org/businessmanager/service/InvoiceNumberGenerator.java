package org.businessmanager.service;

public interface InvoiceNumberGenerator {

	/**
	 * Retrieves the next unique invoice number.
	 * 
	 * @return the next unique invoice number
	 */
	public Long getNextInvoiceNumber();
}
