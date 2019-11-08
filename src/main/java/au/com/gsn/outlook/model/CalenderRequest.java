package au.com.gsn.outlook.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class CalenderRequest {
	
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("body")
	private CalendarBody calBody;
	
	@JsonProperty("start")
	private CalendarTime calStart;
	
	@JsonProperty("end")
	private CalendarTime calEnd;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public CalendarBody getCalBody() {
		return calBody;
	}

	public void setCalBody(CalendarBody calBody) {
		this.calBody = calBody;
	}

	public CalendarTime getCalStart() {
		return calStart;
	}

	public void setCalStart(CalendarTime calStart) {
		this.calStart = calStart;
	}

	public CalendarTime getCalEnd() {
		return calEnd;
	}

	public void setCalEnd(CalendarTime calEnd) {
		this.calEnd = calEnd;
	}

}
