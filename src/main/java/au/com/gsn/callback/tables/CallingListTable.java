package au.com.gsn.callback.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "CallingListTable")
public class CallingListTable {
	@Id
	@NotNull
	@Column(name = "RECORD_ID")
	private int recordId;
	
	@Id
	@NotNull
	@Column(name = "CHAIN_ID")
	private int chainId;

	@Column(name = "CONTACT_FST_NAME")
	private String firstName;
	
	@Column(name = "CONTACT_LAST_NAME")
	private String lastName;

	@Column(name = "CAMPAIGN_NAME")
	private String compaignName;
	
	@Column(name = "STATE")
	private String state;

	@Column(name = "DIAL_SCHED_TIME")
	private int dialSchedTime;

	@NotNull
	@Column(name = "CONTACT_INFO")
	private String preferredPhNo;
	
	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "RECORD_TYPE")
	private int recordType;
	
	@Column(name = "RECORD_STATUS")
	private String recordStatus;
	
	@Column(name = "attempt")
	private String attempt;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;
	
	@Column(name = "ASSET_SERIAL_NUM")
	private String serialNum;
	
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "tz_dbid")
	private int timezoneId;
	
	@Column(name = "VAR_020")
	private int var20;
	
	@Column(name = "VAR_019")
	private int var19;
	
	@Column(name = "VAR_018")
	private int var18;
	
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
	public String getAttempt() {
		return attempt;
	}
	public void setAttempt(String attempt) {
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
	public int getVar20() {
		return var20;
	}
	public void setVar20(int var20) {
		this.var20 = var20;
	}
	public int getVar19() {
		return var19;
	}
	public void setVar19(int var19) {
		this.var19 = var19;
	}
	public int getVar18() {
		return var18;
	}
	public void setVar18(int var18) {
		this.var18 = var18;
	}
	
	
}
