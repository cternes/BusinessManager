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
package org.businessmanager.database.settings;

import java.util.List;

import org.businessmanager.database.GenericDaoImpl;
import org.businessmanager.domain.settings.ApplicationSetting;
import org.businessmanager.domain.settings.ApplicationSetting_;
import org.businessmanager.domain.settings.QApplicationSetting;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author Christian Ternes
 *
 */
@Repository
public class ApplicationSettingsDaoImpl extends GenericDaoImpl<ApplicationSetting> implements ApplicationSettingsDao {

	@Override
	public List<ApplicationSetting> findAll() {
		return findAll(ApplicationSetting_.id, true);
	}

	@Override
	public Class<ApplicationSetting> getPersistenceClass() {
		return ApplicationSetting.class;
	}
	
	@Override 
	public ApplicationSetting getApplicationSettingByKey(String key, String username) {
		JPAQuery query = new JPAQuery(getEntityManager());
		QApplicationSetting setting = QApplicationSetting.applicationSetting;
		query.from(setting).where(setting.paramKey.eq(key));
		
		if(username == null) {
			query.where(setting.username.isNull());
		}
		else {
			query.where(setting.username.eq(username));
		}
		
		List<ApplicationSetting> list = query.list(setting);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
