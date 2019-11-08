package au.com.gsn.callback.model;

import java.util.ArrayList;
import java.util.List;

public class RecordsActionResponse {
	
	private List<UpdateRecordResponse> recordsResponse = new ArrayList<UpdateRecordResponse>();
	private UpdateRecordResponse commonResponse = new UpdateRecordResponse();
	
	public List<UpdateRecordResponse> getRecordsResponse() {
		return recordsResponse;
	}
	public void setRecordsResponse(List<UpdateRecordResponse> recordsResponse) {
		this.recordsResponse = recordsResponse;
	}
	public UpdateRecordResponse getCommonResponse() {
		return commonResponse;
	}
	public void setCommonResponse(UpdateRecordResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
	
}
