package com.xwtech.framework.query.view;

import java.util.ArrayList;
import java.sql.Types;

/**
 * 维定义

 */
class StatDimMetaInfo
{
  public String title; //显示的标题

  public String[][] meta; //元数据键值对

  public boolean needStat; //是否需要统计(仅对Y轴有效)

  public boolean needDrill;//是否需要钻取

  public String fldName;//域名称
}

/**
 * 维相对关系定义

 */
class StatDimRelationInfo
{
  public int parentLevel; //父维标号,从1开始

  public int childLevel; //子维标号,从1开始


  String[][] keyMap; //关系值对
}

/**
 * 统计量定义

 */
class StatMetaInfo
{
  public static final int STAT_TYPE_LONG = 1;
  public static final int STAT_TYPE_DOUBLE = 2;
  public static final int STAT_TYPE_STRING = 3;

  public String title; //显示的标题

  private int type; //数值类型,STAT_TYPE_*
  public String format; //统计数值的格式(仅对浮点型有效,格式为:"0.00")



  /**
   * 是否为字符型
   * @return boolean
   */
  public boolean isTypeString()
  {
    return this.type == STAT_TYPE_STRING;
  }

  /**
   * 是否为整形

   * @return boolean
   */
  public boolean isTypeLong()
  {
    return this.type == STAT_TYPE_LONG;
  }

  /**
   * 是否为浮点型
   * @return boolean
   */
  public boolean isTypeDouble()
  {
    return this.type == STAT_TYPE_DOUBLE;
  }

  /**
   * 解析长整型,如果解析失败,返回0
   * @param src String
   * @return long
   */
  public static long parseLong(String src)
  {
    if(src == null || src.length() == 0)
      return 0;
    else
    {
      try
      {
        return Long.parseLong(src);
      }
      catch (Exception ex)
      {
        return 0;
      }
    }
  }

  /**
   * 解析浮点型,如果解析失败,返回0
   * @param src String
   * @return double
   */
  public static double parseDouble(String src)
  {
    if(src == null || src.length() == 0)
      return 0;
    else
    {
      try
      {
        return Double.parseDouble(src);
      }
      catch (Exception ex)
      {
        return 0;
      }
    }
  }

  /**
   * 格式化数值

   * @param value double
   * @return String
   */
  public String formatValue(double value)
  {
    if(this.isTypeString())
      return "";
    else if(this.isTypeLong())
      return String.valueOf((long)value);
    else if(this.isTypeDouble())
    {
      java.text.DecimalFormat fmt = new java.text.DecimalFormat(this.format);
      return fmt.format(value);
    }
    else
      return "formatValue:unsupport type";
  }


  /**
   * 将数据库类型转成内部类型
   * @param sqlType int
   */
  public void setStatQType(int sqlType)
  {
    switch(sqlType)
    {
      case Types.BIGINT:
      case Types.INTEGER:
      case Types.TINYINT:
      case Types.SMALLINT:
        this.type = STAT_TYPE_LONG;
        break;
      case Types.DECIMAL:
      case Types.DOUBLE:
      case Types.FLOAT:
      case Types.NUMERIC:
      case Types.REAL:
        this.type = STAT_TYPE_DOUBLE;
        break;
      default:
        this.type = STAT_TYPE_STRING;
    }
  }
}

/**
 * 统计信息
 */
class StatInfo
{
  public StatDimMetaInfo[] xDim; //X轴维度定义

  public StatDimMetaInfo[] yDim; //Y轴维度定义

  public StatMetaInfo[] statQ; //统计量定义


  public StatDimRelationInfo[] relXDim; //X维的层次关系
  public StatDimRelationInfo[] relYDim; //Y维的层次关系

  public boolean needXStat; //是否需要行总计
  public boolean needYStat; //是否需要列总计

  public ArrayList dataList; //数据列表

  private boolean hasXDim; //是否具有X维定义


  public StatInfo()
  {
    this.hasXDim = true;
  }

  /**
   * 设置没有X维信息

   */
  public void setNoXDim()
  {
    this.hasXDim = false;
    this.needXStat = false;
  }

  public boolean isNoXDim()
  {
    return !this.hasXDim;
  }
}

class StatNode
{
  public static final int DIM_TYPE_UNUSED = 0;
  public static final int DIM_TYPE_X = 1;
  public static final int DIM_TYPE_Y = 2;
  public static final int DIM_TYPE_Q = 10;
  public static final int DIM_TYPE_ROOT = 100;

  public static final int PH_TYPE_UNUSED = 0;
  public static final int PH_TYPE_STATQ = 1;

  public static final String KEY_SUM_TOTAL = "@sum_total";
  public static final String KEY_SUB_TOTAL = "@sub_total";

  public int dimType; //维度分类,DIM_TYPE_*
  public int phType; //占位类型

  public StatNode parent; //父节点

  public StatNode[] children; //子节点

  public String key; //关键字

  public String title; //标题

  public boolean bFlag; //布尔型标记

  public StatNode()
  {
    this.dimType = DIM_TYPE_UNUSED;
    this.phType = PH_TYPE_UNUSED;
    this.bFlag = false;
  }

  public StatNode(StatNode parent)
  {
    this.dimType = DIM_TYPE_UNUSED;
    this.phType = PH_TYPE_UNUSED;
    this.parent = parent;
    this.bFlag = false;
  }

  /**
   * 是否为统计定义

   * @return boolean
   */
  public boolean isDimTypeQ()
  {
    return this.dimType == DIM_TYPE_Q;
  }

  /**
   * 是否为X维定义

   * @return boolean
   */
  public boolean isDimTypeX()
  {
    return this.dimType == DIM_TYPE_X;
  }

  /**
   * 是否为Y维定义

   * @return boolean
   */
  public boolean isDimTypeY()
  {
    return this.dimType == DIM_TYPE_Y;
  }

  /**
   * 是否为小计占位节点

   * @return boolean
   */
  public boolean isPlaceHolderStatQ()
  {
    return this.phType == PH_TYPE_STATQ;
  }
}

class StatNodeX extends StatNode
{
  public StatNodeX()
  {
    super();
    this.dimType = DIM_TYPE_X;
  }

  public StatNodeX(StatNode parent)
  {
    super(parent);
    this.dimType = DIM_TYPE_X;
  }
}

class StatNodeY extends StatNode
{
  public StatNodeY()
  {
    super();
    this.dimType = DIM_TYPE_Y;
  }

  public StatNodeY(StatNode parent)
  {
    super(parent);
    this.dimType = DIM_TYPE_Y;
  }
}

class StatNodeQ extends StatNode
{
  public String[] values; //统计量数组


  public StatNodeQ()
  {
    super();
    this.dimType = DIM_TYPE_Q;
  }

  public StatNodeQ(StatNode parent)
  {
    super(parent);
    this.dimType = DIM_TYPE_Q;
  }
}
