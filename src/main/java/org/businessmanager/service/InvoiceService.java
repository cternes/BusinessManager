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
package org.businessmanager.service;

import java.util.List;

import org.businessmanager.domain.Invoice;

public interface InvoiceService {
	
	/**
	 * Retrieves all available {@link Invoice}s from the database.
	 * 
	 * @return a list of {@link Invoice}s
	 */
	public List<Invoice> getInvoices();
	
	/**
	 * Save a {@link Invoice} in the database.
	 * 
	 * @param contact the {@link Invoice} which should be saved
	 * @return the {@link Invoice} with set id
	 */
	public Invoice saveInvoice(Invoice invoice);
}
