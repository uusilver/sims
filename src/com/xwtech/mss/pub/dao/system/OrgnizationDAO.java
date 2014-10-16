package com.xwtech.mss.pub.dao.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.formBean.OrgnizationSearchForm;
import com.xwtech.mss.pub.po.Orgnization;

/**
 * Data access object (DAO) for domain model class FrameOrgnization.
 * @see com.xwtech.mss.pub.dao.system.FrameOrgnization
 * @author MyEclipse - Hibernate Tools
 */
public class OrgnizationDAO extends BaseDao {

    private static final Log log = LogFactory.getLog(OrgnizationDAO.class);

	//property constants
	public static final String ORG_NAME = "orgName";
	public static final String DESCRIPTION = "description";
	public static final String ORG_LEVEL = "orgLevel";
	public static final String STATE = "state";

	protected void initDao() {
		//do nothing
	}
    
    public void save(Orgnization transientInstance) {
        log.debug("saving Orgnization instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Orgnization persistentInstance) {
        log.debug("deleting Orgnization instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Orgnization findById( java.lang.Long id) {
        log.debug("getting Orgnization instance with id: " + id);
        try {
            Orgnization instance = (Orgnization) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.Orgnization", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Orgnization instance) {
        log.debug("finding Orgnization instance by example");
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
      log.debug("finding Orgnization instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Orgnization as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByOrgName(Object orgName) {
		return findByProperty(ORG_NAME, orgName);
	}
	
	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}
	
	public List findByOrgLevel(Object orgLevel) {
		return findByProperty(ORG_LEVEL, orgLevel);
	}
	
	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}
	
    public Orgnization merge(Orgnization detachedInstance) {
        log.debug("merging Orgnization instance");
        try {
            Orgnization result = (Orgnization) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Orgnization instance) {
        log.debug("attaching dirty Orgnization instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Orgnization instance) {
        log.debug("attaching clean Orgnization instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static OrgnizationDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (OrgnizationDAO) ctx.getBean("OrgnizationDAO");
	}
	
	
	/**
	 * tigger:BO-getOrgnizationListByForm
	 * operation:根据form的条件返回组织信息查询结果
	 * @param OrgnizationSearchForm
	 * @param currentPage
	 * @param countPerPage
	 * @return
	 */
	public Map getOrgnizationListByForm(OrgnizationSearchForm orgnizationSearchForm, String currentPage, String countPerPage)throws Exception
	{
		//组织名称模糊查询条件
		String fuzzyOrgnizationName = orgnizationSearchForm.getFuzzyOrgnizationName();
		
		//查询组织信息的Hql
		String strHql = "from Orgnization as org where 1=1";
		
		//查询参数
		List listParamers = new ArrayList();
		
		//添加组织名称模糊查询条件
		if(!"".equals(fuzzyOrgnizationName))
		{
			listParamers.add("%"+fuzzyOrgnizationName+"%");
			strHql += " and org.orgName like ?";
		}
		
		//查询该部门名称记录
		if(!"".equals(orgnizationSearchForm.getOrgNameForSelect()))
		{
			strHql += " and org.orgName ='"+orgnizationSearchForm.getOrgNameForSelect()+"'";
		}
		
		//部门id条件(结果集过滤该部门id)
		if(!"".equals(orgnizationSearchForm.getNotExistOrgId()))
		{
			strHql += " and org.orgId <> "+orgnizationSearchForm.getNotExistOrgId();
		}
		
		//仅查询有效记录
		strHql += " and org.state = 'A'";
		
		if(orgnizationSearchForm.getIsPaging().equals("Y"))//分页
		{
			//查询组织信息记录数的Hql
			String strCountHql = "select count(*) " + strHql.substring(strHql.indexOf("from"));
			
			return queryResultCount(String.valueOf(strHql), strCountHql, listParamers, currentPage, countPerPage);
		}
		else //不分页
		{
			Map map = new HashMap();
			Object[] objParamers = new Object[listParamers.size()];
			for(int i = 0;i<listParamers.size();i++)
			{
				objParamers[i] = listParamers.get(i);
			}
			List listResult = getHibernateTemplate().find(strHql,objParamers);
			map.put("orgList", listResult);
			return map;
		}

	}
}