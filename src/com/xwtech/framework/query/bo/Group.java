package com.xwtech.framework.query.bo;

import java.util.ArrayList;
import com.xwtech.framework.pub.utils.SqlString;

/**
 * SqlGroup中的Group对象
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
public class Group implements Comparable, Cloneable
{
  /**
   * 统计条件的名称
   */
  public String groupName;

  /**
   * 统计条件的标签
   */
  public String groupLabel;

  /**
   * 统计条件的宏替换值
   */
  public String macrovalue;

  /**
   * 统计条件的位置，x开头为x轴条件，y开头为y轴条件，后面按照数字排列
   */
  public String position;

  /**
   * 是否需要小计
   */
  public boolean needSubTotal = false;

  /**
   * 是否需要钻取
   */
  public boolean needDrill = false;

  /**
   * 统计条件的metadata
   */
  public ArrayList metadata;

  /**
   * 获取metadata的Sql（如果没有配置的话，就从结果集中抽取metadata）
   */
  public SqlString metadataSqlString;

  public String metadatasql;


  /**
   * 初始化函数
   * @param groupName String
   * @param groupLabel String
   * @param metadatasql String
   * @param macrovalue String
   * @param position String
   */
  public Group(String groupName, String groupLabel, String metadatasql, String macrovalue, String position)
  {
    this.groupName = groupName;
    this.groupLabel = groupLabel;
    this.metadatasql = metadatasql;
    this.metadataSqlString = new SqlString(metadatasql);
    this.macrovalue = macrovalue;
    this.position = position;
    metadata = new ArrayList();
  }

  /**
   * Group的排序函数，按照position进行排序
   * @param object Object
   * @return int
   */
  public int compareTo(Object object)
  {
    if(object == null)
      return 1;
    if(!(object instanceof Group))
      return 1;
    return position.compareTo(((Group)object).position);
  }

  /**
   * 返回对象的副本
   * @return Object
   */
  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch(CloneNotSupportedException ex)
    {
      return null;
    }
  }
}
