package com.xwtech.framework.pub.utils;

import java.util.Map;
import java.util.Iterator;

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
