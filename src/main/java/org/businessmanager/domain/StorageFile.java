package org.businessmanager.domain;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.businessmanager.domain.security.User;
import org.businessmanager.service.filestorage.FileContentType;
import org.hibernate.annotations.Type;
import org.springframework.http.MediaType;

@Entity
public class StorageFile extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String fileId;

	@Enumerated(EnumType.STRING)
	private FileContentType contentType;

	@Type(type = "org.businessmanager.domain.usertypes.MediaUserType")
	private MediaType mediaType;
	private String filepath;
	private Integer version;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deleted;
	
	@ManyToOne(targetEntity=User.class)
	private User user;

	@Transient
	private File file;
	
	public StorageFile() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public FileContentType getContentType() {
		return contentType;
	}

	public void setContentType(FileContentType contentType) {
		this.contentType = contentType;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public File getFile() {
		if(file == null && filepath != null) {
			file = new File(filepath);
		}
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
