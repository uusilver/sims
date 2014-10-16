package com.xwtech.framework.pub.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameRefsql ;

public class FrameRefsqlDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameRefsqlDAO.class);

  public void saveFrameRefsql(FrameRefsql frameRefsql)
  {
    getHibernateTemplate().saveOrUpdate(frameRefsql);
  }

  public FrameRefsql getFrameRefsql(Long id)
  {
    FrameRefsql frameRefsql = (FrameRefsql)getHibernateTemplate().get(FrameRefsql.class, id);
    if(frameRefsql == null)
    {
      logger.warn("uh oh, FrameRefsql " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameRefsql.class, id);
    }
  return frameRefsql;
  }

  public List getFrameRefsqls()
  {
    return getHibernateTemplate().loadAll(FrameRefsql.class);
  }

  public void removeFrameRefsql(Long id)
  {
    getHibernateTemplate().delete(getFrameRefsql(id));
  }

}
