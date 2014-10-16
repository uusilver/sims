package com.xwtech.framework.pub.utils;

import com.xwtech.framework.pub.web.SessionNameConstants;
import java.util.HashMap;
import com.xwtech.framework.login.pub.AbstractLoginToken;
import com.xwtech.framework.pub.po.FrameLogin;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.xwtech.framework.pub.utils.simpleparser.SimpleParser;

/**
 *
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
public class LogicExpression
{
  protected static final Logger logger = Logger.getLogger(LogicExpression.class);

  /**
   * 逻辑表达式
   * @param conditionExpr String
   * @param request HttpServletRequest
   * @return boolean
   */
  public static boolean execLogicExpression(String conditionExpr, HttpServletRequest request)
  {
    Object obj = execExpression(conditionExpr, request);
    if(obj == null)
      return false;
    if(obj instanceof Boolean)
      return ((Boolean)obj).booleanValue();
    return false;
  }

  /**
   * 通用表达式
   * @param conditionExpr String
   * @param request HttpServletRequest
   * @return Object
   */
  public static Object execExpression(String conditionExpr, HttpServletRequest request)
  {
    HashMap conditionExprParaMap = new HashMap();
    conditionExprParaMap.put("request",request);
    conditionExprParaMap.put("session",request.getSession());
    MacroString macroString = null;
    Iterator iterator = null;

    if(StringUtils.contains(conditionExpr, "#["))
    {
      //参数宏替换
      macroString = new MacroString(conditionExpr, "#[", "]#");
      iterator = macroString.getMacroMap().keySet().iterator();
      while(iterator.hasNext())
      {
        String macroName = (String)iterator.next();
        String realMacroName = null;
        if(macroName.startsWith("0")) //整数
        {
          realMacroName = macroName.substring(1, macroName.length());
          Long macroValue = new Long(RequestUtils.getLongParameter(request, realMacroName, 0));
          conditionExprParaMap.put(realMacroName, macroValue);
        }
        if(macroName.startsWith(".")) //浮点数
        {
          realMacroName = macroName.substring(1, macroName.length());
          Double macroValue = new Double(RequestUtils.getDoubleParameter(request, realMacroName, 0));
          conditionExprParaMap.put(realMacroName, macroValue);
        }
        if(macroName.startsWith("'")) //字符串
        {
          realMacroName = macroName.substring(1, macroName.length());
          String macroValue = new String(RequestUtils.getStringParameter(request, realMacroName, ""));
          conditionExprParaMap.put(realMacroName, macroValue);
        }
        if((macroName.startsWith("0") || macroName.startsWith(".") || macroName.startsWith("'")))
        {
          macroString.setMacroValue(macroName, realMacroName);
        }
        else //值替换
        {
          String macroValue = RequestUtils.getStringParameter(request, macroName, "");
          macroString.setMacroValue(macroName, macroValue);
        }
        conditionExpr = macroString.getString(false);
      }
    } //if(StringUtils.contains(conditionExpr, "#["))

    if(StringUtils.contains(conditionExpr, "$["))
    {
      //多重参数宏替换
      macroString = new MacroString(conditionExpr, "$[", "]$");
      iterator = macroString.getMacroMap().keySet().iterator();
      while(iterator.hasNext())
      {
        String macroName = (String)iterator.next();
        String realMacroName = null;
        if(macroName.startsWith("0"))
        {
          realMacroName = macroName.substring(1, macroName.length());
          Long[] macroValue = ArrayUtils.toObject(RequestUtils.getLongParameters(request, realMacroName));
          conditionExprParaMap.put(realMacroName, macroValue);
        }
        if(macroName.startsWith("."))
        {
          realMacroName = macroName.substring(1, macroName.length());
          Double[] macroValue = ArrayUtils.toObject(RequestUtils.getDoubleParameters(request, realMacroName));
          conditionExprParaMap.put(realMacroName, macroValue);
        }
        if(macroName.startsWith("'"))
        {
          realMacroName = macroName.substring(1, macroName.length()); ;
          String[] macroValue = RequestUtils.getStringParameters(request, realMacroName);
          conditionExprParaMap.put(realMacroName, macroValue);
        }
        if((macroName.startsWith("0") || macroName.startsWith(".") || macroName.startsWith("'")))
        {
          macroString.setMacroValue(macroName, realMacroName);
        }
        else //值替换
        {
          boolean isNumber = true;
          if(macroName.endsWith("'"))
          {
            isNumber = false;
          }
          String[] macroValue = null;
          if (isNumber)
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
      }
      conditionExpr = macroString.getString(false);
    } //if(StringUtils.contains(conditionExpr, "$["))

    if(StringUtils.contains(conditionExpr, "#("))
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
          macroString = new MacroString(conditionExpr, "#(", ")#");
          macroString.setMacroValue("loginId", "loginId");
          macroString.setMacroValue("loginName", "loginName");
          conditionExprParaMap.put("loginId", frameLogin.getLoginId());
          conditionExprParaMap.put("loginName", frameLogin.getLoginName());
        }
        conditionExpr = macroString.getString(false);
      }
    } //if(StringUtils.contains(conditionExpr, "#("))
    try
    {
      return SimpleParser.execExpression(conditionExpr, conditionExprParaMap);
    }
    catch(Exception ex)
    {
      logger.error("",ex);
      return null;
    }
  }

  public static void main(String[] args)
  {
    String conditionExpr = "iif(a!='','b','c')";
    HashMap conditionExprParaMap = new HashMap();
    try
    {
      conditionExprParaMap.put("a", "");
      Object obj = SimpleParser.execExpression(conditionExpr, conditionExprParaMap);
      logger.info(obj);
    }
    catch(Exception ex)
    {
      logger.error("", ex);
    }

    conditionExpr = "iif(month_begin != '','and month >=\\'\\'','')";
    conditionExprParaMap = new HashMap();
    try
    {
      conditionExprParaMap.put("month_begin", "2");
      Object obj = SimpleParser.execExpression(conditionExpr, conditionExprParaMap);
      logger.info(obj);
    }
    catch(Exception ex)
    {
      logger.error("", ex);
    }

  }
}
