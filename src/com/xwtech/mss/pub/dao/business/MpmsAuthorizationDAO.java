package com.xwtech.mss.pub.dao.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.formBean.ChatSearchForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.MpmsAuthorization;



/**
 * Data access object (DAO) for domain model class MpmsBoardMsg.
 * @see com.xwtech.dms.pub.po.MpmsBoardMsg
 * @author MyEclipse - Hibernate Tools
 */
public class MpmsAuthorizationDAO extends BaseDao {

    private static final Log log = LogFactory.getLog(MpmsAuthorizationDAO.class);

	//property constants
	public static final String CREATE_TIME = "createTime";
	public static final String UPDATE_TIME = "updateTime";
	public static final String PASSWORD = "password";
	public static final String STATUS = "status";

    
    public void save(MpmsAuthorization transientInstance) {
        log.debug("saving MpmsAuthorization instance");
        try {
            getSession().saveOrUpdate(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MpmsAuthorization persistentInstance) {
        log.debug("deleting MpmsAuthorization instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MpmsAuthorization findById( java.lang.Long id) {
        log.debug("getting MpmsAuthorization instance with id: " + id);
        try {
            MpmsAuthorization instance = (MpmsAuthorization) getSession()
                    .get("com.xwtech.mss.pub.po.MpmsAuthorization", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MpmsAuthorization instance) {
        log.debug("finding MpmsAuthorization instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.xwtech.mss.pub.po.MpmsAuthorization")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding MpmsAuthorization instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MpmsAuthorization as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}
	
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}
	
    public MpmsAuthorization merge(MpmsAuthorization detachedInstance) {
        log.debug("merging MpmsAuthorization instance");
        try {
            MpmsAuthorization result = (MpmsAuthorization) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MpmsAuthorization instance) {
        log.debug("attaching dirty MpmsAuthorization instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MpmsAuthorization instance) {
        log.debug("attaching clean MpmsAuthorization instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public MpmsAuthorization findOnlyOne() {
		StringBuffer hql = new StringBuffer();
		MpmsAuthorization mpmsAuthorization = null;
		hql.append(" select mpmsAuthorization from MpmsAuthorization mpmsAuthorization ");
		List list = getHibernateTemplate().find(hql.toString());
		if(!list.isEmpty()){
			mpmsAuthorization = (MpmsAuthorization)list.get(0);
		}
		return mpmsAuthorization;
    }

}