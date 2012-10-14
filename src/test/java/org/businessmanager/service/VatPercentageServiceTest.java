package org.businessmanager.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.businessmanager.domain.VatPercentage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class VatPercentageServiceTest {

	@Autowired
	private VatPercentageService service;
	
	@Test
	public void testSave() {
		Assert.assertEquals(0, service.getVatPercentages().size());
		
		service.saveInvoice(new VatPercentage(BigDecimal.TEN));
		
		Assert.assertEquals(1, service.getVatPercentages().size());
	}
}
