package au.com.gsn.outlook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import au.com.gsn.outlook.config.DBConfiguration;
import au.com.gsn.outlook.db.imp.EmailDaoImp;
import au.com.gsn.outlook.db.imp.OCSRecordDaoImp;
import au.com.gsn.outlook.db.imp.OutlookSyncDaoImp;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.db.model.OutlookEmailTable;
import au.com.gsn.outlook.db.model.OutlookSyncTable;
import au.com.gsn.outlook.exception.DBException;
import au.com.gsn.outlook.helper.AppContextHelper;
import au.com.gsn.outlook.helper.OutlookDataHelper;
import au.com.gsn.outlook.utils.CallbackUtils;

@Component
public class OutlookDBService {

	private Logger LOGGER = LogManager.getLogger(OutlookDBService.class);
	
	private static int dialThreshodInMinDefault = 30;
	//private static int syncRecordsInDaysDefault = 14;
	private static int overLapNumDefault = 10;
	private static int removeOutlookDaysBeforeDefault = 2;
	
	@Autowired
	private OutlookSyncDaoImp outlookSynDaoImp;
	
	@Autowired
	private OCSRecordDaoImp dao;
	
	@Autowired
	private EmailDaoImp emailDaoImp ;

	public List<String> getAllOCSTables() throws DBException {

		DBConfiguration config = AppContextHelper.getDBConfiguration();
		return dao.findAllOCSTables(config);
	}

	public List<OCSRecord> getOCSRecordsByTable(String tableName) {
		
		DBConfiguration config = AppContextHelper.getDBConfiguration();
		List<OCSRecord> records = new ArrayList<OCSRecord>();
		try {
			records = dao.getOCSRecords(tableName, CallbackUtils.convertStrToIntWithDefault(config.getDialThreshodInMin(),dialThreshodInMinDefault));

		} catch (Exception e) {

		}
		return records;
	}

	public Map<String, List<OCSRecord>> findEmailRecordsMapForAllTables() {

		List<OCSRecord> recordsFromAllTables = new ArrayList<OCSRecord>();
		List<String> tables = new ArrayList<String>();
		try {
			tables = getAllOCSTables();
		} catch (DBException e) {
			LOGGER.error(String.format("Fail to get OCS tables due to [%s]", e.getMessage()));
		}
		for (String table : tables) {
			recordsFromAllTables.addAll(getOCSRecordsByTable(table));
		}
		Map<String, List<OCSRecord>> emailRecordsMap = OutlookDataHelper.populateEmailRecordsMap(recordsFromAllTables);
		return emailRecordsMap;
	}

	public void syncEmailList(List<String> emailList) {
	
		List<String> emailsInDB = getEmailsInDB();
		List<String> syncEmailList = findSyncEmailList(emailList, emailsInDB);
		emailDaoImp.syncEmailList(syncEmailList);
	}

	private List<String> findSyncEmailList(List<String> emailList, List<String> emailsInDB) {
		List<String> newEmailList = new ArrayList<String>();
		for (String email : emailList) {
			if (emailsInDB.contains(email)) {
				continue;
			}
			newEmailList.add(email);
		}
		return newEmailList;
	}

	public boolean emailActive(String emailAddress) {
		boolean emailActive = false;
		
		if(StringUtils.isEmpty(emailAddress)){
			return emailActive;
		}
		List<OutlookEmailTable> list = emailDaoImp.getEmailList();
		for(OutlookEmailTable emailTable : list) {
			String emailInTable = emailTable.getEmailAddress().trim().toLowerCase();
			
			if(!emailAddress.trim().toLowerCase().equals(emailInTable)) {
				continue;
			}
			if(!"Y".equals(emailTable.getActive().trim())) {
				continue;
			}
			emailActive = true;
			break;
		}
		LOGGER.info("emailAddress=="+emailActive);
		return emailActive;
	}
	
	public List<String> getEmailsInDB() {
		List<String> emailsInDB = new ArrayList<String>();
		List<OutlookEmailTable> list = emailDaoImp.getEmailList();
		if (list == null || list.size() == 0) {
			return emailsInDB;
		}
		for (OutlookEmailTable table : list) {
			emailsInDB.add(table.getEmailAddress());
		}
		return emailsInDB;
	}

