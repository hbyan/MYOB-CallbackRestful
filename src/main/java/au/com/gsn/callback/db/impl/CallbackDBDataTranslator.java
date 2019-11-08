package au.com.gsn.callback.db.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import au.com.gsn.callback.model.CallbackListVO;
import au.com.gsn.callback.model.CallbackResponseVO;
import au.com.gsn.callback.model.TimezoneEnum;
import au.com.gsn.callback.tables.CallingListTable;
import au.com.gsn.callback.tables.CallingListTableVO;
import au.com.gsn.callback.utils.CallbackUtils;

public class CallbackDBDataTranslator {

	public static CallbackListVO translateDBRecords(List<CallingListTableVO> list, String tableClassName) {
		CallbackListVO vo = new CallbackListVO();
		List<CallbackResponseVO> callbackVOs = new ArrayList<CallbackResponseVO>();
		if (list == null || list.size() == 0) {
			return vo;
		}
		
		for(CallingListTableVO record:list){
			callbackVOs.add(getCallbackVO(record, tableClassName));	
		}
		vo.setCallbackListVO(callbackVOs);
		return vo;
	}

	public static CallbackResponseVO getCallbackVO(CallingListTableVO record, String tableClassName) {
        
		CallbackResponseVO callbackVO = new CallbackResponseVO();
		if(record == null){
			return callbackVO;
		}
		callbackVO.setId(record.getRecordId());
		callbackVO.setCampaign(record.getCompaignName());
		callbackVO.setAgentId(record.getAgentId());
		callbackVO.setScheduledTime(record.getDialSchedTime());
		callbackVO.setDate(CallbackUtils.convertSchedTimeToDate(record.getDialSchedTime()));
		callbackVO.setTime(CallbackUtils.convertSchedTimeToTime(record.getDialSchedTime()));
		callbackVO.setDateTime(CallbackUtils.convertSchedTime(record.getDialSchedTime()));
		callbackVO.setFirstName(record.getFirstName());
		callbackVO.setLastName(record.getLastName());
		callbackVO.setPreferredPhNo(record.getPreferredPhNo());
		callbackVO.setState(record.getState());
		callbackVO.setSiebelClientId(record.getClientId());
		callbackVO.setTableClassName(tableClassName);
		callbackVO.setRecordStatus(getStatusByKey(record.getRecordStatus()));
		callbackVO.setNotes(record.getNotes());
		callbackVO.setLeadScore(record.getLeadScore());
		callbackVO.setAttempts(""+record.getAttempt());
		callbackVO.setAccountName(record.getAccountName());
		callbackVO.setSerialNum(record.getSerialNum());
		callbackVO.setTimezone(TimezoneEnum.getName(record.getTimezoneId()));
		callbackVO.setChainId(""+record.getChainId());
		
		return callbackVO;
	}
	
	private static String getStatusByKey(String key){
		  
		HashMap<String, String> newMap = new HashMap<String, String>();
		newMap.put("0", "No Record Status");
		newMap.put("1", "Ready");
		newMap.put("2", "Retrieved");
		newMap.put("3", "Updated");
		newMap.put("4", "Stale");
		newMap.put("5", "Cancelled");
		newMap.put("6", "Agent Error");
		newMap.put("7", "Chain Updated");
		newMap.put("8", "Missed Callback");
		newMap.put("9", "Chain Ready");
		newMap.put("10", "Delegated");
		String value = (String) newMap.get(key);
		return value;
	}
}
