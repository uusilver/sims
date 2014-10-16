package com.xwtech.mss.pub.dao.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.mss.formBean.UserManageSearchForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.UserInfo;

/**
 * Data access object (DAO) for domain model class UserInfo.
 * @see com.xwtech.mss.pub.dao.system.UserInfo
 * @author MyEclipse - Hibernate Tools
 */
public class UserInfoDAO extends BaseDao {

    private static final Log log = LogFactory.getLog(UserInfoDAO.class);

	//property constants
	public static final String USER_NUM = "userNum";
	public static final String USER_NAME = "userName";
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_PWD = "loginPwd";
	public static final String USER_TYPE = "userType";
	public static final String FAX = "fax";
	public static final String USER_STATE = "userState";

	protected void initDao() {
		//do nothing
	}
    
    public UserInfo save(UserInfo transientInstance) {
        log.debug("saving UserInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
        return transientInstance;
    }
    
	public void delete(UserInfo persistentInstance) {
        log.debug("deleting UserInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public UserInfo findById( java.lang.Long id) {
        log.debug("getting UserInfo instance with id: " + id);
        try {
            UserInfo instance = (UserInfo) getHibernateTemplate()
                    .get("com.xwtech.mss.pub.po.UserInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UserInfo instance) {
        log.debug("finding UserInfo instance by example");
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
      log.debug("finding UserInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserInfo as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByUserNum(Object userNum) {
		return findByProperty(USER_NUM, userNum);
	}
	
	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}
	
	public List findByLoginName(Object loginName) {
		return findByProperty(LOGIN_NAME, loginName);
	}
	
	public List findByLoginPwd(Object loginPwd) {
		return findByProperty(LOGIN_PWD, loginPwd);
	}
	
	public List findByUserType(Object userType) {
		return findByProperty(USER_TYPE, userType);
	}
	
	public List findByFax(Object fax) {
		return findByProperty(FAX, fax);
	}
	
	public List findByUserState(Object userState) {
		return findByProperty(USER_STATE, userState);
	}
	
    public UserInfo merge(UserInfo detachedInstance) {
        log.debug("merging UserInfo instance");
        try {
            UserInfo result = (UserInfo) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserInfo instance) {
        log.debug("attaching dirty UserInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UserInfo instance) {
        log.debug("attaching clean UserInfo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static UserInfoDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (UserInfoDAO) ctx.getBean("UserInfoDAO");
	}
	
	/**
     * 根据用户名、密码进行系统登陆
     * @param loginName String
     * @param loginPwd String
     * @param role String
     * @return SysUser
     */
    public UserInfo queryFrameLoginByLoginNameAndLoginPwd(String loginName,String loginPwd) {
    	UserInfo sysUser = null;
        String hql = "select sysUser from UserInfo sysUser"
                     + " where sysUser.loginName = ? and sysUser.loginPwd = ? and sysUser.userState='A' ";
        Object[] params = new Object[2];
        params[0] = loginName;
        params[1] = loginPwd;
        List l = getHibernateTemplate().find(hql, params);
        if (l.isEmpty()) {
            logger.warn("not find sysUser, loginName=" + loginName +
                        " ,loginPwd=" + loginPwd);
        } else {
            if (l.size() > 1) {
                logger.warn("not only sysUser, loginName=" + loginName +
                            " ,loginPwd=" + loginPwd);
            } else {
                sysUser = (UserInfo) l.get(0);
            }
        }
        return sysUser;
    }

    /**
     * 根据用户id获取用户权限
     * @param userId Long
     * @return UserProperty
     */
    public List querySysUserRoleRightsByUserId(Long userId){
        String hql ="select distinct(userProperty.role) from UserProperty userProperty,Role role,UserInfo userInfo "
        			+ " where userProperty.role.roleId = role.roleId "
        			+ " and userInfo.role.roleId = role.roleId "
        			+ " and userInfo.userId=? " 
        			+ " and userInfo.userState = 'A'"
        			+ " and role.state = 'A'"
                    +" and userProperty.state='A'";
        return getHibernateTemplate().find(hql, userId);
    }
    
	/**
     * 根据用户id获取用户相关信息
     * @param userLoginId Long
     * @return SysUser
     */
    public UserInfo querySysUserByUserId(Long userId) {
    	UserInfo sysUser = null;
        String hql ="from UserInfo sysUser where sysUser.userId=? ";
        List list = getHibernateTemplate().find(hql, userId);
        if (list.isEmpty()) {
            logger.warn("DiyUser object is null");
        }
        if (list.size() > 1) {
            logger.warn("not only DiyUser, loginId=" + userId);
        }
        if (list.size() > 0) {
            sysUser = (UserInfo) list.get(0);
        }
        return sysUser;
    }
    
	/**
	 * 根据指定条件查询用户列表
	 */
	public HashMap queryUserList(UserManageSearchForm searchForm)
	{
		//查询列表sql
		StringBuffer listHSql = new StringBuffer(" select user from UserInfo as user ");
		//查询记录数sql
		StringBuffer countHSql = new StringBuffer(" select count(user.userId) from UserInfo as user ");
		//过滤条件
		StringBuffer filterSql = new StringBuffer(" where 1=1 ");		
		List paramList = new ArrayList();
		
		if(searchForm.getUserNum()!=null && !searchForm.getUserNum().equals("")){
			filterSql.append(" and user.userNum=?");
			paramList.add((Object)(new Long(searchForm.getUserNum())));
		}
		if(searchForm.getUserName()!=null && !searchForm.getUserName().equals("")){
			filterSql.append(" and user.userName like ?");
			paramList.add((Object)("%" + searchForm.getUserName() + "%"));
		}
		if(searchForm.getUserDept()!=null && !searchForm.getUserDept().equals("")){
			filterSql.append(" and user.orgnization.orgId=?");
			paramList.add((Object)(new Long(searchForm.getUserDept())));
		}
		if(searchForm.getUserRole()!=null && !searchForm.getUserRole().equals("")){
			filterSql.append(" and user.role.roleId=?");
			paramList.add((Object)(new Long(searchForm.getUserRole())));
		}
		if(searchForm.getIndexNO()!=null && !searchForm.getIndexNO().equals("")){
			filterSql.append(" and user.userState='A' ");
		}
		if(!StringUtils.isEmpty(searchForm.getUserState())){
			filterSql.append(" and user.userState=?");
			paramList.add((Object)searchForm.getUserState());
		}
		listHSql.append(filterSql + " order by user.userId ");
		countHSql.append(filterSql);
		
		HashMap map = queryResultCount(listHSql.toString(),countHSql.toString(),paramList,searchForm.getCurrentPage(),String.valueOf(MssConstants.COUNT_FOR_EVERY_PAGE));
		return map;
		
	}


	/**
	 * 根据Id删除记录
	 * 
	 * @param idStr,以‘,’隔开
	 */
	public void delRecords(String idStr) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE frame_user_info SET user_state = 'U' where user_id in (");
		sql.append(idStr.lastIndexOf(",") > 0 ? (idStr.substring(0, idStr
				.lastIndexOf(","))) : idStr.trim());
		sql.append(" ) ");
		
		executeCommonSql(sql.toString());
	}
	
	/**
	 * 校验登录名或是工号是否存在
	 */
	public List checkNoOrNameExist(String userNum, String loginName, String userId)
	{
		StringBuffer sql=new StringBuffer();
		List paramList = new ArrayList();
		sql.append("select count(user.userId) from UserInfo as user where user.userState='A' ");
		if(userNum!=null && !userNum.equals("")){
			sql.append(" and user.userNum=? ");
			paramList.add((Object)new Long(userNum));
		}
		if(loginName!=null && !loginName.equals("")){
			sql.append(" and user.loginName=? ");
			paramList.add((Object)loginName);
		}
		if(userId!=null && !userId.equals("")){
			sql.append(" and user.userId!=? ");
			paramList.add((Object)new Long(userId));
		}
		Object[] obj;
		if(paramList!=null && paramList.size() > 0){
			obj = new Object[paramList.size()];
			for(int i=0;i<paramList.size();i++){
				obj[i] = (Object)paramList.get(i);
			}
		}else{
			obj = null;
		}
		
		return getHibernateTemplate().find(sql.toString(),obj);
	}

	/**
	 * 校验需要关联的userId是否存在
	 */
	public List checkUserIdExist(String userId)
	{
		
		StringBuffer sql=new StringBuffer();
		List paramList = new ArrayList();
		sql.append("select count(user.userId) from UserInfo as user where 1=1 ");
		if(userId!=null && !userId.equals("")){
			sql.append(" and user.userId =? ");
			paramList.add((Object)new Long(userId));
		}
		Object[] params = null;
		if(paramList!=null){
			params = paramList.toArray();
		}
		
		return getHibernateTemplate().find(sql.toString(),params);
	}
	

	/**
	 * 根据部门查找指定用户列表
	 * @author:gu_daping
	 */
	public List getUserByDept(String deptId)
	{
		String sql = "select user from UserInfo as user where user.orgnization.orgId =" + deptId + " and user.userState='A' ";
		
		return getHibernateTemplate().find(sql,"");
	}

}