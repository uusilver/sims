package com.xwtech.mss.pub.dao.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.formBean.OperLogSearForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.OperationLog;

/**
 * Data access object (DAO) for domain model class OperationLog.
 * @see com.xwtech.mss.pub.po.OperationLog
 * @author MyEclipse - Hibernate Tools
 */
public class OperationLogDAO extends BaseDao {

    private static final Log log = LogFactory.getLog(OperationLogDAO.class);

	//property constants
	public static final String LOGIN_NAME = "loginName";
	public static final String DO_TYPE = "doType";
	public static final String DO_OBJECT = "doObject";
	public static final String OBJ_TYPE = "objType";
	public static final String TABLE_NAME = "tableName";
	public static final String DESCRIPTION = "description";
	public static final String DO_TIME = "doTime";

	protected void initDao() {
		//do nothing
	}
    
    public void save(OperationLog transientInstance) {
        log.debug("saving OperationLog instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(OperationLog persistentInstance) {
        log.debug("deleting OperationLog instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public OperationLog findById( java.lang.Long id) {
        log.debug("getting OperationLog instance with id: " + id);
        try {
            OperationLog instance = (OperationLog) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.OperationLog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(OperationLog instance) {
        log.debug("finding OperationLog instance by example");
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
      log.debug("finding OperationLog instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from OperationLog as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByLoginName(Object loginName) {
		return findByProperty(LOGIN_NAME, loginName);
	}
	
	public List findByDoType(Object doType) {
		return findByProperty(DO_TYPE, doType);
	}
	
	public List findByDoObject(Object doObject) {
		return findByProperty(DO_OBJECT, doObject);
	}
	
	public List findByObjType(Object objType) {
		return findByProperty(OBJ_TYPE, objType);
	}
	
	public List findByTableName(Object tableName) {
		return findByProperty(TABLE_NAME, tableName);
	}
	
	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}
	
	public List findByDoTime(Object doTime) {
		return findByProperty(DO_TIME, doTime);
	}
	
    public OperationLog merge(OperationLog detachedInstance) {
        log.debug("merging OperationLog instance");
        try {
            OperationLog result = (OperationLog) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(OperationLog instance) {
        log.debug("attaching dirty OperationLog instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(OperationLog instance) {
        log.debug("attaching clean OperationLog instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static OperationLogDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (OperationLogDAO) ctx.getBean("OperationLogDAO");
	}
	

/**
 *  所有的日志查询
 */	
	
	public List operLogListAll()
	{
		
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(" from OperationLog as operationLog ");
		
		stringBuffer.append(" order by operationLog.logId desc ");		
		List operLogList=this.getHibernateTemplate().find(stringBuffer.toString());
		
		return operLogList;
	}

	
	
	
	
/**
 * 日志查询
 * @author:wxy
 */	
	
	public HashMap operLogSear(OperLogSearForm operLogSearForm)
	{
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer countHSql = new StringBuffer();
		
		stringBuffer.append(" from OperationLog as operationLog where 1=1 ");
		countHSql.append("select count(operationLog.logId) from OperationLog as operationLog where 1=1 ");
		
		List list=new ArrayList();
		
		if(operLogSearForm.getLoginName()!=null&&!operLogSearForm.getLoginName().equals(""))
		{
			String loginName=operLogSearForm.getLoginName().trim();
			list.add("%"+loginName+"%");
			stringBuffer.append(" and operationLog.loginName like ? ");
			countHSql.append(" and operationLog.loginName like ? ");
		}
		
		if(operLogSearForm.getDescription()!=null&&!operLogSearForm.getDescription().equals(""))
		{
			String logDesc=operLogSearForm.getDescription().trim();
			list.add("%"+logDesc+"%");
			stringBuffer.append(" and operationLog.description like ? ");
			countHSql.append(" and operationLog.description like ? ");
		}

		if(operLogSearForm.getDoType()!=null)
		{
			Long doType=operLogSearForm.getDoType();
			list.add(doType);
			stringBuffer.append(" and operationLog.doType=? ");
			countHSql.append(" and operationLog.doType=? ");
		}

		if(operLogSearForm.getObjType()!=null)
		{
			Long objType=operLogSearForm.getObjType();
			list.add(objType);
			stringBuffer.append(" and operationLog.objType=? ");
			countHSql.append(" and operationLog.objType=? ");
		}

		stringBuffer.append(" order by operationLog.doTime desc, operationLog.objType");
		
		Object []obj=null;
		if(list.size()>0)
		{
			obj=new Object[list.size()];
			for(int i=0;i<list.size();i++)
			{
				obj[i]=list.get(i);
			}
		}

		return queryResultCount(stringBuffer.toString(),countHSql.toString(),list,operLogSearForm.getCurrentPage(),String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));
//		List operLogList=this.getHibernateTemplate().find(stringBuffer.toString(),obj);
//		return operLogList;
	}

	
	
}