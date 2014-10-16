package com.xwtech.framework.query.bo;

import java.util.ArrayList;

/**
 * SqlGroup中的Group对象之间的依赖关系
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
public class GroupRelation
{
  /**
   * 统计条件1的名称
   */
  private String groupName1;

  /**
   * 统计条件2的名称
   */
  private String groupName2;

  /**
   * 存放两者关系的一个String[2]的List
   */
  private ArrayList relation;

  /**
   * 初始化函数
   * @param groupName1 String
   * @param groupName2 String
   * @param relation ArrayList
   */
  public GroupRelation(String groupName1,String groupName2,ArrayList relation)
  {
    this.groupName1 = groupName1;
    this.groupName2 = groupName2;
    this.relation = relation;
  }

  /**
   * 返回存放两者关系的一个String[2]的List
   * @return ArrayList
   */
  public ArrayList getRelation()
  {
    return relation;
  }

  /**
   * 返回统计条件2的名称
   * @return String
   */
  public String getGroupName2()
  {
    return groupName2;
  }

  /**
   * 返回统计条件1的名称
   * @return String
   */
  public String getGroupName1()
  {
    return groupName1;
  }
}
