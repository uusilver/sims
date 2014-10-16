package com.xwtech.framework.query.bo;

import java.net.URL;

/**
 * <p>Title: Framework</p>
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
public interface IQuery extends IHtmlet
{
  /**
   * 返回查询名称
   * @return String
   */
  public String getQueryName();

  /**
   * 设置查询名称
   * @param queryName String
   */
  public void setQueryName(String queryName);

  /**
   * 返回配置文件URL
   * @return URL
   */
  public URL getConfigFileUrl();

  /**
   * 设置配置文件URl
   * @param configFileUrl URL
   */
  public void setConfigFileUrl(URL configFileUrl);

  /**
   * 操作初始化
   * @return boolean
   */
  public boolean init();

  /**
   * 重新装载
   * @return boolean
   */
  public boolean reload();

  /**
   * 返回查询条件
   * @return IQueryCondition
   */
  public IQueryCondition getQueryCondition();

  /**
   * 获取查询数据的对象
   * @return QueryValueFetchByJdbcSql
   */
  public QueryValueFetchByJdbcSql getQueryValueFetch();
}
