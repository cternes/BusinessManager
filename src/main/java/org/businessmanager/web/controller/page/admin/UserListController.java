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
package org.businessmanager.web.controller.page.admin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.UserService;
import org.businessmanager.web.controller.state.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("userListController")
@Scope("request")
public class UserListController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserModel model;
	
	@PostConstruct
	public void init() {
		//initialize here
	}

	public UserModel getModel() {
		if(model.getUserList() == null) {
			fetchUsers();
		}
		return model;
	}

	private void fetchUsers() {
		List<User> userList = userService.getUsers();
		model.setUserList(userList);
	}
	
}
