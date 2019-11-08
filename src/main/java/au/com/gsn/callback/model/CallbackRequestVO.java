package au.com.gsn.callback.model;

import java.io.Serializable;

public class CallbackRequestVO implements Serializable{
	
	private static final long serialVersionUID = 9100638039411002233L;
	private int recordID;
	private String tableClassName;
	private int scheduledTime;
	private String notes;
	private String leadScore;
	private String agentId;
	private String campaign;
	private String recordStatus;
	private String timezone;
	private String preferredPhNo;
	private String firstName;
	private String lastName;
	
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public String getTableClassName() {
		return tableClassName;
	}
	public void setTableClassName(String tableClassName) {
		this.tableClassName = tableClassName;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getLeadScore() {
		return leadScore;
	}
	public void setLeadScore(String leadScore) {
		this.leadScore = leadScore;
	}
	
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public int getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(int scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getPreferredPhNo() {
		return preferredPhNo;
	}
	public void setPreferredPhNo(String preferredPhNo) {
		this.preferredPhNo = preferredPhNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
