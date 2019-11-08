package au.com.gsn.outlook.db.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.outlook.db.model.OCSRecord;
import au.com.gsn.outlook.db.model.OutlookSyncTable;
import au.com.gsn.outlook.helper.OutlookDataHelper;

@Component
public class OutlookSyncDaoImp {
	
	
	private Logger LOGGER = LogManager.getLogger(OutlookSyncDaoImp.class);
	
	@Autowired
	private CriteriaHelper criteriaHelper;

	public void saveOutlookSync(OCSRecord ocsRecord, String calId, int auditLogId) {
	
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			OutlookSyncTable table = new OutlookSyncTable();
			table.setCalId(calId);
			table.setCampaign(ocsRecord.getCampaign());
			table.setEmail(ocsRecord.getEmailAddress());
			table.setRecordId(ocsRecord.getRecordId());
			table.setStartTime(ocsRecord.getCalStartTime());
			table.setEndTime(ocsRecord.getCalEndTime());
			table.setScheduledTime(ocsRecord.getScheduledDialTime());
			table.setLogId(auditLogId);
			table.setTableName(ocsRecord.getTableName());
			table.setEventTime(new Date());
			session.save(table);
			txn.commit();

		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to create outlook sync due to [%s] for recordId[%s], campaign[%s]",
					e.getMessage(), ocsRecord.getRecordId(), ocsRecord.getCampaign()));
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}
	}

	public void updateOutlookSync(OCSRecord ocsRecord, String calId) {
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			OutlookSyncTable table = session.get(OutlookSyncTable.class, calId);
			table.setCampaign(ocsRecord.getCampaign());
			table.setEmail(ocsRecord.getEmailAddress());
			table.setRecordId(ocsRecord.getRecordId());
			table.setStartTime(ocsRecord.getCalStartTime());
			table.setEndTime(ocsRecord.getCalEndTime());
			table.setScheduledTime(ocsRecord.getScheduledDialTime());
			txn.commit();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to update outlook sync table due to [%s] for logId[%s]", e.getMessage(), calId));
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}
	}

	public void deleteOutlookSync(String calId) {
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		try {
			OutlookSyncTable table = session.get(OutlookSyncTable.class, calId);
			session.delete(table);
			txn.commit();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to update outlook sync table due to [%s] for logId[%s]", e.getMessage(), calId));
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}
	}
	
	public List<OutlookSyncTable> getOutlookSyncByEmail(String emailAddress, int daysBefore) {
		List<OutlookSyncTable> list = new ArrayList<OutlookSyncTable>();
		Session session = criteriaHelper.getSessionOnly();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<OutlookSyncTable> query = builder.createQuery(OutlookSyncTable.class);
			
			Date dateBefore = OutlookDataHelper.getDateBeforeXDays(daysBefore);

			Root<OutlookSyncTable> root = query.from(OutlookSyncTable.class);
			query.select(root)
					.where(builder.and(
							builder.equal(root.get("email"), emailAddress),
							builder.greaterThan(root.<Date>get("startTime"), dateBefore)
							)
			);
			list = session.createQuery(query).getResultList();
			
			return list;
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to get audit log due to [%s] for email", e.getMessage()));
		} finally {
			session.close();
		}
		return list;
	}

	public String findRecordCalId(int recordId,  String campaign) {
		String calId = "";
		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<OutlookSyncTable> query = builder.createQuery(OutlookSyncTable.class);
			Root<OutlookSyncTable> root = query.from(OutlookSyncTable.class);
			query.select(root).where( builder.and(
							builder.equal(root.get("recordId"), recordId),
							builder.equal(root.get("campaign"), campaign)
						)
					);
			OutlookSyncTable table= session.createQuery(query).getSingleResult();
			calId = table.getCalId();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to get audit log due to [%s] for email", e.getMessage()));
		} finally {
			session.close();
		}
		return calId;
	}

}
