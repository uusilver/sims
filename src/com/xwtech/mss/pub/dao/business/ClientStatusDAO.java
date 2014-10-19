package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.ClientStatus;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ClientStatus
 * @author MyEclipse Persistence Tools
 */
public class ClientStatusDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ClientStatusDAO.class);
	// property constants
	public static final String CLIENTID = "clientid";
	public static final String CLIENTSTATUS = "clientstatus";
	public static final String ONLINETIMER = "onlinetimer";
	public static final String STATUS = "status";

	public void save(ClientStatus transientInstance) {
		log.debug("saving ClientStatus instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientStatus persistentInstance) {
		log.debug("deleting ClientStatus instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientStatus findById(java.lang.Integer id) {
		log.debug("getting ClientStatus instance with id: " + id);
		try {
			ClientStatus instance = (ClientStatus) getSession().get(
					"com.xwtech.mss.pub.dao.business.ClientStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientStatus instance) {
		log.debug("finding ClientStatus instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ClientStatus")
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
		log.debug("finding ClientStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClientStatus as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClientid(Object clientid) {
		return findByProperty(CLIENTID, clientid);
	}

	public List findByClientstatus(Object clientstatus) {
		return findByProperty(CLIENTSTATUS, clientstatus);
	}

	public List findByOnlinetimer(Object onlinetimer) {
		return findByProperty(ONLINETIMER, onlinetimer);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ClientStatus instances");
		try {
			String queryString = "from ClientStatus";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientStatus merge(ClientStatus detachedInstance) {
		log.debug("merging ClientStatus instance");
		try {
			ClientStatus result = (ClientStatus) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientStatus instance) {
		log.debug("attaching dirty ClientStatus instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientStatus instance) {
		log.debug("attaching clean ClientStatus instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}