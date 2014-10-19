package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.ClientServerMapping;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientServerMapping entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ClientServerMapping
 * @author MyEclipse Persistence Tools
 */
public class ClientServerMappingDAO extends BaseDao {
	private static final Log log = LogFactory
			.getLog(ClientServerMappingDAO.class);
	// property constants
	public static final String CLIENTID = "clientid";
	public static final String SERVERID = "serverid";

	public void save(ClientServerMapping transientInstance) {
		log.debug("saving ClientServerMapping instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientServerMapping persistentInstance) {
		log.debug("deleting ClientServerMapping instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientServerMapping findById(java.lang.Integer id) {
		log.debug("getting ClientServerMapping instance with id: " + id);
		try {
			ClientServerMapping instance = (ClientServerMapping) getSession()
					.get("com.xwtech.mss.pub.dao.business.ClientServerMapping",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientServerMapping instance) {
		log.debug("finding ClientServerMapping instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ClientServerMapping")
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
		log.debug("finding ClientServerMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClientServerMapping as model where model."
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

	public List findByServerid(Object serverid) {
		return findByProperty(SERVERID, serverid);
	}

	public List findAll() {
		log.debug("finding all ClientServerMapping instances");
		try {
			String queryString = "from ClientServerMapping";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientServerMapping merge(ClientServerMapping detachedInstance) {
		log.debug("merging ClientServerMapping instance");
		try {
			ClientServerMapping result = (ClientServerMapping) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientServerMapping instance) {
		log.debug("attaching dirty ClientServerMapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientServerMapping instance) {
		log.debug("attaching clean ClientServerMapping instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}