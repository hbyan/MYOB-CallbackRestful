package au.com.gsn.callback.db.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.helper.UpdateRecordAction;
import au.com.gsn.callback.helper.UpdateRecordResponseCode;
import au.com.gsn.callback.model.UpdateRecordResponse;
import au.com.gsn.callback.utils.CallbackUtils;

@Component
public class CBTableDAOImpl {
	
	private static Logger LOGGER = LogManager.getLogger("au.com.gsn.callbackmanager");
	
	public final static String VAR_CB_CLOSE_FROM = "VAR_020";
	public final static String VAR_CB_NOTES = "VAR_019";
	public final static String VAR_CB_LEAD_SCORE = "VAR_018";
	public final static String CLOSE_FROM_CB_VALUE = "Closed from Callback";

	public UpdateRecordResponse rescheduleRecord(Session session, String tableName, int recordId, long newScheduleTime, int timezone)
			 {
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setRecordId(recordId+"");
		response.setAction(UpdateRecordAction.RESCHEDULE.name());
		try {
			if (newScheduleTime == 0 || recordId == 0 || timezone == 0) {
				populateValidationResponse(response);
				return response;
			}
			String query = String.format(
					"update %s set  record_status = 1, DIAL_SCHED_TIME= %s, tz_dbid = %s where RECORD_ID = %s and %s",
					tableName, newScheduleTime, timezone, recordId, getRecordQuery());
			SQLQuery sqlQuery = session.createSQLQuery(query);
			int updateCount = sqlQuery.executeUpdate();
			populateQueryResponse(response, updateCount);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			populateExceptionResponse(response, e.getMessage());
		}
		return response;
	}
	public UpdateRecordResponse asapRecord(Session session, String tableName, int recordId, long newScheduleTime) {
		
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setRecordId(recordId+"");
		response.setAction(UpdateRecordAction.CALL_NOW.name());
		try {
			if (newScheduleTime == 0 || recordId == 0) {
				populateValidationResponse(response);
			}
			String query = String.format(
					"update %s set  record_status = 1, DIAL_SCHED_TIME= %s where RECORD_ID = %s and %s", tableName,
					newScheduleTime, recordId, getRecordQuery());
			SQLQuery sqlQuery = session.createSQLQuery(query);
			int updateCount = sqlQuery.executeUpdate();
			populateQueryResponse(response, updateCount);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			populateExceptionResponse(response, e.getMessage());
		}
		return response;
	}

	public UpdateRecordResponse closeRecord(Session session, String tableName, int recordId) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setRecordId(recordId+"");
		response.setAction(UpdateRecordAction.CLOSE.name());
		try {
			String query = String.format("update %s set call_result=33,  record_status = 3, %s='%s'  where RECORD_ID = %s",
					tableName, VAR_CB_CLOSE_FROM, CLOSE_FROM_CB_VALUE, recordId, getRecordQuery());
			SQLQuery sqlQuery = session.createSQLQuery(query);
			int updateCount = sqlQuery.executeUpdate();
			populateQueryResponse(response, updateCount);
		} catch (Exception e) {
			populateExceptionResponse(response, e.getMessage());
		}
		return response;
	}

	public UpdateRecordResponse changeTimezone(Session session, String tableClassName, int recordId, int timezoneId) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setTableName(tableClassName);
		//String recordIdStr = getRecordIdStr(recordIds);
		response.setRecordId(recordId+"");
		response.setAction(UpdateRecordAction.CHANGE_TIMEZONE.name());
		try {
			if (timezoneId == 0) {
				populateValidationResponse(response);
				return response;
			}
			String query = String.format("update %s set tz_dbid = %s where RECORD_ID = %s and %s", tableClassName,
					timezoneId, recordId, getRecordQuery());
			SQLQuery sqlQuery = session.createSQLQuery(query);
			int updateCount = sqlQuery.executeUpdate();
			populateQueryResponse(response, updateCount);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			populateExceptionResponse(response, e.getMessage());
		}
		return response;
	}

	private String getRecordIdStr(List<String> recordIds) {
		if(recordIds == null || recordIds.size() == 0){
			return "";
		}
		String recordIdStr = "(";
		int count =0;
		for(String recordId : recordIds){
			if(CallbackUtils.isEmptyString(recordId)){
				continue;
			}
			if(count !=0){
				recordIdStr += ",";
			}
			recordIdStr += recordId;
			count++;
		}
		recordIdStr += ")";
		return recordIdStr;
	}
	public UpdateRecordResponse changeNote(Session session, String tableClassName, int recordId, String note) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setRecordId(recordId+"");
		response.setAction(UpdateRecordAction.CHANGE_NOTE.name());
		try {
			if (CallbackUtils.isEmptyString(note)) {
				populateValidationResponse(response);
				return response;
			}
			String query = String.format("update %s set %s = '%s' where RECORD_ID = %s and %s", tableClassName,
					VAR_CB_NOTES, note, recordId, getRecordQuery());
			SQLQuery sqlQuery = session.createSQLQuery(query);
			int updateCount = sqlQuery.executeUpdate();
			populateQueryResponse(response, updateCount);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			populateExceptionResponse(response, e.getMessage());
		}
		return response;
	}

	public UpdateRecordResponse changeLeadScore(Session session, String tableClassName, int recordId, String leadScord) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setRecordId(recordId+"");
		response.setAction(UpdateRecordAction.CHANGE_LEADSCORE.name());
		int updateCount = -1;
		try {
			if (CallbackUtils.isEmptyString(leadScord)) {
				populateValidationResponse(response);
				return response;
			}
			String query = String.format("update %s set %s = '%s' where RECORD_ID = %s and %s", tableClassName,
					VAR_CB_LEAD_SCORE, leadScord, recordId, getRecordQuery());
			SQLQuery sqlQuery = session.createSQLQuery(query);
			updateCount = sqlQuery.executeUpdate();
			populateQueryResponse(response, updateCount);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			populateExceptionResponse(response, e.getMessage());
		}
		return response;
	}

	public String getRecordQuery() {
		return " (record_status != 2 and (record_status != 3 or call_result = 47) and (record_type = 5  OR  record_type = 4))";
	}
	
	
	private void populateValidationResponse(UpdateRecordResponse response) {
		response.setResponseCode(UpdateRecordResponseCode.FAIL_VALIDATION.name());
		response.setLogMessage(String.format("Fail to validate reschedule"));
	}
	private void populateQueryResponse(UpdateRecordResponse response, int updateCount) {
		if(updateCount >= 1){
			response.setSuccessNum(updateCount+"");
			response.setResponseCode(UpdateRecordResponseCode.SUCCESS.name());
		}else{
			response.setResponseCode(UpdateRecordResponseCode.FAIL_RECORD_STATUS.name());
		}
	}
	private void populateExceptionResponse(UpdateRecordResponse response, String exceptionMsg){
		response.setResponseCode(UpdateRecordResponseCode.FAIL.name());
		response.setLogMessage(CallbackUtils.cutString(exceptionMsg,30));
	}
}
