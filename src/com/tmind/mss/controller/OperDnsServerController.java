package com.tmind.mss.controller;

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

import com.tmind.framework.pub.result.ResultInfo;
import com.tmind.framework.pub.result.ResultInfos;
import com.tmind.framework.pub.utils.DateUtils;
import com.tmind.framework.pub.utils.SessionUtils;
import com.tmind.framework.pub.web.RequestNameConstants;
import com.tmind.mss.bo.business.DnsServerBO;
import com.tmind.mss.bo.system.operator.OperLogBO;
import com.tmind.mss.formBean.ServerInfoForm;
import com.tmind.mss.pub.constants.MssConstants;
import com.tmind.mss.pub.constants.SpmsConstants;
import com.tmind.mss.pub.po.OperDNSServer;
import com.tmind.mss.pub.po.OperationLog;
import com.tmind.mss.pub.po.ServerGroupMapping;
import com.tmind.mss.pub.po.TransitServer;
import com.tmind.mss.pub.po.UserInfo;
import com.tmind.mss.pub.result.ResultConstants;
import com.tmind.mss.pub.tools.CommonOperation;

public class OperDnsServerController extends MultiActionController {
	private static final Log log = LogFactory.getLog(OperDnsServerController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private DnsServerBO dnsServerBO;
	
	private OperLogBO operLogBO;
	
	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public void setDnsServerBO(DnsServerBO dnsServerBO) {
		this.dnsServerBO = dnsServerBO;
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
		
		//服务器端口
		final String serverPort = request.getParameter("serverPort");
		
		//所属类别,1 - DNS服务器 ；2 - 运维服务器
		final String serverType = request.getParameter("serverType");
		
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
				OperDNSServer operDNSServer = null;
				if(serverId!=null && !"".equals(serverId)){
					operDNSServer = dnsServerBO.findById(new Integer(serverId));
				}else{
					operDNSServer = new OperDNSServer();
				}

				try{
					//保存服务器信息
					operDNSServer.setServerip(serverIP);
					operDNSServer.setServerport(serverPort==null?null:new Integer(serverPort));
					operDNSServer.setServertype(new Integer(serverType));
					operDNSServer.setNote(note);
					operDNSServer.setStatus(MssConstants.STATE_A);
					if(serverId==null || "".equals(serverId)){
						operDNSServer.setAddWho(new Integer(userId.toString()));
						operDNSServer.setAddTime(DateUtils.convertString2Date(DateUtils.getChar14()));			
					}else{
						operDNSServer.setEditWho(new Integer(userId.toString()));
						operDNSServer.setEditTime(DateUtils.convertString2Date(DateUtils.getChar14()));	
					}
					dnsServerBO.saveOrUpdate(operDNSServer);
					
					//用户操作日志
					//新增/编辑 服务器信息
					OperationLog oper = new OperationLog();
					oper.setDoObject(new Long(MssConstants.OPER_OBJECT_DNS_SERVER));
					
					//4：服务器信息
					oper.setObjType(new Long(MssConstants.OPER_OBJECT_DNS_SERVER));
					
					//1:新增
					if(viewOrEdit==null || MssConstants.VIEW_OR_EDIT_ADD.equals(viewOrEdit)){
						oper.setDoType(new Long(MssConstants.OPER_TYPE_INSERT));
						oper.setDescription("新增DNS服务器信息：【"+operDNSServer.getDnsserverid()+" : "+serverIP+"】");						
					}else{
						oper.setDoType(new Long(MssConstants.OPER_TYPE_UPDATE));
						oper.setDescription("修改DNS服务器信息：【"+operDNSServer.getDnsserverid()+" : "+serverIP+"】");	
					}
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName(MssConstants.OPER_TABLE_DNS_SERVER);
					operLogBO.save(oper);
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_SUCCESS));
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_FAILED));
					status.setRollbackOnly();
					e.printStackTrace();
				}
				resultInfos.setGotoUrl("/mss/jsp/server/operDnsServerController.do?method=queryServerInfoList&viewOrEdit=edit");
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView queryServerInfoList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		ServerInfoForm serverInfoForm = new ServerInfoForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "edit" : request.getParameter("viewOrEdit").trim();
		
		String currentPage = request.getParameter("currentPage");
		
		String queryServerType = request.getParameter("queryServerType");
		
		String queryStatus = request.getParameter("queryStatus");
		
		//是否显示查询条件框
		String showHeader = request.getParameter("showHeader");
		if(showHeader==null||"".equals(showHeader)){
			showHeader="yes";
		}
		
		String indexNO = request.getParameter("indexNO");
		
		
		
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		Long roleId = sysUser.getRole().getRoleId();

		serverInfoForm.setQueryServerType(queryServerType);
		serverInfoForm.setQueryStatus((queryStatus==null||"".equals(queryStatus))?"A":queryStatus);
		serverInfoForm.setViewOrEdit(viewOrEdit);
		serverInfoForm.setShowHeader(showHeader);
		serverInfoForm.setIndexNO(indexNO);

		
		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			
			SessionUtils.setObjectAttribute(request, "serverInfoFormSession", serverInfoForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			serverInfoForm = (ServerInfoForm) SessionUtils.getObjectAttribute(request, "serverInfoFormSession");
		}

		if (null == currentPage || "".equals(currentPage)) {
			currentPage = "1";
		}
		serverInfoForm.setCurrentPage(currentPage);
		
		HashMap serverResult = null;
		serverResult = dnsServerBO.queryDnsServerList(serverInfoForm,String.valueOf(MssConstants.COUNT_FOR_EVERY_PAGE));

		map.put("serverInfoList", (List) serverResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, serverResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, serverResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, serverResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", serverInfoForm);
		map.put("accessType", accessType);
		map.put("roleId", roleId.intValue());

		return new ModelAndView("/mss/jsp/server/dnsServerList.jsp", RequestNameConstants.INFORMATION, map);
		
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
		String queryServerType = request.getParameter("queryServerType");
		String showHeader = request.getParameter("showHeader");
		serverInfoForm.setQueryStatus(SpmsConstants.STATE_A);
		
		OperDNSServer operDNSServer = null;
		if(serverId!=null&&!serverId.equals("")){
			operDNSServer = dnsServerBO.findById(new Integer(serverId));
			
			serverInfoForm.setCurrentPage(currentPage);;
			serverInfoForm.setQueryServerType(queryServerType);;
			serverInfoForm.setViewOrEdit(viewOrEdit);
			serverInfoForm.setShowHeader(showHeader);

			map.put("operDNSServer", operDNSServer);
			map.put("searchForm", serverInfoForm);
		}
		return new ModelAndView("/mss/jsp/server/dnsServerAdd.jsp?viewOrEdit=edit", RequestNameConstants.INFORMATION, map);
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
		final ServerInfoForm serverInfoForm = new ServerInfoForm();

		
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
						dnsServerBO.delServerInfo(serverNumStr.substring(0, serverNumStr.length()-1));
					}
					
					serverInfoForm.setQueryStatus(MssConstants.STATE_A);
					
					//保存服务器删除记录
					if(!"".equals(serverIdStr)){
						OperationLog oper = new OperationLog();
						
						oper.setDoObject(new Long(MssConstants.OPER_OBJECT_DNS_SERVER));
						
						//4：服务器信息
						oper.setObjType(new Long(MssConstants.OPER_OBJECT_DNS_SERVER));
						
						//删除服务器信息
						oper.setDoType(new Long(MssConstants.OPER_TYPE_DELETE));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName(MssConstants.OPER_TABLE_DNS_SERVER);
						oper.setDescription("删除DNS服务器信息成功，ID【"+serverIdStr+"】");
						operLogBO.save(oper);
					}
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/server/operDnsServerController.do?method=queryServerInfoList" + "&ifSession=yes");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("删除DNS服务器信息失败！");
					resultInfos.add(new ResultInfo(ResultConstants.DEL_SERVER_INFO_FAILED));
					resultInfos.setGotoUrl("/mss/jsp/server/operDnsServerController.do?method=queryServerInfoList" + "&ifSession=yes");
					ex.printStackTrace();
				}
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
}
