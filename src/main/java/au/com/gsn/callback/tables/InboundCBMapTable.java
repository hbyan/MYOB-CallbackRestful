package au.com.gsn.callback.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MYOB_INBOUND_CALLBACK_MAP")
public class InboundCBMapTable implements Serializable{
	
	private static final long serialVersionUID = -1965729836130082931L;

	@Id
	@NotNull
	@Column(name = "ROW_ID")
	private String rowId;
	
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

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public String getCallingListName() {
		return callingListName;
	}

	public void setCallingListName(String callingListName) {
		this.callingListName = callingListName;
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
