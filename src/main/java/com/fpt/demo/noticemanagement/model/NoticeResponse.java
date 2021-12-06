package com.fpt.demo.noticemanagement.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DuyHT7
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeResponse {

	private String status;
	private String message;
	private Long id;
	private String title;
	private String content;
	private Date registionDate;
	private Long numberOfView;
	private String author;
	private Boolean isAchieved;
	private List<AttachmentDto> attachments;

	public Boolean isAchieved() {
		return isAchieved;
	}

	public void setAchieved(Boolean isAchieved) {
		this.isAchieved = isAchieved;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentDto> attachments) {
		this.attachments = attachments;
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

	public Date getRegistionDate() {
		return registionDate;
	}

	public void setRegistionDate(Date registionDate) {
		this.registionDate = registionDate;
	}

	public Long getNumberOfView() {
		return numberOfView;
	}

	public void setNumberOfView(Long numberOfView) {
		this.numberOfView = numberOfView;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attachments, author, content, id, isAchieved, message, numberOfView, registionDate, status,
				title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoticeResponse other = (NoticeResponse) obj;
		return Objects.equals(attachments, other.attachments) && Objects.equals(author, other.author)
				&& Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& isAchieved == other.isAchieved && Objects.equals(message, other.message)
				&& Objects.equals(numberOfView, other.numberOfView)
				&& Objects.equals(registionDate, other.registionDate) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title);
	}
}
