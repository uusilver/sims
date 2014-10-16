package com.xwtech.framework.pub.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import java.sql.SQLException;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: xwtech.com</p>
 * @author starxu
 * @version 1.0
 *
 * 基本 hibernate DAO操作
 */
public class BaseHibernateDAO extends HibernateDaoSupport
{
  protected static final Logger logger = Logger.getLogger(BaseHibernateDAO.class);

  public void saveObject(Object o)
  {
    getHibernateTemplate().saveOrUpdate(o);
  }

  public Object getObject(Class clazz, Serializable id)
  {
    Object o = null;
    try
    {
      o = getHibernateTemplate().get(clazz, id);

      if(o == null)
      {
        throw new ObjectRetrievalFailureException(clazz, id);
      }

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    return o;
  }

  public void executeHSQL(final String hSql)
  {
    HibernateTemplate template = new HibernateTemplate(getHibernateTemplate().getSessionFactory());
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException
      {
        logger.info("begin test");
        Query query = session.createQuery(hSql);
        int i = query.executeUpdate();
        logger.info("test is ok");
        return new Integer(i);
      }
    });
  }

  public List getObjects(Class clazz)
  {
    return getHibernateTemplate().loadAll(clazz);
  }

  public void removeObject(Class clazz, Serializable id)
  {
    getHibernateTemplate().delete(getObject(clazz, id));
  }
}
