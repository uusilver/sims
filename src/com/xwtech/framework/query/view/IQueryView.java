package com.xwtech.framework.query.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import com.xwtech.framework.query.bo.QueryValue;

/**
 *
 * <p>Title: 查询列表样式显示基类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊





 */
public abstract class IQueryView
{
  protected PrintWriter out;
  protected StringWriter buf;
  protected HttpServletRequest request;
  protected QueryInfo qInfo;
  protected QueryValue queryValue;

  public IQueryView()
  {
    this.buf = new StringWriter();
    this.out = new PrintWriter(buf);
  }
  /****************************************************************************
   * 以下为必须重载的方法
   ****************************************************************************/
  protected abstract void outputFrameDataListAreaStart();
  protected abstract void outputFrameDataListAreaEnd();
  protected abstract void outputNavBarAreaStart();
  protected abstract void outputNavBarAreaEnd();
  protected abstract void outputConditionAreaStart();
  protected abstract void outputConditionAreaEnd();
  protected abstract void outputDataListAreaStart();
  protected abstract void outputDataListAreaEnd();
  /**
   * 输出查询按钮
   */
  protected abstract void outputQueryButton();
  /**
   * 输出数据行样式





   */
  protected abstract void outputDataListRowStyle(int nRow);
  /**
   * 输出数据列样式





   */
  protected abstract void outputDataListColStyle(int nCol);
  /**
   * 输出数据标题行样式





   */
  protected abstract void outputDataListHeadRowStyle();
  /**
   * 输出数据标题列样式





   */
  protected abstract void outputDataListHeadColStyle();
  /**
   * 输出条件区域标题样式
   */
  protected abstract void outputConditionTitleStyle();
  /**
   * 取得条件区域标题文本,如果返回null,则不显示条件区域标题文本
   * @return String
   */
  protected abstract String getConditionTitle();
  /**
   * 输出用户选择的条件区域样式





   */
  protected abstract void outputConditionStyle();
  /**
   * 输出用户选择页面大小所在单元格样式
   */
  protected abstract void outputResizeAreaStyle();
  /**
   * 输出用户选择页面大小列表样式
   */
  protected abstract void outputResizeListStyle();
  /**
   * 输出查询按钮区域样式
   */
  protected abstract void outputQueryButtonAreaStyle();
  /**
   * 输出链接样式
   */
  protected abstract void outputLinkStyle();
  /**
   * 输出复选框样式
   */
  protected abstract void outputCheckBoxStyle();
  /**
   * 输出列表合计行样式





   */
  protected abstract void outputDataListSumRowStyle();
  /**
   * 输出列表合计列样式





   */
  protected abstract void outputDataListSumColStyle(int nCol);
  /**
   * 输出列表页面统计行样式





   */
  protected abstract void outputDataListStatRowStyle();
  /**
   * 输出列表页面统计列样式





   */
  protected abstract void outputDataListStatColStyle(int nCol);

  protected abstract void outputRecordStatInfoAreaStyle();
  protected abstract void outputRecordStatInfo();
  protected abstract void outputNavButtonAreaStyle();
  protected abstract void outputNavButton();
  protected abstract void outputNavJumpAreaStyle();
  protected abstract void outputNavJump();
  /****************************************************************************
   * 以下为可选可重载的方法





   ****************************************************************************/
  /**
   * 第一次显示提示信息





   */
  protected void outputFirstShowTip()
  {
    out.print("&#12288;&#12288;&#12288;当前未进行任何查询操作。当您选择好您所需要的查询条件后，请按上面的\"查询\"按钮开始查询。");
  }
  /**
   * 第一次显示提示信息行样式
   */
  protected void outputFirstShowRowStyle()
  {
    outputDataListRowStyle(0);
  }
  /**
   * 第一次显示提示信息列样式
   */
  protected void outputFirstShowColStyle()
  {
    outputDataListColStyle(0);
  }
  /**
   * 升序图像
   */
  protected void outputSortAscImage()
  {
    out.print("<img src='/framework/image/asc.gif'/>");
  }
  /**
   * 降序图像
   */
  protected void outputSortDescImage()
  {
    out.print("<img src='/framework/image/desc.gif'>");
  }

