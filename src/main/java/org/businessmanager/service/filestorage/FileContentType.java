package org.businessmanager.service.filestorage;

public enum FileContentType {
	INVOICE("/invoices"), TEMPLATE("/templates");
	
	private String path;
	
	private FileContentType(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
