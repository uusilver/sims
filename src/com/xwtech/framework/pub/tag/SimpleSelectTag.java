package com.xwtech.framework.pub.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.sql.ResultSet;
import com.xwtech.framework.pub.web.ContextBeanUtils;
import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊
 */
public class SimpleSelectTag extends TagSupport
{
  private String name; //选择框名称
  private String value; //选择框当前选中的项的值
  private String dataList;//"1=abc;2=c2323"
  private String emptyItem; //空项
  private String sql; //SQL查询语句，查询语句的第一列必须为值，第二列为要显示的文本

  public int doStartTag() throws JspException
  {
    JspWriter out = pageContext.getOut();
    try
    {
      out.print("<select ");
      if(this.name != null && this.name.length()>0)
        out.print(" name=\"" + this.name + "\"");
      out.println(">");

      ArrayList list = new ArrayList();
      String value = "",title ="";

      //空行
      if(this.emptyItem != null)
      {
        int idx = this.emptyItem.indexOf('=');
        if(idx != -1)
        {
          value = this.emptyItem.substring(0,idx);
          title = this.emptyItem.substring(idx+1);
        }
        else
        {
          value = this.emptyItem;
          title = this.emptyItem;
        }
        list.add(value.trim());
        list.add(title.trim());
      }

      //静态数据列表
      if(this.dataList != null && this.dataList.length()>0)
      {
        String[] arr = this.dataList.split(";");

        for(int i=0;i<arr.length;i++)
        {
          int idx = arr[i].indexOf('=');
          if(idx != -1)
          {
            value = arr[i].substring(0,idx);
            title = arr[i].substring(idx+1);
          }
          else
          {
            value = arr[i];
            title = arr[i];
          }

          list.add(value.trim());
          list.add(title.trim());
        }
      }
      //SQL查询
      else if(this.sql != null && this.sql.length()>0)
      {
        DataSource ds = (DataSource)ContextBeanUtils.getBean("dataSource",(HttpServletRequest)pageContext.getRequest());
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
          conn = ds.getConnection();
          stmt = conn.createStatement();
          rs = stmt.executeQuery(this.sql);
          while(rs.next())
          {
            value = rs.getString(1);
            if(value == null) value = "";
            title = rs.getString(2);
            if(title == null) title = "";

            list.add(value.trim());
            list.add(title.trim());
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        finally
        {
          try
          {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null && !conn.isClosed()) conn.close();
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }
        }
      }

      //输出列表区域
      for(int i=0;i<list.size();i+=2)
      {
        out.print("<option");
        out.print(" value=\"" + list.get(i) + "\"");
        if(this.value != null && this.value.equals(list.get(i)))
          out.print(" selected");
        out.print(">");
        out.print(list.get(i+1));
        out.println("</option>");
      }

      out.print("</select>");
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    return SKIP_BODY;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public void setDataList(String dataList)
  {
    this.dataList = dataList;
  }

  public void setEmptyItem(String emptyItem)
  {
    this.emptyItem = emptyItem;
  }

  public void setSql(String sql)
  {
    this.sql = sql;
  }
}
