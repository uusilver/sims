package com.tmind.framework.query.view;

/**
 *
 * <p>Title: 彩铃首页排行列表样式</p>
 *
 * <p>Description: http://localhost:8080/cring/jsp/user/login.jsp</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: tmind.com</p>
 *
 * @author 杨洋



 */
public class QueryDiySearchView extends IQueryView
{
  boolean isShowNavButton = true;
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
    out.print(" class=\"td_bottom_line\" ");
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
    out.println(" style=\"dispaly:none\"");
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
  }

  protected void outputNavBarAreaStart()
  {
    out.println("  <tr>");
    out.println("    <td height=\"30\" colspan=\"4\"><table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"6\">");
  }

  protected void outputNavBarAreaEnd()
  {
    out.println("</td></tr></table>");
  }

  protected void outputFrameDataListAreaStart()
  {
  }

  protected void outputFrameDataListAreaEnd()
  {
  }

  protected void outputLinkStyle()
  {
    out.print("class=\"link_forgot_pw\" ");
  }

  protected void outputCheckBoxStyle()
  {
    out.print(" style=\"display:none;\" ");
  }

  protected void outputRecordStatInfoAreaStyle()
  {
    out.print(" align='center'");
  }

  protected void outputRecordStatInfo()
  {
    StringBuffer sb = new StringBuffer();

    //显示总记录数,总页数信息


    if(this.qInfo.getRecordCount() >= 0)
    {
      if(this.qInfo.getRecordCount() > 0)
      {
        sb.append("共<span class=\"font_respons\">" + this.qInfo.getPageCount() + "</span>页");
        sb.append("<span class=\"font_respons\">" + this.qInfo.getRecordCount() + "</span>条结果");
      }
      else
      {
        sb.append("未查询到记录");
        isShowNavButton = false;
      }
    }
    //无总记录数信息
    else
    {
      if(this.qInfo.getCurrentPageNo() == 1 && this.qInfo.getDataList().size() == 0)
      {
        sb.append("未查询到记录");
        isShowNavButton = true;
      }
    }
      out.println(sb.toString());
  }
    protected void outputNavButtonAreaStyle()
    {
    }

    protected void outputNavButton()
    {
      int curpage = this.qInfo.getCurrentPageNo(), totalPages = Integer.MAX_VALUE;
      if(curpage < 1)curpage = 1;
      if(this.qInfo.getRecordCount() >0)
      {
        totalPages = this.qInfo.getPageCount();
        if(totalPages == 0)totalPages = 1;
      }
      if(isShowNavButton){
        //将查询到的记录分页显示，分页的导航栏以四个一组显示页码，startRange：起始组编号，endRange：末尾组编号
        int start = (curpage - 1) / 4;
        int end = (start + 1) * 4;
        start = start * 4 + 1;
        if(end > totalPages)
          end = totalPages;
        if(start <= 4)
          out.print("<img src=\"/cring/image/diy/personal_space_r9_c7.gif\" width=\"8\" height=\"11\">");
        else
          out.print("<img src=\"/cring/image/diy/personal_space_r9_c7.gif\" onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + (start - 1) +
              ");\" width=\"8\" height=\"11\" class='hover_hand'>");
        out.println("</td>");
        out.print("<td>");
        for(int i = start; i <= end; i++)
        {
          if(i != curpage)
            out.println("<span class='hover_hand' onclick=\"javascript:querye_gopage('" + this.qInfo.getConditionFormName() + "'," + i + ");\">" + i + "</span>");
          else
            out.println("<span class=\"font_user_name\">" + i + "</span>");
        }
        out.println("</td>");
        if(end >= totalPages)
          out.println("<td align=\"right\"><img src=\"/cring/image/diy/personal_space_r9_c12.gif\" width=\"8\" height=\"11\"></td>");
        else
          out.println("<td align=\"right\"><img src=\"/cring/image/diy/personal_space_r9_c12.gif\" width=\"8\" height=\"11\" class='hover_hand' onclick=\"javascript:querye_gopage('" +
              this.qInfo.getConditionFormName() + "'," + (end + 1) + ");\"></td>");
      }
    }

    protected void outputNavJumpAreaStyle()
    {
      out.print(" width=\"30\" align=\"right\"");
    }

    protected void outputNavJump()
    {
      if(isShowNavButton){
        out.println("<input name='query_jump_pageno' type='text' size=\"3\" class='input_teabar_goto'></td>");
        out.println("<td width=\"12\" align=\"center\"><input name=\"go\" type=\"button\" class=\"button_teabar_goto\" value=\"Go\" onclick=\"javascript:if(!querye_jumppage('" +
            this.qInfo.getConditionFormName() + "',query_jump_pageno," + this.qInfo.getPageCount() + ")) return false;\">");
      }
    }

    protected void outputQueryButton()
    {
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
