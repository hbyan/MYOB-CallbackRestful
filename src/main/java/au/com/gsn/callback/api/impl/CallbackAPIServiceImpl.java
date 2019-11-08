package au.com.gsn.callback.api.impl;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import au.com.gsn.callback.api.helper.CallbackAPIHelper;
import au.com.gsn.callback.helper.UpdateRecordResponseCode;
import au.com.gsn.callback.model.CallbackJSONRequest;
import au.com.gsn.callback.model.CallbackResponseVO;
import au.com.gsn.callback.model.UpdateRecordResponse;

@Service
public class CallbackAPIServiceImpl implements CallbackAPIService {

	private Client client;

	private static final Logger LOGGER = LogManager.getLogger(CallbackAPIServiceImpl.class);

	@Value("${base.url}")
	private String baseUrl;

	@PostConstruct
	public void initService() {
		client = Client.create();
		if (baseUrl == null || baseUrl.equals("")) {
			baseUrl = "http://localhost:1111";
		}
	}

	@Override
	public UpdateRecordResponse createRecord(CallbackResponseVO callbackVO) {

		String resourcePath = baseUrl + callbackVO.getCallingList() + "?req=AddRecord";
		UpdateRecordResponse result = new UpdateRecordResponse();
		try {
			WebResource resource = client.resource(resourcePath);
			CallbackJSONRequest jsonRequest = constructCallbackJSONRequest(callbackVO);
			String jsonStr = CallbackAPIHelper.getJSONFromObj(jsonRequest);
			LOGGER.info(resourcePath + "/n" + jsonStr);
			ClientResponse responseObj = resource.accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, jsonStr);
			String responseStr = responseObj.getEntity(String.class);
			LOGGER.info(responseStr);
			if (HttpStatus.OK.value() == responseObj.getStatus()) {
				result.setResponseCode(UpdateRecordResponseCode.SUCCESS.name());
			} else {
				result.setResponseCode(UpdateRecordResponseCode.API_ERROR_RESPONSE.name());
				result.setLogMessage(responseStr);
			}
		} catch (Exception e) {
			result.setResponseCode(UpdateRecordResponseCode.SYSTEM_ERROR.name());
			LOGGER.error(e);
		}
		return result;
	}

	private CallbackJSONRequest constructCallbackJSONRequest(CallbackResponseVO callbackVO) {
		CallbackJSONRequest request = new CallbackJSONRequest();
		request.setGswAgentId(callbackVO.getAgentId());
		request.setFirstName(callbackVO.getFirstName());
		request.setLastName(callbackVO.getLastName());
		request.setGswCampaignName(callbackVO.getCampaign());
		request.setGswDateTime(CallbackAPIHelper.getFormattedDatetime(callbackVO.getDate(), callbackVO.getTime()));
		request.setState(callbackVO.getState());
		request.setGswPhone(callbackVO.getPreferredPhNo());
		request.setGswPhoneType("2");  //Direct Business Phone
		request.setConnId(callbackVO.getConnId());
		request.setGswRecordType(callbackVO.getRecordType());
		request.setGswTimezone(callbackVO.getTimezone()); // Mandatory
		request.setClientId(callbackVO.getSiebelClientId());
		return request;
	}
	
	private String getXmlResponseContent(String output){
		if(output == null || output.equals("")){
			return "";
		}
		int index1 = output.toLowerCase().indexOf("<body>");
		int index2 = output.toLowerCase().indexOf("</body>");
		String subStr1 = output.substring(index1+6,index2);
		return subStr1;
	}
}
