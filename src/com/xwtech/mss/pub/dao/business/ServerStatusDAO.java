package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.ServerStatus;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServerStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ServerStatus
 * @author MyEclipse Persistence Tools
 */
public class ServerStatusDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ServerStatusDAO.class);
	// property constants
	public static final String SERVERID = "serverid";
	public static final String RUNSTATUS = "runstatus";
	public static final String LINKCOUNT = "linkcount";
	public static final String DELAY = "delay";
	public static final String UPFLOW = "upflow";
	public static final String DOWNFLOW = "downflow";
	public static final String NODETYPE = "nodetype";
	public static final String STATUS = "status";

	public void save(ServerStatus transientInstance) {
		log.debug("saving ServerStatus instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ServerStatus persistentInstance) {
		log.debug("deleting ServerStatus instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ServerStatus findById(java.lang.Integer id) {
		log.debug("getting ServerStatus instance with id: " + id);
		try {
			ServerStatus instance = (ServerStatus) getSession().get(
					"com.xwtech.mss.pub.dao.business.ServerStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServerStatus instance) {
		log.debug("finding ServerStatus instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ServerStatus")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ServerStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ServerStatus as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServerid(Object serverid) {
		return findByProperty(SERVERID, serverid);
	}

	public List findByRunstatus(Object runstatus) {
		return findByProperty(RUNSTATUS, runstatus);
	}

	public List findByLinkcount(Object linkcount) {
		return findByProperty(LINKCOUNT, linkcount);
	}

	public List findByDelay(Object delay) {
		return findByProperty(DELAY, delay);
	}

	public List findByUpflow(Object upflow) {
		return findByProperty(UPFLOW, upflow);
	}

	public List findByDownflow(Object downflow) {
		return findByProperty(DOWNFLOW, downflow);
	}

	public List findByNodetype(Object nodetype) {
		return findByProperty(NODETYPE, nodetype);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ServerStatus instances");
		try {
			String queryString = "from ServerStatus";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServerStatus merge(ServerStatus detachedInstance) {
		log.debug("merging ServerStatus instance");
		try {
			ServerStatus result = (ServerStatus) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServerStatus instance) {
		log.debug("attaching dirty ServerStatus instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServerStatus instance) {
		log.debug("attaching clean ServerStatus instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}