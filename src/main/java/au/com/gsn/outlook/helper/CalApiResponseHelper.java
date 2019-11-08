package au.com.gsn.outlook.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.gsn.outlook.model.ApiErrorBody;
import au.com.gsn.outlook.model.ApiErrorResponse;
import au.com.gsn.outlook.model.CalendarResponse;
import au.com.gsn.outlook.model.OutlookResponseCode;

public class CalApiResponseHelper {

	private static Logger LOGGER = LogManager.getLogger(CalApiResponseHelper.class);

	public static CalendarResponse getAddResponse(Integer status, String responseStr) {
		CalendarResponse response = getDefaultResponse(status);
		if (!OutlookResponseCode.FAIL_OTHERS.name().equals(response.getApiResponseCode())) {
			return response;
		}
		// SUCCESS
		if (status == 201) {
			response = JsonObjectHelper.convertJSONToCalResp(responseStr);
			response.setApiResponseCode(OutlookResponseCode.SUCCESS.name());
		} else {
			LOGGER.error("Error Response:"+response.getApiErrorMsg());
			populateErrorResponse(response, responseStr);
		}

		return response;
	}

	public static CalendarResponse getDeleteResponse(Integer status, String responseStr) {
		CalendarResponse response = getDefaultResponse(status);
		if (!OutlookResponseCode.FAIL_OTHERS.name().equals(response.getApiResponseCode())) {
			return response;
		}
		if (status == 204) {
			response.setApiResponseCode(OutlookResponseCode.SUCCESS.name());
		} else {
			populateErrorResponse(response, responseStr);
		}
		return response;
	}

	public static CalendarResponse getUpdateResponse(Integer status, String responseStr) {
		CalendarResponse response = getDefaultResponse(status);
		if (!OutlookResponseCode.FAIL_OTHERS.name().equals(response.getApiResponseCode())) {
			return response;
		}
		if (status == 201) {
			response = JsonObjectHelper.convertJSONToCalResp(responseStr);
			response.setApiResponseCode(OutlookResponseCode.SUCCESS.name());
		} else {
			populateErrorResponse(response, responseStr);
		}
		return response;
	}

	public static CalendarResponse getDefaultResponse(Integer status) {
		CalendarResponse response = new CalendarResponse();
		response.setApiResponseCode(OutlookResponseCode.FAIL_OTHERS.name());
		if (status == null || status.intValue() == 0) {
			response.setApiResponseCode(OutlookResponseCode.RESP_STATUS_EMPTY.name());
		}
		return response;
	}

	public static void populateErrorResponse(CalendarResponse response, String responseStr) {

		ApiErrorResponse errorResp = JsonObjectHelper.convertJSONStrToErrorResp(responseStr);
		ApiErrorBody error = errorResp.getError();
		response.setApiErrorMsg(error.getMessage());
		response.setApiErrorCode(error.getCode());
		response.setApiResponseCode(OutlookResponseCode.FAIL_ERROR_RESPONSE.name());
	}

}
