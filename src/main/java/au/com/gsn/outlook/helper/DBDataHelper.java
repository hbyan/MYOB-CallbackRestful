package au.com.gsn.outlook.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import au.com.gsn.outlook.db.model.OCSRecord;

public class DBDataHelper {

	public static String getPartialSQLFromCommaSeparateStr(String commaSeparateStr) {

		String sqlStr = "";
		if (StringUtils.isEmpty(commaSeparateStr)) {
			return sqlStr;
		}
		List<String> list = Arrays.asList(commaSeparateStr.split(","));
		for (String excludeStr : list) {

			sqlStr += " and TABLE_Name not like '%" + excludeStr.trim() + "%'";
		}
		return sqlStr;
	}

	public static List<OCSRecord> convertToOCSRecord(List<Object[]> resultList) {
		//CONTACT_FST_NAME,CONTACT_LAST_NAME,STATE, contact_info,ACCOUNT_NAME,CLIENT_ID
		List<OCSRecord> list = new ArrayList<OCSRecord>();
		if(resultList == null || resultList.size() == 0) {
			return list;
		}
		for (Object[] record : resultList) {
			OCSRecord table = new OCSRecord();
			int recordId = convertObjToInteger(record[0]);
			String emailAddress = (String)record[1];
			String campaign = (String) record[2];
			int scheduledTime = convertObjToInteger(record[3]);
			int tzId = convertObjToInteger(record[4]);
			table.setFirstName((String)record[5]);
			table.setLastName((String)record[6]);
			table.setState((String)record[7]);
			table.setContactInfo((String)record[8]);
			table.setAcctName((String)record[9]);
			table.setClientId((String)record[10]);
			
			table.setRecordId(recordId);
			table.setEmailAddress(emailAddress);
			table.setCampaign(campaign);
			table.setScheduledDialTime(scheduledTime);
			table.setTzId(tzId);
			
			list.add(table);
		}
		return list;
	}

	private static int convertObjToInteger(Object valueObj) {
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

	public static boolean isOracle(String isOracle) {
		boolean isOracleDB = true;
		if(StringUtils.isEmpty(isOracle)) {
			return isOracleDB;
		}
		if(!Boolean.parseBoolean(isOracle)) {
			return false; 
		}
		return isOracleDB;
	}


}
