package com.xwtech.framework.pub.dao;

import com.xwtech.framework.pub.po.FrameLogin;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class FrameLoginDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameLoginDAO.class);

  public void saveFrameLogin(FrameLogin frameLogin)
  {
    getHibernateTemplate().saveOrUpdate(frameLogin);
  }

  public FrameLogin getFrameLogin(Long id)
  {
    FrameLogin frameLogin = (FrameLogin)getHibernateTemplate().get(FrameLogin.class, id);
    if(frameLogin == null)
    {
      logger.warn("uh oh, FrameLogin " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameLogin.class, id);
    }
  return frameLogin;
  }

  public List getFrameLogins()
  {
    return getHibernateTemplate().loadAll(FrameLogin.class);
  }

  public void removeFrameLogin(Long id)
  {
    getHibernateTemplate().delete(getFrameLogin(id));
  }

  public FrameLogin queryFrameLoginByLoginName(Long loginId,String loginName)
    {
      FrameLogin frameLogin = null;
      String hql = " from com.xwtech.framework.pub.po.FrameLogin frameLogin"
                   + " where frameLogin.loginName=? and frameLogin.loginState='A'"
                   +(loginId==null?"":" and frameLogin.loginId!="+loginId);
      Object[] params = new Object[1];
      params[0] = loginName;
      List l = getHibernateTemplate().find(hql, params);
      if(l.isEmpty())
        logger.warn("not find frameLogin, loginName=" + loginName );
      else
      {
        if(l.size() > 1)
          logger.warn("not only frameLogin, loginName=" + loginName );
        else
          frameLogin = (FrameLogin)l.get(0);
      }
      return frameLogin;
    }

  public FrameLogin queryFrameLoginByLoginNameAndLoginPwd(String loginName, String loginPwd)
      {
        FrameLogin frameLogin = null;
        String hql = " from com.xwtech.framework.pub.po.FrameLogin frameLogin"
                     + " where frameLogin.loginName = ? and frameLogin.loginPwd = ?"
                     + " and frameLogin.loginState = 'A'";
        Object[] params = new Object[2];
        params[0] = loginName;
        params[1] = loginPwd;
        List l = getHibernateTemplate().find(hql, params);
        if(l.isEmpty())
          logger.warn("not find frameLogin, loginName=" + loginName + " ,loginPwd=" + loginPwd);
        else
        {
          if(l.size() > 1)
            logger.warn("not only frameLogin, loginName=" + loginName + " ,loginPwd=" + loginPwd);
          else
            frameLogin = (FrameLogin)l.get(0);
        }
        return frameLogin;
      }

 public FrameLogin queryFrameLoginByLoginName(String loginName)
 {
   FrameLogin frameLogin = null;
   String hql = " from com.xwtech.framework.pub.po.FrameLogin frameLogin"
                + " where frameLogin.loginName = ? "
                + " and frameLogin.loginState = 'A'";
   Object[] params = new Object[1];
   params[0] = loginName;
   List l = getHibernateTemplate().find(hql, params);
   if(l.isEmpty())
     logger.warn("not find frameLogin, loginName=" + loginName );
   else
   {
     if(l.size() > 1)
       logger.warn("not only frameLogin, loginName=" + loginName);
     frameLogin = (FrameLogin)l.get(0);
   }
   return frameLogin;

 }

}
