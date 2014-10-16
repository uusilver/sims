package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameMenu ;

public class FrameMenuDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameMenuDAO.class);

  public void saveFrameMenu(FrameMenu frameMenu)
  {
    getHibernateTemplate().saveOrUpdate(frameMenu);
  }

  public FrameMenu getFrameMenu(Long id)
  {
    FrameMenu frameMenu = (FrameMenu)getHibernateTemplate().get(FrameMenu.class, id);
    if(frameMenu == null)
    {
      logger.warn("uh oh, FrameMenu " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameMenu.class, id);
    }
  return frameMenu;
  }

  public List getFrameMenus()
  {
    return getHibernateTemplate().loadAll(FrameMenu.class);
  }

  public void removeFrameMenu(Long id)
  {
    getHibernateTemplate().delete(getFrameMenu(id));
  }

}
