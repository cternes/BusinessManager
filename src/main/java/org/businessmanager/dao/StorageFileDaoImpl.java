package org.businessmanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

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

	@Override
	public StorageFile getLatestStorageFile(String fileId) {
		TypedQuery<StorageFile> query = getEntityManager()
				.createQuery(
						"FROM StorageFile s WHERE s.fileId = :fileId ORDER BY s.version DESC",
						StorageFile.class).setParameter("fileId", fileId);

		List<StorageFile> resultList = query.getResultList();

		if (resultList.size() > 0) {
			return resultList.get(0);
		}

		return null;
	}

	@Override
	public StorageFile getStorageFile(String fileId, Integer version) {
		TypedQuery<StorageFile> query = getEntityManager()
				.createQuery(
						"FROM StorageFile s WHERE s.fileId = :fileId AND s.version = :version",
						StorageFile.class).setParameter("fileId", fileId)
				.setParameter("version", version);

		return query.getSingleResult();
	}

}
