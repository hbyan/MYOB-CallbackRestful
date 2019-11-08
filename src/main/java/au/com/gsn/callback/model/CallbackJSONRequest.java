package au.com.gsn.callback.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallbackJSONRequest {
	
	@JsonProperty("GSW_AGENT_ID")
	private String gswAgentId;	
	@JsonProperty("GSW_CAMPAIGN_NAME")
	private String gswCampaignName;	
	@JsonProperty("GSW_PHONE")
	private String gswPhone;
	@JsonProperty("GSW_PHONE_TYPE")
	private String gswPhoneType;
	@JsonProperty("GSW_TZ_NAME")
	private String gswTimezone;
	@JsonProperty("GSW_RECORD_TYPE")
	private String gswRecordType;
	@JsonProperty("GSW_DATE_TIME")
	private String gswDateTime;
	@JsonProperty("STATE")
	private String state;
	@JsonProperty("CONTACT_LAST_NAME")
	private String lastName;
	@JsonProperty("CONTACT_FST_NAME")
	private String firstName;
	@JsonProperty("client-id")
	private String clientId;
	@JsonProperty("VAR_005")
	private String connId;
	
	public String getGswAgentId() {
		return gswAgentId;
	}
	public void setGswAgentId(String gswAgentId) {
		this.gswAgentId = gswAgentId;
	}
	public String getGswCampaignName() {
		return gswCampaignName;
	}
	public void setGswCampaignName(String gswCampaignName) {
		this.gswCampaignName = gswCampaignName;
	}
	public String getGswPhone() {
		return gswPhone;
	}
	public void setGswPhone(String gswPhone) {
		this.gswPhone = gswPhone;
	}
	public String getGswPhoneType() {
		return gswPhoneType;
	}
	public void setGswPhoneType(String gswPhoneType) {
		this.gswPhoneType = gswPhoneType;
	}
	public String getGswTimezone() {
		return gswTimezone;
	}
	public void setGswTimezone(String gswTimezone) {
		this.gswTimezone = gswTimezone;
	}
	public String getGswRecordType() {
		return gswRecordType;
	}
	public void setGswRecordType(String gswRecordType) {
		this.gswRecordType = gswRecordType;
	}
	public String getGswDateTime() {
		return gswDateTime;
	}
	public void setGswDateTime(String gswDateTime) {
		this.gswDateTime = gswDateTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getConnId() {
		return connId;
	}
	public void setConnId(String connId) {
		this.connId = connId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
