package au.com.gsn.callback.helper;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.tables.AuditLogTable;
import au.com.gsn.callback.utils.CallbackUtils;

public class AuditLogHelper {

	private static Logger LOGGER = LogManager.getLogger("au.com.gsn.callbackmanager");
	
	private static final String RECORD_STATUS_CLOSED = "3";


	public static void createAuditLog(Session session, CallbackRequestVO callback, String action,
			String oldValue, String newValue, String outcome) {
		LOGGER.info(String.format("Create audit log for recordId[%s], campaign[%s], action[%s]", callback.getRecordID(),
				callback.getCampaign(), action));
		try {
			AuditLogTable table = new AuditLogTable();
			table.setAgentId(callback.getAgentId());
			table.setEvent(action);
			table.setEventTime(new Date());
			table.setCampaign(callback.getCampaign());
			table.setOldValue(oldValue);
			table.setNewValue(newValue);
			table.setOutcome(CallbackUtils.cutString(outcome, 250));
			table.setRecordId(callback.getRecordID());
			session.save(table);
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to create audit log due to [%s]", e.getMessage()));
		}
	}

	public static void createAuditLogByAction(Session session, CallbackRequestVO callback, String action, String newValue, String outcome) {
		if(UpdateRecordAction.CALL_NOW.name().equals(action)){
			AuditLogHelper.createAuditLog(session, callback, action, callback.getScheduledTime()+"",
					newValue, outcome);
		}else if(UpdateRecordAction.RESCHEDULE.name().equals(action)){
			AuditLogHelper.createAuditLog(session, callback, action, callback.getScheduledTime()+","+callback.getTimezone(),
					newValue, outcome);
		}
		else if(UpdateRecordAction.CHANGE_LEADSCORE.name().equals(action)){
			AuditLogHelper.createAuditLog(session, callback, action, callback.getLeadScore(),
					newValue, outcome);
		}
		else if(UpdateRecordAction.CHANGE_NOTE.name().equals(action)){
			AuditLogHelper.createAuditLog(session, callback, action, "",
					newValue, outcome);
		}
		else if(UpdateRecordAction.CHANGE_TIMEZONE.name().equals(action)){
			AuditLogHelper.createAuditLog(session, callback, action, callback.getTimezone(),
					newValue, outcome);
		}
		else if(UpdateRecordAction.CLOSE.name().equals(action)){
			AuditLogHelper.createAuditLog(session, callback, action, callback.getRecordStatus(),
					RECORD_STATUS_CLOSED, outcome);
		}
	}
}
