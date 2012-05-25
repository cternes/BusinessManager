package org.businessmanager.web.bean;

import org.businessmanager.domain.security.User;

public class UserBean {

	private String username;
	private String mail;
	private String password;
	private boolean isAdministrator;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean getIsAdministrator() {
		return isAdministrator;
	}

	public void setIsAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public void copyDataFromUser(User selectedUser) {
		setUsername(selectedUser.getUsername());
		setMail(selectedUser.getEmail());
		setIsAdministrator(selectedUser.isAdministrator());
	}
	
}
