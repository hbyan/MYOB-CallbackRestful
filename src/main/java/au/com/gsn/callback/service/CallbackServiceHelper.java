package au.com.gsn.callback.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.db.impl.AttemptDAOImpl;
import au.com.gsn.callback.db.impl.CBTableDAOImpl;
import au.com.gsn.callback.db.util.HibernateOCSUtil;
import au.com.gsn.callback.helper.AuditLogHelper;
import au.com.gsn.callback.helper.CallbackActionHelper;
import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.callback.helper.UpdateRecordAction;
import au.com.gsn.callback.helper.UpdateRecordResponseCode;
import au.com.gsn.callback.model.AttemptInfo;
import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.model.RecordsActionResponse;
import au.com.gsn.callback.model.TimezoneEnum;
import au.com.gsn.callback.model.UpdateRecordResponse;
import au.com.gsn.callback.utils.CallbackUtils;

@Component
public class CallbackServiceHelper {

	@Value("${schedule.min.threshhold}")
	private String threadholdMin;

	@Value("${callNow.min.threshhold}")
	private String threadholdMinForCallNow;

	private static Logger LOGGER = LogManager.getLogger(CallbackServiceHelper.class);

	@Autowired
	private CriteriaHelper criteriaHelper;

	@Autowired
	private HibernateOCSUtil HibernateUtil;

	@Autowired
	private CBTableDAOImpl CBTableDAOImpl;

	@Autowired
	private AttemptDAOImpl AttemptDAOImpl;

	public RecordsActionResponse closeCBs(List<CallbackRequestVO> callbacks) {
		RecordsActionResponse actionResponse = new RecordsActionResponse();
		List<UpdateRecordResponse> responses = new ArrayList<UpdateRecordResponse>();
		Session session = criteriaHelper.getSessionOnly();
		Transaction tx = session.beginTransaction();
		try {
			int count = 0;
			for (CallbackRequestVO callback : callbacks) {
				UpdateRecordResponse response = CBTableDAOImpl.closeRecord(session, callback.getTableClassName(),
						callback.getRecordID());
				responses.add(response);
				if (++count % 20 == 0) {
					// flush a batch of updates and release memory:
					session.flush();
					session.clear();
				}
			}
			actionResponse.setRecordsResponse(responses);
			tx.commit();
		} catch (Exception e) {
			catchCommonResponse(actionResponse, e, UpdateRecordAction.CLOSE.name());
		} finally {
			session.close();
		}
		return actionResponse;
	}

	public RecordsActionResponse rescheduleCBs(List<CallbackRequestVO> callbacks, String newDate, String newTime,
			String newTZ) {
		RecordsActionResponse actionResponse = new RecordsActionResponse();
		List<UpdateRecordResponse> responses = new ArrayList<UpdateRecordResponse>();
		// validation for passed time, etc
		UpdateRecordResponse validateResponse = CallbackActionHelper.checkCanReschedule(newDate, newTime,
				threadholdMin);
		if (!UpdateRecordResponseCode.SUCCESS.name().equals(validateResponse.getResponseCode())) {
			actionResponse.setCommonResponse(validateResponse);
			return actionResponse;
		}
		long newScheduleTime = CallbackUtils.getScheduledTime(newDate, newTime);
		int tzId = TimezoneEnum.getId(newTZ);
		Session session = criteriaHelper.getSessionOnly();
		Transaction tx = session.beginTransaction();
		try {
			int count = 0;
			for (CallbackRequestVO callback : callbacks) {
				UpdateRecordResponse response = CBTableDAOImpl.rescheduleRecord(session, callback.getTableClassName(),
						callback.getRecordID(), newScheduleTime, tzId);
				responses.add(response);
				if (++count % 20 == 0) {
					// flush a batch of updates and release memory:
					session.flush();
					session.clear();
				}
			}
			actionResponse.setRecordsResponse(responses);
			tx.commit();

		} catch (Exception e) {
			catchCommonResponse(actionResponse, e, UpdateRecordAction.RESCHEDULE.name());
		} finally {
			session.close();
		}
		return actionResponse;
	}

