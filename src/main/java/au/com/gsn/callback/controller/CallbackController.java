package au.com.gsn.callback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.gsn.callback.model.AttemptInfo;
import au.com.gsn.callback.model.CallbackListVO;
import au.com.gsn.callback.model.CallbackRequestVO;
import au.com.gsn.callback.model.RecordsActionResponse;
import au.com.gsn.callback.model.UpdateRecordResponse;
import au.com.gsn.callback.service.CallbackService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class CallbackController {

	private static CallbackListVO callbacks = new CallbackListVO();

	@SuppressWarnings("rawtypes")
	@Autowired
	private CallbackService callbackService;
	
	@RequestMapping(value = "/callbacks", produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET })
	public CallbackListVO getCallbackList(@RequestParam("agentId") String agentId, @RequestParam("callingList") String callingList,
			@RequestParam("managedCallingList") String managedCallingList) {
		callbacks = callbackService.getCallbacks(agentId, callingList, managedCallingList);
		return callbacks;
	}
	
	@RequestMapping(value = "/attemptInfo",produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET })
	public List<AttemptInfo> getAttemptInfo(@RequestParam("chainId") String chainId, @RequestParam("callingListName") String callingListName) {
		return callbackService.getAttemptInfo(chainId, callingListName);
	}

	@RequestMapping(value = "/closeCBs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public RecordsActionResponse closeCallback(@RequestBody List<CallbackRequestVO> callbacks) {
		return callbackService.closeCallbacks(callbacks);
	}
	
	@RequestMapping(value = "/closeCBsOutlook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public void closeCallbackOutlook(@RequestBody List<CallbackRequestVO> callbacks) {
		callbackService.closeCallbacksOutlook(callbacks);
	}
	
	@RequestMapping(value = "/rescheduleCBs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public RecordsActionResponse rescheduleAll(@RequestBody List<CallbackRequestVO> callbacks, String date, String time, String timezone) {
		return callbackService.rescheduleCBs(callbacks, date, time, timezone);
	}
	
	@RequestMapping(value = "/rescheduleCBsOutlook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public void rescheduleAllOutlook(@RequestBody List<CallbackRequestVO> callbacks, String date, String time, String timezone) {
		callbackService.rescheduleCBsOutlook(callbacks, date, time, timezone);
	}
	
	@RequestMapping(value = "/asapCBs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public RecordsActionResponse asapAll(@RequestBody List<CallbackRequestVO> callbacks) {
		return callbackService.asapAll(callbacks);
	}
	
	@RequestMapping(value = "/changeTZs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public RecordsActionResponse changeTZs(@RequestBody List<CallbackRequestVO> callbacks, String timezone) {
		return callbackService.changeTZs(callbacks, timezone);
	}
	
	@RequestMapping(value = "/changeLSs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public RecordsActionResponse changeLeadScore(@RequestBody List<CallbackRequestVO> callbacks, String leadScore) {
		return callbackService.changeLeadScore(callbacks, leadScore);
	}

	@RequestMapping(value = "/changeNote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public RecordsActionResponse changeNote(@RequestBody CallbackRequestVO callback) {
		return callbackService.changeNote(callback);
	}
	
}
