package au.com.gsn.callback.api.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.gsn.callback.model.CallbackJSONRequest;

public class CallbackAPIHelper {

	private static Logger LOGGER = LogManager.getLogger(CallbackAPIHelper.class);

	public static String getJSONFromObj(CallbackJSONRequest obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			return jsonStr;
		} catch (JsonProcessingException e) {
			LOGGER.error(String.format("Fail to convert to json str from obj for req ID{%s}", obj.getGswPhone()));
			throw e;
		}
	}

	public static String formatDate(String inputDate) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(inputDate);
			SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
			String formattedDate = newFormat.format(date);
			return formattedDate;
		} catch (Exception e) {
			LOGGER.error(String.format("can not format date[%s]", inputDate));
			throw new IllegalArgumentException(String.format("can not format date[%s]", inputDate));
		}
	}

	public static String getFormattedDatetime(String date, String time) {
		String formattedDate = formatDate(date);
		return formattedDate+" "+time;
	}
	
}
