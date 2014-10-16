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
public interface IMultiValuable
{
  /**
   * 获取名字
   * @return 名字
   */
  public String getName();

  /**
   * 设置多个值
   * @param mutipleValues String[]
   */
  public void setMutipleValues(String[] mutipleValues);
}
