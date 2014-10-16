package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameUrl ;

public class FrameUrlDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameUrlDAO.class);

  public void saveFrameUrl(FrameUrl frameUrl)
  {
    getHibernateTemplate().saveOrUpdate(frameUrl);
  }

  public FrameUrl getFrameUrl(Long id)
  {
    FrameUrl frameUrl = (FrameUrl)getHibernateTemplate().get(FrameUrl.class, id);
    if(frameUrl == null)
    {
      logger.warn("uh oh, FrameUrl " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameUrl.class, id);
    }
  return frameUrl;
  }

  public List getFrameUrls()
  {
    return getHibernateTemplate().loadAll(FrameUrl.class);
  }

  public void removeFrameUrl(Long id)
  {
    getHibernateTemplate().delete(getFrameUrl(id));
  }

}
