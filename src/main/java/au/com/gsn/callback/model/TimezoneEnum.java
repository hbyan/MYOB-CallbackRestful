package au.com.gsn.callback.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum TimezoneEnum {

	NSW(143, "Australia/NSW"), VIC(144, "Australia/Victoria"), ACT(145, "tralia/ACT"), TAS(146,
			"Australia/Tasmania"), SA(147, "Australia/South"), NT(148, "Australia/North"), QLD(149,
					"Australia/QLD"), WA(150, "Australia/West"), NZST(151, "NZ"), UNKNOW(0, "");

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
		try{
			for (TimezoneEnum e : TimezoneEnum.values()) {
				if (id == e.getId())
					return e.name();
			}
		}catch(Exception e){
			return "";
		}
		
		return "";
	}

}
