package com.fpt.demo.noticemanagement.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DuyHT7
 */

@Entity
@Table(name = "notice")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notice extends Auditable {

	private static final long serialVersionUID = 1L;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "start_date_time")
	private Date startDateTime;

	@Column(name = "end_date_time")
	private Date endDateTime;

	@Column(name = "number_of_view")
	private Long numberOfView;

	@OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
	private List<Attachment> attachments;

	@ManyToOne
	@JoinColumn(name = "author")
	private User user;

	@Column(name = "is_achieved")
	private boolean isAchieved;

	public boolean isAchieved() {
		return isAchieved;
	}

	public void setIsAchieved(boolean isAchieved) {
		this.isAchieved = isAchieved;
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

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Long getNumberOfView() {
		return numberOfView;
	}

	public void setNumberOfView(Long numberOfView) {
		this.numberOfView = numberOfView;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(attachments, user, content, endDateTime, isAchieved, numberOfView, startDateTime, title);
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
		Notice other = (Notice) obj;
		return Objects.equals(attachments, other.attachments) && Objects.equals(user, other.user)
				&& Objects.equals(content, other.content) && Objects.equals(endDateTime, other.endDateTime)
				&& isAchieved == other.isAchieved && Objects.equals(numberOfView, other.numberOfView)
				&& Objects.equals(startDateTime, other.startDateTime) && Objects.equals(title, other.title);
	}

}
