package com.fpt.demo.noticemanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.demo.noticemanagement.constant.HttpResponse;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.exception.ResourceNotFoundException;
import com.fpt.demo.noticemanagement.model.NoticeRequest;
import com.fpt.demo.noticemanagement.model.NoticeResponse;
import com.fpt.demo.noticemanagement.service.NoticeService;

/**
 * @author DuyHT7
 */

@RestController
@Validated
@RequestMapping("/notice-management/notice")
public class NoticeController {

	@Autowired
	private NoticeService service;

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		NoticeResponse result = new NoticeResponse();
		try {
			result = service.getNoticeById(id);
		} catch (NoticeManagementException noticeManagementException) {
			result.setStatus(HttpStatus.BAD_REQUEST.name());
			result.setMessage(noticeManagementException.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		} catch (ResourceNotFoundException noticeManagementException) {
			result.setStatus(HttpStatus.NOT_FOUND.name());
			result.setMessage(noticeManagementException.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) throws NoticeManagementException {
		try {
			Page<NoticeResponse> result = service.getAllByPage(page, size);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
		}

	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public ResponseEntity<?> create(@RequestPart(value = "files", required = false) List<MultipartFile> files,
			@RequestPart(value = "data") @Validated NoticeRequest request) {
		NoticeResponse result = new NoticeResponse();
		try {
			result = service.create(files, request);
			if (!result.getMessage().equalsIgnoreCase(HttpResponse.SUCCESS.getMessage())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}

	@PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public ResponseEntity<?> update(@RequestPart(value = "files", required = false) List<MultipartFile> files,
			@RequestPart(value = "data") @Validated NoticeRequest request) {
		NoticeResponse result = new NoticeResponse();
		try {
			result = service.update(files, request);
			if (!result.getMessage().equalsIgnoreCase(HttpResponse.SUCCESS.getMessage())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		NoticeResponse result = new NoticeResponse();
		try {
			result = service.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}
}
