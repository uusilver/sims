package com.xwtech.framework.pub.filter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.hibernate.FlushMode;
import org.apache.log4j.*;
/**
 * 设置每个请求使用同一个session
*  目的：可以使用hibernate的lazy功能
 * <p>Title: Framework </p>
 * <p>Description: Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author not attributable
 * @version 1.0
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
{
  protected static final Logger log = Logger.getLogger(OpenSessionInViewFilter.class);
  /**
   * 构造函数
   */
  public OpenSessionInViewFilter()
  {
  }
  /**
   * 获取session
   * 将flushMode设为auto，目的：如果不设，则spring会自动将其设为never，也就是说永远不flush，数据也将无法提交
   * @param sessionFactory SessionFactory
   * @throws DataAccessResourceFailureException        抛出异常
   * @return Session
   */
  protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
    Session session = SessionFactoryUtils.getSession(sessionFactory, true);
    session.setFlushMode(FlushMode.AUTO);
    return session;
  }
  /**
   * 关闭session
   * 1：flush
   * 2：关闭session
   * @param session Session
   * @param sessionFactory SessionFactory
   */
  protected void closeSession(Session session, SessionFactory sessionFactory) {
    try {
      session.flush();
    }
    catch (Exception ex) {
      log.error("closeSession flush失败",ex);
    }
    SessionFactoryUtils.releaseSession(session, sessionFactory);
  }

}
