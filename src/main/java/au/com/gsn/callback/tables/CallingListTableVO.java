package au.com.gsn.callback.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
public class CallingListTableVO {

	private int recordId;

	private int chainId;

	private String firstName;

	private String lastName;

	private String compaignName;

	private String state;

	private int dialSchedTime;

	private String preferredPhNo;

	private String agentId;

	private int recordType;

	private String recordStatus;

	private int attempt;

	private String accountName;

	private String serialNum;

	private String clientId;

	private int timezoneId;

	private String closeStatus;

	private String notes;

	private String leadScore;

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
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

	public String getCompaignName() {
		return compaignName;
	}

	public void setCompaignName(String compaignName) {
		this.compaignName = compaignName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getDialSchedTime() {
		return dialSchedTime;
	}

	public void setDialSchedTime(int dialSchedTime) {
		this.dialSchedTime = dialSchedTime;
	}

	public String getPreferredPhNo() {
		return preferredPhNo;
	}

	public void setPreferredPhNo(String preferredPhNo) {
		this.preferredPhNo = preferredPhNo;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public int getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(int timezoneId) {
		this.timezoneId = timezoneId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
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

}
