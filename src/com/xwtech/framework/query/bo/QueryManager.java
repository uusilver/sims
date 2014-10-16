package com.xwtech.framework.query.bo;

import javax.servlet.http.HttpServletRequest;
import org.jdom.Document;
import org.jdom.Element;
import org.apache.log4j.Logger;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

import org.jdom.input.SAXBuilder;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.StringReader;
import org.jdom.JDOMException;
import com.xwtech.framework.pub.utils.JdomUtils;
import com.xwtech.framework.pub.utils.Assert;
import com.xwtech.framework.pub.utils.ClassUtils;
import com.xwtech.framework.pub.utils.CalTime;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.utils.ExceptionUtils;

/**
 * <p>Title: Framework</p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * QueryManager是查询框架中的总控程序，查询系统的结构如下
 * QueryManager
 *    IQuery（目前的实现为QueryByXml）
 *        IQueryCondition（目前的实现为QueryConditionByXml）：条件区域
 *        IQueryValueFetch（目前的实现为QueryValueFetchByJdbcSql）：结果获取
 *        QueryExhibit：结果展现
 * @author 杨永清
 * @version 1.0
 *
 * 修改记录：
 * 1、日期：2006-09-05 10:39
 *    动作：新增方法 refreshQueryConfigs
 *    人员：朱政
 *    内容：刷新查询配置
 *
 * 2、日期：2006-09-05 10:39
 *    动作：新增方法 refreshCacheHashMap
 *    人员：朱政
 *    内容：根据现有缓存中的数据去重新load数据，
 *          主要目的重载所有在缓存中的数据
 */
public class QueryManager
{
  protected static final Logger logger = Logger.getLogger(QueryManager.class);

  /**
   * 是否缓存配置
   */
  private static boolean cacheConfig = false;

  /**
   * 配置集文件的URL
   */
  private static URL configsFileUrl;

  private static final String DEFAULT_URL = "file:D:/VssDev/projectsrc/Framework/WEB-INF/queryXml/queryConfigs.xml";

  /**
   * 存放查询的HashMap
   */
  private static HashMap queryMap;

  private static HashMap dynamicQueryMap = new HashMap();

  private static HashMap queryStyleMap = new HashMap();

  /**
   * 获取查询配置集文件
   * @return boolean
   */
  private static boolean queryConfigsFileUrl()
  {
    if(configsFileUrl == null)
    {
      try
      {
        configsFileUrl = new URL("file:"+BaseFrameworkApplication.FrameworkWebAppRootPath + "/WEB-INF/"+BaseFrameworkApplication.queryConfigFilePath);
      }
      catch(MalformedURLException ex)
      {
        logger.error("",ex);
        return false;
      }
      /*
      URL url = QueryManager.class.getResource("/");
      try
      {
        if(!url.getPath().endsWith("/WEB-INF/classes/"))
        {
          configsFileUrl = new URL(DEFAULT_URL);
        }
        else
          configsFileUrl = new URL(url, "../queryXml/queryConfigs.xml");
      }
      catch(MalformedURLException ex)
      {
        return false;
      }
*/
    }
    return true;
  }

