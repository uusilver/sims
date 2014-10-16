package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameRole ;
import com.xwtech.framework.pub.utils.StringUtils;

public class FrameRoleDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameRoleDAO.class);

  public void saveFrameRole(FrameRole frameRole)
  {
    getHibernateTemplate().saveOrUpdate(frameRole);
  }

  public FrameRole getFrameRole(Long id)
  {
    FrameRole frameRole = (FrameRole)getHibernateTemplate().get(FrameRole.class, id);
    if(frameRole == null)
    {
      logger.warn("uh oh, FrameRole " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameRole.class, id);
    }
  return frameRole;
  }

  public List getFrameRoles()
  {
    return getHibernateTemplate().loadAll(FrameRole.class);
  }

  public void removeFrameRole(Long id)
  {
    getHibernateTemplate().delete(getFrameRole(id));
  }
}
