package com.xwtech.mss.pub.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.utils.FileUtils;
import com.xwtech.mss.pub.constants.SpmsConstants;

/**
 * 
 * @author jzy
 *
 */
public class analyzeUtil
{
	private final static Logger _logger = Logger.getLogger(analyzeUtil.class);

	/**
	 * 
	 * @param str 将作为oracle insertSQL的字符串内容
	 * 函数将特殊符合转义
	 * @return
	 */
	public static String transferOracleString(String str)
	{
		// 转义单引号
		str = str.replaceAll("'", "''");

		return str;
	}

	/**
	 *
	 * @param str 	需要解析的字符串
	 * @param pattern 解析方式 正则表达式
	 * @return String[] 解析后的结果 字符串不符合正则 return null
	 */
	public static String[] scanByRegular(String str, String pattern)
	{
		String[] strResult = null;
		try
		{
			Pattern pat = Pattern.compile(pattern);
			Matcher m = pat.matcher(str);
			if (!m.matches())
			{
				//匹配失败返回null
				return null;
			}

			//实例扫描对象
			Scanner s = new Scanner(str);

			//设置规则（正则表达式）
			s.findInLine(pattern);
			//得到结果
			MatchResult result = s.match();

			//创建返回结果的字符串数组
			strResult = new String[result.groupCount()];

			for (int i = 0; i < result.groupCount(); i++)
			{
				strResult[i] = result.group(i);
			}
			s.close();
		}
		catch (IllegalStateException ie)
		{
			//匹配失败
			ie.printStackTrace();
			return null;
		}
		catch (PatternSyntaxException pe)
		{
			//正则表单式错误
			pe.printStackTrace();
			return null;
		}
		return strResult;
	}

	/**
	 * 
	 * @param ste
	 * @param pattern
	 * @return
	 */
	public static boolean matches(String str, String pattern)
	{
		Pattern pat = Pattern.compile(pattern);
		Matcher m = pat.matcher(str);
		return m.matches();
	}

	/**
	 * 
	 * @param str 解析的字符串
	 * @param returnKey	返回结果的键
	 * @param pattern 解析方式 正则
	 * @return map 匹配失败返回null
	 */
	public static Map scanByRegular(String str, String[] returnKey, String pattern)
	{
		//创建返回结果集
		Map resultMap = new HashMap();
		try
		{
			//实例扫描对象
			Scanner s = new Scanner(str);

			Pattern pat = Pattern.compile(pattern);
			Matcher m = pat.matcher(str);
			if (!m.matches())
			{
				//匹配失败返回null
				return null;
			}

			//设置规则（正则表达式）
			s.findInLine(pattern);
			//得到结果
			MatchResult result = s.match();

			//如果key和解析结果不对应 返回null
			if (result.groupCount() != returnKey.length + 1)
			{
				return null;
			}

			for (int i = 0; i < returnKey.length; i++)
			{
				//以键值对存储结果
				resultMap.put(returnKey[i], result.group(i + 1));
			}
			s.close();
		}
		catch (IllegalStateException ie)
		{
			//匹配失败
			ie.printStackTrace();
			return null;
		}
		catch (PatternSyntaxException pe)
		{
			//正则表单式错误
			pe.printStackTrace();
			return null;
		}
		return resultMap;
	}

	/**
	 * 与scanByRegular唯一的区别是判断结果时returnKey.length后没有+1
	 * @param str
	 * @param returnKey
	 * @param pattern
	 * @return
	 */
	public static Map _scanByRegular(String str, String[] returnKey, String pattern)
	{
		//创建返回结果集
		Map resultMap = new HashMap();
		try
		{
			//实例扫描对象
			Scanner s = new Scanner(str);

			Pattern pat = Pattern.compile(pattern);
			Matcher m = pat.matcher(str);
			if (!m.matches())
			{
				//匹配失败返回null
				return null;
			}

			//设置规则（正则表达式）
			s.findInLine(pattern);
			//得到结果
			MatchResult result = s.match();

			//如果key和解析结果不对应 返回null
			if (result.groupCount() != returnKey.length)
			{
				return null;
			}

			for (int i = 0; i < returnKey.length; i++)
			{
				//以键值对存储结果
				resultMap.put(returnKey[i], result.group(i + 1));
			}
			s.close();
		}
		catch (IllegalStateException ie)
		{
			//匹配失败
			ie.printStackTrace();
			return null;
		}
		catch (PatternSyntaxException pe)
		{
			//正则表单式错误
			pe.printStackTrace();
			return null;
		}
		return resultMap;
	}