  /**
   * 获取查询
   * @return boolean
   */
  public static boolean getQueryConfigs()
  {
    //获取查询配置集文件
    if(!queryConfigsFileUrl())
      return false;
    if(configsFileUrl == null)
      return false;

    //初始化查询Map
    queryMap = new HashMap();

    //解析查询配置集文件
    Document configsFileDoc = JdomUtils.parseXml(configsFileUrl);
    Element eleQueryConfigs = configsFileDoc.getRootElement();
    Assert.notNull(eleQueryConfigs, "查询配置集文件（" + configsFileUrl.getPath() + "）格式错误");
    logger.debug("通用查询：读取查询配置集文件（" + configsFileUrl.getPath() + "）");
    //读取是否缓存配置
    cacheConfig = JdomUtils.getAttributeBooleanValue(eleQueryConfigs, "cacheConfig", false);

    //读取查询配置并实例化相应的查询配置类
    Iterator iterator = eleQueryConfigs.getChildren("queryConfig").iterator();
    Element eleQueryConfig = null;
    String queryConfigClass = null;
    IQueryConfig queryConfig = null;
    while(iterator.hasNext())
    {
      eleQueryConfig = (Element)iterator.next();
      QueryConfigsDocByXml.serverUrl = JdomUtils.getAttributeValue(eleQueryConfig, "serverUrl");
      logger.debug("通用查询：读取服务的URL地址（" + QueryConfigsDocByXml.serverUrl + "）");

      queryConfigClass = JdomUtils.getAttributeValue(eleQueryConfig, "class");
      Object objQueryConfig = null;
      try
      {
        objQueryConfig = ClassUtils.newInstance(queryConfigClass);
      }
      catch(RuntimeException ex)
      {
        logger.error("通用查询：查询配置解析类（" + queryConfigClass + "）实例化失败", ex);
        continue;
      }

      if(objQueryConfig instanceof IQueryConfig)
      {
        queryConfig = (IQueryConfig)objQueryConfig;
        if(objQueryConfig instanceof IJdomConfig)
          ((IJdomConfig)objQueryConfig).setEleConfig(eleQueryConfig);
        HashMap configQueryMap = queryConfig.getQueries();
        queryMap.putAll(configQueryMap);
      }
    }
    queryMap.putAll(dynamicQueryMap);
    getQueryOtherConfig();
    return true;
  }

  /**
   * 读取其他配置
   * @return boolean
   */
  public static boolean getQueryOtherConfig()
  {
    //获取查询配置集文件
    if(!queryConfigsFileUrl())
      return false;
    if(configsFileUrl == null)
      return false;
    URL styleConfigsFileUrl = null;
    try
    {
      styleConfigsFileUrl = new URL("file:"+BaseFrameworkApplication.FrameworkWebAppRootPath + "/WEB-INF/"+BaseFrameworkApplication.queryStyleConfigFilePath);
    }
    catch(MalformedURLException ex)
    {
      logger.error("",ex);
      return false;
    }
    //解析查询配置集文件
    Document styleConfigsFileDoc = JdomUtils.parseXml(styleConfigsFileUrl);
    Element eleQueryStyleConfigs = styleConfigsFileDoc.getRootElement();
    Assert.notNull(eleQueryStyleConfigs, "查询格式配置文件（" + configsFileUrl.getPath() + "）格式错误");
    logger.debug("通用查询：读取查询格式配置文件（" + configsFileUrl.getPath() + "）");

    //读取查询配置并实例化相应的查询配置类
    Iterator iterator = eleQueryStyleConfigs.getChildren("queryStyleConfig").iterator();
    Element eleQueryStyleConfig = null;
    while(iterator.hasNext())
    {
      eleQueryStyleConfig = (Element)iterator.next();
      String queryStyleConfigName = JdomUtils.getAttributeValue(eleQueryStyleConfig, "name");
      String queryStyleConfigContent = eleQueryStyleConfig.getTextTrim();
      queryStyleMap.put(queryStyleConfigName, queryStyleConfigContent);
    }
    return true;
  }

  /**
   * 动态增加一个查询
   * @param queryConfig String
   * @return boolean
   */
  public static boolean addQuery(String queryConfig)
  {
    SAXBuilder builder = new SAXBuilder();
    Document document = null;
    try
    {
      StringReader reader = new StringReader(queryConfig);
      document = builder.build(reader);
    }
    catch(IOException ex)
    {
      logger.error("",ex);
      return false;
    }
    catch(JDOMException ex)
    {
      logger.error("",ex);
      return false;
    }
    Element eleQueryConfigs = document.getRootElement();
    return addQuery(eleQueryConfigs);
  }
  /**
   * 动态增加一个查询
   * @param eleQuery Element
   * @return boolean
   */
  public static boolean addQuery(Element eleQuery)
  {
    IQuery query = null;
    String queryName = JdomUtils.getAttributeValue(eleQuery, "name");
    String queryClass = JdomUtils.getAttributeValue(eleQuery, "class");
    Object objQuery = null;
    try
    {
      objQuery = ClassUtils.newInstance(queryClass);
    }
    catch(RuntimeException ex)
    {
      logger.error("通用查询：查询（" + queryName + "）对应的类（" + queryClass + "）实例化失败", ex);
      return false;
    }
    if(objQuery instanceof IQuery)
    {
      query = (IQuery)objQuery;
      //设置查询名称
      query.setQueryName(queryName);
      //设置Xml查询配置节点
      if(objQuery instanceof IJdomConfig)
        ((IJdomConfig)objQuery).setEleConfig(eleQuery);
      //查询初始化并放置到查询Map中
      if(query.init())
        queryMap.put(queryName, query);
      return true;
    }
    return false;
  }

