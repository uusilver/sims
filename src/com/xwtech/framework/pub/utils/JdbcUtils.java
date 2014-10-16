package com.xwtech.framework.pub.utils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import java.sql.ResultSetMetaData;
import com.xwtech.framework.pub.exception.JdbcException;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import java.util.ArrayList;

/**
 * <p>Title: Framework </p>
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
public class JdbcUtils
{
  protected static final Logger logger = Logger.getLogger(JdbcUtils.class);

  /**
   * 关闭数据库资源
   * @param rs ResultSet
   * @param stmt Statement
   * @param con Connection
   */
  public static void close(ResultSet rs, Statement stmt, Connection con)
  {
    closeResultSet(rs);
    closeStatement(stmt);
    closeConnection(con);
  }

  /**
   * 关闭数据库连接
   * @param con Connection
   */
  public static void closeConnection(Connection con)
  {
    if(con != null)
    {
      try
      {
        if(!(con.isClosed()))
          con.close();
      }
      catch(SQLException ex)
      {
        logger.error("不能关闭数据库连接", ex);
      }
      catch(RuntimeException ex)
      {
        logger.error("关闭数据库连接时遇到未知异常", ex);
      }
    }
  }

  /**
   * 关闭Statement
   * @param stmt Statement
   */
  public static void closeStatement(Statement stmt)
  {
    if(stmt != null)
    {
      try
      {
        stmt.close();
      }
      catch(SQLException ex)
      {
        logger.warn("不能关闭Statement", ex);
      }
      catch(RuntimeException ex)
      {
        logger.error("关闭Statement时遇到未知异常", ex);
      }
    }
  }

  /**
   * 关闭ResultSet
   * @param rs ResultSet
   */
  public static void closeResultSet(ResultSet rs)
  {
    if(rs != null)
    {
      try
      {
        rs.close();
      }
      catch(SQLException ex)
      {
        logger.warn("不能关闭ResultSet", ex);
      }
      catch(RuntimeException ex)
      {
        logger.error("关闭ResultSet时遇到未知异常", ex);
      }
    }
  }

  /**
   * 执行一个Sql返回一个ArrayList
   * @param sql String
   * @return ArrayList
   */
  public static ArrayList queryForList(String sql)
  {
    DataSource ds = BaseFrameworkApplication.getBaseJdbcDAO().getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    try
    {
      conn = ds.getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = resultSet.getMetaData();

      //查询数据
      ArrayList list = new ArrayList();
      while(resultSet.next())
      {
        String[] data = new String[rsmd.getColumnCount()];
        for(int i = 0; i < rsmd.getColumnCount(); i++)
        {
          data[i] = resultSet.getString(i + 1);
        }
        list.add(data);
      }
      return list;
    }
    catch(SQLException ex)
    {
      throw new JdbcException("Sql执行错误", sql, ex);
    }
    finally
    {
      JdbcUtils.close(resultSet, stmt, conn);
    }
  }

  /**
   * 执行一个Sql返回一个ArrayList
   * @param sql String
   * @return ArrayList
   */
  public static ArrayList queryForList(Connection conn,String sql)
  {
    Statement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = resultSet.getMetaData();

      //查询数据
      ArrayList list = new ArrayList();
      while(resultSet.next())
      {
        String[] data = new String[rsmd.getColumnCount()];
        for(int i = 0; i < rsmd.getColumnCount(); i++)
        {
          data[i] = resultSet.getString(i + 1);
        }
        list.add(data);
      }
      return list;
    }
    catch(SQLException ex)
    {
      throw new JdbcException("Sql执行错误", sql, ex);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
    }
  }
}
