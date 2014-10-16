package com.xwtech.framework.query.bo;

import org.jdom.Element;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.xwtech.framework.pub.exception.JdbcException;
import java.util.Iterator;
import java.util.HashMap;
import com.xwtech.framework.pub.web.SessionNameConstants;
import java.util.Collections;
import com.xwtech.framework.pub.tag.MultiCheckbox;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import com.xwtech.framework.pub.tag.Checkbox;
import com.xwtech.framework.pub.utils.JdomUtils;
import com.xwtech.framework.pub.utils.MacroReplace;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.utils.CalTime;
import com.xwtech.framework.pub.utils.RequestUtils;
import com.xwtech.framework.pub.utils.ClassUtils;
import com.xwtech.framework.pub.utils.SqlString;
import com.xwtech.framework.pub.utils.JdbcUtils;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.utils.ArrayUtils;
import com.xwtech.framework.pub.utils.ArrayListUtils;

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
public class QueryValueFetchByJdbcSql implements IQueryValueFetch, IJdomConfig, IValueFetch
{
  /**
   * 日志对象
   */
  protected static final Logger logger = Logger.getLogger(QueryValueFetchByJdbcSql.class);

  /**
   * Xml配置文件中valuefetch节点
   */
  private Element eleConfig;

  /**
   * 查询对象
   */
  private IQuery query;

  /**
   * 缺省页面大小
   */
  private static final int DEFAULT_PAGE_SIZE = 10;

  /**
   * 页面大小
   */
  private int pageSize;

  /**
   * 代理类名称（从配置文件中获取）






   */
  private String agentClassName;

  /**
   * 代理类






   */
  private IValueFetch agentClass;

  /**
   * 是否首次显示
   */
  private boolean firstShow = true;

  /**
   * 配置文件中的SqlSelect（Sql中的Select子句）






   */
  private String sqlSelect;

  /**
   * 配置文件中的SqlSelect（Sql中的From、Where子句）






   */
  private SqlString sqlStringFromWhere;

  /**
   * 配置文件中的SqlSum（统计Sql中的Select子句）






   */
  private String sqlSum;

  /**
   * 配置文件中的SqlOrder（Sql中的Order子句）






   */
  private String sqlOrder;

  /**
   * 轴的集合
   */
  private ArrayList groupList;
  /**
   * X轴、Y轴、Z轴（备选轴）的集合
   */
  private ArrayList groupListY, groupListX, groupListSelect;
  /**
   * 钻取URL
   */
  private String drillURL;
  /**
   * 轴的HashMap
   */
  private HashMap groupMap;
  /**
   * 轴的关系
   */
  private ArrayList groupRelationList;
  /**
   * 用于统计量的MultiCheckbox组件
   */
  private MultiCheckbox sqlGroupSelect;

  /**
   * 是否需要统计






   */
  private boolean needCount;
  /**
   * 缓存HashMap
   */
  private static HashMap cacheHashMap = new HashMap();
  //是否需要缓存






  private boolean needCache = false;
  /**
   * 应用级的缓存
   */
  private static final int CACHE_APPLICATION = 1;
  /**
   * Session级的缓存
   */
  private static final int CACHE_SESSION = 2;
  /**
   * 缓存范围
   */
  private int cacheScope = CACHE_APPLICATION;

  /**
   * 是否需要随机






   */
  private boolean needRadom = false;
  /**
   * 缺省缓存限制
   */
  private static final int DEFAULT_CACHE_LIMIT = 1000;
  /**
   * 缓存限制
   */
  private int cacheLimit = DEFAULT_CACHE_LIMIT;

  /**
   * 代替宏的HashMap
   */
  private HashMap replaceMap;

  /**
   * 是否需要X轴总计
   */
  public boolean needTotalX=true;
  /**
   * 是否需要Y轴总计
   */
  public boolean needTotalY=true;

  /**
   * Sql配置
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
  class SqlConfig
  {
    /**
     * 配置文件中的SqlSelect（Sql中的Select子句）






     */
    private String sqlSelect = "";
    //    private SqlString sqlStringSelect = null;

    /**
     * 配置文件中的SqlSelect的String（Sql中的From、Where子句）






     */
    private String sqlFromWhere = "";
    /**
     * 配置文件中的SqlSelect的SqlString（Sql中的From、Where子句）






     */
    private SqlString sqlStringFromWhere = null;

    /**
     * 配置文件中的SqlSum（统计Sql中的Select子句）






     */
    private String sqlSum = "";

