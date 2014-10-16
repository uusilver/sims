package com.xwtech.framework.pub.tag;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import com.xwtech.framework.pub.exception.JdbcException;
import com.xwtech.framework.pub.utils.MacroString;
import com.xwtech.framework.pub.web.ContextBeanUtils;

public class DynamicMatchRemoteCore
{
  protected static final Logger logger = Logger.getLogger(DynamicMatchRemoteCore.class);

  /**
   * 通过SQL取得结果字符串
   * @param sql String SQL语句
   * @return String
   */
  public static String getLocalSQL(HttpServletRequest request,String sql)
  {
    DataSource ds = (DataSource)ContextBeanUtils.getBean("dataSource", request);
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer sb = new StringBuffer();

    //无效的SQL语句
    if(sql == null || sql.length()==0)
    {
      logger.warn("无效的SQL语句");
      return sb.toString();
    }

    try
    {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      //成功标识符
      sb.append("+ok\n");
      int m = rs.getMetaData().getColumnCount();
      int i,n = 0;
      String text;

      while(rs.next())
      {
        if(n > 0)
          sb.append('\u0002');
        n++;

        for(i=1;i<=m;i++)
        {
          text = rs.getString(i);

          if(i>1)
            sb.append('\u0001');

          if(text != null && text.length()>0)
            sb.append(text);
        }
      }
    }
    catch(SQLException ex)
    {
      sb.setLength(0);
      throw new JdbcException("", sql, ex);
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
        logger.warn("close db resource error, source sql:\n" + sql, ex);
      }
    }

    return sb.toString();
  }

  /**
   * 通过SQL引用取得结果字符串
   * @param refsqlname String SQL引用名称
   * @param args String 匹配参数
   * @return String
   */
  public static String getRemoteSQL(HttpServletRequest request,String refsqlname, String args)
    throws Exception
  {
    String sql = null;

    //无效的SQL引用名称
    if(refsqlname == null || refsqlname.length() == 0)
    {
      logger.warn("无效的SQL引用名称");
      return "";
    }

    //获得引用所对应的SQL语句
    //sql = " \tselect login_name,login_pwd from frame_login where login_name like '${EMPNAME}$'";
    sql = "com.xwtech.framework.pub.tag.DynamicMatchRemoteCore:ccc";

    if(sql == null)
      sql = "";
    else
      sql = sql.trim();

    if(sql.length()==0)
    {
      logger.warn("无效的SQL引用名称目标:" + refsqlname);
      return "";
    }

    //检查是普通查询还是方法调用
    if(sql.length() > 6 && sql.substring(0,6).equalsIgnoreCase("select"))
    {
      //普通查询
      //参数解包替换
      if(args.length() > 0)
      {
        MacroString ms = new MacroString(sql,"${","}$");
        String[] list = args.split("\u0002");
        for(int i=0;i<list.length;i++)
        {
          String[] arr = list[i].split("\u0001");
          ms.setMacroValue(arr[0],arr[1]);
        }
        sql = ms.getString(false);
      }

      //方法调用
      return getLocalSQL(request,sql);
    }
    else if(sql.indexOf(':') != -1)
    {
      //方法调用
      int idx = sql.indexOf(':');
      String className = sql.substring(0,idx);
      String methodName = sql.substring(idx + 1);

      //参数解包
      HashMap map = new HashMap();
      if(args.length()>0)
      {
        String[] list = args.split("\u0002");
        for(int i=0;i<list.length;i++)
        {
          String[] arr = list[i].split("\u0001");
          map.put(arr[0],arr[1]);
        }
      }

      try
      {
        //自定义组装SQL
        Class cls = Class.forName(className);
        Method method = cls.getMethod(methodName,new Class[]{HashMap.class});
        sql = method.invoke(null,new Object[]{map}).toString();

        //获取数据列表
        return getLocalSQL(request,sql);
      }
      catch(Exception ex)
      {
        logger.warn("调用SQL生成方法失败:" + className + "." + methodName);
        throw ex;
      }
    }
    else
      logger.warn("无法识别的SQL引用名称目标:" + refsqlname + "=" + sql);

    return "";
  }

  public static void main(String[] args)
  {
    try
    {
      System.err.println(DynamicMatchRemoteCore.getRemoteSQL(null, "abc", "EMPNAME\u0001s"));
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
