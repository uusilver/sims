package com.xwtech.mss.pub.constants;

import com.xwtech.framework.pub.web.BaseFrameworkApplication;

//记录系统常量

public class DmsConstants {

	  public static final int COUNT_FOR_EVERY_PAGE = 10;//每业显示条数
	  
	  // tables name in hql
	  public static final String MOD_MATERIAL_REQ = "com.xwtech.dms.pub.po.DmsModStatistics";  
	  public static final String C_USER_INFO = "com.xwtech.dms.pub.po.CUserInfo"; 
	  public static final String C_ORG_NJ = "com.xwtech.dms.pub.po.COrganizationNj";
	  public static final String DMS_MATERIAL_STATISTICS = "com.xwtech.dms.pub.po.DmsMaterialStatistics";
	  public static final String DMS_STA_CONTENT = "com.xwtech.dms.pub.po.DmsStaContent";
	  public static final String DMS_MATERIAL_INFO = "com.xwtech.dms.pub.po.DmsMaterialInfo";
	  public static final String C_ORG_NJ_TYPE = "com.xwtech.dms.pub.po.COrganizationType";
	  public static final String C_ORG_NJ_TYPE_ID = "com.xwtech.dms.pub.po.COrganizationTypeId";
	  public static final String DMS_USER_PROPERTY = "com.xwtech.dms.pub.po.DmsUserProperty";
	  
	  
	  //关帐
	  public static final String DMS_ACCOUNT_CLOSE = "C";
	  
	  //调帐开始时间
	  public static final String DMS_MOD_ACCOUNT_START_TIME = "MS";
	  
	  //调帐开始时间
	  public static final String DMS_MOD_ACCOUNT_END_TIME = "MN";
	  
	  //物料申领开始时间
	  public static final String DMS_MATERIAL_APP_START_TIME = "RS";
	  
	  //物料申领结束时间
	  public static final String DMS_MATERIAL_APP_END_TIME = "RN";
	  
	   //营业厅未入库告警时限
      public static final String DMS_MATERIAL_NOTIN_BUSS_ALARM = "BJ";
      
      //营业厅未入库上级告警时限
      public static final String DMS_MATERIAL_NOTIN_LEAD_ALARM = "SJ";

      //物料有效期
      public static final String DMS_MATERIAL_EFFECT_TIME = "YX";
      
      //系统短信发送时间
      public static final String DMS_SMS_SEND_TIME = "SS";
      
	  //库房
	  public static final String CLOSE_OBJECT_NAME_DEPOT = "DEPOT";
	  
	  //区域
	  public static final String CLOSE_OBJECT_NAME_AREA = "AREA";
	  
	  //营业厅
	  public static final String CLOSE_OBJECT_NAME_BUSS = "BUSS";

