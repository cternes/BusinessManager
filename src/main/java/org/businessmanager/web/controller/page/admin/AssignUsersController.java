package org.businessmanager.web.controller.page.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.GroupService;
import org.businessmanager.service.security.UserService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.state.UserGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("assignUsersController")
@Scope("view")
public class AssignUsersController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserGroupModel model;
	
	private String username;
	private List<User> assignedUsers = new ArrayList<User>();
	private User selectedUser;
	
	@PostConstruct
	public void init() {
		Group selectedGroup = model.getSelectedGroup();
		if(selectedGroup != null) {
			List<User> members = selectedGroup.getMembers();
			assignedUsers.addAll(members);
		}
	}
	
	public String navigateBack() {
		return navigationManager.getAdminSecuritymanagement();
	}
	
	public List<String> searchUser(String usernameFragment) {
		List<String> resultList = new ArrayList<String>();
		List<User> userList = userService.getUsersByNameFragment(usernameFragment);
		
		for (User user : userList) {
			resultList.add(user.getUsername());
		}
		
		return resultList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void assignUser() {
		User user = retrieveUser();
		if(user != null) {
			if(!assignedUsers.contains(user)) {
				assignedUsers.add(user);
			}
		}
	}
	
	private User retrieveUser() {
		if(!StringUtils.isEmpty(username)) {
			User user = userService.getUserByName(username);
			if(user != null) {
				return user;
			}
			else {
				addErrorMessage("assignUsers_error_user_not_found");
			}
		}
		else {
			addErrorMessage("assignUsers_error_empty_username");
		}
		return null;
	}
	
	public List<User> getAssignedUsers() {
		return assignedUsers;
	}
	
	public String save() {
		if(model.getSelectedGroup() != null) {
			groupService.assignUsersToGroup(assignedUsers, model.getSelectedGroup());
			
			addMessage(FacesMessage.SEVERITY_INFO, "assignUsers_success_users_assigned");
			return navigationManager.getAdminSecuritymanagement();
		}
		return "#";
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public void removeUser() {
		if(selectedUser != null) {
			assignedUsers.remove(selectedUser);
		}
	}
	
}
