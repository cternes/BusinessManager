package org.businessmanager.web.controller.page.admin;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.businessmanager.aop.annotation.ErrorHandled;
import org.businessmanager.domain.security.User;
import org.businessmanager.error.DuplicateUserException;
import org.businessmanager.service.security.UserService;
import org.businessmanager.web.bean.UserBean;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.state.UserGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("userEditController")
@Scope("request")
public class UserEditController extends AbstractController {

	private static final String DUPLICATE_USER_ERROR = "edituser_error_duplicate_user";

	@Autowired
	private UserGroupModel model;
	
	@Autowired
	private UserService userService;
	
	private UserBean bean = new UserBean();
	
	@PostConstruct
	public void init() {
		User selectedUser = model.getSelectedUser();
		if(selectedUser != null) {
			bean.copyDataFromUser(selectedUser);
		}
	}
	
	public String deleteUser() {
		User selectedUser = model.getSelectedUser();
		if(selectedUser != null) {
			userService.deleteUser(selectedUser.getId());
			
			refreshModel();
			addMessage(FacesMessage.SEVERITY_INFO, "edituser_success_user_deleted");
			return navigationManager.getAdminSecuritymanagement();
		}
		return "#";
	}

	public UserGroupModel getModel() {
		return model;
	}

	public UserBean getBean() {
		return bean;
	}
	
	public String navigateBack() {
		model.setSelectedUser(null);
		return navigationManager.getAdminSecuritymanagement();
	}
	
	@ErrorHandled
	public String saveUser() {
		if (model.getSelectedUser() == null) {
			return createUser();
		}
		else {
			return updateUser();
		}
	}
	
	public String createUser() {
		if(validateInput()) {
			User user = new User(bean.getUsername(), bean.getPassword());
			user = fillUser(user);
			if(user != null) {
				try {
					user = userService.addUser(user, bean.getIsAdministrator());
					model.refresh();
					addMessage(FacesMessage.SEVERITY_INFO, "edituser_success_user_created");
					return navigationManager.getAdminSecuritymanagement();
				}
				catch(DuplicateUserException e) {
					addErrorMessage(DUPLICATE_USER_ERROR);
				}
			}
		}
		return "#";
	}
	
	private boolean validateInput() {
		boolean isValid = true;
		
		if(bean.getPassword() == null || bean.getPassword().isEmpty()) {
			isValid = false;
			addErrorMessage("edituser_error_missing_password");
		}
		
		return isValid;
	}

	private String updateUser() {
		User user = fillUser(model.getSelectedUser());
		boolean updatePassword = false;
		if(bean.getPassword() != null && !bean.getPassword().isEmpty()) {
			user.setPassword(bean.getPassword());
			updatePassword = true;
		}
		
		try {
			userService.updateUser(user, updatePassword, user.isAdministrator());
			refreshModel();
			
			addMessage(FacesMessage.SEVERITY_INFO, "edituser_success_user_edited");
			return navigationManager.getAdminSecuritymanagement();
		}
		catch(DuplicateUserException e) {
			addErrorMessage(DUPLICATE_USER_ERROR);
		}
		
		return "#";
	}

	private void refreshModel() {
		//refresh model & clear selection
		model.refresh();
		model.setSelectedUser(null);
	}
	
	public User fillUser(User user) {
		user.setUsername(bean.getUsername());
		user.setEmail(bean.getMail());
		user.setAdministrator(bean.getIsAdministrator());
		return user;
	}
	
	/**
	 * Determines if a user can be deleted.
	 * 
	 * @return true, if there is a user selected in the model
	 */
	public boolean getCanDelete() {
		User selectedUser = model.getSelectedUser();
		if(selectedUser != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if the selected user is the admin user.
	 * 
	 * @return true, if the selected user is the admin user
	 */
	public boolean getIsAdminUser() {
		User selectedUser = model.getSelectedUser();
		if(selectedUser != null && selectedUser.getUsername().equals(User.ADMIN_USER)) {
			return true;
		}
		return false;
	}

}