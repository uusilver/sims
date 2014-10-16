package com.xwtech.framework.pub.tag;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.xwtech.framework.pub.utils.BeanUtils;
import com.xwtech.framework.pub.utils.SoapUtils;
import com.xwtech.framework.pub.utils.SqlString;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import com.xwtech.framework.query.bo.IHtmlet;
import com.xwtech.mss.pub.tools.ChineseSpellingToPinYin;

/**
 * @author yug
 *
 * 联动框的Tag
 */

public class LinkTag extends TagSupport implements IHtmlet
{
  protected static final Logger logger = Logger.getLogger(LinkTag.class);

  //某一级的值
  ArrayList arrOne = new ArrayList();

  private String requestArrOne = null;

  //样式名称
  private String style = null;

  //第几级
  private String num = null;

  //是否还有下一级
  private String next = null;

  //选择框中的提示标题
  private String title = null;

  //下拉框的id属性名称
  private String id = null;

  //下拉框的value属性名称
  private String valueName = null;

  //父id的属性名称
  private String fid = null;

  private static long secq = System.currentTimeMillis();

  //下拉框本身id的名称
  private String selectId = null;

  //最后一级下拉框的事件
  private String event = null;

  //select框的name值
  private String name = null;

  //select框的SQL语句
  private String sql = null;

  //select框是否支持多选
  private String mutiple = null;

  //多选框的大小
  private String selectSize = null;

  private String value = null;

  private String[] mutipleValues = null;

  private String isPage = null;

  //上一级下拉框的名称
  private String fatherName = null;

//上一级下拉框的值
  private String fvalue = null;

//下拉框是否初始化时就有值
  private String isLoad = null;

  private String mvalue = null;

  //以下是调用SOAP接口所需的参数
  private String module;

  private String operation;

  private String eventName;

  private String eventParm;

  private String respName;

  private String isSoap;

  private String soapInfo;

  private String soapId;

  private String soapName;

  private String soapFid;

  private String fatherCatalogID;

  /**
   * 控制本标签是否可用
   */
  private String disabled = null;

  public String getDisabled() {
    return disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }

  /**
   * @return 返回 soapFid。
   */
  public String getSoapFid()
  {
    return soapFid;
  }

  public void setSoapFid(String soapFid)
  {
    this.soapFid = soapFid;
  }

  /**
   * @return 返回 soapId。
   */
  public String getSoapId()
  {
    return soapId;
  }

  /**
   * @param soapId 要设置的 soapId。
   */
  public void setSoapId(String soapId)
  {
    this.soapId = soapId;
  }

  /**
   * @return 返回 soapInfo。
   */
  public String getSoapInfo()
  {
    return soapInfo;
  }

  /**
   * @param soapInfo 要设置的 soapInfo。
   */
  public void setSoapInfo(String soapInfo)
  {
    this.soapInfo = soapInfo;
  }

  /**
   * @return 返回 soapName。
   */
  public String getSoapName()
  {
    return soapName;
  }

  /**
   * @param soapName 要设置的 soapName。
   */
  public void setSoapName(String soapName)
  {
    this.soapName = soapName;
  }

  /**
   * @return 返回 isSoap。
   */
  public String getIsSoap()
  {
    return isSoap;
  }

  /**
   * @param isSoap 要设置的 isSoap。
   */
  public void setIsSoap(String isSoap)
  {
    this.isSoap = isSoap;
  }

  /**
   * @return 返回 eventName。
   */
  public String getEventName()
  {
    return eventName;
  }

  /**
   * @param eventName 要设置的 eventName。
   */
  public void setEventName(String eventName)
  {
    this.eventName = eventName;
  }

  /**
   * @return 返回 eventParm。
   */
  public String getEventParm()
  {
    return eventParm;
  }

  /**
   * @param eventParm 要设置的 eventParm。
   */
  public void setEventParm(String eventParm)
  {
    this.eventParm = eventParm;
  }

  /**
   * @return 返回 module。
   */
  public String getModule()
  {
    return module;
  }

  /**
   * @param module 要设置的 module。
   */
  public void setModule(String module)
  {
    this.module = module;
  }

  /**
   * @return 返回 operation。
   */
  public String getOperation()
  {
    return operation;
  }

