package com.xwtech.framework.pub.utils;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ExceptionUtils
{
  public static String getStackTrace(Throwable ex)
  {
    if (ex == null ) return "";
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    return sw.toString();
  }
}
