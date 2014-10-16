package com.xwtech.mss.pub.dao.system;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xwtech.mss.pub.po.FrameResult;

/**
 * Data access object (DAO) for domain model class FrameResult.
 * @see com.xwtech.mss.pub.po.FrameResult
 * @author MyEclipse - Hibernate Tools
 */
public class FrameResultDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(FrameResultDAO.class);

	//property constants
	public static final String RESULT_CODE = "resultCode";
	public static final String RESULT_INFO = "resultInfo";
	public static final String RESULT_TYPE = "resultType";
	public static final String RESULT_STATE = "resultState";
	public static final String CONSTANT_NAME = "constantName";
	public static final String RESULT_DESC = "resultDesc";

	protected void initDao() {
		//do nothing
	}
    
    public void save(FrameResult transientInstance) {
        log.debug("saving FrameResult instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(FrameResult persistentInstance) {
        log.debug("deleting FrameResult instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FrameResult findById( java.lang.Long id) {
        log.debug("getting FrameResult instance with id: " + id);
        try {
            FrameResult instance = (FrameResult) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.FrameResult", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FrameResult instance) {
        log.debug("finding FrameResult instance by example");
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
      log.debug("finding FrameResult instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FrameResult as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByResultCode(Object resultCode) {
		return findByProperty(RESULT_CODE, resultCode);
	}
	
	public List findByResultInfo(Object resultInfo) {
		return findByProperty(RESULT_INFO, resultInfo);
	}
	
	public List findByResultType(Object resultType) {
		return findByProperty(RESULT_TYPE, resultType);
	}
	
	public List findByResultState(Object resultState) {
		return findByProperty(RESULT_STATE, resultState);
	}
	
	public List findByConstantName(Object constantName) {
		return findByProperty(CONSTANT_NAME, constantName);
	}
	
	public List findByResultDesc(Object resultDesc) {
		return findByProperty(RESULT_DESC, resultDesc);
	}
	
    public FrameResult merge(FrameResult detachedInstance) {
        log.debug("merging FrameResult instance");
        try {
            FrameResult result = (FrameResult) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FrameResult instance) {
        log.debug("attaching dirty FrameResult instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FrameResult instance) {
        log.debug("attaching clean FrameResult instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static FrameResultDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (FrameResultDAO) ctx.getBean("FrameResultDAO");
	}
}