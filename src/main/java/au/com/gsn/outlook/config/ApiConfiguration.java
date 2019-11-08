package au.com.gsn.outlook.config;

public class ApiConfiguration {
	
	private int connectionTimeout;
	private int readTimeout;
	private String apiBaseUrl;
	private String content;
	private String subject;
	private String contentType;
	private int calEndTimeThreshod;
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	public String getApiBaseUrl() {
		return apiBaseUrl;
	}
	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getCalEndTimeThreshod() {
		return calEndTimeThreshod;
	}
	public void setCalEndTimeThreshod(int calEndTimeThreshod) {
		this.calEndTimeThreshod = calEndTimeThreshod;
	}
	
}
