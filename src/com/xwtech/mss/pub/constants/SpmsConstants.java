package com.xwtech.mss.pub.constants;

public class SpmsConstants
{
	/***************************************************************************
	 * 基本常量
	 **************************************************************************/
	public static final int COUNT_FOR_EVERY_PAGE = 30; // 每页显示的记录条数
	
	public static final String[] FRAME_URL_VALUES = {"/mss/","/framework/"}; // 可访问url值
	
	/***************************************************************************
	 * 数据库相关常量
	 **************************************************************************/
	public static final String STATE_A = "A";	//状态-有效
	public static final String STATE_U = "U";	//状态-无效
	public static final String STATE_D = "D";	//状态-删除
	
	public static final String IN_RECORD = "1"; //入库单
	public static final String OUT_RECORD = "2";//出库单
	public static final String BACK_RECORD = "3";//退库单

	public static final String RECORD_UNPAYED = "0";//未付款
	public static final String RECORD_PAYED = "1";//已付款
	public static final String RECORD_OUT_TO_BACK = "2";//出库单转退库单后为‘已付款’
	public static final String RECORD_FINISHED = "9";//完结，用于入库与退库的流水操作
	
	public static final String ROLE_ADMIN = "1";  //系统管理员权限	
	
	// 查询角色状态
	public static final String QUERY_ROLE_STATE = "SELECT t.check_value, t.check_desc FROM frame_field_check t" +
	" WHERE t.table_name = 'frame_role' AND t.field_name='state'";
	// 查询角色信息
	public static final String QUERY_ROLE_INFO = "SELECT role_id, role_name FROM frame_role WHERE state='A'";
	// 查询一级菜单信息
	public static final String QUERY_MENUINFO_FIRST = "SELECT menu_id, menu_name FROM frame_menu" +
			" WHERE menu_state = 'A' AND menu_level = 1";
	//查询菜单级联信息（一级菜单 + ' - ' + 二级菜单）
//	public static final String QUERY_MENUINFO_CASCADE = "SELECT fm.menu_id, fm2.menu_name || ' - ' || fm.menu_name," +
//			" fr.role_id, fr.role_name FROM frame_menu fm, frame_menu fm2, frame_role fr, frame_user_property fup" +
//			" WHERE fr.role_id = fup.role_id AND fm.menu_id = fup.menu_id AND fm.up_menu_id = fm2.menu_id" +
//			" AND fm.menu_level = 2 AND fm.menu_state = 'A'";
	public static final String QUERY_MENUINFO_CASCADE = "SELECT fm.menu_id, CONCAT(fm2.menu_name,' - ',fm.menu_name)," +
			" fr.role_id, fr.role_name FROM frame_menu fm, frame_menu fm2, frame_role fr, frame_user_property fup" +
			" WHERE fr.role_id = fup.role_id AND fm.menu_id = fup.menu_id AND fm.up_menu_id = fm2.menu_id" +
			" AND fm.menu_level = 2 AND fm.menu_state = 'A'";
	//查询部门信息
	public static final String QUERY_ORGINFO = "SELECT org_id, org_name FROM frame_orgnization" +
			" WHERE state='A' ORDER BY org_name";
	
	// 查询所有物品类别信息
	public static final String QUERY_GOODSTYPE_INFO = "SELECT type_num, type_name FROM goods_type " +
			" WHERE type_state = 'A'  ORDER BY type_name";
	
	// 查询所有购买人信息
	public static final String QUERY_CLIENT_INFO = "SELECT client_num, client_name FROM client_info " +
			" WHERE client_state = 'A' and client_type=1  ORDER BY client_name";
	
	// 查询所有购买人信息
//	public static final String QUERY_CLIENT_INFO_WITH_NICK = "SELECT client_num, first_letter_nick||' - '||client_nick||' - '||client_name FROM client_info " +
//			" WHERE client_state = 'A' and client_type=1  ORDER BY first_letter_nick";
	public static final String QUERY_CLIENT_INFO_WITH_NICK = "SELECT client_num, CONCAT(first_letter_nick,' - ',client_nick,' - ',client_name) FROM client_info " +
			" WHERE client_state = 'A' and client_type=1  ORDER BY first_letter_nick";