	public RecordsActionResponse asapCBs(List<CallbackRequestVO> callbacks) {
		RecordsActionResponse actionResponse = new RecordsActionResponse();
		List<UpdateRecordResponse> recordsResponse = new ArrayList<UpdateRecordResponse>();
		Session session = criteriaHelper.getSessionOnly();
		Transaction tx = session.beginTransaction();
		try {
			int newScheduleTime = (int) CallbackUtils.getCurrentTime(threadholdMinForCallNow);
			int count = 0;
			for (CallbackRequestVO callback : callbacks) {
				UpdateRecordResponse response = CBTableDAOImpl.asapRecord(session, callback.getTableClassName(),
						callback.getRecordID(), newScheduleTime);
				recordsResponse.add(response);
				if (++count % 20 == 0) {
					// flush a batch of updates and release memory:
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			actionResponse.setRecordsResponse(recordsResponse);
		} catch (Exception e) {
			catchCommonResponse(actionResponse, e, UpdateRecordAction.CALL_NOW.name());
		} finally {
			session.close();
		}
		return actionResponse;
	}

	public RecordsActionResponse changeTZs(List<CallbackRequestVO> callbacks, String newTZ) {
		RecordsActionResponse actionResponse = new RecordsActionResponse();
		List<UpdateRecordResponse> recordsResponse = new ArrayList<UpdateRecordResponse>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		int timezoneId = TimezoneEnum.getId(newTZ);
		Transaction tx = session.beginTransaction();
		try {
			/*
			 * List<String> tableList = getTableList(callbacks); for (String
			 * table : tableList) { List<String> recordIdList =
			 * getRecordIDList(callbacks, table); UpdateRecordResponse response
			 * = CBTableDAOImpl.changeTimezone(session, table, recordIdList,
			 * timezoneId); recordsResponse.add(response); }
			 */
			int count = 0;
			for (CallbackRequestVO callback : callbacks) {
				UpdateRecordResponse response = CBTableDAOImpl.changeTimezone(session, callback.getTableClassName(),
						callback.getRecordID(), timezoneId);
				recordsResponse.add(response);
				if (++count % 20 == 0) {
					// flush a batch of updates and release memory:
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			actionResponse.setRecordsResponse(recordsResponse);
		} catch (Exception e) {
			catchCommonResponse(actionResponse, e, UpdateRecordAction.CHANGE_TIMEZONE.name());
		} finally {
			session.close();
		}
		return actionResponse;
	}

	private List<String> getTableList(List<CallbackRequestVO> callbacks) {
		List<String> tables = new ArrayList<String>();
		for (CallbackRequestVO vo : callbacks) {
			if (tables.contains(vo.getTableClassName())) {
				continue;
			} else {
				tables.add(vo.getTableClassName());
			}
		}
		return tables;
	}

	private List<String> getRecordIDList(List<CallbackRequestVO> callbacks, String table) {
		List<String> recordIDs = new ArrayList<String>();
		for (CallbackRequestVO vo : callbacks) {
			if (!table.equals(vo.getTableClassName())) {
				continue;
			} else {
				if (!recordIDs.contains(vo.getRecordID())) {
					recordIDs.add(vo.getRecordID() + "");
				}
			}
		}
		return recordIDs;
	}

	public RecordsActionResponse changeLeadScore(List<CallbackRequestVO> callbacks, String leadScore) {
		RecordsActionResponse actionResponse = new RecordsActionResponse();
		List<UpdateRecordResponse> recordsResponse = new ArrayList<UpdateRecordResponse>();
		Session session = criteriaHelper.getSessionOnly();
		Transaction tx = session.beginTransaction();
		try {
			for (CallbackRequestVO callback : callbacks) {
				UpdateRecordResponse response = CBTableDAOImpl.changeLeadScore(session, callback.getTableClassName(),
						callback.getRecordID(), leadScore);
				recordsResponse.add(response);
			}
			tx.commit();
			actionResponse.setRecordsResponse(recordsResponse);
		} catch (Exception e) {
			catchCommonResponse(actionResponse, e, UpdateRecordAction.CHANGE_LEADSCORE.name());
		} finally {
			session.close();
		}
		return actionResponse;
	}

	public RecordsActionResponse changeNote(CallbackRequestVO callback) {
		RecordsActionResponse actionResponse = new RecordsActionResponse();
		List<UpdateRecordResponse> recordsResponse = new ArrayList<UpdateRecordResponse>();
		Session session = criteriaHelper.getSessionOnly();
		Transaction tx = session.beginTransaction();
		try {
			UpdateRecordResponse response = CBTableDAOImpl.changeNote(session, callback.getTableClassName(),
					callback.getRecordID(), callback.getNotes());
			recordsResponse.add(response);
			actionResponse.setRecordsResponse(recordsResponse);
			tx.commit();
		} catch (Exception e) {
			catchCommonResponse(actionResponse, e, UpdateRecordAction.CHANGE_NOTE.name());
		} finally {
			session.close();
		}
		return actionResponse;
	}

	private void catchCommonResponse(RecordsActionResponse actionResponse, Exception e, String action) {
		String error = String.format("FAIL action [%s] for callbacks due to [%s]", action, e.getMessage());
		LOGGER.error(error);
		UpdateRecordResponse commonResponse = new UpdateRecordResponse();
		commonResponse.setResponseCode(UpdateRecordResponseCode.FAIL.name());
		commonResponse.setLogMessage(CallbackUtils.cutString(error, 30));
		actionResponse.setCommonResponse(commonResponse);
	}

	public void updateAuditLog(RecordsActionResponse recordActionResponse, List<CallbackRequestVO> callbacks,
			String newValue) {
		Session session = criteriaHelper.getSessionOnly();
		Transaction tx = session.beginTransaction();
		if (recordActionResponse.getRecordsResponse() == null) {
			return;
		}
		if (recordActionResponse.getRecordsResponse().size() == 0) {
			return;
		}
		try {
			int count = 0;
			for (UpdateRecordResponse response : recordActionResponse.getRecordsResponse()) {
				CallbackRequestVO callback = getCallback(callbacks, response.getRecordId());
				AuditLogHelper.createAuditLogByAction(session, callback, response.getAction(), newValue,
						response.getResponseCode());
				if (count % 20 == 0) {
					session.flush();
					session.clear();
				}
				count++;
			}
			tx.commit();
		} catch (Exception e) {
			LOGGER.error(String.format("FAIL to updateAuditLog due to [%s]", e.getMessage()));
		} finally {
			session.close();
		}
	}

	private CallbackRequestVO getCallback(List<CallbackRequestVO> callbacks, String recordId) {
		if (callbacks == null || callbacks.size() == 0 || recordId == null) {
			return null;
		}
		int recordIdInt = CallbackUtils.convertStrToInteger(recordId);
		for (CallbackRequestVO callback : callbacks) {
			if (recordIdInt == callback.getRecordID()) {
				return callback;
			}
		}
		return null;
	}

	public List<AttemptInfo> getAttemptInfo(String chainId, String callingListName) {

		/*List<AttemptInfo> attempts = new ArrayList<AttemptInfo>();
		AttemptInfo info = new AttemptInfo();
		info.setAttemptOrdinal("3");
		info.setRecordType("Campaign Rescheduled");
		info.setAgentName("EllenXu");
		info.setCallResult("Answering Machine Detected");
		info.setBusinessResult("temp");
		info.setInteractionStartTime(1511322278);
		attempts.add(info);

		info = new AttemptInfo();
		info.setAttemptOrdinal("2");
		info.setRecordType("Campaign Rescheduled");
		info.setAgentName("EllenXu2");
		info.setCallResult("Answering Machine Detected");
		info.setBusinessResult("temp");
		info.setInteractionStartTime(1523054728);
		attempts.add(info);

		info = new AttemptInfo();
		info.setAttemptOrdinal("1");
		info.setRecordType("Campaign Rescheduled");
		info.setAgentName("EllenXu2");
		info.setCallResult("Answering Machine Detected");
		info.setBusinessResult("temp");
		info.setInteractionStartTime(1522221360);
		attempts.add(info);*/

		List<AttemptInfo> attempts = 
		 AttemptDAOImpl.getAttemptInfoList(callingListName, chainId);
		for (AttemptInfo attemptInfo : attempts) {
			int timestamp = attemptInfo.getInteractionStartTime();
			attemptInfo.setDateTime(CallbackUtils.convertSchedTime(timestamp));
		}
		Collections.sort(attempts, new Comparator<AttemptInfo>() {
			public int compare(AttemptInfo o1, AttemptInfo o2) {
				return o1.getAttemptOrdinal().compareTo(o2.getAttemptOrdinal());
			}
		});
		return attempts;
	}

}
