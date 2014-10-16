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
import com.xwtech.mss.pub.po.MpmsBoardMsg;



/**
 * Data access object (DAO) for domain model class MpmsBoardMsg.
 * @see com.xwtech.dms.pub.po.MpmsBoardMsg
 * @author MyEclipse - Hibernate Tools
 */
public class MpmsBoardMsgDAO extends BaseDao {

    private static final Log log = LogFactory.getLog(MpmsBoardMsgDAO.class);

	//property constants
	public static final String CREATE_TIME = "createTime";
	public static final String MSG_INFO = "msgInfo";
	public static final String STATUS = "status";

    
    public void save(MpmsBoardMsg transientInstance) {
        log.debug("saving MpmsBoardMsg instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MpmsBoardMsg persistentInstance) {
        log.debug("deleting MpmsBoardMsg instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MpmsBoardMsg findById( java.lang.Long id) {
        log.debug("getting MpmsBoardMsg instance with id: " + id);
        try {
            MpmsBoardMsg instance = (MpmsBoardMsg) getSession()
                    .get("com.xwtech.mss.pub.po.MpmsBoardMsg", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MpmsBoardMsg instance) {
        log.debug("finding MpmsBoardMsg instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.xwtech.mss.pub.po.MpmsBoardMsg")
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
      log.debug("finding MpmsBoardMsg instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MpmsBoardMsg as model where model." 
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
	
	public List findByMsgInfo(Object msgInfo) {
		return findByProperty(MSG_INFO, msgInfo);
	}
	
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}
	
    public MpmsBoardMsg merge(MpmsBoardMsg detachedInstance) {
        log.debug("merging MpmsBoardMsg instance");
        try {
            MpmsBoardMsg result = (MpmsBoardMsg) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MpmsBoardMsg instance) {
        log.debug("attaching dirty MpmsBoardMsg instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MpmsBoardMsg instance) {
        log.debug("attaching clean MpmsBoardMsg instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    
	/**
	 * 根据指定条件查询聊天记录
	 */
	public HashMap queryChatList(ChatSearchForm searchForm)
	{
		
		//查询列表sql
		StringBuffer listHSql = new StringBuffer(" select boardMsg from MpmsBoardMsg boardMsg ");
		//查询记录数sql
		StringBuffer countHSql = new StringBuffer(" select count(boardMsg.msgId) from MpmsBoardMsg as boardMsg ");
		//过滤条件
		StringBuffer filterSql = new StringBuffer(" where 1=1 ");		

		List paramList = new ArrayList();
		if(searchForm.getCreateTime1()!=null && !searchForm.getCreateTime2().equals("")){
//			filterSql.append(" and substr(boardMsg.createTime,0,8)>='" + searchForm.getCreateTime1() + "' ");
			filterSql.append(" and substr(boardMsg.createTime,1,8)>='" + searchForm.getCreateTime1() + "' ");
		}
		
		if(searchForm.getCreateTime2()!=null && !searchForm.getCreateTime2().equals("")){
//			filterSql.append(" and substr(boardMsg.createTime,0,8)<='" + searchForm.getCreateTime2() + "' ");
			filterSql.append(" and substr(boardMsg.createTime,1,8)<='" + searchForm.getCreateTime2() + "' ");
		}

		if(searchForm.getMsgInfo()!=null && !searchForm.getMsgInfo().equals("")){
			filterSql.append(" and boardMsg.msgInfo=?");
			paramList.add((Object)("%"+searchForm.getMsgInfo()+ "%"));
		}	
		listHSql.append(filterSql + ") order by boardMsg.createTime desc ");
		countHSql.append( filterSql);
		
		HashMap map = queryResultCount(listHSql.toString(),countHSql.toString(),paramList,searchForm.getCurrentPage(),String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));
		return map;
		
	}
	
	
	

    /*
     * 按照查询条件进行留言信息查询
     * @param chatSearchForm 查询条件组合
     * @param CUserInfo 当前登陆用户信息
     * @return List 
     */
    public HashMap chatSearchList(ChatSearchForm searchForm)
    {
        StringBuffer hql = new StringBuffer();       
                           
        hql.append(" select boardMsg from MpmsBoardMsg boardMsg ");  
        
        //查询记录数
		StringBuffer countHSql =new StringBuffer(" select count(boardMsg.msgId) from MpmsBoardMsg as boardMsg ");

		List paramList = new ArrayList();
		
		//过滤条件
		StringBuffer filterSql = new StringBuffer(" where 1=1 ");
		
  
        
		if(searchForm.getCreateTime1()!=null && !searchForm.getCreateTime1().equals("")){
//			filterSql.append(" and substr(boardMsg.createTime,0,8)>=" + searchForm.getCreateTime1() + " ");			
			filterSql.append(" and substr(boardMsg.createTime,1,8)>=" + searchForm.getCreateTime1() + " ");
		}
		
		if(searchForm.getCreateTime2()!=null && !searchForm.getCreateTime2().equals("")){
//			filterSql.append(" and substr(boardMsg.createTime,0,8)<=" + searchForm.getCreateTime2() + " ");
			filterSql.append(" and substr(boardMsg.createTime,1,8)<=" + searchForm.getCreateTime2() + " ");
					
		}

		if(searchForm.getMsgInfo()!=null && !searchForm.getMsgInfo().equals("")){
			
			filterSql.append(" and boardMsg.msgInfo like ? ");  
			
			paramList.add((Object)("%"+searchForm.getMsgInfo()+ "%"));
		}	
       
        
        hql.append(filterSql + " order by boardMsg.createTime desc");
        
        countHSql.append(filterSql);
        
        
        HashMap map = queryResultCount(hql.toString(),countHSql.toString(),paramList,searchForm.getCurrentPage(),String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));
		return map;
    }

	public void delById(Long id) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from mpms_board_msg where msg_id ="+id);
	
		executeCommonSql(sql.toString());
	}

}