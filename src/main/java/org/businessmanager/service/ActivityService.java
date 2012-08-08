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

import org.businessmanager.domain.Activity;
import org.businessmanager.domain.security.User;
import org.businessmanager.dto.ActivityDto;

public interface ActivityService {

	/**
	 * Saves a {@link Activity} in the database.
	 * 
	 * @param activity the {@link Activity} to save
	 * @return the saved {@link Activity} with set id
	 */
	public Activity saveActivity(Activity activity);
	
	/**
	 * Retrieves recent activities from the database.
	 * 
	 * @param numberOfRows the number of activities to retrieve
	 * @return a list of {@link ActivityDto} ordered by time DESC
	 */
	public List<ActivityDto> getRecentActivities(int numberOfRows);
	
	/**
	 * Retrieves recent activities for a specific user from the database.
	 * 
	 * @param userId the id of the {@link User}
	 * @param numberOfRows the number of activities to retrieve
	 * @return a list of {@link ActivityDto} ordered by time DESC
	 */
	public List<ActivityDto> getRecentActivitiesByUser(Long userId, int numberOfRows);
}
