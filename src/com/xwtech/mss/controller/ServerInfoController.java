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
import com.xwtech.mss.bo.business.ServerGroupMappingBO;
import com.xwtech.mss.bo.business.ServerInfoBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.ServerInfoForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.ServerGroupMapping;
import com.xwtech.mss.pub.po.TransitServer;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.CommonOperation;

public class ServerInfoController extends MultiActionController {
	private static final Log log = LogFactory.getLog(ServerInfoController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
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

	/**
	 * 保存服务器信息和流水记录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveServerInfo(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//服务器ID
		final String serverId = request.getParameter("serverId");
		
		//服务器IP
		final String serverIP = request.getParameter("serverIP");
		
		//所属国家
		final String countryID = request.getParameter("country_id");
		
		//所属省（州）
		final String provinceID = request.getParameter("province_id");
		
		//所属城市
		final String cityID = request.getParameter("city_id");
		
		//服务区域
		final String regionID = request.getParameter("serveRegion");
		
		//所属分组
		final String serverGroupID = request.getParameter("serveGroup");
		
		//所属类别,0 - 浏览下载 ；1 - 渗透攻击
		final String serverType = request.getParameter("serverType");
		
		//服务器状态
		final String serverStatus = request.getParameter("serverStatus");
		
		//失效日期，默认值设定为2020年12月31日
		final String invalidTime = request.getParameter("invalidTime");
		
		//约束：
		//0 - 无
		//1 - 不能作为最后一跳
		//2 - 不能作为第一跳
		//3 - 只能作为最后一跳
		//4 – 只能作为第一跳；
		final String limit = request.getParameter("limit");
		
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

				// 1.添加服务器信息
				//服务器信息
				TransitServer transitServer = null;
				if(serverId!=null && !"".equals(serverId)){
					transitServer = serverInfoBO.findById(new Integer(serverId));
				}else{
					transitServer = new TransitServer();
				}

				try{
					//保存服务器信息
					transitServer.setServerip(serverIP);
					transitServer.setCountryid(new Integer(countryID));
					transitServer.setProvinceid(new Integer(provinceID));
					transitServer.setCityid(new Integer(cityID));
					transitServer.setServertype(new Integer(serverType));
					transitServer.setServerstatus(new Integer(serverStatus));
					transitServer.setInvalidtime(DateUtils.convertString2Date(invalidTime));
					transitServer.setLimitation(new Integer(limit));
					transitServer.setRegionid(new Integer(regionID));
					transitServer.setNote(note);
					transitServer.setStatus(MssConstants.STATE_A);
					if(serverId==null || "".equals(serverId)){
						transitServer.setAddWho(new Integer(userId.toString()));
						transitServer.setAddTime(DateUtils.convertString2Date(DateUtils.getChar14()));			
					}else{
						transitServer.setEditWho(new Integer(userId.toString()));
						transitServer.setEditTime(DateUtils.convertString2Date(DateUtils.getChar14()));	
					}
					serverInfoBO.saveOrUpdate(transitServer);
					
					//保存服务器所属分组信息
					if(serverGroupID!=null&&!"".equals(serverGroupID)){
						ServerGroupMapping sgMapping = new ServerGroupMapping();
						sgMapping.setServergroupid(new Integer(serverGroupID));
						sgMapping.setServerid(transitServer.getServerid());
						serverGroupMappingBO.saveOrUpdate(sgMapping);
					}
					
					//用户操作日志
					//新增/编辑 服务器信息
					OperationLog oper = new OperationLog();
					oper.setDoObject(new Long(MssConstants.OPER_OBJECT_SERVER));
					
					//4：服务器信息
					oper.setObjType(new Long(MssConstants.OPER_OBJECT_SERVER));
					
					//1:新增
					if(viewOrEdit==null || MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)){
						oper.setDoType(new Long(MssConstants.OPER_TYPE_INSERT));
						oper.setDescription("新增服务器信息：【"+transitServer.getServerid()+" : "+serverIP+"】");						
					}else{
						oper.setDoType(new Long(MssConstants.OPER_TYPE_UPDATE));
						oper.setDescription("修改服务器信息：【"+transitServer.getServerid()+" : "+serverIP+"】");	
					}
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName(MssConstants.OPER_TABLE_SERVER);
					operLogBO.save(oper);
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_SUCCESS));
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_FAILED));
					status.setRollbackOnly();
					e.printStackTrace();
				}
				//resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
				resultInfos.setGotoUrl("/mss/jsp/server/serverInfoController.do?method=queryServerInfoList&viewOrEdit=edit");
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
	public ModelAndView queryServerInfoList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		ServerInfoForm serverInfoForm = new ServerInfoForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		// 进行服务器选择查询时使用
		String rowId = request.getParameter("trId");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		String queryCountryId = request.getParameter("queryCountryId");
		
		String queryProvinceId = request.getParameter("queryProvinceId");
		
		String queryCityId = request.getParameter("queryCityId");
		
		String currentPage = request.getParameter("currentPage");
		
		String queryServerType = request.getParameter("queryServerType");
		
		String queryServerStatus = request.getParameter("queryServerStatus");
		
		String queryRegionId = request.getParameter("queryRegionId");
		
		String queryServerGroup = request.getParameter("queryServerGroup");
		
		String queryStartTime = request.getParameter("queryStartTime");
		
		String queryEndTime = request.getParameter("queryEndTime");
		//是否显示查询条件框
		String showHeader = request.getParameter("showHeader");
		if(showHeader==null||"".equals(showHeader)){
			showHeader="yes";
		}
		
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		Long roleId = sysUser.getRole().getRoleId();

		serverInfoForm.setQueryCountryId((queryCountryId==null||"".equals(queryCountryId)?"-10":queryCountryId));
		serverInfoForm.setQueryProvinceId((queryProvinceId==null||"".equals(queryProvinceId)?"-20":queryProvinceId));
		serverInfoForm.setQueryCityId((queryCityId==null||"".equals(queryCityId)?"-30":queryCityId));
		serverInfoForm.setQueryServerType(queryServerType);
		serverInfoForm.setQueryServerStatus(queryServerStatus);
		serverInfoForm.setQueryRegionId(queryRegionId);
		serverInfoForm.setQueryServerGroup(queryServerGroup);
		serverInfoForm.setQueryStartTime(queryStartTime);
		serverInfoForm.setQueryEndTime(queryEndTime);
		serverInfoForm.setViewOrEdit(viewOrEdit);
		serverInfoForm.setShowHeader(showHeader);

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}
			serverInfoForm.setCurrentPage(currentPage);
			
			SessionUtils.setObjectAttribute(request, "serverInfoFormSession", serverInfoForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			serverInfoForm = (ServerInfoForm) SessionUtils.getObjectAttribute(request, "serverInfoFormSession");
		}
		
		HashMap serverResult = null;
		serverResult = serverInfoBO.queryServerInfoList(serverInfoForm,String.valueOf(MssConstants.COUNT_FOR_EVERY_PAGE));

		map.put("serverInfoList", (List) serverResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, serverResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, serverResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, serverResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", serverInfoForm);
		map.put("accessType", accessType);
		map.put("rowId", rowId);
		map.put("roleId", roleId.intValue());

		return new ModelAndView("/mss/jsp/server/serverInfoList.jsp", RequestNameConstants.INFORMATION, map);
		
	}
	
	/**
	 * 根据服务器类别ID查询服务器类别详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryServerInfoById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		ServerInfoForm serverInfoForm = new ServerInfoForm();
		String serverId = request.getParameter("serverId");
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		String currentPage = request.getParameter("currentPage");
		String queryCountryId = request.getParameter("queryCountryId");
		String queryProvinceId = request.getParameter("queryProvinceId");
		String queryCityId = request.getParameter("queryCityId");
		String queryServerType = request.getParameter("queryServerType");
		String queryServerStatus = request.getParameter("queryServerStatus");
		String queryServerGroup = request.getParameter("queryServerGroup");
		String queryRegionId = request.getParameter("queryRegionId");
		String queryStartTime = request.getParameter("queryStartTime");
		String queryEndTime = request.getParameter("queryEndTime");
		String showHeader = request.getParameter("showHeader");
		serverInfoForm.setQueryStatus(SpmsConstants.STATE_A);
		
		TransitServer transitServer = null;
		if(serverId!=null&&!serverId.equals("")){
			transitServer = serverInfoBO.findById(new Integer(serverId));
			List serverGroupList = serverInfoBO.queryServerGroup(serverId);
			ServerGroupMapping sgMapping = null;
			if(serverGroupList!=null&&!serverGroupList.isEmpty()){
				sgMapping = (ServerGroupMapping)serverGroupList.get(0);
			}
			serverInfoForm.setCurrentPage(currentPage);
			serverInfoForm.setQueryCountryId(queryCountryId);
			serverInfoForm.setQueryProvinceId(queryProvinceId);
			serverInfoForm.setQueryCityId(queryCityId);
			serverInfoForm.setQueryServerType(queryServerType);
			serverInfoForm.setQueryServerStatus(queryServerStatus);
			serverInfoForm.setQueryServerGroup(queryServerGroup);
			serverInfoForm.setQueryRegionId(queryRegionId);
			serverInfoForm.setQueryStartTime(queryStartTime);
			serverInfoForm.setQueryEndTime(queryEndTime);
			serverInfoForm.setViewOrEdit(viewOrEdit);
			serverInfoForm.setShowHeader(showHeader);

			map.put("transitServer", transitServer);
			map.put("serverGroupId", String.valueOf(sgMapping==null?"":sgMapping.getServergroupid()));
			map.put("searchForm", serverInfoForm);
		}
		return new ModelAndView("/mss/jsp/server/serverInfoAdd.jsp?viewOrEdit=edit", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 删除用户选择的记录（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delServerInfo(final HttpServletRequest request, HttpServletResponse response) throws Exception {
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
						serverInfoBO.delServerInfo(serverNumStr.substring(0, serverNumStr.length()-1));
					}
					
					//保存服务器删除记录
					if(!"".equals(serverIdStr)){
						OperationLog oper = new OperationLog();
						
						oper.setDoObject(new Long(MssConstants.OPER_OBJECT_SERVER));
						
						//4：服务器信息
						oper.setObjType(new Long(MssConstants.OPER_OBJECT_SERVER));
						
						//删除服务器信息
						oper.setDoType(new Long(MssConstants.OPER_TYPE_DELETE));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName(MssConstants.OPER_TABLE_SERVER);
						oper.setDescription("删除服务器信息成功，ID【"+serverIdStr+"】");
						operLogBO.save(oper);
					}
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/server/serverInfoController.do?method=queryServerInfoList" + "&ifSession=yes");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("删除服务器信息失败！");
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_FAILED));
					resultInfos.setGotoUrl("/mss/jsp/server/serverInfoController.do?method=queryServerInfoList" + "&ifSession=yes");
					ex.printStackTrace();
				}
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
}