	// 查询用户信息
	public static final String QUERY_USER_INFO = "SELECT user_id, user_name FROM frame_user_info WHERE user_state='A'";
	
	
	/***************************************************************************
	 * 日志常量
	 **************************************************************************/
	//操作类型
	public static final Long DO_TYPE_ADD = new Long("1"); //新增
	public static final Long DO_TYPE_UPDATE = new Long("2"); //修改
	public static final Long DO_TYPE_DEL = new Long("3"); //删除
	
	//对象类型
	public static final Long OBJ_TYPE_ROLE = new Long("1"); //角色
	public static final Long OBJ_TYPE_USER = new Long("2"); //用户
	public static final Long OBJ_TYPE_ORG = new Long("3"); //部门机构
	
	//表名
	public static final String TABLE_NAME_FRAME_ORGNIZATION = "frame_orgnization"; //部门机构表
	
	//备注
	public static final String DESC_ORG_ADD = "新增部门";
	public static final String DESC_ORG_UPDATE = "更新部门";
	public static final String DESC_ORG_DEL = "删除部门";
	
	
	//add by jzy 09-02-25 业务类型
	public static final String WAP_APACHE ="1";  //wap系统apache
	public static final String WAP_LANMU ="2";  //wap栏目
	public static final String WAP_OTHER ="3";  //wap其它运营
	public static final String WEB_APACHE ="4";  //web系统(apache)
	public static final String WEB_FORUM ="5";  //web社区访问
	public static final String WEB_PLATE ="6";  //web板块访问
	public static final String WEB_TOPIC ="7";  //web主题访问
	public static final String WEB_TOPIC_PLATE_INFO ="8";  //web主题板块信息
	public static final String WEB_USER_ACC ="9";  //web用户访问（行为）
	public static final String WEB_USER_POINT ="10";  //web用户积分
	
	//	add by jzy 09-03-02 业务日报excel文件名
	public static final String WAP_APACHE_NAME ="WAP_APACHE";  //wap系统apache
	public static final String WAP_LANMU_NAME  ="WAP_LANMU";  //wap栏目
	public static final String WAP_OTHER_NAME  ="WAP_OTHER";  //wap其它运营
	public static final String WEB_APACHE_NAME  ="WEB_APACHE";  //web系统(apache)
	public static final String WEB_FORUM_NAME  ="WEB_FORUM";  //web社区访问
	public static final String WEB_PLATE_NAME  ="WEB_PLATE";  //web板块访问
	public static final String WEB_TOPIC_NAME  ="WEB_TOPIC";  //web主题访问
	public static final String WEB_TOPIC_PLATE_INFO_NAME  ="WEB_TOPIC_PLATE_INFO";  //web主题板块信息
	public static final String WEB_USER_ACC_NAME  ="WEB_USER_ACC";  //web用户访问（行为）
	public static final String WEB_USER_POINT_NAME  ="WEB_USER_POINT";  //web用户积分
	
	//add by jzy 09-02-25 文件名校验正则
	
	//校验 web apache日志文件名 new Name access_mzone_192.168.16.78_log.2008-08-19.10.log  	old: "access_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.\\d\\d.log"; 
	public static final String WEB_APACHE_FILE_NAME_REGULAR ="access_mzone_\\d+.\\d+.\\d+.\\d+_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.\\d\\d.log";
	
	public static final String WAP_APACHE_FILE_NAME_REGULAR ="access_mzone_\\d+.\\d+.\\d+.\\d+_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.\\d\\d.log";
	
	public static final String WEB_APACHE_FILE_NAME_REGULAR_WITHOUT_HOUR ="access_mzone_\\d+.\\d+.\\d+.\\d+_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.log";
	
	public static final String WAP_APACHE_FILE_NAME_REGULAR_WITHOUT_HOUR ="access_mzone_\\d+.\\d+.\\d+.\\d+_log.\\d\\d\\d\\d-\\d\\d-\\d\\d.log";
	
