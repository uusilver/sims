package com.xwtech.framework.query.view;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Attribute;
import org.jdom.Element;

/**
 * <p>Title: 配置读取类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊

 */
public class QueryExhibitUtil
{
  /**
   * 获取一个属性

   * @param ele Element 节点
   * @param name String 属性名称

   * @param def String 默认值

   * @return String 当有属性值，且不为空时返回设置值；否则返回默认值。

   */
  public static String getAttributeNotEmpty(Element ele, String name, String def)
  {
    Attribute attr = ele.getAttribute(name);
    if(attr == null)return def;
    String val = attr.getValue();
    if(val == null)return def;
    val = val.trim();
    if(val.length() == 0)return def;
    return val;
  }

  /**
   * 获取一个属性

   * @param ele Element 节点
   * @param name String 属性名称

   * @param def int 默认值

   * @return int 当为有效的属性值时返回设置值，否则返回默认值。

   */
  public static int getAttributeNotEmpty(Element ele, String name, int def)
  {
    String val = getAttributeNotEmpty(ele, name, "");
    if(val.length() == 0)return def;
    try
    {
      return Integer.parseInt(val);
    }
    catch(Exception ex)
    {
      return def;
    }
  }

  /**
   * 获取一个子节点的属性

   * @param ele Element 节点
   * @param child String 子节点名称

   * @param name String 属性名称

   * @param def String 默认值

   * @return String 当有属性值，且不为空时返回设置值；否则返回默认值。

   */
  public static String getSingleChildAttributeNotEmpty(Element ele, String child, String name, String def)
  {
    Element eleChild = ele.getChild(child);
    if(eleChild == null)return def;
    return getAttributeNotEmpty(eleChild, name, def);
  }

  /**
   * 获取一个子节点的整型值

   * @param ele Element 节点
   * @param child String 子节点名称

   * @param def int 默认值

   * @return int 如果不是有效的值，则返回默认的值

   */
  public static int getSingleChildValue(Element ele, String child, int def)
  {
    Element eleChild = ele.getChild(child);
    if(eleChild == null)return def;
    String val = eleChild.getTextTrim();
    if(val == null || val.length() == 0)return def;
    try
    {
      return Integer.parseInt(val);
    }
    catch(Exception ex)
    {
      return def;
    }
  }

  /**
   * 获取一个子节点的字符串值

   * @param ele Element 节点
   * @param child String 子节点名称

   * @param def String 默认值

   * @return String 当有值，且不为空时返回设置值；否则返回默认值。

   */
  public static String getSingleChildValueNotEmpty(Element ele, String child, String def)
  {
    Element eleChild = ele.getChild(child);
    if(eleChild == null)return def;
    String val = eleChild.getTextTrim();
    if(val == null || val.length() == 0)return def;
    return val;
  }

  public static String getValueNotEmpty(Element ele, String def)
  {
    String val = ele.getTextTrim();
    if(val == null || val.length() == 0)return def;
    return val;
  }

  /**
   * 取得当前请求的完整URL
   * @param request HttpServletRequest
   * @return String
   */
  public static String getRequesstURL(HttpServletRequest request)
  {
    String url = request.getRequestURL().toString();
    String q = request.getQueryString();
    if(q != null)
      return url + "?" + q;
    else
      return url;
  }

  /**
   * 取得当前请求的完整URL,不含传入的请求参数
   * @param request HttpServletRequest
   * @return String
   */
  public static String getRequesstURLNoRequest(HttpServletRequest request)
  {
    return request.getRequestURL().toString();
  }  
}
