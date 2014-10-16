package com.xwtech.framework.query.bo;

import javax.servlet.http.HttpServletRequest;
import org.jdom.Element;
import org.apache.log4j.Logger;
import com.xwtech.framework.query.QueryException;
import com.xwtech.framework.query.QueryExhibit;
import java.net.URL;
import org.jdom.Document;
import com.xwtech.framework.pub.utils.CalTime;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.utils.LogUtils;
import com.xwtech.framework.pub.utils.JdomUtils;
import com.xwtech.framework.pub.utils.Assert;
import com.xwtech.framework.pub.utils.ClassUtils;

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
public class QueryByXml implements IQuery, IJdomConfig
{
  protected static final Logger logger = Logger.getLogger(QueryByXml.class);

  private Element eleConfig;

  private String queryName;

  private URL configFileUrl;

  private IQueryCondition queryCondition;

  private static final String DEFAULT_QUERY_VALUE_FETCH_CLASS = "com.xwtech.framework.query.bo.QueryValueFetchByJdbcSql";

  private QueryValueFetchByJdbcSql queryValueFetch;

  /**
   * 运行一个查询
   * @param request HttpServletRequest
   * @return String
   */
  public String getHtml(HttpServletRequest request)
  {
    CalTime calTime = new CalTime();
    calTime.begin();
    //结果获取
    QueryValue queryValue = this.queryValueFetch.run(request);
    queryValue.calTimeSb.append("总结果获取时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

    //获取条件区域
    calTime.begin();
    String queryConditionString = queryCondition.getHtml(request);
    queryConditionString = StringUtils.replace(queryConditionString, "<!---->", "");
    //结果呈现
    Element elementResultView = eleConfig.getChild("resultview");
    if(elementResultView == null)
      throw new QueryException(this, "没有配置resultview");
    QueryExhibit queryExhibit = new QueryExhibit(request);
    queryExhibit.setConfigRoot(elementResultView);
    queryExhibit.setQueryValue(queryValue);

    queryExhibit.setConditionHTML(queryConditionString);
    queryExhibit.setPageSize(queryValue.pageSize);
    queryExhibit.setColsTitle(queryValue.title);
    queryExhibit.setDataList(queryValue.dataList);
    queryExhibit.setSumList(queryValue.dataSum);
    queryExhibit.setRecordCount(queryValue.recordCount);
    queryExhibit.setHasNextPage(queryValue.hasNextPage);
    queryExhibit.setCurrentPageNo(queryValue.pageNo);
    //缓存不允许排序
    if(this.queryValueFetch.isNeedCache())
    {
      queryExhibit.setForceNoSort();
    }
    //随机不允许跳转
    if(queryValueFetch.isNeedRadom())
    {
      queryExhibit.setForceNoJump();
    }
    queryValue.calTimeSb.append("总后台运行时间（除去结果获取时间）：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

    calTime.begin();
    String html = null;
    try
    {
      html = queryExhibit.toHTML();
    }
    catch(Exception ex)
    {
      throw new QueryException(this, "通用查询：结果呈现错误", ex);
    }
    queryValue.calTimeSb.append("前台展现时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);
    LogUtils.logTimer(logger,queryValue.calTimeSb.toString());
    return html;
  }

  /**
   * 设置查询的名称
   * @param queryName String
   */
  public void setQueryName(String queryName)
  {
    this.queryName = queryName;
  }

  /**
   * 设置配置的URL
   * @param configFileUrl URL
   */
  public void setConfigFileUrl(URL configFileUrl)
  {
    this.configFileUrl = configFileUrl;
  }

  /**
   * 获取查询的名称
   * @return String
   */
  public String getQueryName()
  {
    return this.queryName;
  }

  /**
   * 获取配置的URL
   * @return URL
   */
  public URL getConfigFileUrl()
  {
    return configFileUrl;
  }

  /**
   * 获取查询操作的对象
   * @return QueryValueFetchByJdbcSql
   */
  public QueryValueFetchByJdbcSql getQueryValueFetch()
  {
    return queryValueFetch;
  }

  /**
   * 获取查询条件对象
   * @return IQueryCondition
   */
  public IQueryCondition getQueryCondition()
  {
    return queryCondition;
  }

  /**
   * 设置查询的配置
   * @param eleConfig Element
   */
  public void setEleConfig(Element eleConfig)
  {
    this.eleConfig = eleConfig;
  }

  /**
   * 重新载入配置
   * @return boolean
   */
  public boolean reload()
  {
    if(configFileUrl == null)
      return false;
    Document configFileDoc = JdomUtils.parseXml(configFileUrl);
    Element eleQueries = configFileDoc.getRootElement();
    Assert.notNull(eleQueries, "查询文件" + configFileUrl.getPath() + "格式错误");
    logger.debug("通用查询：读取查询文件：" + configFileUrl.getPath());
    Element eleQuery = JdomUtils.getChildByAttribute(eleQueries, "query", "name", this.queryName);
    if(eleQuery == null)
      return false;
    this.eleConfig = eleQuery;
    return this.init();
  }

  /**
   * 初始化操作
   * @return boolean
   */
  public boolean init()
  {
    Assert.notNull(this.eleConfig, "通过Xml配置的查询没有设置Xml节点");
    this.queryCondition = null;
    this.queryValueFetch = null;

    //解析查询条件配置
    Element eleQueryCondition = eleConfig.getChild("querycondition");
    IQueryCondition queryCondition = null;
    String queryConditionClass = null;
    if(eleQueryCondition == null)
    {
      eleQueryCondition = new Element("querycondition");
      queryConditionClass = "com.xwtech.framework.query.bo.QueryConditionByXml";
//      QueryManager.logError(logger, this, "没有配置querycondition");
//      return false;
    }
    else
    {
      queryConditionClass = JdomUtils.getAttributeValue(eleQueryCondition, "class");
    }


    Object objQueryCondition = null;
    try
    {
      objQueryCondition = ClassUtils.newInstance(queryConditionClass);
    }
    catch(RuntimeException ex)
    {
      QueryManager.logError(logger, this, "条件配置类（" + queryConditionClass + "）实例化失败");
      return false;
    }

    if(objQueryCondition instanceof IQueryCondition)
    {
      queryCondition = (IQueryCondition)objQueryCondition;
      queryCondition.setQuery(this);
      if(queryCondition instanceof IJdomConfig)
        ((IJdomConfig)objQueryCondition).setEleConfig(eleQueryCondition);
      if(!queryCondition.init())
        return false;
      this.queryCondition = queryCondition;
    }

    //获取结果
    Element eleValueFetch = eleConfig.getChild("valuefetch");
    if(eleValueFetch == null)
    {
      QueryManager.logError(logger, this, "没有配置valuefetch");
      return false;
    }
    String queryValueFetchClass = JdomUtils.getAttributeValue(eleValueFetch, "class", DEFAULT_QUERY_VALUE_FETCH_CLASS);
    Object objQueryValueFetch = null;
    try
    {
      objQueryValueFetch = ClassUtils.newInstance(queryValueFetchClass);
    }
    catch(RuntimeException ex)
    {
      QueryManager.logError(logger, this, "结果获取类（" + queryValueFetchClass + "）实例化失败");
      return false;
    }

    if(!(objQueryValueFetch instanceof QueryValueFetchByJdbcSql))
    {
      QueryManager.logError(logger, this, "valuefetch对应的类"
          + queryValueFetchClass + "不是一个QueryValueFetchByJdbcSql");
      return false;
    }
    QueryValueFetchByJdbcSql queryValueFetch = (QueryValueFetchByJdbcSql)objQueryValueFetch;
    if(queryValueFetch instanceof IJdomConfig)
      ((IJdomConfig)queryValueFetch).setEleConfig(eleValueFetch);
    if(!queryValueFetch.init())
      return false;
    queryValueFetch.setQuery(this);
    this.queryValueFetch = queryValueFetch;

    //结果呈现
    Element elementResultView = eleConfig.getChild("resultview");
    if(eleValueFetch == null)
    {
      QueryManager.logError(logger, this, "没有配置resultview");
      return false;
    }
    //QueryExhibit queryExhibit = new QueryExhibit(request);

    return true;
  }
}
