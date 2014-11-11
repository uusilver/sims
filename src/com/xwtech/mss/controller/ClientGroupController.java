package com.xwtech.mss.controller;

import java.io.UnsupportedEncodingException;
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
import com.xwtech.mss.bo.business.ClientGroupBO;
import com.xwtech.mss.bo.business.ClientGroupMappingBO;
import com.xwtech.mss.bo.business.ClientInfoBO;
import com.xwtech.mss.bo.business.ClientServerMappingBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.ClientGroupForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.Client;
import com.xwtech.mss.pub.po.ClientGroup;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.CommonOperation;

public class ClientGroupController extends MultiActionController {
	private static final Log log = LogFactory.getLog(ClientGroupController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private ClientGroupBO clientGroupBO;
	
	private ClientInfoBO clientInfoBO;
	
	private ClientGroupMappingBO clientGroupMappingBO;
	
	private ClientServerMappingBO clientServerMappingBO;
	
	private OperLogBO operLogBO;
	
	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}


	public void setClientInfoBO(ClientInfoBO clientInfoBO) {
		this.clientInfoBO = clientInfoBO;
	}


	public void setClientGroupMappingBO(ClientGroupMappingBO clientGroupMappingBO) {
		this.clientGroupMappingBO = clientGroupMappingBO;
	}
	
	public void setClientServerMappingBO(ClientServerMappingBO clientServerMappingBO) {
		this.clientServerMappingBO = clientServerMappingBO;
	}

	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	public void setClientGroupBO(ClientGroupBO clientGroupBO) {
		this.clientGroupBO = clientGroupBO;
	}


