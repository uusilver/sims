package com.xwtech.framework.pub.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

/**
 * @author yug
 * 
 * @mender:gu_daping
 *
 * 双向选择框的Tag
 */
public class SetectTag extends TagSupport
{
  //左边列表框的值
  ArrayList arl = new ArrayList();

  //左边列表的预设值
  ArrayList preArl = new ArrayList();
  
  //右边列表框的值
  ArrayList arr = new ArrayList();

  //右边列表的预设值
  ArrayList preArr = new ArrayList();
  
  //左边标题显示
  private String selectItem = null;

  //右边标题显示
  private String waitItem = null;

  //左边是否可以上下排序
  private String leftSort = null;

  //右边是否可以上下排序
  private String rightSort = null;

  //左边列表框的id值
  private String leftId = null;

  //左边列表框的value值
  private String leftValue = null;

  //右边列表框的id值
  private String rightId = null;

  //右边列表框的value值
  private String rightValue = null;

  //整个双向select框的宽度
  private String width = null;

  //整个双向select框的偏向
  private String align = null;

  //标题的样式
  private String itemStyle = null;

  //移动按钮的样式
  private String moveStyle = null;

  //select框的样式
  private String selectStyle = null;

  //左边列表框的name值
  private String leftName = null;

  //右边列表框的name值
  private String rightName = null;

  private static long secq = System.currentTimeMillis();

  //左边列表框的id
  private String lid = null;

  //左边列表框的id
  private String rid = null;

  //列表的size
  private Long size = new Long(10);
  
  /**
   * @return 返回 leftName。
   */
  public String getLeftName()
  {
    return leftName;
  }

  /**
   * @param leftName 要设置的 leftName。
   */
  public void setLeftName(String leftName)
  {
    this.leftName = leftName;
  }

  /**
   * @return 返回 rightName。
   */
  public String getRightName()
  {
    return rightName;
  }

  /**
   * @param rightName 要设置的 rightName。
   */
  public void setRightName(String rightName)
  {
    this.rightName = rightName;
  }

  /**
   * @return 返回 selectStyle。
   */
  public String getSelectStyle()
  {
    return selectStyle;
  }

  /**
   * @param selectStyle 要设置的 selectStyle。
   */
  public void setSelectStyle(String selectStyle)
  {
    this.selectStyle = selectStyle;
  }

  /**
   * @return 返回 moveStyle。
   */
  public String getMoveStyle()
  {
    return moveStyle;
  }

  /**
   * @param moveStyle 要设置的 moveStyle。
   */
  public void setMoveStyle(String moveStyle)
  {
    this.moveStyle = moveStyle;
  }

  /**
   * @return 返回 itemStyle。
   */
  public String getItemStyle()
  {
    return itemStyle;
  }

  /**
   * @param itemStyle 要设置的 itemStyle。
   */
  public void setItemStyle(String itemStyle)
  {
    this.itemStyle = itemStyle;
  }

  /**
   * @return 返回 align。
   */
  public String getAlign()
  {
    return align;
  }

