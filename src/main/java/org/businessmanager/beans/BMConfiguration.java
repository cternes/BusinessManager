package org.businessmanager.beans;

public class BMConfiguration {

	private String fileStoragePath;

	public String getFileStoragePath() {
		if (fileStoragePath != null && !fileStoragePath.endsWith("/")
				&& fileStoragePath.endsWith("\\")) {
			fileStoragePath += "/";
		}
		return fileStoragePath;
	}

	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}
}
