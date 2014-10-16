package com.xwtech.framework.pub.utils;

import java.lang.reflect.*;
import org.apache.log4j.Logger;
import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.BeanUtilsBean;

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
public class BeanUtils
{
  protected static final Logger logger = Logger.getLogger(BeanUtils.class);

  /**
   * 设置Bean的属性
   * @param bean Object
   * @param name String
   * @param value Object
   */
  public static void setProperty(Object bean, String name, Object value)
  {
    try
    {
      org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
    }
    catch(InvocationTargetException ex)
    {
      logger.error("bean " + bean.getClass() + "'s " + name + " can't set value " + value, ex);
    }
    catch(IllegalAccessException ex)
    {
      logger.error("bean " + bean.getClass() + "'s " + name + " can't set value " + value, ex);
    }
  }

  /**
   * 获取Bean的属性
   * @param bean Object
   * @param name String
   * @return Object
   */
  public static Object getProperty(Object bean, String name)
  {
    if (bean==null)
      return null;
    try
    {
      return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
    }
    catch(NoSuchMethodException ex1)
    {
      return null;
    }
    catch(InvocationTargetException ex1)
    {
      return null;
    }
    catch(IllegalAccessException ex1)
    {
      return null;
    }

  }

  /**
   * 复制两个对象中具有相同属性的值
   * 当源对象的值为null时不copy该属性的值
   * @param dest Object              目标对象
   * @param orig Object              源对象
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static void copySameProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException
  {
    BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
    // Validate existence of the specified beans
    if(dest == null)
    {
      throw new IllegalArgumentException
          ("No destination bean specified");
    }
    if(orig == null)
    {
      throw new IllegalArgumentException("No origin bean specified");
    }
    if(logger.isDebugEnabled())
    {
      logger.debug("BeanUtils.copySameProperties(" + dest + ", " +
          orig + ")");
    }

    // Copy the properties, converting as necessary
    if(orig instanceof DynaBean)
    {
      DynaProperty origDescriptors[] =
          ((DynaBean)orig).getDynaClass().getDynaProperties();
      for(int i = 0; i < origDescriptors.length; i++)
      {
        String name = origDescriptors[i].getName();
        if(beanUtilsBean.getPropertyUtils().isWriteable(dest, name))
        {
          Object value = ((DynaBean)orig).get(name);
          if(value != null)
            beanUtilsBean.copyProperty(dest, name, value);
        }
      }
    }
    else
      if(orig instanceof Map)
      {
        Iterator names = ((Map)orig).keySet().iterator();
        while(names.hasNext())
        {
          String name = (String)names.next();
          if(beanUtilsBean.getPropertyUtils().isWriteable(dest, name))
          {
            Object value = ((Map)orig).get(name);
            if(value != null)
              beanUtilsBean.copyProperty(dest, name, value);
          }
        }
      }
      else /* if (orig is a standard JavaBean) */
      {
        PropertyDescriptor origDescriptors[] =
            beanUtilsBean.getPropertyUtils().getPropertyDescriptors(orig);
        for(int i = 0; i < origDescriptors.length; i++)
        {
          String name = origDescriptors[i].getName();
          if("class".equals(name))
          {
            continue; // No point in trying to set an object's class
          }
          if(beanUtilsBean.getPropertyUtils().isReadable(orig, name) &&
              beanUtilsBean.getPropertyUtils().isWriteable(dest, name))
          {
            try
            {
              Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(orig, name);
              if(value != null)
                beanUtilsBean.copyProperty(dest, name, value);
            }
            catch(NoSuchMethodException e)
            {
              ; // Should not happen
            }
          }
        }
      }
  }

  public static String getDescribeString(Object bean, String separator)
  {
    String describeString = null;
    try
    {
      java.util.Map map = org.apache.commons.beanutils.BeanUtils.describe(bean);

      //对于class和styleClass的特殊处理
      map.remove("class");
      Object objStyleClass = map.get("styleClass");
      if(objStyleClass != null)
      {
        map.remove("styleClass");
        map.put("class", objStyleClass);
      }

      //获取最终的describeString
      describeString = MapUtils.toString(map, separator);
    }
    catch(NoSuchMethodException ex)
    {
      logger.error("bean " + bean.getClass() + " get  describe error", ex);
    }
    catch(InvocationTargetException ex)
    {
      logger.error("bean " + bean.getClass() + " get  describe error", ex);
    }
    catch(IllegalAccessException ex)
    {
      logger.error("bean " + bean.getClass() + " get  describe error", ex);
    }
    return describeString;
  }
}
