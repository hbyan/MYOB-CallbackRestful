package au.com.gsn.callback.model;

import java.util.ArrayList;
import java.util.List;

public class AgentGroupTableList {
	
	private List<TableClassName> tableClassNames = new ArrayList<TableClassName>();
	private List<AgentGroupTable> agentGroupTables = new ArrayList<AgentGroupTable>();

	public List<AgentGroupTable> getAgentGroupTables() {
		return agentGroupTables;
	}

	public void setAgentGroupTables(List<AgentGroupTable> agentGroupTables) {
		this.agentGroupTables = agentGroupTables;
	}

	public List<TableClassName> getTableClassNames() {
		return tableClassNames;
	}

	public void setTableClassNames(List<TableClassName> tableClassNames) {
		this.tableClassNames = tableClassNames;
	}
	
}
