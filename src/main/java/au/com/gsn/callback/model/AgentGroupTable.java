package au.com.gsn.callback.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentGroupTable {
	
	@JsonProperty("agentGroup")
	private String agentGroupName;
	
	@JsonProperty("tableNames")
	private List<String> tableNames = new ArrayList<String>();
	
	@JsonProperty("agents")
	private List<Agent> agents = new ArrayList<Agent>();
	
	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public String getAgentGroupName() {
		return agentGroupName;
	}

	public void setAgentGroupName(String agentGroupName) {
		this.agentGroupName = agentGroupName;
	}
	
	
}