	  public static final String DMS_MATERIAL_FLOW_INFO = "com.xwtech.dms.pub.po.DmsMaterialFlowInfo";
	  public static final String DMS_MATERIAL_RELATION = "com.xwtech.dms.pub.po.DmsMaterielRelation";
	  public static final String DMS_MATERIAL_TOTAL_INFO = "com.xwtech.dms.pub.po.DmsFlowTotalInfo";

      
      // form type  单据类型
      public static final Long FORM_TYPE_REQUIRE = new Long(0);//物料需求单
      public static final Long FORM_TYPE_APPLY = new Long(1);//物料申领
      public static final Long FORM_TYPE_EXPORT = new Long(2);//物料出库单
      public static final Long FORM_TYPE_BACK = new Long(3);//物料退库单
      public static final Long FORM_TYPE_HANDOUT = new Long(4);//统一下发单
      public static final Long FORM_TYPE_CUS_BACK = new Long(5);//顾客退库单
      public static final Long FORM_TYPE_IMPORT = new Long(6);//物料入库单
      public static final Long FORM_TYPE_BACK_APPLY = new Long(7);//物料退库申请单
      public static final Long FORM_TYPE_CUS_EXPORT = new Long(8);//顾客出库单
      public static final Long FORM_TYPE_SUB_BACK = new Long(9);//下级退库入库单
      public static final Long FORM_TYPE_HELP_BACK_APPLY = new Long(10);//调配退库申请单
      public static final Long FORM_TYPE_HELP_BACK = new Long(11);//调配退库单
      public static final Long FORM_TYPE_HELP_SELF_IMPORT = new Long(12);//本区调配入库单
      public static final Long FORM_TYPE_HELP_OTHER_IMPORT = new Long(13);//别区调配入库单
      public static final Long FORM_TYPE_HELP_OUT = new Long(14);//外协出库单
      public static final Long FORM_TYPE_WASTE_OUT = new Long(15);//损耗出库单
      public static final Long FORM_TYPE_DIS_IMPORT = new Long(16);//差异量补入单
      public static final Long FORM_TYPE_DIS_BACK_APPLY = new Long(17);//差异量退库申请单
      public static final Long FORM_TYPE_DIS_BACK = new Long(18);//差异量退库单
      public static final Long FORM_TYPE_CONVERT_OUT = new Long(19);//活动类型转换出库单
      public static final Long FORM_TYPE_CONVERT_IMPORT = new Long(20);//活动类型转换入库单
      public static final Long FORM_TYPE_HANDOUT_CONFIG = new Long(21);//模板配置统一下发单
      public static final Long FORM_TYPE_ADJUST_BACK = new Long(22);//调配中转入库单
      public static final Long FORM_TYPE_ADJUST_OUT = new Long(23);//调配出库单
      public static final Long FORM_TYPE_HANDOUT_CONFIG_TEMP = new Long(24);//模板配置统一下发临时出库
      public static final Long FORM_TYPE_MAT_CLEAR_ZERO = new Long(25);//物料清零申请单
      public static final Long FORM_TYPE_DIFF_CLEAR_ZERO = new Long(26);//差异量清零申请单
      //调配入库退回所添加的单据类型
      public static final Long FORM_TYPE_ADJUST_OUT_BACK_APPLY = new Long(27); //调配入库退回申请单
      public static final Long FORM_TYPE_ADJUST_OUT_BACK = new Long(28);  //调配入库退回单
      public static final Long FORM_TYPE_ADJUST_BACK_IMPORT = new Long(29); //调配退回入库
      public static final Long FROM_TYPE_ADJUST_IMPORT_AGAIN = new Long(30); //调配2次入库
      
      
      //操作类型
      public static final Long OPER_TYPE_REQUIRE = new Long(0);//物料需求单
      public static final Long OPER_TYPE_APPLY = new Long(1);//物料申领
      public static final Long OPER_TYPE_EXPORT = new Long(2);//物料出库单
      public static final Long OPER_TYPE_BACK = new Long(3);//物料退库单
      public static final Long OPER_TYPE_HANDOUT = new Long(4);//统一下发单
      public static final Long OPER_TYPE_CUS_BACK = new Long(5);//顾客退库单
      public static final Long OPER_TYPE_IMPORT = new Long(6);//物料入库单
      public static final Long OPER_TYPE_BACK_APPLY = new Long(7);//物料退库申请单
      public static final Long OPER_TYPE_CUS_EXPORT = new Long(8);//顾客出库单
      public static final Long OPER_TYPE_BACK_IMPORT = new Long(9);//下级退库入库单
      public static final Long OPER_TYPE_HELP_BACK_APPLY = new Long(10);//调配退库申请单
      public static final Long OPER_TYPE_HELP_BACK = new Long(11);//调配退库单
      public static final Long OPER_TYPE_HELP_SELF_IMPORT = new Long(12);//本区调配入库单
      public static final Long OPER_TYPE_HELP_OTHER_IMPORT = new Long(13);//别区调配入库单
      public static final Long OPER_TYPE_HELP_OUT = new Long(14);//外协出库单
      public static final Long OPER_TYPE_WASTE_OUT = new Long(15);//损耗出库单
      public static final Long OPER_TYPE_DIS_IMPORT = new Long(16);//差异量补入单
      public static final Long OPER_TYPE_DIS_BACK_APPLY = new Long(17);//差异量退库申请单
      public static final Long OPER_TYPE_DIS_BACK = new Long(18);//差异量退库单
      public static final Long OPER_TYPE_CONVERT_OUT = new Long(19);//活动类型转换出库单
      public static final Long OPER_TYPE_CONVERT_IMPORT = new Long(20);//活动类型转换入库单
      public static final Long OPER_TYPE_HANDOUT_CONFIG = new Long(21);//模板配置统一下发单
      public static final Long OPER_TYPE_ADJUST_BACK = new Long(22);//调配中转入库单
      public static final Long OPER_TYPE_ADJUST_OUT = new Long(23);//调配出库单
            
      public static final Long OPER_TYPE_DIFF_CLEAR_ZERO = new Long(25);//差异量清零申请单
      public static final Long OPER_TYPE_HANDOUT_CONFIG_TEMP = new Long(26);//模板配置统一下发临时出库
      //调配入库退回所添加的操作
      public static final Long OPER_TYPE_ADJUST_OUT_BACK_APPLY = new Long(27); //调配入库退回申请单
      public static final Long OPER_TYPE_ADJUST_OUT_BACK = new Long(28);  //调配入库退回单
      public static final Long OPER_TYPE_ADJUST_BACK_IMPORT = new Long(29); //调配退回入库
      public static final Long OPER_TYPE_ADJUST_IMPORT_AGAIN = new Long(30); //调配2次入库
      
