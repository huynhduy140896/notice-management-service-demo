package com.fpt.demo.noticemanagement.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.demo.noticemanagement.constant.Constants;
import com.fpt.demo.noticemanagement.entity.Notice;
import com.fpt.demo.noticemanagement.entity.Role;
import com.fpt.demo.noticemanagement.entity.User;
import com.fpt.demo.noticemanagement.model.NoticeResponse;

public class MockData {

	
	public Optional<Notice> getMockOptionalNotice() {
		Notice notice =  new Notice();
		
		notice.setContent("This is content");
		notice.setId(RandomUtils.nextLong());
		notice.setIsAchieved(false);
		notice.setTitle("This is title");
		notice.setAttachments(null);
		notice.setStartDateTime(new Date());
		notice.setEndDateTime(convertStringToDate("2099-01-01"));
		notice.setUser(getMockUser());
		notice.setNumberOfView(0l);
		return Optional.of(notice);
	}
	
	public Page<Notice> getMockPageNotice() {
		Notice notice =  new Notice();
		
		notice.setContent("This is content");
		notice.setId(RandomUtils.nextLong());
		notice.setIsAchieved(false);
		notice.setTitle("This is title");
		notice.setAttachments(null);
		notice.setStartDateTime(new Date());
		notice.setEndDateTime(convertStringToDate("2099-01-01"));
		notice.setUser(getMockUser());
		notice.setNumberOfView(0l);
		return new PageImpl<Notice>(java.util.Arrays.asList(notice));
	}
	
	public NoticeResponse getMockNoticeDto() {
		NoticeResponse notice =  new NoticeResponse();
		
		notice.setContent("This is content");
		notice.setId(RandomUtils.nextLong());
		notice.setTitle("This is title");
		notice.setAttachments(null);
		notice.setAuthor("Duy");
		
		return notice;
	}
	
	public User getMockUser() {
		User user = new User();
		
		user.setId(RandomUtils.nextLong());
		user.setEmail("huynhduy@gmail.com");
		user.setFirstName("Duy");
		user.setLastName("Huynh");
		user.setPassword("xxxxxx");
		user.setUsername("duyht7");
		user.setRoles(Set.of(getMockRoleAdmin(), getMockRoleUser()));
		
		return user;
	}
	
	public Role getMockRoleAdmin() {
		Role role = new Role();
		
		role.setName("ADMIN");
		role.setDescription("Use for admin");
		
		return role;
	}
	
	public Role getMockRoleUser() {
		Role role = new Role();
		
		role.setName("USER");
		role.setDescription("Use for admin");
		
		return role;
	}
	
	public List<MultipartFile> mockAttachmentFiles(){
		List<MultipartFile> attachments = new ArrayList<MultipartFile>();
		return attachments;
	}
	
	private Date convertStringToDate(String dateString) {
		if (StringUtils.isNotBlank(dateString)) {
			// Convert String to date with format yyyy-MM-dd
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TIMESTAMP_PATTERN);
			try {
				return dateFormat.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
}
