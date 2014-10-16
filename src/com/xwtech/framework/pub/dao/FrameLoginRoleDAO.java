package com.xwtech.framework.pub.dao;

import com.xwtech.framework.pub.po.FrameLoginRole;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.util.List;

public class FrameLoginRoleDAO extends BaseDao
{

  protected static final Logger logger = Logger.getLogger(FrameLoginRoleDAO.class);

  public void saveFrameLoginRole(FrameLoginRole frameLoginRole)
  {
    getHibernateTemplate().saveOrUpdate(frameLoginRole);
  }

  public FrameLoginRole getFrameLoginRole(Long id)
  {
    FrameLoginRole frameLoginRole = (FrameLoginRole)getHibernateTemplate().get(FrameLoginRole.class, id);
    if(frameLoginRole == null)
    {
      logger.warn("uh oh, FrameLoginRole " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameLoginRole.class, id);
    }
  return frameLoginRole;
  }

  public List getFrameLoginRoles()
  {
    return getHibernateTemplate().loadAll(FrameLoginRole.class);
  }

  public void removeFrameLoginRole(Long id)
  {
    getHibernateTemplate().delete(getFrameLoginRole(id));
  }

   /**
   * 批量删除销户用户的登录ROLE记录
   */
  public boolean removeFrameLoginRoleByUserStatus(String userStatus)
  {
    StringBuffer hsql = new StringBuffer();
    hsql.append(" delete from frame_login_role a where exists (select 1 from Frame_Login  b where exists (select 1 from user_Info c where c.login_Id=b.login_Id and c.user_Status='"+userStatus+"') and a.login_Id=b.login_Id)");
    return executeCommonSql(hsql.toString()).intValue() != -1;
  }

}
