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

import javax.faces.application.FacesMessage;

import org.apache.commons.collections.ListUtils;
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.GroupService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.UserGroupModel;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("assignGroupsController")
@Scope("request")
public class AssignGroupsController extends AbstractController {

	@Autowired
	private UserGroupModel model;
	
	@Autowired
	private GroupService groupService;
	
	private DualListModel<Group> groups;
	
	public UserGroupModel getModel() {
		return model;
	}
	
	public DualListModel<Group> getGroups() {
		if(groups == null) {
			createGroupsModel();
		}
		return groups;
	}

	@SuppressWarnings("unchecked")
	private void createGroupsModel() {
		User selectedUser = model.getSelectedUser();
		
		List<Group> source = model.getGroupList();
		List<Group> target = selectedUser.getAssignedGroups();
	
		source = ListUtils.subtract(source, target);
		
		groups = new DualListModel<Group>(source, target);
	}
	
	public void setGroups(DualListModel<Group> groups) {
		this.groups = groups;
	}

	public String assignGroups() {
		List<Group> notAssignedGroups = groups.getSource();
		for (Group group : notAssignedGroups) {
			removeUserFromGroup(group);
			groupService.assignUsersToGroup(group.getMembers(), group);
		}
		
		
		List<Group> assignedGroups = groups.getTarget();
		for (Group group : assignedGroups) {
			addUserToGroup(group);
			groupService.assignUsersToGroup(group.getMembers(), group);
		}
		
		model.refresh();
		
		addMessage(FacesMessage.SEVERITY_INFO, "assignGroups_success_groups_assigned");
		return navigateBack();
	}

	private void removeUserFromGroup(Group group) {
		List<User> members = group.getMembers();
		members.remove(model.getSelectedUser());
	}

	private void addUserToGroup(Group group) {
		List<User> members = group.getMembers();
		if(!members.contains(model.getSelectedUser())) {
			group.getMembers().add(model.getSelectedUser());	
		}
	}
	
	public String navigateBack() {
		return navigationManager.getAdminSecuritymanagement();
	}
}
