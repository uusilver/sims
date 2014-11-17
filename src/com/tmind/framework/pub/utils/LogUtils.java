package com.tmind.framework.pub.utils;

import org.apache.log4j.Logger;
import com.tmind.framework.pub.web.BaseFrameworkApplication;

/**
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: tmind.com</p>
 *
 * @author 杨永清
 * @version 1.0
 */
public class LogUtils
{
  public static void logTimer(Logger logger,String msg)
  {
    if (BaseFrameworkApplication.logTimer)
      logger.info(msg);
  }
}