      //记录单据状态
      public static final Long FORM_STATUS_SAVE = new Long(1);//保存
      public static final Long FORM_STATUS_SUBMIT = new Long(2);//提交
      public static final Long FORM_STATUS_BACK = new Long(3);//退回
      public static final Long FORM_STATUS_STAY = new Long(4);//暂留
      public static final Long FORM_STATUS_FINISH = new Long(5);//完成
      public static final Long FORM_STATUS_DISABLE = new Long(6);//作废
      public static final Long FORM_STATUS_EXAMINED = new Long(7);//已审批
      
      //流水状态
      public static final String FLOW_STATE_SUBMIT = "S";//提交
      public static final String FLOW_STATE_FINISH = "F";//完成
      public static final String FLOW_STATE_BACK = "B";//退回（目前在调配入库退回中使用）
      
      //短信模版类型
      public static final Long SMS_TEMP_TYPE_OUT = new Long(1);//出库
      public static final Long SMS_TEMP_TYPE_APPLY = new Long(0);//申领
      public static final Long SMS_TEMP_TYPE_NOT_IMPORT = new Long(2);//未入库
      public static final Long SMS_TEMP_TYPE_NOT_EXPORT = new Long(3);//未出库
      public static final Long SMS_TEMP_TYPE_OVERDUE= new Long(4);//过期
      public static final Long SMS_TEMP_TYPE_LACK = new Long(5);//库存不足
      
      // 出账报表excel文件导出目录
      public static final String REPORTFORMDOWNPATH = BaseFrameworkApplication.FrameworkWebAppRootPath+"dms/jsp/statistics/";
      
      //订单状态
      public static final int SAVE_REQ_INFO = 1;  //保存
      public static final int SAVE_AS_REQUIRE_REQ_INFO = 2;  //保存为需求但
      public static final int SUBMIT_REQ_INFO = 3;  //提交
      public static final int NO_BUDGET_REQ_INFO = 4;  //无预算
      public static final int HANDLE_REQ_INFO = 5;  //处理中
      public static final int FINISH_REQ_INFO = 6;  //完成
      public static final int DISABLE_REQ_INFO = 7;  //作废
      
      //订单操作步骤
      public static final int INDENT_SAVE_SUCCED = 1;  //保存订货单
      public static final int MAIL_CONNECT_FAILE = 2;  //邮件服务器连接不上
      public static final int ORDER_SEND_SUCCED = 3;  //订单生成
      
      
      //订单类型
      public static final int ORDER_TYPE_COMMON = 1;  //普通订货单
      public static final int ORDER_TYPE_NET = 2;  //网络设备订货单
      public static final int ORDER_TYPE_BUY = 3;  //购货单
      public static final int ORDER_TYPE_CONTR= 4;  //签订合同
      public static final int ORDER_TYPE_COMP = 5;  //招标比价
      public static final int ORDER_TYPE_BUS = 6;  //营业厅订单

      //采购品状态
      public static final int MATE_STATE_WAIT_CONTR = 0;  //待签合同
      public static final int MATE_STATE_WAIT_DISTR = 1;  //待分发
      public static final int MATE_STATE_WAIT_HANDLE = 2;  //待处理
      public static final int MATE_STATE_WAIT_PAY= 3;  //待付款
      public static final int MATE_STATE_WAIT_FINISH = 4;  //完成
      public static final int MATE_STATE_DISABLE = 5;  //作废
      
      //订单状态
      public static final String ORDER_STATE_AVAILABLE = "A";  //有效
      public static final String ORDER_STATE_UNAVAILABLE = "U";  //无效
      public static final String ORDER_STATE_PIGONHOLE = "2";  //已归档
      public static final String ORDER_STATE_UNPIGONHOLE = "1";  //未归档
      
      //短信提醒类型
      public static final int MESSAGE_TYPE_APPLY = 0; //物料申领
      public static final int MESSAGE_TYPE_HANDOUT = 1;//物品统一下发
      public static final int MESSAGE_TYPE_NOIMPORT = 2;//时限为入库
      public static final int MESSAGE_TYPE_NOEXPORT = 3;//时限未出库
      public static final int MESSAGE_TYPE_OVERDUE = 4;//物料过期
      public static final int MESSAGE_TYPE_LACK = 5;//库存量不足
      
      //短信内容状态
      public static final String MESSAGE_STATE_NOSEND = "N";  //未发送
      public static final String MESSAGE_STATE_ALSEND = "Y";  //已发送
      
