package au.com.gsn.outlook.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.aad.adal4j.AuthenticationResult;

import au.com.gsn.outlook.api.OutlookAPI;
import au.com.gsn.outlook.api.OutlookAPIImp;
import au.com.gsn.outlook.exception.APIAuthException;
import au.com.gsn.outlook.exception.OutlookAPIException;
import au.com.gsn.outlook.helper.JsonObjectHelper;
import au.com.gsn.outlook.model.CalendarResponse;
import au.com.gsn.outlook.model.CalenderRequest;
import au.com.gsn.outlook.model.OutlookResponseCode;
import au.com.gsn.outlook.utils.CallbackUtils;

public class OutlookService {

	private Logger LOGGER = LogManager.getLogger(OutlookService.class);

	public CalendarResponse addOutlookCal(CalenderRequest request, String email) {
		CalendarResponse response = new CalendarResponse();
		try {
			AuthenticationResult result = AuthorityService.getInstance().getAuthResult();
			String requestJson = JsonObjectHelper.getJSONFromObj(request);
			OutlookAPI outlookAPI = new OutlookAPIImp();
			response = outlookAPI.addOutlookCal(email, result.getAccessToken(), requestJson);

		} catch (APIAuthException e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_AUTH.name());
		} catch (OutlookAPIException e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_API.name());
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_OTHERS.name());
		}
		return response;
	}

	public CalendarResponse deleteOutlookCal(String email, String calId) {
		CalendarResponse response = new CalendarResponse();
		try {
			AuthenticationResult result = AuthorityService.getInstance().getAuthResult();

			OutlookAPI outlookAPI = new OutlookAPIImp();
			response = outlookAPI.deleteOutlookCal(email, result.getAccessToken(), calId);

		} catch (APIAuthException e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_AUTH.name());
		} catch (OutlookAPIException e) {
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			LOGGER.info(e.getMessage());
			response.setApiResponseCode(OutlookResponseCode.FAIL_API.name());
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_OTHERS.name());
		}
		return response;
	}

	public CalendarResponse updateOutlookCal(CalenderRequest request, String email, String calId) {
		CalendarResponse response = new CalendarResponse();
		try {
			AuthenticationResult result = AuthorityService.getInstance().getAuthResult();
			String requestJson = JsonObjectHelper.getJSONFromObj(request);
			OutlookAPI outlookAPI = new OutlookAPIImp();
			outlookAPI.deleteOutlookCal(email, result.getAccessToken(), calId);
			response = outlookAPI.addOutlookCal(email, result.getAccessToken(), requestJson);

			if (StringUtils.isEmpty(response.getId())) {
				response.setApiResponseCode(OutlookResponseCode.FAIL_EMPTY_RESPONSE.name());
			}
			response.setApiResponseCode(OutlookResponseCode.SUCCESS.name());

		} catch (APIAuthException e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_AUTH.name());
		} catch (OutlookAPIException e) {
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			LOGGER.info(e.getMessage());
			response.setApiResponseCode(OutlookResponseCode.FAIL_API.name());
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			response.setApiErrorMsg(CallbackUtils.cutString(e.getMessage(),249));
			response.setApiResponseCode(OutlookResponseCode.FAIL_OTHERS.name());
		}
		return response;
	}

}