  /****************************************************************************
   * 以下为不可重载的方法
   ****************************************************************************/
  /**
   * 输出导航区域
   */
  protected final void outputNavBarArea()
  {
    //导航栏前部区域





    outputNavBarAreaStart();

    out.println("<tr onselectstart='javascript:return false;'>");

    //总数信息
    out.print("<td");
    outputRecordStatInfoAreaStyle();
    out.print(">");
    outputRecordStatInfo();
    out.println("</td>");

    //翻页信息
    out.print("<td");
    outputNavButtonAreaStyle();
    out.print(">");
    outputNavButton();
    out.println("</td>");

    //跳转信息
    if(this.qInfo.isCanJump())
    {
      out.print("<td");
      outputNavJumpAreaStyle();
      out.print(">");
      outputNavJump();
      out.println("</td>");
    }

    out.println("</tr>");

    //导航栏尾部区域





    outputNavBarAreaEnd();
  }

  /**
   * 底部全选框显示
   */
  private void outputSelallArea()
  {
    outputDataListAreaStart();
    out.println("<tr>");
    out.println("<td align=\"right\">全选：<input type='checkbox' onclick=\"javascript:querye_checkAll_all(this,'"+this.qInfo.getCheckBoxNameBottom()+"');\"/></td>");
    out.println("</tr>");
    outputDataListAreaEnd();
  }

  /**
   * 如果没有要输出条件区域,但是又要显示导航分页栏,则输出一个默认表单





   */
  protected final void outputHideConditionArea()
  {
    //条件区域
    if (this.qInfo.isNeedrequest())
      out.println("<form name=\"" + this.qInfo.getConditionFormName() + "\" method='post' action=\"" + QueryExhibitUtil.getRequesstURL(this.request) + "\" style=\"display:none;\">");
    else
      out.println("<form name=\"" + this.qInfo.getConditionFormName() + "\" method='post' action=\"" + QueryExhibitUtil.getRequesstURLNoRequest(this.request) + "\" style=\"display:none;\">");
    out.println("<input type=\"hidden\" name=\"query_name\" value=\"" + this.queryValue.queryName + "\"/>"); //查询名



    out.println("<input type=\"hidden\" name=\"" + this.queryValue.queryName + "_query_pageno\"/>"); //查询的页码



    out.println("<input type=\"hidden\" name=\"query_sortcol\" value=\"" + this.qInfo.getQuerySortCol() + "\"/>"); //排序列



    out.println("<input type=\"hidden\" name=\"query_sortdir\" value=\"" + this.qInfo.getQuerySortDir() + "\"/>"); //排序方法
    out.println("</form>");
  }

  /**
   * 输出条件区域
   */
  protected final void outputConditionArea()
  {
    //输出条件区域前部
    outputConditionAreaStart();
    out.println("<tr>");

    //条件区域标题
    if(getConditionTitle() != null)
    {
      out.print("<td");
      outputConditionTitleStyle();
      out.print(">");
      out.print(getConditionTitle());
      out.println("</td>");
    }

    //条件区域主内容



    out.print("<td");
    outputConditionStyle();
    out.print(">");
    out.print(this.qInfo.getConditionHTML());
    out.println("</td>");

    //自定义页面大小



    if(this.qInfo.getPageSize() > 0)
    {
      if(this.qInfo.isCanResize())
      {
        out.print("<td");
        outputResizeAreaStyle();
        out.print(">");

        out.print("<select name='query_pagesize'");
        outputResizeListStyle();
        out.print(">");

        out.println("<option value='" + this.qInfo.getPageSize() + "'>页面大小</option>");
        for(int i = 0; i < this.qInfo.getSizeList().length; i++)
          out.println("<option value='" + this.qInfo.getSizeList()[i] + "'" + (this.qInfo.getCurPageSize() == this.qInfo.getSizeList()[i] ? " selected" : "") + ">" + this.qInfo.getSizeList()[i] + "</option>\n");
        out.println("</select>");

        out.println("</td>");
      }
      else
        out.println("<td style='none'><input type='hidden' name='query_pagesize' value='" + this.qInfo.getPageSize() + "'/></td>");
    }

    //查询按钮
    if(this.qInfo.isNeedQueryButton() && this.queryValue.hasCondition)
    {
      out.print("<td");
      outputQueryButtonAreaStyle();
      out.print(">");
      outputQueryButton();
      out.println("</td>");
    }

    out.println("</tr>");
    //输出条件区域尾部
    outputConditionAreaEnd();
  }

