package com.xwtech.framework.query.bo;

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
public interface IQueryComponent
{
  /**
   * 返回查询
   * @return IQuery
   */
  public IQuery getQuery();

  /**
   * 设置查询
   * @param query IQuery
   */
  public void setQuery(IQuery query);

  /**
   * 操作初始化
   * @return boolean
   */
  public boolean init();

}
