package com.xwtech.mss.pub.constants;

/**
 * 
 * @title:通用常量类 
 * @description: 定义了系统常量信息
 * @author: Yao
 */
public class CommonConstants
{
	/** 每页显示记录数量 */
	public static final int MAX_PAGE_ITEMS = 10;
	/** 显示索引数量 */
	public static final int MAX_INDEX_PAGES = 3;

	/**超级管理员帐号*/
	public static final String SUPER_ADMIN_NAME = "admin";

	/**最高地区编码 例：江苏省*/
	public static final String SUPER_REGION_CODE = "0000";
	/**地区表地区级别--省份*/
	public static final Long REGION_LEVEL_PROVINCES = Long.valueOf(1);
	/**地区表地区级别--城市*/
	public static final Long REGION_LEVEL_CITY = Long.valueOf(2);
	/**地区表地区级别--区域*/
	public static final Long REGION_LEVEL_DISTRICT = Long.valueOf(3);

	/**资费类型 1-免费*/
	public static final int FEE_TYPE_1 = 1;
	/**资费类型 2-按条*/
	public static final int FEE_TYPE_2 = 2;
	/**资费类型 3-包月*/
	public static final int FEE_TYPE_3 = 3;

	/**群发任务状态 0-未审核*/
	public static final int TASK_STATUS_0 = 0;
	/**群发任务状态 1-审核通过*/
	public static final int TASK_STATUS_1 = 1;
	/**群发任务状态 2-审核驳回*/
	public static final int TASK_STATUS_2 = 2;
	/**群发任务状态 3-暂停*/
	public static final int TASK_STATUS_3 = 3;
	/**群发任务状态 4-发送中*/
	public static final int TASK_STATUS_4 = 4;
	/**群发任务状态 7-取消*/
	public static final int TASK_STATUS_7 = 7;

	/**逗号分割符*/
	public static final String SPLIT_SYMBOL_COMMA = ",";

	/**竖杆分割符*/
	public static final String SPLIT_SYMBOL_VERTICAL_LINE = "|";

	/**横线分割符*/
	public static final String SPLIT_SYMBOL_TRANSVERSE_LINE = "-";

	/**冒号分割符*/
	public static final String SPLIT_SYMBOL_COLON = ":";

	/**下划线分割符*/
	public static final String SPLIT_SYMBOL_UNDERLINE = "_";

	/**文件后缀名（文本文件）*/
	public static final String FILE_SUFFIX_TXT = ".txt";

	/**文件路径分割符*/
	public static final String FILE_SEPARATOR = "/";

	/**无上级单位的页面展示数据*/
	public static final String NO_PARENT_CORP = "---";

	/**系统类型 1-运营商管理系统*/
	public static final Long SYSTEM_TYPE_1 = new Long("1");
	/**系统类型 2-合作商管理系统*/
	public static final Long SYSTEM_TYPE_2 = new Long("2");

	/**公告已阅读*/
	public static long NOTICE_READ_STATUS = 1;

	/**公告未阅读*/
	public static long NOTICE_NEVER_READ_STATUS = 0;

	/**技术支持已回复*/
	public static long SUPPORT_REPLY_STATUS = 1;

	/**技术支持未回复*/
	public static long SUPPORT_NOREPLY_STATUS = 0;

	/**
	 * 普通公告
	 */
	public static long COMMON_NOTICE = 1;

	/**
	 * 技术支持公告
	 */
	public static long TECHNIC_NOTICE = 2;

	/**状态 1-有效*/
	public static final long STATUS_VALID = 1L;
	/**状态 0-无效*/
	public static final long STATUS_INVALID = 0L;

	/**用户是否永久有效 1-有效*/
	public static final String USER_VALID_1 = "Y";
	/**用户是否永久有效 0-无效*/
	public static final String USER_VALID_0 = "N";

	/**用户类型 0-超级管理员*/
	public static final long USER_TYPE_SUPER_ADMIN = 0;
	/**用户类型 1-管理员*/
	public static final long USER_TYPE_ADMIN = 1;
	/**用户类型 2-操作员*/
	public static final long USER_TYPE_OPERATOR = 2;

