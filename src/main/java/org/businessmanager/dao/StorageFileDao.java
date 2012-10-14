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
