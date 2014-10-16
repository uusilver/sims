package com.xwtech.framework.query.bo;

import javax.servlet.http.HttpServletRequest;
import org.jdom.Element;

import java.util.Iterator;
import org.jdom.Attribute;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import com.xwtech.framework.pub.tag.MultiCheckbox;
import com.xwtech.framework.pub.utils.Assert;
import com.xwtech.framework.pub.utils.JdomUtils;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.utils.SqlString;
import com.xwtech.framework.pub.utils.ClassUtils;
import com.xwtech.framework.pub.utils.RequestUtils;
import com.xwtech.framework.pub.utils.LogicExpression;
import com.xwtech.framework.pub.utils.MacroString;
import com.xwtech.framework.pub.utils.BeanUtils;

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
public class QueryConditionByXml implements IQueryCondition, IJdomConfig
{
  protected static final Logger logger = Logger.getLogger(QueryConditionByXml.class);

  private Element eleConfig;
  private boolean hasCondition = false;
  private IQuery query;
  private String style;
  private ArrayList conditionList;
  private ArrayList conditionHiddenExprList;

  /**
   * 获取查询对象
   * @return IQuery
   */
  public IQuery getQuery()
  {
    return query;
  }

  /**
   * 设置查询条件的配置
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

  /**
   * 获取查询条件的配置
   * @return Element
   */
  public Element getEleConfig()
  {
    return eleConfig;
  }

  /**
   * 查询条件配置的初始化：读取配置
   * @return boolean
   */
  public boolean init()
  {
    Assert.notNull(this.eleConfig, "通过Xml配置的查询条件配置没有设置Xml节点");
    String style = JdomUtils.getAttributeValue(this.eleConfig, "style", "");
    if ( StringUtils.isNotEmpty(style))
      this.style = (String)QueryManager.getQueryStyleMap().get(style);

    //初始化条件列表
    conditionList = new ArrayList();
    conditionHiddenExprList = new ArrayList();

    //解析条件
    Iterator iterator = eleConfig.getChildren().iterator();
    Element eleCondition = null;
    String conditionClass = null;
    String conditionLabel = null;
    String conditionHiddenExpr = null;
    String attrName = null;
    while(iterator.hasNext())
    {
      this.hasCondition = true;
      eleCondition = (Element)iterator.next();
      //对于br的特殊处理
      if(eleCondition.getName().equals("br"))
      {
        conditionList.add("<br/>");
        conditionHiddenExprList.add("");
        continue;
      }

      //对于raw的特殊处理
      if(eleCondition.getName().equals("raw"))
      {
        SqlString sqlString = new SqlString(eleCondition.getTextTrim());
        conditionList.add(sqlString);
        conditionHiddenExprList.add("");
        continue;
      }
      if(!eleCondition.getName().equals("condition"))
        continue;

      //对于condition的处理
      conditionClass = null;
      conditionLabel = null;

      conditionClass = JdomUtils.getAttributeValue(eleCondition, "class");
      conditionHiddenExpr = JdomUtils.getAttributeValue(eleCondition, "hiddenExpr");
      Object objCondition = null;
      try
      {
        objCondition = ClassUtils.newInstance(conditionClass);
      }
      catch(RuntimeException ex)
      {
        QueryManager.logError(logger,query,"条件类（" + conditionClass + "）实例化失败");
        continue;
      }
      if(objCondition instanceof IHtmlet)
      {
        Iterator iteratorConditionAttr = eleCondition.getAttributes().iterator();
        Attribute attr = null;
        while(iteratorConditionAttr.hasNext())
        {
          attr = (Attribute)iteratorConditionAttr.next();
          attrName = attr.getName();
          if(attrName.equals("class"))
            continue;
          if(attrName.equals("conditionLabel"))
            conditionLabel = attr.getValue();
          else
            BeanUtils.setProperty(objCondition, attrName, attr.getValue());
        } //end while(iteratorConditionAttr.hasNext())
        Condition condition = new Condition();
        condition.setConditionLabel(conditionLabel);
        condition.setConditionHtmlet((IHtmlet)objCondition);
        conditionList.add(condition);
        conditionHiddenExprList.add(conditionHiddenExpr);
      }
    } //end while
    return true;
  }

