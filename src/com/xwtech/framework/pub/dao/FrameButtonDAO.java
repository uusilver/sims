package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameButton ;

public class FrameButtonDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameButtonDAO.class);

  public void saveFrameButton(FrameButton frameButton)
  {
    getHibernateTemplate().saveOrUpdate(frameButton);
  }

  public FrameButton getFrameButton(Long id)
  {
    FrameButton frameButton = (FrameButton)getHibernateTemplate().get(FrameButton.class, id);
    if(frameButton == null)
    {
      logger.warn("uh oh, FrameButton " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameButton.class, id);
    }
  return frameButton;
  }

  public List getFrameButtons()
  {
    return getHibernateTemplate().loadAll(FrameButton.class);
  }

  public void removeFrameButton(Long id)
  {
    getHibernateTemplate().delete(getFrameButton(id));
  }

}
