package au.com.gsn.callback.tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "GSN_CALLBACK_AUDIT_LOG")
public class AuditLogTable {
	
	private static final long serialVersionUID = -5256544198309693357L;
	@Id
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	@Column(name = "LOG_ID")
	private int logId;
	
	@Column(name = "RECORD_ID")
	private Integer recordId;
	
	@Column(name = "EVENT_TIME")
	private Date eventTime;
	
	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "EVENT")
	private String event;
	
	@Column(name = "CAMPAIGN")
	private String campaign;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "SURNAME")
	private String surname;
	
	@Column(name = "ORIGINAL_DATE")
	private int originalDate;
	
	@Column(name = "NEW_DATE")
	private int newDate;
	
	@Column(name = "outcome")
	private String outcome;
	
	@Column(name = "old_value")
	private String oldValue;
	
	@Column(name = "new_value")
	private String newValue;
	
	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public Date getEventTime() {
		return eventTime;
	}
	
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(int originalDate) {
		this.originalDate = originalDate;
	}

	public int getNewDate() {
		return newDate;
	}

	public void setNewDate(int newDate) {
		this.newDate = newDate;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}
