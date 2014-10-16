package com.xwtech.framework.pub.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.xwtech.framework.query.bo.IHtmlet;
import com.xwtech.framework.query.bo.IValuable;

public class DynamicMatchTag extends TagSupport implements IHtmlet,IValuable
{
  private static long sequenceid = System.currentTimeMillis();

  private static final String DYN_TYPE_LOCALDATA = "localdata";
  private static final String DYN_TYPE_LOCALSQL = "localsql";
  private static final String DYN_TYPE_REMOTESQL = "remotesql";

  private boolean readonly;//是否只读
  private boolean disabled;//是否禁用
  private byte size;//文本框宽度

  private String value;//值

  private String style;//内联样式
  private String styleClass;//样式名称
  private String onafterquery;//当查询结束时发生
  private String name;//文本框名称

  private String title;//文本框提示文本

  private String width;//选择框宽度

  private String height;//高度
  private boolean showhscrollbar;//是否显示水平滚动条

  private boolean showvscrollbar;//是否显示垂直滚动条

  private String dataarray;//数据二维数组名称(仅对本地数据动态匹配有效)
  private String formatarray;//格式二维数组名称
  private String ondataprepare;//数据准备函数名(仅对本地数据动态匹配有效)
  private String onsqlprepare;//本地SQL准备函数名(仅对本地组装SQL远程数据动态匹配有效)
  private String refsqlname;//远程SQL引用名(仅对远程SQL数据动态匹配有效)
  private String onsqlargsprepare;//准备远程SQL所需过滤参数(仅对远程SQL数据动态匹配有效)
  private boolean autodefault;

  public DynamicMatchTag()
  {
    super();

    this.readonly = false;
    this.disabled = false;
    this.size = 0;
    this.height = "150px";
    this.showhscrollbar = false;
    this.showvscrollbar = true;
    this.autodefault = true;
  }

