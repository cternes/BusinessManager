package org.businessmanager.dao;

import java.util.List;

import org.businessmanager.domain.StorageFile;
import org.businessmanager.domain.StorageFile_;

public class StorageFileDaoImpl extends GenericDaoImpl<StorageFile> implements
		StorageFileDao {

	@Override
	public List<StorageFile> findAll() {
		return findAll(StorageFile_.id, true);
	}

	@Override
	public Class<StorageFile> getPersistenceClass() {
		return StorageFile.class;
	}

}
