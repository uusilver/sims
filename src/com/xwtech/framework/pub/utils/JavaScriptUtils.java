package com.xwtech.framework.pub.utils;

import javax.servlet.jsp.JspWriter;
public class JavaScriptUtils
{
  public JavaScriptUtils()
  {
  }

  public static void  JsExec(JspWriter out,String jsSourceString) throws Exception
  {
    out.println("<script language=javascript>");
    out.println(jsSourceString);
    out.println("</script>");
  }
}