  /**
   * 输出总计行





   */
  protected final void outputDataListSumHTML()
  {
    out.println("<tbody id=\"query_sumbody_" + Long.toHexString(this.qInfo.getCurrentSequenceid()) + "\">");
    out.print("<tr");
    outputDataListSumRowStyle();
    out.print(">");

    for(int j = 0; j < this.qInfo.getHiddenColNames().length; j++)
      out.println("<td>" + this.qInfo.HTMLEncode(this.qInfo.getSumList()[j]) + "</td>");
    for(int j = this.qInfo.getHiddenColNames().length; j < this.qInfo.getColsCount(); j++)
    {
      out.print("<td");
      outputDataListSumColStyle(j - this.qInfo.getHiddenColNames().length);
      out.print(">");
      out.print(this.qInfo.HTMLEncodeNotEmpty(this.qInfo.getSumList()[j]));
      out.println("</td>");
    }

    out.println("</tr>");
    out.println("</tbody>");
  }

  /**
   * 输出页面统计行





   */
  protected final void outputDataListStatHTML()
  {
    out.println("<tbody id=\"query_statbody_" + Long.toHexString(this.qInfo.getCurrentSequenceid()) + "\">");
    out.print("<tr");
    outputDataListStatRowStyle();
    out.println(">");

    for(int j = 0; j < this.qInfo.getColsCount(); j++)
    {
      out.print("<td");
      outputDataListStatColStyle(j - this.qInfo.getHiddenColNames().length);
      out.print(">");
      out.print("&nbsp;");
      out.println("</td>");
    }

    out.println("</tr>");
    out.println("</tbody>");
  }