	public String getCalId(OCSRecord ocsRecord) {
		
		return outlookSynDaoImp.findRecordCalId(ocsRecord.getRecordId(), ocsRecord.getCampaign());
	}

	public List<OCSRecord> getOutlookSyncListByEmail(String emailAddress) {
		
		DBConfiguration config = AppContextHelper.getDBConfiguration();
		
		List<OutlookSyncTable> tableList = outlookSynDaoImp.getOutlookSyncByEmail(emailAddress, removeOutlookDaysBeforeDefault);
		List<OCSRecord> ocsRecords = new ArrayList<OCSRecord>();
		for (OutlookSyncTable table : tableList) {
			OCSRecord ocsRecord = new OCSRecord();
			ocsRecord.setRecordId(table.getRecordId());
			ocsRecord.setEmailAddress(table.getEmail());
			ocsRecord.setCampaign(table.getCampaign());
			ocsRecord.setCalStartTime(table.getStartTime());
			ocsRecord.setCalEndTime(table.getEndTime());
			ocsRecord.setCalId(table.getCalId());
			ocsRecord.setScheduledDialTime(table.getScheduledTime());
			ocsRecord.setTableName(table.getTableName());
			ocsRecords.add(ocsRecord);
		}
		return ocsRecords;
	}

	public List<OCSRecord> filterOutlookSyncListByEmail(List<OCSRecord> list, String email) {

		List<OCSRecord> finalList = new ArrayList<OCSRecord>();

		List<OCSRecord> alreadySyncedList = getOutlookSyncListByEmail(email);

		for (OCSRecord ocsRecord : list) {
			if (OutlookDataHelper.timeOverlapped(finalList, ocsRecord.getScheduledDialTime())) {
				continue;
			}
			if (OutlookDataHelper.alreadySynced(ocsRecord, alreadySyncedList)) {
				continue;
			}
			finalList.add(ocsRecord);
		}

		return finalList;
	}

	public List<OCSRecord> filterOutlookUpdateListByEmail(List<OCSRecord> ocsRecords, String email) {
		
		List<OCSRecord> finalList = new ArrayList<OCSRecord>();
		List<OCSRecord> alreadySyncedList = getOutlookSyncListByEmail(email);
		
		for(OCSRecord ocsRecord : ocsRecords) {
			String calId = OutlookDataHelper.findCalId(ocsRecord, alreadySyncedList);
			if(!StringUtils.isEmpty(calId)) {
				ocsRecord.setCalId(calId);
				finalList.add(ocsRecord);
			}
		}
		return finalList;
	}

	public List<OCSRecord> filterOutlookSyncListByEmail(List<OCSRecord> list, List<OCSRecord> alreadySyncedList) {
		List<OCSRecord> finalList = new ArrayList<OCSRecord>();
		
		for (OCSRecord ocsRecord : list) {
			if (OutlookDataHelper.alreadySynced(ocsRecord, alreadySyncedList)) {
				continue;
			}
			finalList.add(ocsRecord);
		}
		
		return finalList;
	}
	
	public List<OCSRecord> filterOutlookUpdateListByEmail(List<OCSRecord> list, List<OCSRecord> alreadySyncedList) {
		List<OCSRecord> finalList = new ArrayList<OCSRecord>();
		for(OCSRecord ocsRecord : list) {
			String calId = OutlookDataHelper.alreadySyncWithDiffDateTime(ocsRecord, alreadySyncedList);
			if(!StringUtils.isEmpty(calId)) {
				ocsRecord.setCalId(calId);
				finalList.add(ocsRecord);
			}
		}
		return finalList;
	}

	public List<OCSRecord> filterOutlookDeleteListByEmail(List<OCSRecord> list, List<OCSRecord> alreadySyncedList) {
		List<OCSRecord> finalList = new ArrayList<OCSRecord>();
		for(OCSRecord syncRecord : alreadySyncedList) {
			if(!syncRecordInOCSRecordList(syncRecord, list)) {
				finalList.add(syncRecord);
			}
		}
		return finalList;
	}

	private boolean syncRecordInOCSRecordList(OCSRecord syncRecord, List<OCSRecord> list) {
		boolean found = false;
		for(OCSRecord ocsRecord : list) {
			if(syncRecord.getRecordId() == ocsRecord.getRecordId() && syncRecord.getCampaign().equals(ocsRecord.getCampaign())) {
				found = true;
				break;
			}
		}
		return found;
	}

}