	/**
	 * 通过正则pattern寻找符合的字符串，寻找到几个就返回几个，以数组形式返回
	 * 函数名Single表示pattern（正则表达式）中只能有一个分组（一个括号）
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static String[] findSingleArray(String str, String pattern)
	{
		List list = new ArrayList();
		try
		{
			Matcher m = Pattern.compile(pattern).matcher(str);

			while (m.find())
			{
				list.add(m.group(1));
			}
		}
		catch (IllegalStateException ie)
		{
			//匹配失败
			ie.printStackTrace();
			return null;
		}
		catch (PatternSyntaxException pe)
		{
			//正则表单式错误
			pe.printStackTrace();
			return null;
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String findSingle(String str, String pattern)
	{
		try
		{
			Matcher m = Pattern.compile(pattern).matcher(str);

			if (m.find())
			{
				return m.group(1);
			}
		}
		catch (IllegalStateException ie)
		{
			//匹配失败
			ie.printStackTrace();
			return "";
		}
		catch (PatternSyntaxException pe)
		{
			//正则表单式错误
			pe.printStackTrace();
			return "";
		}
		return "";
	}

	/**
	 * 包含业务逻辑的方法
	 * 分拣系统日志
	 *  
	 * @param file
	 * @param type
	 * @param pattern
	 * @param returnKey
	 * @param resultTabel
	 * @param dao
	 * @throws Exception
	 */
	public static String[] separateSysLog(File file, String type, String pattern, String[] returnKey, String resultTabel, BaseDao dao)
			throws Exception
	{
		//处理结果
		String[] result = new String[3];
		//未提交正确记录集
		List listUnInsert = new ArrayList();
		//	总记录数
		int intSum = 0;
		//错误记录数
		int intErr = 0;
		//正确记录数
		int intCorrect = 0;
		//未提交记录数
		int intUnInsert = 0;

		/*FileInputStream fs = new FileInputStream(file);
		 BufferedReader br = new BufferedReader(new InputStreamReader(fs, "utf-8"));//建立BufferedReader对象，并实例化为br
		 String strRead;

		 FileChannel fc = FileUtils.getChannel(fs, new FileDescriptor());// fs.getChannel();   
		 FileLock fl = fc.tryLock();

		 if (!fl.isValid())
		 {
		 _logger.info("未能得到" + file.getPath() + " 的锁，文件被其它程序占用，放弃分拣...");
		 }
		 else
		 {
		 _logger.info("得到" + file.getPath() + " 的锁");
		 }*/

		RandomAccessFile reader = new RandomAccessFile(file, "rw");
		String strRead;

		FileChannel fc = reader.getChannel();

		FileLock fl = fc.tryLock();
		
		try
		{
			if (null!=fl && fl.isValid())
			{
				_logger.info("得到" + file.getPath() + " 的锁");
			}
			else if(null==fl || !fl.isValid())
			{
				_logger.info("未能得到" + file.getPath() + " 的锁，文件被其它程序占用，放弃分拣...");
				return null;
			}

			while ((strRead = reader.readLine()) != null)
			{
				//转码
				strRead = new String(strRead.getBytes("ISO8859_1"), "utf-8");
				
				intSum++; //总记录数加1

				Map resultMap = analyzeUtil.scanByRegular(strRead, returnKey, pattern);

				if (resultMap != null)//匹配成功
				{
					intCorrect++; //正确数加1
					intUnInsert++; //正确记录未插入数加1
					listUnInsert.add(resultMap);//存入未提交正确记录集

					if (intUnInsert == 200) //正确记录达到200条时  执行数据插入
					{
						//拼写sql脚本
						StringBuffer sb_sql = new StringBuffer();
						sb_sql.append("insert into ");
						sb_sql.append(resultTabel);
						sb_sql.append(" select SEQ_SPMS_WEB_LOG_TMP.NEXTVAL,");//log_id
						sb_sql.append(" t.*");
						/*sb_sql.append(" t.area_id,");
						 sb_sql.append(" t.ip,");
						 sb_sql.append(" t.time_time,");
						 sb_sql.append(" t.method,");
						 sb_sql.append(" t.version,");
						 sb_sql.append(" t.bytes,");
						 sb_sql.append(" t.mobile_num,");
						 sb_sql.append(" t.mobile_type,");
						 sb_sql.append(" t.uri,");
						 sb_sql.append(" t.status,");
						 sb_sql.append(" t.state,");
						 sb_sql.append(" t.log_date,");
						 sb_sql.append(" t.log_time");*/
						sb_sql.append(" from (");

						Iterator it = listUnInsert.iterator();

						while (it.hasNext())
						{
							HashMap map = (HashMap) it.next();

							//转换日期和时间格式
							String date = CommonOperation.engMonthToNum(map.get(SpmsConstants.LOG_DATE).toString());
							String time = CommonOperation.timeToNum(map.get(SpmsConstants.LOG_TIME).toString());

							//获得号码和手机型号
							String mobile_num = findSingle(map.get(SpmsConstants.LOG_URI).toString(), SpmsConstants.FIND_PHONE_REGULAR);
							String mobile_type = findSingle(map.get(SpmsConstants.LOG_URI).toString(), SpmsConstants.FIND_MOBILE_TYPE_REGULAR);

							String bytes = map.get(SpmsConstants.LOG_BYTES).toString();

							if (bytes.equals("-") || bytes.equals(""))
							{
								bytes = "null";
							}

							if (mobile_type == null || "".equals(mobile_type))
							{
								mobile_type = map.get(SpmsConstants.LOG_USER_AGENT).toString();
							}

							if (mobile_num == null || "".equals(mobile_num))
							{
								mobile_num = "null";
							}

							sb_sql.append(" select ");
							sb_sql.append("'" + "' \"area_id\","); //area_id
							sb_sql.append("'" + map.get(SpmsConstants.LOG_IP) + "' \"ip\","); //ip
							sb_sql.append("'" + date + time + "' \"date_time\","); //date-time
							sb_sql.append("'" + map.get(SpmsConstants.LOG_METHOD) + "' \"method\","); //方法 method
							sb_sql.append("'" + map.get(SpmsConstants.LOG_VERSION) + "' \"version\","); //版本 version
							sb_sql.append("" + bytes + " \"bytes\","); //流量 bytes
							sb_sql.append("" + mobile_num + " \"mobile_num\","); //手机号码
							sb_sql.append("'" + mobile_type + "' \"mobile_type\","); //手机型号
							sb_sql.append("'" + map.get(SpmsConstants.LOG_URI).toString().replaceAll("&", "'||chr(38)||'") + "' \"uri\","); //URI
							sb_sql.append("'" + map.get(SpmsConstants.LOG_STATUS) + "' \"status\","); //请求状态
							sb_sql.append("'" + SpmsConstants.STAT_STATE + "' \"state\","); //统计状态
							sb_sql.append("'" + date + "' \"log_date\","); //日期
							sb_sql.append("'" + time + "' \"log_time\","); //时刻
							sb_sql.append("'" + CommonOperation.getSystemTime("dateTime") + "' \" SEPARATETIME\""); //分拣时刻

							sb_sql.append(" from dual");
							sb_sql.append(" union all ");
						}

						//删除最后一个union
						sb_sql.delete(sb_sql.lastIndexOf("union"), sb_sql.length());

						sb_sql.append(" )t");

						//执行sql
						dao.executeCommonSql(sb_sql.toString());
						//未提交记录数清零
						intUnInsert = 0;
						//清零未提交正确记录集
						listUnInsert.clear();
					}
				}
				else
				//匹配失败
				{
					//数据校验失败
					intErr++; //错误数加1
					//记录失败日志
					String sql = "insert into spms_err_log select SEQ_SPMS_LOG_ERR.NEXTVAL,'" + type + "','" + file.getName() + "','"
							+ CommonOperation.getSystemTime("dateTime") + "','" + transferOracleString(strRead) + "' from dual";

					//执行sql
					dao.executeCommonSql(sql.toString());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//在这里捕获异常为的是在finally中关闭锁和流
			//再次抛出 让上层捕获记录日志
			throw new Exception();
		}
		finally
		{
			//读完关闭
			if (null != fl && fl.isValid())
			{
				fl.release();
				_logger.info("释放" + file.getPath() + " 的锁");
			}
			reader.close();

		}

		if (intUnInsert > 0) //将剩余记录插入数据库
		{

			//			拼写sql脚本
			StringBuffer sb_sql = new StringBuffer();
			sb_sql.append("insert into ");
			sb_sql.append(resultTabel);
			sb_sql.append(" select SEQ_SPMS_WEB_LOG_TMP.NEXTVAL,");//log_id
			sb_sql.append(" t.*");
			/*sb_sql.append(" t.area_id,");
			 sb_sql.append(" t.ip,");
			 sb_sql.append(" t.time_time,");
			 sb_sql.append(" t.method,");
			 sb_sql.append(" t.version,");
			 sb_sql.append(" t.bytes,");
			 sb_sql.append(" t.mobile_num,");
			 sb_sql.append(" t.mobile_type,");
			 sb_sql.append(" t.uri,");
			 sb_sql.append(" t.status,");
			 sb_sql.append(" t.state,");
			 sb_sql.append(" t.log_date,");
			 sb_sql.append(" t.log_time");*/
			sb_sql.append(" from (");

			Iterator it = listUnInsert.iterator();

			while (it.hasNext())
			{
				HashMap map = (HashMap) it.next();

				//转换日期和时间格式
				String date = CommonOperation.engMonthToNum(map.get(SpmsConstants.LOG_DATE).toString());
				String time = CommonOperation.timeToNum(map.get(SpmsConstants.LOG_TIME).toString());

				//获得号码和手机型号
				String mobile_num = findSingle(map.get(SpmsConstants.LOG_URI).toString(), SpmsConstants.FIND_PHONE_REGULAR);
				String mobile_type = findSingle(map.get(SpmsConstants.LOG_URI).toString(), SpmsConstants.FIND_MOBILE_TYPE_REGULAR);

				String bytes = map.get(SpmsConstants.LOG_BYTES).toString();

				if (bytes.equals("-") || bytes.equals(""))
				{
					bytes = "null";
				}

				if (mobile_type == null || "".equals(mobile_type))
				{
					mobile_type = map.get(SpmsConstants.LOG_USER_AGENT).toString();
				}

				if (mobile_num == null || "".equals(mobile_num))
				{
					mobile_num = "null";
				}

				sb_sql.append(" select ");
				sb_sql.append("'" + "' \"area_id\","); //area_id
				sb_sql.append("'" + map.get(SpmsConstants.LOG_IP) + "' \"ip\","); //ip
				sb_sql.append("'" + date + time + "' \"date_time\","); //date-time
				sb_sql.append("'" + map.get(SpmsConstants.LOG_METHOD) + "' \"method\","); //方法 method
				sb_sql.append("'" + map.get(SpmsConstants.LOG_VERSION) + "' \"version\","); //版本 version
				sb_sql.append("" + bytes + " \"bytes\","); //流量 bytes
				sb_sql.append("" + mobile_num + " \"mobile_num\","); //手机号码
				sb_sql.append("'" + mobile_type + "' \"mobile_type\","); //手机型号
				sb_sql.append("'" + map.get(SpmsConstants.LOG_URI).toString().replaceAll("&", "'||chr(38)||'") + "' \"uri\","); //URI
				sb_sql.append("'" + map.get(SpmsConstants.LOG_STATUS) + "' \"status\","); //请求状态
				sb_sql.append("'" + SpmsConstants.STAT_STATE + "' \"state\","); //统计状态
				sb_sql.append("'" + date + "' \"log_date\","); //日期
				sb_sql.append("'" + time + "' \"log_time\","); //时刻
				sb_sql.append("'" + CommonOperation.getSystemTime("dateTime") + "' \" SEPARATETIME\""); //分拣时刻

				sb_sql.append(" from dual");
				sb_sql.append(" union all ");
			}

			//删除最后一个union
			sb_sql.delete(sb_sql.lastIndexOf("union"), sb_sql.length());

			sb_sql.append(" )t");

			//执行sql
			dao.executeCommonSql(sb_sql.toString());
			//未提交记录数清零
			intUnInsert = 0;
			//清零未提交正确记录集
			listUnInsert.clear();
		}

		//		记录结果
		result[0] = String.valueOf(intSum); //总记录数
		result[1] = String.valueOf(intErr); //校验失败的记录数
		result[2] = String.valueOf(intCorrect); //插入数据库的记录
		return result;
	}

	/**
	 * 包含业务逻辑的方法
	 * 分拣系统日志
	 *  
	 * @param file
	 * @param type
	 * @param pattern
	 * @param returnKey
	 * @param resultTabel
	 * @param dao
	 * @throws Exception
	 */
	public static String[] separateSysLogSingle(File file, String type, String[] returnKey, String resultTabel, BaseDao dao, String date,
			String pattern) throws Exception
	{
		//处理结果
		String[] result = new String[3];
		//未提交正确记录集
		List listUnInsert = new ArrayList();
		//	总记录数
		int intSum = 0;
		//错误记录数
		int intErr = 0;
		//正确记录数
		int intCorrect = 0;
		//未提交记录数
		int intUnInsert = 0;

		//分解数
		int limit = 0;
		
		
		//String column = "stat_date";
		String delSql = "delete from " + resultTabel + " where stat_date = " + date;
		String insertColumns = null;
		
		if (type.equals(SpmsConstants.WEB_FORUM)) //社区访问
		{
			
		}
		else if (type.equals(SpmsConstants.WEB_PLATE)) //板块访问
		{
			
		}
		else if (type.equals(SpmsConstants.WEB_TOPIC)) //主题访问
		{
			limit = 6;
		}
		else if (type.equals(SpmsConstants.WEB_TOPIC_PLATE_INFO)) //主题板块信息
		{
			limit = 7;
		}
		else if (type.equals(SpmsConstants.WEB_USER_ACC)) ///web用户访问（行为）
		{
			
		}
		else if (type.equals(SpmsConstants.WEB_USER_POINT)) ///web用户积分
		{
			
		}
		else if(type.equals(SpmsConstants.WAP_LANMU))	//wap 栏目
		{
			//清理分拣表
			delSql = "delete from " + resultTabel;
			insertColumns = SpmsConstants.WAP_LANMU_INSERT_COLUMNS;
		}
		else if(type.equals(SpmsConstants.WAP_OTHER)) //wap 其它统计数据
		{
			//清理分拣表
			delSql = "delete from " + resultTabel;
		}
		
		/**
		 * 清理sql 
		 * 运营日志统计时 如果存在所属日期记录 删除 防止冗余
		 * （apache日志不需要，分拣时有临时表，临时表在计算后清理，并且统计时报时已经有清理步骤）
		 */
		
		
		//执行清理操作
		dao.executeCommonSql(delSql);

		/*BufferedReader br = null;
		 InputStreamReader isr = null;
		 FileInputStream fis = null;
		 fis = new FileInputStream(file);
		 isr = new InputStreamReader(fis, "UTF-8");
		 br = new BufferedReader(isr);*/

		/*FileInputStream fs = new FileInputStream(file);
		 

		 FileChannel   fc   = fs.getChannel();  ;// fs.getChannel();   FileUtils.getChannel(fs,new FileDescriptor())
		 FileLock   fl   =   fc.tryLock();  
		 if   (!fl.isValid())   {   
		 _logger.info("未能得到"+file.getPath()+" 的锁，文件被其它程序占用，放弃分拣...");  
		 }
		 else
		 {
		 _logger.info("得到"+file.getPath()+" 的锁"); 
		 }
		 BufferedReader br = new BufferedReader(new InputStreamReader(fs,"utf-8"));//建立BufferedReader对象，并实例化为br
		 *
		 *
		 */

		RandomAccessFile reader = new RandomAccessFile(file, "rw");
		String strRead;

		FileChannel fc = reader.getChannel();

		FileLock fl = fc.tryLock();
		try
		{
			if (!fl.isValid())
			{
				_logger.info("未能得到" + file.getPath() + " 的锁，文件被其它程序占用，放弃分拣...");
				return null;
			}
			else
			{
				_logger.info("得到" + file.getPath() + " 的锁");
			}

			while ((strRead = reader.readLine()) != null)
			{
				strRead = new String(strRead.getBytes("ISO8859_1"), "utf-8");
				//	去掉空格
				strRead = strRead.replaceAll(" ", "");
				intSum++; //总记录数加1

				boolean isCorrect = true;
				if (null != pattern && !"".equals(pattern))
				{
					isCorrect = matches(strRead, pattern);
				}
				
				String log[];
				//根据分隔符 分隔日志
				if(limit == 0)
				{
					log = strRead.split("\\|");
				}
				else
				{
					log = strRead.split("\\|",limit);
				}
				
				if (log.length == returnKey.length && isCorrect)//匹配成功
				{
					intCorrect++; //正确数加1
					intUnInsert++; //正确记录未插入数加1
					listUnInsert.add(log);//存入未提交正确记录集

					if (intUnInsert == 200) //正确记录达到200条时  执行数据插入
					{
						//拼写sql脚本
						StringBuffer sb_sql = new StringBuffer();
						sb_sql.append("insert into ");
						sb_sql.append(resultTabel);
						if(null!=insertColumns)
						{	//如果指定了插入字段
							sb_sql.append("("+insertColumns+")");
						}
						sb_sql.append(" select SEQ_SPMS_WEB_LOG_TMP.NEXTVAL,");//log_id
						sb_sql.append(" t.*");
						sb_sql.append(" from (");

						Iterator it = listUnInsert.iterator();

						while (it.hasNext())
						{
							String _log[] = (String[]) it.next();

							sb_sql.append(" select ");
							//根据 returnKey的长度 拼写sql
							for (int i = 0; i < returnKey.length; i++)
							{//new String(name.getBytes("8859_1"),"gb2312") 
								sb_sql.append("'" + transferOracleString(_log[i]) + "' \"" + returnKey[i] + "\",");
							}

							//创建时间
							sb_sql.append("'" + CommonOperation.getSystemTime("dateTime") + "' \" CREATETIME\"");

							sb_sql.append(" from dual");
							sb_sql.append(" union all ");
						}

						//删除最后一个union
						sb_sql.delete(sb_sql.lastIndexOf("union"), sb_sql.length());

						sb_sql.append(" )t");

						//执行sql
						dao.executeCommonSql(sb_sql.toString());
						//未提交记录数清零
						intUnInsert = 0;
						//清零未提交正确记录集
						listUnInsert.clear();
					}
				}
				else
				//匹配失败
				{
					//数据校验失败
					intErr++; //错误数加1
					//记录失败日志
					String sql = "insert into spms_err_log select SEQ_SPMS_LOG_ERR.NEXTVAL,'" + type + "','" + file.getName() + "','"
							+ CommonOperation.getSystemTime("dateTime") + "','" + transferOracleString(strRead) + "' from dual";

					//执行sql
					dao.executeCommonSql(sql.toString());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			//在这里捕获异常为的是在finally中关闭锁和流
			//再次抛出 让上层捕获记录日志
			throw new Exception(e);
		}
		finally
		{
			//读完关闭
			if (null != fl && fl.isValid())
			{
				fl.release();
				
				_logger.info("释放" + file.getPath() + " 的锁");
			}
			fc.close();
			reader.close();
			
		}

		if (intUnInsert > 0) //将剩余记录插入数据库
		{
			//拼写sql脚本
			StringBuffer sb_sql = new StringBuffer();
			sb_sql.append("insert into ");
			sb_sql.append(resultTabel);
			if(null!=insertColumns)
			{	//如果指定了插入字段
				sb_sql.append("("+insertColumns+")");
			}
			sb_sql.append(" select SEQ_SPMS_WEB_LOG_TMP.NEXTVAL,");//log_id
			sb_sql.append(" t.*");
			sb_sql.append(" from (");

			Iterator it = listUnInsert.iterator();

			while (it.hasNext())
			{
				String _log[] = (String[]) it.next();

				sb_sql.append(" select ");
				//根据 returnKey的长度 拼写sql
				for (int i = 0; i < returnKey.length; i++)
				{
					sb_sql.append("'" + transferOracleString(_log[i]) + "' \"" + returnKey[i] + "\",");
				}

				//创建时间
				sb_sql.append("'" + CommonOperation.getSystemTime("dateTime") + "' \" CREATETIME\"");

				sb_sql.append(" from dual");
				sb_sql.append(" union all ");
			}

			//删除最后一个union
			sb_sql.delete(sb_sql.lastIndexOf("union"), sb_sql.length());

			sb_sql.append(" )t");

			//执行sql
			dao.executeCommonSql(sb_sql.toString());
			//未提交记录数清零
			intUnInsert = 0;
			//清零未提交正确记录集
			listUnInsert.clear();
		}

		//		记录结果
		result[0] = String.valueOf(intSum); //总记录数
		result[1] = String.valueOf(intErr); //校验失败的记录数
		result[2] = String.valueOf(intCorrect); //插入数据库的记录
		return result;
	}

	public static void main(String args[])
	{
		String s = "BBS_WEB|20090306|3会员统计|今日论坛之星|11   |统计今日论坛之星括号内是论坛之星的发帖数|None(0)";
		System.out.println(s);
		s = s.replaceAll(" ", "");
		System.out.println(s);
		System.out.println(matches(s,"[a-zA-Z0-9_\u4e00-\u9fa5]+\\|\\d{8}\\|[a-zA-Z0-9\u4e00-\u9fa5]+\\|[a-zA-Z0-9\\s\u4e00-\u9fa5]+\\|[.a-zA-Z0-9\u4e00-\u9fa5]+\\|[a-zA-Z0-9\u4e00-\u9fa5]+\\|[\\p{Punct}a-zA-Z0-9\u4e00-\u9fa5]+"));
		
		System.out.println(matches("435_re|飞","[a-zA-Z0-9_\u4e00-\u9fa5]+\\|[\u4e00-\u9fa5]")); //^[\u4e00-\u9fa5]+$
		System.out.println(matches("d|2","\\w\\|\\d"));
		/*String ss[] = s.split("\\|");
		for(int i=0;i<ss.length;i++)
		{
			System.out.println(ss[i]);
		}
		
		System.out.println(transferOracleString(s));*/
		//access_log.2008-08-19.10.log
		//System.out.println(analyzeUtil.matches("aaa.txt", "access_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.\\d\\d.log"));
		//System.out.println(analyzeUtil.matches("access_log.2008-12-12.00.log", "access_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.\\d\\d.log"));

		//apache日志
		String str = "221.131.128.199 - - [06/Feb/2009:15:00:15 +0800] \"GET /searchres/resdetail.jsp?resid=34947&phone=13770516844&page=3&phone=13770516845&cpage=1&phone=13770516846 HTTP/1.0\" 302 - \"123\" \"SonyEricssonW810c\"";
		//apache正则
		String pattern = "(\\d+.\\d+.\\d+.\\d+) ([\\-\\w]+) ([\\-\\w]+) \\[(\\d+/\\w+/\\d+):(\\d+:\\d+:\\d+) \\+\\d+\\] \"([\\w\\W]*) ([\\w\\W]*) ([\\w\\W]*)\" ([\\w\\W]*) ([\\w\\W]*) \"([\\w\\W]*)\" \"([\\w\\W]*)\"([\\w\\W]*)";//
		//wap运营数据日志
		//	String str = "nj_wap 13951776391 20090216 091034 13 篮球";
		//运营正则
		//	String pattern	= "(\\w+) (\\d+) (\\d+) (\\d+) (\\d+) ([\\w\\W]*)([\\w\\W]*)";

		/*		String[] strResult = scanByRegular(str, pattern);

		 if (strResult == null)
		 {
		 System.out.println("匹配失败");
		 }
		 else
		 {
		 for (int i = 0; i < strResult.length; i++)
		 {
		 System.out.println(strResult[i]);
		 }
		 }*/

		/*String[] returnKey =
		 { "c-ip", "cs-username", "cs-userpassword", "date", "time", "cs-method", "cs-uri-stem", "cs-version", "sc-status", "sc-bytes", "cs(Referer)",
		 "cs(User-Agent)" };
		 System.out.println("**************map*************");
		 Map resultMap = scanByRegular(str, returnKey, pattern);
		 if (resultMap != null)
		 {
		 System.out.println(returnKey[0] + " = " + resultMap.get(returnKey[0]));
		 System.out.println(returnKey[1] + " = " + resultMap.get(returnKey[1]));
		 System.out.println(returnKey[2] + " = " + resultMap.get(returnKey[2]));
		 System.out.println(returnKey[3] + " = " + resultMap.get(returnKey[3]));
		 System.out.println(returnKey[4] + " = " + resultMap.get(returnKey[4]));
		 System.out.println(returnKey[5] + " = " + resultMap.get(returnKey[5]));
		 System.out.println(returnKey[6] + " = " + resultMap.get(returnKey[6]));
		 System.out.println(returnKey[7] + " = " + resultMap.get(returnKey[7]));
		 System.out.println(returnKey[8] + " = " + resultMap.get(returnKey[8]));
		 System.out.println(returnKey[9] + " = " + resultMap.get(returnKey[9]));
		 System.out.println(returnKey[10] + " = " + resultMap.get(returnKey[10]));
		 System.out.println(returnKey[11] + " = " + resultMap.get(returnKey[11]));

		 System.out.println("再次在cs-uri-stem中查找手机号码");// phone=13770516841&phone=13770516842&phone=13770516843&phone=&phone=ef
		 //String r = findSingle(resultMap.get("cs-uri-stem").toString(), "phone=(\\d+)");
		 String r = findSingle(resultMap.get("cs(User-Agent)").toString(), "(\\w+)/[\\w\\W]*");
		 if (r == null)
		 {
		 System.out.println("not found~");
		 }
		 else
		 {
		 for (int i = 0; i < r.length; i++)
		 {
		 System.out.println("找到的: " + r);
		 //}
		 }
		 System.out.println("over=======");
		 }
		 else
		 {
		 System.out.println("匹配失败！");
		 }
		 */
	}
}
