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
 * @author 杨洋



 */
public class QueryNewInditeRings extends IQueryView
{
  /**
   * 表格前部分





   */
  protected void outputDataListAreaStart()
  {
    out.println("<table valign=\"top\" width=\"100%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
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
    out.print(" style=\"display:none;\" ");
  }

  protected void outputDataListHeadColStyle()
  {
  }

  protected void outputDataListRowStyle(int nRow)
  {

  }

  protected void outputDataListColStyle(int nCol)
  {
    out.print(" class=\"indite_ring2\" ");
  }

  protected void outputConditionAreaStart()
  {
    out.println("<table border='0' width='100%' align='center' cellpadding='0' cellspacing='0'>");
  }

  protected void outputConditionAreaEnd()
  {
    out.println("</table>");
  }

  protected void outputConditionTitleStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected String getConditionTitle()
  {
    return null;
  }

  protected void outputConditionStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputResizeAreaStyle()
  {
  }

  protected void outputResizeListStyle()
  {
  }

  protected void outputQueryButtonAreaStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputNavBarAreaStart()
  {
    out.println("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
    out.println("  <tr bgcolor='#F5F5F5'>");
    out.println("    <td height='30' align='right'><table width='100%' border='0' cellspacing='7' cellpadding='0'>");
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
    out.print("class=\"link_forgot_pw\" " );
  }

  protected void outputCheckBoxStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputRecordStatInfoAreaStyle()
  {
  }

  protected void outputRecordStatInfo()
  {
  }

  protected void outputNavButtonAreaStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputNavButton()
  {
    if(this.qInfo.getCurrentPageNo() > 1)
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c17.jpg' alt='第一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "',1);\"></span>");
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() - 1) + ");\"></span>");
    }
    else
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='第一页' width='9' height='9' class='icon_hand' disabled></span>");
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' width='9' height='9' class='icon_hand' disabled></span>");
    }

    if(this.qInfo.isHasNextPage())
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() + 1) + ");\"></span>");
      if(this.qInfo.getPageCount() > 0)
        out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + this.qInfo.getPageCount() + ");\"></span>");
      else
        out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='icon_hand' disabled></span>");
    }
    else
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' width='9' height='9' class='icon_hand' disabled></span>");
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='icon_hand' disabled></span>");
    }
  }

  protected void outputNavJumpAreaStyle()
  {
    out.print(" style='width:70px' align='right'");
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
