package com.xwtech.framework.pub.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DividePagingDis  extends TagSupport
{

	private int recordTotalNum;//共多少条记录
	private int currPage;//当前第几页
	private int totalPage;//共多少页
	private String url;
	public DividePagingDis()
	{
		// TODO Auto-generated constructor stub
	}
	public int getCurrPage() {
		return currPage;
	}


	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}


	public int getRecordTotalNum() {
		return recordTotalNum;
	}


	public void setRecordTotalNum(int recordTotalNum) {
		this.recordTotalNum = recordTotalNum;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	
	public int doStartTag() throws JspException
	  {
		String html="";
		html=html+"<form  id=\"pageForm\"  action=\"\"   method=\"post\">";
		
		html=html+"<input type=\"hidden\" name=\"currPage\"  value=\""+currPage+"\">";
		html=html+"<input type=\"hidden\" name=\"totalPage\" value=\""+totalPage+"\">";
		html=html+"<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">";
		html=html+"<tr>";
		
		
		
		html=html+"<td  align=\"center\" class=\"qinggoudan_table_td1\">";
		html=html+"总计&nbsp;&nbsp;"+recordTotalNum+"&nbsp;&nbsp;条记录&nbsp;&nbsp;&nbsp;";
		if(currPage==1)
		{
			html=html+"首页&nbsp;&nbsp; 上一页 ";
		}
		if(currPage>1)
		{

			html=html+"<a href=\"javascript:gotopage(1,'pageForm','"+ url +"')\">首页</a>&nbsp;&nbsp;";
			html=html+"<a href=\"javascript:gotopage(" + (currPage-1) + ",'pageForm','"+ url +"')" + "\">上一页</a>";
		}
		if(currPage==totalPage)
		{
			html=html+" 下一页&nbsp;&nbsp; 尾页";			
		}
		if(currPage<totalPage)
		{

			html=html+"<a href=\"javascript:gotopage(" + (currPage+1)  + ",'pageForm','"+ url +"')" + "\">下一页</a>&nbsp;&nbsp;";
			html=html+"<a href=\"javascript:gotopage(" + totalPage + ",'pageForm','"+ url +"')" + "\">尾页</a>";
		}
		html=html+"&nbsp;&nbsp;第&nbsp;"+ currPage+"&nbsp;/&nbsp;"+totalPage+"&nbsp;页&nbsp;&nbsp;";
		html=html+"跳到&nbsp;";

		html=html+"<input name=\"jumpToPage\" type=\"text\"  class=\"xuqiu_page_input\"  onkeydown=\"keyDown('pageForm','"+ url +"')\"  style=\"height:18px;width:20px\">";
		html=html+"&nbsp;页</td>";
		
		
		
		html=html+"</tr></table>";
		html=html+"</form>";
		
		JspWriter out=pageContext.getOut();
		try 
		{
			out.println(html);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	  }

	  public int doEndTag() throws JspException
	  {
	    return EVAL_PAGE;
	  }


	
}
