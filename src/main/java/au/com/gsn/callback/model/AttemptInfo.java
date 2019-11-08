package au.com.gsn.callback.model;

public class AttemptInfo {
	
	private String callResult;
	private int interactionStartTime;
	private String dateTime;
	private String agentName;
	private String attemptOrdinal;
	private String businessResult;
	private String recordType;
	public String getCallResult() {
		return callResult;
	}
	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}
	
	public String getAgentName() {
		return agentName;
	}
	public int getInteractionStartTime() {
		return interactionStartTime;
	}
	public void setInteractionStartTime(int interactionStartTime) {
		this.interactionStartTime = interactionStartTime;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAttemptOrdinal() {
		return attemptOrdinal;
	}
	public void setAttemptOrdinal(String attemptOrdinal) {
		this.attemptOrdinal = attemptOrdinal;
	}
	public String getBusinessResult() {
		return businessResult;
	}
	public void setBusinessResult(String businessResult) {
		this.businessResult = businessResult;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
}
