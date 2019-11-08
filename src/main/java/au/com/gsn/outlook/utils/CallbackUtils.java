package au.com.gsn.outlook.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CallbackUtils {

	private static Logger LOGGER = LogManager.getLogger(CallbackUtils.class);

	static final int ONE_MINUTE_IN_MILLIS = 60;

	public static String className = "CallbackUtils";

	public static boolean isEmptyString(String input) {
		if (input == null || input.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static String convertSchedTimeToTime(int dialSchedTime) {
		if (dialSchedTime == 0) {
			return "";
		}
		try {
			String dateAsTime = new SimpleDateFormat("HH:mm").format(new Date(dialSchedTime * 1000L));
			return dateAsTime;
		} catch (Exception e) {
			LOGGER.error(String.format("Fail to convertSchedTimeToTime from dialSchedTime[%s]", dialSchedTime));
			return "";
		}
	}

	public static Date convertTimestampToDate(int dialSchedTime) {
		return new Date(dialSchedTime * 1000L);
	}

	public static Date convertTimestampToDateInMin(int dialSchedTime, int min) {
		int newSchedTime = getNewTimeStampAfterMins(dialSchedTime, min);
		return new Date(newSchedTime * 1000L);
	}

	public static String convertSchedTimeToDate(int dialSchedTime) {
		if (dialSchedTime == 0) {
			return "";
		}
		try {
			String dateAsDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(dialSchedTime * 1000L));
			return dateAsDate;
		} catch (Exception e) {
			LOGGER.error(String.format("Fail to convertSchedTimeToDate from dialSchedTime[%s]", dialSchedTime));
			return "";
		}
	}

	public static String convertDateToStr(Date dateTime) {

		try {
			String dateAsDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateTime);
			return dateAsDate;
		} catch (Exception e) {
			LOGGER.error(String.format("Fail to convertSchedTimeToDate from dialSchedTime[%s]", dateTime));
			return "";
		}
	}

	public static String convertSchedTime(int dialSchedTime) {
		String date = convertSchedTimeToDate(dialSchedTime);
		String time = convertSchedTimeToTime(dialSchedTime);
		if (!isEmptyString(date) && !isEmptyString(time)) {
			return date + " " + time;
		}
		return "";
	}

	public static String convertSchedTimeInMin(int dialSchedTime, int min) {
		int newSchedTime = getNewTimeStampAfterMins(dialSchedTime, min);
		String date = convertSchedTimeToDate(newSchedTime);
		String time = convertSchedTimeToTime(newSchedTime);
		if (!isEmptyString(date) && !isEmptyString(time)) {
			return date + " " + time;
		}
		return "";
	}

	public static int getNewTimeStampAfterMins(int dialSchedTime, int min) {
		return dialSchedTime + (min * ONE_MINUTE_IN_MILLIS);
	}

	public static boolean isWithinTimeRange(int checkTimestamp, int startTimestamp, int threshodInMin) {
		long endTimestamp = startTimestamp + (threshodInMin * ONE_MINUTE_IN_MILLIS);
		if (checkTimestamp >= startTimestamp && checkTimestamp <= endTimestamp)
			return true;
		return false;
	}

	public static long getScheduledTime(String newDate, String newTime) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			cal.setTime(sdf.parse(newDate + " " + newTime));//
			long newScheduleTime = cal.getTimeInMillis() / 1000;
			return newScheduleTime;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					String.format("Fail to get scheduled time from newDate [%s] and newTime[%s]", newDate, newTime));
		}
	}

	public static long getScheduledTime(String newDateTime) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			cal.setTime(sdf.parse(newDateTime));//
			long newScheduleTime = cal.getTimeInMillis() / 1000;
			return newScheduleTime;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					String.format("Fail to get scheduled time from newDate [%s] and newTime[%s]", newDateTime));
		}
	}

	public static long getScheduledTimeInTimezone(String newDate, String newTime, String timezoneCode) {
		String timezone = CallbackUtils.getTimeZonID(timezoneCode);
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf.setTimeZone(TimeZone.getTimeZone(timezone));
			cal.setTime(sdf.parse(newDate + " " + newTime));//
			long newScheduleTime = cal.getTimeInMillis() / 1000;
			return newScheduleTime;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					String.format("Fail to get scheduled time from newDate [%s] and newTime[%s]", newDate, newTime));
		}
	}

	public static boolean isDateTimeValidWithTimezone(String newDate, String newTime, String callbackTimezone) {
		if (isEmptyString(newDate) || isEmptyString(newTime)) {
			return false;
		}
		try {
			long scheduledTime = getScheduledTimeInTimezone(newDate, newTime, callbackTimezone);
			Calendar date = Calendar.getInstance();
			long currentTime = date.getTimeInMillis() / 1000;
			if (scheduledTime > currentTime) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;

	}

	public static boolean isDateTimeValid(String newDate, String newTime) {
		if (isEmptyString(newDate) || isEmptyString(newTime)) {
			return false;
		}
		try {
			long scheduledTime = getScheduledTime(newDate, newTime);
			Calendar date = Calendar.getInstance();
			long currentTime = date.getTimeInMillis() / 1000;
			if (scheduledTime > currentTime) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static long getCurrentTime(String threadholdMinForCallNow) {
		int threadholdMin = 0;
		if (!isEmptyString(threadholdMinForCallNow)) {
			threadholdMin = Integer.parseInt(threadholdMinForCallNow);
		}
		Calendar date = Calendar.getInstance();
		long currentTime = date.getTimeInMillis() / 1000;
		if (threadholdMin != 0) {
			currentTime = currentTime + (threadholdMin * ONE_MINUTE_IN_MILLIS);
		}

		return currentTime;
	}

	public static List<String> convertStringToList(String inputString) {
		List<String> stringList = new ArrayList<String>();
		if (CallbackUtils.isEmptyString(inputString)) {
			return stringList;
		}
		inputString = inputString.replace("[", "");
		if (CallbackUtils.isEmptyString(inputString)) {
			return stringList;
		}
		inputString = inputString.replace("]", "");
		if (!CallbackUtils.isEmptyString(inputString)) {
			stringList = Arrays.asList(inputString.split("\\s*,\\s*"));
		}
		return stringList;
	}

	public static List<String> removeRecordFromList1ThatAlsoInList2(String stringList1, String stringList2) {
		List<String> list1 = convertStringToList(stringList1);
		List<String> list2 = convertStringToList(stringList2);
		List<String> newList = new ArrayList<String>();
		for (String record : list1) {
			if (list2.contains(record)) {
				continue;
			} else if (!newList.contains(record)) {
				newList.add(record);
			}
		}
		return newList;
	}

	public static String getTimeZonID(String timezoneCode) {
		Map<String, String> timezone = new HashMap<String, String>();
		timezone.put("ACT", "Australia/ACT");
		timezone.put("AET", "AET");
		timezone.put("NSW", "Australia/NSW");
		timezone.put("NT", "Australia/North");
		timezone.put("NZST", "NZ");
		timezone.put("SA", "Australia/South");
		timezone.put("TAS", "Australia/Tasmania");
		timezone.put("VIC", "Australia/Victoria");
		timezone.put("WA", "Australia/West");

		return timezone.get(timezoneCode);
	}

	public static int convertStrToInteger(String value) {
		if (isEmptyString(value)) {
			return 0;
		}
		try {
			int intValue = Integer.valueOf(value);
			return intValue;
		} catch (Exception e) {
			return 0;
		}
	}

	public static int convertObjToInteger(Object valueObj) {
		if (valueObj instanceof Integer) {
			return ((Integer) valueObj).intValue();
		} else if (valueObj instanceof BigDecimal) {
			BigDecimal obj = (BigDecimal) valueObj;
			return obj.intValue();
		}
		throw new IllegalArgumentException(String.format("Fail to convertObjToInteger for obj[%s]", valueObj));
	}

	public static String convertObjToString(Object valueObj) {
		if (valueObj instanceof Integer) {
			Integer intObj = (Integer) valueObj;
			return intObj.toString();
		} else if (valueObj instanceof BigDecimal) {
			BigDecimal obj = (BigDecimal) valueObj;
			return obj.toString();
		}
		throw new IllegalArgumentException(String.format("Fail to convertObjToString for obj[%s]", valueObj));
	}

	public static String cutString(String input, int numOfChars) {
		if (isEmptyString(input)) {
			return "";
		}
		if (input.length() >= 250)
			return input.substring(0, numOfChars - 1);
		return input;
	}

	public static long getCutOffTime(int thresholdInMin) {

		Calendar date = Calendar.getInstance();
		long currentTime = date.getTimeInMillis() / 1000;
		long cutOffTime = currentTime + (thresholdInMin * ONE_MINUTE_IN_MILLIS);

		return cutOffTime;
	}

	public static int convertStrToIntWithDefault(String str, int defaultInt) {
		int result = defaultInt;
		if (StringUtils.isEmpty(str)) {
			return defaultInt;
		}
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			result = defaultInt;
		}

		return result;
	}
}
