package au.com.gsn.callback.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


public class UpdateTableHelper {

	private static Logger LOGGER = LogManager.getLogger("au.com.gsn.callbackmanager");
	

	public UpdateTableHelper() {
	};

	public void rescheduleRecord(Session session, String tableName, int recordId, int newScheduleTime) throws Exception {
		if(newScheduleTime == 0 || recordId == 0){
			throw new Exception(String.format("Fail to update record because newScheduledTime =0 "));
		}
	
		String query = String.format("update %s set  record_status = 1, DIAL_SCHED_TIME= %s  where RECORD_ID = %s", tableName,newScheduleTime,recordId);
				
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.executeUpdate();
	    
		session.getTransaction().commit();
	}
}
