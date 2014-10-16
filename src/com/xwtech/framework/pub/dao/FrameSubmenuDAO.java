package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameSubmenu ;

public class FrameSubmenuDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameSubmenuDAO.class);

  public void saveFrameSubmenu(FrameSubmenu frameSubmenu)
  {
    getHibernateTemplate().saveOrUpdate(frameSubmenu);
  }

  public FrameSubmenu getFrameSubmenu(Long id)
  {
    FrameSubmenu frameSubmenu = (FrameSubmenu)getHibernateTemplate().get(FrameSubmenu.class, id);
    if(frameSubmenu == null)
    {
      logger.warn("uh oh, FrameSubmenu " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameSubmenu.class, id);
    }
  return frameSubmenu;
  }

  public List getFrameSubmenus()
  {
    return getHibernateTemplate().loadAll(FrameSubmenu.class);
  }

  public void removeFrameSubmenu(Long id)
  {
    getHibernateTemplate().delete(getFrameSubmenu(id));
  }

}