  /**
   * @param operation 要设置的 operation。
   */
  public void setOperation(String operation)
  {
    this.operation = operation;
  }

  /**
   * @return 返回 respName。
   */
  public String getRespName()
  {
    return respName;
  }

  /**
   * @param respName 要设置的 respName。
   */
  public void setRespName(String respName)
  {
    this.respName = respName;
  }

  public String getMvalue()
  {
    return mvalue;
  }

  public void setMvalue(String mvalue)
  {
    this.mvalue = mvalue;
  }

  public String getIsLoad()
  {
    return isLoad;
  }

  public void setIsLoad(String isLoad)
  {
    this.isLoad = isLoad;
  }

  public String getFvalue()
  {
    return fvalue;
  }

  public void setFvalue(String fvalue)
  {
    this.fvalue = fvalue;
  }

  public String getFatherName()
  {
    return fatherName;
  }

  public void setFatherName(String fatherName)
  {
    this.fatherName = fatherName;
  }

  public String getIsPage()
  {
    return isPage;
  }

  public void setIsPage(String isPage)
  {
    this.isPage = isPage;
  }

  /**
   * @return 返回 mutipleValues。
   */
  public String[] getMutipleValues()
  {
    return mutipleValues;
  }

  /**
   * @param mutipleValues 要设置的 mutipleValues。

   */
  public void setMutipleValues(String[] mutipleValues)
  {
    this.mutipleValues = mutipleValues;
  }

  /**
   * @return 返回 mutiple。

   */
  public String getMutiple()
  {
    return mutiple;
  }

  /**
   * @param mutiple 要设置的 mutiple。

   */
  public void setMutiple(String mutiple)
  {
    this.mutiple = mutiple;
  }

  /**
   * @return 返回 selectSize。

   */
  public String getSelectSize()
  {
    return selectSize;
  }

  /**
   * @param selectSize 要设置的 selectSize。

   */
  public void setSelectSize(String selectSize)
  {
    this.selectSize = selectSize;
  }

  /**
   * @return 返回 sql。

   */
  public String getSql()
  {
    return sql;
  }

  /**
   * @param sql 要设置的 sql。

   */
  public void setSql(String sql)
  {
    this.sql = sql;
  }

  /**
   * @return 返回 name。

   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name 要设置的 name。

   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return 返回 event。

   */
  public String getEvent()
  {
    return event;
  }

  /**
   * @param event 要设置的 event。

   */
  public void setEvent(String event)
  {
    this.event = event;
  }

  /**
   * @return 返回 fid。

   */
  public String getFid()
  {
    return fid;
  }

  /**
   * @param fid
   *            要设置的 fid。

   */
  public void setFid(String fid)
  {
    this.fid = fid;
  }

  /**
   * @return 返回 id。

   */
  public String getId()
  {
    return id;
  }

  /**
   * @param id
   *            要设置的 id。

   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * @return 返回 value。

   */
  public String getValue()
  {
    return value;
  }

  /**
   * @param value
   *            要设置的 value。

   */
  public void setValue(String value)
  {
    this.value = value;
  }

  /**
   * @return 返回 value。

   */

  public String getValueName()
  {
    return valueName;
  }

  /**
   * @param valueName
   *            要设置的 valueName。

   */
  public void setValueName(String valueName)
  {
    this.valueName = valueName;
  }

  /**
   * @return 返回 next。

   */
  public String getNext()
  {
    return next;
  }

  /**
   * @param next
   *            要设置的 next。

   */
  public void setNext(String next)
  {
    this.next = next;
  }

  /**
   * @return 返回 title。

   */
  public String getTitle()
  {
    return title;
  }

  /**
   * @param title
   *            要设置的 title。

   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * @return 返回 num。

   */
  public String getNum()
  {
    return num;
  }

  /**
   * @param num
   *            要设置的 num。

   */
  public void setNum(String num)
  {
    this.num = num;
  }

  /**
   * @return 返回 style。

   */
  public String getStyle()
  {
    return style;
  }

  /**
   * @param style
   *            要设置的 style。

   */
  public void setStyle(String style)
  {
    this.style = style;
  }

  /**
   * @return 返回 arrOne。

   */
  public ArrayList getArrOne()
  {
    return arrOne;
  }

  /**
   * @param arrOne
   *            要设置的 arrOne。

   */
  public void setArrOne(ArrayList arrOne)
  {
    this.arrOne = arrOne;
  }

