package com.xwtech.framework.pub.utils;

import com.xwtech.framework.pub.web.SessionNameConstants;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import com.xwtech.framework.login.pub.AbstractLoginToken;
import com.xwtech.framework.pub.po.FrameLogin;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.xwtech.framework.query.bo.QueryValueFetchByJdbcSql;
import com.xwtech.framework.pub.utils.simpleparser.SimpleParser;

/**
 * 存放Sql的字符串
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
public class SqlString
{
  protected static final Logger logger = Logger.getLogger(SqlString.class);

  private String sql;

  //是否需要解析表达式宏（如果要解析表达式宏也就需要解析参数宏）
  private boolean needParseExpr = false;

  //是否需要解析替换宏（如果要解析替换宏也就需要解析参数宏）
  private boolean needParseReplace = false;

  //是否需要解析参数宏
  private boolean needParsePara = false;

  //是否需要解析多参数宏
  private boolean needParseParaMulti = false;

  //是否需要解析Session宏
  private boolean needParseSesseion = false;

  //是否需要处理where后面的and和空where
  private boolean needDealWhere = false;

  public SqlString(String sql)
  {
    setSql(sql);
  }

  public boolean needParse()
  {
    return needParseExpr|| needParseReplace || needParsePara || needParseParaMulti || needParseSesseion;
  }

  public void setSql(String sql)
  {
    this.sql = sql;
    if(this.sql == null)
      return;
    needParseExpr = false;
    needParseReplace = false;
    needParsePara = false;
    needParseParaMulti = false;
    needParseSesseion = false;

    if(StringUtils.contains(sql, "${"))
      needParseExpr = true;
    if(StringUtils.contains(sql, "#{"))
      needParseReplace = true;
    if(StringUtils.contains(sql, "#["))
      needParsePara = true;
    if(StringUtils.contains(sql, "$["))
      needParseParaMulti = true;
    if(StringUtils.contains(sql, "#("))
      needParseSesseion = true;
    if(needParse() && StringUtils.contains(sql.toLowerCase(), "where"))
      needDealWhere = true;
  }

  /**
   * 获取宏替换后的Sql
   * @param request HttpServletRequest
   * @param sql String
   * @return String
   */
  public String getParseSql(HttpServletRequest request, HashMap replaceMap)
  {
    String returnsql = sql;
    MacroString macroString = null;
    Iterator iterator = null;
    boolean needParseReplacePara = false;
    boolean needParseReplaceParaMulti = false;
    if(this.needParseExpr)
    {
      //参数宏替换
      macroString = new MacroString(returnsql, "${", "}$");
      iterator = macroString.getMacroMap().keySet().iterator();
      while(iterator.hasNext())
      {
        String macroName = (String)iterator.next();
        String macroValue = LogicExpression.execExpression(macroName, request).toString();
        if(macroValue != null)
        {
          macroString.setMacroValue(macroName, macroValue);
        }
      }
      returnsql = macroString.getString(false);
      if(StringUtils.contains(returnsql, "#["))
        needParseReplacePara = true;
      if(StringUtils.contains(returnsql, "$["))
        needParseReplaceParaMulti = true;
    }
    if(this.needParseReplace && replaceMap != null)
    {
      //条件宏替换
      macroString = new MacroString(returnsql, "#{", "}#");
      iterator = replaceMap.keySet().iterator();
      while(iterator.hasNext())
      {
        String macroName = (String)iterator.next();
        ArrayList paraReplaceList = (ArrayList)replaceMap.get(macroName);
        macroString.setMacroValue(macroName, parseReplace(paraReplaceList, request));
      }
      returnsql = macroString.getString(false);
      if(StringUtils.contains(returnsql, "#["))
        needParseReplacePara = true;
      if(StringUtils.contains(returnsql, "$["))
        needParseReplaceParaMulti = true;
    }

    if(needParseReplacePara || this.needParsePara)
    {
      //参数宏替换
      macroString = new MacroString(returnsql, "#[", "]#");
      iterator = macroString.getMacroMap().keySet().iterator();
      while(iterator.hasNext())
      {
        String macroName = (String)iterator.next();
        String macroValue = RequestUtils.getStringParameter(request, macroName, null);
        if(macroValue != null)
        {
          macroString.setMacroValue(macroName, StringUtils.escapeSql(macroValue));
        }
      }
      returnsql = macroString.getString(false);
    }

    if(needParseReplaceParaMulti || this.needParseParaMulti)
    {
      //多重参数宏替换
      macroString = new MacroString(returnsql, "$[", "]$");
      iterator = macroString.getMacroMap().keySet().iterator();
      while(iterator.hasNext())
      {
        boolean isNumber = true;
        String macroName = (String)iterator.next();
        if(macroName.endsWith("'"))
        {
          isNumber = false;
        }
        String[] macroValue = null;
        if(isNumber)
          macroValue = request.getParameterValues(macroName);
        else
          macroValue = request.getParameterValues(macroName.substring(0, macroName.length() - 1));
        if(macroValue != null && macroValue.length > 0)
        {
          StringBuffer sb = new StringBuffer();
          for(int i = 0; i < macroValue.length; i++)
          {
            if(i > 0)
              sb.append(",");
            if(!isNumber)
              sb.append("'");
            sb.append(StringUtils.escapeSql(macroValue[i]));
            if(!isNumber)
              sb.append("'");
          }
          macroString.setMacroValue(macroName, sb.toString());
        }
      }
      returnsql = macroString.getString(false);
    }

    if(this.needParseSesseion)
    {
      //Session宏替换
      Object objLoginToken = SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
      if(objLoginToken == null)
      {
        logger.error("无法获取Session中的loginToken");
      }
      if(objLoginToken != null)
      {
        AbstractLoginToken loginToken = (AbstractLoginToken)objLoginToken;
        FrameLogin frameLogin = loginToken.getFrameLogin();
        if(frameLogin != null)
        {
          macroString = new MacroString(returnsql, "#(", ")#");
          macroString.setMacroValue("loginId", String.valueOf(frameLogin.getLoginId()));
          macroString.setMacroValue("loginName", frameLogin.getLoginName());
        }
        returnsql = macroString.getString(false);
      }
    }

    //去除注释
    if(StringUtils.contains(returnsql, "/*"))
    {
      macroString = new MacroString(returnsql, "/*", "*/");
      returnsql = macroString.getString(false);
    }

    //处理where后面的and和空where
    if(needDealWhere)
    {
      returnsql = returnsql.replaceAll("[Ww][Hh][Ee][Rr][Ee][ \t\n]*[Aa][Nn][Dd]", "where");
      returnsql = returnsql.replaceAll("[Ww][Hh][Ee][Rr][Ee][ \t\n]*$", "");
    }

    return returnsql;
  }

  /**
   * 根据Request解析Replace的字符串
   * @param paraReplaceList ArrayList
   * @param request HttpServletRequest
   * @return String
   */
  public String parseReplace(ArrayList paraReplaceList, HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < paraReplaceList.size(); i++)
    {
      MacroReplace replace = (MacroReplace)paraReplaceList.get(i);
      //对于逻辑表达式的处理
      if(replace.conditionExpr.length() > 0)
      {
        if(LogicExpression.execLogicExpression(replace.conditionExpr, request))
          sb.append(" ").append(replace.replacevalue);
      }
      else
      {
        //包含
        if(replace.relation.equals("include"))
        {
          if(ArrayUtils.contains(request.getParameterValues(replace.paraname), replace.paravalue))
            sb.append(" ").append(replace.replacevalue);
        }
        //相等
        if(replace.relation.equals("="))
        {
          if(StringUtils.equals(StringUtils.trimToEmpty(request.getParameter(replace.paraname)), replace.paravalue))
            sb.append(" ").append(replace.replacevalue);
        }
        //不相等
        if(replace.relation.equals("!="))
        {
          if(!StringUtils.equals(StringUtils.trimToEmpty(request.getParameter(replace.paraname)), replace.paravalue))
            sb.append(" ").append(replace.replacevalue);
        }
      }
    }
    return sb.toString();
  }

  public String getSql()
  {
    return sql;
  }

}
