package javed.assignment5.exception;

import javed.assignment5.response.Error;

public class AppException extends Exception {
	
	private String code;
	private String message;
	
	public AppException(Error error) {
		super(error.getDescription());
		this.code=error.getCode();
		this.message=error.getDescription();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
