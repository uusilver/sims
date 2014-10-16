package com.xwtech.framework.query.bo;

import org.jdom.Element;

import com.xwtech.framework.pub.utils.Assert;
import com.xwtech.framework.pub.utils.JdomUtils;
import com.xwtech.framework.pub.utils.ClassUtils;
import java.util.Iterator;
import java.net.URL;
import java.net.MalformedURLException;
import org.apache.log4j.Logger;
import org.jdom.Document;
import java.util.HashMap;

/**
 * <p>Title: Framework </p>
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
public class QueryConfigByXml implements IQueryConfig, IJdomConfig
{
  protected static final Logger logger = Logger.getLogger(QueryConfigByXml.class);

  /**
   * Xml配置节点
   */
  private Element eleConfig;

  private HashMap queryMap;

  /**
   * 获取查询配置中的所有查询
   * @return IQuery[]
   */
  public HashMap getQueries()
  {
    Assert.notNull(eleConfig, "通过Xml配置的查询配置没有设置Xml配置节点");

    //初始化查询Map
    this.queryMap = new HashMap();

    //读取查询配置并解析所有的查询文件
    Iterator iterator = eleConfig.getChildren("configFilePath").iterator();
    Element eleConfigFilePath = null;
    String configFilePath = null;
    while(iterator.hasNext())
    {
      eleConfigFilePath = (Element)iterator.next();
      //获取查询文件
      configFilePath = eleConfigFilePath.getTextTrim();
      try
      {
        //获取查询配置文件的URL
        URL configFileUrl = new URL(QueryManager.getConfigsFileUrl(), configFilePath);
        //通过单个查询文件获取查询
        getQueries(configFileUrl);
      }
      catch(MalformedURLException ex)
      {
        logger.error("通用查询：查询配置文件（" + configFilePath + "）URL格式错误");
      }
    }
    return this.queryMap;
  }

  /**
   * 根据单个查询文件获取查询
   * @param configFileUrl URL
   * @return boolean
   */
  public boolean getQueries(URL configFileUrl)
  {
    Document configFileDoc = null;
    try
    {
      configFileDoc = JdomUtils.parseXml(configFileUrl);
    }
    catch(RuntimeException ex)
    {
      logger.error("通用查询：查询文件（" + configFileUrl.getPath() + "）读取失败", ex);
      return false;
    }
    Element eleQueries = configFileDoc.getRootElement();
    Assert.notNull(eleQueries, "查询文件" + configFileUrl.getPath() + "格式错误");
    logger.debug("通用查询：读取查询文件：" + configFileUrl.getPath());

    //读取查询配置并实例化相应的查询配置类
    Iterator iterator = eleQueries.getChildren("query").iterator();
    Element eleQuery = null;
    String queryClass = null;
    String queryName = null;
    IQuery query = null;
    while(iterator.hasNext())
    {
      eleQuery = (Element)iterator.next();
      queryName = JdomUtils.getAttributeValue(eleQuery, "name");
      queryClass = JdomUtils.getAttributeValue(eleQuery, "class");
      Object objQuery = null;
      try
      {
        objQuery = ClassUtils.newInstance(queryClass);
      }
      catch(RuntimeException ex)
      {
        logger.error("通用查询：查询（" + queryName + "）对应的类（" + queryClass + "）实例化失败", ex);
        continue;
      }
      if(objQuery instanceof IQuery)
      {
        query = (IQuery)objQuery;
        //设置查询名称
        query.setQueryName(queryName);
        //设置查询文件URL
        query.setConfigFileUrl(configFileUrl);
        //设置Xml查询配置节点
        if(objQuery instanceof IJdomConfig)
          ((IJdomConfig)objQuery).setEleConfig(eleQuery);
        //查询初始化并放置到查询Map中
        if(query.init())
          queryMap.put(queryName, query);
      }
      else
      {
        logger.error("通用查询：查询配置文件（" + configFileUrl.getPath() + "）中的Query（"
            + queryName + "）中的查询类（" + queryClass + "）不是IQuery");
      }
    }
    return true;
  }

  /**
   * 设置Xml配置节点
   * @param eleConfig Element
   */
  public void setEleConfig(Element eleConfig)
  {
    this.eleConfig = eleConfig;
  }
}
