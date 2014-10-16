package com.xwtech.framework.pub.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.*;
import java.io.*;

/**
 * @author Administrator
 *
 * Tab的Tag
 */

public class TabTag extends BodyTagSupport
{

  //级数
  private String num = null;

  //名称
  private String tabName = null;

  //TAB标签的间距
  private String width = null;

  //主TAB框是否点击才能看见内容
  private String isClick = null;

  //进入页面时显示第几个TAB
  private String several = null;

  //TAB框的宽度
  private String tabWidth = null;

  //TAB框的样式
  private String style = null;

  private StringBuffer bod;

  /**
   * @return 返回 style。
   */
  public String getStyle()
  {
    return style;
  }

  /**
   * @param style 要设置的 style。
   */
  public void setStyle(String style)
  {
    this.style = style;
  }

  /**
   * @return 返回 tabWidth。
   */
  public String getTabWidth()
  {
    return tabWidth;
  }

  /**
   * @param tabWidth 要设置的 tabWidth。
   */
  public void setTabWidth(String tabWidth)
  {
    this.tabWidth = tabWidth;
  }

  /**
   * @return 返回 several。
   */
  public String getSeveral()
  {
    return several;
  }

  /**
   * @param several 要设置的 several。
   */
  public void setSeveral(String several)
  {
    this.several = several;
  }

  /**
   * @return 返回 isClick。
   */
  public String getIsClick()
  {
    return isClick;
  }

  /**
   * @param isClick 要设置的 isClick。
   */
  public void setIsClick(String isClick)
  {
    this.isClick = isClick;
  }

  /**
   * @return 返回 width。
   */
  public String getWidth()
  {
    return width;
  }

  /**
   * @param width 要设置的 width。
   */
  public void setWidth(String width)
  {
    this.width = width;
  }

  /**
   * @return 返回 tabName。
   */
  public String getTabName()
  {
    return tabName;
  }

  /**
   * @param tabName 要设置的 tabName。
   */
  public void setTabName(String tabName)
  {
    this.tabName = tabName;
  }

  /**
   * @return 返回 num。
   */
  public String getNum()
  {
    return num;
  }

  /**
   * @param num 要设置的 num。
   */
  public void setNum(String num)
  {
    this.num = num;
  }

  public TabTag()
  {
    num = "0";
    isClick = "true";
    width = "20";
    several = "0";
    tabWidth = "100%";
    style = "table_big";
  }

