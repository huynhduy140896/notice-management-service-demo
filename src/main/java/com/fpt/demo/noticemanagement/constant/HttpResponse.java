package com.fpt.demo.noticemanagement.constant;

/**
 * @author DuyHT7
 */

public enum HttpResponse {

	SUCCESS("Success"), DUPLICATE_NOTICE_ID("This notice id is already created"),
	NOTICE_IS_EXPIRED("Notice is expired"), NOTICE_IS_ACHIEVED("Notice is achieved"),
	NOTICE_DOES_NOT_EXIST("Notice doesn't exist"), UPDATE_SUCCESSFULLY("Update notice successfully"),
	SOFT_DELETE_SUCCESSFULLY("Soft delete notice successfully"),
	NOTICE_CREATE_SUCCESSFULLY("Notice is created successfully"), REGISTER_SUCCESSFULLY("User register successfully"),
	USERNAME_EXISTED("Dulpicate username"), EMAIL_EXIST("Duplucate email"), ROLE_NOT_FOUND("Role not fould"),
	ROLE_EXISTED("Duplicate role name"), INPUT_VALIDATION_FAIL("Input validation fail"), BAD_REQUEST("Bad Request"),
	UNEXPECTED("Unexpected Error"), NOT_FOUND("Not Found"), VALIDATION_FAILED("Validation Failed"),
	ACCESS_DENIED("Access Denied"), INVALID_DATA("Invalid Data"), UNAUTHENTICATED("Unauthenticated"),
	BAD_CREDENTIALS("Invalid user name or password"), INVALID_TOKEN("Invalid token"), UNAUTHORIZED("Unauthorized"),
	NOT_MATCHED("Not Matched"), INVALID_DATETIME_FORMAT("Invalid date time format"),
	END_DATETIME_CHECK_FAILED("End date time can not before today"),
	START_DATETIME_CHECK_FAILED("Start date time can not in the past"),
	FILE_LENGTH_ERROR("Can not attach more than 10 files"), EMPTY_CONTENT_ERROR("Notice content can not empty"),
	EMPTY_TITLE_ERROR("Notice title can not empty"), USERNAME_NULL_ERROR("Username can not null or empty"),
	PASSWORD_NULL_ERROR("Password can not null or empty"), EMAIL_NULL_ERROR("Email can not null or empty"),
	INVALID_EMAIL_FORMAT("Invalid email format");

	private String message;

	private HttpResponse(String message) {
		this.message = message;
	}

	public String getCode() {
		return this.name();
	}

	public String getMessage() {
		return message;
	}
}
