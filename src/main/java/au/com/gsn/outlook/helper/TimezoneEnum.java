package au.com.gsn.outlook.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum TimezoneEnum {

	NSW(143, "AUS Eastern Standard Time"), 
	VIC(144, "AUS Eastern Standard Time"),
	ACT(145, "AUS Eastern Standard Time"), 
	TAS(146,"Tasmania Standard Time"), 
	SA(147, "AUS Central Standard Time"), 
	NT(148, "AUS Central Standard Time"), 
	QLD(149,"E. Australia Standard Time"), 
	WA(150,"W. Australia Standard Time"), 
	NZST(151, "New Zealand Standard Time"), 
	UNKNOW(0, "");

	private static Logger logger = LogManager.getLogger(TimezoneEnum.class);
	private int id;
	private String desc;

	TimezoneEnum(int id, String desc) {
		this.desc = desc;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static int getId(String name) {
		try {
			return TimezoneEnum.valueOf(name).getId();
		} catch (Exception e) {
		}
		return 0;
	}

	public static String getName(int id) {
		try {
			for (TimezoneEnum e : TimezoneEnum.values()) {
				if (id == e.getId())
					return e.name();
			}
		} catch (Exception e) {
			return "";
		}

		return "";
	}
	
	public static String getDesc(int id) {
		try {
			for (TimezoneEnum e : TimezoneEnum.values()) {
				if (id == e.getId())
					return e.desc;
			}
		} catch (Exception e) {
			return "";
		}

		return "";
	}


}
