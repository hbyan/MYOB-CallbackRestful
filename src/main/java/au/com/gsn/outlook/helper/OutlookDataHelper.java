package au.com.gsn.outlook.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import au.com.gsn.outlook.config.ApiConfiguration;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.db.model.OutlookAuditTable;
import au.com.gsn.outlook.model.CalendarBody;
import au.com.gsn.outlook.model.CalendarTime;
import au.com.gsn.outlook.model.CalenderRequest;
import au.com.gsn.outlook.utils.CallbackUtils;

public class OutlookDataHelper {

	public static CalenderRequest constructRequest(OCSRecord ocsRecord) {

		ApiConfiguration apiConfig = AppContextHelper.getApiConfiguration();

		CalenderRequest request = new CalenderRequest();
		request.setSubject(getCalSubject(ocsRecord));

		CalendarBody calBody = new CalendarBody();
		calBody.setContent(getCalContent(ocsRecord));
		calBody.setContentType(apiConfig.getContentType());
		request.setCalBody(calBody);

		CalendarTime calStart = new CalendarTime();
		calStart.setDateTime(CallbackUtils.convertSchedTime(ocsRecord.getScheduledDialTime()));
		calStart.setTimeZone(TimezoneEnum.getDesc(ocsRecord.getTzId()));
		request.setCalStart(calStart);

		CalendarTime calEnd = new CalendarTime();
		calEnd.setDateTime(CallbackUtils.convertSchedTimeInMin(ocsRecord.getScheduledDialTime(),
				apiConfig.getCalEndTimeThreshod()));
		calEnd.setTimeZone(TimezoneEnum.getDesc(ocsRecord.getTzId()));
		request.setCalEnd(calEnd);

		return request;

	}

	private static String getCalSubject(OCSRecord ocsRecord) {
		String subject = "Callback";
		if (!StringUtils.isEmpty(ocsRecord.getContactInfo())) {
			subject += "-" + ocsRecord.getContactInfo();
		}
		if (!StringUtils.isEmpty(ocsRecord.getFirstName())) {
			subject += "-Acct Name:" + ocsRecord.getAcctName();
		}
		return subject;
	}

	private static String getCalContent(OCSRecord ocsRecord) {

		String lineBreak = "<br>";
		String content = "Callback Information" + lineBreak;
		if (!StringUtils.isEmpty(ocsRecord.getFirstName())) {
			content += "Name:" + ocsRecord.getFirstName();
		}
		if (!StringUtils.isEmpty(ocsRecord.getLastName())) {
			content += " " + ocsRecord.getLastName() + lineBreak;
		}
		if (!StringUtils.isEmpty(ocsRecord.getContactInfo())) {
			content += "Contact:" + ocsRecord.getContactInfo() + lineBreak;
		}
		if (!StringUtils.isEmpty(ocsRecord.getAcctName())) {
			content += "Account Name:" + ocsRecord.getAcctName() + lineBreak;
		}
		if (!StringUtils.isEmpty(ocsRecord.getState())) {
			content += "State:" + ocsRecord.getState() + lineBreak;
		}
		return content;
	}

	public static boolean timeOverlapped(List<OCSRecord> list, int checkTimestamp) {

		boolean fallInPreviousTimeRange = false;
		for (OCSRecord record : list) {
			if (CallbackUtils.isWithinTimeRange(checkTimestamp, record.getScheduledDialTime(), 15)) {
				fallInPreviousTimeRange = true;
				break;
			}
		}
		return fallInPreviousTimeRange;
	}

	public static boolean recordSynced(List<OutlookAuditTable> list, int recordId) {

		boolean alreadySynced = false;
		for (OutlookAuditTable record : list) {
			if (recordId != record.getRecordId()) {
				continue;
			}
		}
		return alreadySynced;
	}

	public static Map<String, List<OCSRecord>> populateEmailRecordsMap(List<OCSRecord> ocsRecords) {
		Map<String, List<OCSRecord>> emailRecordsMap = new HashMap<String, List<OCSRecord>>();
		if (emailRecordsMap.isEmpty()) {
			emailRecordsMap = new HashMap<String, List<OCSRecord>>();
		}

		if (ocsRecords == null || ocsRecords.size() == 0) {
			return emailRecordsMap;
		}
		for (OCSRecord ocsRecord : ocsRecords) {

			List<OCSRecord> recordList = emailRecordsMap.get(ocsRecord.getEmailAddress());
			if (recordList == null) {
				recordList = new ArrayList<OCSRecord>();
			}
			recordList.add(ocsRecord);
			emailRecordsMap.put(ocsRecord.getEmailAddress(), recordList);
		}
		return emailRecordsMap;
	}

	public static boolean alreadySynced(OCSRecord ocsRecord, List<OCSRecord> alreadySyncedList) {
		for (OCSRecord sycnedRecord : alreadySyncedList) {
			if (ocsRecord.getRecordId() == sycnedRecord.getRecordId()
					&& ocsRecord.getTableName().equals(sycnedRecord.getTableName())
					&& ocsRecord.getEmailAddress().equals(sycnedRecord.getEmailAddress())
					&& ocsRecord.getScheduledDialTime() == sycnedRecord.getScheduledDialTime()) {
				return true;
			}
		}
		return false;
	}

	public static String findCalId(OCSRecord ocsRecord, List<OCSRecord> alreadySyncedList) {
		String calId = "";

		for (OCSRecord syncedRecord : alreadySyncedList) {
			if (syncedRecord.getRecordId() != ocsRecord.getRecordId()) {
				continue;
			}
			if (!syncedRecord.getTableName().equals(ocsRecord.getTableName())) {
				continue;
			}
			if (StringUtils.isEmpty(syncedRecord.getCalId())) {
				continue;
			}
			if (!syncedRecord.getEmailAddress().equals(ocsRecord.getEmailAddress())) {
				continue;
			}
			return syncedRecord.getCalId();
		}
		return calId;

	}

	public static OCSRecord findSycRecord(OCSRecord ocsRecord, List<OCSRecord> alreadySyncedList) {

		for (OCSRecord syncedRecord : alreadySyncedList) {
			if (syncedRecord.getRecordId() != ocsRecord.getRecordId()) {
				continue;
			}
			if (!syncedRecord.getCampaign().equals(ocsRecord.getCampaign())) {
				continue;
			}
			if (StringUtils.isEmpty(syncedRecord.getCalId())) {
				continue;
			}
			if (!syncedRecord.getEmailAddress().equals(ocsRecord.getEmailAddress())) {
				continue;
			}
			return syncedRecord;
		}
		return null;

	}

	public static String alreadySyncWithDiffDateTime(OCSRecord ocsRecord, List<OCSRecord> alreadySyncedList) {
		String callId = "";

		OCSRecord syncedRecord = findSycRecord(ocsRecord, alreadySyncedList);
		if (syncedRecord == null) {
			return callId;
		}
		if (syncedRecord.getScheduledDialTime() != ocsRecord.getScheduledDialTime()) {
			callId = syncedRecord.getCalId();
		}
		return callId;

	}

	public static Date getDateBeforeXDays(int daysBefore) {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime newDate = now.minusDays(daysBefore);
		Date dateBefore = Date.from(newDate.atZone(ZoneId.systemDefault()).toInstant());
		return dateBefore;

	}
}