  /**
   * 运行查询
   * @param queryName String 查询名称
   * @param request HttpServletRequest Request对象
   * @return String Html文本
   */
  public static String getHtml(String queryName, HttpServletRequest request)
  {
    CalTime calTime = new CalTime();
    calTime.begin();
    long beginTime = System.currentTimeMillis();
    if(!cacheConfig)
      getQueryOtherConfig();

    //第一次运行时读取查询配置
    if(queryMap == null)
      getQueryConfigs();
    //获取查询
    Object objQuery = queryMap.get(queryName);
    if(objQuery == null)
    {
      //如果没有读取到并且不缓存配置，重新读取配置
      if(!cacheConfig)
      {
        getQueryConfigs();
        objQuery = queryMap.get(queryName);
      }
      if(objQuery == null)
        throw new RuntimeException("通用查询：没有找到对应的查询，查询名称" + queryName);
    }
    else
    {
      if(!cacheConfig)
        ((IQuery)objQuery).reload();
    }
    String html = ((IQuery)objQuery).getHtml(request);
    logger.info("通用查询：查询"+queryName+"运行时间为"+calTime.endString());
    return html;
  }

  /**
   * 获取配置集文件的URL
   * @return URL
   */
  public static URL getConfigsFileUrl()
  {
    return configsFileUrl;
  }

  /**
   * 获取查询风格的HashMap
   * @return HashMap
   */
  public static HashMap getQueryStyleMap()
  {
    return queryStyleMap;
  }
  /**
   * 错误日志打印
   * @param logger Logger
   * @param query IQuery
   * @param msg String
   * @param ex Throwable
   */
  public static void logError(Logger logger,IQuery query,String msg,Throwable ex)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("通用查询错误：");
    sb.append(msg);
    sb.append(StringUtils.LINE_SEPARATOR);
    if (query!=null)
    {
      sb.append("查询名：" + query.getQueryName());
      if (query.getConfigFileUrl()!=null)
        sb.append("查询配置文件：" + query.getConfigFileUrl().getPath());
    }
    logger.error(msg);
    if (ex!=null)
      logger.error(ExceptionUtils.getStackTrace(ex));
  }

  /**
   * 错误日志打印
   * @param logger Logger
   * @param query IQuery
   * @param msg String
   */
  public static void logError(Logger logger,IQuery query,String msg)
  {
    logError(logger,query,msg,null);
  }

  /**
   * 刷新查询配置
   * 直接调用获取查询配置方法 getQueryConfigs()
   */
  public static void refreshQueryConfigs() {
    getQueryConfigs();
  }

  /**
   * 重新加载所有在缓存中的数据
   */
  public void refreshCacheHashMap(HttpServletRequest request) {
    if (queryMap != null) {
      Iterator iterator = queryMap.values().iterator();
      while (iterator.hasNext()) {
        QueryByXml queryByXml = (QueryByXml)iterator.next();
        QueryValueFetchByJdbcSql queryValueFetchByJdbcSql = queryByXml.getQueryValueFetch();
        if (queryValueFetchByJdbcSql.isNeedCache()) {
          Element element = queryValueFetchByJdbcSql.getEleConfig();
          QueryValue queryValue = new QueryValue();
          queryValue.queryName = queryValueFetchByJdbcSql.getQuery().getQueryName();
          queryValue.dataList = new ArrayList();
          queryValue.allDataList = new ArrayList();
          queryValue.pageSize = queryValueFetchByJdbcSql.getPageSize();
          queryValue.pageNo = 1;
          queryValueFetchByJdbcSql.getAgentClass().fetchCacheResultFromDb(element,queryValueFetchByJdbcSql.getSqlConfig(queryValueFetchByJdbcSql,request),request,queryValue);
        }
      }
    }
  }
}
