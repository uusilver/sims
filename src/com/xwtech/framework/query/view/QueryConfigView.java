package com.xwtech.framework.query.view;

/**
 *
 * <p>Title: 查询列表默认根据css文件显示样式</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 欣网信息</p>
 * @author 朱政

 */
public class QueryConfigView extends IQueryView
{
  /**
   * 取得条件区域标题文本,如果返回null,则不显示条件区域标题文本
   * @return String
   */
  protected String getConditionTitle()
  {
    return null;
  }

  /**
   * 可选框样式
   */
  protected void outputCheckBoxStyle()
  {
  }

  /**
   * 输出到页面条件区表格结束标签
   */
  protected void outputConditionAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * 输出到页面条件区表格框样式condition_area_table
   */
  protected void outputConditionAreaStart()
  {
    out.println("<table class='condition_area_table'>");
  }

  /**
   *  条件样式
   */
  protected void outputConditionStyle()
  {
  }

  /**
   * 条件标题样式
   */
  protected void outputConditionTitleStyle()
  {
    out.println(" class='condition_title_style'");
  }

  /**
   * 输出数据列表区结束标签
   */
  protected void outputDataListAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * 输出数据列表区样式
   */
  protected void outputDataListAreaStart()
  {
    out.println("<table class='data_list_area_table'>");
  }

  /**
   * 数据列表区列数据样式
   * @param nCol 列编号
   */
  protected void outputDataListColStyle(int nCol)
  {
    out.print(" class='td_col_line' ");
  }

  /**
   * 数据列表区列头样式
   */
  protected void outputDataListHeadColStyle()
  {
    out.print(" class='td_data_list_headCol'");
  }

  /**
   * 数据列表区行头样式
   */
  protected void outputDataListHeadRowStyle()
  {
    out.print(" class='data_list_headrow_style' ");
  }

  /**
   * 数据列表区行样式
   * @param nRow 行编号
   */
  protected void outputDataListRowStyle(int nRow)
  {
  }

  /**
   * 数据列表区结束标签
   */
  protected void outputFrameDataListAreaEnd()
  {
  }

  /**
   * 数据列表区开始标签
   */
  protected void outputFrameDataListAreaStart()
  {
  }

  /**
   * 输出链接样式
   */
  protected void outputLinkStyle()
  {
  }

  /**
   * 输出导航结束标签
   */
  protected void outputNavBarAreaEnd()
  {
    out.println("</table></td></tr></table>");
  }

  /**
   * 输出导航开始标签
   */
  protected void outputNavBarAreaStart()
  {
    out.println("<table class='nav_bar_area_1' >");
    out.println("  <tr class='nav_bar_area_tr' >");
    out.println("    <td class='nav_bar_area_td'><table class='nav_bar_area_2'>");
  }

  /**
   * 输出导航按钮样式
   */
  protected void outputNavButton()
  {
    if(this.qInfo.getCurrentPageNo() > 1)
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c17.jpg' alt='第一页' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "',1);\"></span>");
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() - 1) + ");\"></span>");
    }
    else
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='第一页' class='icon_hand' disabled></span>");
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' class='icon_hand' disabled></span>");
    }

    if(this.qInfo.isHasNextPage())
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() + 1) + ");\"></span>");
      if(this.qInfo.getPageCount() > 0)
        out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + this.qInfo.getPageCount() + ");\"></span>");
      else
        out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' class='icon_hand' disabled></span>");
    }
    else
    {
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' class='icon_hand' disabled></span>");
      out.print("<span class='nav_button_span'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' class='icon_hand' disabled></span>");
    }
  }

  /**
   * outputNavButtonAreaStyle
   */
  protected void outputNavButtonAreaStyle()
  {
    out.print(" class='nav_button_area_style' ");
  }

  /**
   * outputNavJump
   */
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

  /**
   * outputNavJumpAreaStyle
   */
  protected void outputNavJumpAreaStyle()
  {
    out.print(" class='nav_jump_area_style' ");
  }

  /**
   * outputQueryButtonAreaStyle
   */
  protected void outputQueryButtonAreaStyle()
  {
    out.println(" class='query_button_area_style' ");
  }

  /**
   * outputQueryButtonStyle
   */
  protected void outputQueryButtonStyle()
  {
  }

  /**
   * outputRecordStatInfo
   */
  protected void outputRecordStatInfo()
  {
  }

  /**
   * outputRecordStatInfoAreaStyle
   */
  protected void outputRecordStatInfoAreaStyle()
  {
  }

  /**
   *
   */
  protected void outputResizeAreaStyle()
  {
  }

  /**
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
  }

  protected void outputDataListStatRowStyle()
  {
  }

  protected void outputDataListStatColStyle(int nCol)
  {
  }
}
