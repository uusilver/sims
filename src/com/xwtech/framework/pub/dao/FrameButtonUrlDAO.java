package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameButtonUrl ;

public class FrameButtonUrlDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameButtonUrlDAO.class);

  public void saveFrameButtonUrl(FrameButtonUrl frameButtonUrl)
  {
    getHibernateTemplate().saveOrUpdate(frameButtonUrl);
  }

  public FrameButtonUrl getFrameButtonUrl(Long id)
  {
    FrameButtonUrl frameButtonUrl = (FrameButtonUrl)getHibernateTemplate().get(FrameButtonUrl.class, id);
    if(frameButtonUrl == null)
    {
      logger.warn("uh oh, FrameButtonUrl " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameButtonUrl.class, id);
    }
  return frameButtonUrl;
  }

  public List getFrameButtonUrls()
  {
    return getHibernateTemplate().loadAll(FrameButtonUrl.class);
  }

  public void removeFrameButtonUrl(Long id)
  {
    getHibernateTemplate().delete(getFrameButtonUrl(id));
  }

}
