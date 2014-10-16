package com.xwtech.framework.query.view;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public interface IQueryPartView
{
  /**
   * 通用查询附加视图
   * @param areaType int 区域类型：QueryConfigExtraPart.AREA_TYPE_*
   * @param request HttpServletRequest 请求对象
   * @param args HashMap 静态参数列表，如果没有静态参数，则元素个数为0
   * @return String
   */
  public String toHTML(int areaType, HttpServletRequest request,HashMap args);
}
