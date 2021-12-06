package com.fpt.demo.noticemanagement.model;

import java.util.Objects;

/**
 * @author DuyHT7
 */
public class AttachmentDto {

	private Long id;
	private Long noticeId;
	private String fileType;
	private String fileSize;
	private String fileName;
	private String fileUrl;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	@Override
	public int hashCode() {
		return Objects.hash(fileName, fileSize, fileType, fileUrl, id, noticeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttachmentDto other = (AttachmentDto) obj;
		return Objects.equals(fileName, other.fileName) && Objects.equals(fileSize, other.fileSize)
				&& Objects.equals(fileType, other.fileType) && Objects.equals(fileUrl, other.fileUrl)
				&& Objects.equals(id, other.id) && Objects.equals(noticeId, other.noticeId);
	}
}