	//校验 社区访问日志文件名
	public static final String WEB_FORUM_FILE_NAME_REGULAR ="forumtotal_\\d{8}.txt"; 
	//	校验 板块访问日志文件名
	public static final String WEB_PLATE_FILE_NAME_REGULAR ="platetotal_\\d{8}.txt"; 
	//	校验 主题访问日志文件名
	public static final String WEB_TOPIC_FILE_NAME_REGULAR ="topictotal_\\d{8}.txt"; 
	//	校验 板块日志文件名
	public static final String WEB_TOPIC_PLATE_FILE_NAME_REGULAR ="plateinfo_\\d{8}.txt"; 
	//	校验 用户访问日志文件名
	public static final String WEB_USER_ACC_FILE_NAME_REGULAR ="forumuser_\\d{8}.txt"; 
	//	校验 用户积分日志文件名
	public static final String WEB_USER_POINTS_FILE_NAME_REGULAR ="pointtotal_\\d{8}.txt"; 
	// 校验 wap 栏目访问文件名
	public static final String WAP_LANMU_FILE_NAME_REGULAR = "lanmutotal_\\d{10}.txt";	
	//	 校验 wap 其他访问文件名
	public static final String WAP_OTHER_FILE_NAME_REGULAR = "othertotal_\\d{10}.txt";
	
	//	 校验 wap 栏目访问文件名
	public static final String WAP_LANMU_FILE_NAME_REGULAR_WITHOUT_HOUR = "lanmutotal_\\d{8}.txt";	
	//	 校验 wap 其他访问文件名
	public static final String WAP_OTHER_FILE_NAME_REGULAR_WITHOUT_HOUR = "othertotal_\\d{8}.txt";
	
	//校验 web apache日志
	public static final String WEB_APACHE_LOG_REGULAR = "(\\d+.\\d+.\\d+.\\d+) ([\\-\\w]+) ([\\-\\w]+) \\[(\\d+/\\w+/\\d+):(\\d+:\\d+:\\d+) \\+\\d+\\] \"([\\w\\W]*) ([\\w\\W]*) ([\\w\\W]*)\" ([\\w\\W]*) ([\\w\\W]*) \"([\\w\\W]*)\" \"([\\w\\W]*)\"([\\w\\W]*)";
	//从文件web apache日志的文件名中获得 时间 yyyymmddhh
	public static final String WEB_APACHE_TIME_REGULAR = "access_mzone_\\d+.\\d+.\\d+.\\d+_log.(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d).(\\d\\d).log";//"access_mzone_\\d+.\\d+.\\d+.\\d+_log.(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d).(\\d\\d).log";
	//	从文件wap apache日志的文件名中获得 时间 yyyymmddhh
	public static final String WAP_APACHE_TIME_REGULAR = "access_mzone_\\d+.\\d+.\\d+.\\d+_log.(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d).(\\d\\d).log";
	
	//	从文件web apache日志的文件名中获得 时间 yyyymmddhh
	public static final String WEB_APACHE_TIME_REGULAR_WITHOUT_HOUR = "access_mzone_\\d+.\\d+.\\d+.\\d+_log.(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d).log";//"access_mzone_\\d+.\\d+.\\d+.\\d+_log.(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d).(\\d\\d).log";
	//	从文件wap apache日志的文件名中获得 时间 yyyymmddhh
	public static final String WAP_APACHE_TIME_REGULAR_WITHOUT_HOUR = "access_mzone_\\d+.\\d+.\\d+.\\d+_log.(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d).log";
	
	
	//从URI中提取电话号码
	public static final String FIND_PHONE_REGULAR  = "phone=(\\d+)";
	//从	User-Agent中提取手机型号		
	public static final String FIND_MOBILE_TYPE_REGULAR  = "(\\w+)/[\\w\\W]*";
	
