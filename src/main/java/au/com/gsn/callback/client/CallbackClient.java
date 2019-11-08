package au.com.gsn.callback.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import au.com.gsn.callback.model.CallbackListVO;
import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.model.CallbackResponseVO;
import au.com.gsn.callback.model.InboundCallbackResponse;
import au.com.gsn.callback.model.RecordsActionResponse;
import au.com.gsn.callback.model.UpdateRecordResponse;

public class CallbackClient {

	private WebResource resource;

	public CallbackClient(final String baseURL) {
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJaxbJsonProvider.class);
		Client client = Client.create();
		resource = client.resource(baseURL);
	}

	public CallbackListVO getCallbackList(String agentId, String callingList, String managedCallingList) {

		String path = "/callbacks";
		try {
			return this.resource.path(path)
					.queryParam("agentId", agentId)
					.queryParam("callingList", callingList)
					.queryParam("managedCallingList", managedCallingList)
					.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).get(CallbackListVO.class);
		} catch (Exception e) {
			String error = e.getMessage();
		}
		return null;
	}
	
	public RecordsActionResponse closeCB(List<CallbackRequestVO> callbacks) {
		try{
			String json = getJSONFromObj(callbacks);
			return this.resource.path("/closeCBs")
					.type(MediaType.APPLICATION_JSON_TYPE)
					.entity(json, MediaType.APPLICATION_JSON_TYPE)
					.get(RecordsActionResponse.class);
		}
		catch (Exception e) {
			String error = e.getMessage();
		}
		return null;
		
	}

	public UpdateRecordResponse rescheduleDB(int id, CallbackResponseVO callbackVO) {
		try{
			return this.resource.path("/rescheduleDB")
					.type(MediaType.APPLICATION_JSON_TYPE)
					.entity("", MediaType.APPLICATION_JSON_TYPE)
					.get(UpdateRecordResponse.class);
		}
		catch (Exception e) {
			String error = e.getMessage();
		}
		return null;
	}

	public RecordsActionResponse callNow(List<CallbackRequestVO> callbacks) {
		try{
			String json = getJSONFromObj(callbacks);
			return this.resource.path("/asapCBs/")
					.type(MediaType.APPLICATION_JSON_TYPE)
					.entity(json, MediaType.APPLICATION_JSON_TYPE)
					.get(RecordsActionResponse.class);
		}
		catch (Exception e) {
			String error = e.getMessage();
		}
		return null;
	}

	public UpdateRecordResponse createCallback(CallbackResponseVO callbackVO) {
		return this.resource.path("/create")
				.type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.entity(callbackVO).get(UpdateRecordResponse.class);
	}

	public HttpStatus deleteCallback(int id) {
		HttpStatus result = this.resource.path("/callbacks/delete/" + id).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(HttpStatus.class);
		return result;
	}

	public HttpStatus editCallback(int id, CallbackResponseVO callbackVO) {
		return this.resource.path("/edit/" + id).type(MediaType.APPLICATION_JSON_TYPE).entity(callbackVO, MediaType.APPLICATION_JSON_TYPE).get(HttpStatus.class);
	}

	public InboundCallbackResponse getInboundCB(String agentId){
		String path = "/inboundCB";
		try {
			return this.resource.path(path)
					.queryParam("agentId", agentId)
					.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).get(InboundCallbackResponse.class);
		} catch (Exception e) {
			String error = e.getMessage();
		}
		return null;
		
	}
	
	private String getJSONFromObj(List<CallbackRequestVO> callbacks) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(callbacks);
			return jsonStr;
		} catch (JsonProcessingException e) {
			throw e;
		}
	}
	
}