  /**
   * 获取查询条件
   * @param request HttpServletRequest
   * @return 查询条件的Html
   * 生成查询条件的Html，并根据HttpServletRequest中的参数设置当前选择值
   */
  public String getHtml(HttpServletRequest request)
  {
    QueryValue queryValue = (QueryValue)request.getAttribute("queryValue");
    queryValue.hasCondition = this.hasCondition;

    StringBuffer sb = new StringBuffer();
    //当前查询名称
    sb.append("<input type=hidden name=QueryName value="+this.query.getQueryName()+">");

    //查找queryCondition
    for(int i = 0; i < conditionList.size(); i++)
    {
      String conditionString=null;
      Object objCondition = conditionList.get(i);

      //对于<br>的处理
      if(objCondition instanceof String)
      {
        sb.append(objCondition);
        continue;
      }

      //对于<raw>的处理
      if(objCondition instanceof SqlString)
      {
        SqlString sqlString = (SqlString)objCondition;
        sb.append(sqlString.getParseSql(request,null));
        continue;
      }

      if(!(objCondition instanceof Condition))
        continue;
      String conditionHiddenExpr =  (String)conditionHiddenExprList.get(i);
      //对condition的处理
      Condition condition = (Condition)objCondition;
      IHtmlet conditionHtmlet = condition.getConditionHtmlet();
      //对于单个值传递的处理
      if(conditionHtmlet instanceof IValuable)
      {
        IValuable valuable = (IValuable)conditionHtmlet;
        String value = RequestUtils.getStringParameter(request, valuable.getName(), "");
        if(!StringUtils.isBlank(value))
          valuable.setValue(value);
      }
      //对于多个值传递的处理
      if(conditionHtmlet instanceof IMultiValuable)
      {
        IMultiValuable valuable = (IMultiValuable)conditionHtmlet;
        String[] values = request.getParameterValues(valuable.getName());
        valuable.setMutipleValues(values);
      }
      try{
          conditionString = conditionHtmlet.getHtml(request);
      }catch(Throwable ex)
      {
        QueryManager.logError(logger,query,"获取条件（"+condition.getConditionLabel()+"）时出现异常",ex);
      }
      boolean hidden = false;
      hidden =  (StringUtils.isNotEmpty(conditionHiddenExpr) && LogicExpression.execLogicExpression(conditionHiddenExpr,request));
      if (hidden)
        sb.append("<span style='display=none'>");
      if (StringUtils.isEmpty(this.style))
      {
        sb.append(StringUtils.trimToEmpty(condition.getConditionLabel()));
        sb.append(StringUtils.HTML_BLANK);
        sb.append(conditionString);
        sb.append(StringUtils.HTML_BLANK);
      }
      else
      {
        MacroString macro= new MacroString(this.style,"$(",")$");
        macro.setMacroValue("conditionLabel",condition.getConditionLabel());
        macro.setMacroValue("condition",conditionString);
        sb.append(macro.getString(false));
      }
      if (hidden)
        sb.append("</span>");
    }

    //统计量
    MultiCheckbox sqlGroupSelect = query.getQueryValueFetch().getSqlGroupSelect();
    if (sqlGroupSelect!=null && StringUtils.isNotEmpty(this.style))
    {
      MacroString macro= new MacroString(this.style,"$(",")$");
      macro.setMacroValue("conditionLabel","统计量");
      macro.setMacroValue("condition",sqlGroupSelect.getHtml(request));
      sb.append(macro.getString(false));
    }

    //统计条件
    if(queryValue.groupList != null)
    {
      sb.append(QueryManager.getQueryStyleMap().get("GroupAdmin"));
      sb.append("<script>");
      for(int i = 0; i < queryValue.groupListY.size(); i++)
      {
        Group group = (Group)queryValue.groupListY.get(i);
        sb.append("addGroup('Y','" + group.groupLabel + "','" + group.groupName + "');")
            .append(StringUtils.LINE_SEPARATOR);
      }
      for(int i = 0; i < queryValue.groupListX.size(); i++)
      {
        Group group = (Group)queryValue.groupListX.get(i);
        sb.append("addGroup('X','" + group.groupLabel + "','" + group.groupName + "');")
            .append(StringUtils.LINE_SEPARATOR);
      }
      for(int i = 0; i < queryValue.groupListSelect.size(); i++)
      {
        Group group = (Group)queryValue.groupListSelect.get(i);
        sb.append("addGroup('Z','" + group.groupLabel + "','" + group.groupName + "');")
            .append(StringUtils.LINE_SEPARATOR);
      }
      sb.append("</script>");
    }

    return sb.toString();
  }
}
