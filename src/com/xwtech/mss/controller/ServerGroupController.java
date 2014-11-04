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
import com.xwtech.mss.bo.business.ServerGroupBO;
import com.xwtech.mss.bo.business.ServerGroupMappingBO;
import com.xwtech.mss.bo.business.ServerInfoBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.ServerGroupForm;
import com.xwtech.mss.formBean.ServerInfoForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.ServerGroup;
import com.xwtech.mss.pub.po.ServerGroupMapping;
import com.xwtech.mss.pub.po.TransitServer;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.CommonOperation;

public class ServerGroupController extends MultiActionController {
	private static final Log log = LogFactory.getLog(ServerGroupController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private ServerGroupBO serverGroupBO;
	
	private ServerInfoBO serverInfoBO;
	
	private ServerGroupMappingBO serverGroupMappingBO;
	
	private OperLogBO operLogBO;
	
	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}


	public void setServerInfoBO(ServerInfoBO serverInfoBO) {
		this.serverInfoBO = serverInfoBO;
	}


	public void setServerGroupMappingBO(ServerGroupMappingBO serverGroupMappingBO) {
		this.serverGroupMappingBO = serverGroupMappingBO;
	}


	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	public void setServerGroupBO(ServerGroupBO serverGroupBO) {
		this.serverGroupBO = serverGroupBO;
	}


