package org.businessmanager.service.filestorage;

import java.util.List;

import org.businessmanager.beans.BMConfiguration;
import org.businessmanager.domain.StorageFile;
import org.businessmanager.service.security.SpringSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

	@Autowired
	private SpringSecurityService securityService;
	
	@Autowired
	private BMConfiguration configuration;
	
	@Override
	public void saveFile(StorageFile storageFile) {
		// TODO Auto-generated method stub
		Long userid = securityService.getLoggedInUserId();
		String fileStoragePath = configuration.getFileStoragePath();
		
		
		
		// 1. Verzeichnis aus Config lesen
		// 2. Überprüfung ob Insert oder Update (Versionierung)
		// 3. Falls Verzeichnis für User und Content-Type noch nicht angelegt -> mkdir
		// 4. Copy storageFile.file -> file in target dir
		// 5. Delete storageFile.file, update storageFile.file -> tempdir
		// 6. insert into database
	}

	@Override
	public List<StorageFile> getFilesOfContentType(FileContentType contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StorageFile getFile(String fileId, FileContentType contentType) {
		// TODO Auto-generated method stub
		return null;
	}

}
