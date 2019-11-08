package au.com.gsn.callback.model;

import java.io.Serializable;

public class CallbackResponseVO implements Serializable{
	
	private static final long serialVersionUID = 9100638039411002233L;
	private int id;
	private String accountName;
	private String serialNum;
	private String siebelClientId;
	private String firstName;
	private String lastName;
	private String state;
	private String timezone;
	private String dateTime;
	private String preferredPhNo;
	private String campaign;
	private String notes;
	private String attempts;
	private String recordStatus;
	private String leadScore;
	private int scheduledTime;
	private String date;
	private String time;
	private String callingList;
	private String agentId;
	private String tableClassName;
	private String recordType;
	private String connId;
	private String chainId;
	
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public CallbackResponseVO(){
		
	}
	public int getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(int scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public String getSiebelClientId() {
		return siebelClientId;
	}

	public void setSiebelClientId(String siebelClientId) {
		this.siebelClientId = siebelClientId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public String getCallingList() {
		return callingList;
	}
	public void setCallingList(String callingList) {
		this.callingList = callingList;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPreferredPhNo() {
		return preferredPhNo;
	}
	public void setPreferredPhNo(String preferredPhNo) {
		this.preferredPhNo = preferredPhNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getTableClassName() {
		return tableClassName;
	}
	public void setTableClassName(String tableClassName) {
		this.tableClassName = tableClassName;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getConnId() {
		return connId;
	}
	public void setConnId(String connId) {
		this.connId = connId;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAttempts() {
		return attempts;
	}
	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}
	public String getLeadScore() {
		return leadScore;
	}
	public void setLeadScore(String leadScore) {
		this.leadScore = leadScore;
	}
	public String getChainId() {
		return chainId;
	}
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}
	
}
