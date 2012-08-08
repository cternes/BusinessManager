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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.businessmanager.dao.ActivityDao;
import org.businessmanager.domain.Activity;
import org.businessmanager.dto.ActivityDto;
import org.businessmanager.dto.ActivityDtoFactory;
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
	public List<ActivityDto> getRecentActivities(int numberOfRows) {
		List<Activity> activityList = activityDao.findRecentActivities(numberOfRows);
		ArrayList<ActivityDto> activityDtoList = convertToDtoList(activityList);
		
		return activityDtoList;
	}

	@Override
	public List<ActivityDto> getRecentActivitiesByUser(Long userId, int numberOfRows) {
		List<Activity> activityList = activityDao.findRecentActivitiesByUser(userId, numberOfRows);
		
		ArrayList<ActivityDto> activityDtoList = convertToDtoList(activityList);
		return activityDtoList;
	}

	private ArrayList<ActivityDto> convertToDtoList(List<Activity> activityList) {
		ArrayList<ActivityDto> activityDtoList = new ArrayList<ActivityDto>();
		for (Activity activity : activityList) {
			ActivityDto dto = ActivityDtoFactory.createActivityDto(activity);
			activityDtoList.add(dto);
		}
		return activityDtoList;
	}
}
