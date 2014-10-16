package com.xwtech.framework.query.view;

/**
 *
 * <p>Title: 彩铃首页排行列表样式</p>
 *
 * <p>Description: http://localhost:8080/cring/jsp/user/login.jsp</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊


 */
public class QueryUserAndManpowerView extends IQueryView
{
  /**
   * 表格前部分


   */
  protected void outputDataListAreaStart()
  {
    out.println("<table class='data_list_area_table'>");
  }

  /**
   * 表格后部分


   */
  protected void outputDataListAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * 列表标题行属性


   */
  protected void outputDataListHeadRowStyle()
  {
    out.print(" class='data_list_headrow_style' ");
  }

  /**
   * 列表标题列属性


   */
  protected void outputDataListHeadColStyle()
  {
    out.print(" class='td_data_list_headCol' ");
  }

  protected void outputDataListRowStyle(int nRow)
  {
  }

  protected void outputDataListColStyle(int nCol)
  {
    out.print(" class='td_index_ring'");
  }

  protected void outputConditionAreaStart()
  {
    out.println("<table class='condition_area_table' >");
  }

  protected void outputConditionAreaEnd()
  {
    out.println("</table>");
  }

  protected void outputConditionTitleStyle()
  {
  }

  protected String getConditionTitle()
  {
    return null;
  }

  protected void outputConditionStyle()
  {
  }

  protected void outputResizeAreaStyle()
  {
  }

  protected void outputResizeListStyle()
  {
  }

  protected void outputQueryButtonAreaStyle()
  {
    out.println(" class='font_info'");
  }

  protected void outputNavBarAreaStart()
  {
    out.println("<table class='nav_bar_area_1' >");
    out.println("  <tr class='nav_bar_area_tr' >");
    out.println("    <td class='nav_bar_area_td'><table class='nav_bar_area_2'>");
  }

  protected void outputNavBarAreaEnd()
  {
    out.println("</table></td></tr></table>");
  }

  protected void outputFrameDataListAreaStart()
  {
  }

  protected void outputFrameDataListAreaEnd()
  {
  }

  protected void outputLinkStyle()
  {
  }

  protected void outputCheckBoxStyle()
  {
  }

  protected void outputRecordStatInfoAreaStyle()
  {
  }

  protected void outputRecordStatInfo()
  {
  }

  protected void outputNavButtonAreaStyle()
  {
    out.print(" class='nav_button_area_style' ");
  }

  protected void outputNavButton()
  {
    if(this.qInfo.getCurrentPageNo() > 1)
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c17.jpg' alt='第一页' class='icon_hand_1' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "',1);\"></span>");
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' class='icon_hand_1' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() - 1) + ");\"></span>");
    }
    else
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='第一页' class='icon_hand_1' disabled></span>");
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' class='icon_hand_1' disabled></span>");
    }

    if(this.qInfo.isHasNextPage())
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' class='icon_hand_1' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() + 1) + ");\"></span>");
      if(this.qInfo.getPageCount() > 0)
        out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' class='icon_hand_1' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + this.qInfo.getPageCount() + ");\"></span>");
      else
        out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' class='icon_hand_1' disabled></span>");
    }
    else
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' class='icon_hand_1' disabled></span>");
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' class='icon_hand_1' disabled></span>");
    }
  }

  protected void outputNavJumpAreaStyle()
  {
    out.print(" class='nav_jump_area_style' ");
  }

  protected void outputNavJump()
  {
    out.print("<span>");
    out.print("<input name='query_jump_pageno' type='text' size='4'");
    if(this.qInfo.isFirstShow())
      out.print(" readonly='true'");
    out.print(">");

    out.print("<input type='button' value='Go!' onclick=\"javascript:querye_jumppage('" + this.qInfo.getConditionFormName() + "',parentNode.all.query_jump_pageno," + this.qInfo.getPageCount() + ");\"");
    if(this.qInfo.isFirstShow())
      out.print(" disabled");
    out.print(">");
    out.print("</span>");
  }

  protected void outputQueryButton()
  {
    out.print("<input type='button' class='button_search_bg' value='搜 索' onclick=\"javascript:querye_toquery('" + this.qInfo.getConditionFormName() + "');\">");
  }

  protected void outputDataListSumRowStyle()
  {
  }

  protected void outputDataListSumColStyle(int nCol)
  {
  }

  protected void outputDataListStatRowStyle()
  {
  }

  protected void outputDataListStatColStyle(int nCol)
  {
  }
}
