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
package org.businessmanager.dto;

import org.businessmanager.domain.Activity;
import org.businessmanager.domain.Activity.ActivityType;
import org.businessmanager.web.bean.ContactActivityBean;

public final class ActivityDtoFactory {

	public static ActivityDto createActivityDto(Activity activity) {
		ActivityDto dto = new ActivityDto();
		dto.setUserId(activity.getUserId());
		dto.setObjectId(activity.getSourceId());
		dto.setActivityType(activity.getType());
		dto.setTime(activity.getCreationDate());
		
		if(ActivityType.CONTACT.equals(activity.getType())) {
			ContactActivityBean bean = new ContactActivityBean().fromJson(activity.getData());
			dto.setObjectName(bean.getContactName());
			dto.setUsername(bean.getUsername());

			if(bean.getActivity() != null) {
				dto.setActivity(bean.getActivity());
			}
		}
		
		return dto;
	}
	
	
}
