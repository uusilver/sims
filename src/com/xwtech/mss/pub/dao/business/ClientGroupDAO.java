package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.ClientGroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ClientGroup
 * @author MyEclipse Persistence Tools
 */
public class ClientGroupDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ClientGroupDAO.class);
	// property constants
	public static final String CLIENTGROUPNAME = "clientgroupname";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void save(ClientGroup transientInstance) {
		log.debug("saving ClientGroup instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientGroup persistentInstance) {
		log.debug("deleting ClientGroup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientGroup findById(java.lang.Integer id) {
		log.debug("getting ClientGroup instance with id: " + id);
		try {
			ClientGroup instance = (ClientGroup) getSession().get(
					"com.xwtech.mss.pub.dao.business.ClientGroup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientGroup instance) {
		log.debug("finding ClientGroup instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ClientGroup")
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
		log.debug("finding ClientGroup instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ClientGroup as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClientgroupname(Object clientgroupname) {
		return findByProperty(CLIENTGROUPNAME, clientgroupname);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ClientGroup instances");
		try {
			String queryString = "from ClientGroup";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientGroup merge(ClientGroup detachedInstance) {
		log.debug("merging ClientGroup instance");
		try {
			ClientGroup result = (ClientGroup) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientGroup instance) {
		log.debug("attaching dirty ClientGroup instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientGroup instance) {
		log.debug("attaching clean ClientGroup instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}