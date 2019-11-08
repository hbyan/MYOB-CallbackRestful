package au.com.gsn.outlook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarTime {
	
	@JsonProperty("dateTime")
	private String dateTime;
	
	@JsonProperty("timeZone")
	private String timeZone;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
}
