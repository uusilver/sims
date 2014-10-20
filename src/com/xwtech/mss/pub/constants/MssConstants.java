package com.xwtech.mss.pub.constants;

public class MssConstants {
	/***************************************************************************
	 * 基本常量
	 **************************************************************************/
	public static final int COUNT_FOR_EVERY_PAGE = 30; // 每页显示的记录条数
	

	/***************************************************************************
	 * 邮件发送相关常量
	 **************************************************************************/
	public static final String SEND_MAIL_BY_ZIP_FILE = "1"; // 将附件以压缩文件的形式发送邮件
	public static final String SEND_MAIL_BY_COMMON_FILE = "0"; // 将附件以普通文件的形式发送邮件
	public static final String SEND_MAIL_TYPE = "SM"; // 发送邮件类型

	/***************************************************************************
	 * 业务常量
	 **************************************************************************/
	/* start 群发筛选功能导出操作的相关常量 */
	public static final String CUSTOMER_FILE_NAME = "customer"; // 群发筛选导出文件名，后缀加当前时间（精确到秒）
	public static final String CUSTOMER_FILE_DIR = "\\file\\export\\"; // 群发筛选导出文件相对路径
	public static final String FILE_TYPE_TXT = ".txt"; // 文件类型
	public static final int SELECTED_LIKE_LIST_PAGE = 500; // “选择兴趣类别”跳出页面，每页显示的记录条数，意即全部显示
	public static final String SELECTED_LIKE_LIST_ROW = "5"; // “选择兴趣类别”跳出页面，每行显示的列数
	public static final String SELECTED_LIKE_ORDERBY_LIKENAME = "orderByLikeName";
	/* end 群发筛选功能导出操作的相关常量 */

	/***************************************************************************
	 * 数据库相关常量
	 **************************************************************************/
	// 所有表中字段涉及到是否，均以以下常量表示。
	public static final String DATA_NO = "0"; // 表示未/否
	public static final String DATA_YES = "1"; // 表示已/是

	// add by jzy 09-01-14 日志记录操作类型
	public static final String OPER_TYPE_INSERT = "A"; // 新增
	public static final String OPER_TYPE_UPDATE = "U"; // 修改
	public static final String OPER_TYPE_DELETE = "D"; // 删除	
	
	public static final String STATE_A = "A";	//状态-有效
	public static final String STATE_U = "U";	//状态-无效
	public static final String STATE_D = "D";	//状态-删除

	// 用户状态：正常A、停机U、销号D
	public static final String CUSTOMER_STATE_VALID = "A"; // 正常
	public static final String CUSTOMER_STATE_INVALID = "U"; // 停机
	public static final String CUSTOMER_STATE_DELETE = "D"; // 销号

	// 查询服务器所在国家表有效信息SQL
	public static final String QUERY_COUNTRY_INFO_SQL = "SELECT C.COUNTRYID, C.COUNTRYNAME FROM COUNTRY C"
			+ " WHERE C.STATUS = '" + STATE_A +"'";
	
	// 查询服务器所在省（州）表有效信息SQL
	public static final String QUERY_PROVINCE_INFO_SQL = "SELECT P.PROVINCEID, P.PROVINCENAME,P.COUNTRYID FROM PROVINCE P"
			+ " WHERE P.STATUS = '" + STATE_A + "'";
	
	// 查询服务器所在城市表有效信息SQL
	public static final String QUERY_CITY_INFO_SQL = "SELECT T.CITYID, T.CITYNAME,T.PROVINCEID FROM CITY T"
			+ " WHERE T.STATUS = '" + STATE_A + "'";
	
	// 查询服务器服务地域表有效信息SQL
	public static final String QUERY_SERVE_REGION_INFO_SQL = "SELECT R.REGIONID, R.REGIONNAME FROM REGION R"
			+ " WHERE R.STATUS = '" + STATE_A + "'";
	
	// 查询服务器类型信息SQL
	public static final String QUERY_SERVER_TYPE_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='SERVER_TYPE'";
	
	// 查询服务器状态信息SQL
	public static final String QUERY_SERVER_STATUS_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='SERVER_STATUS'";
	
	// 查询服务器约束信息SQL
	public static final String QUERY_SERVER_LIMIT_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='LIMITATION'";
	
	
	// 19位日期格式
	public static final String DATE_FOMATTER_19 = "yyyy-MM-dd HH:mm:ss";

	// 14位日期格式
	public static final String DATE_FOMATTER_15 = "yyyyMMddHHmmss";
}
