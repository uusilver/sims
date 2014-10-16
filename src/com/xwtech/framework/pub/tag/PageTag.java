package com.xwtech.framework.pub.tag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.xwtech.framework.query.bo.IHtmlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.utils.BeanUtils;
import com.xwtech.framework.pub.web.ContextBeanUtils;

import org.apache.log4j.Logger;
import com.xwtech.framework.query.bo.IValuable;

/**
 * <p>Title: Framework</p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author gdp
 * @version 1.0
 */
public class PageTag extends TagSupport
{
	protected static final Logger logger = Logger.getLogger(PageTag.class);
  
	//记录总条数
	private int totalCount = 0;
	//总页数
	private int totalPage = 0;
	//当前页
	private int currentPage = 0;
	//链接样式
	private String linkStyle = "";
	//跳转框样式
	private String inputStyle = "";
	//跳转按钮样式
	private String buttonStyle = "anniu_s_out";
	//文本样式
	private String textStyle = "page_td";
	//form名称
	private String formName = "";
	
	public String getButtonStyle() {
		return buttonStyle;
	}
	
	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public String getInputStyle() {
		return inputStyle;
	}
	
	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}
	
	public String getLinkStyle() {
		return linkStyle;
	}
	
	public void setLinkStyle(String linkStyle) {
		this.linkStyle = linkStyle;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getTextStyle() {
		return textStyle;
	}

	public void setTextStyle(String textStyle) {
		this.textStyle = textStyle;
	}

	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		try {
			out.println("<input type=\"hidden\" name=\"currentPage\" value=\"" + currentPage + "\">");
			out.println("<input type=\"hidden\" name=\"totalPage\" value=\"" + totalPage + "\">");
			out.println("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"  class=\"qinggoudan_table\">");
			out.println("<tr><td align=\"center\" class=\"" + textStyle + "\">");
			out.println("总计&nbsp;&nbsp;" + totalCount + "&nbsp;&nbsp;条记录&nbsp;&nbsp;&nbsp;&nbsp;");
			if(currentPage==1){
				out.print("&nbsp;&nbsp;首页&nbsp;&nbsp;上一页&nbsp;&nbsp;");
			}else{
				out.print("&nbsp;&nbsp;<a href=\"javascript:goPage('1')\" class=\""+linkStyle+"\">首页</a>&nbsp;&nbsp;" +
						"<a href=\"javascript:goPage('" + (currentPage-1) + "')\" class=\""+linkStyle+"\">上一页</a>&nbsp;&nbsp;");
			}
			if(currentPage==totalPage || totalPage==1){
				out.print("下一页&nbsp;&nbsp;尾页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}else{
				out.print("<a href=\"javascript:goPage('" + (currentPage+1) + "')\" class=\""+linkStyle+"\">下一页</a>&nbsp;&nbsp;");
				out.print("<a href=\"javascript:goPage('" + totalPage + "')\" class=\""+linkStyle+"\">尾页 	</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			out.print("第&nbsp;" + currentPage + "&nbsp;/&nbsp;" + totalPage + "&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.print("跳到&nbsp;<input name=\"jumpPage\" style=\"width:30px;\" type=\"text\" class=\""+inputStyle+"\" ");
			out.print("		onkeydown=\"enterkeyDown('materialApplyList')\">&nbsp;页");
			out.print("&nbsp;&nbsp;<input type=\"button\" onclick=\"enterkeyDown('btn')\" value=\"GO\" class=\""+buttonStyle+"\" onMouseOver=\"className='anniu_s_over'\" onMouseOut=\"className='anniu_s_out'\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("<script type=\"text/javascript\" language=\"javascript\">");
			out.println("function goPage(pageValue){");
			out.println("		document.all.currentPage.value=pageValue;");
			out.println("		document.getElementsByName('"+formName+"')[0].submit();");
			out.println("}");
			out.println("function enterkeyDown(oprFlag){");
			out.println("		var ieKey= window.event.keyCode;");
			out.println("		if(ieKey==13 || oprFlag==\"btn\"){");
			out.println("			event.returnValue=false;");
			out.println("			if(document.all.jumpPage.value == \"\"){");
			out.println("				alert(\"请输入跳转页码！\");");
			out.println("				return false;");
			out.println("			} else if(!checkIsNum(document.all.jumpPage.value)){");
			out.println("				alert('跳转页数应为数字！');");
			out.println("				return false;");
			out.println("			}else if(parseInt(document.all.jumpPage.value) <= 0 || parseInt(document.all.jumpPage.value) > parseInt(document.all.totalPage.value)){");
			out.println("				alert(\"跳转页数超出范围！\");");
			out.println("				return false;");
			out.println("			} else {");
			out.println("				goPage(document.all.jumpPage.value);");
			out.println("			}");
			out.println("		}");
			out.println("}");
			out.println("</script>");
		} catch (IOException ex){
			ex.printStackTrace();
		}
	
	    return SKIP_BODY;
	}

}
