package com.xwtech.framework.pub.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.xwtech.framework.query.bo.IHtmlet;
import com.xwtech.framework.query.bo.IValuable;

public class DateTimePicker extends TagSupport implements IHtmlet,IValuable
{
  private String ubound;//可选择的日期上限		格式固定为”yyyyMMdd”.
  private String lbound;//可选择的日期下限		格式固定为”yyyyMMdd”.
  private int relubound;//可选择的日期年份上限		该值为相对于当前年份的偏移量.
  private int rellbound;//可选择的日期年份下限		该值为相对于当前年份的偏移量.
  private boolean readonly;//是否为只读
  private String countDateTargetIndex;//当前日期与选择日期的差值的显示目的地的次序，默认是0
  private String countDateTarget;//当前日期与选择日期的差值的显示目的地

  private boolean canempty;//是否可以为空
  private String btnimage;//选择按钮图片
  private String btntext;//按钮文本
  private String name;//名称
  private byte size;//文本框长度

  private String style;//内联样式
  private String styleClass;//样式名称
  private boolean disabled;//是否禁用
  private String title;//提示文本
  private String format;//格式
  private String value;//初始值

  private boolean relpos;//选择框的位置是否跟随文本框

  private static long sequenceid = System.currentTimeMillis();
  private boolean autodefault;

  private String localvalue;//实际值


  /**
   * 变量初始化



   */
  public DateTimePicker()
  {
    super();

    this.format = "yyyyMMdd";
    this.rellbound = -10;
    this.relubound = 10;
    this.ubound = "";
    this.lbound = "";
    this.readonly = false;
    this.canempty = true;
//    this.btntext = "选择...";
    this.btnimage = "/mss/image/setting_contactor_2_r29_c11.jpg";
    this.disabled = false;
    this.size = 0;
    this.relpos = false;
    this.autodefault = true;
  }

  public int doStartTag() throws JspException
  {
    JspWriter out = pageContext.getOut();
    try
    {
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
    
    //请求链接
    String requestURL = request.getRequestURL().toString();
    
    String relativePath = "/";
    
    if(requestURL!=null && !requestURL.equals("")){
    	String[] StrTokens = requestURL.split("/");
    	int length = StrTokens.length;
    	relativePath = "";
    	for(int i = 0 ; i < length-4 ; i++){
    		relativePath += "../"; 
    	}
    }

    this.id = "dtp_" + Long.toHexString(this.sequenceid++);

    //设置自动默认值

    if(this.autodefault && this.name != null && this.name.length() > 0)
    {
      this.localvalue = request.getParameter(this.name);
      if(this.localvalue == null)
        this.localvalue = this.value;
    }
    else
        this.localvalue = this.value;

    //生成名字
    if(this.name == null || this.name.equals(""))
      this.name = this.id;

    //输出外部脚本引用
    if(request.getAttribute("DateTimePicker_Script_Ref") == null)
    {
      sb.append("<script type='text/javascript' src='"+request.getContextPath()+"/framework/js/calendar.js'></script>\n");
      request.setAttribute("DateTimePicker_Script_Ref","1");
    }

    sb.append("<span>");

    //输出文本框

    sb.append("<input type=\"text\" readonly=\"true\"");
    sb.append(" name=\"" + this.name + "\"");
    if(this.size > 0)
      sb.append(" size=\"" + this.size + "\"");
    if(this.style != null && !this.style.equals(""))
      sb.append(" style=\"" + this.style + "\"");
    if(this.styleClass != null && !this.styleClass.equals(""))
      sb.append(" class=\"" + this.styleClass + "\"");
    if(this.disabled)
      sb.append(" disabled=\"true\"");
    if(this.title != null && !this.title.equals(""))
      sb.append(" title=\"" + this.title + "\"");
    if(this.localvalue != null && !this.localvalue.equals(""))
      sb.append(" value=\"" + this.localvalue + "\"");
    sb.append("/>");

    sb.append("&nbsp;");

    //输出文本按钮或图片按钮

    if(this.btnimage != null && !this.btnimage.equals(""))
      sb.append("<img name='" + name + "Img' src=\"" +request.getContextPath()+ this.btnimage + "\" align=\"absmiddle\" style=\"cursor:pointer;\" class=\"icon_hand\"");
    else
      sb.append("<input type=\"button\" value=\"" + this.btntext + "\"");
    if(this.disabled || this.readonly)
      sb.append(" style=\"display:none;\"");
    sb.append(" onclick=\"javascript:dtp_showpicker(this.parentNode.getElementsByTagName('INPUT')['" + this.name + "']"
        + ",'" + this.format
        + "'," + this.canempty
        + "," + this.readonly
        + ",'" + this.ubound
        + "','" + this.lbound
        + "'," + this.relubound
        + "," + this.rellbound
        + "," + this.relpos
        + ",'" + this.countDateTargetIndex
        + "','" + this.countDateTarget
        + "','"+request.getContextPath()+"/');\"");
    sb.append("/>");

    sb.append("</span>");

    return sb.toString();
  }

  public void setUbound(String ubound)
  {
    this.ubound = ubound;
  }

  public void setLbound(String lbound)
  {
    this.lbound = lbound;
  }

  public void setRelubound(int relubound)
  {
    this.relubound = relubound;
  }

  public void setRellbound(int rellbound)
  {
    this.rellbound = rellbound;
  }

  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }

  public void setCanempty(boolean canempty)
  {
    this.canempty = canempty;
  }

  public void setBtnimage(String btnimage)
  {
    this.btnimage = btnimage;
  }

  public void setBtntext(String btntext)
  {
    this.btntext = btntext;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setSize(byte size)
  {
    this.size = size;
  }

  public void setStyle(String style)
  {
    this.style = style;
  }

  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }

  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public void setFormat(String format)
  {
    this.format = format;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public void setRelpos(boolean relpos)
  {
    this.relpos = relpos;
  }

  public void setAutodefault(boolean autodefault)
  {
    this.autodefault = autodefault;
  }

  public String getName()
  {
    return name;
  }

	public String getCountDateTargetIndex() {
	return countDateTargetIndex;
}

public void setCountDateTargetIndex(String countDateTargetIndex) {
	this.countDateTargetIndex = countDateTargetIndex;
}

	public void setCountDateTarget(String countDateTarget) {
		this.countDateTarget = countDateTarget;
	}
	  
}
