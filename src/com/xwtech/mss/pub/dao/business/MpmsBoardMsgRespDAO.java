package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.MpmsBoardMsgResp;

/**
 * Data access object (DAO) for domain model class MpmsBoardMsgResp.
 * 
 * @see 
 * @author MyEclipse - Hibernate Tools
 */
public class MpmsBoardMsgRespDAO  extends BaseDao {

	private static final Log log = LogFactory.getLog(MpmsBoardMsgRespDAO.class);

	// property constants
	public static final String MPM_USER_ID = "mpmUserId";

	public static final String RESP_INFO = "respInfo";

	public static final String RESP_TIME = "respTime";

	public void save(MpmsBoardMsgResp transientInstance) {
		log.debug("saving MpmsBoardMsgResp instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MpmsBoardMsgResp persistentInstance) {
		log.debug("deleting MpmsBoardMsgResp instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MpmsBoardMsgResp findById(java.lang.Long id) {
		log.debug("getting MpmsBoardMsgResp instance with id: " + id);
		try {
			MpmsBoardMsgResp instance = (MpmsBoardMsgResp) getSession().get(
					"com.xwtech.mss.pub.po.MpmsBoardMsgResp", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MpmsBoardMsgResp instance) {
		log.debug("finding MpmsBoardMsgResp instance by example");
		try {
			List results = getSession().createCriteria(
					"com.xwtech.mss.pub.po.MpmsBoardMsgResp").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding MpmsBoardMsgResp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MpmsBoardMsgResp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMpmUserId(Object mpmUserId) {
		return findByProperty(MPM_USER_ID, mpmUserId);
	}

	public List findByRespInfo(Object respInfo) {
		return findByProperty(RESP_INFO, respInfo);
	}

	public List findByRespTime(Object respTime) {
		return findByProperty(RESP_TIME, respTime);
	}

	public MpmsBoardMsgResp merge(MpmsBoardMsgResp detachedInstance) {
		log.debug("merging MpmsBoardMsgResp instance");
		try {
			MpmsBoardMsgResp result = (MpmsBoardMsgResp) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MpmsBoardMsgResp instance) {
		log.debug("attaching dirty MpmsBoardMsgResp instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MpmsBoardMsgResp instance) {
		log.debug("attaching clean MpmsBoardMsgResp instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List rechatSearchList() {
		StringBuffer hql = new StringBuffer();

		hql.append(" select reboardMsg from MpmsBoardMsgResp reboardMsg ");

		hql.append(" where 1=1 ");
		
		List list = getHibernateTemplate().find(hql.toString());

		return list;
	}
	/**
	 * 根据Id删除记录
	 * 
	 * @param
	 */
	public void delReId(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from mpms_board_msg_resp where resp_id ="+id);
	
		executeCommonSql(sql.toString());
	}
	/**
	 * 根据留言的id删除回复信息记录
	 * 
	 * @param
	 */
	public void delRebychatId(Long chatId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from mpms_board_msg_resp where msg_id ="+chatId);
	
		executeCommonSql(sql.toString());
	}
	
	/**
	 * 根据留言的id查找回复信息记录
	 * 
	 * @param
	 */
	public List ChatIdRebychatId(Long msgid) {
		
		StringBuffer hql = new StringBuffer();

		hql.append(" select reboardMsg from MpmsBoardMsgResp reboardMsg where msg_id ="+msgid+"order by reboardMsg.createTime desc ");
		
		List list = getHibernateTemplate().find(hql.toString());

		return list;
	}
	
}