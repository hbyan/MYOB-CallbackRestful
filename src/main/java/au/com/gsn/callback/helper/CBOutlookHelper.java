package au.com.gsn.callback.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.model.TimezoneEnum;
import au.com.gsn.callback.utils.CallbackUtils;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.service.OutlookSyncService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class CBOutlookHelper {

	private String className = "CBOutlookHelper";
	private static Logger LOGGER = LogManager.getLogger(CBOutlookHelper.class);

	@Autowired
	private OutlookSyncService outlookSyncService;

	public List<OCSRecord> convertToOCSRecords(List<CallbackRequestVO> callbacks) {
		List<OCSRecord> ocsRecords = new ArrayList<OCSRecord>();
		for (CallbackRequestVO callback : callbacks) {
			OCSRecord ocsRecord = new OCSRecord();
			ocsRecord.setRecordId(callback.getRecordID());
			ocsRecord.setCampaign(callback.getTableClassName());
			ocsRecord.setEmailAddress(callback.getAgentId());
			ocsRecord.setScheduledDialTime(callback.getScheduledTime());
			ocsRecord.setTableName(callback.getTableClassName());
			ocsRecords.add(ocsRecord);
		}
		return ocsRecords;
	}

	public List<OCSRecord> convertToOCSRecordsWithNewTime(List<CallbackRequestVO> callbacks, long newScheduledTime) {
		List<OCSRecord> ocsRecords = new ArrayList<OCSRecord>();
		for (CallbackRequestVO callback : callbacks) {
			OCSRecord ocsRecord = new OCSRecord();
			ocsRecord.setRecordId(callback.getRecordID());
			ocsRecord.setCampaign(callback.getCampaign());
			ocsRecord.setEmailAddress(callback.getAgentId());
			ocsRecord.setTzId(TimezoneEnum.getId(callback.getTimezone()));
			ocsRecord.setScheduledDialTime(Integer.parseInt(newScheduledTime + ""));
			ocsRecord.setFirstName(callback.getFirstName());
			ocsRecord.setLastName(callback.getLastName());
			ocsRecord.setContactInfo(callback.getPreferredPhNo());
			ocsRecord.setTableName(callback.getTableClassName());
			ocsRecords.add(ocsRecord);
		}
		return ocsRecords;
	}

	public void syncOutlookForDelete(List<CallbackRequestVO> callbacks) {
		try {
			List<OCSRecord> ocsRecords = convertToOCSRecords(callbacks);
			outlookSyncService.deleteOutlookCals(ocsRecords);
		} catch (Exception e) {

		}
	}

	public void syncOutlookForReschedule(List<CallbackRequestVO> callbacks, String newDate, String newTime) {

		try {
			long newScheduleTime = CallbackUtils.getScheduledTime(newDate, newTime);
			List<OCSRecord> ocsRecords = convertToOCSRecordsWithNewTime(callbacks, newScheduleTime);
			outlookSyncService.updateOutlookCals(ocsRecords);
		} catch (Exception e) {
			LOGGER.error("Failed to syncOutlookForReschedule:" + e.getMessage());
		}
	}

}
