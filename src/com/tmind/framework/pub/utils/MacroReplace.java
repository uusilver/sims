package com.tmind.framework.pub.utils;

/**
 * 替换宏中的替换定义
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: tmind.com</p>
 *
 * @author 杨洋
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
