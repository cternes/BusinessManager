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

import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.GroupService;
import org.businessmanager.service.security.UserService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.UserGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("userGroupController")
@Scope("request")
public class UserGroupController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserGroupModel model;
	
	private boolean showGroupDialog = false;
	private boolean showUserDialog = false;
	private boolean showAssignGroupDialog = false;
	
	@PostConstruct
	public void init() {
		//initialize here
	}

	public UserGroupModel getModel() {
		if(model.getUserList() == null) {
			retrieveUsers();
		}
		if(model.getGroupList() == null) {
			retrieveGroups();
		}
		return model;
	}

	private void retrieveGroups() {
		List<Group> groupList = groupService.getGroups();
		model.setGroupList(groupList);
	}

	private void retrieveUsers() {
		List<User> userList = userService.getUsers();
		model.setUserList(userList);
	}
	
	public boolean getShowGroupDialog() {
		return showGroupDialog;
	}
	
	public void openGroupDialog() {
		showGroupDialog = true;
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
	
	public String navigateToAssignUsers() {
		return navigationManager.getAdminAssignUsers();
	}

	public boolean getShowAssignGroupDialog() {
		return showAssignGroupDialog;
	}

	public void setShowAssignGroupDialog(boolean showAssignGroupDialog) {
		this.showAssignGroupDialog = showAssignGroupDialog;
	}
	
	public void openAssignGroupDialog() {
		showAssignGroupDialog = true;
	}
	
	public void closeAssignGroupDialog() {
		showAssignGroupDialog = false;
	}
	
}
