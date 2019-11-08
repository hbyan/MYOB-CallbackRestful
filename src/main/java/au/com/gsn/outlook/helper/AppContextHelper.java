package au.com.gsn.outlook.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import au.com.gsn.callback.helper.ConfigFileHelper;
import au.com.gsn.outlook.config.ApiConfiguration;
import au.com.gsn.outlook.config.AuthConfiguration;
import au.com.gsn.outlook.config.DBConfiguration;
import au.com.gsn.outlook.db.imp.AuditLogDaoImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppContextHelper {
	
	private static Logger LOGGER = LogManager.getLogger(AuditLogDaoImp.class);
	
	private static String configFileName = "config.properties";
	private static String externalPropertyLocations = "contextPropertyLocations";
	
	private static ApplicationContext context;
	
	public static AuthConfiguration getAuthConfiguration() {
	
		AuthConfiguration config = (AuthConfiguration) getInstance().getBean("authConfiguration");
		return config;
	}
	
	public static DBConfiguration getDBConfiguration() {
		
		DBConfiguration config = (DBConfiguration) getInstance().getBean("dbConfiguration");
		return config;
	}
	
	public static ApiConfiguration getApiConfiguration() {
	
		ApiConfiguration config = (ApiConfiguration) getInstance().getBean("apiConfiguration");
		return config;
	}
	
	public static ApplicationContext getInstance() {
		
		ConfigFileHelper helper = new ConfigFileHelper();
		String serverPath = helper.findServerPath(configFileName);
		
		String configFileWithPath = serverPath
				+ helper.getPropValueByName(configFileName, externalPropertyLocations);
		
		if(!configFileWithPath.startsWith("/")) {
			configFileWithPath = "/"+configFileWithPath;
		}
		if(context == null) {
			LOGGER.info("configFileWithPath="+configFileWithPath);
			context = new FileSystemXmlApplicationContext("file:"+configFileWithPath);
		}
		return context;
	}

}
