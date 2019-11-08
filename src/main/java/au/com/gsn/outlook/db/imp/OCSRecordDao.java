package au.com.gsn.outlook.db.imp;

import java.util.List;

import au.com.gsn.outlook.config.DBConfiguration;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.exception.DBException;

public interface OCSRecordDao {
	
	List<String> findAllOCSTables(DBConfiguration config) throws DBException ;
	List<OCSRecord> getOCSRecords(String tableName, int cutOffThreshod) throws DBException ;
	void updateRecordOutlookSyncStatus(String campaignName, int recordId, String event) throws DBException ;
	
}
