package au.com.gsn.callback.controller;

import java.io.Serializable;

public class InboundCallbackMap implements Serializable{
	
	private static final long serialVersionUID = 9071299059421188674L;
	
	private String campaignName;
	private String callingListName;
	private String activeFlag;
	private String friendlyName;
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getCallingListName() {
		return callingListName;
	}
	public void setCallingListName(String callingListName) {
		this.callingListName = callingListName;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	
}
