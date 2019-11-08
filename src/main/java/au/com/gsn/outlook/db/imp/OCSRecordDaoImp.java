package au.com.gsn.outlook.db.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.outlook.config.DBConfiguration;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.exception.DBException;
import au.com.gsn.outlook.helper.DBDataHelper;
import au.com.gsn.outlook.utils.CallbackUtils;

@Repository
public class OCSRecordDaoImp implements OCSRecordDao {

	private Logger LOGGER = LogManager.getLogger(OCSRecordDaoImp.class);
	
	@Autowired
	private CriteriaHelper criteriaHelper;
	
	public List<OCSRecord> getOCSRecords(String tableName, int thresholdInMin) throws DBException {
		
		List<OCSRecord> ocsRecords = new ArrayList<OCSRecord>();
		long cutOffTime = CallbackUtils.getCutOffTime(thresholdInMin);
		String sqlQuery = "select record_id,agent_id,campaign_name, dial_sched_time, tz_dbid,CONTACT_FST_NAME,CONTACT_LAST_NAME,STATE, contact_info,ACCOUNT_NAME,CLIENT_ID"
				+ " from " + tableName;
		sqlQuery += " where agent_id != '' and record_status != 2 and (record_status != 3 or call_result = 47) and (record_type = 5  OR  record_type = 4) "
				+ " and dial_sched_time > " + cutOffTime
				+ " and agent_id not in (select Email_Address from GSN_OUTLOOK_EMAILLIST where active = 'N') "
				+ " order by dial_sched_time";
		
		Session session = criteriaHelper.getSessionOnly();
		try {
			@SuppressWarnings("unchecked")
			NativeQuery query = session.createNativeQuery(sqlQuery);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = query.list();
			ocsRecords = DBDataHelper.convertToOCSRecord(resultList);
			return ocsRecords;

		} catch (Exception e) {
			LOGGER.error(String.format("Fail to get ocs records due to [%s]", e.getMessage()));
			throw new DBException(e.getMessage());
		} finally {
			session.close();
		}

	}

	public List<String> findAllOCSTables(DBConfiguration config) throws DBException {

		List<String> tableNames = new ArrayList<String>();

		String sqlQuery = "";
		if (DBDataHelper.isOracle(config.getIsOracle())) {
			sqlQuery = "SELECT table_name FROM all_tab_columns";
			sqlQuery += String.format(
					" where COLUMN_NAME like '%s' and table_name not in (select view_name from all_views)",
					config.getOcsTableColumn());
		} else {
			sqlQuery = "select TABLE_Name from INFORMATION_SCHEMA.COLUMNS";
			sqlQuery += String.format(
					" where COLUMN_NAME like '%s' and table_name not in (select table_name from INFORMATION_SCHEMA.VIEWS)",
					config.getOcsTableColumn());
		}

		if (!StringUtils.isEmpty(config.getTableNamePattern())) {
			sqlQuery += " and TABLE_NAME like '"+config.getTableNamePattern()+"%'";;
		}

		String excludeQuery = DBDataHelper.getPartialSQLFromCommaSeparateStr(config.getOcsTableNameExcluded());
		if (!StringUtils.isEmpty(excludeQuery)) {
			sqlQuery += excludeQuery;
		}
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		try {
			NativeQuery<String> query = session.createNativeQuery(sqlQuery);
			tableNames = query.getResultList();
			return tableNames;

		} catch (Exception e) {
			String errorMsg = String.format("Fail to get ocs records due to [%s]", e.getMessage());
			LOGGER.error(errorMsg);
			throw new DBException(errorMsg);
		} finally {
			session.close();
		}
	}

	public void updateRecordOutlookSyncStatus(String campaignName, int recordId, String event) throws DBException {
		String sqlQuery = String.format("update %s set VAR_017 = '%s' where record_Id = %s", campaignName, event,
				recordId);

		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			SQLQuery query = session.createSQLQuery(sqlQuery);
			int updateCount = query.executeUpdate();
			txn.commit();
		} catch (Exception e) {
			LOGGER.error(String.format("Failed to update outlook record due to [%s]", e.getMessage()));
		} finally {
			session.close();
		}

	}

}