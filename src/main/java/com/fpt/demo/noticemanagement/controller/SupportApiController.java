package com.fpt.demo.noticemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.demo.noticemanagement.constant.HttpResponse;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.mapper.RoleMapper;
import com.fpt.demo.noticemanagement.model.Message;
import com.fpt.demo.noticemanagement.model.RoleDto;
import com.fpt.demo.noticemanagement.repository.RoleRepository;

/**
 * @author DuyHT7
 */

@RestController
@Validated
@RequestMapping("/notice-management/role")
public class SupportApiController {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleMapper mapper;

	@PostMapping
	public ResponseEntity<?> createRole(@Validated @RequestBody RoleDto dto) throws NoticeManagementException {
		if (roleRepository.findByName(dto.getName()).isPresent()) {
			return ResponseEntity.ok(new Message(HttpResponse.ROLE_EXISTED.getCode()));
		}
		roleRepository.save(mapper.toEntity(dto));
		return ResponseEntity.status(HttpStatus.OK).body(HttpResponse.REGISTER_SUCCESSFULLY.getCode());
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(roleRepository.findAll());
	}
}
