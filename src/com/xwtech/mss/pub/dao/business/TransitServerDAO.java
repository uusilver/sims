package com.xwtech.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.TransitServer;

/**
 * A data access object (DAO) providing persistence and search support for
 * TransitServer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.TransitServer
 * @author MyEclipse Persistence Tools
 */
public class TransitServerDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TransitServerDAO.class);
	// property constants
	public static final String SERVERIP = "serverip";
	public static final String COUNTRYID = "countryid";
	public static final String PROVINCEID = "provinceid";
	public static final String CITYID = "cityid";
	public static final String SERVERTYPE = "servertype";
	public static final String SERVERSTATUS = "serverstatus";
	public static final String LIMITATION = "limitation";
	public static final String REGIONID = "regionid";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void save(TransitServer transientInstance) {
		log.debug("saving TransitServer instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TransitServer persistentInstance) {
		log.debug("deleting TransitServer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TransitServer findById(java.lang.Integer id) {
		log.debug("getting TransitServer instance with id: " + id);
		try {
			TransitServer instance = (TransitServer) getSession().get(
					"com.xwtech.mss.pub.dao.business.TransitServer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TransitServer instance) {
		log.debug("finding TransitServer instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.TransitServer")
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
		log.debug("finding TransitServer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TransitServer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServerip(Object serverip) {
		return findByProperty(SERVERIP, serverip);
	}

	public List findByCountryid(Object countryid) {
		return findByProperty(COUNTRYID, countryid);
	}

	public List findByProvinceid(Object provinceid) {
		return findByProperty(PROVINCEID, provinceid);
	}

	public List findByCityid(Object cityid) {
		return findByProperty(CITYID, cityid);
	}

	public List findByServertype(Object servertype) {
		return findByProperty(SERVERTYPE, servertype);
	}

	public List findByServerstatus(Object serverstatus) {
		return findByProperty(SERVERSTATUS, serverstatus);
	}

	public List findByLimitation(Object limitation) {
		return findByProperty(LIMITATION, limitation);
	}

	public List findByRegionid(Object regionid) {
		return findByProperty(REGIONID, regionid);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all TransitServer instances");
		try {
			String queryString = "from TransitServer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TransitServer merge(TransitServer detachedInstance) {
		log.debug("merging TransitServer instance");
		try {
			TransitServer result = (TransitServer) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TransitServer instance) {
		log.debug("attaching dirty TransitServer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TransitServer instance) {
		log.debug("attaching clean TransitServer instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}