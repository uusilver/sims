package com.tmind.framework.query.view;

public class QueryGroupMeasure extends IQueryView
{
  /**
   * 取得条件区域标题文本,如果返回null,则不显示条件区域标题文本
   *
   * @return String
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected String getConditionTitle()
  {
    return null;
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
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

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputConditionStyle()
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputConditionTitleStyle()
  {
  }


  protected void outputDataListAreaEnd()
  {
    out.println("</table>");
  }


  protected void outputDataListAreaStart()
  {
    out.println("<table width=\"100%\" id=\""+this.qInfo.getTableId()+ "\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
  }


  protected void outputDataListColStyle(int nCol)
  {
    out.print(" align=\"center\" class=\"td_index_ring\" ");
  }


  protected void outputDataListHeadColStyle()
  {
     out.print(" style=\"display:none;\" ");
  }


  protected void outputDataListHeadRowStyle()
  {
  }

  /**
   *
   * @param nRow int
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputDataListRowStyle(int nRow)
  {
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputDataListStatColStyle(int nCol)
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputDataListStatRowStyle()
  {
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputDataListSumColStyle(int nCol)
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputDataListSumRowStyle()
  {
  }

  /**
   * outputFrameDataListAreaEnd
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaEnd()
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaStart()
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputLinkStyle()
  {
  }

  /**
   * outputNavBarAreaEnd
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * outputNavBarAreaStart
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaStart()
  {
    out.println("<table valign=\"top\" width=\"100%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
  }

  /**
   * outputNavButton
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputNavButton()
  {
    if(this.qInfo.getCurrentPageNo() > 1)
     {
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c17.jpg' alt='第一页' width='9' height='9' class='hover_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "',1);\"></span>");
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' width='9' height='9' class='hover_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() - 1) + ");\"></span>");
     }
     else
     {
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c17.jpg' alt='第一页' width='9' height='9' class='hover_hand' disabled></span>");
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c19.jpg' alt='上一页' width='9' height='9' class='hover_hand' disabled></span>");
     }

     if(this.qInfo.isHasNextPage())
     {
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' width='9' height='9' class='hover_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (this.qInfo.getCurrentPageNo() + 1) + ");\"></span>");
       if(this.qInfo.getPageCount() > 0)
         out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='hover_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + this.qInfo.getPageCount() + ");\"></span>");
       else
         out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='hover_hand' disabled></span>");
     }
     else
     {
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c21.jpg' alt='下一页' width='9' height='9' class='hover_hand' disabled></span>");
       out.print("<span style='width:16'><img src='/cring/image/user/search_ring_r12_c23.jpg' alt='最后一页' width='9' height='9' class='hover_hand' disabled></span>");
     }
  }

  /**
   * outputNavButtonAreaStyle
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputNavButtonAreaStyle()
  {
    //out.println(" height=\"60\" background=\"/cring/image/diy/personal_space_r6_c1.gif\" class=\"td_bottom_line_2h\"");
  }

  /**
   * outputNavJump
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
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
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputNavJumpAreaStyle()
  {
    out.print(" style='width:70px' align='right'");
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputQueryButton()
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputQueryButtonAreaStyle()
  {
  }

  /**
   * outputRecordStatInfo
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
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
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputRecordStatInfoAreaStyle()
  {
     out.println(" height=\"60\" align=\"center\" class=\"td_bottom_line_2h\"");
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputResizeAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.tmind.framework.query.view.IQueryView method
   */
  protected void outputResizeListStyle()
  {
  }
}
