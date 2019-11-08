package au.com.gsn.outlook.api;

import au.com.gsn.outlook.exception.OutlookAPIException;
import au.com.gsn.outlook.model.CalendarResponse;

public interface OutlookAPI {
	
	public CalendarResponse addOutlookCal(String email, String token, String requestJson) throws OutlookAPIException ;
	
	public CalendarResponse deleteOutlookCal(String email, String token, String id) throws OutlookAPIException ;
	
/*	public CalendarResponse updateOutlookCal(String email, String token, String id, String requestJson) throws OutlookAPIException;
	*/
}