	/**内容库新增类型 1-内容库管理页面的新增*/
	public static final String CONT_LIB_ADD_TYPE_MGR = "1";
	/**内容库新增类型 2-业务申请处的新增*/
	public static final String CONT_LIB_ADD_TYPE_BIZ = "2";

	/**短/彩信内容状态 0-未审核*/
	public static final int CONT_STATUS_0 = 0;
	/**短/彩信内容状态 1-内部审核通过,待运营商审核*/
	public static final int CONT_STATUS_1 = 1;
	/**短/彩信内容状态 2-内部审核驳回*/
	public static final int CONT_STATUS_2 = 2;
	/**短/彩信内容状态 3-审核通过*/
	public static final int CONT_STATUS_3 = 3;
	/**短/彩信内容状态 4-审核驳回*/
	public static final int CONT_STATUS_4 = 4;
	/**短/彩信内容状态 5-上线中*/
	public static final int CONT_STATUS_5 = 5;
	/**短/彩信内容状态 6-下线*/
	public static final int CONT_STATUS_6 = 6;
	/**
	 * 内部审核后需要运营商再次审核
	 */
	public static final long NEED_OPERA_AUDIT = 1;

	/**
	 * 内部审核后不需要运营商再次审核
	 */
	public static final long NOT_NEED_OPERA_AUDIT = 0;

	/**内容类型 1-短信*/
	public static final int CONT_TYPE_SMS = 1;
	/**内容类型 2-彩信*/
	public static final int CONT_TYPE_MMS = 2;
	/**
	 * 上线业务状态
	 */
	public static final long BIZ_STATUS_ONLINE = 8;

	/**
	 * 单个业务代订
	 */
	public static final long TASK_SUBSCRIBLE_SINGLE_ADD = 1;

	/**
	 * 批量业务代订
	 */
	public static final long TASK_SUBSCRIBLE_BATCH_ADD = 2;

	/**
	 * 单个业务退订
	 */
	public static final long TASK_SUBSCRIBLE_SINGLE_DELETE = 3;

	/**
	 * 批量业务退订
	 */
	public static final long TASK_SUBSCRIBLE_BATCH_DELETE = 4;

	/**
	号码输入方式系统提供
	*/
	public static final long IMPORT_MODEL_SYSTEM_PROVIDE = 0;

	/**
	号码输入手工方式
	*/
	public static final long IMPORT_MODEL_HANDWORK = 1;

	/**
	号码输入文件方式
	*/
	public static final long IMPORT_MODEL_FILE = 2;

	/**
	 * 业务订制状态 未审核
	 */
	public static final long TASK_SUBSCRIBLE_STATUS_WAIT_AUDID = 0;

	/**
	 * 业务订制状态 审核不通过
	 */
	public static final long TASK_SUBSCRIBLE_STATUS_AUDID_NOT_PASS = 2;

	/**
	 * 彩信存放路径
	 */
	public static final String MMS_SEND_SOURCE_PATH = "/send/";

	/**
	 * 采集帧内容类型 1-图片
	 */
	public static final String MMS_CONT_TYPE_IMG = "1";

	/**
	 * 采集帧内容类型 2-文字
	 */
	public static final String MMS_CONT_TYPE_TXT = "2";

	/**
	 * 采集帧内容类型 3-声音
	 */
	public static final String MMS_CONT_TYPE_AUDIO = "3";

	/**
	 * 地区限制
	 */
	public static int REGION_LIMIT = 1;

	/**
	 * 无地区限制
	 */
	public static int NOT_REGION_LIMIT = 0;

	/**
	 * 是否需要内部审核 0-不需要
	 */
	public static final long CONT_AUDIT_FLAG_NO = 0;

	/**
	 * 是否需要内部审核 1-需要
	 */
	public static final long CONT_AUDIT_FLAG_YES = 1;

	/**
	 * 彩信帧编辑标志位 1-添加
	 */
	public static final String MMS_FRAME_EDIT_ADD = "1";