	//校验 web forum 日志
	public static final String WEB_FORUM_LOG_REGULAR = "[a-zA-Z0-9_\\s\u4e00-\u9fa5]+\\|\\d{8}\\|[a-zA-Z0-9\\s\u4e00-\u9fa5]+\\|[a-zA-Z0-9\\s\u4e00-\u9fa5]+\\|[.a-zA-Z0-9\\s\u4e00-\u9fa5]+\\|[a-zA-Z0-9\\s\u4e00-\u9fa5]+\\|[\\p{Punct}a-zA-Z0-9\\s\u4e00-\u9fa5]+";

	//	校验 wap other 日志
	public static final String WAP_OTHER_LOG_REGULAR = "[a-zA-Z0-9_\u4e00-\u9fa5]+\\|\\d{14}\\|\\d{11}\\|\\d+\\|\\d+\\|\\d+\\|\\d+\\|\\d+\\|\\d+\\|\\d+\\|\\d+";

	
	//校验 web apache日志 返回Key
	public static final String LOG_IP = "c-ip";
	public static final String LOG_USER_NAME = "cs-username";
	public static final String LOG_USER_PASSWORD = "cs-userpassword";
	public static final String LOG_DATE = "date";
	public static final String LOG_TIME = "time";
	public static final String LOG_METHOD = "cs-method";
	public static final String LOG_URI = "cs-uri-stem";
	public static final String LOG_VERSION = "cs-version";
	public static final String LOG_STATUS = "sc-status";
	public static final String LOG_BYTES = "sc-bytes";
	public static final String LOG_REFERER = "cs(Referer)";
	public static final String LOG_USER_AGENT = "cs(User-Agent)";
	
	public static final String STAT_STATE = "0";
	
	//	wap apache 日志
	public static final String[] WAP_APACHE_LOG_KEY = {LOG_IP,LOG_USER_NAME, LOG_USER_PASSWORD, LOG_DATE, LOG_TIME, 
		LOG_METHOD, LOG_URI, LOG_VERSION, LOG_STATUS, LOG_BYTES, LOG_REFERER, LOG_USER_AGENT};
	
	//	wap apache excel columns
	public static final String WAP_APACHE_HQL_COLUMNS ="areaId,currUserCnt,pageVisitCnt,userVisitCnt,visitBytes,repDate";
	
	//web apache 日志
	public static final String[] WEB_APACHE_LOG_KEY = {LOG_IP,LOG_USER_NAME, LOG_USER_PASSWORD, LOG_DATE, LOG_TIME, 
		LOG_METHOD, LOG_URI, LOG_VERSION, LOG_STATUS, LOG_BYTES, LOG_REFERER, LOG_USER_AGENT};
	
	//	web apache excel columns
	public static final String WEB_APACHE_HQL_COLUMNS ="areaId,currUserCnt,pageVisitCnt,repDate";

	
	//社区访问 key  	
	public static final String[] WEB_FORUM_LOG_KEY = {"AREAID","CDATE","TOTALCLASS","TOTALITEM","TOTALRESULT","REMARK","TOTALNAME"};
	//	web TOPIC excel columns
	public static final String WEB_FORUM_HQL_COLUMNS ="areaId,statDate,statType,statItem,statResult,remark,statName";

	
	//板块访问 
	public static final String[] WEB_PLATE_LOG_KEY = {"AREAID","CDATE","PLATECODE","PLATENAME","NOTECNT","NOTEREP","NOTEMONYH","NOTEDAY"};
	//	web user plate HQL columns
	public static final String WEB_USER_PLATE_HQL_COLUMNS ="areaId,statDate,plateCode,plateName,plateCnt,plateRepCnt,plateMonthCnt,plateYesdayCnt";

	//主题访问 
	public static final String[] WEB_TOPIC_LOG_KEY = {"AREAID","CDATE","TOPICCODE","NOTEREAD","NOTEREP","TOPICNAME"};
	//	web TOPIC HQL columns
	public static final String WEB_TOPIC_HQL_COLUMNS ="areaId,statDate,topicCode,topicReadCnt,topicRepCnt,topicName";

	
	
