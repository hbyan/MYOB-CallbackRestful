package au.com.gsn.callback.helper;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.db.util.HibernateInfomartUtil;
import au.com.gsn.callback.db.util.HibernateOCSUtil;

@Component
public class CriteriaHelper {

	@Autowired
	private HibernateOCSUtil hibernateOCSUtil;
	
	@Autowired
	private HibernateInfomartUtil hibernateInfomartUtil;

	private Session session;

	public void closeSession() {
		session.close();
	}

	public Session getOCSSession() {
		Session session = hibernateOCSUtil.getSessionFactory().openSession();
		session.beginTransaction();
		return session;
	}
	
	public Session getInfomartSession() {
		Session session = hibernateInfomartUtil.getSessionFactory().openSession();
		session.beginTransaction();
		return session;
	}
	
	public Session getSessionOnly() {
		Session session = hibernateOCSUtil.getSessionFactory().openSession();
		return session;
	}

	public Criteria createCriteria(Session session, String tableClassName) {
		try {
			Class<?> className = Class.forName("au.com.gsn.callback.tables."+tableClassName);
			return session.createCriteria(className);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Fail to create criteria from tableClassName[%s]", tableClassName));
		}
	}

}
