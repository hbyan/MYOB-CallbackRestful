package au.com.gsn.outlook.db.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.db.util.HibernateOCSUtil;
import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.outlook.db.model.OutlookEmailTable;

@Component
public class EmailDaoImp {
	
	@Autowired
	private CriteriaHelper criteriaHelper;

	public void syncEmailList(List<String> emailList) {

		//SessionFactory sessionFactory = hibernateOCSUtil.getSessionFactory();
		Session session = criteriaHelper.getSessionOnly();
		Transaction txn = session.beginTransaction();
		if (emailList == null || emailList.size() == 0) {
			return;
		}
		try {
			for (String emailAddress : emailList) {
				OutlookEmailTable newEmailTable = new OutlookEmailTable();
				newEmailTable.setEmailAddress(emailAddress);
				newEmailTable.setActive("Y");
				session.save(newEmailTable);
			}
			txn.commit();
		} catch (RuntimeException e) {
			if (txn != null && txn.isActive())
				txn.rollback();
		} finally {
			session.close();
		}
	}

	public List<OutlookEmailTable> getEmailList() {
		Session session = criteriaHelper.getSessionOnly();
		List<OutlookEmailTable> emailsInDB = new ArrayList<OutlookEmailTable>();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<OutlookEmailTable> query = builder.createQuery(OutlookEmailTable.class);
			Root<OutlookEmailTable> root = query.from(OutlookEmailTable.class);
			query.select(root);
			Query<OutlookEmailTable> q = session.createQuery(query);
			return q.getResultList();

		} catch (RuntimeException e) {
		} finally {
			session.close();
		}
		return emailsInDB;
	}

}
