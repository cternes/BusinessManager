package org.businessmanager.dao;

import java.util.List;

import org.businessmanager.domain.StorageFile;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.filestorage.FileContentType;

public interface StorageFileDao extends GenericDao<StorageFile> {
	public StorageFile getLatestStorageFile(User user, String fileId);

	public StorageFile getStorageFile(User user, String fileId, Integer version);
	
	public List<StorageFile> getStorageFilesOfContentType(User user, FileContentType contentType);
}
