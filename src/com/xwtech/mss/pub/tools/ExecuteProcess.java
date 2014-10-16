package com.xwtech.mss.pub.tools;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.framework.pub.web.BaseFrameworkApplication;


/*
 * author:jzy  2009-01-09
 * titel:执行存储过程
 */
public class ExecuteProcess
{
	private static final Log log = LogFactory.getLog(ExecuteProcess.class);
	
	private static String driverClassName = "oracle.jdbc.OracleDriver";
	
	private static String url = "jdbc:oracle:thin:@192.168.16.97:1521:NJZWDB4";
//	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:myoracle";
//	private static String url = "jdbc:oracle:thin:@192.168.4.23:1521:smnew";
	
	private static String user_name = "mss";
	
	private static String password = "mss";
	
	private static String FILE_PATH = "WEB-INF/jdbc.properties";
	
	public static void init()
	{
			
			Properties propJdbc = new Properties();
		      try {
		        String path = BaseFrameworkApplication.FrameworkWebAppRootPath+FILE_PATH;
		        
		          propJdbc.load(new FileInputStream(path));
		          
		          driverClassName = (String)propJdbc.get("jdbc.driverClassName");
		          
		          url = (String)propJdbc.get("jdbc.url");
		          
		          user_name = (String)propJdbc.get("jdbc.username");
		          
		          password = (String)propJdbc.get("jdbc.password");
		          
		      }catch(Exception e){
		    	  e.printStackTrace();
		      }
		}
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = null;
	    try
	    {
	    	log.info("connect to "+url+"......"+user_name);
	      Class.forName(driverClassName);
	      conn = DriverManager.getConnection(url, user_name, password);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return conn;
	  }
	public static void close(ResultSet set,CallableStatement call,Connection conn){
		closeResultSet(set);
		closeCallableStatement(call);
		closeConnection(conn);
	}
	
	/**
	 * 关闭数据库连接
	 *
	 */
	public static void closeConnection(Connection conn){
	    if(conn != null)
	    {
	      try
	      {
	        if(!(conn.isClosed())){
	        	conn.close();
	        	log.info("关闭数据库连接 ："+conn.getCatalog());
	        }
	      }
	      catch(SQLException ex)
	      {
	        log.error("不能关闭数据库连接", ex);
	      }
	      catch(RuntimeException ex)
	      {
	        log.error("关闭数据库连接时遇到未知异常", ex);
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
	        log.info("关闭ResultSet！");
	      }
	      catch(SQLException ex)
	      {
	        log.warn("不能关闭ResultSet", ex);
	      }
	      catch(RuntimeException ex)
	      {
	        log.error("关闭ResultSet时遇到未知异常", ex);
	      }
	    }
	  }
	  
	  /**
	   * 关闭CallableStatement
	   * @param rs ResultSet
	   */
	  public static void closeCallableStatement(CallableStatement call)
	  {
	    if(call != null)
	    {
	      try
	      {
	    	  call.close();
	      }
	      catch(SQLException ex)
	      {
	        log.warn("不能关闭CallableStatement", ex);
	      }
	      catch(RuntimeException ex)
	      {
	        log.error("关闭CallableStatement时遇到未知异常", ex);
	      }
	    }
	  }
	  
		/*
		 * author: jzy 
		 * date: 09-01-09
		 * titel: 调用存储过程 利用导入数据更新用户兴趣分 
		 * event: dataOperationBO 类 updateWithImport方法
		 * operation: 1 调用存储过程 2 返回结果
		 * return: 处理结果
		 */
	  
	  public static String[] updateWithImport(String tableName,String likeId,Connection conn)
	  {
		  CallableStatement cs = null;
		  String str_call = "{call mss_pkg.updateWithImport2(?,?,?,?)}";
		  
		  String result[] = new String[2];
		  try
		  {
			  cs = conn.prepareCall(str_call);
			  //禁用自动提交
			  conn.setAutoCommit(false);
			  //设置临时表名
			  cs.setString(1,tableName);		
			  //设置兴趣id
			  cs.setInt(2,new Integer(likeId).intValue());
			  //设置输出参数
			  cs.registerOutParameter(3, Types.INTEGER);
			  cs.registerOutParameter(4, Types.INTEGER);
			  //执行
			  cs.execute();
			  //得到临时表记录总数
			  result[0] = cs.getObject(3).toString();
			  //得到更新记录总数
			  result[1] = cs.getObject(4).toString();

		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  		  
		  return result;
	  }
	  
}
