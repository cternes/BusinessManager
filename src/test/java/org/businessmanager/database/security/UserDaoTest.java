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
		/*User user = new User("testUser", "empty");
		
		user = userDao.save(user);
		
		User userFromDb = userDao.findUserByName("testUser");
		Assert.assertNotNull(userFromDb);
		
		Assert.assertEquals("testUser", userFromDb.getUsername());
		Assert.assertEquals(user.getId(), userFromDb.getId());*/
	}
}
