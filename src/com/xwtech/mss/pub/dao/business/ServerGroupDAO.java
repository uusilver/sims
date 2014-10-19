package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.ServerGroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServerGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ServerGroup
 * @author MyEclipse Persistence Tools
 */
public class ServerGroupDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ServerGroupDAO.class);
	// property constants
	public static final String SERVERGROUPNAME = "servergroupname";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void save(ServerGroup transientInstance) {
		log.debug("saving ServerGroup instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ServerGroup persistentInstance) {
		log.debug("deleting ServerGroup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ServerGroup findById(java.lang.Integer id) {
		log.debug("getting ServerGroup instance with id: " + id);
		try {
			ServerGroup instance = (ServerGroup) getSession().get(
					"com.xwtech.mss.pub.dao.business.ServerGroup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServerGroup instance) {
		log.debug("finding ServerGroup instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ServerGroup")
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
		log.debug("finding ServerGroup instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ServerGroup as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServergroupname(Object servergroupname) {
		return findByProperty(SERVERGROUPNAME, servergroupname);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ServerGroup instances");
		try {
			String queryString = "from ServerGroup";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServerGroup merge(ServerGroup detachedInstance) {
		log.debug("merging ServerGroup instance");
		try {
			ServerGroup result = (ServerGroup) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServerGroup instance) {
		log.debug("attaching dirty ServerGroup instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServerGroup instance) {
		log.debug("attaching clean ServerGroup instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}