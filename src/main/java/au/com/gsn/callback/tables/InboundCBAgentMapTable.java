package au.com.gsn.callback.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MYOB_INBOUND_PCB_AGENT_MAP")
public class InboundCBAgentMapTable implements Serializable {

	private static final long serialVersionUID = 8435750207306245631L;

	@Id
	@NotNull
	@Column(name = "ROW_ID")
	private String rowId;

	@Column(name = "GENESYS_AGENT_ID")
	private String agentId;

	@Column(name = "CALLING_LIST_TABLE_NAME")
	private String callingListName;

	@Column(name = "FRIENDLY_NAME")
	private String friendlyName;

	@Column(name = "OCS_CAMPAIGN_ID")
	private String campaignName;

	@Column(name = "ACTIVE_FLG")
	private String activeFlag;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCallingListName() {
		return callingListName;
	}

	public void setCallingListName(String callingListName) {
		this.callingListName = callingListName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}
