package com.tmind.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.tmind.framework.pub.dao.BaseDao;
import com.tmind.mss.pub.po.ClientGateway;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientGateway entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.tmind.mss.pub.dao.business.ClientGateway
 * @author MyEclipse Persistence Tools
 */
public class ClientGatewayDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ClientGatewayDAO.class);
	// property constants
	public static final String CLIENTID = "clientid";
	public static final String GATEWAYID = "gatewayid";

	public void save(ClientGateway transientInstance) {
		log.debug("saving ClientGateway instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientGateway persistentInstance) {
		log.debug("deleting ClientGateway instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientGateway findById(java.lang.Integer id) {
		log.debug("getting ClientGateway instance with id: " + id);
		try {
			ClientGateway instance = (ClientGateway) getSession().get(
					"com.tmind.mss.pub.dao.business.ClientGateway", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientGateway instance) {
		log.debug("finding ClientGateway instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.tmind.mss.pub.dao.business.ClientGateway")
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
		log.debug("finding ClientGateway instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClientGateway as model where model."
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

	public List findByGatewayid(Object gatewayid) {
		return findByProperty(GATEWAYID, gatewayid);
	}

	public List findAll() {
		log.debug("finding all ClientGateway instances");
		try {
			String queryString = "from ClientGateway";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientGateway merge(ClientGateway detachedInstance) {
		log.debug("merging ClientGateway instance");
		try {
			ClientGateway result = (ClientGateway) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientGateway instance) {
		log.debug("attaching dirty ClientGateway instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientGateway instance) {
		log.debug("attaching clean ClientGateway instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}