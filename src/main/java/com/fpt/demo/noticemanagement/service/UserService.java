package com.fpt.demo.noticemanagement.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fpt.demo.noticemanagement.constant.Constants;
import com.fpt.demo.noticemanagement.constant.HttpResponse;
import com.fpt.demo.noticemanagement.constant.RoleEnum;
import com.fpt.demo.noticemanagement.entity.Role;
import com.fpt.demo.noticemanagement.entity.User;
import com.fpt.demo.noticemanagement.exception.NoticeManagementException;
import com.fpt.demo.noticemanagement.model.Message;
import com.fpt.demo.noticemanagement.model.RegisterForm;
import com.fpt.demo.noticemanagement.repository.RoleRepository;
import com.fpt.demo.noticemanagement.repository.UserRepository;
import com.fpt.demo.noticemanagement.service.UserService;

/**
 * @author DuyHT7
 */

@Service
public class UserService extends BaseService<User, Long> {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	/**
	 *
	 * @param username
	 * @return
	 * @throws NoticeManagementException
	 */
	public User getUserByUsername(String username) throws NoticeManagementException {

		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			logger.warn("Can not find user by username: {}", username);
		}
		return user.get();
	}

	/**
	 *
	 * @param register form
	 * @return
	 * @throws NoticeManagementException
	 */

	public Message register(RegisterForm form) throws NoticeManagementException {
		Message message = validateInput(form);
		if(!message.getMessage().equalsIgnoreCase(HttpResponse.REGISTER_SUCCESSFULLY.getMessage())) {
			return message;
		}
		if (userRepository.existsByUsername(form.getUsername())) {
			message.setMessage(HttpResponse.USERNAME_EXISTED.getCode());
			return message;
		}

		if (userRepository.existsByEmail(form.getEmail())) {
			message.setMessage(HttpResponse.EMAIL_EXIST.getCode());
			return message;
		}

		// Create new user's account
		User user = new User();
		user.setUsername(form.getUsername());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setEmail(form.getEmail());

		Set<RoleEnum> roleNames = form.getRoles();
		Set<Role> roles = new HashSet<Role>();

		Optional<Role> userRole = Optional.empty();
		if (!CollectionUtils.isEmpty(roleNames)) {
			for (RoleEnum roleName : roleNames) {
				userRole = roleRepository.findByName(roleName.name());
				if (userRole.isEmpty()) {
					message.setMessage(HttpResponse.ROLE_NOT_FOUND.getCode());
					return message;
				}
				roles.add(userRole.get());
			}

		}
		user.setRoles(roles);
		save(user);
		return new Message(HttpResponse.REGISTER_SUCCESSFULLY.getCode());
	}
	
	private boolean validateEmailFormat(String email) {
		return Pattern.compile(Constants.EMAIL_PATTERN).matcher(email).matches();
	}
	
	private Message validateInput(RegisterForm form) {
		Message message = new Message(HttpResponse.REGISTER_SUCCESSFULLY.getMessage());
		
		if(StringUtils.isBlank(form.getUsername())) {
			message.setMessage(HttpResponse.USERNAME_NULL_ERROR.getMessage());
			return message;
		}
		if(StringUtils.isBlank(form.getPassword())) {
			message.setMessage(HttpResponse.PASSWORD_NULL_ERROR.getMessage());
			return message;
		}
		if(StringUtils.isBlank(form.getEmail())) {
			message.setMessage(HttpResponse.EMAIL_NULL_ERROR.getMessage());
			return message;
		} else if(!validateEmailFormat(form.getEmail())) {
			message.setMessage(HttpResponse.INVALID_EMAIL_FORMAT.getMessage());
			return message;
		}
		return message;
	}
}
