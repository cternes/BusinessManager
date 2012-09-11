package org.businessmanager.dao;

import org.businessmanager.domain.StorageFile;

public interface StorageFileDao extends GenericDao<StorageFile> {
	public StorageFile getLatestStorageFile(String fileId);

	public StorageFile getStorageFile(String fileId, Integer version);
}
