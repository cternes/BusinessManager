package org.businessmanager.web.controller.page.admin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.ListUtils;
import org.businessmanager.domain.security.Group;
import org.businessmanager.domain.security.User;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.state.UserGroupModel;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("assignGroupsController")
@Scope("request")
public class AssignGroupsController extends AbstractController {

	@Autowired
	private UserGroupModel model;
	
	private DualListModel<Group> groups;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		User selectedUser = model.getSelectedUser();
		
		List<Group> source = model.getGroupList();
		List<Group> target = selectedUser.getAssignedGroups();
	
		source = ListUtils.subtract(source, target);
		
		groups = new DualListModel<Group>(source, target);
	}

	public UserGroupModel getModel() {
		return model;
	}
	
	public String navigateBack() {
		return navigationManager.getAdminSecuritymanagement();
	}

	public DualListModel<Group> getGroups() {
		
		return groups;
	}

	public void setGroups(DualListModel<Group> groups) {
		this.groups = groups;
	}

	public String assignGroups() {
		//TODO: assign groups
		return navigationManager.getAdminSecuritymanagement();
	}
}
