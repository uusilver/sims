package com.xwtech.mss.pub.constants;

public class MssConstants {

	// 日志记录操作类型
	public static final String OPER_TYPE_INSERT = "1"; // 新增
	public static final String OPER_TYPE_UPDATE = "2"; // 修改
	public static final String OPER_TYPE_DELETE = "3"; // 删除

	//日志记录操作对象
	public static final String OPER_OBJECT_SYSUSER = "10"; // 系统用户
	public static final String OPER_OBJECT_SYSROLE = "20"; // 系统角色
	public static final String OPER_OBJECT_SYSMENU = "30"; // 系统菜单
	public static final String OPER_OBJECT_SERVER = "40"; // 服务器
	public static final String OPER_OBJECT_SERVER_GROUP = "41"; // 服务器分组
	public static final String OPER_OBJECT_CLIENT = "50"; // 客户信息
	public static final String OPER_OBJECT_CLIENT_GROUP = "51"; // 客户分组	
	public static final String OPER_OBJECT_CLIENT_GATEWAY = "52"; // 客户与网关映射关系
	public static final String OPER_OBJECT_CLIENT_SERVER = "53"; // 客户与服务器映射关系
	public static final String OPER_OBJECT_GATEWAY = "60"; // 网关信息
	public static final String OPER_OBJECT_COUNTRY = "70"; // 国家信息
	public static final String OPER_OBJECT_PROVINCE = "71"; // 省（州）
	public static final String OPER_OBJECT_CITY = "72"; // 市
	public static final String OPER_OBJECT_REGION = "73"; // 地域信息
	
	//日志记录操作表
	public static final String OPER_TABLE_SYSUSER = "frame_user_info"; // 系统用户
	public static final String OPER_TABLE_SYSROLE = "frame_role"; // 系统角色
	public static final String OPER_TABLE_SYSMENU = "frame_menu"; // 系统菜单
	public static final String OPER_TABLE_SERVER = "transit_server"; // 服务器
	public static final String OPER_TABLE_SERVER_GROUP = "server_group"; // 服务器分组
	public static final String OPER_TABLE_CLIENT = "client"; // 客户信息
	public static final String OPER_TABLE_CLIENT_GROUP = "client_group"; // 客户分组	
	public static final String OPER_TABLE_CLIENT_GATEWAY = "client_gateway"; // 客户与网关映射关系
	public static final String OPER_TABLE_CLIENT_SERVER = "client_server_mapping"; // 客户与服务器映射关系
	public static final String OPER_TABLE_GATEWAY = "gateway"; // 网关信息
	public static final String OPER_TABLE_COUNTRY = "country"; // 国家信息
	public static final String OPER_TABLE_PROVINCE = "province"; // 省（州）
	public static final String OPER_TABLE_CITY = "city"; // 市
	public static final String OPER_TABLE_REGION = "region"; // 地域信息
	
	
	
	public static final String STATE_A = "A";	//状态-有效
	public static final String STATE_U = "U";	//状态-无效
	public static final String STATE_D = "D";	//状态-删除
	
	public static final String VIEW_OR_EDIT_EDIT = "edit";	//编辑
	public static final String VIEW_OR_EDIT_ADD = "add";	//新增


	// 查询状态值
	public static final String QUERY_ROLE_STATE = "SELECT t.check_value, t.check_desc FROM frame_field_check t" +
	" WHERE t.table_name = 'frame_role' AND t.field_name='state'";

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
	

	// 查询未分组SQL
	public static final String QUERY_NO_SERVE_GROUP_INFO_SQL = "SELECT 0, '未分组' FROM DUAL ";
	
	// SQL - UNION
	public static final String SQL_UNION = " UNION ";
	
	// 查询服务器分组信息SQL
	public static final String QUERY_SERVE_GROUP_INFO_SQL = "SELECT G.SERVERGROUPID , G.SERVERGROUPNAME FROM SERVER_GROUP G"
			+ " WHERE G.STATUS = '" + STATE_A + "'";
	
	// 查询客户属性信息SQL:ModifyPass
	public static final String QUERY_CLIENT_MODIFYPASS_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='MODIFY_PASS'";
	
	// 查询客户属性信息SQL:AUTH_TYPE
	public static final String QUERY_CLIENT_AUTH_TYPE_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='AUTH_TYPE'";
	
	// 查询客户属性信息SQL:DISABLE_FLAG
	public static final String QUERY_CLIENT_DISABLE_FLAG_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='DISABLE_FLAG'";
	
	// 查询客户属性信息SQL:USER_TYPE
	public static final String QUERY_CLIENT_USER_TYPE_SQL = "SELECT S.VALUE, S.TEXT FROM CODE_BOOK S"
			+ " WHERE S.STATUS = '" + STATE_A + "' AND S.TAG='USER_TYPE'";
	
	// 19位日期格式
	public static final String DATE_FOMATTER_19 = "yyyy-MM-dd HH:mm:ss";

	// 14位日期格式
	public static final String DATE_FOMATTER_15 = "yyyyMMddHHmmss";
	
	// 服务器类型
	
	public static final String SERVER_TYPE = "SERVER_TYPE"; 
	// 服务器状态
	public static final String SERVER_STATUS = "SERVER_STATUS"; 
	
	/***************************************************************************
	 * 基本常量
	 **************************************************************************/
	public static final int COUNT_FOR_EVERY_PAGE = 40; // 每页显示的记录条数
	

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
}
