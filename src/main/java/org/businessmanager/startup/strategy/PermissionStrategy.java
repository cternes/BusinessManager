package org.businessmanager.startup.strategy;

import javax.persistence.metamodel.SingularAttribute;

import org.businessmanager.database.GenericDao;
import org.businessmanager.database.security.PermissionDao;
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
		return true;
	}

}