  /**
   * @param align 要设置的 align。
   */
  public void setAlign(String align)
  {
    this.align = align;
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
   * @return 返回 leftId。
   */
  public String getLeftId()
  {
    return leftId;
  }

  /**
   * @param leftId 要设置的 leftId。
   */
  public void setLeftId(String leftId)
  {
    this.leftId = leftId;
  }

  /**
   * @return 返回 leftValue。
   */
  public String getLeftValue()
  {
    return leftValue;
  }

  /**
   * @param leftValue 要设置的 leftValue。
   */
  public void setLeftValue(String leftValue)
  {
    this.leftValue = leftValue;
  }

  /**
   * @return 返回 rightId。
   */
  public String getRightId()
  {
    return rightId;
  }

  /**
   * @param rightId 要设置的 rightId。
   */
  public void setRightId(String rightId)
  {
    this.rightId = rightId;
  }

  /**
   * @return 返回 rightValue。
   */
  public String getRightValue()
  {
    return rightValue;
  }

  /**
   * @param rightValue 要设置的 rightValue。
   */
  public void setRightValue(String rightValue)
  {
    this.rightValue = rightValue;
  }

  /**
   * @return 返回 leftSort。
   */
  public String getLeftSort()
  {
    return leftSort;
  }

  /**
   * @param leftSort
   *            要设置的 leftSort。
   */
  public void setLeftSort(String leftSort)
  {
    this.leftSort = leftSort;
  }

  /**
   * @return 返回 rightSort。
   */
  public String getRightSort()
  {
    return rightSort;
  }

  /**
   * @param rightSort
   *            要设置的 rightSort。
   */
  public void setRightSort(String rightSort)
  {
    this.rightSort = rightSort;
  }

  /**
   * @return 返回 arl。
   */
  public ArrayList getArl()
  {
    return arl;
  }

  /**
   * @param arl
   *            要设置的 arl。
   */
  public void setArl(ArrayList arl)
  {
    this.arl = arl;
  }

  public ArrayList getPreArl() {
	return preArl;
}

public void setPreArl(ArrayList preArl) {
	this.preArl = preArl;
}

/**
   * @return 返回 arr。
   */
  public ArrayList getArr()
  {
    return arr;
  }

  /**
   * @param arr
   *            要设置的 arr。
   */
  public void setArr(ArrayList arr)
  {
    this.arr = arr;
  }

  public ArrayList getPreArr() {
	return preArr;
}

public void setPreArr(ArrayList preArr) {
	this.preArr = preArr;
}

/**
   * @return 返回 selectItem。
   */
  public String getSelectItem()
  {
    return selectItem;
  }

  /**
   * @param selectItem
   *            要设置的 selectItem。
   */
  public void setSelectItem(String selectItem)
  {
    this.selectItem = selectItem;
  }

  /**
   * @return 返回 waitItem。
   */
  public String getWaitItem()
  {
    return waitItem;
  }
  
  public Long getSize() {
	return size;
}

public void setSize(Long size) {
	this.size = size;
}

/**
   * @param waitItem
   *            要设置的 waitItem。
   */
  public void setWaitItem(String waitItem)
  {
    this.waitItem = waitItem;
  }

  private static Method getMethod(Method[] objMethods, String strMethodName)
  {
    for(int i = 0; i < objMethods.length; i++)
    {
      if(objMethods[i].getName().equalsIgnoreCase("get" + strMethodName))
      {
        return objMethods[i];
      }
    }
    return null;
  }

  private static String getValue(ArrayList myarr, int j, String name)
  {
    String result = null;
    if(myarr == null || myarr.size() == 0)
    {
      result = "";
    }
    else
    {
      try
      {
        String className = myarr.get(j).getClass().getName();
        Class c = Class.forName(className);
        Method[] objMethods = c.getMethods();
        Method obj = getMethod(objMethods, name);
        Object res = obj.invoke(myarr.get(j), null);
        result = String.valueOf(res);
      }
      catch(Exception e1)
      {
        e1.printStackTrace();
      }
    }
    return result;
  }

  public SetectTag()
  {
    selectItem = "";
    waitItem = "";
    rightSort = "false";
    leftSort = "false";
    width = "80%";
    align = "center";
    itemStyle = "item_two_select";
    moveStyle = "button_select";
    selectStyle = "two_select";
    leftName = "";
    rightName = "";
  }

  // Method called when the closing tag is encountered
  public int doStartTag() throws JspException
  {

    lid = "Select" + String.valueOf(secq++);
    rid = "Wait" + String.valueOf(secq++);
    StringBuffer body = new StringBuffer();
    body = body
           .append("<script language=\"javascript\">\n function openwin(url, l, t, w ,h){\n open(url,'','toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width='+w +',height='+ h +',left='+ l +',top='+ t);\n}\n");
    body = body
           .append("function MoveSingleItem(sel_source, sel_dest){\n if (sel_source.selectedIndex==-1)\n return;\n var slength = sel_source.options.length;\n for(i=0;i<sel_source.options.length; i++){\n if(sel_source.options[i].selected){\n var newOption = document.createElement(\"option\"); \n newOption.text = sel_source.options[i].text; \n newOption.value = sel_source.options[i].value; \n sel_dest.add(newOption);\n sel_source.options.remove(i);i=i-1;\n}\n}\n}\n");
    body = body
           .append("function MoveAllItems(sel_source, sel_dest){\n var sel_source_len = sel_source.length;\n for (var j=0; j<sel_source_len; j++){\n var newOption = document.createElement(\"option\"); \n newOption.text = sel_source.options[j].text; \n newOption.value = sel_source.options[j].value;\n sel_dest.add(newOption);\n }\n while((k=sel_source.length-1)>=0){\n sel_source.options.remove(k);\n}\n}\n");
    body = body
           .append("function SelectAll(theSel){\n for (i = 0 ;i<theSel.length;i++)\n theSel.options[i].selected = true;\n}\n");
    body = body.append("var curOption=new Option();\n");
    body = body
           .append("function up(obj){\n if(obj.selectedIndex!=-1){\n var obj1=obj.options(obj.selectedIndex);\n if (obj.selectedIndex>0)\n obj1.swapNode(obj1.previousSibling);\n}\n}\n");
    body = body
           .append(
           "function down(obj){\n if(obj.selectedIndex!=-1){\n var obj1=obj.options(obj.selectedIndex);\n if (obj.selectedIndex<obj.options.length-1)\n obj1.swapNode(obj1.nextSibling);\n}\n}\n");
    body = body
           .append(
           "function swapOption(obj){\n if(curOption!=null && curOption!=obj.options[getIndex(obj)] && getIndex(obj)>-1 && getIndex(obj)<obj.size)\n curOption.swapNode(obj.options[getIndex(obj)]);\n}\n");
    body = body
           .append("function getIndex(obj){\n var theIndex=Math.floor((event.offsetY+2)/(obj.offsetHeight/obj.size));\n if(theIndex<0)\n theIndex=0;\n else if(theIndex>obj.options.length)\n theIndex=obj.options.length;\n return theIndex;\n}\n");
    body = body.append("</script>\n");
    body = body.append("");
    body = body.append("<table width=\"" + width + "\" align=\"" + align + "\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n");
    body = body.append("<tr>\n <td width=\"48%\" align=center valign=\"top\">\n");
    body = body.append("<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
    body = body.append("<tr bgcolor=\"#FFFFFF\">\n");
    body = body.append("<td width=\"3\" height=\"23\">&nbsp;</td>\n <td valign=\"bottom\">\n");
    body = body.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
    body = body.append("<tr><td class=\"" + itemStyle + "\">" + selectItem + "</td></tr></table></td>\n");
    body = body.append("<td align=\"right\" valign=\"bottom\">\n");
    body = body.append("<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
    if(leftSort.equals("true"))
    {
      body = body.append("<tr><td align=\"right\"><button class=\"" + moveStyle + "\" onclick=\"up(" + lid + ")\">↑</button></td>\n");
      body = body.append("<td width=\"25\" align=\"right\"><button class=\"" + moveStyle + "\" onclick=\"down(" + lid + ")\">↓</button></td></tr>\n");
    }
    body = body.append("</table>\n </td>\n <td width=\"3\">&nbsp;</td>\n </tr>\n </table>\n");
    body = body.append("<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"3\">\n");
    body = body.append("<tr>\n <td bgcolor=\"#FFFFFF\" align=\"center\">\n");
    body = body.append("<select name=\"" + leftName + "\" size=" + size + " multiple  class=\"" + selectStyle + "\" id=\"" + lid + "\" ondblclick=\"MoveSingleItem(" + lid + ", " + rid + ")\" >\n");
    if(arl == null || arl.size() == 0)
    {
      body = body.append("");
    }
    else
    {
      for(int i = 0; i < arl.size(); i++)
      {
		String lValue = getValue(arl, i, leftId);
		body = body.append("<option value=" + lValue);
		for(int ik=0; ik<preArl.size(); ik++){
			String preLValue = getValue(preArl, ik, leftId);
			if(preLValue!=null && preLValue.equals(lValue)){
				body.append("selected=\"selected\"");
			}
		}
		body.append(">" + getValue(arl, i, leftValue) + "</option>\n");
      }
    }
    body = body.append("</select>\n </td>\n </tr>\n </table>\n </td>\n ");
    body = body.append("<td width=\"4%\" align=\"center\" valign=\"middle\">\n");
    body = body.append("<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
    body = body.append("<tr>\n <td height=\"159\" align=\"center\" bgcolor=\"#FFFFFF\">\n");
    body = body.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n");
    body = body.append("<tr><td height=\"37\"><button class=\"" + moveStyle + "\" onClick=\"MoveAllItems(" + lid + ", " + rid + ")\">&gt;&gt;</button></td></tr>\n");
    body = body.append("<tr><td height=\"37\"><button class=\"" + moveStyle + "\" onClick=\"MoveSingleItem(" + lid + ", " + rid + ")\">&gt;</button></td></tr>\n");
    body = body.append("<tr><td height=\"37\"><button class=\"" + moveStyle + "\" onClick=\"MoveSingleItem(" + rid + ", " + lid + ")\">&lt;</button></td></tr>\n");
    body = body.append("<tr><td height=\"37\"><button class=\"" + moveStyle + "\" onClick=\"MoveAllItems(" + rid + ", " + lid + ")\">&lt;&lt;</button></td></tr>\n");
    body = body.append("</table>\n </td>\n </tr>\n </table>\n </td>\n");
    body = body.append("<td width=\"48%\" align=center valign=\"top\">\n");
    body = body.append("<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
    body = body.append("<tr bgcolor=\"#FFFFFF\"><td width=\"3\">&nbsp;</td>\n");
    body = body.append("<td height=\"23\" valign=\"bottom\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
    body = body.append("<tr><td class=\"" + itemStyle + "\">" + waitItem + "</td></tr></table></td>\n");
    body = body.append("<td valign=\"bottom\">\n");
    body = body.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
    if(rightSort.equals("true"))
    {
      body = body.append("<tr><td align=\"right\"><button class=\"" + moveStyle + "\" onclick=\"up(" + rid + ")\">↑</button></td>\n");
      body = body.append("<td width=\"25\" align=\"right\"><button class=\"" + moveStyle + "\" onclick=\"down(" + rid + ")\">↓</button></td></tr>\n");
    }
    body = body.append("</table></td><td width=\"3\">&nbsp;</td></tr></table>\n");
    body = body.append("<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\">\n");
    body = body.append("<tr><td bgcolor=\"#FFFFFF\" align=\"center\">\n");
    body = body.append("<select name=\"" + rightName + "\" size=" + size + " multiple=\"true\" class=\"" + selectStyle + "\" id=\"" + rid + "\" ondblclick=\"MoveSingleItem(" + rid + ", " + lid + ")\" >\n");
    if(arr == null || arr.size() == 0)
    {
      body = body.append("");
    }
    else
    {
      for(int i = 0; i < arr.size(); i++)
      {
    	String rValue = getValue(arr, i, rightId);
        body = body.append("<option value=" + rValue);
        for(int ik=0; ik<preArr.size(); ik++){
			String preRValue = getValue(preArr, ik, rightId);
			if(preRValue!=null && preRValue.equals(rValue)){
				body.append(" selected=\"selected\" ");
			}
		}
        body.append(">" + getValue(arr, i, rightValue) + "</option>\n");
      }
    }
    body = body.append("</select>\n </td></tr></table></td></tr></table>\n");
    body = body.append("");
    String sBody = body.toString();
    try
    {
      pageContext.getOut().print(sBody);
    }
    catch(IOException e)
    {
      throw new JspTagException("Tag: " + e.getMessage());
    }
    return SKIP_BODY;
  }

  // Method called when the closing tag is encountered
  public int doEndTag() throws JspException
  {
    return EVAL_PAGE;
  }

  public void release()
  {
    super.release();
	}
}
