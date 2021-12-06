package com.fpt.demo.noticemanagement.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.demo.noticemanagement.constant.Constants;
import com.fpt.demo.noticemanagement.constant.HttpResponse;
import com.fpt.demo.noticemanagement.entity.Notice;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.exception.ResourceNotFoundException;
import com.fpt.demo.noticemanagement.mapper.NoticeMapper;
import com.fpt.demo.noticemanagement.model.NoticeRequest;
import com.fpt.demo.noticemanagement.model.NoticeResponse;
import com.fpt.demo.noticemanagement.repository.NoticeRepository;
import com.fpt.demo.noticemanagement.repository.UserRepository;
import com.fpt.demo.noticemanagement.service.NoticeService;

/**
 * @author DuyHT7
 */

@Service
@EnableJpaRepositories
public class NoticeService extends BaseService<Notice, Long> {

	protected final static Logger LOGGER = LoggerFactory.getLogger(NoticeService.class);

	private DateTimeFormatter format = DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN);

	@Autowired
	private NoticeRepository repository;

	@Autowired
	private NoticeMapper mapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public NoticeService(NoticeRepository repository) {
		super(repository);
		this.repository = repository;
	}

	/**
	 * Get notice by id without expire or achieve notice
	 * 
	 * @param id
	 * @return
	 * @throws NoticeManagementException
	 */
	public NoticeResponse getNoticeById(Long id) throws NoticeManagementException, ResourceNotFoundException {

		LOGGER.info("Start to call method get notice by id");
		Notice exitingNotice = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(HttpResponse.NOTICE_DOES_NOT_EXIST.getMessage()));
		if (exitingNotice.getEndDateTime() != null) {
			if (exitingNotice.getEndDateTime().before(new Date())) {
				LOGGER.info(HttpResponse.NOTICE_IS_EXPIRED.getMessage());
				throw new NoticeManagementException(HttpResponse.NOTICE_IS_EXPIRED.getMessage());
			}
		}
		if (exitingNotice.isAchieved()) {
			LOGGER.info(HttpResponse.NOTICE_IS_ACHIEVED.getMessage());
			throw new NoticeManagementException(HttpResponse.NOTICE_IS_ACHIEVED.getMessage());
		}

		NoticeResponse result = mapper.toDto(exitingNotice);
		result.setAuthor(exitingNotice.getUser().getUsername());
		setNoticeIdForAttachments(result);
		// Increase number of views
		exitingNotice.setNumberOfView(exitingNotice.getNumberOfView() + 1);
		repository.saveAndFlush(exitingNotice);
		LOGGER.info("End method get by id");
		return result;
	}

	/**
	 * Get all notice by page without expire or achieve notice
	 * 
	 * @param page, size
	 * @return
	 * @throws NoticeManagementException
	 */
	public Page<NoticeResponse> getAllByPage(int page, int size) throws NoticeManagementException {
		LOGGER.info("Start to call method get all by page");

		Pageable paging = PageRequest.of(page, size);
		Page<Notice> exitingNotices = repository.findAllByIsAchievedFalse(paging);
		exitingNotices.forEach(notice -> {
			// Increase number of views
			notice.setNumberOfView(notice.getNumberOfView() + 1);
			repository.saveAndFlush(notice);
		});
		List<NoticeResponse> listRes = mapper.toDtoList(exitingNotices.toList());
		listRes.forEach(item -> {
			setNoticeIdForAttachments(item);
			for (Notice notice : exitingNotices) {
				item.setAuthor(notice.getUser().getUsername());
			}
		});
		Page<NoticeResponse> result = new PageImpl<NoticeResponse>(listRes);
		LOGGER.info("End method get by id");
		return result;
	}

	/**
	 * Create notice with attachment files and the request Attachment files will be
	 * stored to src/main/resource/attachments
	 * 
	 * @param files, request
	 * @return
	 * @throws NoticeManagementException
	 */
	public NoticeResponse create(List<MultipartFile> files, NoticeRequest request) throws NoticeManagementException {
		LOGGER.info("Start to call method create notice");
		NoticeResponse response = valdateInputRequest(request, files);
		if(!response.getMessage().equalsIgnoreCase(HttpResponse.SUCCESS.getMessage())) {
			return response;
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		Notice notice = new Notice();
		BeanUtils.copyProperties(request, notice);
		notice.setUser(userRepository.findByUsername(username).get());
		notice.setNumberOfView(0L);
		notice.setEndDateTime(convertStringToDate(request.getEndDateTime()));
		notice.setStartDateTime(convertStringToDate(request.getStartDateTime()));
		Notice result = repository.save(notice);
		try {
			attachmentService.addAttachments(files, result);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			return response;
		}
		response = mapper.toDto(result);
		response.setAuthor(username);
		response.setRegistionDate(result.getStartDateTime());
		response.setMessage(HttpResponse.NOTICE_CREATE_SUCCESSFULLY.getMessage());

		LOGGER.info("End method create notice");
		return response;
	}

	public NoticeResponse update(List<MultipartFile> files, NoticeRequest request)
			throws NoticeManagementException, ResourceNotFoundException {
		LOGGER.info("Start to call method update notice");
		NoticeResponse response = valdateInputRequest(request, files);
		if(!response.getMessage().equalsIgnoreCase(HttpResponse.SUCCESS.getMessage())) {
			return response;
		}
		Notice exitingNotice = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException(HttpResponse.NOTICE_DOES_NOT_EXIST.getMessage()));
		// Copy data from request to existing notice
		BeanUtils.copyProperties(request, exitingNotice);
		exitingNotice.setEndDateTime(convertStringToDate(request.getEndDateTime()));
		exitingNotice.setStartDateTime(convertStringToDate(request.getStartDateTime()));
		Notice updatedData = repository.saveAndFlush(exitingNotice);
		try {
			attachmentService.addAttachments(files, updatedData);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			return response;
		}
		response = mapper.toDto(updatedData);
		setNoticeIdForAttachments(response);
		response.setMessage(HttpResponse.UPDATE_SUCCESSFULLY.getMessage());
		LOGGER.info("End to call method update notice");
		return response;
	}

	public NoticeResponse delete(Long id) throws NoticeManagementException, ResourceNotFoundException {
		LOGGER.info("Start to call method delete notice");
		NoticeResponse response = new NoticeResponse();
		Notice exitingNotice = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(HttpResponse.NOTICE_DOES_NOT_EXIST.getMessage()));
		if (exitingNotice.isAchieved()) {
			LOGGER.info("Notice with id {} is achieved,", id);
			response.setMessage(HttpResponse.NOTICE_IS_ACHIEVED.getMessage());
			return response;
		}
		exitingNotice.setIsAchieved(true);
		Notice updatedData = repository.saveAndFlush(exitingNotice);
		response = mapper.toDto(updatedData);
		setNoticeIdForAttachments(response);
		response.setMessage(HttpResponse.SOFT_DELETE_SUCCESSFULLY.getMessage());
		LOGGER.info("End to call method delete notice");
		return response;
	}

	private NoticeResponse valdateInputRequest(NoticeRequest request, List<MultipartFile> attachments)
			throws NoticeManagementException {
		NoticeResponse res = new NoticeResponse();
		res.setMessage(HttpResponse.SUCCESS.getMessage());
		if (StringUtils.isEmpty(request.getContent())) {
			res.setMessage(HttpResponse.EMPTY_CONTENT_ERROR.getMessage());
			return res;
		}
		if (StringUtils.isEmpty(request.getTitle())) {
			res.setMessage(HttpResponse.EMPTY_TITLE_ERROR.getMessage());
			return res;
		}
		LocalDate requestDate1 = null;
		LocalDate requestDate2 = null;
		try {
			if (!StringUtils.isBlank(request.getEndDateTime())) {
				requestDate1 = LocalDate.parse(request.getEndDateTime(), format);
			}
			if (!StringUtils.isBlank(request.getStartDateTime())) {
				requestDate2 = LocalDate.parse(request.getStartDateTime(), format);
			}
		} catch (Exception e) {
			res.setMessage(HttpResponse.INVALID_DATETIME_FORMAT.getMessage());
			return res;
		}
		if (requestDate1 != null && requestDate1.isBefore(LocalDate.now())) {
			LOGGER.info(HttpResponse.END_DATETIME_CHECK_FAILED.getMessage());
			res.setMessage(HttpResponse.END_DATETIME_CHECK_FAILED.getMessage());
			return res;
		}
		if (requestDate2 != null && requestDate2.isBefore(LocalDate.now())) {
			LOGGER.info(HttpResponse.START_DATETIME_CHECK_FAILED.getMessage());
			res.setMessage(HttpResponse.START_DATETIME_CHECK_FAILED.getMessage());
			return res;
		}
		res.setMessage(HttpResponse.SUCCESS.getMessage());
		if (attachments != null && attachments.size() >= 10) {
			res.setMessage(HttpResponse.FILE_LENGTH_ERROR.getMessage());
			return res;
		}
		return res;
	}

	private void setNoticeIdForAttachments(NoticeResponse noticeResponse) {
		noticeResponse.getAttachments().forEach(item -> {
			item.setNoticeId(noticeResponse.getId());
		});
	}

	private Date convertStringToDate(String dateString) {
		if (StringUtils.isNotBlank(dateString)) {
			// Convert String to date with format yyyy-MM-dd
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TIMESTAMP_PATTERN);
			try {
				return dateFormat.parse(dateString);
			} catch (ParseException e) {
				logger.error("Date is not formatted as yyyy-MM-dd");
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

}
