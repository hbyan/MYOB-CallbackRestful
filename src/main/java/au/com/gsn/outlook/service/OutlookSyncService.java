package au.com.gsn.outlook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.outlook.db.imp.AuditLogDaoImp;
import au.com.gsn.outlook.db.imp.OCSRecordDaoImp;
import au.com.gsn.outlook.db.imp.OutlookSyncDaoImp;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.helper.OutlookDataHelper;
import au.com.gsn.outlook.model.CalendarResponse;
import au.com.gsn.outlook.model.OutlookEventSource;
import au.com.gsn.outlook.model.OutlookEventType;
import au.com.gsn.outlook.model.OutlookResponseCode;
import au.com.gsn.outlook.utils.CallbackUtils;

@Component
public class OutlookSyncService {

	private Logger LOGGER = LogManager.getLogger(OutlookSyncService.class);

	@Autowired
	private OutlookDBService dbService;

	@Autowired
	private AuditLogDaoImp auditImp;

	@Autowired
	private OCSRecordDaoImp ocsDao;

	@Autowired
	private OutlookSyncDaoImp outlookSyncDao;

	
	public void updateOutlookCals(List<OCSRecord> ocsRecordsFromCBMng) {

		Map<String, List<OCSRecord>> map = OutlookDataHelper.populateEmailRecordsMap(ocsRecordsFromCBMng);

		List<String> emailList = new ArrayList<String>();
		for (Map.Entry<String, List<OCSRecord>> entry : map.entrySet()) {

			List<OCSRecord> ocsRecords = (List<OCSRecord>) entry.getValue();
			String emailAddress = entry.getKey();
			emailList.add(emailAddress);
			
			if (!dbService.emailActive(emailAddress)) {
				continue;
			}

			List<OCSRecord> alreadySyncedList = dbService.getOutlookSyncListByEmail(emailAddress);
			LOGGER.info("alreadySyncedList=" + alreadySyncedList.size());
			// do update
			List<OCSRecord> outlookUpdateList = dbService.filterOutlookUpdateListByEmail(ocsRecords, alreadySyncedList);
			LOGGER.info("outlookUpdateList=" + outlookUpdateList.size());
			for (OCSRecord record : outlookUpdateList) {
				outlookSyncDelete(record, OutlookEventSource.CB_MANAGER);
				alreadySyncedList.remove(record);
			}

			// do add
			List<OCSRecord> outlookAddList = dbService.filterOutlookSyncListByEmail(ocsRecords, alreadySyncedList);
			LOGGER.info("outlookAddList=" + outlookAddList.size());
			for (OCSRecord record : outlookAddList) {
				outlookSyncAdd(record, OutlookEventSource.CB_MANAGER);
			}

		}
		dbService.syncEmailList(emailList);

	}

	public void deleteOutlookCals(List<OCSRecord> ocsRecordsFromCBMng) {

		Map<String, List<OCSRecord>> map = OutlookDataHelper.populateEmailRecordsMap(ocsRecordsFromCBMng);

		for (Map.Entry<String, List<OCSRecord>> entry : map.entrySet()) {
			List<OCSRecord> ocsRecords = (List<OCSRecord>) entry.getValue();
			String email = entry.getKey();

			List<OCSRecord> outlookSyncList = dbService.filterOutlookUpdateListByEmail(ocsRecords, email);
			LOGGER.info("DELETE list = "+ outlookSyncList.size());
			for (OCSRecord ocsRecord : outlookSyncList) {
				outlookSyncDelete(ocsRecord, OutlookEventSource.CB_MANAGER);
			}
		}

	}

	private void outlookSyncAdd(OCSRecord ocsRecord, OutlookEventSource eventSource) {

		OutlookService outlookService = new OutlookService();
		populateCalDateForOCSRecord(ocsRecord, 15);
		String email = ocsRecord.getEmailAddress();

		try {
			
			CalendarResponse response = outlookService.addOutlookCal(OutlookDataHelper.constructRequest(ocsRecord),
					email);
			// Create a record for audit log
			auditImp.createAuditLog(ocsRecord, OutlookEventType.NEW_CAL, eventSource, response.getApiErrorMsg(),
					response.getId());

			if (OutlookResponseCode.SUCCESS.name().equals(response.getApiResponseCode())) {
				outlookSyncDao.saveOutlookSync(ocsRecord, response.getId(), 0);
			}

		} catch (Exception e) {
			LOGGER.info(String.format("Failed to add outlook record due to [%s]", e.getMessage()));
		}

	}

	private void populateCalDateForOCSRecord(OCSRecord ocsRecord, int min) {
		ocsRecord.setCalStartTime(CallbackUtils.convertTimestampToDate(ocsRecord.getScheduledDialTime()));
		ocsRecord.setCalEndTime(CallbackUtils.convertTimestampToDateInMin(ocsRecord.getScheduledDialTime(), 15));
	}

	private void outlookSyncDelete(OCSRecord ocsRecord, OutlookEventSource eventSource) {

		OutlookService outlookService = new OutlookService();
		String calId = ocsRecord.getCalId();
		if (StringUtils.isEmpty(calId)) {
			LOGGER.info("Empty cal Id");
			return;
		}
		try {

			CalendarResponse response = outlookService.deleteOutlookCal(ocsRecord.getEmailAddress(),
					ocsRecord.getCalId());

			// Create a record for audit log
			auditImp.createAuditLog(ocsRecord, OutlookEventType.DELETE_CAL, eventSource, response.getApiErrorMsg(),
					response.getId());

			if (OutlookResponseCode.SUCCESS.name().equals(response.getApiResponseCode())) {
			
				outlookSyncDao.deleteOutlookSync(ocsRecord.getCalId());
			}

		} catch (Exception e) {
			LOGGER.info(String.format("Failed to delete outlook record due to [%s]", e.getMessage()));
		}

	}

}
