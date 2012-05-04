package org.businessmanager.database.security;

import org.businessmanager.database.security.UserDao;
import org.businessmanager.domain.security.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void testAdd() {
		User user = new User("testUser", "empty");
		
		user = userDao.save(user);
		
		User userFromDb = userDao.findUserByName("testUser");
		Assert.assertNotNull(userFromDb);
		
		Assert.assertEquals("testUser", userFromDb.getUsername());
		Assert.assertEquals(user.getId(), userFromDb.getId());
	}
}
