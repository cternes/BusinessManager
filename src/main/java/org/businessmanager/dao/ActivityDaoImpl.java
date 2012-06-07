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
package org.businessmanager.dao;

import java.util.List;

import org.businessmanager.domain.Activity;
import org.businessmanager.domain.Activity_;
import org.businessmanager.domain.QActivity;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;
 
@Repository
public class ActivityDaoImpl extends GenericDaoImpl<Activity> implements ActivityDao {

	@Override
	public List<Activity> findAll() {
		return findAll(Activity_.time, false);
	}

	@Override
	public Class<Activity> getPersistenceClass() {
		return Activity.class;
	}

	@Override
	public List<Activity> findRecentActivities(int numberOfRows) {
		return findAll(Activity_.time, false, 0, numberOfRows);
	} 

	@Override
	public List<Activity> findRecentActivitiesByUser(Long userId,
			int numberOfRows) {
		JPAQuery query = new JPAQuery(getEntityManager());
		QActivity activity = QActivity.activity;
		return query.from(activity).where(activity.userId.eq(userId)).orderBy(activity.time.desc()).limit(numberOfRows).list(activity);
	}

}
