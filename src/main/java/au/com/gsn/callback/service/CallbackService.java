package au.com.gsn.callback.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.db.impl.CallbackDBDataTranslator;
import au.com.gsn.callback.db.impl.CallingListDAOImpl;
import au.com.gsn.callback.helper.CBOutlookHelper;
import au.com.gsn.callback.helper.CallbackActionHelper;
import au.com.gsn.callback.helper.UpdateRecordResponseCode;
import au.com.gsn.callback.model.AttemptInfo;
import au.com.gsn.callback.model.CallbackListVO;
import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.model.RecordsActionResponse;
import au.com.gsn.callback.model.UpdateRecordResponse;
import au.com.gsn.callback.tables.CallingListTableVO;
import au.com.gsn.callback.utils.CallbackUtils;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.service.OutlookSyncService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class CallbackService {
	
	private static Logger LOGGER = LogManager.getLogger(CallbackService.class);
	

	@SuppressWarnings("rawtypes")
	@Autowired
	private CallbackServiceHelper CallbackServiceHelper;
	
	@Autowired
	private CBOutlookHelper CBOutlookHelper;
	
	
	@Autowired
	private CallingListDAOImpl callingListDAOImpl;
	
	public CallbackListVO getCallbacks(String agentId, String callingList, String managedCallingList) {
		CallbackListVO combinedVO = new CallbackListVO();
		List<String> tableListForSupervisor = CallbackUtils.convertStringToList(managedCallingList);
		List<String> tableListForAgent = CallbackUtils.removeRecordFromList1ThatAlsoInList2(callingList, managedCallingList);
		
		for(String tableName:tableListForSupervisor){
			CallbackListVO vo = getCallbacksByTableClassName(tableName, agentId, true);
			combinedVO.getCallbackListVO().addAll(vo.getCallbackListVO());
		}
		
		for(String tableName:tableListForAgent){
			CallbackListVO vo = getCallbacksByTableClassName(tableName, agentId, false);
			combinedVO.getCallbackListVO().addAll(vo.getCallbackListVO());
		}
		return combinedVO;
	}
	
	public CallbackListVO getCallbacksByTableClassName(String tableName, String agentId, boolean isSupervisor) {
		CallbackListVO callbackListVO = new CallbackListVO();
		@SuppressWarnings("unchecked")
		List<CallingListTableVO> list = callingListDAOImpl.getCallingListByQuery(tableName, agentId, isSupervisor);
		callbackListVO = CallbackDBDataTranslator.translateDBRecords(list, tableName);
		return callbackListVO;
	}
	
	public RecordsActionResponse closeCallbacks(List<CallbackRequestVO> callbacks) {
		RecordsActionResponse responses =  CallbackServiceHelper.closeCBs(callbacks);
		CallbackServiceHelper.updateAuditLog(responses, callbacks, "");
		return responses;
	}
	
	public void closeCallbacksOutlook(List<CallbackRequestVO> callbacks) {
		CBOutlookHelper.syncOutlookForDelete(callbacks);
	}
	
	public RecordsActionResponse rescheduleCBs(List<CallbackRequestVO> callbacks, String date, String time,
			String timezone) {
		RecordsActionResponse responses = CallbackServiceHelper.rescheduleCBs(callbacks, date, time, timezone);
		CallbackServiceHelper.updateAuditLog(responses, callbacks, date+" "+time+" "+timezone);
		return responses;
	}
	
	public void rescheduleCBsOutlook(List<CallbackRequestVO> callbacks, String date, String time,
			String timezone) {
		UpdateRecordResponse validateResponse = CallbackActionHelper.checkCanReschedule(date, time, "0");
		if (UpdateRecordResponseCode.SUCCESS.name().equals(validateResponse.getResponseCode())) {
			CBOutlookHelper.syncOutlookForReschedule(callbacks, date, time);
		}
	}

	public RecordsActionResponse asapAll(List<CallbackRequestVO> callbacks) {
		RecordsActionResponse responses =  CallbackServiceHelper.asapCBs(callbacks);
		CallbackServiceHelper.updateAuditLog(responses, callbacks, "");
		return responses;
	}

	public RecordsActionResponse changeTZs(List<CallbackRequestVO> callbacks, String timezone) {
		RecordsActionResponse responses = CallbackServiceHelper.changeTZs(callbacks, timezone);
		CallbackServiceHelper.updateAuditLog(responses, callbacks, timezone);
		return responses;
	}

	public RecordsActionResponse changeLeadScore(List<CallbackRequestVO> callbacks, String leadScore) {
		RecordsActionResponse responses = CallbackServiceHelper.changeLeadScore(callbacks, leadScore);
		CallbackServiceHelper.updateAuditLog(responses, callbacks, leadScore);
		return responses;
	}

	public RecordsActionResponse changeNote(CallbackRequestVO callback) {
		RecordsActionResponse responses = CallbackServiceHelper.changeNote(callback);
		List<CallbackRequestVO> callbacks  =new ArrayList<CallbackRequestVO>();
		callbacks.add(callback);
		CallbackServiceHelper.updateAuditLog(responses, callbacks, callback.getNotes());
		return responses;
	}

	public List<AttemptInfo> getAttemptInfo(String chainId, String callingListName) {
		return CallbackServiceHelper.getAttemptInfo(chainId, callingListName);
	}

}
