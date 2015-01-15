package com.tmind.framework.pub.utils;

import org.apache.log4j.Logger;

/**
 * <p>Title: Framework</p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: tmind.com</p>
 *
 * @author 杨洋

 * @version 1.0
 */
public class ClassUtils
{
  protected static final Logger logger = Logger.getLogger(ClassUtils.class);

  /**
   * 实例化一个类，封装实例化中的异常
   * @param className String 类名
   * @return Object 实例化的类
   */
  public static Object newInstance(String className)
  {
    Object object = null;
    try
    {
      object = Class.forName(className).newInstance();
    }
    catch(ClassNotFoundException ex)
    {
      throw new RuntimeException("new instance "+className+" error",ex);
    }
    catch(IllegalAccessException ex)
    {
      throw new RuntimeException("new instance "+className+" error",ex);
    }
    catch(InstantiationException ex)
    {
      throw new RuntimeException("new instance "+className+" error",ex);
    }
    return object;
  }
}
