package com.xwtech.framework.query.bo;

import javax.servlet.http.HttpServletRequest;
import org.jdom.Element;

/**
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IValueFetch
{
  /**
   * 从缓存中查询数据
   * @param eleConfig Element
   * @param sqlConfig SqlConfig
   * @param request HttpServletRequest
   * @param queryValue QueryValue
   * @return boolean
   */
  public boolean fetchCacheResultFromDb(Element eleConfig,QueryValueFetchByJdbcSql.SqlConfig sqlConfig, HttpServletRequest request, QueryValue queryValue);

  /**
   * 通过查询操作获取数据
   * @param eleConfig Element
   * @param sqlConfig SqlConfig
   * @param request HttpServletRequest
   * @param queryValue QueryValue
   */
  public void fetchPageResultFromDb(Element eleConfig,QueryValueFetchByJdbcSql.SqlConfig sqlConfig, HttpServletRequest request, QueryValue queryValue);
}
