package com.tmind.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.tmind.framework.pub.dao.BaseDao;
import com.tmind.mss.pub.po.Country;
import com.tmind.mss.pub.po.Province;

/**
 * A data access object (DAO) providing persistence and search support for
 * Country entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.tmind.mss.pub.dao.business.Country
 * @author MyEclipse Persistence Tools
 */
public class CountryDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(CountryDAO.class);
	// property constants
	public static final String COUNTRYNAME = "countryname";
	public static final String STATUS = "status";

	public void save(Country transientInstance) {
		log.debug("saving Country instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Country persistentInstance) {
		log.debug("deleting Country instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Country findById(java.lang.Integer id) {
		log.debug("getting Country instance with id: " + id);
		try {
			Country instance = (Country) getSession().get(
					"com.tmind.mss.pub.dao.business.Country", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Country instance) {
		log.debug("finding Country instance by example");
		try {
			List results = getSession()
					.createCriteria("com.tmind.mss.pub.dao.business.Country")
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
		log.debug("finding Country instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Country as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCountryname(Object countryname) {
		return findByProperty(COUNTRYNAME, countryname);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Country instances");
		try {
			String queryString = "from Country";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Country merge(Country detachedInstance) {
		log.debug("merging Country instance");
		try {
			Country result = (Country) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Country instance) {
		log.debug("attaching dirty Country instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Country instance) {
		log.debug("attaching clean Country instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 查询所有有效的国家信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Country> queryAllCountries(){
		StringBuffer listHql = new StringBuffer();
		listHql.append("select country from Country country where country.status='A'");
		//按物品类别和名称排序
		listHql.append(" order by country.countryid asc ");
		List<Country> list = getHibernateTemplate().find((listHql.toString()));
		return list;
	}
}