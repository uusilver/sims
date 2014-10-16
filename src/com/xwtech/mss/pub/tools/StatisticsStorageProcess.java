package com.xwtech.mss.pub.tools;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.Date;
import java.util.Properties;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oracle.jdbc.OracleTypes;

import com.xwtech.mss.pub.tools.StatisticsStorageProcess;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;

public class StatisticsStorageProcess
{

	private static final Log log = LogFactory.getLog(StatisticsStorageProcess.class);

	private static String driverClassName = "oracle.jdbc.OracleDriver";

	private static String url = "jdbc:oracle:thin:@10.33.5.11:1521:NJZWDB4";

	//	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:myoracle";
	//	private static String url = "jdbc:oracle:thin:@192.168.4.23:1521:smnew";

	private static String user_name = "xwsx";

	private static String password = "xwsx";

	private static String FILE_PATH = "/WEB-INF/jdbc.properties";

	public static void init()
	{

		Properties propJdbc = new Properties();
		try
		{
			String path = BaseFrameworkApplication.FrameworkWebAppRootPath + FILE_PATH;

			propJdbc.load(new FileInputStream(path));

			driverClassName = (String) propJdbc.get("jdbc.driverClassName");

			url = (String) propJdbc.get("jdbc.url");

			user_name = (String) propJdbc.get("jdbc.username");

			password = (String) propJdbc.get("jdbc.password");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection()
	{
		Connection conn = null;
		try
		{
			log.info("connect to " + url + "......" + user_name);
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, user_name, password);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(ResultSet set, CallableStatement call, Connection conn)
	{
		closeResultSet(set);
		closeCallableStatement(call);
		closeConnection(conn);
	}

	/**
	 * 关闭数据库连接
	 *
	 */
	public static void closeConnection(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				if (!(conn.isClosed()))
				{
					conn.close();
					log.info("关闭数据库连接 ：" + conn.getCatalog());
				}
			}
			catch (SQLException ex)
			{
				log.error("不能关闭数据库连接", ex);
			}
			catch (RuntimeException ex)
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
		if (rs != null)
		{
			try
			{
				rs.close();
				log.info("关闭ResultSet！");
			}
			catch (SQLException ex)
			{
				log.warn("不能关闭ResultSet", ex);
			}
			catch (RuntimeException ex)
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
		if (call != null)
		{
			try
			{
				call.close();
			}
			catch (SQLException ex)
			{
				log.warn("不能关闭CallableStatement", ex);
			}
			catch (RuntimeException ex)
			{
				log.error("关闭CallableStatement时遇到未知异常", ex);
			}
		}
	}

	/**
	 * web apache 统计时报
	 * @param statisticDate
	 * call create_HourReport_WebApache
	 */
	public static int CallProcedure_WebApacheHourReport(String statisticDate,String skipOverHourStatistics)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_HourReport_WebApache(?,?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setString(2, skipOverHourStatistics);
			cs.setInt(3, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(3)).intValue();
			if (flag == 0)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " web apache 时报成功");
				}
				else
				{
					log.info("统计" + statisticDate + " web apache 日志成功(跳过时报)");
				}
			}
			else if (flag == 1)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " web apache 时报失败");
				}
				else
				{
					log.info("统计" + statisticDate + " web apache 日志失败(跳过时报)");
				}
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * web apcahe 统计日报
	 * @param statisticDate
	 * call create_DayReport_WebApache
	 */
	public static int CallProcedure_WebApacheDayReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_DayReport_WebApache(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web apache 日报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web apache 日报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * web 月报apache 统计
	 * @param statisticDate
	 * call create_MonthReport_WebApache
	 */
	public static int CallProcedure_WebApacheMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WebApache(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web apache 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web apache 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * web forum 社区访问 统计月报
	 * @param statisticDate
	 * call create_MonthReport_WebForum
	 */
	public static int CallProcedure_WebForumMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WebForum(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web 社区访问 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web 社区访问 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * web plate 板块访问 统计月报
	 * @param statisticDate
	 * call create_MonthReport_WebPlate
	 */
	public static int CallProcedure_WebPlateMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WebPlate(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web 板块访问 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web 板块访问 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * web topoic 主题访问 统计月报
	 * @param statisticDate
	 * call create_MonthReport_WebTopic
	 */
	public static int CallProcedure_WebTopicMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WebTopic(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web 主题访问 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web 主题访问 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * web user access 用户访问行为 统计月报
	 * @param statisticDate
	 * call create_MonthReport_WebUserAcc
	 */
	public static int CallProcedure_WebUserAccMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WebUserAcc(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web 用户访问行为 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web 用户访问行为 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * web user access 用户积分 统计月报
	 * @param statisticDate
	 * call create_MonthReport_WebUserPoi
	 */
	public static int CallProcedure_WebUserPointMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WebUserPoi(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " web 用户积分 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " web 用户积分 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * wap apache 统计时报
	 * @param statisticDate
	 * call create_HourReport_WapApache
	 */
	public static int CallProcedure_WapApacheHourReport(String statisticDate,String skipOverHourStatistics)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_HourReport_WapApache(?,?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setString(2, skipOverHourStatistics);
			cs.setInt(3, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(3)).intValue();
			if (flag == 0)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " wap apache 时报成功");
				}
				else
				{
					log.info("统计" + statisticDate + " wap apache 日志成功(跳过时报)");
				}
			}
			else if (flag == 1)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " wap apache 时报失败");
				}
				else
				{
					log.info("统计" + statisticDate + " wap apache 日志失败(跳过时报)");
				}
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * wap apcahe 统计日报
	 * @param statisticDate
	 * call create_DayReport_WapApache
	 */
	public static int CallProcedure_WapApacheDayReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_DayReport_WapApache(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " wap apache 日报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " wap apache 日报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * wap 月报apache 统计
	 * @param statisticDate
	 * call create_MonthReport_WapApache
	 */
	public static int CallProcedure_WapApacheMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WapApache(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " wap apache 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " wap apache 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}
	
	/**
	 * wap 栏目 统计时报
	 * @param statisticDate
	 * call create_HourReport_WapApache
	 */
	public static int CallProcedure_WapLanmuHourReport(String statisticDate,String skipOverHourStatistics)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_HourReport_WapLanmu(?,?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setString(2, skipOverHourStatistics);
			cs.setInt(3, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(3)).intValue();
			if (flag == 0)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " wap 栏目 时报成功");
				}
				else
				{
					log.info("统计" + statisticDate + " wap 栏目 日志成功(跳过时报)");
				}
			}
			else if (flag == 1)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " wap 栏目 时报失败");
				}
				else
				{
					log.info("统计" + statisticDate + " wap 栏目 日志失败(跳过时报)");
				}
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * wap 栏目 统计日报
	 * @param statisticDate
	 * call create_DayReport_WapApache
	 */
	public static int CallProcedure_WapLanmuDayReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_DayReport_WapLanmu(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " wap 栏目 日报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " wap 栏目 日报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * wap 栏目 月报 统计
	 * @param statisticDate
	 * call create_MonthReport_WapApache
	 */
	public static int CallProcedure_WapLanmuMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WapLanmu(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " wap 栏目 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " wap 栏目 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}	
	
	
	/**
	 * wap 其它统计 统计时报
	 * @param statisticDate
	 * call create_HourReport_WapOther
	 */
	public static int CallProcedure_WapOtherHourReport(String statisticDate,String skipOverHourStatistics)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_HourReport_WapOther(?,?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setString(2, skipOverHourStatistics);
			cs.setInt(3, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(3)).intValue();
			if (flag == 0)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " wap 其它统计 时报成功");
				}
				else
				{
					log.info("统计" + statisticDate + " wap 其它统计 日志成功(跳过时报)");
				}
				
			}
			else if (flag == 1)
			{
				if(skipOverHourStatistics.equals("false"))
				{
					log.info("统计" + statisticDate + " wap 其它统计 时报失败");
				}
				else
				{
					log.info("统计" + statisticDate + " wap 其它统计 日志失败(跳过时报)");
				}
				
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}

	/**
	 * wap 其它统计 统计日报
	 * @param statisticDate
	 * call create_DayReport_WapOther
	 */
	public static int CallProcedure_WapOtherDayReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_DayReport_WapOther(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " wap 其它统计 日报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " wap 其它统计 日报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;
	}

	/**
	 * wap 其它统计 月报 统计
	 * @param statisticDate
	 * call create_MonthReport_WapOther
	 */
	public static int CallProcedure_WapOtherMonthReport(String statisticDate)
	{
		int flag = 0;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			//		初始化dataSource
			StatisticsStorageProcess.init();
			//打开数据库连接
			conn = StatisticsStorageProcess.getConnection();

			String call = "{call spms_pkg.create_MonthReport_WapOther(?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.setString(1, statisticDate);
			cs.setInt(2, flag);
			cs.execute();
			flag = ((Integer) cs.getObject(2)).intValue();
			if (flag == 0)
			{
				log.info("统计" + statisticDate + " wap 其它统计 月报成功");
			}
			else if (flag == 1)
			{
				log.info("统计" + statisticDate + " wap 其它统计 月报失败");
			}
		}
		catch (Exception e)
		{
			flag = 1;
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return flag;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 利用存储过程实现报表统计
	 * add by jzy 080612
	 * 《《范例》》
	 */
	public static ResultSet getReportStatistics(int selectModel, int orgId, String areaType, String startTime, String endTime, int activityId,
			int configId, String fuzzyMaterialName, int stratNum, Connection conn, String materialTpye) throws SQLException
	{
		CallableStatement cs = null;
		ResultSet rs = null;

		try
		{
			String call = "{call DMS_PKG.REPORT_STATISTICS(?,?,?,?,?,?,?,?,?,?,?)}";
			cs = conn.prepareCall(call);
			conn.setAutoCommit(false);

			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2, selectModel);
			cs.setInt(3, orgId);
			cs.setString(4, areaType);
			cs.setString(5, startTime);
			cs.setString(6, endTime);
			cs.setInt(7, activityId);
			cs.setInt(8, configId);
			cs.setString(9, fuzzyMaterialName);
			cs.setInt(10, stratNum);
			cs.setString(11, materialTpye);
			cs.execute();
			rs = (ResultSet) cs.getObject(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, cs, conn);
		}
		return rs;
	}
	
	

}
