package com.xwtech.mss.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.business.ClientInfoBO;
import com.xwtech.mss.bo.business.ClientServerMappingBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.Client;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.ChineseSpellingToPinYin;
import com.xwtech.mss.pub.tools.CommonOperation;

public class ClientInfoController extends MultiActionController {
	private static final Log log = LogFactory.getLog(ClientInfoController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private ClientInfoBO clientInfoBO;
	
	private ClientServerMappingBO clientServerMappingBO;
	
	private OperLogBO operLogBO;


	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public void setClientInfoBO(ClientInfoBO clientInfoBO) {
		this.clientInfoBO = clientInfoBO;
	}
	
	public void setClientServerMappingBO(ClientServerMappingBO clientServerMappingBO) {
		this.clientServerMappingBO = clientServerMappingBO;
	}

	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	/**
	 * 保存或者更新客户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveClientInfo(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//客户id
		final String clientId = request.getParameter("clientNum");
		
		//客户名称
		final String trueName = request.getParameter("clientName");
		
		//收件地址
		final String loginName = request.getParameter("userName");
		
		//客户联系电话
		final String password = request.getParameter("password");
		
		//是否要求修改密码
		final String modifyPass = request.getParameter("modifyPass");
		
		//校验类型
		final String authenticationType = request.getParameter("authType");
		
		//Disable
		final String disableFlag = request.getParameter("disableFlag");
		
		//用户类型
		final String userType = request.getParameter("userType");
		
		//固定电话
		final String telePhone = request.getParameter("telePhone");
		
		//移动电话
		final String mobilePhone = request.getParameter("mobilePhone");
		
		//服务器ID串,如：1，2，3，4
		final String serverIds = request.getParameter("hiddenServerIds");
		
		//备注
		final String clientComment = request.getParameter("clientComment");

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();
		final Long userId = sysUser.getUserId();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 1.添加客户信息
				Client clientInfo = null;
				OperationLog oper = new OperationLog();
				
				// 新增
				if (null == clientId || "".equals(clientId)) {
					clientInfo = new Client();
					clientInfo.setAddWho(new Integer(userId.toString()));
					clientInfo.setAddTime(DateUtils.convertString2Date(DateUtils.getChar14()));	
					oper.setDoType(new Long(MssConstants.OPER_TYPE_INSERT));
					oper.setDescription("新增客户信息：姓名：【"+trueName+"】");
				}
				// 更新
				else { 
					clientInfo = clientInfoBO.findById(new Integer(clientId));
					clientInfo.setEditWho(new Integer(userId.toString()));
					clientInfo.setEditTime(DateUtils.convertString2Date(DateUtils.getChar14()));	
					oper.setDoType(new Long(MssConstants.OPER_TYPE_UPDATE));
					oper.setDescription("修改客户信息，ID：【"+clientInfo.getClientid()+"】，姓名：【"+clientInfo.getTruename()+"】 为 ：【"+trueName+"】");
				}

				try{
					clientInfo.setUsername(loginName);
					clientInfo.setPassword(password);
					clientInfo.setAuthenticationtype((authenticationType!=null&&!"".equals(authenticationType))?new Integer(authenticationType):null);
					clientInfo.setModifypass((modifyPass!=null&&!"".equals(modifyPass))?new Integer(modifyPass):null);
					clientInfo.setDisable((disableFlag!=null&&!"".equals(disableFlag))?new Integer(disableFlag):null);
					clientInfo.setTruename(trueName);
					clientInfo.setUsertype((userType!=null&&!"".equals(userType))?new Integer(userType):null);
					clientInfo.setTelephone(telePhone);
					clientInfo.setMobilephone(mobilePhone);
					clientInfo.setStatus(SpmsConstants.STATE_A);
					clientInfo.setNote(clientComment);
					//用户姓名首字母
			      	ChineseSpellingToPinYin py2 = new ChineseSpellingToPinYin();
					clientInfo.setFirstLetter(py2.getFirstletterByName(loginName));
					clientInfoBO.saveOrUpdate(clientInfo);
					
					//保存客户端可以访问的服务器记录
					if(serverIds!=null&&!"".equals(serverIds)){
						int result = clientServerMappingBO.delMappingRecords(serverIds, clientInfo.getClientid().toString());
						if(result>=0){
							clientServerMappingBO.saveClientServerLink(clientInfo.getClientid().toString(), serverIds);
						}
					}
					
					resultInfos.add(new ResultInfo(ResultConstants.ADD_CLIENT_INFO_SUCCESS));
					
					//保存操作日志
					
					//新增客户信息
					oper.setDoObject(new Long(MssConstants.OPER_OBJECT_CLIENT));
					//50：客户信息
					oper.setObjType(new Long(MssConstants.OPER_OBJECT_CLIENT));
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName(MssConstants.OPER_TABLE_CLIENT);
					operLogBO.save(oper);
					
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_CLIENT_INFO_FAILED));
					e.printStackTrace();
					status.setRollbackOnly();
				}
				resultInfos.setGotoUrl("/mss/jsp/client/clientInfoController.do?method=queryClientInfoList&addOrView='edit'");
				resultInfos.setIsAlert(true);
				resultInfos.setIsRedirect(true);
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
	}
	
	/**
	 * 查询客户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView queryClientInfoList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		ClientInfoForm clientInfoForm = new ClientInfoForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "edit" : request.getParameter("viewOrEdit").trim();
		//是否显示查询条件框
		String showHeader = request.getParameter("showHeader");
		if(showHeader==null||"".equals(showHeader)){
			showHeader="yes";
		}
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			clientInfoForm.setQueryStatus(SpmsConstants.STATE_A);
			clientInfoForm.setViewOrEdit(viewOrEdit);
			clientInfoForm.setShowHeader(showHeader);
			SessionUtils.setObjectAttribute(request, "clientInfoFormSession", clientInfoForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			clientInfoForm = (ClientInfoForm) SessionUtils.getObjectAttribute(request, "clientInfoFormSession");
		} else {
			String currentPage = request.getParameter("currentPage");
			String queryClientName = request.getParameter("queryClientName");
			String queryAuthType = request.getParameter("queryAuthType");
			String queryDisableFlag = request.getParameter("queryDisableFlag");
			String queryStatus = request.getParameter("queryStatus");
			String queryUserType = request.getParameter("queryUserType");
			String queryClientGroup = request.getParameter("queryClientGroup");
			

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}

			clientInfoForm.setCurrentPage(currentPage);
			clientInfoForm.setQueryClientName(queryClientName);
			clientInfoForm.setQueryAuthType(queryAuthType);
			clientInfoForm.setQueryDisableFlag(queryDisableFlag);
			clientInfoForm.setQueryStatus(queryStatus);
			clientInfoForm.setQueryUserType(queryUserType);
			clientInfoForm.setViewOrEdit(viewOrEdit);
			clientInfoForm.setShowHeader(showHeader);
			clientInfoForm.setQueryClientGroup(queryClientGroup);

			SessionUtils.setObjectAttribute(request, "clientInfoFormSession", clientInfoForm);
		}

		HashMap clientInfoResult = clientInfoBO.queryClientInfoList(clientInfoForm,String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));

		map.put("clientInfoList", (List) clientInfoResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, clientInfoResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, clientInfoResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, clientInfoResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", clientInfoForm);
		map.put("accessType", ("menu".equals(accessType) ? "" : accessType));

		return new ModelAndView("/mss/jsp/client/clientInfoList.jsp", RequestNameConstants.INFORMATION, map);
		
	}
	
	/**
	 * 根据客户类别ID查询客户类别详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryClientInfoById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		ClientInfoForm clientInfoForm = new ClientInfoForm();
		String clientId = request.getParameter("clientNum");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		String showHeader = request.getParameter("showHeader");
		String currentPage = request.getParameter("currentPage");
		String queryUserName = request.getParameter("queryUserName");
		String queryAuthType = request.getParameter("queryAuthType");
		String queryDisableFlag = request.getParameter("queryDisableFlag");
		String queryUserType = request.getParameter("queryUserType");
		String queryStatus = request.getParameter("queryStatus");
		String queryClientGroup = request.getParameter("queryClientGroup");
		clientInfoForm.setQueryStatus(SpmsConstants.STATE_A);
		
		Client clientInfo = null;
		String typeNameStr = "";
		String typeNumStr = "";
		List csMappingList = null;
		List unAccessedServerList = null;
		String csMappingResult = "";
		
		if(clientId!=null&&!clientId.equals("")){
			
			clientInfo = clientInfoBO.findById(new Integer(clientId));
			
			//查询所有该客户端不能访问的服务器对象
//			List serverList = serverGroupMappingBO.queryServerTextByGroupId(null);
			unAccessedServerList = clientInfoBO.queryUnAccessedServer(clientId,false);

			//查询该客户端可以访问的服务器对象
			csMappingList = clientInfoBO.queryUnAccessedServer(clientId,true);
			
			if(csMappingList!=null&&!csMappingList.isEmpty()){
				Gson gson = new Gson();
				csMappingResult= gson.toJson(csMappingList);
			}
		}
		//新增客户端
		else if(viewOrEdit!=null&&MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)) {
			//查询所有服务器对象
			unAccessedServerList = clientInfoBO.queryUnAccessedServer(null,false);
		}
		
		clientInfoForm.setClientId(clientId);
		clientInfoForm.setCurrentPage(currentPage);
		clientInfoForm.setQueryAuthType(queryAuthType);
		clientInfoForm.setQueryClientName(queryUserName);
		clientInfoForm.setQueryDisableFlag(queryDisableFlag);
		clientInfoForm.setQueryUserType(queryUserType);
		clientInfoForm.setQueryStatus(queryStatus);
		clientInfoForm.setQueryClientGroup(queryClientGroup);
		clientInfoForm.setViewOrEdit(viewOrEdit);
		clientInfoForm.setShowHeader(showHeader);
		
		map.put("clientInfo", clientInfo);
		map.put("searchForm",clientInfoForm);
		map.put("serverList", unAccessedServerList);
		map.put("csMappingResult", csMappingResult);
		return new ModelAndView("/mss/jsp/client/clientInfoAdd.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 删除用户选择的记录（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delClientInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();
		
		
		// 获得页面表单信息
		final String clientNumStr = request.getParameter("clientNumStr");
		
		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//删除操作返回值
				int result = 0;
				List clientNameList = null;
				String clientNameStr = "";
				String clientIdStr = "";
				try {
					if(clientNumStr!=null&&!clientNumStr.equals("")){
//						clientNameList = clientInfoBO.queryGoodsName(clientNumStr.substring(0, clientNumStr.length()-1));
						// 根据权限ID删除相关权限信息
						clientInfoBO.delClientInfo(clientNumStr.substring(0, clientNumStr.length()-1));
					}
					if(!"".equals(clientNameStr)){
						//删除客户信息
						OperationLog oper = new OperationLog();
						oper.setDoObject(new Long(MssConstants.OPER_TYPE_INSERT));
						
						//5：客户信息
						oper.setDoType(new Long(MssConstants.OPER_TYPE_INSERT));
						
						//2:删除
						oper.setDoType(new Long(MssConstants.OPER_TYPE_DELETE));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName(MssConstants.OPER_OBJECT_CLIENT);
						oper.setDescription("删除客户信息，ID：【"+clientIdStr+"】，姓名：【"+clientNameStr+"】");
						operLogBO.save(oper);
					}	
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_CLIENT_INFO_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/client/clientInfoController.do?method=queryClientInfoList" + "&ifSession=yes");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("删除客户信息失败！");
					resultInfos.add(new ResultInfo(ResultConstants.DEL_CLIENT_INFO_FAILED));
					ex.printStackTrace();
				}
		}});
			
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
	
}
