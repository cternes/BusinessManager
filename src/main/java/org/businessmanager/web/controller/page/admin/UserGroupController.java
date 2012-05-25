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

import org.businessmanager.domain.security.Role;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.RoleService;
import org.businessmanager.service.security.UserService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.state.UserGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("userGroupController")
@Scope("request")
public class UserGroupController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserGroupModel model;
	
	private boolean showRoleDialog = false;
	private boolean showUserDialog = false;
	
	@PostConstruct
	public void init() {
		//initialize here
	}

	public UserGroupModel getModel() {
		if(model.getUserList() == null) {
			retrieveUsers();
		}
		if(model.getRoleList() == null) {
			retrieveRoles();
		}
		return model;
	}

	private void retrieveRoles() {
		List<Role> roleList = roleService.getRoles();
		model.setRoleList(roleList);
	}

	private void retrieveUsers() {
		List<User> userList = userService.getUsers();
		model.setUserList(userList);
	}
	
	public boolean getShowRoleDialog() {
		return showRoleDialog;
	}
	
	public void openRoleDialog() {
		showRoleDialog = true;
	}
	
	public boolean getShowUserDialog() {
		return showUserDialog;
	}
	
	public void openUserDialog() {
		showUserDialog = true;
	}
	
	public String navigateToAddUser() {
		model.setSelectedUser(null);
		return navigateToEditUser();
	}
	
	public String navigateToEditUser() {
		return navigationManager.getAdminUserEdit();
	}
	
	public String navigateToAssignGroups() {
		return navigationManager.getAdminAssignGroups();
	}
	
}
