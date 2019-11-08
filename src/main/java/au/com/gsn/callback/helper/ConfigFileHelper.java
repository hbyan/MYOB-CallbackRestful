package au.com.gsn.callback.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

public class ConfigFileHelper {

	private static Logger LOGGER = LogManager.getLogger(ConfigFileHelper.class);
	
	private static String configFileName = "config.properties";
	private static String externalPropertyLocations = "externalPropertyLocations";
	private static String projectName = "projectName";
	
	public Properties getExternalProperties() {
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			String serverPath = findServerPath(configFileName);
			String configFileWithPath = serverPath + getPropValueByName(configFileName, externalPropertyLocations);
			inputStream = new FileInputStream(configFileWithPath);
			if (inputStream != null) {
				prop.load(inputStream);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return prop;
	}
	
	public String getPropValueByName(String configFileName, String propertyName) {

		InputStream inputStream = null;
		String propValue = "";
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			}
			propValue = prop.getProperty(propertyName);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		if(StringUtils.isEmpty(propValue)){
			throw new IllegalArgumentException(String.format("Fail to find property for configFile[%s] and propertyName[%s]", configFileName, propertyName));
		}
		return propValue;
	}

	public String findServerPath(String configFileName) {
		String fileAbsolutePath = findFileAbsolutePath(configFileName);
		String serverPath = "";
		if (StringUtils.isEmpty(fileAbsolutePath)) {
			throw new IllegalArgumentException(String.format("Fail to find fileAbsolutePath for file[%s]", configFileName));
		}
		int lookupIndex = fileAbsolutePath.indexOf("webapps");
		if (lookupIndex <= 0) {
			lookupIndex = findIndexFromLocalPackage(fileAbsolutePath);
		}
		if (lookupIndex > 0) {
			serverPath = fileAbsolutePath.substring(0, lookupIndex);
		}
		return serverPath;
	}

	// the following is only for local testing which is not under tomcat
	private int findIndexFromLocalPackage(String fileAbsolutePath) {
		String project = getPropValueByName(configFileName, projectName);
		int packageIndex = fileAbsolutePath.indexOf(project);
		if (packageIndex <= 0) {
			packageIndex = fileAbsolutePath.indexOf("BRR");
		}
		return packageIndex;
	}

	public String findFileAbsolutePath(String configFileName) {

		String filePath = "";
		ClassLoader classLoader = getClass().getClassLoader();
		URL url = classLoader.getResource(configFileName);
		if (url != null) {
			filePath = url.getFile();
		}
		return filePath;
	}
	
}