  /**
   * 数据列表
   */
  public final void outputDataListDataHTML()
  {
    String[] row;

    out.println("<tbody id=\"query_listbody_" + Long.toHexString(this.qInfo.getCurrentSequenceid()) + "\">");

    //首次查询提示
    if(this.qInfo.isFirstShow())
    {
      out.print("<tr querye_hasdata=\"0\"");
      outputFirstShowRowStyle();
      out.println(">");
      out.print("<td align=\"left\" colspan=\"" + (this.qInfo.getColsCount() - this.qInfo.getHiddenColNames().length) + "\"");
      outputFirstShowColStyle();
      out.print(">");
      outputFirstShowTip();
      out.println("</td>");
      out.println("</tr>");
    }

    String[] sysRow = new String[1];
    /**
     * 数据列





     */
    for(int i = 0; i < this.qInfo.getDataList().size(); i++)
    {
      row = (String[])this.qInfo.getDataList().get(i);
      sysRow[0] = String.valueOf(i+1);

      //首行属性





      out.print("<tr querye_hasdata=\"1\"");
      outputDataListRowStyle(i);
      if(this.qInfo.getOnRowDblClick() != null && this.qInfo.getOnRowDblClick().length() > 0)
        out.print(" style=\"cursor:hand;\" ondblclick=\"javascript:" + this.qInfo.MacroReplace(this.qInfo.getOnRowDblClick(), row, sysRow) + "\"");
      out.println(">");

      //隐藏列





      for(int j = 0; j < this.qInfo.getHiddenColNames().length; j++)
        out.println("<td><input type='hidden' name='" + this.qInfo.getHiddenColNames()[j] + "' value='" + this.qInfo.HTMLEncode(row[j]) + "'/></td>");

      //普通列
      for(int j = this.qInfo.getHiddenColNames().length; j < this.qInfo.getColsCount(); j++)
      {
        out.print("<td");
        outputDataListColStyle(j - this.qInfo.getHiddenColNames().length);
        out.print(">");

        QueryColExhibit eh = this.qInfo.getColConfig()[j + 1];
        //展示类型:checkbox
        if(eh != null && eh.ExhibitType.equalsIgnoreCase("checkbox"))
        {
          out.print("<input type=\"checkbox\"");
          out.print(" name=\"" + eh.ExhibitCheckBoxName + "\"");
          out.print(" value=\"" + this.qInfo.HTMLEncode(row[j]) + "\"");
          outputCheckBoxStyle();

          //没有需要统计的列





          if((eh.onExhibitCheckBoxClick != null && eh.onExhibitCheckBoxClick.length() > 0)
              || (eh.StatCols != null && eh.StatCols.length > 0))
          {
            out.print(" onclick=\"javascript:");
            //需要统计的方法
            if(eh.StatCols != null && eh.StatCols.length > 0)
            {
              out.print("querye_tostat(this");
              for(int k = 0; k < eh.StatCols.length; k++)
                out.print("," + (eh.StatCols[k].ColNum - 1) + ",'" + eh.StatCols[k].StatMode + "'");
              out.print(");");
            }
            //自己的方法





            if(eh.onExhibitCheckBoxClick != null && eh.onExhibitCheckBoxClick.length() > 0)
              out.print(this.qInfo.MacroReplace(eh.onExhibitCheckBoxClick, row, sysRow));
            out.print("\"");
          }
          out.print("/>");
        }
        //展示类型:link
        else if(eh != null && eh.ExhibitType.equalsIgnoreCase("link"))
        {
          out.print("<a");
          if(eh.ExhibitLinkTarget != null && eh.ExhibitLinkTarget.length() > 0)
            out.print(" target=\"" + eh.ExhibitLinkTarget + "\"");
          if(eh.ExhibitLinkTitle != null && eh.ExhibitLinkTitle.length() > 0)
            out.print(" title=\"" + eh.ExhibitLinkTitle + "\"");
          if(eh.ExhibitLinkHref != null && eh.ExhibitLinkHref.length() > 0)
            out.print(" href=\"" + this.qInfo.MacroReplace(eh.ExhibitLinkHref, row, sysRow) + "\"");
          if(eh.onExhibitLinkClick != null && eh.onExhibitLinkClick.length() > 0)
            out.print(" onclick=\"" + this.qInfo.MacroReplace(eh.onExhibitLinkClick, row, sysRow) + "\"");
          outputLinkStyle();
          out.print(">");
          if(eh.ExhibitLinkText != null && eh.ExhibitLinkText.length() > 0)
            out.print(this.qInfo.MacroReplace(eh.ExhibitLinkText, row, sysRow));
          else
            out.print(this.qInfo.HTMLEncode(row[j]));
          out.print("</a>");
        }
        //展示类型:raw及其它





        else
        {
          if(eh.ExhibitRawTemplate == null || eh.ExhibitRawTemplate.length() == 0)
            out.print(this.qInfo.HTMLEncodeNotEmpty(row[j]));
          else
            out.print(this.qInfo.MacroReplace(eh.ExhibitRawTemplate, row, sysRow));
        }

        out.println("</td>");
      }
      out.println("</tr>");
    }

    //对于分页且允许空行填充的情况，输出空白行
    if(this.qInfo.getPageSize() > 0 && this.qInfo.isFixRowsCount())
    {
      for(int i = this.qInfo.getDataList().size(); i < this.qInfo.getPageSize(); i++)
      {
        out.print("<tr querye_hasdata=\"0\"");
        outputDataListRowStyle(i);
        out.println(">");
        for(int j = 0; j < this.qInfo.getHiddenColNames().length; j++)
          out.println("<td></td>");
        for(int j = this.qInfo.getHiddenColNames().length; j < this.qInfo.getColsCount(); j++)
        {
          out.print("<td");
          outputDataListColStyle(j - this.qInfo.getHiddenColNames().length);
          out.print(">");
          out.print("&nbsp;");
          out.println("</td>");
        }
        out.println("</tr>");
      }
    }
    out.println("</tbody>");
  }

