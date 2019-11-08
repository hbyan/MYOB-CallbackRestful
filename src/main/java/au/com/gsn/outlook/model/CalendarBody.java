package au.com.gsn.outlook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarBody {
	
	@JsonProperty("contentType")
	private String contentType;
	
	
	@JsonProperty("content")
	private String content;


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

}
