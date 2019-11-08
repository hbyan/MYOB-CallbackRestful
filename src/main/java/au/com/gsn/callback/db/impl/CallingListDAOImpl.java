package au.com.gsn.callback.db.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.callback.tables.CallingListTableVO;

@Component
public class CallingListDAOImpl {

	private String className = "CallingListDAOImpl";
	private static Logger LOGGER = LogManager.getLogger(CallingListDAOImpl.class);
	
	@Autowired
	private CriteriaHelper criteriaHelper;

	public List<CallingListTableVO> getCallingListByQuery(String tableName, String agentId, boolean isSupervisor) {
		LOGGER.info(className + ".getCallingList: start");
		List<CallingListTableVO> list = new ArrayList<CallingListTableVO>();
		Session session = criteriaHelper.getOCSSession();
		try {
			String sql = "select record_id,"
					+ "CONTACT_FST_NAME,CONTACT_LAST_NAME,CAMPAIGN_NAME,STATE,dial_sched_time,contact_info,record_status,"
					+ "record_type,agent_id,chain_id, CLIENT_ID, ACCOUNT_NAME, ASSET_SERIAL_NUM, "
					+ "tz_dbid, attempt,VAR_018,VAR_019,VAR_020"
					+ " from " + tableName + " where "+ getRecordQuery();	
			if (!isSupervisor) {
				sql = sql + " and agent_id = '" + agentId + "'";
			}
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object[]> resultList= sqlQuery.list();
			if(resultList!=null && resultList.size() != 0){
				list = convertResultList(resultList);
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Fail to getCallingList from campaignName[%s], agentId[%s] due to [%s]",
					tableName, agentId, e.getMessage()));
		} finally {
			session.close();
		}
		return list;
	}
	
	public String getRecordQuery(){
		return " record_status != 2 and (record_status != 3 or call_result = 47) and (record_type = 5  OR  record_type = 4)";
	}
	
	private List<CallingListTableVO> convertResultList(List<Object[]> resultList) {
		List<CallingListTableVO> list = new ArrayList<CallingListTableVO>();
		for(Object[] record : resultList){
			CallingListTableVO table = new CallingListTableVO();
			int recordId = convertObjToInteger(record[0]);
			String firstName = (String)record[1];
			String lastName = (String)record[2];
			String campaignName = (String)record[3];
			String state = (String)record[4];
			int scheduledTime = convertObjToInteger(record[5]);
			String contactInfo = (String)record[6];
			String recordStatus = convertObjToString(record[7]);
			int recordType = convertObjToInteger(record[8]);
		    String agentId = (String)record[9];
		    int chainId = convertObjToInteger(record[10]);
		    String clientId = (String)record[11];
		    
		    String accountName =   convertObjToString(record[12]);
		    String serialNum = (String)record[13];
		    int timezoneId = convertObjToInteger(record[14]);
		    
		    int attempt = convertObjToInteger(record[15]);
		    
		    String var18 =  convertObjToString(record[16]); //lead score
		    String var19 =  convertObjToString(record[17]); //notes
		    String var20 =  convertObjToString(record[18]);
					
			table.setAgentId(agentId);
			table.setChainId(chainId);
			table.setCompaignName(campaignName);
			table.setDialSchedTime(scheduledTime);
			table.setFirstName(firstName);
			table.setLastName(lastName);
			table.setPreferredPhNo(contactInfo);
			table.setRecordId(recordId);
			table.setRecordStatus(recordStatus);
			table.setRecordType(recordType);
			table.setState(state);
			table.setClientId(clientId);
			table.setSerialNum(serialNum);
			
			table.setAccountName(accountName);
			table.setTimezoneId(timezoneId);
			table.setAttempt(attempt);
			table.setLeadScore(var18);
			table.setNotes(var19);
			table.setCloseStatus(var20);
			list.add(table);
		}
		return list;
	}
	
	private int convertObjToInteger(Object valueObj){
		if(valueObj == null){
			return 0;
		}
		if(valueObj instanceof Integer){
			return ((Integer) valueObj).intValue();
		}
		else if(valueObj instanceof BigDecimal){
			BigDecimal obj = (BigDecimal)valueObj;
			return obj.intValue();
		}
		throw new IllegalArgumentException(String.format("Fail to convertObjToInteger for obj[%s]", valueObj));
	}
	
	private String convertObjToString(Object valueObj){
		if(valueObj == null){
			return "";
		}
		else if(valueObj instanceof String){
			return valueObj.toString();
		}
		else if(valueObj instanceof Integer){
			Integer intObj = (Integer)valueObj;
			return intObj.toString();
		}
		else if(valueObj instanceof BigDecimal){
			BigDecimal obj = (BigDecimal)valueObj;
			return obj.toString();
		}
		throw new IllegalArgumentException(String.format("Fail to convertObjToString for obj[%s]", valueObj));
	}
	
}
