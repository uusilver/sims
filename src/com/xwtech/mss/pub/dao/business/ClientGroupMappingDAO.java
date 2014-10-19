package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.ClientGroupMapping;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientGroupMapping entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ClientGroupMapping
 * @author MyEclipse Persistence Tools
 */
public class ClientGroupMappingDAO extends BaseDao {
	private static final Log log = LogFactory
			.getLog(ClientGroupMappingDAO.class);
	// property constants
	public static final String CLIENTID = "clientid";
	public static final String CLIENTGROUPID = "clientgroupid";

	public void save(ClientGroupMapping transientInstance) {
		log.debug("saving ClientGroupMapping instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientGroupMapping persistentInstance) {
		log.debug("deleting ClientGroupMapping instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientGroupMapping findById(java.lang.Integer id) {
		log.debug("getting ClientGroupMapping instance with id: " + id);
		try {
			ClientGroupMapping instance = (ClientGroupMapping) getSession()
					.get("com.xwtech.mss.pub.dao.business.ClientGroupMapping",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientGroupMapping instance) {
		log.debug("finding ClientGroupMapping instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ClientGroupMapping")
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
		log.debug("finding ClientGroupMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClientGroupMapping as model where model."
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

	public List findByClientgroupid(Object clientgroupid) {
		return findByProperty(CLIENTGROUPID, clientgroupid);
	}

	public List findAll() {
		log.debug("finding all ClientGroupMapping instances");
		try {
			String queryString = "from ClientGroupMapping";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientGroupMapping merge(ClientGroupMapping detachedInstance) {
		log.debug("merging ClientGroupMapping instance");
		try {
			ClientGroupMapping result = (ClientGroupMapping) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientGroupMapping instance) {
		log.debug("attaching dirty ClientGroupMapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientGroupMapping instance) {
		log.debug("attaching clean ClientGroupMapping instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}