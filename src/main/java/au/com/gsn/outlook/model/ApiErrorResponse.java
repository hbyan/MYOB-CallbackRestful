package au.com.gsn.outlook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiErrorResponse {
	
	@JsonProperty("error")
	private ApiErrorBody error;

	public ApiErrorBody getError() {
		return error;
	}

	public void setError(ApiErrorBody error) {
		this.error = error;
	}
	
	

}
