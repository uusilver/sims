package com.xwtech.mss.pub.dao.system;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xwtech.mss.pub.po.FrameFieldCheck;

/**
 * Data access object (DAO) for domain model class MssFrameFieldCheck.
 * @see com.xwtech.mss.pub.po.MssFrameFieldCheck
 * @author MyEclipse - Hibernate Tools
 */
public class FrameFieldCheckDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(FrameFieldCheckDAO.class);

	//property constants
	public static final String TABLE_NAME = "tableName";
	public static final String FIELD_NAME = "fieldName";
	public static final String CHECK_VALUE = "checkValue";
	public static final String CHECK_DESC = "checkDesc";
	public static final String CONSTANT_NAME = "constantName";
	public static final String CHECK_STATE = "checkState";

	protected void initDao() {
		//do nothing
	}
    
    public void save(FrameFieldCheck transientInstance) {
        log.debug("saving FrameFieldCheck instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(FrameFieldCheck persistentInstance) {
        log.debug("deleting FrameFieldCheck instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FrameFieldCheck findById( java.lang.Long id) {
        log.debug("getting FrameFieldCheck instance with id: " + id);
        try {
            FrameFieldCheck instance = (FrameFieldCheck) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.FrameFieldCheck", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FrameFieldCheck instance) {
        log.debug("finding FrameFieldCheck instance by example");
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
      log.debug("finding FrameFieldCheck instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FrameFieldCheck as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTableName(Object tableName) {
		return findByProperty(TABLE_NAME, tableName);
	}
	
	public List findByFieldName(Object fieldName) {
		return findByProperty(FIELD_NAME, fieldName);
	}
	
	public List findByCheckValue(Object checkValue) {
		return findByProperty(CHECK_VALUE, checkValue);
	}
	
	public List findByCheckDesc(Object checkDesc) {
		return findByProperty(CHECK_DESC, checkDesc);
	}
	
	public List findByConstantName(Object constantName) {
		return findByProperty(CONSTANT_NAME, constantName);
	}
	
	public List findByCheckState(Object checkState) {
		return findByProperty(CHECK_STATE, checkState);
	}
	
    public FrameFieldCheck merge(FrameFieldCheck detachedInstance) {
        log.debug("merging FrameFieldCheck instance");
        try {
            FrameFieldCheck result = (FrameFieldCheck) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FrameFieldCheck instance) {
        log.debug("attaching dirty FrameFieldCheck instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FrameFieldCheck instance) {
        log.debug("attaching clean FrameFieldCheck instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static FrameFieldCheckDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (FrameFieldCheckDAO) ctx.getBean("FrameFieldCheckDAO");
	}
}