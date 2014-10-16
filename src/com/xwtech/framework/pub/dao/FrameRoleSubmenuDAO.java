package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameRoleSubmenu ;

public class FrameRoleSubmenuDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameRoleSubmenuDAO.class);

  public void saveFrameRoleSubmenu(FrameRoleSubmenu frameRoleSubmenu)
  {
    getHibernateTemplate().saveOrUpdate(frameRoleSubmenu);
  }

  public FrameRoleSubmenu getFrameRoleSubmenu(Long id)
  {
    FrameRoleSubmenu frameRoleSubmenu = (FrameRoleSubmenu)getHibernateTemplate().get(FrameRoleSubmenu.class, id);
    if(frameRoleSubmenu == null)
    {
      logger.warn("uh oh, FrameRoleSubmenu " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameRoleSubmenu.class, id);
    }
  return frameRoleSubmenu;
  }

  public List getFrameRoleSubmenus()
  {
    return getHibernateTemplate().loadAll(FrameRoleSubmenu.class);
  }

  public void removeFrameRoleSubmenu(Long id)
  {
    getHibernateTemplate().delete(getFrameRoleSubmenu(id));
  }

}
