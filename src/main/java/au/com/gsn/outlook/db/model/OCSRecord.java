package au.com.gsn.outlook.db.model;

import java.io.Serializable;
import java.util.Date;


public class OCSRecord implements Serializable {

	private static final long serialVersionUID = -958497146956442806L;
	
	private int recordId;
	private String emailAddress;
	private int scheduledDialTime;
	private String campaign;
	private int tzId;
	private Date calStartTime;
	private Date calEndTime;
	private String calId;
	private String firstName;
	private String lastName;
	private String acctName;
	private String state;
	private String contactInfo;
	private String clientId; 
	private String tableName;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getScheduledDialTime() {
		return scheduledDialTime;
	}

	public void setScheduledDialTime(int scheduledDialTime) {
		this.scheduledDialTime = scheduledDialTime;
	}

	public int getTzId() {
		return tzId;
	}

	public void setTzId(int tzId) {
		this.tzId = tzId;
	}

	public Date getCalStartTime() {
		return calStartTime;
	}

	public void setCalStartTime(Date calStartTime) {
		this.calStartTime = calStartTime;
	}

	public Date getCalEndTime() {
		return calEndTime;
	}

	public void setCalEndTime(Date calEndTime) {
		this.calEndTime = calEndTime;
	}

	public String getCalId() {
		return calId;
	}

	public void setCalId(String calId) {
		this.calId = calId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
