package au.com.gsn.outlook.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GSN_OUTLOOK_SYNC")
public class OutlookSyncTable implements Serializable{

	private static final long serialVersionUID = 204016091047987458L;

	@Id
	@Column(name = "CALL_ID")
	private String calId;

	@Column(name = "EMAIL_ADDRESS")
	private String email;

	@Column(name = "START_TIME")
	private Date startTime;

	@Column(name = "END_TIME")
	private Date endTime;

	@Column(name = "RECORD_ID")
	private int recordId;

	@Column(name = "CAMPAIGN")
	private String campaign;
	
	@Column(name = "SCHEDULED_TIME")
	private int scheduledTime;
	
	@Column(name = "LOG_ID")
	private Integer logId;
	
	@Column(name = "TABLE_NAME")
	private String tableName;

	
	@Column(name = "EVENT_TIME")
	private Date eventTime;

	
	public String getCalId() {
		return calId;
	}

	public void setCalId(String calId) {
		this.calId = calId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(int scheduledTime) {
		this.scheduledTime = scheduledTime;
	}


	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

}
