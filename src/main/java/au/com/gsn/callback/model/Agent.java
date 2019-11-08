package au.com.gsn.callback.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Agent {
	
	@JsonProperty("agentId")
	private String agentId;
	
	@JsonProperty("isSupervisor")
	private Boolean isSupervisor;
	
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public Boolean getIsSupervisor() {
		return isSupervisor;
	}
	public void setIsSupervisor(Boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

}
