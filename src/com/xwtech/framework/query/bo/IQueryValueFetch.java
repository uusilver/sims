package com.xwtech.framework.query.bo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 杨永清
 * @version 1.0
 */
public interface IQueryValueFetch extends IQueryComponent
{
  /**
   * 判断是否需要随机
   * @return boolean
   */
  public boolean isNeedRadom();

  /**
   * 判断是否需要统计
   * @return boolean
   */
  public boolean isNeedCount();

  /**
   * 判断是否需要缓存
   * @return boolean
   */
  public boolean isNeedCache();

  /**
   * 运行查询
   * @param request HttpServletRequest
   * @return QueryValue
   */
  public QueryValue run(HttpServletRequest request);
}
