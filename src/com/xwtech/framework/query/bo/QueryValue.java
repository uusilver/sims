package com.xwtech.framework.query.bo;

import java.util.ArrayList;

/**
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
public class QueryValue implements java.io.Serializable
{
  /**
   * 本次查询的名称
   */
  public String queryName;

  /**
   * 上个查询的名称
   */
  public String lastQueryName;
  /**
   * -1：从别的Query提交过来
   * 0：第一次运行
   * 1：第n次运行
   */
  public int runTime;

  /**
   * 是否有条件
   */
  public boolean hasCondition = false;

  /**
   * 标题行
   */
  public String[] title;
  /**
   * 结果集的类型
   */
  public int[] dataType;
  /**
   * 统计行
   */
  public String[] dataSum;
  /**
   * 页面结果集
   */
  public ArrayList dataList;
  /**
   * 缓存的结果集
   */
  public ArrayList allDataList;
  /**
   * 是否有下一页
   */
  public boolean hasNextPage = false;
  /**
   * 总记录数
   */
  public int recordCount = -1;
  /**
   * 页面大小（每页记录数）
   */
  public int pageSize = -1;
  /**
   * 当前页号
   */
  public int pageNo = -1;
  /**
   * 统计条件
   */
  public ArrayList groupList,groupListY,groupListX,groupListSelect;
  /**
   * 统计条件的钻取URL
   */
  public String drillURL;

  /**
   * 是否需要X轴总计
   */
  public boolean needTotalX=true;
  /**
   * 是否需要Y轴总计
   */
  public boolean needTotalY=true;
  /**
   * 统计条件关系
   */
  public ArrayList groupRalationList;

  public StringBuffer calTimeSb = new StringBuffer();
  /**
   * 初始化函数
   */
  public QueryValue()
  {
    //dataList = new ArrayList();
    //allDataList = new ArrayList();
  }
}
