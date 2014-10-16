package com.xwtech.mss.pub.dao.system;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xwtech.mss.pub.po.FrameUrl;

/**
 * Data access object (DAO) for domain model class FrameUrl.
 * @see com.xwtech.mss.pub.po.FrameUrl
 * @author MyEclipse - Hibernate Tools
 */
public class FrameUrlDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(FrameUrlDAO.class);

	//property constants
	public static final String URL_VALUE = "urlValue";
	public static final String URL_NAME = "urlName";
	public static final String URL_DESC = "urlDesc";
	public static final String URL_STATE = "urlState";

	protected void initDao() {
		//do nothing
	}
    
    public void save(FrameUrl transientInstance) {
        log.debug("saving FrameUrl instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(FrameUrl persistentInstance) {
        log.debug("deleting FrameUrl instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FrameUrl findById( java.lang.Long id) {
        log.debug("getting FrameUrl instance with id: " + id);
        try {
            FrameUrl instance = (FrameUrl) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.FrameUrl", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FrameUrl instance) {
        log.debug("finding FrameUrl instance by example");
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
      log.debug("finding FrameUrl instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FrameUrl as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByUrlValue(Object urlValue) {
		return findByProperty(URL_VALUE, urlValue);
	}
	
	public List findByUrlName(Object urlName) {
		return findByProperty(URL_NAME, urlName);
	}
	
	public List findByUrlDesc(Object urlDesc) {
		return findByProperty(URL_DESC, urlDesc);
	}
	
	public List findByUrlState(Object urlState) {
		return findByProperty(URL_STATE, urlState);
	}
	
    public FrameUrl merge(FrameUrl detachedInstance) {
        log.debug("merging FrameUrl instance");
        try {
            FrameUrl result = (FrameUrl) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FrameUrl instance) {
        log.debug("attaching dirty FrameUrl instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FrameUrl instance) {
        log.debug("attaching clean FrameUrl instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static FrameUrlDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (FrameUrlDAO) ctx.getBean("FrameUrlDAO");
	}
}