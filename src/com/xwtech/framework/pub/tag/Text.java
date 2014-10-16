package com.xwtech.framework.pub.tag;

import com.xwtech.framework.query.bo.IHtmlet;
import javax.servlet.http.HttpServletRequest;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.utils.BeanUtils;
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
 * @author 杨永清
 * @version 1.0
 */
public class Text implements IHtmlet,IValuable
{
  protected static final Logger logger = Logger.getLogger(Text.class);
  private String type="Text";
  private String name;
  private String htmlet;
  private String value;
  private String style;//内联样式
  private String styleClass;//样式名称
  private String size;

  private String maxlength;//文本框最大长度

  public String getHtml(HttpServletRequest request)
  {
    if (StringUtils.isBlank(htmlet))
      getHtmlet(request);
    return htmlet;
  }

  public void getHtmlet(HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("<input ");
    sb.append(BeanUtils.getDescribeString(this," "));
    sb.append(" >");
    htmlet = sb.toString();
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }

  public void setStyle(String style)
  {
    this.style = style;
  }

  public void setSize(String size)
  {
    this.size = size;
  }

  public String getName()
  {
    return name;
  }

  public String getValue()
  {
    return value;
  }

  public String getStyleClass()
  {
    return styleClass;
  }

  public String getStyle()
  {
    return style;
  }

  public String getSize()
  {
    return size;
  }

  public String getType()
  {
    return type;
  }

  public String getMaxlength() {
    return maxlength;
  }

  public void setMaxlength(String maxlength) {
    this.maxlength = maxlength;
  }
}
