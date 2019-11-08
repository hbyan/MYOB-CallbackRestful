package au.com.gsn.callback.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.gsn.callback.helper.CriteriaHelper;
import au.com.gsn.callback.tables.InboundCBAgentMapTable;
import au.com.gsn.callback.tables.InboundCBMapTable;

@Component
public class InboundCallbackDAOImpl {
	
	private static Logger logger = LogManager.getLogger(InboundCallbackDAOImpl.class);
	
	@Autowired
	private CriteriaHelper criteriaHelper;
	
	@SuppressWarnings("unchecked")
	public List<InboundCBMapTable> findInboundMap(){
		List<InboundCBMapTable> topList = new ArrayList<InboundCBMapTable>();
		Session session = criteriaHelper.getOCSSession();
		try {
			Criteria criteria = session.createCriteria(InboundCBMapTable.class);
			criteria.add(Restrictions.sqlRestriction("ACTIVE_FLG='Y'"));
			topList = (List<InboundCBMapTable>) criteria.list();
		} catch (Exception e) {
			logger.error(String.format("Fail to retrieve data from MYOB_INBOUND_CALLBACK_MAP due to [%s]",e.getMessage()));
		} finally {
			session.close();
		}
		
		return topList;
	}
	
	@SuppressWarnings("unchecked")
	public List<InboundCBAgentMapTable> findPersonalCBByAgentId(String agentId){
		List<InboundCBAgentMapTable> list = new ArrayList<InboundCBAgentMapTable>();
		Session session = criteriaHelper.getOCSSession();
		try {
			Criteria criteria = session.createCriteria(InboundCBAgentMapTable.class);
			criteria.add(Restrictions.sqlRestriction(String.format("genesys_agent_id='%s'", agentId)));
			list = (List<InboundCBAgentMapTable>) criteria.list();
			session.close();
		} catch (Exception e) {
			logger.error(String.format("Fail to retrieve data from MYOB_INBOUND_CALLBACK_MAP due to [%s]",e.getMessage()));
		} finally {
			session.close();
		}
		return list;
	}
}
