package au.com.gsn.outlook.helper;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.gsn.outlook.model.ApiErrorResponse;
import au.com.gsn.outlook.model.CalendarResponse;
import au.com.gsn.outlook.model.CalenderRequest;

public class JsonObjectHelper {
	
	private static Logger LOGGER = LogManager.getLogger(JsonObjectHelper.class);

	public static String getJSONFromObj(CalenderRequest obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			return jsonStr;
		} catch (JsonProcessingException e) {
			LOGGER.error(String.format("Fail to convert to json str from obj"));
			throw e;
		}
	}
	
	public static CalendarResponse convertJSONToCalResp(String responseStr) {
		CalendarResponse jsonResponse = new CalendarResponse();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jsonResponse = mapper.readValue(responseStr, CalendarResponse.class);
		} catch (IOException e) {
			LOGGER.error(String.format("Fail to convert success resp str [%s] to CalendarReponse", responseStr));
		}
		return jsonResponse;
	}
	
	
	public static ApiErrorResponse convertJSONStrToErrorResp(String responseStr){
		ApiErrorResponse errorResp = new ApiErrorResponse();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			errorResp = mapper.readValue(responseStr, ApiErrorResponse.class);
		} catch (IOException e) {
			LOGGER.error(String.format("Fail to convert success resp str [%s] to CalendarReponse", responseStr));
		}
		return errorResp;
	}

}
