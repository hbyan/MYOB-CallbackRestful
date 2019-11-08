package au.com.gsn.callback.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.gsn.callback.model.UpdateRecordResponse;
import au.com.gsn.callback.utils.CallbackUtils;

public class CallbackActionHelper {
	
	private static Logger LOGGER = LogManager.getLogger("au.com.gsn.callbackmanager");
	
	public static final int minThreashold = 5;
	
	private static int getThreadhold(String threadholdMin) {
		if (CallbackUtils.isEmptyString(threadholdMin)) {
			return minThreashold;
		} else {
			return Integer.parseInt(threadholdMin);
		}
	}
	
	public static UpdateRecordResponse checkCanReschedule(String date, String time, String threshod) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		response.setResponseCode(UpdateRecordResponseCode.SUCCESS.name());
		if (!CallbackUtils.isDateTimeValid(date, time)) {
			String error = "Can't reschedule to pass date and time, or empty date time";
			LOGGER.error(error);
			response.setLogMessage(error);
			response.setResponseCode(UpdateRecordResponseCode.FAIL_VALIDATION.name());
		}
		boolean canReschedule = CallbackUtils.canReschedule(date, time, getThreadhold(threshod));
		if (!canReschedule) {
			String error = "Can't reschedule to pass date and time, or empty date time";
			LOGGER.info(error);
			response.setLogMessage(error);
			response.setResponseCode(UpdateRecordResponseCode.CANNOT_RESCHEDULE.name());
		}
		
		return response;
	}

}
