package com.tmind.framework.pub.utils;

import java.util.Map;
import java.util.Iterator;

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
public class MapUtils
{
  public static String toString(Map map,String separator)
  {
    //参数检查
    if (map == null)
      return null;

    Iterator iterator = map.keySet().iterator();
    StringBuffer sb = new StringBuffer();
    boolean ifFirst = true;
    while(iterator.hasNext())
    {
      Object key = iterator.next();
      Object value = map.get(key);
      if (value!=null)
      {
        if(ifFirst)
          ifFirst = false;
        else
          sb.append(separator);
        sb.append(key).append("=\"").append(value).append("\"");
      }
    }
    return sb.toString();
  }
}
