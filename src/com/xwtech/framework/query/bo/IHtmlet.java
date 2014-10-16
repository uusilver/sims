package com.xwtech.framework.query.bo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: Framework</p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 杨永清 * @version 1.0
 */
public interface IHtmlet
{
  /**
   * 生成Html片段
   * @param request HttpServletRequest
   * @return Html片段
   */
  public String getHtml(HttpServletRequest request);

}
