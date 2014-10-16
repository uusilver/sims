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

import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.business.ClientInfoBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.ClientInfo;
import com.xwtech.mss.pub.po.GoodsInfo;
import com.xwtech.mss.pub.po.GoodsType;
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
	
	private OperLogBO operLogBO;


	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public void setClientInfoBO(ClientInfoBO clientInfoBO) {
		this.clientInfoBO = clientInfoBO;
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
		final String clientNum = request.getParameter("clientNum");
		
		//客户名称
		final String clientName = request.getParameter("clientName");
		
		//客户昵称
		final String clientNick = request.getParameter("clientNick");
		
		//收件地址
		final String clientAddr = request.getParameter("clientAddr");
		
		//客户联系电话
		final String clientTel = request.getParameter("clientTel");
		
		//客户邮编
		final String zipCode = request.getParameter("zipCode");
		
		//Email
		final String eMail = request.getParameter("eMail");
		
		//客户类型，1：购买人，2：询价人
		final String clientType = request.getParameter("clientType");
		
		//备注
		final String clientComment = request.getParameter("clientComment");

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 1.添加客户信息
				ClientInfo clientInfo = null;
				//新增客户信息
				OperationLog oper = new OperationLog();
				oper.setDoObject(new Long(5));
				//5：客户信息
				oper.setObjType(new Long(5));
				
				// 新增
				if (null == clientNum || "".equals(clientNum)) {
					clientInfo = new ClientInfo();
					clientInfo.setCreateTime(DateUtils.getChar12());
					oper.setDoType(new Long(1));
					oper.setDescription("新增客户信息：姓名：【"+clientName+"】 - 昵称：【"+clientNick+"】");
				}
				// 更新
				else { 
					clientInfo = clientInfoBO.findById(new Long(clientNum));
					clientInfo.setModifyTime(DateUtils.getChar12());
					oper.setDoType(new Long(2));
					oper.setDescription("修改客户信息，ID：【"+clientInfo.getClientNum()+"】，姓名：【"+clientInfo.getClientName()+"】 为 ：【"+clientName+"】 - 昵称：【"+clientInfo.getClientNick()+"】 为 【"+clientNick+"】");
				}

				try{
					clientInfo.setClientName(clientName);
					clientInfo.setClientNick(clientNick);
					clientInfo.setClientAddr(clientAddr);
					clientInfo.setClientTel(clientTel);
					clientInfo.setZipCode(zipCode);
					clientInfo.seteMail(eMail);
					clientInfo.setClientType(clientType);
					clientInfo.setClientState(SpmsConstants.STATE_A);
					clientInfo.setClientComm(clientComment);
			      	ChineseSpellingToPinYin py2 = new ChineseSpellingToPinYin();
					clientInfo.setFirstLetterName(py2.getFirstletterByName(clientName));
					clientInfo.setFirstLetterNick(py2.getFirstletterByName(clientNick));
					clientInfoBO.saveOrUpdate(clientInfo);
					resultInfos.add(new ResultInfo(ResultConstants.ADD_CLIENT_INFO_SUCCESS));
					
					//保存操作日志
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName("clientInfo");
					operLogBO.save(oper);
					
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_CLIENT_INFO_FAILED));
					status.setRollbackOnly();
				}
				resultInfos.setGotoUrl("/mss/jsp/business/clientInfoController.do?method=queryClientInfoList&addOrView='edit'");
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
	public ModelAndView queryClientInfoList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		ClientInfoForm clientInfoForm = new ClientInfoForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			clientInfoForm.setQueryClientState(SpmsConstants.STATE_A);
			clientInfoForm.setViewOrEdit(viewOrEdit);
			SessionUtils.setObjectAttribute(request, "clientInfoFormSession", clientInfoForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			clientInfoForm = (ClientInfoForm) SessionUtils.getObjectAttribute(request, "clientInfoFormSession");
		} else {
			String currentPage = request.getParameter("currentPage");
			String queryClientName = request.getParameter("queryClientName");
			String queryClientNick = request.getParameter("queryClientNick");
			String queryClientState = request.getParameter("queryClientState");
			String queryClientType = request.getParameter("queryClientType");

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}

			clientInfoForm.setCurrentPage(currentPage);
			clientInfoForm.setQueryClientName(queryClientName);
			clientInfoForm.setQueryClientNick(queryClientNick);
			clientInfoForm.setQueryClientState(queryClientState);
			clientInfoForm.setQueryClientType(queryClientType);
			clientInfoForm.setViewOrEdit(viewOrEdit);

			SessionUtils.setObjectAttribute(request, "clientInfoFormSession", clientInfoForm);
		}

		HashMap goodsTypeResult = clientInfoBO.queryClientInfoList(clientInfoForm,String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));

		map.put("clientInfoList", (List) goodsTypeResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, goodsTypeResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, goodsTypeResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, goodsTypeResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", clientInfoForm);
		map.put("accessType", ("menu".equals(accessType) ? "" : accessType));

		return new ModelAndView("/mss/jsp/business/clientInfoList.jsp", RequestNameConstants.INFORMATION, map);
		
	}
	
	/**
	 * 根据客户类别ID查询客户类别详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryClientInfoById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		String clientNum = request.getParameter("clientNum");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		ClientInfo clientInfo = null;
		String typeNameStr = "";
		String typeNumStr = "";
		if(clientNum!=null&&!clientNum.equals("")){
			clientInfo = clientInfoBO.findById(new Long(clientNum));
		}
			map.put("clientInfo", clientInfo);
		return new ModelAndView("/mss/jsp/business/clientInfoAdd.jsp?viewOrEdit="+viewOrEdit, RequestNameConstants.INFORMATION, map);
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
						clientNameList = clientInfoBO.queryGoodsName(clientNumStr.substring(0, clientNumStr.length()-1));
						// 根据权限ID删除相关权限信息
						clientInfoBO.delClientInfo(clientNumStr.substring(0, clientNumStr.length()-1));
					}
					
					if(clientNameList!=null&&clientNameList.size()>0){
						for (int i = 0; i < clientNameList.size(); i++) {
							clientNameStr += ((ClientInfo)clientNameList.get(i)).getClientName();
							clientIdStr += String.valueOf(((ClientInfo)clientNameList.get(i)).getClientNum().longValue());
							if(i< clientNameList.size()-1){
								clientNameStr += " | ";
								clientIdStr += " | ";
							}
						}
					}
					
					if(!"".equals(clientNameStr)){
						//删除客户信息
						OperationLog oper = new OperationLog();
						oper.setDoObject(new Long(5));
						
						//5：客户信息
						oper.setObjType(new Long(5));
						
						//2:删除
						oper.setDoType(new Long(3));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName("clientInfo");
						oper.setDescription("删除客户信息，ID：【"+clientIdStr+"】，姓名：【"+clientNameStr+"】");
						operLogBO.save(oper);
					}	
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_CLIENT_INFO_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/business/clientInfoController.do?method=queryClientInfoList" + "&ifSession=yes");
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
