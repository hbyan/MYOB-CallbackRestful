package au.com.gsn.callback.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.controller.InboundCallbackMap;
import au.com.gsn.callback.controller.InboundCallbackResponse;
import au.com.gsn.callback.db.impl.InboundCallbackDAOImpl;
import au.com.gsn.callback.tables.InboundCBAgentMapTable;
import au.com.gsn.callback.tables.InboundCBMapTable;

@Component
public class InboundCallbackDataHelper {
	
	@Autowired
	private InboundCallbackDAOImpl inboundCallbackDAOImpl;
	
	public InboundCallbackResponse getInboundCallbackMap(String agentId) {
		
		InboundCallbackResponse response = new InboundCallbackResponse();
		List<InboundCBAgentMapTable> agentMapTable = inboundCallbackDAOImpl.findPersonalCBByAgentId(agentId);
		InboundCallbackMap agentCBMap = new InboundCallbackMap(); 
		//TODO: what if found more than one
		for(InboundCBAgentMapTable callback:agentMapTable){
			agentCBMap.setActiveFlag(callback.getActiveFlag());
			agentCBMap.setCallingListName(callback.getCallingListName());
			agentCBMap.setCampaignName(callback.getCampaignName());
			break;
		}
		response.setAgentInboundCBMap(agentCBMap);		
		
		List<InboundCallbackMap> campaignList = new ArrayList<InboundCallbackMap>();
		List<InboundCBMapTable> campaignTableList = inboundCallbackDAOImpl.findInboundMap();
		for(InboundCBMapTable camp : campaignTableList){
			InboundCallbackMap map = new InboundCallbackMap();
			map.setActiveFlag(camp.getActiveFlag());
			map.setCallingListName(camp.getCallingListName());
			map.setCampaignName(camp.getCampaignName());
			map.setFriendlyName(camp.getFriendlyName());
			campaignList.add(map);
		}
		response.setCampaignList(campaignList);		
		return response;
	}
	
}
