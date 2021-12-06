package com.fpt.demo.noticemanagement.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import com.fpt.demo.noticemanagement.constant.HttpResponse;
import com.fpt.demo.noticemanagement.entity.Notice;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.exception.ResourceNotFoundException;
import com.fpt.demo.noticemanagement.mapper.NoticeMapper;
import com.fpt.demo.noticemanagement.model.NoticeRequest;
import com.fpt.demo.noticemanagement.model.NoticeResponse;
import com.fpt.demo.noticemanagement.repository.NoticeRepository;
import com.fpt.demo.noticemanagement.repository.UserRepository;
import com.fpt.demo.noticemanagement.service.AttachmentService;
import com.fpt.demo.noticemanagement.service.NoticeService;

@RunWith(MockitoJUnitRunner.class)
public class NoticeServiceTest {

	@InjectMocks
	private NoticeService noticeService;
	
	@Mock
	private NoticeRepository repository;
	
	private NoticeMapper mapper = Mockito.spy(NoticeMapper.class);;
	
	@Mock
	private AttachmentService attachmentService;
	
	@Mock
	private UserRepository userRepository;
	
	private MockData mockData = new MockData();
	@Before
	public void init() {
	}
	
	@Test
	public void testGetById_Success() throws ResourceNotFoundException, NoticeManagementException {
		Long testId = 1L;
		Optional<Notice> mockedNotice = mockData.getMockOptionalNotice();
		when(repository.findById(anyLong())).thenReturn(mockedNotice);
		when(mapper.toDto(any())).thenReturn(mockData.getMockNoticeDto());
		when(repository.saveAndFlush(any())).thenReturn(mockedNotice.get());
		
		NoticeResponse result = noticeService.getNoticeById(testId);
		assertEquals(result.getAuthor(), mockedNotice.get().getUser().getUsername());
	}
	
	@Test
	public void testGetById_IsExpired() throws ResourceNotFoundException, NoticeManagementException {
		Long testId = 1L;
		Optional<Notice> mockedNotice = mockData.getMockOptionalNotice();
		mockedNotice.get().setEndDateTime(new Date());
		when(repository.findById(anyLong())).thenReturn(mockedNotice);
		try {
			noticeService.getNoticeById(testId);
		} catch (Exception e) {
			assertEquals(e.getMessage(), HttpResponse.NOTICE_IS_EXPIRED.getMessage());
		}
	}
	
	@Test
	public void testGetById_IsAchieved() throws ResourceNotFoundException, NoticeManagementException {
		Long testId = 1L;
		Optional<Notice> mockedNotice = mockData.getMockOptionalNotice();
		mockedNotice.get().setIsAchieved(true);
		when(repository.findById(anyLong())).thenReturn(mockedNotice);
		try {
			noticeService.getNoticeById(testId);
		} catch (Exception e) {
			assertEquals(e.getMessage(), HttpResponse.NOTICE_IS_ACHIEVED.getMessage());
		}
	}
	
	@Test
	public void testGetAll_Success() throws ResourceNotFoundException, NoticeManagementException {
		when(repository.findAllByIsAchievedFalse(any())).thenReturn(mockData.getMockPageNotice());
		when(repository.saveAndFlush(any())).thenReturn(mockData.getMockOptionalNotice().get());
		when(mapper.toDtoList(any())).thenReturn(Arrays.asList(mockData.getMockNoticeDto()));
		
		Page<NoticeResponse> result = noticeService.getAllByPage(0, 5);
		assertEquals(1, result.getSize());
	}
	
	@Test
	public void testCreate_IvalidInput_ContentEmpty() throws NoticeManagementException{
		NoticeRequest request = new NoticeRequest();
		request.setContent("");
		request.setTitle("a");
		request.setEndDateTime(new Date().toString());
		request.setStartDateTime(new Date().toString());
		
		NoticeResponse res = noticeService.create(null, request);
		assertEquals(res.getMessage(), HttpResponse.EMPTY_CONTENT_ERROR.getMessage());
	}
	
	@Test
	public void testCreate_IvalidInput_TitleEmpty() throws NoticeManagementException{
		NoticeRequest request = new NoticeRequest();
		request.setContent("a");
		request.setTitle("");
		request.setEndDateTime(new Date().toString());
		request.setStartDateTime(new Date().toString());
		
		NoticeResponse res = noticeService.create(null, request);
		assertEquals(res.getMessage(), HttpResponse.EMPTY_TITLE_ERROR.getMessage());
	}
	
	@Test
	public void testCreate_IvalidInput_WrongDateFormat() throws NoticeManagementException{
		NoticeRequest request = new NoticeRequest();
		request.setContent("a");
		request.setTitle("a");
		request.setEndDateTime(new Date().toString());
		request.setStartDateTime("2021-12-1");
		
		NoticeResponse res = noticeService.create(null, request);
		assertEquals(res.getMessage(), HttpResponse.INVALID_DATETIME_FORMAT.getMessage());
	}
	
	@Test
	public void testCreate_IvalidInput_EndDateInThePast() throws NoticeManagementException{
		NoticeRequest request = new NoticeRequest();
		request.setContent("a");
		request.setTitle("a");
		request.setEndDateTime("2021-12-01");
		request.setStartDateTime("2021-12-12");
		
		NoticeResponse res = noticeService.create(null, request);
		assertEquals(res.getMessage(), HttpResponse.END_DATETIME_CHECK_FAILED.getMessage());
	}
}
