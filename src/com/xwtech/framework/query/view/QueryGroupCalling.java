package com.xwtech.framework.query.view;

public class QueryGroupCalling extends IQueryView
{
  public QueryGroupCalling()
  {
  }

  protected String getConditionTitle()
  {
    return null;
  }

  protected void outputCheckBoxStyle()
  {
  }

  protected void outputConditionAreaEnd()
  {
    out.println("</table>");
  }

  protected void outputConditionAreaStart()
  {
    out.println("<table border='0' width='100%' align='center' cellpadding='0' cellspacing='0'>");
  }

  protected void outputConditionStyle()
  {
  }

  protected void outputConditionTitleStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputDataListAreaEnd()
  {
    out.println("</table>");
  }

  protected void outputDataListAreaStart()
  {
    out.println("<table valign=\"top\" width=\"100%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
  }

  protected void outputDataListColStyle(int nCol)
  {
    out.print(" class=\"td_index_ring1\" ");
  }

  protected void outputDataListHeadColStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputDataListHeadRowStyle()
  {

  }

  protected void outputDataListRowStyle(int nRow)
  {

  }

  protected void outputDataListStatColStyle(int nCol)
  {
  }

  protected void outputDataListStatRowStyle()
  {
  }

  protected void outputDataListSumColStyle(int nCol)
  {
  }

  protected void outputDataListSumRowStyle()
  {
  }

  protected void outputFrameDataListAreaEnd()
  {
  }

  protected void outputFrameDataListAreaStart()
  {
  }

  protected void outputLinkStyle()
  {
  }

  protected void outputNavBarAreaEnd()
  {
    out.println("</table>");
  }

  protected void outputNavBarAreaStart()
  {
    out.println("<table valign=\"top\" width=\"100%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
  }

  protected void outputNavButtonAreaStyle()
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

  }

  protected void outputRecordStatInfoAreaStyle()
  {
  }

  protected void outputRecordStatInfo()
  {
  }

  protected void outputNavButton()
  {
    if(this.qInfo.getCurrentPageNo() > 1)
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c17.jpg' alt='第一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" +
          this.qInfo.getConditionFormName() + "',1);\"></span>");
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" +
          this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() - 1) + ");\"></span>");
    }
    else
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='第一页' width='9' height='9' class='icon_hand' disabled></span>");
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' width='9' height='9' class='icon_hand' disabled></span>");
    }

    if(this.qInfo.isHasNextPage())
    {
      out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" +
          this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() + 1) + ");\"></span>");
      if(this.qInfo.getPageCount() > 0)
        out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='icon_hand' onclick=\"javascript:querye_gopage('" +
            this.qInfo.getConditionFormName() + "'," + this.qInfo.getPageCount() + ");\"></span>");
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

    out.print("<input type='button' value='Go!' onclick=\"javascript:querye_jumppage('" + this.qInfo.getConditionFormName() + "',parentNode.all.query_jump_pageno," + this.qInfo.getPageCount() +
        ");\"");
    if(this.qInfo.isFirstShow())
      out.print(" disabled");
    out.print(">");
    out.print("</span>");
  }

  protected void outputQueryButton()
  {
  }

}
