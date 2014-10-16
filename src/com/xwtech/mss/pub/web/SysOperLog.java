package com.xwtech.mss.pub.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.web.SessionNameConstants;
import com.xwtech.mss.pub.UserBaseInfo;
import com.xwtech.mss.pub.UserLoginToken;
import com.xwtech.mss.pub.dao.system.OperationLogDAO;
import com.xwtech.mss.pub.dao.system.UserInfoDAO;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.tools.CommonOperation;
public class SysOperLog 
{
	private static OperationLogDAO operationLogDAO;

	private static UserInfoDAO userInfoDAO;
	
	public  OperationLogDAO getOperationLogDAO() {
		return operationLogDAO;
	}

	public void setOperationLogDAO(OperationLogDAO operationLogDAO) {
		this.operationLogDAO = operationLogDAO;
	}

	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	public SysOperLog() 
	{
		// TODO Auto-generated constructor stub
	}
/**
 *
 * @param httpRequest:HttpServletRequest类.
 * @param doType:  调用com.xwtech.mss.pub.constants.SysOperLogConstants方法.1:新增；2：修改；3：删除；4：作废；5：回复；6：确认
 * @param tableName:操做的数据库表名，如mpms_operation_log
 * @param doObject:操做的表对应的记录的ID
 * @param objType:调用com.xwtech.mss.pub.constants.SysOperLogConstants方法.
 * 					1:请购单;2:需求采购单;3:合同审批单;4:合同;5:商务合同记帐单;6:费用报销单;7:物品信息;8:厂家信息;9:人员信息;
 * 					10:附件;11:订单;12:签订合同;13:招标、比价;14:公司发文;15:物品信息;16:物品类别;17:用户权限;18:系统模板;
 * 					19:营业厅订货单23:营业厅预算;25:营业厅留言板;30:部门信息;
 * @param description:用户的描述信息，最长为30
 */

	public static void saveOperLog(HttpServletRequest httpRequest,Long doType,String tableName,Long doObject,Long objType,String description)
	{
		UserBaseInfo baseInfo;
		if(httpRequest.getSession()==null || httpRequest.getSession().getAttribute(SessionNameConstants.LOGIN_TOKEN) == null){
			baseInfo = null;
		}else{
			baseInfo = new CommonOperation().getLoginUserInfo(httpRequest);
		}
		UserInfo mpmsUserInfo = new UserInfo();
		if(baseInfo==null || baseInfo.getSysUser()==null){
			mpmsUserInfo = userInfoDAO.findById(new Long(1));
		}else{
			mpmsUserInfo = baseInfo.getSysUser();
		}
		if(mpmsUserInfo.getUserId()==null){
			mpmsUserInfo = userInfoDAO.findById(new Long(1));
		}
		
		OperationLog operationLog=new OperationLog();
		operationLog.setLoginName(mpmsUserInfo.getLoginName());
		operationLog.setDoType(doType);
		operationLog.setTableName(tableName);
		operationLog.setDoObject(doObject);
		operationLog.setObjType(objType);
		operationLog.setDescription(description!=null&&!description.equals("")&&description.length()>30?description.substring(0,30):description);
		operationLog.setDoTime(DateUtils.getChar12());
		
		operationLogDAO.save(operationLog);
		
	}

	

}