	//主题板块信息 
	public static final String[] WEB_TOPIC_PLATE_LOG_KEY = {"AREAID","CDATE","PLATESTAT","PLATEID","PLATENAME","NOTEID","NOTENAME"};
	//	web PT HQL columns
	public static final String WEB_TOPIC_PLATE_HQL_COLUMNS ="areaId,statDate,plateStat,plateCode,plateName,topicCode,topicName";

	
	
	//web用户访问（行为）
	public static final String[] WEB_USER_ACC_LOG_KEY = {"AREAID","CDATE","USERID","MOBILE","NOTEID","PLATENAME","READTOPIC","SENDTOPIC","RECTOPIC","GOODTOPIC"};
	//web user acc HQL columns
	public static final String WEB_USER_ACC_HQL_COLUMNS ="areaId,statDate,userId,mobileNum,plateCode,topicReadCnt,topicSendCnt,topicRepCnt,topicGoodCnt,plateName";
	
	
	//web用户积分（行为）
	public static final String[] WEB_USER_POINT_LOG_KEY = {"AREAID","CDATE","USERID","MOBILE","POINTVAL","PRESTIGEVAL","MONEYVAL","MZONEVAL"};
	//web user point HQL columns
	public static final String WEB_USER_POINT_HQL_COLUMNS ="areaId,statDate,userId,mobileNum,piontVal,prestigeVal,moneyVal,mzoneVal";
	
	//wap 栏目(功能)访问
	public static final String[] WAP_LANMU_LOG_KEY = {"AREAID","MOBILE","CDATE","CTIME","LANMUID","LANMUNAME"/*,"LANMULEVEL","PRELANMUID","PRELANMU"*/};
	//	wap 栏目 HQL columns
	public static final String WAP_LANMU_HQL_COLUMNS ="areaId,currUserCnt,userVisitCnt,lanmuId,lanmuName,repDate";
	//	wap 栏目(功能)访问excel
	public static final String[] WAP_LANMU_LOG_KEY_EXCEL = {"AREAID","CURRUSERCNT","USERVISITCNT","LANMUID","LANMUNAME","PREDATE"};
	//  wap 栏目 insert into colums
	public static final String WAP_LANMU_INSERT_COLUMNS ="log_id,area_id,mobile_num,visit_date,visit_time,lanmu_id,lanmu_name,create_time";
	
	//	wap 其他业务统计
	public static final String[] WAP_OTHER_LOG_KEY = {"AREAID","CDATE","MOBILE","SMSCNT","MMSCNT","MUSICCNT","GAMECNT","MCOUNT","BLOGCNT","SENDNOTE","REPNOTE"};
	//	wap 其他业务统计 HQL columns
	public static final String WAP_OTHER_HQL_COLUMNS ="areaId,smsCnt,mmsCnt,musicCnt,gameCnt,mzoneCnt,blogCnt,sendNoteCnt,repNoteCnt,repDate";
	//	wap 其他业务统计 excel
	public static final String[] WAP_OTHER_LOG_KEY_EXCEL = {"AREAID","MMSCNT","MMSCNT","MUSICCNT","GAMECNT","MCOUNT","BLOGCNT","SENDNOTE","REPNOTE","PREDATE"};
	

	
	//add by jzy 09-02-25 文件操作日志类型
	public static final String GET_COR_FILE = "1"; //采集到正确（文件名的）文件
	public static final String GET_ERR_FILE = "2"; //采集到错误（文件名的）文件
	public static final String GET_FILE_RUNTIME_ERR = "3"; //采集过程异常
	public static final String GET_FILE_OVER = "4"; //采集过程结束
	public static final String NOT_FOUND_CFG = "5";	//没有找到配置记录
	public static final String CANNOT_GET_PATH = "6";	//没有得到分拣目录
	public static final String SEPARATE_ERR = "7";	//分拣时异常
	public static final String SEPARATE_OVER = "8"; //分拣结束
	public static final String CANNOT_GET_SEP_TABLE= "9";	//没有得到分拣表
	public static final String CREATE_RPT_ERR = "10";	//生产报表异常
	public static final String WITHOUT_RPT_FILE = "11";	//没有记录产生excel文件(生成excel失败)
	
