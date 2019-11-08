package au.com.gsn.outlook.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "GSN_OUTLOOK_AUDIT_LOG_CBMNG")
public class OutlookAuditTable implements Serializable{
	
	private static final long serialVersionUID = -5256544198309693357L;
	@Id
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	@Column(name = "LOG_ID")
	private Integer logId;
	
	@Column(name = "RECORD_ID")
	private Integer recordId;
	
	@Column(name = "CAMPAIGN")
	private String campaign;
	
	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "SCHEDULED_TIME")
	private int scheduledTime;
	
	@Column(name = "EVENT_TIME")
	private Date eventTime;
	
	@Column(name = "API_RESPONSE_ID")
	private String apiResponseId;
	
	@Column(name = "API_RESPONSE")
	private String apiResponse;
	
	@Column(name = "EVENT")
	private String eventType;
	
	@Column(name = "EVENT_SOURCE")
	private String eventSource;
	
	@Column(name = "TABLE_NAME")
	private String tableName;
	
	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
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

	public String getApiResponseId() {
		return apiResponseId;
	}

	public void setApiResponseId(String apiResponseId) {
		this.apiResponseId = apiResponseId;
	}

	public String getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(String apiResponse) {
		this.apiResponse = apiResponse;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	public int getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(int scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
