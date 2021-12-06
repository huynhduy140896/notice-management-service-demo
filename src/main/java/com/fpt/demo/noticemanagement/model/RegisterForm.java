package com.fpt.demo.noticemanagement.model;

import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;

import com.fpt.demo.noticemanagement.constant.RoleEnum;

/**
 * @author DuyHT7
 */

public class RegisterForm {

	@NonNull
	private String username;

	@NonNull
	private String password;

	@NonNull
	private String email;

	private Set<RoleEnum> roles;

	public Set<RoleEnum> getRoles() {
		return roles;
	}

	public void setRole(Set<RoleEnum> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RegisterForm(String username, String password, String email, Set<RoleEnum> roles) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, password, roles, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegisterForm other = (RegisterForm) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password)
				&& Objects.equals(roles, other.roles) && Objects.equals(username, other.username);
	}

}