	/**
	 * 保存服务器分组信息和操作流水
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveClientGroup(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//服务器分组ID
		final String clientGroupId = request.getParameter("clientGroupId");
		
		//服务器分组名称
		final String clientGroupName = request.getParameter("clientGroupName");
		
		//客户端ID串,如：1，2，3，4
		final String clientIds = request.getParameter("hiddenClientIds");
		
		//服务器ID串,如：1，2，3，4
		final String serverIds = request.getParameter("hiddenServerIds");
		
		//备注
		final String note = request.getParameter("clientComment");
		
		//新增/编辑
		final String viewOrEdit = request.getParameter("viewOrEdit");

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final Long userId = sysUser.getUserId();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 1.添加服务器分组信息
				ClientGroup clientGroup = null;
				if(clientGroupId!=null && !"".equals(clientGroupId)){
					clientGroup = clientGroupBO.findById(new Integer(clientGroupId));
				}else{
					clientGroup = new ClientGroup();
				}

				try{
					//保存服务器分组信息
					clientGroup.setClientgroupname(clientGroupName);
					clientGroup.setNote(note);
					clientGroup.setStatus(MssConstants.STATE_A);

					if(viewOrEdit==null || MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)){
						clientGroup.setAddWho(new Integer(userId.toString()));
						clientGroup.setAddTime(DateUtils.convertString2Date(DateUtils.getChar14()));
					}else{
						clientGroup.setEditWho(new Integer(userId.toString()));
						clientGroup.setEditTime(DateUtils.convertString2Date(DateUtils.getChar14()));
					}
					clientGroupBO.saveOrUpdate(clientGroup);
					
					//保存服务器所属分组信息
					if(clientIds!=null&&clientIds.length()>0){
						//删除原有的服务器关系记录，然后再插入新选择的服务器与分组关系记录
//						if(viewOrEdit!=null&&MssConstants.VIEW_OR_EDIT_EDIT.equals(viewOrEdit)){
//						}
						int returnValue = clientGroupMappingBO.delMappingRecords(clientGroup.getClientgroupid().toString());
						if(returnValue>=0){
							clientGroupMappingBO.saveClientGroupLink(clientGroup.getClientgroupid(),clientIds);
						}
					}
					
					//保存客户端可以访问的服务器记录
					if(serverIds!=null&&!"".equals(serverIds)){
						//先删除客户端与服务器的关系记录
						int result = clientServerMappingBO.delMappingRecords(serverIds, clientIds);
						if(result>=0){
							clientServerMappingBO.saveClientServerLink(clientIds, serverIds);
						}
					}
					
					//用户操作日志
					//新增/编辑 服务器分组信息
					OperationLog oper = new OperationLog();
					oper.setDoObject(new Long(MssConstants.OPER_OBJECT_SERVER_GROUP));
					
					//4：服务器信息
					oper.setObjType(new Long(MssConstants.OPER_OBJECT_SERVER_GROUP));
					
					//1:新增
					if(viewOrEdit==null || MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)){
						oper.setDoType(new Long(MssConstants.OPER_TYPE_INSERT));
						oper.setDescription("新增服务器组：【"+clientGroup.getClientgroupid()+"】");						
					}else{
						oper.setDoType(new Long(MssConstants.OPER_TYPE_UPDATE));
						oper.setDescription("修改服务器信息：【"+clientGroup.getClientgroupid()+"】");	
					}
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName(MssConstants.OPER_TABLE_SERVER_GROUP);
					operLogBO.save(oper);
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_SUCCESS));
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_FAILED));
					status.setRollbackOnly();
					e.printStackTrace();
				}
				resultInfos.setGotoUrl("/mss/jsp/client/clientGroupController.do?method=queryClientGroupList&viewOrEdit="+viewOrEdit);
				resultInfos.setIsAlert(true);
				resultInfos.setIsRedirect(true);
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
	}
	
	
	/**
	 * 查询服务器信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryClientGroupList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap map = new HashMap();
		ClientGroupForm clientGroupForm = new ClientGroupForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		// 进行服务器选择查询时使用
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "edit" : request.getParameter("viewOrEdit").trim();
		
		String queryClientGroupName = request.getParameter("queryClientGroupName");
		
		String queryNote = request.getParameter("queryNote");
		try {
			if(queryClientGroupName!=null){
				queryClientGroupName = new String(request.getParameter("queryClientGroupName").getBytes("ISO8859-1"),"UTF-8").trim();
			}
			if(queryNote!=null){
				queryNote = new String(request.getParameter("queryNote").getBytes("ISO8859-1"),"UTF-8").trim();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String queryStatus = request.getParameter("queryStatus");
		
		String currentPage = request.getParameter("currentPage");
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		Long roleId = sysUser.getRole().getRoleId();

		clientGroupForm.setQueryClientGroupName(queryClientGroupName);
		clientGroupForm.setQueryStatus(queryStatus);
		clientGroupForm.setQueryNote(queryNote);
		clientGroupForm.setViewOrEdit(viewOrEdit);

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}
			clientGroupForm.setCurrentPage(currentPage);
			
			SessionUtils.setObjectAttribute(request, "clientGroupFormSession", clientGroupForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			clientGroupForm = (ClientGroupForm) SessionUtils.getObjectAttribute(request, "clientGroupFormSession");
		}
		
		HashMap clientResult = null;
		clientResult = clientGroupBO.queryClientGroupList(clientGroupForm,String.valueOf(MssConstants.COUNT_FOR_EVERY_PAGE));

		map.put("clientGroupList", (List) clientResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, clientResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, clientResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, clientResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", clientGroupForm);
		map.put("accessType", accessType);

		return new ModelAndView("/mss/jsp/client/clientGroupList.jsp", RequestNameConstants.INFORMATION, map);
		
	}
	
	/**
	 * 根据服务器组ID查询服务器分组详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView queryClientGroupById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		ClientGroupForm clientGroupForm = new ClientGroupForm();
		String clientGroupId = request.getParameter("clientGroupId");
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		String currentPage = request.getParameter("currentPage");
		String queryClientGroupName = request.getParameter("queryClientGroupName");
		String queryNote = request.getParameter("queryNote");
		String queryStatus = request.getParameter("queryStatus");
		
		ClientGroup clientGroup = null;
//		Client clientInfo = null;
		List cgMappingList = null;
		List unGroupedClientList = null;
		
		List csMappingList = null;
		List unAccessedServerList = null;
		String csMappingResult = "";
		
		//加载未分组的客户端和该组中的客户端列表
		if(clientGroupId!=null&&!"".equals(clientGroupId)){
			//查询所有不在该分组的服务器对象
			unGroupedClientList = clientInfoBO.queryUnGroupedClient(clientGroupId,false);
			
			//查询服务器组对象
			clientGroup = clientGroupBO.findById(new Integer(clientGroupId));
			
			//查询该服务器组中的服务器对象
			if(clientGroup!=null){
				cgMappingList = clientInfoBO.queryUnGroupedClient(clientGroupId,true);
			}
		}
		//新增服务器组
		else if(viewOrEdit!=null&&MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)) {
			//查询所有服务器对象
			unGroupedClientList = clientInfoBO.queryUnGroupedClient("",false);
		}
		
		

		//加载不可访问的服务器和该组可以访问的服务器列表
		if(clientGroup!=null){
			
			List clientList = clientGroupBO.queryClientsByGroupId(clientGroup.getClientgroupid().toString());
			String clientIds = "";
			if(clientList!=null&&!clientList.isEmpty()){
				Client client = null;
				for(int i=0;i<clientList.size();i++){
					client = (Client)clientList.get(i);
					clientIds += client.getClientid().toString()+",";
				}
				clientIds = clientIds.substring(0,clientIds.lastIndexOf(","));
			}
			
			//查询所有该客户端不能访问的服务器对象
//			List serverList = serverGroupMappingBO.queryServerTextByGroupId(null);
			unAccessedServerList = clientInfoBO.queryUnAccessedServer(clientIds,false);

			//查询该客户端可以访问的服务器对象
			csMappingList = clientInfoBO.queryUnAccessedServer(clientIds,true);
			
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
		
		clientGroupForm.setQueryClientGroupName(queryClientGroupName);
		clientGroupForm.setQueryNote(queryNote);
		clientGroupForm.setQueryStatus(queryStatus);
		clientGroupForm.setViewOrEdit(viewOrEdit);
		clientGroupForm.setCurrentPage(currentPage);
		String cgMappingResult = "";
		
		if(cgMappingList!=null&&!cgMappingList.isEmpty()){
			Gson gson = new Gson();
			cgMappingResult= gson.toJson(cgMappingList);
		}
		
		map.put("clientGroup", clientGroup);
		map.put("clientList", unGroupedClientList);
		map.put("cgMappingResult", cgMappingResult);
		map.put("searchForm", clientGroupForm);
		map.put("serverList", unAccessedServerList);
		map.put("csMappingResult", csMappingResult);
		
		return new ModelAndView("/mss/jsp/client/clientGroupAdd.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 删除用户选择的记录（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delClientGroups(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();

		
		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				String clientIdStr = "";
				
				//删除操作返回值
				int result = 0;
				try {
					// 获得页面表单信息
					String clientGroupIdStr = request.getParameter("clientIdStr");
					if(clientGroupIdStr!=null&&!clientGroupIdStr.equals("")){
						clientGroupIdStr = clientGroupIdStr.substring(0, clientGroupIdStr.length()-1);
						//根据客户端ID和GroupID删除映射记录
						result = clientGroupMappingBO.delMappingRecords(clientGroupIdStr);
						// 根据权限ID删除相关权限信息
						if(result>=0){
							clientGroupBO.delClientGroup(clientGroupIdStr);
						}
					}
					
					//保存服务器删除记录
					if(!"".equals(clientIdStr)){
						OperationLog oper = new OperationLog();
						
						oper.setDoObject(new Long(4));
						
						//4：服务器信息
						oper.setObjType(new Long(4));
						
						//删除服务器信息
						oper.setDoType(new Long(3));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName("goodsInfo");
						oper.setDescription("删除服务器分组成功，ID【"+clientIdStr+"】");
						operLogBO.save(oper);
					}
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/client/clientGroupController.do?method=queryClientGroupList" + "&ifSession=yes");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("删除服务器分组失败！");
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_FAILED));
					resultInfos.setGotoUrl("/mss/jsp/client/clientGroupController.do?method=queryClientGroupList" + "&ifSession=yes");
					ex.printStackTrace();
				}
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
}
