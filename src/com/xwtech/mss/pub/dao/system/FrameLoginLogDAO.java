package com.xwtech.mss.pub.dao.system;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xwtech.mss.pub.po.FrameLoginLog;

/**
 * Data access object (DAO) for domain model class MssFrameLoginLog.
 * @see com.xwtech.mss.pub.po.MssFrameLoginLog
 * @author MyEclipse - Hibernate Tools
 */
public class FrameLoginLogDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(FrameLoginLogDAO.class);

	//property constants
	public static final String LOGIN_IP = "loginIp";
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_PWD = "loginPwd";
	public static final String LOGIN_TIME = "loginTime";
	public static final String LOGIN_RESULT_CODE = "loginResultCode";
	public static final String LOGOUT_TIME = "logoutTime";

	protected void initDao() {
		//do nothing
	}
    
    public void save(FrameLoginLog transientInstance) {
        log.debug("saving FrameLoginLog instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(FrameLoginLog persistentInstance) {
        log.debug("deleting FrameLoginLog instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FrameLoginLog findById( java.lang.Long id) {
        log.debug("getting FrameLoginLog instance with id: " + id);
        try {
            FrameLoginLog instance = (FrameLoginLog) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.FrameLoginLog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FrameLoginLog instance) {
        log.debug("finding FrameLoginLog instance by example");
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
      log.debug("finding FrameLoginLog instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FrameLoginLog as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByLoginIp(Object loginIp) {
		return findByProperty(LOGIN_IP, loginIp);
	}
	
	public List findByLoginName(Object loginName) {
		return findByProperty(LOGIN_NAME, loginName);
	}
	
	public List findByLoginPwd(Object loginPwd) {
		return findByProperty(LOGIN_PWD, loginPwd);
	}
	
	public List findByLoginTime(Object loginTime) {
		return findByProperty(LOGIN_TIME, loginTime);
	}
	
	public List findByLoginResultCode(Object loginResultCode) {
		return findByProperty(LOGIN_RESULT_CODE, loginResultCode);
	}
	
	public List findByLogoutTime(Object logoutTime) {
		return findByProperty(LOGOUT_TIME, logoutTime);
	}
	
    public FrameLoginLog merge(FrameLoginLog detachedInstance) {
        log.debug("merging FrameLoginLog instance");
        try {
            FrameLoginLog result = (FrameLoginLog) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FrameLoginLog instance) {
        log.debug("attaching dirty FrameLoginLog instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FrameLoginLog instance) {
        log.debug("attaching clean FrameLoginLog instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static FrameLoginLogDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (FrameLoginLogDAO) ctx.getBean("FrameLoginLogDAO");
	}
}