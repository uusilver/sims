package com.xwtech.mss.pub.dao.system;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.po.FrameOrgCityRelation;

/**
 * Data access object (DAO) for domain model class FrameOrgCityRelation.
 * @see com.xwtech.mss.pub.po.FrameOrgCityRelation
 * @author MyEclipse - Hibernate Tools
 */
public class FrameOrgCityRelationDAO extends BaseDao {

    private static final Log log = LogFactory.getLog(FrameOrgCityRelationDAO.class);

	//property constants
	public static final String CITY_ID = "cityId";

	protected void initDao() {
		//do nothing
	}
    
    public void save(FrameOrgCityRelation transientInstance) {
        log.debug("saving FrameOrgCityRelation instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
   
	public void deleteAll(Collection collection) {
        log.debug("deleting FrameOrgCityRelation instance By Collection");
        try {
            getHibernateTemplate().deleteAll(collection);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
	
	public void delete(FrameOrgCityRelation persistentInstance) {
        log.debug("deleting FrameOrgCityRelation instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FrameOrgCityRelation findById( java.lang.Long id) {
        log.debug("getting FrameOrgCityRelation instance with id: " + id);
        try {
            FrameOrgCityRelation instance = (FrameOrgCityRelation) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.FrameOrgCityRelation", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FrameOrgCityRelation instance) {
        log.debug("finding FrameOrgCityRelation instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding FrameOrgCityRelation instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FrameOrgCityRelation as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCityId(Object cityId) {
		return findByProperty(CITY_ID, cityId);
	}
	
    public FrameOrgCityRelation merge(FrameOrgCityRelation detachedInstance) {
        log.debug("merging FrameOrgCityRelation instance");
        try {
            FrameOrgCityRelation result = (FrameOrgCityRelation) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FrameOrgCityRelation instance) {
        log.debug("attaching dirty FrameOrgCityRelation instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FrameOrgCityRelation instance) {
        log.debug("attaching clean FrameOrgCityRelation instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static FrameOrgCityRelationDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (FrameOrgCityRelationDAO) ctx.getBean("FrameOrgCityRelationDAO");
	}
}