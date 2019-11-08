package au.com.gsn.outlook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarResponse {

	@JsonProperty("@odata.context")
	private String odataContext;

	@JsonProperty("id")
	private String id;

	@JsonProperty("createdDateTime")
	private String createdDateTime;
	
	private String apiResponseCode;
	private String apiErrorCode;
	private String apiErrorMsg;

	public String getOdataContext() {
		return odataContext;
	}

	public void setOdataContext(String odataContext) {
		this.odataContext = odataContext;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getApiResponseCode() {
		return apiResponseCode;
	}

	public void setApiResponseCode(String apiResponseCode) {
		this.apiResponseCode = apiResponseCode;
	}

	
	public String getApiErrorCode() {
		return apiErrorCode;
	}

	public void setApiErrorCode(String apiErrorCode) {
		this.apiErrorCode = apiErrorCode;
	}

	public String getApiErrorMsg() {
		return apiErrorMsg;
	}

	public void setApiErrorMsg(String apiErrorMsg) {
		this.apiErrorMsg = apiErrorMsg;
	}

	
}