  /**
   * 列表标题
   */
  protected final void outputDataListHeadHTML()
  {
    out.println("<thead onselectstart='javascript:return false;'>");

    //标题行样式





    out.print("<tr");
    outputDataListHeadRowStyle();
    out.println(">");

    //隐藏列





    for(int i = 1; i <= this.qInfo.getHiddenColNames().length; i++)
      out.println("<td>" + this.qInfo.getColsTitle()[i - 1] + "</td>");

    //普通列
    for(int i = this.qInfo.getHiddenColNames().length + 1; i <= this.qInfo.getColsCount(); i++)
    {
      QueryColExhibit eh = this.qInfo.getColConfig()[i];

      boolean skip = false;

      //列标题样式





      out.print("<td");
      outputDataListHeadColStyle();

      //checkbox列特殊处理





      if(eh != null && eh.ExhibitType.equalsIgnoreCase("checkbox"))
      {
        //全选模式:all
        if(eh.ExhibitCheckAllType.equalsIgnoreCase("all"))
        {
          out.print(">");
          out.print("<input type='checkbox' onclick=\"javascript:querye_checkAll_all(this,'" + eh.ExhibitCheckBoxName + "');\"");
          outputCheckBoxStyle();
          out.print("/>");
          skip = true;
        }
        //全选模式:raw
        else if(eh.ExhibitCheckAllType.equalsIgnoreCase("raw"))
        {
          out.print(">");
          out.print("<input type='checkbox' onclick=\"" + eh.onExhibitCheckAllRawClick + "\"");
          outputCheckBoxStyle();
          out.print("/>");
          skip = true;
        }
      }

      if(!skip)
      {
        //输出排序标题属性





        if(eh != null && eh.Sortable && !this.qInfo.isFirstShow())
        {
          out.print(" title=\"双击该标题来对该列进行排序\" style=\"cursor:hand;\"");
          out.print(" ondblclick=\"javascript:querye_tosort('" + this.qInfo.getConditionFormName() + "',this.cellIndex+1);\"");
        }
        out.println(">");

        //普通标题





        out.print(this.qInfo.getColsTitle()[i - 1]);

        //输出排序动作
        if(eh != null && eh.Sortable)
        {
          //处理排序
          if(this.qInfo.getQuerySortCol().length() >0 && this.qInfo.getQuerySortCol().equals(String.valueOf(i)))
          {
            if(this.qInfo.getQuerySortDir().equals("asc"))
              outputSortAscImage();
            else if(this.qInfo.getQuerySortDir().equals("desc"))
              outputSortDescImage();
          }
        }
      }

      out.println("</td>");
    }
    out.println("</tr>");
    out.println("</thead>");
  }

  /**
   * 样式输出
   */
  protected final void outputDataListStyleHTML()
  {
    out.println("<colgroup>");

    //隐藏列





    for(int i = 1; i <= this.qInfo.getHiddenColNames().length; i++)
      out.println("<col style='display:none;'/>");

    //普通列
    for(int i = this.qInfo.getHiddenColNames().length + 1; i <= this.qInfo.getColsCount(); i++)
    {
      QueryColExhibit eh = this.qInfo.getColConfig()[i];
      //复选框列宽度特别处理






      if(eh != null && eh.ExhibitType.equalsIgnoreCase("checkbox")
          && (eh.ExhibitCheckAllType.equalsIgnoreCase("all") || eh.ExhibitCheckAllType.equalsIgnoreCase("raw")))
      {
        if(eh.Width == null || eh.Width.length() == 0)
          eh.Width = "20px";
      }

      out.print("<col");

      //列的align属性





      if(eh != null && eh.Align != null && eh.Align.length() > 0)
        out.print(" align='" + eh.Align + "'");
      else
        out.print(" align='center'");

      //列的width属性





      if(eh != null && eh.Width != null && eh.Width.length() > 0)
        out.print(" width='" + eh.Width + "'");

      out.println("/>");
    }

    out.println("</colgroup>");
  }

  /**
   * 数据列表
   */
  protected final void outputDataListArea()
  {
    //表格前输出




    outputDataListAreaStart();
    //样式输出
    outputDataListStyleHTML();
    //标题
    outputDataListHeadHTML();
    //数据列表
    outputDataListDataHTML();
    //总计
    if(this.qInfo.getSumList() != null && this.qInfo.getSumList().length > 0)
      outputDataListSumHTML();
    //统计
    if(this.qInfo.isNeedStat())
      outputDataListStatHTML();
    //表格后输出




    outputDataListAreaEnd();

    //输出列表加载事件
    if(this.qInfo.getOnTableLoad() != null)
    {
      out.println("<script language='javascript'>");
      out.println(this.qInfo.getOnTableLoad() + "(document.getElementById('query_listbody_" + Long.toHexString(this.qInfo.getCurrentSequenceid()) + "'));");
      out.println("</script>");
    }
  }

  /**
   * 条件页面上部
   */
  protected final void outputConditionTopExtraArea()
  {
    if(this.qInfo.getCondTopExtra() != null)
      this.qInfo.getCondTopExtra().outputHTML(out,this.request);
  }

  /**
   * 条件页面下部
   */
  protected final void outputConditionBottomExtraArea()
  {
    if(this.qInfo.getCondBottomExtra() != null)
      this.qInfo.getCondBottomExtra().outputHTML(out,this.request);
  }