  public void setRequestArrOne(String requestArrOne)
  {
    this.requestArrOne = requestArrOne;
  }

  public String getRequestArrOne()
  {
    return this.requestArrOne;
  }

  public String getFatherCatalogID() {
    return fatherCatalogID;
  }

  public void setFatherCatalogID(String fatherCatalogID) {
    this.fatherCatalogID = fatherCatalogID;
  }



  public LinkTag()
  {
    next = "false";
    title = "";
    style = "";
    event = "";
    name = "";
    sql = "";
    value = "";
    mutiple = "false";
    selectSize = "1";
    isPage = "true";
    fatherName = "";
    fvalue = "";
    isLoad = "false";
    mvalue = "";
    isSoap = "false";
    disabled = "";
  }

  private static Method getMethod(Method[] objMethods, String strMethodName)
  {
    for(int i = 0; i < objMethods.length; i++)
    {
      if(objMethods[i].getName().equalsIgnoreCase("get" + strMethodName))
      {
        return objMethods[i];
      }
    }
    return null;
  }

  private static String getValue(ArrayList myarr, int j, String name)
  {
    String result = null;
    if(myarr == null || myarr.size() == 0)
    {
      result = "";
    }
    else
    {
      try
      {
        Object res = myarr.get(j);
        StringTokenizer st = new StringTokenizer(name, ".");
        while(st.hasMoreElements())
        {
          res = getMethod(Class.forName(res.getClass().getName()).getMethods(), st.nextElement().toString()).invoke(res, null);
        }
        result = String.valueOf(res);

        /*
        if(name.indexOf(".")>0)//对于包含一层子对象的处理
        {
        String className = myarr.get(j).getClass().getName();
        Class c = Class.forName(className);
        Method[] objMethods = c.getMethods();
        Method obj = getMethod(objMethods, name.substring(0,name.indexOf(".")));
        Object res = obj.invoke(myarr.get(j), null);
        obj = getMethod(objMethods, name.substring(name.indexOf(".")+1,name.length()));
        res = obj.invoke(res, null);
        result = String.valueOf(res);
        }
        else
        {
        String className = myarr.get(j).getClass().getName();
        Class c = Class.forName(className);
        Method[] objMethods = c.getMethods();
        Method obj = getMethod(objMethods, name);
        Object res = obj.invoke(myarr.get(j), null);
        result = String.valueOf(res);
        }
        */
      }
      catch(Exception e1)
      {
        e1.printStackTrace();
      }
    }
    return result;
  }

