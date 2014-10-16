package com.xwtech.framework.query.view;

/**
 * <p>Title: 后台管理的默认查询样式2(蓝色)</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊

 */
public class QueryAdminTopView extends IQueryView
{
  /**
   * 取得条件区域标题文本,如果返回null,则不显示条件区域标题文本
   *
   * @return String
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected String getConditionTitle()
  {
    return null;
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputCheckBoxStyle()
  {
  }

  /**
   * outputConditionAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * outputConditionAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionAreaStart()
  {
    out.println("<table border='0' align='center'  cellspacing='1' cellpadding='0' width='100%'>");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionTitleStyle()
  {
  }

  /**
   * outputDataListAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * outputDataListAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListAreaStart()
  {
    out.println("<table width='97%' id=\"" + this.qInfo.getTableId() + "\" border='0' cellspacing='1' cellpadding='0' class='table_normal'>");
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListColStyle(int nCol)
  {
    out.print("  class='td_normal_center'");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListHeadColStyle()
  {
    out.print(" class='td_item_center'");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListHeadRowStyle()
  {
  }

  /**
   *
   * @param nRow int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListRowStyle(int nRow)
  {
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListStatColStyle(int nCol)
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListStatRowStyle()
  {
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListSumColStyle(int nCol)
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListSumRowStyle()
  {
  }

  /**
   * outputFrameDataListAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaEnd()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaStart()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputLinkStyle()
  {
  }

  /**
   * outputNavBarAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * outputNavBarAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaStart()
  {
    out.println("<table width='97%' border='0' align='center' cellpadding='0' cellspacing='0'>");
  }

  /**
   * outputNavButton
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavButton()
  {
    int curpage = this.qInfo.getCurrentPageNo(), total = Integer.MAX_VALUE;
    if(curpage < 1) curpage = 1;
    if(this.qInfo.getRecordCount() >= 0)
    {
      total = this.qInfo.getPageCount();
      if(total == 0) total = 1;
    }

    int start = (curpage - 1) / 10;
    int end = (start + 1) * 10;
    start = start * 10 + 1;
    if(end > total) end = total;

    if(start == 1)
      out.println("<span class='font_title_normal'>&lt;</span>");
    else
      out.println("<span class='hand_hover' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (start - 1) + ");\">&lt;</span>");

    for(int i=start;i<=end;i++)
    {
      if(i != curpage)
        out.println("<span class='hand_hover' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + i + ");\">" + i + "</span>");
      else
        out.println("<span class='font_title_normal'>" + i + "</span>");
    }

    if(end >= total)
      out.println("<span class='font_title_normal'>&gt;</span>");
    else
      out.println("<span class='hand_hover' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (end + 1) + ");\">&gt;</span>");
  }

  /**
   * outputNavButtonAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavButtonAreaStyle()
  {
    out.println(" align='right'");
  }

  /**
   * outputNavJump
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavJump()
  {
    out.print("跳转:&nbsp;<input name='query_jump_pageno' type='text' class='input_goto' onkeydown=\"javascript:if(event.keyCode==13){if(!querye_jumppage('" + this.qInfo.getConditionFormName() + "',this," + this.qInfo.getPageCount() + ")){return false;};}\"");
    if(this.qInfo.isFirstShow())
      out.print(" readonly='true'");
    out.print(">&nbsp;页&nbsp;");
  }

  /**
   * outputNavJumpAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavJumpAreaStyle()
  {
    out.print(" width='100' align='right'");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputQueryButton()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputQueryButtonAreaStyle()
  {
  }

  /**
   * outputRecordStatInfo
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputRecordStatInfo()
  {
    StringBuffer sb = new StringBuffer();
    boolean skipShowCurPage = false;

    //显示总记录数,总页数信息
    if(this.qInfo.getRecordCount() >= 0)
    {
      if(this.qInfo.getRecordCount() > 0)
      {
        sb.append("共查询到<span class='font_title_normal'>" + this.qInfo.getRecordCount() + "</span>条记录");
        sb.append(",<span class='font_title_normal'>" + this.qInfo.getPageCount() + "</span>页");
      }
      else
      {
        sb.append("未查询到记录");
        skipShowCurPage = true;
      }
    }
    //无总记录数信息
    else
    {
      if(this.qInfo.getCurrentPageNo() == 1 && this.qInfo.getDataList().size() == 0)
      {
        sb.append("未查询到记录");
        skipShowCurPage = true;
      }
    }

    //当前页信息
    if(!skipShowCurPage)
    {
      if(sb.length() > 0)
        sb.append(", ");
      sb.append("当前第<span class='font_title_normal'>" + this.qInfo.getCurrentPageNo() + "</span>页");
    }

    out.println(sb.toString());
  }

  /**
   * outputRecordStatInfoAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputRecordStatInfoAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputResizeAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputResizeListStyle()
  {
  }
}