      //短信内容提醒--宏
      public static final String MESSAGE_MACRO_DATE = "date"; //日期
      public static final String MESSAGE_MACRO_UPPERORG = "upperOrg"; //上级
      public static final String MESSAGE_MACRO_LOWERORG = "lowerOrg"; //下级
      public static final String MESSAGE_MACRO_GOODSINFO = "goodsInfo"; //物品
      public static final String MESSAGE_MACRO_ORGNAME = "orgName"; //营业厅名称
      public static final String MESSAGE_MACRO_TIMELIMIT = "timeLimit"; //时间期限
      
      //操作类型--联系信息更新
      public static final Long LINK_UPDATE_TYPE_ORG = new Long(0);
      public static final Long LINK_UPDATE_TYPE_AREA = new Long(1); 
      public static final Long LINK_UPDATE_TYPE_ORG_CLEAR = new Long(2); 
      
      
      //用户层级
      public static final String LEVEL_MASTERMIND = "-1";//策划组
      public static final String LEVEL_DEPOT = "0";//库房
      public static final String LEVEL_CANTONAL_CENTER_OR_COUNTY_COMPANY = "1";//市区营销中心/县公司
      public static final String LEVEL_AREA = "2";//区域
      
      //物料大类
      public static final String WHOLE_TYPE_YINGXIAO = "1";//营销类
      public static final String WHOLE_TYPE_PROJECT = "2";//工程类
      public static final String WHOLE_TYPE_PERMANENT_ASSETS = "3";//固定资产类
      public static final String WHOLE_TYPE_NOT_PERMANENT_ASSETS_A = "4";//非固定资产A类
      public static final String WHOLE_TYPE_NOT_PERMANENT_ASSETS_B = "5";//非固定资产B类
      
      //物料状态记录（需求物料流水）中物料状态(MATERIAL_STATE)状态
      public static final String MATERIAL_STATE_SAVE = "1";//1．保存；
      public static final String MATERIAL_STATE_SUBMIT = "2";//2．待审批；
      public static final String MATERIAL_STATE_BACK = "3";//3．退回；
      public static final String MATERIAL_STATE_OUT = "4";//4．待入库;
      public static final String MATERIAL_STATE_TO_SUM = "5";//5．待汇总；
      public static final String MATERIAL_STATE_SUM = "6";//6．已汇总；
      public static final String MATERIAL_STATE_REQ = "7";//7．已请购；
      public static final String MATERIAL_STATE_TO_IN = "8";//8．待收货；
      public static final String MATERIAL_STATE_DONE = "9";//9．完成。
      public static final String MATERIAL_STATE_TO_REQ = "0";//0．待请购。
      
      //物料状态记录（需求物料流水）中当前物料所处层级(CURR_LEVEL)
      public static final String MATER_CURR_LEVEL_ORG = "1";//1 营业厅
      public static final String MATER_CURR_LEVEL_AREA = "2";//2 区域
      public static final String MATER_CURR_LEVEL_CENTER = "3";//3 市区营销中心
      public static final String MATER_CURR_LEVEL_MART = "4";//4 市场部
      
      
      //物料状态记录（需求物料流水）中各层级操作单类型,同操作总记录表中操作类型(OPER_TEYP)
      public static final String OPER_TEYP_APPLY_SUBMIT = "1";//1 物料申请
      public static final String OPER_TEYP_MATER_OUT = "2";//2 物料下发
      public static final String OPER_TEYP_MATER_BACK = "3";//3 物料退回
      public static final String OPER_TEYP_APPLY_SUM = "4";//4 物料汇总
      public static final String OPER_TEYP_APPLY_REQ = "5";//5 物料请购
      public static final String OPER_TEYP_MATER_IN = "6";//6 物料入库
      
      //操作总记录表中操作单状态(OPER_STATE)
      public static final String MATER_OPER_STATE_SAVE = "0";//0 保存
      public static final String MATER_OPER_STATE_TO_DONE = "1";//1 待处理
      public static final String MATER_OPER_STATE_DOING = "2";//2 处理中
      public static final String MATER_OPER_STATE_DONE = "3";//3 已处理
      
      // 全部县公司的TYPE
      public static final String COUNTY_ALL_TYPE = "'57','58','59','60','61'";
      
      //全部区域的TYPE
      public static final String AREA_ALL_TYPE = "'51','52','53','54','55'";
      
      public static final String DMS_IP_TEST = "http://192.168.16.30/dms/jsp/main.jsp";
      public static final String DMS_IP_TEST_2 = "http://10.33.89.37:8899/dms/jsp/main.jsp";
      public static final String MPMS_IP_TEST = "http://10.33.32.87:8899/mpms/mpms/jsp/main.jsp";
      
      public static final String DMS_IP = "http://10.33.89.37/dms/jsp/main.jsp";
      public static final String MPMS_IP = "http://10.33.32.87:88/mpms/mpms/jsp/main.jsp";
      
}
