package org.businessmanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.businessmanager.domain.StorageFile;
import org.businessmanager.domain.StorageFile_;
import org.businessmanager.domain.security.User;
import org.businessmanager.service.filestorage.FileContentType;

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

	@Override
	public StorageFile getLatestStorageFile(User user, String fileId) {
		TypedQuery<StorageFile> query = getEntityManager()
				.createQuery(
						"FROM StorageFile s WHERE s.fileId = :fileId AND s.user = :user ORDER BY s.version DESC",
						StorageFile.class).setParameter("fileId", fileId)
				.setParameter("user", user);

		List<StorageFile> resultList = query.getResultList();

		if (resultList.size() > 0) {
			return resultList.get(0);
		}

		return null;
	}

	@Override
	public StorageFile getStorageFile(User user, String fileId, Integer version) {
		TypedQuery<StorageFile> query = getEntityManager()
				.createQuery(
						"FROM StorageFile s WHERE s.fileId = :fileId AND s.version = :version AND s.user = :user",
						StorageFile.class).setParameter("fileId", fileId)
				.setParameter("version", version).setParameter("user", user);

		return query.getSingleResult();
	}

	@Override
	public List<StorageFile> getStorageFilesOfContentType(User user,
			FileContentType contentType) {

		TypedQuery<StorageFile> query = getEntityManager()
				.createQuery(
						"FROM StorageFile s WHERE s.contentType = :contentType AND s.user = :user",
						StorageFile.class)
				.setParameter("contentType", contentType)
				.setParameter("user", user);

		return query.getResultList();
	}
}