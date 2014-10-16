package com.xwtech.framework.pub.utils;

import org.jdom.Element;
import org.jdom.Attribute;
import java.util.Iterator;
import org.jdom.Document;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.StringReader;
import org.jdom.input.SAXBuilder;
import org.jdom.JDOMException;
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
public class JdomUtils
{

  /**
   * 根据URL解析Xml
   * @param resource Resource Xml文件对应的URL对象
   * @return Document 返回Xml Document
   */
  public static Document parseXml(URL url)
  {
    if (url==null)
      return null;
    SAXBuilder builder = new SAXBuilder();
    Document document = null;
    try
    {
      document = builder.build(url);
    }
    catch(IOException ex)
    {
      throw new RuntimeException(ex);
    }
    catch(JDOMException ex)
    {
      throw new RuntimeException(ex);
    }
    return document;
  }


  /**
   * 根据String 内容解析Xml
   * @param resource Resource Xml内容对应的String
   * @return Document 返回Xml Document
   */
  public static Document parseXml(String contend)
  {
    if (contend==null)
      return null;
    SAXBuilder builder = new SAXBuilder();
    Document document = null;
    try
    {
      document = builder.build(new StringReader(contend));
    }
    catch(IOException ex)
    {
      throw new RuntimeException(ex);
    }
    catch(JDOMException ex)
    {
      throw new RuntimeException(ex);
    }
    return document;
  }
  /**
   * 获取一个属性
   * @param element Element 节点
   * @param attributeName String 属性名称
   * @param defaultValue String 默认值
   * @return String 当有属性值，且不为空时返回属性值；否则返回默认值。
   */
  public static String getAttributeValue(Element element, String attributeName, String defaultValue)
  {
    //参数检查
    if(element == null)
      return defaultValue;
    if (StringUtils.isBlank(attributeName))
      return defaultValue;

    //获取属性值
    String val = element.getAttributeValue(attributeName,defaultValue);
    if(val==null)
      return defaultValue;
    return val;
  }

  /**
   * 获取一个属性
   * <i>getAttributeValue(element, attributeName,"")</i>
   * @param element Element
   * @param attributeName String
   * @return String
   * @see #getAttributeValue(Element element, String attributeName, String defaultValue)
   */
  public static String getAttributeValue(Element element, String attributeName)
  {
    return getAttributeValue(element, attributeName,"");
  }

  /**
   * 根据属性查找第一个节点
   * @param element Element 父节点
   * @param childName String 子节点名（可选）
   * @param attributeName String 属性名
   * @param attributeValue String 属性值
   * @return Element 匹配给定子节点名和属性的第一个节点
   */
  public static Element getChildByAttribute(Element element, String childName,String attributeName, String attributeValue)
  {
    //参数检查
    if (element==null)
      return null;
    if (StringUtils.isBlank(attributeName))
      return null;

    //根据childName查找所有的Children
    Iterator iterator = null;
    if (StringUtils.isBlank(childName))
      iterator = element.getChildren().iterator();
    else
    {
      childName = childName.trim();
      iterator = element.getChildren(childName).iterator();
    }

    //属性匹配
    Element elementResult = null;
    while(iterator.hasNext())
    {
      elementResult = (Element) iterator.next();
      if (getAttributeValue(elementResult,attributeName).equals(attributeValue))
        break;
      elementResult = null;
    }
    return elementResult;
  }

  /**
   * 获取一个属性
   * @param element Element 节点
   * @param attributeName String 属性名称
   * @param defaultValue String 默认值
   * @return String 当有属性值，且不为空时返回属性值；否则返回默认值。
   */
  public static boolean getAttributeBooleanValue(Element element, String attributeName, boolean defaultValue)
  {
    //参数检查
    if(element == null)
      return defaultValue;
    if (StringUtils.isBlank(attributeName))
      return defaultValue;

    //获取属性值
    String val = element.getAttributeValue(attributeName,(defaultValue?"true":"false"));
    val = val.toLowerCase();
    if (val.equals("true"))
      return true;
    if (val.equals("false"))
      return false;
    return defaultValue;
  }

  /**
   * 获取一个属性
   * @param element Element 节点
   * @param attributeName String 属性名称
   * @param defaultValue String 默认值
   * @return String 当有属性值，且不为空时返回属性值；否则返回默认值。
   */
  public static int getAttributeIntValue(Element element, String attributeName, int defaultValue)
  {
    //参数检查
    if(element == null)
      return defaultValue;
    if (StringUtils.isBlank(attributeName))
      return defaultValue;

    //获取属性值
    String val = element.getAttributeValue(attributeName,String.valueOf(defaultValue));
    return NumberUtils.toInt(val,defaultValue);
  }
}
