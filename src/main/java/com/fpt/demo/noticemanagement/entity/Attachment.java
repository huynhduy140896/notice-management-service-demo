package com.fpt.demo.noticemanagement.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DuyHT7
 */

@Entity
@Table(name = "attachment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment extends Auditable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "notice_id")
	private Notice notice;

	@Column(name = "file_size")
	private String fileSize;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "file_url")
	private String fileUrl;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fileName, fileSize, fileType, fileUrl, notice);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		return Objects.equals(fileName, other.fileName) && Objects.equals(fileSize, other.fileSize)
				&& Objects.equals(fileType, other.fileType) && Objects.equals(fileUrl, other.fileUrl)
				&& Objects.equals(notice, other.notice);
	}

}
