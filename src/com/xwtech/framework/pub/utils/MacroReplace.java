package com.xwtech.framework.pub.utils;

/**
 * 替换宏中的替换定义
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
public class MacroReplace
{
  String conditionExpr;
  String paraname;

  String paravalue;

  String relation;

  String replacevalue;

  public MacroReplace(String conditionExpr,String paraname, String paravalue, String relation, String replacevalue)
  {
    this.conditionExpr = conditionExpr;
    this.paraname = paraname;
    this.paravalue = paravalue;
    this.relation = relation;
    this.replacevalue = replacevalue;
  }
  }
