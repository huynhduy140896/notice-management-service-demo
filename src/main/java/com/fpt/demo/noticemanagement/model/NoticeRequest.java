package com.fpt.demo.noticemanagement.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author DuyHT7
 */
public class NoticeRequest {

	private Long id;
	@NotNull
	private String title;
	@NotNull
	private String content;
	private String startDateTime;
	private String endDateTime;
	private MultipartFile[] attachFiles;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public MultipartFile[] getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(MultipartFile[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	
}
