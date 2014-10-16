package com.xwtech.framework.query.view;

public class QueryColExhibit
{
  public int ColNum = 0; //对应于记录集中的列序号

  public String Align = null; //对齐属性

  public String Width = null; //列宽度

  public boolean Sortable = false; //是否排序

  public String ExhibitType = "raw"; //展示类型

  public String ExhibitRawTemplate = null; //如果展示类型为“raw”，则在这里定义要展示的模板。

  public String ExhibitLinkTarget = "_blank"; //新页目标。

  public String ExhibitLinkTitle = null; //提示文本

  public String onExhibitLinkClick = null; //onclick处理方法。

  public String ExhibitLinkHref = null; //链接。

  public String ExhibitLinkText = null; //显示文本

  public String ExhibitCheckBoxName = null; //复选框的名称

  public String onExhibitCheckBoxClick = null; //onclick处理方法

  public String ExhibitCheckAllType = "all"; //复选框的名称

  public String onExhibitCheckAllRawClick = null; //全选复选框的onclick处理方法

  public QueryColStatExhibit StatCols[] = null; //列统计定义
}
