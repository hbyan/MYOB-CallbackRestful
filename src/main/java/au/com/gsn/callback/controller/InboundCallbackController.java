package au.com.gsn.callback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.gsn.callback.api.impl.CallbackAPIService;
import au.com.gsn.callback.helper.InboundCallbackHelper;
import au.com.gsn.callback.helper.UpdateRecordResponseCode;
import au.com.gsn.callback.model.CallbackResponseVO;
import au.com.gsn.callback.model.InboundCallbackResponse;
import au.com.gsn.callback.model.UpdateRecordResponse;
import au.com.gsn.callback.utils.CallbackUtils;

@RestController
public class InboundCallbackController {
	
	@Autowired
	private CallbackAPIService callbackAPIService;
	
	@Autowired
	private InboundCallbackHelper inboundCallbackHelper;

	@RequestMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE }, method = { RequestMethod.GET, RequestMethod.POST })
	public UpdateRecordResponse createCallback(@RequestBody CallbackResponseVO callback) {
		UpdateRecordResponse response = new UpdateRecordResponse();
		if (!CallbackUtils.isDateTimeValidWithTimezone(callback.getDate(), callback.getTime(), callback.getTimezone())) {
			response.setResponseCode(UpdateRecordResponseCode.FAIL_VALIDATION.name());
			response.setLogMessage("Can't create callback for past date and time, or empty date time");
		} else {
			response = callbackAPIService.createRecord(callback);
		}
		return response;
	}
	
	@RequestMapping(value = "/inboundCB",produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET})
	public InboundCallbackResponse callbackDBMap(@RequestParam("agentId") String agentId) {
		return inboundCallbackHelper.getInboundCallbackMap(agentId);
	}
	
	@RequestMapping(value = "/ping",produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET})
	public String ping() {
		return "Hello";
	}
}
