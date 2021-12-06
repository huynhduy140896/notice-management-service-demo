package com.fpt.demo.noticemanagement.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DuyHT7
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

	private String token;
	private Long id;
	private String username;
	private String email;
	private String role;

	public TokenResponse(String accessToken, Long id, String username, String email, String role) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, role, token, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenResponse other = (TokenResponse) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(role, other.role)
				&& Objects.equals(token, other.token) && Objects.equals(username, other.username);
	}
}