    /**
     * 配置文件中的SqlOrder（Sql中的Order子句）






     */
    private String sqlOrder = "";

    /**
     * 实际运行时生成的SQL语句，分别为数据、总计、条数统计的SQL
     */
    private String sqlData = "", sqlDataSum = "", sqlCount = "";

    private boolean isOverrideCache = false;

    public boolean getIsOverrideCache() {
      return isOverrideCache;
    }

  }

  /**
   * 初始化：读取配置信息
   * @return boolean
   */
  public boolean init()
  {
    //页面大小
    this.pageSize = JdomUtils.getAttributeIntValue(eleConfig, "pageSize", DEFAULT_PAGE_SIZE);

    //从配置文件中读取代理类名并实例化代理类






    this.agentClassName = JdomUtils.getAttributeValue(eleConfig, "agentClass", "");
    if(agentClassName.length() > 0)
    {
      try
      {
        this.agentClass = (IValueFetch)ClassUtils.newInstance(agentClassName);
      }
      catch(RuntimeException ex)
      {
        QueryManager.logError(logger, this.query, "结果获取代理类（" +agentClassName + "）实例化失败");
      }
    }
    if(agentClass == null)
      agentClass = this;

    //读取是否首次显示
    this.firstShow = JdomUtils.getAttributeBooleanValue(eleConfig, "firstShow", true);

    //读取Sql配置信息
    Element eleSql = eleConfig.getChild("sql");
    if(eleSql == null)
    {
      QueryManager.logError(logger, query, "没有配置valuefetch中的sql");
      return false;
    }

    //读取Cache配置
    Element eleCache = eleConfig.getChild("cache");
    if(eleCache != null)
    {
      this.needCache = true;
      this.needRadom = JdomUtils.getAttributeBooleanValue(eleCache, "radom", false);
      this.cacheLimit = JdomUtils.getAttributeIntValue(eleCache, "radom", DEFAULT_CACHE_LIMIT);
    }

    //读取Sql配置
    this.sqlSelect = StringUtils.trimToEmpty(eleSql.getChildText("SqlSelect"));
    this.sqlStringFromWhere = new SqlString(StringUtils.trimToEmpty(eleSql.getChildText("SqlFromWhere")));
    this.sqlSum = StringUtils.trimToEmpty(eleSql.getChildTextTrim("SqlSum"));
    this.sqlOrder = StringUtils.trimToEmpty(eleSql.getChildTextTrim("SqlOrder"));

    //读取Group定义
    Element eleSqlGroup = eleConfig.getChild("sql").getChild("SqlGroup");
    if(eleSqlGroup != null)
    {
      drillURL = JdomUtils.getAttributeValue(eleSqlGroup, "drillURL");

      this.needTotalX = JdomUtils.getAttributeBooleanValue(eleSqlGroup,"needTotalX",true);
      this.needTotalY = JdomUtils.getAttributeBooleanValue(eleSqlGroup,"needTotalY",true);

      //统计缺省第一次不运行
      firstShow = false;

      this.pageSize = -1;
      groupList = new ArrayList();
      groupMap = new HashMap();
      groupListY = new ArrayList();
      groupListX = new ArrayList();
      groupListSelect = new ArrayList();
      Iterator iterator = eleSqlGroup.getChildren("Group").iterator();
      while(iterator.hasNext())
      {
        Element eleGroup = (Element)iterator.next();
        String groupName = JdomUtils.getAttributeValue(eleGroup, "groupName");
        String groupLabel = JdomUtils.getAttributeValue(eleGroup, "groupLabel");
        String metadatasql = JdomUtils.getAttributeValue(eleGroup, "metadatasql");
        String macrovalue = JdomUtils.getAttributeValue(eleGroup, "macrovalue");
        String position = JdomUtils.getAttributeValue(eleGroup, "position");
        Group group = new Group(groupName, groupLabel, metadatasql, macrovalue, position);
        if(JdomUtils.getAttributeBooleanValue(eleGroup, "subTotal", false))
          group.needSubTotal = true;
        group.needDrill = JdomUtils.getAttributeBooleanValue(eleGroup, "drill", false);
        if(!group.metadatasql.equals("") && !group.metadataSqlString.needParse())
          group.metadata = JdbcUtils.queryForList(metadatasql);
        if(StringUtils.contains(position, "y"))
          groupListY.add(group);
        else
          if(StringUtils.contains(position, "x"))
            groupListX.add(group);
          else
            groupListSelect.add(group);
        groupMap.put(group.groupName, group);
        Collections.sort(groupListY);
        Collections.sort(groupListX);
        Collections.sort(groupListSelect);
      }
      groupList.addAll(groupListX);
      groupList.addAll(groupListY);

      //读取统计量配置






      ArrayList sqlGroupSelectList = new ArrayList();
      sqlGroupSelect = new MultiCheckbox();
      sqlGroupSelect.name = "sqlGroupSelect";
      Iterator iteratorSqlGroupSelect = eleConfig.getChild("sql").getChild("SqlGroupSelect").getChildren("GroupSelect").iterator();
      while(iteratorSqlGroupSelect.hasNext())
      {
        Element eleGroupSelect = (Element)iteratorSqlGroupSelect.next();
        String label = JdomUtils.getAttributeValue(eleGroupSelect, "label");
        String value = JdomUtils.getAttributeValue(eleGroupSelect, "value");
        String addvalue = JdomUtils.getAttributeValue(eleGroupSelect, "addvalue");
        Checkbox groupSelect = new Checkbox("", label, value, addvalue, true);
        sqlGroupSelectList.add(groupSelect);
      }
      this.sqlGroupSelect.metadata = sqlGroupSelectList;

      //读取Group关系定义
      Element eleSqlGroupRelation = eleConfig.getChild("sql").getChild("SqlGroupRelation");
      groupRelationList = new ArrayList();
      if(eleSqlGroupRelation != null)
      {
        iterator = eleSqlGroupRelation.getChildren("GroupRelation").iterator();
        while(iterator.hasNext())
        {
          Element eleGroupRelation = (Element)iterator.next();
          String groupName1 = JdomUtils.getAttributeValue(eleGroupRelation, "groupName1");
          String groupName2 = JdomUtils.getAttributeValue(eleGroupRelation, "groupName2");
          String relationsql = JdomUtils.getAttributeValue(eleGroupRelation, "relationsql");
          ArrayList relation = JdbcUtils.queryForList(relationsql);
          GroupRelation groupRelation = new GroupRelation(groupName1, groupName2, relation);
          groupRelationList.add(groupRelation);
        }
      }
    }

    if((sqlSelect.length() == 0 && groupList == null) || sqlStringFromWhere.getSql().length() == 0)
    {
      QueryManager.logError(logger, query, "没有配置valuefetch中的sql中的SqlSelect或者SqlFromWhere");
      return false;
    }
    needCount = JdomUtils.getAttributeBooleanValue(eleSql, "count", true);

    //读取宏替换配置






    Element elementReplaces = eleConfig.getChild("sql").getChild("replaces");
    replaceMap = new HashMap();
    if(elementReplaces != null)
    {
      Iterator iterator = elementReplaces.getChildren().iterator();
      while(iterator.hasNext())
      {
        Element elementReplace = (Element)iterator.next();

        String replacename = JdomUtils.getAttributeValue(elementReplace, "replacename", "");
        ArrayList paraReplaceList = new ArrayList();
        MacroReplace replace = null;

        if(elementReplace.getAttributeValue("replacevalue") == null) //配置放在下面的parareplace节点
        {
          Iterator iteratorParaReplace = elementReplace.getChildren().iterator();
          while(iteratorParaReplace.hasNext())
          {
            Element elementParaReplace = (Element)iteratorParaReplace.next();
            String conditionExpr = JdomUtils.getAttributeValue(elementParaReplace, "conditionExpr", "");
            String paraname = JdomUtils.getAttributeValue(elementParaReplace, "paraname", "");
            String paravalue = JdomUtils.getAttributeValue(elementParaReplace, "paravalue", "");
            String relation = JdomUtils.getAttributeValue(elementParaReplace, "relation", "!=");
            String replacevalue = JdomUtils.getAttributeValue(elementParaReplace, "replacevalue", "");
            replace = new MacroReplace(conditionExpr, paraname, paravalue, relation, replacevalue);
            paraReplaceList.add(replace);
          }
          replaceMap.put(replacename, paraReplaceList);
        }
        else //配置就放在replace节点
        {
          String conditionExpr = JdomUtils.getAttributeValue(elementReplace, "conditionExpr", "");
          String paraname = JdomUtils.getAttributeValue(elementReplace, "paraname", "");
          String paravalue = JdomUtils.getAttributeValue(elementReplace, "paravalue", "");
          String relation = JdomUtils.getAttributeValue(elementReplace, "paravalue", "!=");
          String replacevalue = JdomUtils.getAttributeValue(elementReplace, "replacevalue", "");
          replace = new MacroReplace(conditionExpr, paraname, paravalue, relation, replacevalue);
          paraReplaceList.add(replace);
          replaceMap.put(replacename, paraReplaceList);
        }
      }
    }
    return true;
  }

  /**
   * 运行查询
   * @param request HttpServletRequest
   * @return QueryValue
   */
  public QueryValue run(HttpServletRequest request)
  {
    CalTime calTime = new CalTime();
    CalTime calTimeSub = new CalTime();
    //获取结果
    QueryValue queryValue = new QueryValue();
    request.setAttribute("queryValue", queryValue);

    //获取运行次数
    queryValue.queryName = this.getQuery().getQueryName();
    queryValue.lastQueryName = RequestUtils.getStringParameter(request, "QueryName", "");
    if(queryValue.queryName.equals(queryValue.lastQueryName))
      queryValue.runTime = 1;
    else
    {
      if(queryValue.lastQueryName.equals(""))
        queryValue.runTime = 0;
      else
        queryValue.runTime = -1;
    }

    //解析参数中的页面大小和页数






    queryValue.pageSize = RequestUtils.getIntParameter(request, "query_pagesize", 0);
    if(queryValue.pageSize == 0)
      queryValue.pageSize = this.pageSize;
    queryValue.pageNo = RequestUtils.getIntParameter(request, queryValue.queryName + "_query_pageno", 1);

    //将配置的Sql元素传送给SqlConfig
    SqlConfig sqlConfig = new SqlConfig();
    SqlString sqlStringSelect = new SqlString(this.sqlSelect);
    sqlConfig.sqlSelect = sqlStringSelect.getParseSql(request, null);
    //sqlConfig.sqlStringSelect = this.sqlStringSelect;
    sqlConfig.sqlStringFromWhere = this.sqlStringFromWhere;

    //统计解析
    String sqlGroup = "";
    String sqlGroupSelectGroup = "";
    queryValue.drillURL = drillURL;
    queryValue.needTotalX = this.needTotalX;
    queryValue.needTotalY = this.needTotalY;

    if(groupList != null)
    {
      //统计没有分页
      queryValue.pageSize = -1;
      StringBuffer sqlGroupSb = new StringBuffer();
      StringBuffer sqlGroupSelectGroupSb = new StringBuffer();
      queryValue.groupRalationList = this.groupRelationList;
      queryValue.groupList = new ArrayList();

      //初始化XYZ轴






      if(queryValue.runTime != 1) //第一次运行和从别的查询过来






      {
        queryValue.groupListY = (ArrayList)this.groupListY.clone();
        queryValue.groupListX = (ArrayList)this.groupListX.clone();
        queryValue.groupListSelect = (ArrayList)this.groupListSelect.clone();
        queryValue.groupList = (ArrayList)this.groupList.clone();
      } //end 初始化XYZ轴 if
      else
      {
        //从Request中获取用户选择的X、Y轴






        queryValue.groupListY = new ArrayList();
        queryValue.groupListX = new ArrayList();
        queryValue.groupListSelect = new ArrayList();

        //Y轴






        String[] groupYName = RequestUtils.getStringParameters(request, "groupYName");
        String[] checkGroupYSubTotal = RequestUtils.getStringParameters(request, "checkGroupYSubTotal");
        for(int i = 0; i < groupYName.length; i++)
        {
          Object obj = groupMap.get(groupYName[i]);
          if(obj != null)
          {
            Group group = (Group)((Group)obj).clone();
            group.position = 'y' + String.valueOf(i + 1);
            if(ArrayUtils.contains(checkGroupYSubTotal, groupYName[i]))
              group.needSubTotal = true;
            else
              group.needSubTotal = false;
            queryValue.groupListY.add(group);
          }
        }

        //X轴






        String[] groupXName = RequestUtils.getStringParameters(request, "groupXName");
        for(int i = 0; i < groupXName.length; i++)
        {
          Object obj = groupMap.get(groupXName[i]);
          if(obj != null)
          {
            Group group = (Group)((Group)obj).clone();
            group.position = 'x' + String.valueOf(i + 1);
            queryValue.groupListX.add(group);
          }
        }

        //Z轴






        String[] groupList = RequestUtils.getStringParameters(request, "groupZName");
        for(int i = 0; i < groupList.length; i++)
        {
          Object obj = groupMap.get(groupList[i]);
          if(obj != null)
          {
            Group group = (Group)((Group)obj).clone();
            group.position = String.valueOf(i + 1);
            queryValue.groupListSelect.add(group);
          }
        }
        queryValue.groupList.addAll(queryValue.groupListX);
        queryValue.groupList.addAll(queryValue.groupListY);
      } //end 初始化XYZ轴 else

      if(!(queryValue.runTime == 0 && !firstShow))
      {
        for(int i = 0; i < queryValue.groupList.size(); i++)
        {
          Group group = (Group)queryValue.groupList.get(i);
          if(group.metadata.size() == 0 && !group.metadatasql.equals(""))
          {
            group.metadata = JdbcUtils.queryForList(group.metadataSqlString.getParseSql(request, this.replaceMap));
          }
          sqlGroupSb.append(group.macrovalue).append(',');
          sqlGroupSelectGroupSb.append(group.macrovalue).append(" as ").append(group.groupLabel).append(',');
        }
        sqlGroup = sqlGroupSb.deleteCharAt(sqlGroupSb.length() - 1).toString();
        sqlGroupSelectGroup = sqlGroupSelectGroupSb.toString();
      }
    }
    //如果不是第1次并配置为第1次不运行
    if(queryValue.runTime == 0 && !firstShow)
    {
      queryValue.title = new String[0];
    }
    else
    {
      queryValue.dataList = new ArrayList();
      queryValue.allDataList = new ArrayList();
      //进行sqlFromWhere的解析







      sqlConfig.sqlFromWhere = sqlConfig.sqlStringFromWhere.getParseSql(request, this.replaceMap);
//    sqlConfig.sqlSelect = sqlConfig.sqlStringSelect.getParseSql(request, this.replaceMap);

      if(sqlGroup.length() > 0)
        sqlConfig.sqlData = "select " + sqlGroupSelectGroup + sqlGroupSelect.getSql(request) + " " + sqlConfig.sqlFromWhere
                            + " group by " + sqlGroup;
      else
      {
        sqlConfig.sqlData = "select " + sqlConfig.sqlSelect + " " + sqlConfig.sqlFromWhere;
        sqlConfig.sqlSum = this.sqlSum;
        if(sqlConfig.sqlSum.length() > 0)
          sqlConfig.sqlDataSum = "select " + sqlConfig.sqlSum + " " + sqlConfig.sqlFromWhere;
        sqlConfig.sqlCount = "select count(*) from (" + sqlConfig.sqlData + ")"; // + sqlConfig.sqlFromWhere;
        String query_sortcol = RequestUtils.getStringParameter(request, "query_sortcol", "");
        sqlConfig.sqlOrder = this.sqlOrder;
        if(!StringUtils.isBlank(query_sortcol))
        {
          sqlConfig.sqlOrder = " order by " + query_sortcol + " " + RequestUtils.getStringParameter(request, "query_sortdir", "");
        }
        sqlConfig.sqlOrder = StringUtils.trimToEmpty(sqlConfig.sqlOrder);
      }

      //从数据库获取数据
      calTime.begin();
      if(needCache && agentClass.fetchCacheResultFromDb(eleConfig, sqlConfig, request, queryValue))
      {
        int position = queryValue.pageSize * (queryValue.pageNo - 1);
        int maxsize = position + queryValue.pageSize;
        if(queryValue.allDataList.size() <= maxsize)
          maxsize = queryValue.allDataList.size();
        else
        {
          queryValue.hasNextPage = true;
        }
        if(queryValue.pageSize == -1)
        {
          position = 0;
          maxsize = queryValue.allDataList.size();
        }
        for(int i = position; i < maxsize; i++)
        {
          queryValue.dataList.add(queryValue.allDataList.get(i));
        }
      }
      else
      {
        calTimeSub.begin();
        agentClass.fetchPageResultFromDb(eleConfig, sqlConfig, request, queryValue);
        queryValue.calTimeSb.append("从数据库获取数据时间：").append(calTimeSub.endString()).append(StringUtils.LINE_SEPARATOR);
      }
      queryValue.calTimeSb.append("总共获取数据时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);
    }
    return queryValue;
  }

  /**
   * 从数据库获取一个页面的结果
   * @param eleConfig Element
   * @param sqlConfig SqlConfig
   * @param request HttpServletRequest
   * @param queryValue QueryValue
   */
  public void fetchPageResultFromDb(Element eleConfig, SqlConfig sqlConfig, HttpServletRequest request, QueryValue queryValue)
  {
    DataSource ds = BaseFrameworkApplication.getBaseJdbcDAO().getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    String sql = null;
    try
    {
      conn = ds.getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      sql = sqlConfig.sqlData + sqlConfig.sqlOrder;
      resultSet = stmt.executeQuery(sql);
      if(logger.isDebugEnabled())
      {
        logger.debug("通用查询：查询" + this.query.getQueryName() + "获取数据，Sql为：" + StringUtils.LINE_SEPARATOR);
        logger.debug(sql.replaceAll("[\n][ \t]*", "\n").replaceAll("[\n]+", "\n"));
      }
      //获取标题
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int columnCount = rsmd.getColumnCount();
      queryValue.title = new String[columnCount];
      queryValue.dataType = new int[columnCount];
      for(int i = 0; i < rsmd.getColumnCount(); i++)
      {
        queryValue.dataType[i] = rsmd.getColumnType(i + 1);
        queryValue.title[i] = rsmd.getColumnLabel(i + 1);
      }
      //TODO 首次是否查询
      if(true)
      {
        //计算位置并跳转






        int position = 0;
        if(queryValue.pageSize > 0)
        {
          position = queryValue.pageSize * (queryValue.pageNo - 1);
          if(position > 0)
            resultSet.absolute(position);
        }
        //查询数据
        int record = 0;
        while((record < queryValue.pageSize || queryValue.pageSize == -1) && resultSet.next())
        {
          String[] data = new String[columnCount];
          for(int i = 0; i < rsmd.getColumnCount(); i++)
          {
            data[i] = resultSet.getString(i + 1);
          }
          queryValue.dataList.add(data);
          record++;
        }
        if(resultSet.next())
          queryValue.hasNextPage = true;
        resultSet.close();

        //查询统计数据
        if(sqlConfig.sqlDataSum.length() > 0)
        {
          sql = sqlConfig.sqlDataSum;
          resultSet = stmt.executeQuery(sql);
          rsmd = resultSet.getMetaData();
          if(resultSet.next())
          {
            queryValue.dataSum = new String[queryValue.title.length];
            for(int i = 0; i < rsmd.getColumnCount() && i < queryValue.dataSum.length; i++)
            {
              queryValue.dataSum[i] = resultSet.getString(i + 1);
            }
          }
        }
        if(needCount && sqlConfig.sqlCount.length() > 0)
        {
          sql = sqlConfig.sqlCount;
          resultSet = stmt.executeQuery(sql);
          if(resultSet.next())
          {
            queryValue.recordCount = resultSet.getInt(1);
          }
        }

        if(queryValue.groupList != null)
        {
          //Group从数据中获取MetaData
          for(int i = 0; i < queryValue.groupList.size(); i++)
          {
            Group group = (Group)queryValue.groupList.get(i);
            //仅对没有metadata的进行处理（没有配置metadata获取方式）







            if(group.metadata.size() == 0)
            {
              ArrayList metadataList = new ArrayList();
              //在数据集中循环






              for(int j = 0; j < queryValue.dataList.size(); j++)
              {
                String[] data = (String[])queryValue.dataList.get(j);

                //数组越界检查






                if(data.length < i)
                  continue;

                //在metadataList中查询是否存在






                boolean exist = false;
                for(int k = 0; k < metadataList.size(); k++)
                {
                  if(((String[])metadataList.get(k))[0].equals(data[i]))
                  {
                    exist = true;
                    break;
                  }
                }

                //如果metadataList中没有就插入
                if(!exist && data[i] != null)
                  metadataList.add(new String[]
                      {data[i]});
              }
              group.metadata = metadataList;
            } //if(group.metadata.size() == 0)
          } //for(int i = 0; i < queryValue.groupList.size(); i++)
        } //Group从数据中获取MetaData
      }
    }
    catch(SQLException ex)
    {
      throw new JdbcException("通用查询：Sql执行错误", sql, ex);
    }
    finally
    {
      JdbcUtils.close(resultSet, stmt, conn);
    }
  }

  /**
   * 获取需要缓存的数据结果集






   * @param eleConfig Element
   * @param sqlConfig SqlConfig
   * @param request HttpServletRequest
   * @param queryValue QueryValue
   * @return boolean
   */
  public boolean fetchCacheResultFromDb(Element eleConfig, SqlConfig sqlConfig, HttpServletRequest request, QueryValue queryValue)
  {
    CalTime calTime = new CalTime();
    CalTime calTimeTotal = new CalTime();

    calTimeTotal.begin();
    calTime.begin();
    DataSource ds = BaseFrameworkApplication.getBaseJdbcDAO().getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    String sql = null;
    queryValue.calTimeSb.append("初始化时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

    try
    {
      //根据SqlData从缓存获取缓存结果






      calTime.begin();
      HashMap sqlCacheMap = null;
      if(cacheScope == this.CACHE_APPLICATION)
        sqlCacheMap = cacheHashMap;
      if(cacheScope == this.CACHE_SESSION)
      {
        Object objSqlCacheMap = SessionUtils.getObjectAttribute(request, SessionNameConstants.SQL_CACHE_MAP);
        if(objSqlCacheMap != null)
          sqlCacheMap = (HashMap)objSqlCacheMap;
        else
        {
          sqlCacheMap = new HashMap();
          SessionUtils.setObjectAttribute(request, SessionNameConstants.SQL_CACHE_MAP, sqlCacheMap);
        }
      }
      Object objCacheQueryValue = sqlCacheMap.get(sqlConfig.sqlData);
      queryValue.calTimeSb.append("根据SqlData从缓存获取缓存结果时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

      if(objCacheQueryValue != null && objCacheQueryValue instanceof QueryValue && sqlConfig.isOverrideCache==false)
      {
        calTime.begin();
        QueryValue cacheQueryValue = (QueryValue)objCacheQueryValue;
        queryValue.dataSum = cacheQueryValue.dataSum;
        queryValue.allDataList = cacheQueryValue.allDataList;
        queryValue.recordCount = cacheQueryValue.recordCount;
        queryValue.title = cacheQueryValue.title;
        queryValue.calTimeSb.append("根据SqlData缓存结果赋值时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);
      }
      else
      {
        calTime.begin();
        conn = ds.getConnection();
        queryValue.calTimeSb.append("获取连接时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

        calTime.begin();
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        queryValue.calTimeSb.append("创建Statement时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

        //获取Count
        /*
                 sql = sqlConfig.sqlCount;
                 resultSet = stmt.executeQuery(sql);
                 if(resultSet.next())
                 {
          queryValue.recordCount = resultSet.getInt(1);
                 }
                 if(queryValue.recordCount > cacheLimit)
          return false;
                 LogUtils.logTimer(logger,"通用查询：获取数据：Count时间" + calTime.endString());
         */

        calTime.begin();
        sql = sqlConfig.sqlData + sqlConfig.sqlOrder;
        resultSet = stmt.executeQuery(sql);
        if(logger.isDebugEnabled())
        {
          logger.debug("通用查询：查询" + this.query.getQueryName() + "获取数据，Sql为：" + StringUtils.LINE_SEPARATOR);
          logger.debug(sql.replaceAll("[\n][ \t]*", "\n").replaceAll("[\n]+", "\n"));
        }
        queryValue.calTimeSb.append("Sql执行时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

        //获取标题
        calTime.begin();
        ResultSetMetaData rsmd = resultSet.getMetaData();

        int columnCount = rsmd.getColumnCount();
        queryValue.title = new String[columnCount];
        queryValue.dataType = new int[columnCount];
        for(int i = 0; i < rsmd.getColumnCount(); i++)
        {
          queryValue.dataType[i] = rsmd.getColumnType(i + 1);
          queryValue.title[i] = rsmd.getColumnLabel(i + 1);
        }
        queryValue.calTimeSb.append("获取标题时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

        //获取数据
        calTime.begin();
        while(resultSet.next())
        {
          String[] data = new String[columnCount];
          for(int i = 0; i < rsmd.getColumnCount(); i++)
          {
            data[i] = resultSet.getString(i + 1);
          }
          queryValue.allDataList.add(data);
        }
        resultSet.close();
        queryValue.recordCount = queryValue.allDataList.size();
        queryValue.calTimeSb.append("获取数据时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

        //随机处理
        calTime.begin();
        if(needRadom)
        {
          queryValue.allDataList = ArrayListUtils.random(queryValue.allDataList);
        }
        queryValue.calTimeSb.append("随机处理时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);

        //统计数据获取
        if(sqlConfig.sqlDataSum.length() > 0)
        {
          calTime.begin();
          sql = sqlConfig.sqlDataSum;
          resultSet = stmt.executeQuery(sql);
          rsmd = resultSet.getMetaData();
          if(resultSet.next())
          {
            queryValue.dataSum = new String[queryValue.title.length];
            for(int i = 0; i < rsmd.getColumnCount() && i < queryValue.dataSum.length; i++)
            {
              queryValue.dataSum[i] = resultSet.getString(i + 1);
            }
          }
          queryValue.calTimeSb.append("统计数据时间：").append(calTime.endString()).append(StringUtils.LINE_SEPARATOR);
        }
      }
      sqlCacheMap.put(sqlConfig.sqlData, queryValue);
      queryValue.calTimeSb.append("从缓存获取数据时间：").append(calTimeTotal.endString()).append(StringUtils.LINE_SEPARATOR);

      return true;
    }
    catch(SQLException ex)
    {
      throw new JdbcException("通用查询：Sql执行错误", sql, ex);
    }
    finally
    {
      JdbcUtils.close(resultSet, stmt, conn);
    }
  }

  /**
   * 获取是否需要随机



   * @return boolean
   */
  public boolean isNeedRadom()
  {
    return needRadom;
  }

  /**
   * 获取是否需要统计



   * @return boolean
   */
  public boolean isNeedCount()
  {
    return needCount;
  }

  /**
   * 获取是否需要缓存



   * @return boolean
   */
  public boolean isNeedCache()
  {
    return needCache;
  }

  /**
   * 获取Xml配置文件中valuefetch节点
   * @return Element
   */
  public Element getEleConfig()
  {
    return eleConfig;
  }

  /**
   * 获取查询对象
   * @return IQuery
   */
  public IQuery getQuery()
  {
    return query;
  }

  /**
   * 获取用于统计量的MultiCheckbox组件
   * @return MultiCheckbox
   */
  public MultiCheckbox getSqlGroupSelect()
  {
    return sqlGroupSelect;
  }

  /**
   * 设置页面大小
   * @param pageSize int
   */
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  /**
   * 设置Xml配置文件中valuefetch节点配置
   * @param eleConfig Element
   */
  public void setEleConfig(Element eleConfig)
  {
    this.eleConfig = eleConfig;
  }

  /**
   * 设置查询对象
   * @param query IQuery
   */
  public void setQuery(IQuery query)
  {
    this.query = query;
  }

  public IValueFetch getAgentClass() {
    return agentClass;
  }

  public void setAgentClass(IValueFetch agentClass) {
    this.agentClass = agentClass;
  }

  public SqlConfig getSqlConfig(QueryValueFetchByJdbcSql agentClass,HttpServletRequest request) {
    //将配置的Sql元素传送给SqlConfig
    SqlConfig sqlConfig = new SqlConfig();
//    SqlString sqlStringSelect = new SqlString(agentClass.sqlSelect);
//    sqlConfig.sqlSelect = sqlStringSelect.getParseSql(request, null);
    //sqlConfig.sqlStringSelect = this.sqlStringSelect;
//    sqlConfig.sqlStringFromWhere = agentClass.sqlStringFromWhere;


//      sqlConfig.sqlFromWhere = sqlConfig.sqlStringFromWhere.getParseSql(request, this.replaceMap);
//    sqlConfig.sqlSelect = sqlConfig.sqlStringSelect.getParseSql(request, this.replaceMap);

        sqlConfig.sqlData = "select " + agentClass.sqlSelect + " " +
                   agentClass.sqlStringFromWhere.getParseSql(request, agentClass.replaceMap);
//        sqlConfig.sqlSum = agentClass.sqlSum;
//        if(sqlConfig.sqlSum.length() > 0)
//          sqlConfig.sqlDataSum = "select " + sqlConfig.sqlSum + " " + sqlConfig.sqlFromWhere;
//        sqlConfig.sqlCount = "select count(*) from (" + sqlConfig.sqlData + ")"; // + sqlConfig.sqlFromWhere;
//        String query_sortcol = RequestUtils.getStringParameter(request, "query_sortcol", "");
        sqlConfig.sqlOrder = agentClass.sqlOrder;
//        if(!StringUtils.isBlank(query_sortcol))
//        {
//          sqlConfig.sqlOrder = " order by " + query_sortcol + " " + RequestUtils.getStringParameter(request, "query_sortdir", "");
//        }
//        sqlConfig.sqlOrder = StringUtils.trimToEmpty(sqlConfig.sqlOrder);
    sqlConfig.isOverrideCache = true;
    return sqlConfig;
  }

  public int getPageSize() {
    return pageSize;
  }
}
