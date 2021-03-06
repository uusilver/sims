package com.tmind.mss.pub.dao.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.tmind.framework.pub.dao.BaseDao;
import com.tmind.mss.pub.po.City;

/**
 * A data access object (DAO) providing persistence and search support for City
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.tmind.mss.pub.dao.business.City
 * @author MyEclipse Persistence Tools
 */
public class CityDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(CityDAO.class);
	// property constants
	public static final String CITYNAME = "cityname";
	public static final String PROVINCEID = "provinceid";
	public static final String STATUS = "status";

	public void save(City transientInstance) {
		log.debug("saving City instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(City persistentInstance) {
		log.debug("deleting City instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public City findById(java.lang.Integer id) {
		log.debug("getting City instance with id: " + id);
		try {
			City instance = (City) getSession().get(
					"com.tmind.mss.pub.dao.business.City", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(City instance) {
		log.debug("finding City instance by example");
		try {
			List results = getSession()
					.createCriteria("com.tmind.mss.pub.dao.business.City")
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
		log.debug("finding City instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from City as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCityname(Object cityname) {
		return findByProperty(CITYNAME, cityname);
	}

	public List findByProvinceid(Object provinceid) {
		return findByProperty(PROVINCEID, provinceid);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all City instances");
		try {
			String queryString = "from City";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public City merge(City detachedInstance) {
		log.debug("merging City instance");
		try {
			City result = (City) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(City instance) {
		log.debug("attaching dirty City instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(City instance) {
		log.debug("attaching clean City instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 查询所有有效的城市信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<City> queryAllCitys(){
		StringBuffer listHql = new StringBuffer();
		listHql.append("select city from City city where city.status='A'");
		//按物品类别和名称排序
		listHql.append(" order by city.provinceid ,city.cityid asc ");
		List<City> list = getHibernateTemplate().find((listHql.toString()));
		return list;
	}
	
	/**
	 * 查询所有有效的城市信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<City> queryCityByProvinceId(String provinceId){
		Integer[] paramList = new Integer[1];
		StringBuffer listHql = new StringBuffer();
		listHql.append("select city from City city where city.status='A' AND city.provinceid=?");
		if(provinceId==null||"".equals(provinceId)){
			return null;
		}
		paramList[0] = Integer.valueOf(provinceId);
		//按物品类别和名称排序
		listHql.append(" order by city.provinceid ,city.cityid asc ");
		List<City> list = getHibernateTemplate().find((listHql.toString()),paramList);
		return list;
	}
	
}