package org.businessmanager.web.controller.state;

import java.util.List;

import org.businessmanager.domain.security.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Christian Ternes
 *
 */
@Component
@Scope("conversation.access")
public class UserModel {

	private List<User> userList;
	private User selectedUser;

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public User getSelectedUser() {
		return selectedUser;
	}
	
}
