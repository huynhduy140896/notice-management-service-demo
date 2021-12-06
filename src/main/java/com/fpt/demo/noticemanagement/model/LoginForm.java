package com.fpt.demo.noticemanagement.model;

import java.util.Objects;

import org.springframework.lang.NonNull;

/**
 * @author DuyHT7
 */
public class LoginForm {

	@NonNull
	private String username;

	@NonNull
	private String password;

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

	public LoginForm(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginForm other = (LoginForm) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
}