	/**
	 * 保存服务器分组信息和操作流水
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveServerGroup(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//服务器分组ID
		final String serverGroupId = request.getParameter("serverGroupId");
		
		//服务器分组名称
		final String serverGroupName = request.getParameter("serverGroupName");
		
		//服务器ID串,如：1，2，3，4
		final String serverIds = request.getParameter("hiddenServerIds");
		
		//备注
		final String note = request.getParameter("serverComment");
		
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
				ServerGroup serverGroup = null;
				if(serverGroupId!=null && !"".equals(serverGroupId)){
					serverGroup = serverGroupBO.findById(new Integer(serverGroupId));
				}else{
					serverGroup = new ServerGroup();
				}

				try{
					//保存服务器分组信息
					serverGroup.setServergroupname(serverGroupName);
					serverGroup.setNote(note);
					serverGroup.setStatus(MssConstants.STATE_A);

					if(viewOrEdit==null || MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)){
						serverGroup.setAddWho(new Integer(userId.toString()));
						serverGroup.setAddTime(DateUtils.convertString2Date(DateUtils.getChar14()));
					}else{
						serverGroup.setEditWho(new Integer(userId.toString()));
						serverGroup.setEditTime(DateUtils.convertString2Date(DateUtils.getChar14()));
					}
					serverGroupBO.saveOrUpdate(serverGroup);
					
					//保存服务器所属分组信息
					if(serverIds!=null&&serverIds.length()>1){
						//删除原有的服务器关系记录，然后再插入新选择的服务器与分组关系记录
//						if(viewOrEdit!=null&&MssConstants.VIEW_OR_EDIT_EDIT.equals(viewOrEdit)){
//						}
						int returnValue = serverGroupMappingBO.delMappingRecords(serverIds,serverGroup.getServergroupid().toString());
						if(returnValue>=0){
							serverGroupMappingBO.saveServerGroupLink(serverGroup.getServergroupid(),serverIds);
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
						oper.setDescription("新增服务器组：【"+serverGroup.getServergroupid()+"】");						
					}else{
						oper.setDoType(new Long(MssConstants.OPER_TYPE_UPDATE));
						oper.setDescription("修改服务器信息：【"+serverGroup.getServergroupid()+"】");	
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
				resultInfos.setGotoUrl("/mss/jsp/server/serverGroupController.do?method=queryServerGroupList&viewOrEdit="+viewOrEdit);
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
	public ModelAndView queryServerGroupList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap map = new HashMap();
		ServerGroupForm serverGroupForm = new ServerGroupForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		// 进行服务器选择查询时使用
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		String queryServerGroupName = request.getParameter("queryServerGroupName");
		try {
			if(queryServerGroupName!=null){
				queryServerGroupName = new String(request.getParameter("queryServerGroupName").getBytes("ISO8859-1"),"UTF-8").trim();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String queryNote = request.getParameter("queryNote");
		
		String queryStatus = request.getParameter("queryStatus");
		
		String currentPage = request.getParameter("currentPage");
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		Long roleId = sysUser.getRole().getRoleId();

		serverGroupForm.setQueryServerGroupName(queryServerGroupName);
		serverGroupForm.setQueryStatus(queryStatus);
		serverGroupForm.setQueryNote(queryNote);
		serverGroupForm.setViewOrEdit(viewOrEdit);

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}
			serverGroupForm.setCurrentPage(currentPage);
			
			SessionUtils.setObjectAttribute(request, "serverGroupFormSession", serverGroupForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			serverGroupForm = (ServerGroupForm) SessionUtils.getObjectAttribute(request, "serverGroupFormSession");
		}
		
		HashMap serverResult = null;
		serverResult = serverGroupBO.queryServerGroupList(serverGroupForm,String.valueOf(MssConstants.COUNT_FOR_EVERY_PAGE));

		map.put("serverGroupList", (List) serverResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, serverResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, serverResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, serverResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", serverGroupForm);
		map.put("accessType", accessType);

		return new ModelAndView("/mss/jsp/server/serverGroupList.jsp", RequestNameConstants.INFORMATION, map);
		
	}
	
	/**
	 * 根据服务器类别ID查询服务器类别详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
//	@SuppressWarnings("unchecked")
//	public ModelAndView queryServerInfoById(HttpServletRequest request, HttpServletResponse response)
//															throws ServletRequestBindingException {
//		HashMap map = new HashMap();
//		ServerInfoForm serverInfoForm = new ServerInfoForm();
//		String serverId = request.getParameter("serverId");
//		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
//		String currentPage = request.getParameter("currentPage");
//		String queryCountryId = request.getParameter("queryCountryId");
//		String queryProvinceId = request.getParameter("queryProvinceId");
//		String queryCityId = request.getParameter("queryCityId");
//		String queryServerType = request.getParameter("queryServerType");
//		String queryServerStatus = request.getParameter("queryServerStatus");
//		String quserServerGroup = request.getParameter("quserServerGroup");
//		String queryRegionId = request.getParameter("queryRegionId");
//		String queryStartTime = request.getParameter("queryStartTime");
//		String queryEndTime = request.getParameter("queryEndTime");
//		serverInfoForm.setQueryStatus(SpmsConstants.STATE_A);
//		
//		TransitServer serverGroup = null;
//		if(serverId!=null&&!serverId.equals("")){
//			serverGroup = serverInfoBO.findById(new Integer(serverId));
//			List serverGroupList = serverInfoBO.queryServerGroup(serverId);
//			ServerGroupMapping sgMapping = null;
//			if(serverGroupList!=null&&!serverGroupList.isEmpty()){
//				sgMapping = (ServerGroupMapping)serverGroupList.get(0);
//			}
//			serverInfoForm.setCurrentPage(currentPage);
//			serverInfoForm.setQueryCountryId(queryCountryId);
//			serverInfoForm.setQueryProvinceId(queryProvinceId);
//			serverInfoForm.setQueryCityId(queryCityId);
//			serverInfoForm.setQueryServerType(queryServerType);
//			serverInfoForm.setQueryServerStatus(queryServerStatus);
//			serverInfoForm.setQueryServerGroup(quserServerGroup);
//			serverInfoForm.setQueryRegionId(queryRegionId);
//			serverInfoForm.setQueryStartTime(queryStartTime);
//			serverInfoForm.setQueryEndTime(queryEndTime);
//			serverInfoForm.setViewOrEdit(viewOrEdit);
//
//			map.put("serverGroup", serverGroup);
//			map.put("menuStrmenu", sgMapping.getServergroupid().toString());
//			map.put("searchForm", serverInfoForm);
//		}
//		return new ModelAndView("/mss/jsp/server/serverInfoAdd.jsp?viewOrEdit=edit", RequestNameConstants.INFORMATION, map);
//	}
	
	/**
	 * 根据服务器组ID查询服务器分组详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryServerGroupById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		ServerGroupForm serverGroupForm = new ServerGroupForm();
		String serverGroupId = request.getParameter("serverGroupId");
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		String currentPage = request.getParameter("currentPage");
		String queryServerGroupName = request.getParameter("queryServerGroupName");
		String queryNote = request.getParameter("queryNote");
		String queryStatus = request.getParameter("queryStatus");
		
		ServerGroup serverGroup = null;
		List sgMappingList = null;
		List unGroupedServerList = null;
		
		
		if(serverGroupId!=null&&!"".equals(serverGroupId)){
			//查询所有不在该分组的服务器对象
//			List serverList = serverGroupMappingBO.queryServerTextByGroupId(null);
			unGroupedServerList = serverInfoBO.queryUnGroupedServer(serverGroupId,false);
			
			//查询服务器组对象
			serverGroup = serverGroupBO.findById(new Integer(serverGroupId));
			
			//查询该服务器组中的服务器对象
			if(serverGroup!=null){
				sgMappingList = serverInfoBO.queryUnGroupedServer(serverGroupId,true);
			}
		}
		//新增服务器组
		else if(viewOrEdit!=null&&MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)) {
			//查询所有服务器对象
			unGroupedServerList = serverInfoBO.queryUnGroupedServer("",false);
		}
		
		serverGroupForm.setQueryServerGroupName(queryServerGroupName);
		serverGroupForm.setQueryNote(queryNote);
		serverGroupForm.setQueryStatus(queryStatus);
		serverGroupForm.setViewOrEdit(viewOrEdit);
		serverGroupForm.setCurrentPage(currentPage);
		String sgMappingResult = "";
		
		if(sgMappingList!=null&&!sgMappingList.isEmpty()){
			Gson gson = new Gson();
			sgMappingResult= gson.toJson(sgMappingList);
		}
		
		map.put("serverGroup", serverGroup);
		map.put("serverList", unGroupedServerList);
		map.put("sgMappingResult", sgMappingResult);
//		map.put("viewOrEdit", viewOrEdit);
		map.put("searchForm", serverGroupForm);
		return new ModelAndView("/mss/jsp/server/serverGroupAdd.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 删除用户选择的记录（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delServerGroups(final HttpServletRequest request, HttpServletResponse response) throws Exception {
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
				
				String serverIdStr = "";
				
				//删除操作返回值
				int result = 0;
				try {
					// 获得页面表单信息
					String serverNumStr = request.getParameter("serverIdStr");
					if(serverNumStr!=null&&!serverNumStr.equals("")){
						// 根据权限ID删除相关权限信息
						serverGroupBO.delServerGroup(serverNumStr.substring(0, serverNumStr.length()-1));
					}
					
					//保存服务器删除记录
					if(!"".equals(serverIdStr)){
						OperationLog oper = new OperationLog();
						
						oper.setDoObject(new Long(4));
						
						//4：服务器信息
						oper.setObjType(new Long(4));
						
						//删除服务器信息
						oper.setDoType(new Long(3));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName("goodsInfo");
						oper.setDescription("删除服务器分组成功，ID【"+serverIdStr+"】");
						operLogBO.save(oper);
					}
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/server/serverGroupController.do?method=queryServerGroupList" + "&ifSession=yes");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("删除服务器分组失败！");
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_FAILED));
					resultInfos.setGotoUrl("/mss/jsp/server/serverGroupController.do?method=queryServerGroupList" + "&ifSession=yes");
					ex.printStackTrace();
				}
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
}
