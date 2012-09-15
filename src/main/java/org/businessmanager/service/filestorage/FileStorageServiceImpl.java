package org.businessmanager.service.filestorage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.solr.common.util.FileUtils;
import org.businessmanager.beans.BMConfiguration;
import org.businessmanager.dao.StorageFileDao;
import org.businessmanager.domain.StorageFile;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.security.SpringSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

	@Autowired
	private SpringSecurityService securityService;

	@Autowired
	private BMConfiguration configuration;

	@Autowired
	private StorageFileDao storageFileDao;

	@Override
	public void saveFile(StorageFile storageFile) {

		if (storageFile == null) {
			throw new IllegalArgumentException(
					"The parameter storageFile MUST NOT be null!");
		}

		User user = securityService.getLoggedInUser();
		String fileStoragePath = configuration.getFileStoragePath();

		String filePath = fileStoragePath + user.getId()
				+ storageFile.getContentType().getPath();
		ensurePathExists(filePath);

		StorageFile latestStorageFile = storageFileDao.getLatestStorageFile(
				user, storageFile.getFileId());
		Integer newVersion = latestStorageFile.getVersion() + 1;
		storageFile.setVersion(newVersion);

		String filename = storageFile.getFileId() + "_"
				+ System.currentTimeMillis() + "_" + newVersion;

		if (MediaType.parseMediaType("application/pdf").equals(
				storageFile.getMediaType())) {
			filename += ".pdf";
		} else if (MediaType
				.parseMediaType(
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document")
				.equals(storageFile.getMediaType())) {
			filename += ".docx";
		}

		File newFile = new File(filename);

		try {
			FileUtils.copyFile(storageFile.getFile(), newFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		storageFile.getFile().delete();
		storageFile.setFile(newFile);
		storageFile.setFilepath(newFile.getAbsolutePath());
		storageFile.setCreated(new Date());
		storageFile.setUser(user);

		storageFileDao.save(storageFile);
	}

	@Override
	public List<StorageFile> getFilesOfContentType(FileContentType contentType) {
		User user = securityService.getLoggedInUser();

		List<StorageFile> storageFiles = storageFileDao
				.getStorageFilesOfContentType(user, contentType);

		return storageFiles;
	}

	@Override
	public StorageFile getFile(String fileId, FileContentType contentType) {
		User user = securityService.getLoggedInUser();

		StorageFile storageFile = storageFileDao.getLatestStorageFile(user,
				fileId);

		return storageFile;
	}

	private void ensurePathExists(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