	//add by jzy 09-02-25 文件备注
	public static final String GET_COR_FILE_REMARK = "采集到正确（文件名的）文件";
	public static final String GET_ERR_FILE_REMARK = "采集到错误（文件名的）文件"; //
	public static final String GET_FILE_RUNTIME_ERR_REMARK = "采集过程异常"; //
	public static final String GET_FILE_OVER_REMARK = "采集成功"; //
	public static final String NOT_FOUND_CFG_REMARK = "没有找到配置记录";	//没有找到配置记录
	public static final String CANNOT_GET_PATH_REMARK = "没有得到分拣目录";	//没有得到目录
	public static final String SEPARATE_ERR_REMARK = "分拣时异常";	//分拣时异常
	public static final String CANNOT_GET_SEP_TABLE_REMARK= "没有得到分拣表";	//没有得到分拣表
	public static final String CREATE_RPT_ERR_REMARK = "生成excel报表异常";	//生产报表异常
	public static final String WITHOUT_RPT_FILE_REMARK = "没有记录产生excel文件(生成excel失败)";	//没有记录产生excel文件(生成excel失败)
	
	//邮件操作
	public static final String SEND_MAIL_SUCCESS = "0"; //发送邮件成功
	public static final String SEND_MAIL_FAILED = "1"; //发送邮件失败
	//邮件主题
	public static final String WAP_APACHE_SUBJECT = "wap_apache_日报"; //wap apache日报
	public static final String WAP_LANMU_SUBJECT = "wap_栏目_日报"; //wap lanmu日报
	public static final String WAP_OTHER_SUBJECT = "wap_其他统计_日报"; //wap 其它日报
	public static final String WEB_APACHE_SUBJECT = "web_apache_日报"; //web apache日报
	public static final String WEB_FORUM_SUBJECT  ="WEB_社区访问_日报";  //web社区访问
	public static final String WEB_PLATE_SUBJECT  ="WEB_板块访问_日报";  //web板块访问
	public static final String WEB_TOPIC_SUBJECT  ="WEB_主题访问_日报";  //web主题访问
	public static final String WEB_TOPIC_PLATE_INFO_SUBJECT  ="WEB_主题板块信息_INFO_日报";  //web主题板块信息
	public static final String WEB_USER_ACC_SUBJECT  ="WEB_USER_用户访问（行为）_日报";  //web用户访问（行为）
	public static final String WEB_USER_POINT_SUBJECT  ="WEB_用户积分_POINT_日报";  //web用户积分
	//邮件定制记录状态
	public static final String SEND_FLAG_STATE_A = "0"; //有效
	//file postfix 文件后缀
	public static final String EXCEL_POSTFIX = ".xls"; //excel文件后缀
	
	
	//不同平台文件路径
	public static final String WINDOWS_SEPARATRIX ="\\";
	public static final String LIUNX_SEPARATRIX ="/";
	
	//日志分隔符
	public static final String SPLIT_STR_1 = "\\|";
	
	//ftp目录
	public static final String WAP_APACHE_FTP_DIR = "mwapapache/"; //wap apache日报
	public static final String WAP_LANMU_FTP_DIR = "mwapservice/"; //wap lanmu日报
	public static final String WAP_OTHER_FTP_DIR = "mwapservice/"; //wap 其它日报
	public static final String WEB_APACHE_FTP_DIR = "mbbsapache/"; //web apache日报
	public static final String WEB_FORUM_FTP_DIR  ="forumtotal/";  //web社区访问
	public static final String WEB_PLATE_FTP_DIR  ="forumtotal/";  //web板块访问
	public static final String WEB_TOPIC_FTP_DIR  ="forumtotal/";  //web主题访问
	public static final String WEB_TOPIC_PLATE_INFO_FTP_DIR  ="forumtotal/";  //web主题板块信息
	public static final String WEB_USER_ACC_FTP_DIR  ="forumtotal/";  //web用户访问（行为）
	public static final String WEB_USER_POINT_FTP_DIR  ="forumtotal/";  //web用户积分
	
	public static final String FALSE = "false";
	public static final String TRUE = "true";
}
