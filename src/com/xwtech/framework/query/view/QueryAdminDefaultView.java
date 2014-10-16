package com.xwtech.framework.query.view;

/**
 * <p>Title: 后台管理的默认查询样式</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊
 */
public class QueryAdminDefaultView extends IQueryView
{
  /**
   * 取得条件区域标题文本,如果返回null,则不显示条件区域标题文本
   *
   * @return String
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected String getConditionTitle()
  {
    return "Search";
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
    out.println("<table border='0' align='center' cellpadding='0' cellspacing='0' class='table_big'>");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionStyle()
  {
    out.print(" class='td_search'");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionTitleStyle()
  {
    out.print("  class='td_search' width='60px'");
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
    out.println("<table width='100%' id=\"" + this.qInfo.getTableId() + "\" border='0' cellspacing='2' cellpadding='0'>");
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListColStyle(int nCol)
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListHeadColStyle()
  {
    out.print(" class='td_search_title'");
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
    if(nRow % 2 == 0)
      out.print(" class='td_search_list_1'");
    else
      out.print(" class='td_search_list_2'");
  }

  /**
   * outputFrameDataListAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaEnd()
  {
    out.println("    </td>");
    out.println("  </tr>");
    out.println("</table>");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaStart()
  {
    out.println("<table  width='100%' align='center' cellpadding='0' cellspacing='0' class='table_big'>");
    out.println("  <tr>");
    out.println("    <td class='td_search_padding'>");
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
    out.println("      </table>");
    out.println("    </td>");
    out.println("  </tr>");
    out.println("</table>");
  }

  /**
   * outputNavBarAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaStart()
  {
    out.println("<table width='100%'  border='0' cellspacing='2' cellpadding='0'>");
    out.println("  <tr>");
    out.println("    <td height='28' align='right' valign='bottom' class='td_next'>");
    out.println("      <table width='100%' border='0' cellspacing='3' cellpadding='0'>");
  }

  /**
   * outputNavButton
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavButton()
  {
    if(this.qInfo.getCurrentPageNo() > 1)
    {
      out.print("<input type='button' class='button_select' value='|<' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "',1);\">");
      out.print("<input type='button' class='button_select' value='<' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() - 1) + ");\">");
    }
    else
    {
      out.print("<input type='button' class='button_select' value='|<' disabled>");
      out.print("<input type='button' class='button_select' value='<' disabled>");
    }

    if(this.qInfo.isHasNextPage())
    {
      out.print("<input type='button' class='button_select' value='>' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() + 1) + ");\">");
      if(this.qInfo.getPageCount() > 0)
        out.print("<input type='button' class='button_select' value='>|' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + this.qInfo.getPageCount() + ");\">");
      else
        out.print("<input type='button' class='button_select' value='>|' disabled>");
    }
    else
    {
      out.print("<input type='button' class='button_select' value='>' disabled>");
      out.print("<input type='button' class='button_select' value='>|' disabled>");
    }
  }

  /**
   * outputNavButtonAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavButtonAreaStyle()
  {
    out.print(" align='right'");
  }

  /**
   * outputNavJump
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavJump()
  {
    out.print("<span>");
    out.print("<input name='query_jump_pageno' type='text' class='input_go'");
    if(this.qInfo.isFirstShow())
      out.print(" readonly='true'");
    out.print(">");

    out.print("<input type='button' class='button_select' value='Go!' onclick=\"javascript:querye_jumppage('" + this.qInfo.getConditionFormName() + "',parentNode.all.query_jump_pageno," + this.qInfo.getPageCount() + ");\"");
    if(this.qInfo.isFirstShow())
      out.print(" disabled");
    out.print(">");
    out.print("</span>");
  }

  /**
   * outputNavJumpAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavJumpAreaStyle()
  {
    out.print(" style='width:70px' align='right'");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputQueryButtonAreaStyle()
  {
    out.print(" class='td_search' width='60px'");
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
        sb.append("共查询到 " + this.qInfo.getRecordCount() + " 条记录");
        sb.append(", " + this.qInfo.getPageCount() + " 页");
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
      sb.append("当前第 " + this.qInfo.getCurrentPageNo() + " 页");
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
    out.print(" class='td_search' width='65px'");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputResizeListStyle()
  {
  }

  protected void outputQueryButton()
  {
    out.print("<input type='button' class='button_search' value='查询' onclick=\"javascript:querye_toquery('" + this.qInfo.getConditionFormName() + "');\">");
  }

  protected void outputDataListSumRowStyle()
  {
  }

  protected void outputDataListSumColStyle(int nCol)
  {
    out.print(" class=\"td_search_bottom_1\"");
  }

  protected void outputDataListStatRowStyle()
  {
  }

  protected void outputDataListStatColStyle(int nCol)
  {
    out.print(" class=\"td_search_bottom_2\"");
  }
}