  public int doStartTag() throws JspException
  {
    JspWriter out = pageContext.getOut();
    try
    {
      //设置自动默认值
      if(this.autodefault && this.value == null
        && this.name != null && this.name.length()>0)
      {
        setValue(pageContext.getRequest().getParameter(this.name));
      }

      out.print(getHtml((HttpServletRequest)pageContext.getRequest()));
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    return SKIP_BODY;
  }

  /**
   * 生成Html片段
   * @param request HttpServletRequest
   * @return Html片段
   */
  public String getHtml(HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer();

    this.id = "dynmatch_" + Long.toHexString(this.sequenceid++);

    //获取类型
    String type = "";
    if(this.dataarray != null && !this.dataarray.equals(""))
      type = DYN_TYPE_LOCALDATA;
    else if(this.onsqlprepare != null && !this.onsqlprepare.equals(""))
      type = DYN_TYPE_LOCALSQL;
    else if(this.refsqlname != null && !this.refsqlname.equals(""))
      type = DYN_TYPE_REMOTESQL;

    //生成名字
    if(this.name == null || this.name.equals(""))
      this.name = this.id;

    //输出外部脚本引用
    if(request.getAttribute("DynamicMatchTag_Script_Ref") == null)
    {
      sb.append("<script type='text/javascript' src='/framework/js/dynmatch.js'></script>\n");
      request.setAttribute("DynamicMatchTag_Script_Ref","1");
    }

    sb.append("<span>");
    //输出文本框

    sb.append("<input type=\"text\"");
    sb.append(" name=\"" + this.name + "\"");
    if(this.size > 0)
      sb.append(" size=\"" + this.size + "\"");
    if(this.style != null && !this.style.equals(""))
      sb.append(" style=\"" + this.style + "\"");
    if(this.styleClass != null && !this.styleClass.equals(""))
      sb.append(" class=\"" + this.styleClass + "\"");
    if(this.disabled)
      sb.append(" disabled=\"true\"");
    if(this.readonly)
      sb.append(" readonly=\"true\"");
    if(this.title != null && !this.title.equals(""))
      sb.append(" title=\"" + this.title + "\"");
    if(this.value != null && !this.value.equals(""))
      sb.append(" value=\"" + this.value + "\"");
    sb.append(" onkeydown=\"javascript:dynmatch_onkeydown(this);\"");
    sb.append(" dynmatch_type=\"" + type + "\"");
    if(this.formatarray != null && !this.formatarray.equals(""))
      sb.append(" dynmatch_data_format=\"" + this.formatarray + "\"");
    if(this.onafterquery != null && !this.onafterquery.equals(""))
      sb.append(" dynmatch_onafterquery=\"" + this.onafterquery + "\"");
    //本地数据动态匹配

    if(type.equals(DYN_TYPE_LOCALDATA))
    {
      sb.append(" dynmatch_data=\"" + this.dataarray + "\"");
      if(this.ondataprepare != null && !this.ondataprepare.equals(""))
        sb.append(" dynmatch_ondataprepare=\"" + this.ondataprepare + "\"");
    }
    //本地组装SQL远程数据动态匹配

    else if(type.equals(DYN_TYPE_LOCALSQL))
      sb.append(" dynmatch_onsqlprepare=\"" + this.onsqlprepare + "\"");
    //远程SQL数据动态匹配

    else if(type.equals(DYN_TYPE_REMOTESQL))
    {
      sb.append(" dynmatch_refsqlname=\"" + this.refsqlname + "\"");
      if(this.onsqlargsprepare != null && !this.onsqlargsprepare.equals(""))
        sb.append(" dynmatch_onsqlargsprepare=\"" + this.onsqlargsprepare + "\"");
    }
    //其它附加属性

    sb.append(" dynmatch_code_overflow=\"");
    if(this.showhscrollbar || this.showvscrollbar)
    {
      if(this.showhscrollbar && this.showvscrollbar)
        sb.append("document.getElementById('dynmatch_data_container').style.overflow='auto';");
      else if(this.showhscrollbar)
        sb.append("document.getElementById('dynmatch_data_container').style.overflowX='auto';");
      else if(this.showvscrollbar)
        sb.append("document.getElementById('dynmatch_data_container').style.overflowY='auto';");
    }
    sb.append("\"");
    if(this.width != null && !this.width.equals(""))
      sb.append(" dynmatch_code_width=\"document.getElementById('dynmatch_data_container').style.width='" + this.width + "';\"");
    if(this.height != null && !this.height.equals(""))
      sb.append(" dynmatch_code_height=\"document.getElementById('dynmatch_data_container').style.height='" + this.height + "';\"");
    if(this.width != null && !this.width.equals(""))
      sb.append(" dynmatch_code_tablewidth = \"document.getElementById('dynmatch_table_container').width='100%'\"");
    sb.append("/>");

    return sb.toString();
  }

  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }

  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }

  public void setSize(byte size)
  {
    this.size = size;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public void setStyle(String style)
  {
    this.style = style;
  }

  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }

  public void setOnafterquery(String onafterquery)
  {
    this.onafterquery = onafterquery;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public void setWidth(String width)
  {
    this.width = width;
  }

  public void setShowhscrollbar(boolean showhscrollbar)
  {
    this.showhscrollbar = showhscrollbar;
  }

  public void setShowvscrollbar(boolean showvscrollbar)
  {
    this.showvscrollbar = showvscrollbar;
  }

  public void setDataarray(String dataarray)
  {
    this.dataarray = dataarray;
  }

  public void setFormatarray(String formatarray)
  {
    this.formatarray = formatarray;
  }

  public void setOndataprepare(String ondataprepare)
  {
    this.ondataprepare = ondataprepare;
  }

  public void setHeight(String height)
  {
    this.height = height;
  }

  public void setOnsqlprepare(String onsqlprepare)
  {
    this.onsqlprepare = onsqlprepare;
  }

  public void setRefsqlname(String refsqlname)
  {
    this.refsqlname = refsqlname;
  }

  public void setOnsqlargsprepare(String onsqlargsprepare)
  {
    this.onsqlargsprepare = onsqlargsprepare;
  }

  public void setAutodefault(boolean autodefault)
  {
    this.autodefault = autodefault;
  }

  public String getName()
  {
    return name;
  }
}
