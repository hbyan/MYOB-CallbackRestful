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
import au.com.gsn.callback.model.AttemptInfo;

@Component
public class AttemptDAOImpl {

	private static Logger LOGGER = LogManager.getLogger("au.com.gsn.callbackmanager");
	
	@Autowired
	private CriteriaHelper criteriaHelper;

	public List<AttemptInfo> getAttemptInfoList(String callingListName, String chainId) {

		List<AttemptInfo> list = new ArrayList<AttemptInfo>();
		Session session = criteriaHelper.getInfomartSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(getQuery(callingListName, chainId));
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = sqlQuery.list();
			list = convertResultList(resultList);
		} catch (Exception e) {
			LOGGER.error(String.format("Fail to getAttemptInfoList due to [%s]", e.getMessage()));
		} finally {
			session.close();
		}
		return list;
	}

	private List<AttemptInfo> convertResultList(List<Object[]> resultList) {
		List<AttemptInfo> list = new ArrayList<AttemptInfo>();
		for (Object[] record : resultList) {
			AttemptInfo table = new AttemptInfo();
			table.setAttemptOrdinal(convertObjToString(record[0])); 
		    table.setCallResult(convertObjToString(record[1]) );
			table.setInteractionStartTime(convertObjToInteger(record[2]));			
			table.setAgentName(convertObjToString(record[3])); 
			table.setBusinessResult(convertObjToString(record[4]) );
			table.setRecordType(convertObjToString(record[5]) );
			list.add(table);
		}
		return list;
	}

	public String getQuery(String callingListName, String chainId) {
		String query = String.format("SELECT A.ATTEMPT_ORDINAL,I.CALL_RESULT,"
				+ " A.START_TS AS INTERACTION_START_DATE"
				+ ",CASE WHEN L.AGENT_FIRST_NAME = 'NON	E' THEN NULL ELSE  L.AGENT_FIRST_NAME + '' + L.AGENT_LAST_NAME END AS AGNET_NAME,"
				+ " N.BUSINESS_RESULT,G.RECORD_TYPE" + " FROM [dbo].CONTACT_ATTEMPT_FACT A"
				+ " LEFT JOIN dbo.IRF_USER_DATA_GEN_1 B ON A.CALL_ATTEMPT_ID = B.GSW_CALL_ATTEMPT_GUID"
				+ " LEFT JOIN dbo.INTERACTION_RESOURCE_FACT C ON B.INTERACTION_RESOURCE_ID = C.INTERACTION_RESOURCE_ID"
				+ " LEFT JOIN [dbo].CAMPAIGN D ON A.CAMPAIGN_KEY = D.CAMPAIGN_KEY"
				+ " LEFT JOIN [dbo].CALLING_LIST E ON A.CALLING_LIST_KEY = E.CALLING_LIST_KEY"
				+ " LEFT JOIN [dbo].ATTEMPT_DISPOSITION F ON A.ATTEMPT_DISPOSITION_KEY = F.ATTEMPT_DISPOSITION_KEY"
				+ " LEFT JOIN [dbo].RECORD_TYPE G ON A.RECORD_TYPE_KEY = G.RECORD_TYPE_KEY"
				+ " LEFT JOIN [dbo].RECORD_STATUS H ON A.RECORD_STATUS_KEY = H.RECORD_STATUS_KEY"
				+ " LEFT JOIN [dbo].CALL_RESULT I ON A.CALL_RESULT_KEY = I.CALL_RESULT_KEY"
				+ " LEFT JOIN dbo.DATE_TIME J ON A.START_DATE_TIME_KEY = J.DATE_TIME_KEY"
				+ " LEFT JOIN dbo.DATE_TIME K ON A.END_DATE_TIME_KEY = K.DATE_TIME_KEY"
				+ " LEFT JOIN dbo.RESOURCE_ L ON A.RESOURCE_KEY = L.RESOURCE_KEY"
				+ " LEFT JOIN dbo.IRF_USER_DATA_KEYS M ON C.INTERACTION_RESOURCE_ID = M.INTERACTION_RESOURCE_ID"
				+ " LEFT JOIN dbo.INTERACTION_DESCRIPTOR N ON M.INTERACTION_DESCRIPTOR_KEY = N.INTERACTION_DESCRIPTOR_KEY"
				+ " WHERE CALLING_LIST_NAME = '%s' AND CHAIN_ID = '%s'", callingListName, chainId);

		return query;
	}

	private int convertObjToInteger(Object valueObj) {
		if (valueObj == null) {
			return 0;
		}
		if (valueObj instanceof Integer) {
			return ((Integer) valueObj).intValue();
		} else if (valueObj instanceof BigDecimal) {
			BigDecimal obj = (BigDecimal) valueObj;
			return obj.intValue();
		}
		throw new IllegalArgumentException(String.format("Fail to convertObjToInteger for obj[%s]", valueObj));
	}

	private String convertObjToString(Object valueObj) {
		if (valueObj == null) {
			return "";
		} else if (valueObj instanceof String) {
			return valueObj.toString();
		} else if (valueObj instanceof Integer) {
			Integer intObj = (Integer) valueObj;
			return intObj.toString();
		} else if (valueObj instanceof BigDecimal) {
			BigDecimal obj = (BigDecimal) valueObj;
			return obj.toString();
		}
		throw new IllegalArgumentException(String.format("Fail to convertObjToString for obj[%s]", valueObj));
	}

}
