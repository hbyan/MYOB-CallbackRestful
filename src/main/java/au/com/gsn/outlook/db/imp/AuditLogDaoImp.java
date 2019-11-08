package au.com.gsn.outlook.db.imp;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.db.util.HibernateOCSUtil;
import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.db.model.OutlookAuditTable;
import au.com.gsn.outlook.model.OutlookEventSource;
import au.com.gsn.outlook.model.OutlookEventType;

@Component
public class AuditLogDaoImp {

	private Logger LOGGER = LogManager.getLogger(AuditLogDaoImp.class);
	
	@Autowired
	private CriteriaHelper criteriaHelper;
	
	public int saveAuditLog(OCSRecord ocsRecord, OutlookEventType eventType, OutlookEventSource eventSource) {
		int savedRecordId = 0;
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			OutlookAuditTable table = new OutlookAuditTable();
			table.setAgentId(ocsRecord.getEmailAddress());
			table.setCampaign(ocsRecord.getCampaign());
			table.setEventSource(eventSource.name());
			table.setEventTime(new Date());
			table.setEventType(eventType.name());
			table.setRecordId(ocsRecord.getRecordId());
			table.setScheduledTime(ocsRecord.getScheduledDialTime());
			table.setTableName(ocsRecord.getTableName());
			table.setApiResponse("");
			table.setApiResponseId("");
			session.save(table);
			txn.commit();
			savedRecordId = table.getLogId();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to create audit log due to [%s] for recordId[%s], campaign[%s]",
					e.getMessage(), ocsRecord.getRecordId(), ocsRecord.getCampaign()));
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}
		return savedRecordId;
	}
	
	public void createAuditLog(OCSRecord ocsRecord, OutlookEventType eventType, OutlookEventSource eventSource, String apiResponse, String calId) {
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			OutlookAuditTable table = new OutlookAuditTable();
			table.setAgentId(ocsRecord.getEmailAddress());
			table.setCampaign(ocsRecord.getCampaign());
			table.setEventSource(eventSource.name());
			table.setEventTime(new Date());
			table.setEventType(eventType.name());
			table.setRecordId(ocsRecord.getRecordId());
			table.setScheduledTime(ocsRecord.getScheduledDialTime());
			table.setTableName(ocsRecord.getTableName());
			table.setApiResponse(apiResponse);
			table.setApiResponseId(calId);
			session.save(table);
			txn.commit();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to create audit log due to [%s] for recordId[%s], campaign[%s]",
					e.getMessage(), ocsRecord.getRecordId(), ocsRecord.getCampaign()));
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}

	}

	public void updateAuditLog(Integer logId, String apiResponseId, String apiResponse) {
		
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			OutlookAuditTable table = session.get(OutlookAuditTable.class, logId);
			table.setApiResponseId(apiResponseId);
			table.setApiResponse(apiResponse);
			txn.commit();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to update audit log due to [%s] for logId[%s]", e.getMessage(), logId));
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}
	}

}