  // Method called when the closing tag is encountered
  public int doStartTag() throws JspException
  {
    int mn = Integer.parseInt(num);
    if(mn < 1)
    {
      mn = 1;
    }
    int serv = Integer.parseInt(several);
    if(serv < 0)
    {
      serv = 0;
    }
    bod = new StringBuffer();
    bod = bod.append("<script type=\"text/javascript\">\n");
    bod = bod.append("var topMenuSpacer = " + width + ";\n");
    bod = bod.append("var activateSubOnClick = " + isClick + ";\n");
    bod = bod.append("var leftAlignSubItems = false;\n var activeMenuItem = false;\n var activeTabIndex = " + serv + ";\n var rememberActiveTabByCookie = true;\n");
    bod = bod.append("function Get_Cookie(name) {\n var start = document.cookie.indexOf(name+\"=\");\n var len = start+name.length+1;\n if ((!start) && (name != document.cookie.substring(0,name.length)))\n return null;\n if (start == -1) return null;\n var end = document.cookie.indexOf(\";\",len);\n if (end == -1) end = document.cookie.length;\n return unescape(document.cookie.substring(len,end));\n }\n");
    bod = bod.append("function Set_Cookie(name,value,expires,path,domain,secure) {\n expires = expires * 60*60*24*1000;\n var today = new Date();\n var expires_date = new Date( today.getTime() + (expires) );\n var cookieString = name + \"=\" +escape(value) +((expires) ? \";expires=\" + expires_date.toGMTString() : \"\") +((path) ? \";path=\" + path : \"\") +( (domain) ? \";domain=\" + domain : \"\")+((secure) ? \";secure\" : \"\");document.cookie = cookieString;\n }\n");
    bod = bod.append("function showHide(){\n if(activeMenuItem){\n activeMenuItem.className = 'inactiveMenuItem';\n var theId = activeMenuItem.id.replace(/[^\\d]/g,'');\n document.getElementById('submenu_'+theId).style.display='none';\n }\n activeMenuItem = this;this.className = 'activeMenuItem';\n var theId = this.id.replace(/[^\\d]/g,'');\n document.getElementById('submenu_'+theId).style.display='block';\n if(rememberActiveTabByCookie){\n Set_Cookie('dhtmlgoodies_tab_menu_tabIndex','index: ' + (theId-1),100);\n}\n}\n");
    bod = bod.append("function initMenu(){\n var mainMenuObj = document.getElementById('mainMenu');\n var subMenuObj = document.getElementById('submenu');\n mainMenuObj.style.visibility=subMenuObj.style.visibility=\"visible\";\n var menuItems = mainMenuObj.getElementsByTagName('A');\n if(document.all){\n mainMenuObj.style.visibility = 'hidden';\n document.getElementById('submenu').style.visibility='hidden';\n}\n");
    bod = bod.append("if(rememberActiveTabByCookie){\n var cookieValue = Get_Cookie('dhtmlgoodies_tab_menu_tabIndex') + '';\n cookieValue = cookieValue.replace(/[^\\d]/g,'');\n if(cookieValue.length>0 && cookieValue<menuItems.length){activeTabIndex = cookieValue/1;\n}\n}\n");
    bod = bod.append("var currentLeftPos = 15;\n for(var no=0;no<menuItems.length;no++){\n if(activateSubOnClick) menuItems[no].onclick = showHide;\n else menuItems[no].onmouseover = showHide;menuItems[no].id = 'mainMenuItem' + (no+1);menuItems[no].style.left = currentLeftPos + 'px';\n currentLeftPos = currentLeftPos + menuItems[no].offsetWidth + topMenuSpacer;\n if(no==activeTabIndex){\n menuItems[no].className='activeMenuItem';\n activeMenuItem = menuItems[no];\n}\n else menuItems[no].className='inactiveMenuItem';\n if(!document.all)menuItems[no].style.bottom = '-1px';\n}\n");
    bod = bod.append("var mainMenuLinks = mainMenuObj.getElementsByTagName('A');\n var subCounter = 1;\n var parentWidth = mainMenuObj.offsetWidth;\n while(document.getElementById('submenu_' + subCounter)){\n var subItem = document.getElementById('submenu_' + subCounter);\n");
    bod = bod.append("if(leftAlignSubItems){}\n else{\n var leftPos = mainMenuLinks[subCounter-1].offsetLeft;\n document.getElementById('submenu_'+subCounter).style.paddingLeft =  leftPos + 'px';\n subItem.style.position ='absolute';\n if(subItem.offsetWidth > parentWidth){\n leftPos = leftPos - Math.max(0,subItem.offsetWidth-parentWidth);\n}\n subItem.style.paddingLeft =  leftPos + 'px';\n subItem.style.position ='static';\n}\n if(subCounter==(activeTabIndex+1)){\n subItem.style.display='block';\n}\n else{\n subItem.style.display='none';\n}\n subCounter++;\n}\n");
    bod = bod.append("if(document.all){\n mainMenuObj.style.visibility = 'visible';\n document.getElementById('submenu').style.visibility='visible';\n}\n document.getElementById('submenu').style.display='block';\n}\n window.onload = initMenu;\n");
    bod = bod.append("</script>\n");
    bod = bod.append("");
    bod = bod.append("<table width=\"" + tabWidth + "\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"" + style + "\">\n");
    bod = bod.append("<tr>\n <td>\n <div id=\"mainMenu\">\n");
    if(tabName == null || tabName.equals(""))
    {
      bod = bod.append("");
    }
    else
    {
      StringTokenizer st = new StringTokenizer(tabName, "@@");
      int count = st.countTokens();
      for(int i = 0; i < count; i++)
      {
        bod = bod.append("<a>" + st.nextToken() + "</a>\n");
      }
    }
    bod = bod.append("</div>\n");
    bod = bod.append("");
    bod = bod.append("<div id=\"submenu\">\n");
    bod = bod.append("");
    String sBod = bod.toString();
    return EVAL_BODY_BUFFERED;
  }

  /**
   * 获得标记内的记录列表内容
   */
  public int doAfterBody() throws JspTagException
  {
    BodyContent bodyContent = getBodyContent();
    if(bodyContent != null)
    {
      bod.append(bodyContent.getString());
      try
      {
        bodyContent.clear();
      }
      catch(IOException ex)
      {
        throw new JspTagException("Fatal IO Error");
      }
    }
    return SKIP_BODY;
  }

  /**
   * 输出标记内的内容
   */
  public int doEndTag() throws JspTagException
  {
    BodyContent bodyContent = getBodyContent();
    try
    {
      if(bodyContent != null)
      {
        bod = bod.append("</div>\n");
        bod = bod.append("</td>\n </tr>\n </table>\n");
        //输出全部内容
        bodyContent.getEnclosingWriter().write(bod.toString());
      }
    }
    catch(IOException ex)
    {
      throw new JspTagException("Fatal IO Error");
    }

    return EVAL_PAGE;
  }

  public void release()
  {
    super.release();
  }
}