  /**
   * 查询页面下部
   */
  protected final void outputPageBottomExtraArea()
  {
    if(this.qInfo.getPageBottomExtra() != null)
      this.qInfo.getPageBottomExtra().outputHTML(out,this.request);
  }

  /**
   * 获取HTML
   * @param queryInfo QueryInfo
   * @return StringBuffer
   */
  public final StringBuffer getHTML(QueryInfo queryInfo, HttpServletRequest request,QueryValue qValue)
  {
    this.qInfo = queryInfo;
    this.request = request;
    this.queryValue = qValue;

    //自身脚本引用
    if(this.request.getAttribute("common_query_script") == null)
    {
      out.println("<script language='javascript' src='/framework/js/queryexhibit.js'></script>");
      this.request.setAttribute("common_query_script","1");
    }
    //外部脚本引用
    if(this.qInfo.getExternScriptSrc() != null && this.qInfo.getExternScriptSrc().length() > 0)
      out.println("<script language='" + this.qInfo.getExternScriptLanguage() + "' src='" + this.qInfo.getExternScriptSrc() + "'></script>");

    if(!this.qInfo.isConditionFormCustom())
    {
      if (this.qInfo.isNeedrequest())
        out.println("<form name=\"" + this.qInfo.getConditionFormName() + "\" method='post' action=\"" + QueryExhibitUtil.getRequesstURL(this.request) + "\"");
      else
        out.println("<form name=\"" + this.qInfo.getConditionFormName() + "\" method='post' action=\"" + QueryExhibitUtil.getRequesstURLNoRequest(this.request) + "\"");
      out.print(" style=\"display:inline;");
      if(this.qInfo.getConditionFormStyle() != null)
        out.print(this.qInfo.getConditionFormStyle());
      out.print("\">");
    }                                 

    out.println("<input type=\"hidden\" name=\"query_name\" value=\"" + this.queryValue.queryName + "\"/>"); //查询名




    out.println("<input type=\"hidden\" name=\"" + this.queryValue.queryName + "_query_pageno\"/>"); //查询的页码




    out.println("<input type=\"hidden\" name=\"query_sortcol\" value=\"" + this.qInfo.getQuerySortCol() + "\"/>"); //排序列




    out.println("<input type=\"hidden\" name=\"query_sortdir\" value=\"" + this.qInfo.getQuerySortDir() + "\"/>"); //排序方法

    //条件区域前部
    outputConditionTopExtraArea();

    //条件区域
    if(this.qInfo.isNeedCond())
      outputConditionArea();

    //条件区域后部
    outputConditionBottomExtraArea();

    if(!this.qInfo.isConditionFormCustom())
      out.println("</form>");

    //如果设置结果集表单名称，则输出表单


    if(this.qInfo.getResultFormName() != null)
    {
      out.print("<form name=\"" + this.qInfo.getResultFormName() + "\" style=\"display:inline;\"");
      out.print(" action=\"" + this.qInfo.getResultFormAction() + "\"");
      out.print(" method=\"" + this.qInfo.getResultFormMethod() + "\"");
      out.println(">");
      out.println("<input type=\"hidden\" name=\"sequenceid\" value=\"" + Long.toHexString(this.qInfo.getCurrentSequenceid()) + "\"/>");
      if(this.qInfo.getResultFormContent() != null)
        out.println(this.qInfo.getResultFormContent());
    }

    //列表框架前部
    outputFrameDataListAreaStart();

    //上部分页导航区域
    if(this.qInfo.isNeedNavTop())
      outputNavBarArea();

    //列表区域
    outputDataListArea();

    //结果集下部额外区域


    if(this.qInfo.getResultBottomExtra() != null)
      this.qInfo.getResultBottomExtra().outputHTML(out,this.request);

    //下部分页导航区域
    if(this.qInfo.isNeedNavBottom())
      outputNavBarArea();

    if (this.qInfo.isNeedSelallBottom())
      outputSelallArea();
    
    //列表框架后部
    outputFrameDataListAreaEnd();

    //查询页面后部
    outputPageBottomExtraArea();

    //如果设置结果集表单名称，则输出表单




    if(this.qInfo.getResultFormName() != null)
      out.println("</form>");

    return this.buf.getBuffer();
  }
}
