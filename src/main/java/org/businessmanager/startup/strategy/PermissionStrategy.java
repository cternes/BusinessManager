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
package org.businessmanager.startup.strategy;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.dao.GenericDao;
import org.businessmanager.dao.security.PermissionDao;
import org.businessmanager.domain.security.Permission;
import org.businessmanager.domain.security.Permission_;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Christian Ternes
 *
 */
public class PermissionStrategy extends AbstractStorageStrategy<Permission> implements EntityStorageStrategy<Permission> {
	
 	@Autowired
	private PermissionDao dao;
	
	@Override
	public GenericDao<Permission> getDao() {
		return dao;
	}

	@Override
	public SingularAttribute<Permission, ?> getAttributeKey() {
		return Permission_.name;
	}

	@Override
	public Object getAttributeValue(Permission source) {
		return source.getName();
	}

	@Override
	public Permission updateEntity(Permission source, Permission target) {
		target.setName(source.getName());
		return target;
	}

	@Override
	public boolean isEntitiesRemovedOnStartup() {
		return false;
	}

}
