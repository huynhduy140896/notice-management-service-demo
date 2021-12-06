package com.fpt.demo.noticemanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.demo.noticemanagement.constant.HttpResponse;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.model.TokenResponse;
import com.fpt.demo.noticemanagement.model.LoginForm;
import com.fpt.demo.noticemanagement.model.Message;
import com.fpt.demo.noticemanagement.model.RegisterForm;
import com.fpt.demo.noticemanagement.security.JwtUtils;
import com.fpt.demo.noticemanagement.security.UserDetailsImpl;
import com.fpt.demo.noticemanagement.service.UserService;

/**
 * @author DuyHT7
 */

@RestController
@Validated
@RequestMapping("/notice-management/authen")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(HttpServletRequest httpRequest, @Validated @RequestBody LoginForm form)
			throws NoticeManagementException {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new TokenResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles.get(0)));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@Validated @RequestBody RegisterForm form) throws NoticeManagementException {
		Message message = userService.register(form);
		if(!message.getMessage().equalsIgnoreCase(HttpResponse.REGISTER_SUCCESSFULLY.getMessage())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		return ResponseEntity.ok(message);
	}
}
