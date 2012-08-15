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
package org.businessmanager.web.controller.page.home;

import java.util.List;

import org.businessmanager.domain.Activity.ActivityType;
import org.businessmanager.domain.Contact;
import org.businessmanager.dto.ActivityDto;
import org.businessmanager.service.ActivityService;
import org.businessmanager.service.ContactService;
import org.businessmanager.web.controller.AbstractController;
import org.businessmanager.web.controller.model.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dashboardController")
@Scope("request")
public class DashboardController extends AbstractController {

	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private ContactModel contactModel;
	
	@Autowired
	private ContactService contactService;
	
	private List<ActivityDto> recentActivityList;
	
	private ActivityDto selectedActivity;
	
	public void fetchRecentActivity() {
		recentActivityList = activityService.getRecentActivities(50);
	}

	public List<ActivityDto> getRecentActivityList() {
		if(recentActivityList == null) {
			fetchRecentActivity();
		}
		return recentActivityList;
	}
	
	public String navigateToObject() {
		if(selectedActivity != null) {
			if(selectedActivity.getActivityType().equals(ActivityType.CONTACT)) {
				Contact contact = contactService.getContactById(selectedActivity.getObjectId());
				contactModel.setSelectedEntity(contact);
				return navigationManager.getContactView();
			}
		}
		return "#";
	}

	public void setSelectedActivity(ActivityDto selectedActivity) {
		this.selectedActivity = selectedActivity;
	}

	public ActivityDto getSelectedActivity() {
		return selectedActivity;
	}
	
	public String navigateToAddContact() {
		contactModel.setSelectedEntity(null);
		return navigationManager.getEditContact();
	}
	
}
