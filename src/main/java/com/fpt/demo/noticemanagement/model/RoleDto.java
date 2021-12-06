package com.fpt.demo.noticemanagement.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DuyHT7
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleDto other = (RoleDto) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name);
	}

}