  public String getHtml(HttpServletRequest request)
  {
    if(getRequestArrOne() != null)
    {
      setArrOne((ArrayList)request.getAttribute(getRequestArrOne()));
    }

    if(!"".equals(name))
    {
      if(mutiple.equals("false"))
      {
        if(isLoad.equals("false"))
        {
          this.setValue(request.getParameter(name) == null ? "" : request.getParameter(name));
        }
      }
      else
      {
        if(isLoad.equals("false"))
        {
          this.setMutipleValues(request.getParameterValues(name));
        }
      }
    }
    if(!"".equals(fatherName))
    {
      if(isLoad.equals("false"))
      {
        this.setFvalue(request.getParameter(fatherName));
      }
    }
    if(!mvalue.equals(""))
    {
      this.setValue(mvalue);
    }
    StringBuffer body = new StringBuffer();
    if(mutiple.equals("true"))
    {
      //从数据库中取出的值存放在此
      ArrayList sqlArr = new ArrayList();
      selectId = "select" + String.valueOf(secq++);
      if(!sql.equals(""))
      {
        DataSource ds = BaseFrameworkApplication.getBaseJdbcDAO().getDataSource();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
          conn = ds.getConnection();
          stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          SqlString metadataSqlString = new SqlString(sql);
          String metadatasql = metadataSqlString.getParseSql(request, null);
          rs = stmt.executeQuery(metadatasql);
          while(rs.next())
          {
            LinkParam linkParam = new LinkParam();
            linkParam.setSqlid(rs.getString(1));
            linkParam.setSqlvalue(rs.getString(2));
            sqlArr.add(linkParam);
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        finally
        {
          try
          {
            if(rs != null)
            {
              rs.close();
            }
            if(stmt != null)
            {
              stmt.close();
            }
            if(conn != null && !conn.isClosed())
            {
              conn.close();
            }
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }
        }
      }
      body = body.append("<select "+this.disabled+" name=\"" + name + "\" id=\"" + selectId + "1\" class=\"" + style + "\"  multiple  size=\"" + selectSize + "\"  " + event + ">\n");
      if(!"".equals(title))
      {
        body = body.append("<option value=\"\">" + title + "</option>\n");
      }
      if(sql.equals(""))
      {
        if(arrOne == null || arrOne.size() == 0)
        {
          body = body.append("");
        }
        else
        {
          for(int i = 0; i < arrOne.size(); i++)
          {
            int muLength = 0;
            if(mutipleValues != null)
            {
              muLength = mutipleValues.length;
              int flg = 0;
              for(int j = 0; j < muLength; j++)
              {
                if(mutipleValues[j].equals(getValue(arrOne, i, id)))
                {
                  body = body.append("<option value="
                          + getValue(arrOne, i, id) + " selected>"
                          + getValue(arrOne, i, valueName) + "</option>\n");
                  flg = 1;
                }
              }
              if(flg == 0)
              {
                body = body.append("<option value="
                        + getValue(arrOne, i, id) + ">"
                        + getValue(arrOne, i, valueName) + "</option>\n");
                flg = 0;
              }
            }
            else
            {
              body = body.append("<option value="
                      + getValue(arrOne, i, id) + ">"
                      + getValue(arrOne, i, valueName) + "</option>\n");
            }
          }
        }
      }
      else
      {
        if(sqlArr == null || sqlArr.size() == 0)
        {
          body = body.append("");
        }
        else
        {
          for(int i = 0; i < sqlArr.size(); i++)
          {
            int muLength = 0;
            if(mutipleValues != null)
            {
              muLength = mutipleValues.length;

              int flg = 0;
              for(int j = 0; j < muLength; j++)
              {
                if(mutipleValues[j].equals(getValue(sqlArr, i, "sqlid")))
                {
                  body = body.append("<option value="
                          + getValue(sqlArr, i, "sqlid") + " selected>"
                          + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                  flg = 1;
                }
              }
              if(flg == 0)
              {
                body = body.append("<option value="
                        + getValue(sqlArr, i, "sqlid") + ">"
                        + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                flg = 0;
              }
            }
            else
            {
              body = body.append("<option value="
                      + getValue(sqlArr, i, "sqlid") + ">"
                      + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
            }

          }
        }
      }
      body = body.append("</select>\n");
    }
    else
    {
      ArrayList sqlArr = new ArrayList();
      ArrayList soapArr = new ArrayList();
      if(!next.equals("false"))
      {
        next = "true";
      }
      selectId = "select" + String.valueOf(secq++);
      int mn = Integer.parseInt(num);
      if(!sql.equals(""))
      {
        DataSource ds = BaseFrameworkApplication.getBaseJdbcDAO().getDataSource();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
          conn = ds.getConnection();
          stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          SqlString metadataSqlString = new SqlString(sql);
          String metadatasql = metadataSqlString.getParseSql(request, null);
          rs = stmt.executeQuery(metadatasql);
          if(mn <= 1)
          {
            while(rs.next())
            {
              LinkParam linkParam = new LinkParam();
              linkParam.setSqlid(rs.getString(1));
              linkParam.setSqlvalue(rs.getString(2));
              sqlArr.add(linkParam);
            }
          }
          else
          {
            while(rs.next())
            {
              LinkParam linkParam = new LinkParam();
              linkParam.setSqlid(rs.getString(1));
              linkParam.setSqlvalue(rs.getString(2));
              linkParam.setSqlfid(rs.getString(3));
              sqlArr.add(linkParam);
            }
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        finally
        {
          try
          {
            if(rs != null)
            {
              rs.close();
            }
            if(stmt != null)
            {
              stmt.close();
            }
            if(conn != null && !conn.isClosed())
            {
              conn.close();
            }
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }
        }
      }
      else if(!isSoap.equals("false"))
      {
        try
        {
          Object obj = Class.forName(eventName).newInstance();
          StringTokenizer st = new StringTokenizer(eventParm, ",");
          int count = 0;
          count = st.countTokens();
          for(int i = 0; i < count / 2; i++)
          {
            BeanUtils.setProperty(obj, st.nextToken(), st.nextToken());
          }
          SoapUtils soapUtils = new SoapUtils();
          Object o = soapUtils.fetchPageResultFromDb(module, operation, obj, respName);
          Object[] iterator;
          if (!operation.equals("queryToneCategory")) {
           iterator = (Object[])o.getClass().getMethod("get" + soapInfo, null).invoke(o, null);
          } else {
            Object aa = (Object)o.getClass().getMethod("get" + soapInfo, null).invoke(o, null);
            iterator = (Object[])aa.getClass().getMethod("getCatalogInfo", null).invoke(aa, null);
          }
          if(mn <= 1)
          {
            for(int i = 0; i < iterator.length; i++)
            {
              //在cp推荐查询中需要过滤父目录
              if (fatherCatalogID != null && !fatherCatalogID.equals("")) {
                if (!((String)BeanUtils.getProperty(iterator[i], soapId)).equals(fatherCatalogID)) {
                  LinkParam linkParam = new LinkParam();
                  linkParam.setSqlid((String)BeanUtils.getProperty(iterator[i], soapId));
                  linkParam.setSqlvalue((String)BeanUtils.getProperty(iterator[i], soapName));
                  soapArr.add(linkParam);
                }
              } else {
                LinkParam linkParam = new LinkParam();
                linkParam.setSqlid((String)BeanUtils.getProperty(iterator[i], soapId));
                linkParam.setSqlvalue((String)BeanUtils.getProperty(iterator[i], soapName));
                soapArr.add(linkParam);
              }
            }
          }
          else
          {
            for(int i = 0; i < iterator.length; i++)
            {
              LinkParam linkParam = new LinkParam();
              linkParam.setSqlid((String)BeanUtils.getProperty(iterator[i], soapId));
              linkParam.setSqlvalue((String)BeanUtils.getProperty(iterator[i], soapName));
              linkParam.setSqlfid((String)BeanUtils.getProperty(iterator[i], soapFid));
              soapArr.add(linkParam);
            }

          }
        }
        catch(Exception ex)
        {
          ex.printStackTrace();
        }
      }
      if(mn <= 1)
      {
        mn = 1;
        if(next.equals("false"))
        {
          body = body.append("<select "+this.disabled+" name=\"" + name + "\" id=\"" + selectId + "1\" class=\""
                  + style + "\"  " + event + ">\n");
          if(!"".equals(title))
          {
            body = body.append("<option value=\"\">" + title + "</option>\n");
          }
          if(sql.equals("") && isSoap.equals("false"))
          {
            if(arrOne == null || arrOne.size() == 0)
            {
              body = body.append("");
            }
            else
            {
              for(int i = 0; i < arrOne.size(); i++)
              {
                if(!value.equals("") && value.equals(getValue(arrOne, i, id)))
                {
                  body = body.append("<option value="
                          + getValue(arrOne, i, id) + " selected>"
                          + getValue(arrOne, i, valueName) + "</option>\n");
                }
                else
                {
                  body = body.append("<option value="
                          + getValue(arrOne, i, id) + ">"
                          + getValue(arrOne, i, valueName) + "</option>\n");
                }
              }
            }
          }
          else if(sql.equals("") && !isSoap.equals("false"))
          {
            if(soapArr == null || soapArr.size() == 0)
            {
              body = body.append("");
            }
            else
            {
              for(int i = 0; i < soapArr.size(); i++)
              {
                if(!value.equals("") && value.equals(getValue(soapArr, i, "sqlid")))
                {
                  body = body.append("<option value="
                          + getValue(soapArr, i, "sqlid") + " selected>"
                          + getValue(soapArr, i, "sqlvalue") + "</option>\n");
                }
                else
                {
                  body = body.append("<option value="
                          + getValue(soapArr, i, "sqlid") + ">"
                          + getValue(soapArr, i, "sqlvalue") + "</option>\n");
                }
              }
            }

          }

          else
          {
            if(sqlArr == null || sqlArr.size() == 0)
            {
              body = body.append("");
            }
            else
            {
              for(int i = 0; i < sqlArr.size(); i++)
              {
                if(!value.equals("") && value.equals(getValue(sqlArr, i, "sqlid")))
                {
                  body = body.append("<option value="
                          + getValue(sqlArr, i, "sqlid") + " selected>"
                          + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                }
                else
                {
                  body = body.append("<option value="
                          + getValue(sqlArr, i, "sqlid") + ">"
                          + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                }
              }
            }

          }
        }
        else
        {
          body = body.append("<select "+this.disabled+" name=\"" + name + "\" id=\"" + selectId + "1\" class=\""
                  + style + "\" onChange=\"changeselect"+name+"1(this.value)\">\n");
          body = body.append("<option value=\"\">" + title + "</option>\n");
          if(sql.equals("") && isSoap.equals("false"))
          {
            if(arrOne == null || arrOne.size() == 0)
            {
              body = body.append("");
            }
            else
            {
              for(int i = 0; i < arrOne.size(); i++)
              {
                if(!value.equals("") && value.equals(getValue(arrOne, i, id)))
                {
                  body = body.append("<option value="
                          + getValue(arrOne, i, id) + " selected>"
                          + getValue(arrOne, i, valueName) + "</option>\n");
                }
                else
                {
                  body = body.append("<option value="
                          + getValue(arrOne, i, id) + ">"
                          + getValue(arrOne, i, valueName) + "</option>\n");
                }
              }
            }
          }
          else if(sql.equals("") && !isSoap.equals("false"))
          {
            if(soapArr == null || soapArr.size() == 0)
            {
              body = body.append("");
            }
            else
            {
              for(int i = 0; i < soapArr.size(); i++)
              {
                if(!value.equals("") && value.equals(getValue(soapArr, i, "sqlid")))
                {
                  body = body.append("<option value="
                          + getValue(soapArr, i, "sqlid") + " selected>"
                          + getValue(soapArr, i, "sqlvalue") + "</option>\n");
                }
                else
                {
                  body = body.append("<option value="
                          + getValue(soapArr, i, "sqlid") + ">"
                          + getValue(soapArr, i, "sqlvalue") + "</option>\n");
                }
              }
            }
          }
          else
          {
            if(sqlArr == null || sqlArr.size() == 0)
            {
              body = body.append("");
            }
            else
            {
              for(int i = 0; i < sqlArr.size(); i++)
              {
                if(!value.equals("") && value.equals(getValue(sqlArr, i, "sqlid")))
                {
                  body = body.append("<option value="
                          + getValue(sqlArr, i, "sqlid") + " selected>"
                          + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                }
                else
                {
                  body = body.append("<option value="
                          + getValue(sqlArr, i, "sqlid") + ">"
                          + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                }
              }
            }
          }
        }
        body = body.append("</select>\n");
      }
      else
      {
        body = body.append("<script language=\"javascript\">\n");
        body = body.append("var subcat" + name + mn + " = new Array();\n");
        if(sql.equals("") && isSoap.equals("false"))
        {
          if(arrOne == null || arrOne.size() == 0)
          {
            body = body.append("");
          }
          else
          {
            for(int i = 0; i < arrOne.size(); i++)
            {
              body = body.append("subcat" + name + mn + "[" + i
                      + "] = new Array('" + getValue(arrOne, i, fid)
                      + "','" + getValue(arrOne, i, valueName) + "','"
                      + getValue(arrOne, i, id) + "');\n");
            }
          }
        }
        else if(sql.equals("") && !isSoap.equals("false"))
        {
          if(soapArr == null || soapArr.size() == 0)
          {
            body = body.append("");
          }
          else
          {
            for(int i = 0; i < soapArr.size(); i++)
            {
              body = body.append("subcat" + name + mn + "[" + i
                      + "] = new Array('" + getValue(soapArr, i, "sqlfid")
                      + "','" + getValue(soapArr, i, "sqlvalue") + "','"
                      + getValue(soapArr, i, "sqlid") + "');\n");
            }
          }
        }
        else
        {
          if(sqlArr == null || sqlArr.size() == 0)
          {
            body = body.append("");
          }
          else
          {
            for(int i = 0; i < sqlArr.size(); i++)
            {
              body = body.append("subcat" + name + mn + "[" + i
                      + "] = new Array('" + getValue(sqlArr, i, "sqlfid")
                      + "','" + getValue(sqlArr, i, "sqlvalue") + "','"
                      + getValue(sqlArr, i, "sqlid") + "');\n");
            }
          }
        }
        body = body.append("function changeselect" + fatherName + (mn - 1)
                + "(locationid){\n");
        body = body.append("document.all." + selectId + num
                + ".length = 0;\n");
        body = body.append("document.all." + selectId + num
                + ".options[0] = new Option('" + title + "','');\n");
        if(next.equals("true"))
        {
          body = body.append("document.all." + selectId + mn + ".fireEvent(\"onChange\");\n");
        }
        body = body.append("for (i=0; i<subcat" + name + mn
                + ".length; i++){\nif (subcat" + name + mn
                + "[i][0] == locationid){\ndocument.all." + selectId + num
                + ".options[document.all." + selectId + mn
                + ".length] = new Option(subcat" + name + mn + "[i][1], subcat" + name
                + mn + "[i][2]);\n}\n}\n}\n");
        body = body.append("");
        body = body.append("</script>\n");
        body = body.append("");
        if(next.equals("false"))
        {
          body = body.append("<select "+this.disabled+" name=\"" + name + "\" id=\"" + selectId + num
                  + "\" class=\"" + style + "\"  " + event + ">\n");
          if(isLoad.equals("false"))
          {
            body = body.append("<option value=\"\">" + title + "</option>\n");
          }
          else
          {
            if(sql.equals(""))
            {
              if(arrOne == null || arrOne.size() == 0)
              {
                body = body.append("");
              }
              else
              {
                for(int i = 0; i < arrOne.size(); i++)
                {
                  if(!value.equals("") && value.equals(getValue(arrOne, i, id)))
                  {
                    body = body.append("<option value="
                            + getValue(arrOne, i, id) + " selected>"
                            + getValue(arrOne, i, valueName) + "</option>\n");
                  }
                  else
                  {
                    body = body.append("<option value="
                            + getValue(arrOne, i, id) + ">"
                            + getValue(arrOne, i, valueName) + "</option>\n");
                  }
                }
              }
            }
            else
            {
              if(sqlArr == null || sqlArr.size() == 0)
              {
                body = body.append("");
              }
              else
              {
                for(int i = 0; i < sqlArr.size(); i++)
                {
                  if(!value.equals("") && value.equals(getValue(sqlArr, i, "sqlid")))
                  {
                    body = body.append("<option value="
                            + getValue(sqlArr, i, "sqlid") + " selected>"
                            + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                  }
                  else
                  {
                    body = body.append("<option value="
                            + getValue(sqlArr, i, "sqlid") + ">"
                            + getValue(sqlArr, i, "sqlvalue") + "</option>\n");
                  }
                }
              }
            }

          }
        }
        else
        {
          body = body.append("<select "+this.disabled+" name=\"" + name + "\" id=\"" + selectId + num
                  + "\" class=\"" + style + "\" onChange=\"changeselect" + name
                  + mn + "(this.value)\">\n");
          body = body.append("<option value=\"\">" + title + "</option>\n");
        }
        body = body.append("</select>\n");
        body = body.append("<script language=\"javascript\">\n");
        body = body.append("document.all." + fatherName + ".value =\"" + fvalue + "\";\n");
        body = body.append("if(document.all." + fatherName + ".selectedIndex < 0&& document.all." + fatherName + ".options.length > 0)\n");
        body = body.append("document.all." + fatherName + ".selectedIndex = 0;");
        body = body.append("document.all." + fatherName + ".fireEvent(\"onchange\");\n");
        body = body.append("document.all." + name + ".value =\"" + value + "\";\n");
        body = body.append("</script>\n");
      }
      body = body.append("");
    }
    return body.toString();
  }

  public int doStartTag() throws JspException
  {
    try
    {

      pageContext.getOut().print(getHtml((HttpServletRequest)pageContext.getRequest()));
    }
    catch(IOException e)
    {
      throw new JspTagException("Tag: " + e.getMessage());
    }
    return SKIP_BODY;
  }

  public int doEndTag() throws JspException
  {
    return EVAL_PAGE;
  }

  public void release()
  {
    super.release();
  }

  public static void main(String[] args)
  {
    java.util.StringTokenizer st = new java.util.StringTokenizer("aaaaa.bb.cc", ".");
    while(st.hasMoreElements())
    {
      logger.info(st.nextElement());
    }
  }
}
