package javed.assignment5.response;

import java.io.Serializable;

public class Response implements Serializable{

	private String status;
	private Error error;
	private String successMessage;
	
	
	
	public String getStatus() {
		return status;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.status="ERROR";
		this.error = error;
	}


	public String getSuccessMessage() {
		return successMessage;
	}


	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	
	
}
