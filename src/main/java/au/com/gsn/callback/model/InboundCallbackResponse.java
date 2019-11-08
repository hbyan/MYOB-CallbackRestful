package au.com.gsn.callback.model;

import java.util.ArrayList;
import java.util.List;

public class InboundCallbackResponse {
	
	private List<InboundCallbackMap> campaignList = new ArrayList<InboundCallbackMap>();
	
	private InboundCallbackMap agentInboundCBMap;
	
	public List<InboundCallbackMap> getCampaignList() {
		return campaignList;
	}

	public void setCampaignList(List<InboundCallbackMap> campaignList) {
		this.campaignList = campaignList;
	}

	public InboundCallbackMap getAgentInboundCBMap() {
		return agentInboundCBMap;
	}

	public void setAgentInboundCBMap(InboundCallbackMap agentInboundCBMap) {
		this.agentInboundCBMap = agentInboundCBMap;
	}
	
}
