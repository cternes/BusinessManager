package org.businessmanager.geodb;

import java.util.Currency;
import java.util.List;

import junit.framework.Assert;

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
public class OpenGeoDBTest {

	@Autowired
	private OpenGeoDB service;
	
	@Test
	public void testGetCurrencies() {
		List<Currency> list = service.getListOfCurrencies("de");
		Assert.assertEquals(218, list.size());
		Assert.assertEquals("ADP", list.get(0).getCurrencyCode());
	}
	
	@Test
	public void testGetCountries() {
		List<Country> list = service.getListOfCountries("de");
		Assert.assertEquals(95, list.size());
		Assert.assertEquals("AL", list.get(0).getCode());
	}
	
}
