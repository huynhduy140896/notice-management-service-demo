package com.fpt.demo.noticemanagement.exception;

import com.fpt.demo.noticemanagement.constant.HttpResponse;

/**
 * @author DuyHT7
 */

public class NoticeManagementException extends Exception {

	private static final long serialVersionUID = 4906208789286284503L;
	private HttpResponse errorCode;

	public NoticeManagementException(HttpResponse errorCode, String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		this.errorCode = errorCode;
	}

	public NoticeManagementException(HttpResponse errorCode, String detailMessage) {
		super(detailMessage);
		this.errorCode = errorCode;
	}

	public NoticeManagementException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NoticeManagementException(Throwable throwable) {
		super(throwable);
	}

	public NoticeManagementException(String detailMessage) {
		super(detailMessage);
	}

	public NoticeManagementException() {
	}

	/**
	 * @return the errorCode
	 */
	public HttpResponse getErrorCode() {
		return errorCode;
	}
}
