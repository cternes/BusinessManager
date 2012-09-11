package org.businessmanager.service.filestorage;

import java.util.List;

import org.businessmanager.domain.StorageFile;

public interface FileStorageService {

	public void saveFile(StorageFile storageFile);
	
	public List<StorageFile> getFilesOfContentType(FileContentType contentType);
	
	public StorageFile getFile(String fileId, FileContentType contentType);
}
