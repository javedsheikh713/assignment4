package javed.assignment5.response;

public class Error {

	private String code;
	private String description;
	
	public Error(String errorCode,String errorDescription) {
		this.code=errorCode;
		this.description=errorDescription;
	}

	public Error() {}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
