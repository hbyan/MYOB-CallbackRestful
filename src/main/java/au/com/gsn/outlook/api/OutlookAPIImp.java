package au.com.gsn.outlook.api;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;

import au.com.gsn.outlook.config.ApiConfiguration;
import au.com.gsn.outlook.exception.OutlookAPIException;
import au.com.gsn.outlook.helper.AppContextHelper;
import au.com.gsn.outlook.helper.CalApiResponseHelper;
import au.com.gsn.outlook.model.CalendarResponse;
import au.com.gsn.outlook.model.OutlookResponseCode;

@Component
public class OutlookAPIImp implements OutlookAPI {

	private Logger LOGGER = LogManager.getLogger(OutlookAPIImp.class);

	public CalendarResponse addOutlookCal(String email, String token, String jsonRequest) throws OutlookAPIException {
		
		
		ApiConfiguration config = AppContextHelper.getApiConfiguration();

		Client client = Client.create();
		client.setConnectTimeout(config.getConnectionTimeout());
		client.setReadTimeout(config.getReadTimeout());
		String requestUrl = config.getApiBaseUrl() + email + "/events";
		String authToken = "Bearer " + token;
		String responseStr = "";
		LOGGER.info(String.format("Add Outlook Cal, Send API ***Request*** %s", jsonRequest));
		try {

			ClientResponse response = client.resource(requestUrl).accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json").header("Authorization", authToken)
					.post(ClientResponse.class, jsonRequest);

			responseStr = response.getEntity(String.class);
			LOGGER.info(String.format("Add Outlook Cal,  API ***Response*** %s", responseStr));
			
			return CalApiResponseHelper.getAddResponse(response.getStatus(), responseStr);
			
		}catch(ClientHandlerException e) {
			String errorMsg = String.format("FAIL to add outlook calendar to email[%s] due to [%s]", email,
					e.getMessage());
			LOGGER.error(errorMsg);
			throw new OutlookAPIException(errorMsg);
		} catch (Exception e) {
			String errorMsg = String.format("FAIL to add outlook calendar to email[%s] due to [%s]", email,
					e.getMessage());
			LOGGER.error(errorMsg);
			throw new OutlookAPIException(errorMsg);
		}

	}

	public CalendarResponse deleteOutlookCal(String email, String token, String id) throws OutlookAPIException {
		
		ApiConfiguration config = AppContextHelper.getApiConfiguration();

		Client client = Client.create();
		client.setConnectTimeout(config.getConnectionTimeout());
		client.setReadTimeout(config.getReadTimeout());
		String requestUrl = config.getApiBaseUrl() + email + "/events/"+id;
		String authToken = "Bearer " + token;
		String responseStr = "";
		LOGGER.info(String.format("Delete Outlook Cal, Send API ***Request URL***", requestUrl));
		try {

			ClientResponse response = client.resource(requestUrl).accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json").header("Authorization", authToken)
					.delete(ClientResponse.class);
			
			int status = response.getStatus();
			CalendarResponse calResponse = CalApiResponseHelper.getDefaultResponse(status);
			if (response.getStatus() == 204) {
				calResponse.setApiResponseCode(OutlookResponseCode.SUCCESS.name());
			} else {
				responseStr = response.getEntity(String.class);
				CalApiResponseHelper.populateErrorResponse(calResponse, responseStr);
			}
	
			LOGGER.info(String.format("Delete Outlook Cal,  API ***Response*** %s", responseStr));
			return calResponse;
			
		}catch(ClientHandlerException e) {
			String errorMsg = String.format("FAIL to delete outlook calendar to email[%s] due to [%s]", email,
					e.getMessage());
			LOGGER.error(errorMsg);
			throw new OutlookAPIException(errorMsg);
		} catch (Exception e) {
			String errorMsg = String.format("FAIL to delete outlook calendar to email[%s] due to [%s]", email,
					e.getMessage());
			LOGGER.error(errorMsg);
			throw new OutlookAPIException(errorMsg);
		}

	}

	public CalendarResponse updateOutlookCal(String email, String token, String id, String jsonRequest) throws OutlookAPIException {
		ApiConfiguration config = AppContextHelper.getApiConfiguration();

		Client client = Client.create();
		client.setConnectTimeout(config.getConnectionTimeout());
		client.setReadTimeout(config.getReadTimeout());
		String requestUrl = config.getApiBaseUrl() + email + "/events/"+id;
		String authToken = "Bearer " + token;
		String responseStr = "";
		LOGGER.info(String.format("Delete Outlook Cal, Send API ***Request URL***", requestUrl));
		try {

			ClientResponse response = client.resource(requestUrl).accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json").header("Authorization", authToken)
					.put(ClientResponse.class, jsonRequest);

			responseStr = response.getEntity(String.class);
			LOGGER.info(String.format("Update Outlook Cal,  API ***Response*** %s", responseStr));

			return CalApiResponseHelper.getUpdateResponse(response.getStatus(), responseStr);
			
		}catch(ClientHandlerException e) {
			String errorMsg = String.format("FAIL to update outlook calendar to email[%s] due to [%s]", email,
					e.getMessage());
			LOGGER.error(errorMsg);
			throw new OutlookAPIException(errorMsg);
		} catch (Exception e) {
			String errorMsg = String.format("FAIL to update outlook calendar to email[%s] due to [%s]", email,
					e.getMessage());
			LOGGER.error(errorMsg);
			throw new OutlookAPIException(errorMsg);
		}

	}


}
