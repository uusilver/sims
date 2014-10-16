package com.xwtech.framework.pub.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.*;

/**
 * @author yug
 *
 * 静态Ajax树的Tag
 */
public class AjaxTreeTag extends TagSupport
{

  //sql语句
  private String sql = null;

  //js事件的名称
  private String jsevent = null;

  //图片路径
  private String url = null;

  //ajaxTreeStyle.jsp的路径
  private String styleUrl = null;

  //是否需要有添加根节点功能
  private String ifFather = null;

  //添加根节点的名称
  private String rootName = null;

  public void setSql(String sql)
  {
    this.sql = sql;
  }

  public void setJsevent(String jsevent)
  {
    this.jsevent = jsevent;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public void setStyleUrl(String styleUrl)
  {
    this.styleUrl = styleUrl;
  }

  public void setIfFather(String ifFather)
  {
    this.ifFather = ifFather;
  }

  public void setRootName(String rootName)
  {
    this.rootName = rootName;
  }

  public String getSql()
  {
    return sql;
  }

  public String getJsevent()
  {
    return jsevent;
  }

  public String getUrl()
  {
    return url;
  }

  public String getStyleUrl()
  {
    return styleUrl;
  }

  public String getIfFather()
  {
    return ifFather;
  }

  public String getRootName()
  {
    return rootName;
  }

  public AjaxTreeTag()
  {
    sql = "";
    url = "";
    rootName = "";
    ifFather = "false";
  }

  public int doStartTag() throws JspException
  {
    StringBuffer bod = new StringBuffer();
    if(ifFather.equals("true"))
    {
      if(rootName.equals(""))
      {
        bod = bod.append("<a href=\"javascript:" + jsevent + "('');void(0);\">增加父节点</a>\n");
      }
      else
      {
          bod = bod.append("<a href=\"javascript:" + jsevent + "('');void(0);\">" + rootName + "</a>\n");
      }
      bod = bod.append("");
    }
    bod = bod.append("<div style=\"display:none\">\n");
    bod = bod.append("<textarea name=\"treesql\">\n");
    bod = bod.append(""+sql+"\n");
    bod = bod.append("</textarea>\n");
    bod = bod.append("</div>\n");
    bod = bod.append("");
    bod = bod.append("<div id=\"0\"></div>\n");
    bod = bod.append("<script type=\"text/javascript\">\n");
    bod = bod.append("TreeMenu._url=\""+styleUrl+"\";\n");
    bod = bod.append("TreeMenu._jsevent=\""+jsevent+"\";\n");
    bod = bod.append("TreeMenu._img=\""+url+"\";\n");
    bod = bod.append("var processRequest=function(obj)\n");
    bod = bod.append("{\n");
    bod = bod.append("alert(obj.responseText);\n");
    bod = bod.append("}\n");
    bod = bod.append("var dd = window.onload;\n");
    bod = bod.append("window.onload = \"\";\n");
    bod = bod.append("window.onload = treetag_unload;\n");
    bod = bod.append("function treetag_unload() {\n");
    bod = bod.append("if(dd != null){\n");
    bod = bod.append("dd();\n");
    bod = bod.append("}\n");
    bod = bod.append("TreeMenu.getChildren('0');\n");
    bod = bod.append("}\n");
    bod = bod.append("</script>\n");
    String sBod = bod.toString();
    try
    {
      pageContext.getOut().print(sBod);
    }
    catch(IOException e)
    {
      throw new JspTagException("Tag: " + e.getMessage());
    }
    return SKIP_BODY;
  }

  public int doEndTag() throws JspException
  {
    return EVAL_PAGE;
  }

  public void release()
  {
    super.release();
  }

}
