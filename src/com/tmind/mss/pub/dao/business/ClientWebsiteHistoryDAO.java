package com.tmind.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.tmind.framework.pub.dao.BaseDao;
import com.tmind.mss.pub.po.ClientWebsiteHistory;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientWebsiteHistory entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.tmind.mss.pub.dao.business.ClientWebsiteHistory
 * @author MyEclipse Persistence Tools
 */
public class ClientWebsiteHistoryDAO extends BaseDao {
	private static final Log log = LogFactory
			.getLog(ClientWebsiteHistoryDAO.class);
	// property constants
	public static final String CLIENTID = "clientid";
	public static final String DOMAINNAME = "domainname";
	public static final String WEBSITEIP = "websiteip";
	public static final String STATUS = "status";

	public void save(ClientWebsiteHistory transientInstance) {
		log.debug("saving ClientWebsiteHistory instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientWebsiteHistory persistentInstance) {
		log.debug("deleting ClientWebsiteHistory instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientWebsiteHistory findById(java.lang.Integer id) {
		log.debug("getting ClientWebsiteHistory instance with id: " + id);
		try {
			ClientWebsiteHistory instance = (ClientWebsiteHistory) getSession()
					.get("com.tmind.mss.pub.dao.business.ClientWebsiteHistory",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientWebsiteHistory instance) {
		log.debug("finding ClientWebsiteHistory instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.tmind.mss.pub.dao.business.ClientWebsiteHistory")
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
		log.debug("finding ClientWebsiteHistory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClientWebsiteHistory as model where model."
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

	public List findByDomainname(Object domainname) {
		return findByProperty(DOMAINNAME, domainname);
	}

	public List findByWebsiteip(Object websiteip) {
		return findByProperty(WEBSITEIP, websiteip);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ClientWebsiteHistory instances");
		try {
			String queryString = "from ClientWebsiteHistory";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientWebsiteHistory merge(ClientWebsiteHistory detachedInstance) {
		log.debug("merging ClientWebsiteHistory instance");
		try {
			ClientWebsiteHistory result = (ClientWebsiteHistory) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientWebsiteHistory instance) {
		log.debug("attaching dirty ClientWebsiteHistory instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientWebsiteHistory instance) {
		log.debug("attaching clean ClientWebsiteHistory instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}