	/**
	 * 彩信帧编辑标志位 2-修改
	 */
	public static final String MMS_FRAME_EDIT_UPDATE = "2";

	/**
	 * 彩信审核标志位 1-内部复核
	 */
	public static final String MMS_AUDIT_FLAG_CHECK = "1";

	/**
	 * 彩信审核标志位 2-运营商审核
	 */
	public static final String MMS_AUDIT_FLAG_AUDIT = "2";

	/**
	 * 彩信查询标志位 1-审核查询
	 */
	public static final String MMS_QUERY_FLAG_AUDIT = "1";

	/**
	 * 彩信查询标志位 2-列表查询
	 */
	public static final String MMS_QUERY_FLAG_LIST = "2";

	/**
	 * 彩信是否需要地区限制标志位 1-限制
	 */
	public static final String MMS_REGION_CONSTRAINT_YES = "1";

	/**
	 * 彩信是否需要地区限制标志位 0-不限制
	 */
	public static final String MMS_REGION_CONSTRAINT_NO = "0";

	/**
	 * 操作类型登陆
	 */
	public static long OPERATE_TYPE_LOGIN = 0;

	/**
	 * 操作类型增加
	 */
	public static long OPERATE_TYPE_ADD = 1;

	/**
	 * 操作类型删除
	 */
	public static long OPERATE_TYPE_DELETE = 2;

	/**
	 * 操作类型修改
	 */
	public static long OPERATE_TYPE_UPDATE = 3;

	/**
	 * 操作类型审核
	 */
	public static long OPERATE_TYPE_AUDIT = 4;

	/**合作商申请提交成功*/
	public static String SP_APPLY_SUBMIT_SUCCESS = "您的加盟申请已收到，我们会在3个工作日内对您的加盟申请进行审核，届时会将审核结果以短信方式通知您，谢谢您对数字内容发布平台的支持！";

	/**
	 * 合作商申请未审核
	 */
	public static final long SP_APPLY_NO_AUDIT = 0;
	/**
	 * 合作商申请审核通过
	 */
	public static final long SP_APPLY_AUDIT_ALLOW = 1;

	/**
	 * 合作商申请审核没有通过（即被驳回）
	 */
	public static final long SP_APPLY_AUDIT_UNALLOW = 2;

	/**
	 * 合作商资料修改申请未审核
	 */
	public static final long SP_APPLY_INFOEDIT_NO_AUDIT = 0;
	/**
	 * 合作商资料修改申请审核通过
	 */
	public static final long SP_APPLY_INFOEDIT_AUDIT_ALLOW = 1;

	/**
	 * 合作商资料修改申请审核没有通过（即被驳回）
	 */
	public static final long SP_APPLY_INFOEDIT_AUDIT_UNALLOW = 2;

	/**
	 * 合作商的初始角色ID
	 */
	public static final long SP_INIT_ROLE_ID = 1;
	/**
	 * 合作商的初始密码
	 */
	public static final String PASSWORD = "111111";
	/**
	 * 合作商的初始用户前缀
	 */
	public static final String SP_USERNAME_PREFIX = "admin_";

	/**
	 * 特服号最长长度(彩信，短信)
	 */
	public static final String SP_CODE_MAX_LENGTH = "20";

	/**
	 * 单位类型－运营商单位
	 */
	public static final Long CORP_STYPE_OPER = Long.valueOf("1");
	/**
	 * 单位类型－合作商单位
	 */
	public static final Long CORP_STYPE_SP = Long.valueOf("2");

	/**
	 * 上行方式--1 短信
	 */
	public static final long MO_TYPE_SMS = 1;
	/**
	 *  上行方式--2 彩信
	 */
	public static final long MO_TYPE_MMS = 2;

	/**
	 * 权限类型--1 菜单
	 */
	public static final long PERMISSION_TYPE_1 = 1;

	/**
	 * 权限类型--2 按钮
	 */
	public static final long PERMISSION_TYPE_2 = 2;

}
