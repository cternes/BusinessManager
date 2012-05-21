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
package org.businessmanager.service;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.businessmanager.database.ActivityDao;
import org.businessmanager.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public Activity saveActivity(Activity activity) {
		Validate.notNull(activity, "Parameter activity must not be null!");
		
		if(activity.getId() == null) {
			return activityDao.save(activity);
		}
		else {
			return activityDao.update(activity);
		}
	}

	@Override
	public List<Activity> getRecentActivities(int numberOfRows) {
		return activityDao.findRecentActivities(numberOfRows);
	}

	@Override
	public List<Activity> getRecentActivitiesByUser(Long userId,
			int numberOfRows) {
		return activityDao.findRecentActivitiesByUser(userId, numberOfRows);
	}

}
