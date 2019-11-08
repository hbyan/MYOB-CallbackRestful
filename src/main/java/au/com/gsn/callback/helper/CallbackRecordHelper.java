/*package au.com.gsn.callback.helper;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.db.impl.CBTableDAOImpl;
import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.model.TimezoneEnum;
import au.com.gsn.callback.model.UpdateRecordResponse;

@Component
public class CallbackRecordHelper {
	
	private static final String RECORD_STATUS_CLOSED = "3";
	
	@Autowired
	private CBTableDAOImpl CBTableDAOImpl;
	
	public UpdateRecordResponse rescheduleRecord(Session session, CallbackRequestVO callback, long newScheduleTime,
			String newTZ) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		try {
			int timezoneId = TimezoneEnum.valueOf(newTZ).getId();
			int updateCount = CBTableDAOImpl.rescheduleRecord(session, callback.getTableClassName(), callback.getRecordID(), newScheduleTime, timezoneId);
			populateResponse(response, UpdateRecordAction.RESCHEDULE, updateCount, );
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.RESCHEDULE, callback.getScheduledTime()+","+callback.getTimezone(),
					newScheduleTime+","+newTZ, UpdateRecordResponseCode.SUCCESS.name());
			return response;
		} catch (Exception e) {
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.RESCHEDULE, callback.getScheduledTime()+","+callback.getTimezone(),
					newScheduleTime+","+newTZ, e.getMessage());
		}
		return response;
	}
	
	private void populateResponse(UpdateRecordResponse response, UpdateRecordAction action, int updateCount, ) {
		response.setAction(action.name());
		if(updateCount == 1){
			response.set
		}
	}

	public int asapRecord(Session session, CallbackRequestVO callback, long newScheduleTime) {
		int updateCount = -1;
		try {
			updateCount = CBTableDAOImpl.asapRecord(session, callback.getTableClassName(), callback.getRecordID(), newScheduleTime);
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CALL_NOW, callback.getScheduledTime()+"",
					newScheduleTime+"", UpdateRecordResponseCode.SUCCESS.name());
			return updateCount;
		} catch (Exception e) {
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CALL_NOW, callback.getScheduledTime()+"",
					newScheduleTime+"", e.getMessage());
		}
		return updateCount;
	}
	
	public int closeRecord(Session session, CallbackRequestVO callback) {
		int updateCount = -1;
		try {
			updateCount = CBTableDAOImpl.closeRecord(session, callback.getTableClassName(), callback.getRecordID());
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CLOSE, callback.getRecordStatus(),
					RECORD_STATUS_CLOSED, UpdateRecordResponseCode.SUCCESS.name());
			return updateCount;
		} catch (Exception e) {
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CLOSE, callback.getRecordStatus(),
					RECORD_STATUS_CLOSED, e.getMessage());
		}
		return updateCount;
	}
	public int changeTZ(Session session, CallbackRequestVO callback, String newTZ) {
		int updateCount = -1;
		try {
			int timezoneId = TimezoneEnum.valueOf(newTZ).getId();
			updateCount = CBTableDAOImpl.changeTimezone(session, callback.getTableClassName(), callback.getRecordID(), timezoneId);
			return updateCount;
		} catch (Exception e) {
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CHANGE_TIMEZONE, callback.getTimezone(),
					newTZ, e.getMessage());
		}
		return updateCount;
	}
	
	public int changeLeadScore(Session session, CallbackRequestVO callback, String leadScore) {
		int updateCount = -1;
		try {
			updateCount = CBTableDAOImpl.changeLeadScore(session, callback.getTableClassName(), callback.getRecordID(), leadScore);
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CHANGE_LEADSCORE, callback.getLeadScore(),
					leadScore, UpdateRecordResponseCode.SUCCESS.name());
			return updateCount;
		} catch (Exception e) {
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CHANGE_LEADSCORE, callback.getLeadScore(),
					leadScore, e.getMessage());
		}
		return updateCount;
	}

	public int changeNote(Session session, CallbackRequestVO callback) {
		int updateCount = -1;
		try {
			updateCount =  CBTableDAOImpl.changeNote(session, callback.getTableClassName(), callback.getRecordID(), callback.getNote());
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CHANGE_NOTE, "", callback.getNote(),
					UpdateRecordResponseCode.SUCCESS.name());
			return updateCount;
		} catch (Exception e) {
			AuditLogHelper.createAuditLog(session, callback, UpdateRecordAction.CHANGE_NOTE, "", callback.getNote(),
					e.getMessage());
		}
		return updateCount;
	}

}
*/