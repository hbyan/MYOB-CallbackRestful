package au.com.gsn.callback.db.util;

import java.io.File;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.helper.ConfigFileHelper;

@Component
@Scope("singleton")
public class HibernateOCSUtil {

	private SessionFactory sessionFactory;
	private static String configFileName = "config.properties";
	private static String externalPropertyLocations = "externalOCSDBLocations";

	@PostConstruct
	public void buildSessionFactory() {
		try {
			ConfigFileHelper helper = new ConfigFileHelper();
			String serverPath = helper.findServerPath(configFileName);
			String configFileWithPath = serverPath
					+ helper.getPropValueByName(configFileName, externalPropertyLocations);
			File file = new File(configFileWithPath);
			sessionFactory = new Configuration().configure(file).buildSessionFactory();

		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory;
	}

	public void shutdown() {
		getSessionFactory().close();
	}

}