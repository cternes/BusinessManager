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
