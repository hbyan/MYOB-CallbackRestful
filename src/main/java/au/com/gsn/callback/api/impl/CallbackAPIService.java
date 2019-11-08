package au.com.gsn.callback.api.impl;

import au.com.gsn.callback.model.CallbackResponseVO;
import au.com.gsn.callback.model.UpdateRecordResponse;

public interface CallbackAPIService {
	
	public UpdateRecordResponse createRecord(CallbackResponseVO callbackVO);
		
}
