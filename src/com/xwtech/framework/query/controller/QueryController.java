package com.xwtech.framework.query.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;
import com.xwtech.framework.pub.utils.RequestUtils;
import com.xwtech.framework.query.bo.QueryManager;
import java.util.HashMap;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class QueryController extends BaseCommandController
{
  protected static final Logger logger = Logger.getLogger(QueryController.class);

  protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    throws Exception
  {
    String name = RequestUtils.getStringParameter(httpServletRequest,"name","").trim();
    if(name.length() == 0)
    {
      logger.info("未指定的查询,URL中缺少name属性");
      throw new Exception("未指定的查询,URL中缺少name属性");
    }

    HashMap map = new HashMap();
    long startTime = System.currentTimeMillis();
    map.put("html",QueryManager.getHtml(name,httpServletRequest));
    System.out.println("queryCostTime="+(System.currentTimeMillis()-startTime));
    return new ModelAndView("/framework/jsp/query.jsp","info",map);
  }
}
