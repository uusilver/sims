package com.tmind.framework.pub.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description: Framework</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: tmind.com</p>
 * @author starxu
 * @version 1.0
 * Context配置bean的存取类
 */
public class ContextBeanUtils
{
  public ContextBeanUtils()
  {
  }

  public static Object getBean(String beanName,HttpServletRequest request)
  {
    return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean(beanName);
  }

}
