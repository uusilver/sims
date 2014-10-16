package com.xwtech.mss.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.business.AuthorizationBO;
import com.xwtech.mss.bo.system.menu.MenuBO;
import com.xwtech.mss.bo.system.orgnization.OrgnizationBO;
import com.xwtech.mss.bo.system.property.RoleBO;
import com.xwtech.mss.bo.system.property.UserPropertyBO;
import com.xwtech.mss.bo.system.user.UserManageBO;
import com.xwtech.mss.formBean.UserManageSearchForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.MpmsAuthorization;
import com.xwtech.mss.pub.po.Role;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.po.UserProperty;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.CommonOperation;
import com.xwtech.mss.pub.web.SysOperLog;

public class UserManageController extends MultiActionController {

	private static final Log log = LogFactory.getLog(UserManageController.class);

	private UserManageBO userManageBO;

	private OrgnizationBO orgnizationBO;

	private MenuBO menuBO;

	private RoleBO roleBO;

	private UserPropertyBO userPropertyBO;
	
	private AuthorizationBO authorizationBO;

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;

	public UserManageBO getUserManageBO() {
		return userManageBO;
	}

	public void setUserManageBO(UserManageBO userManageBO) {
		this.userManageBO = userManageBO;
	}

	public void setOrgnizationBO(OrgnizationBO orgnizationBO) {
		this.orgnizationBO = orgnizationBO;
	}

	public TransactionTemplate getTransTemplate() {
		return transTemplate;
	}

	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public MenuBO getMenuBO() {
		return menuBO;
	}

	public void setMenuBO(MenuBO menuBO) {
		this.menuBO = menuBO;
	}

	public RoleBO getRoleBO() {
		return roleBO;
	}

	public void setRoleBO(RoleBO roleBO) {
		this.roleBO = roleBO;
	}

	public UserPropertyBO getUserPropertyBO() {
		return userPropertyBO;
	}

	public void setUserPropertyBO(UserPropertyBO userPropertyBO) {
		this.userPropertyBO = userPropertyBO;
	}

	public AuthorizationBO getAuthorizationBO() {
		return authorizationBO;
	}

	public void setAuthorizationBO(AuthorizationBO authorizationBO) {
		this.authorizationBO = authorizationBO;
	}

	/*
	 * 查询用户列表
	 */
	public ModelAndView queryUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		// 获取并传递查询条件
		UserManageSearchForm searchForm = strucQueryParamForm(request);
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");

		// 菜单首次访问，默认查询状态有效的信息
		if (accessType != null && accessType.equals("menu")) {
			searchForm.setUserState(SpmsConstants.STATE_A);
		}

		map.put("searchForm", searchForm);

		HashMap userListResult = userManageBO.queryUserList(searchForm);

		List list = (List) userListResult.get(RequestNameConstants.RESULT_LIST);

		if (list != null && list.size() > 0 && (searchForm.getIndexNO() == null || searchForm.getIndexNO().equals(""))) {
			for (int i = 0; i < list.size(); i++) {
				UserInfo userInfo = (UserInfo) list.get(i);
				if (/*
					 * (userInfo.getMssAuditingContractsForApplyUser()!=null &&
					 * userInfo.getMssAuditingContractsForApplyUser().size()>0)
					 * ||(userInfo.getCiUserInfos()!=null &&
					 * userInfo.getCiUserInfos().size()>0) //
					 * ||(userInfo.getMssExpenseAccounts()!=null &&
					 * userInfo.getMssExpenseAccounts().size()>0) //
					 * ||(userInfo.getMssOrdersInfos()!=null &&
					 * userInfo.getMssOrdersInfos().size()>0) //
					 * ||(userInfo.getMssAuditingContractsForLinkMan()!=null &&
					 * userInfo.getMssAuditingContractsForLinkMan().size()>0) //
					 * ||(userInfo.getMssReqInfos()!=null &&
					 * userInfo.getMssReqInfos().size()>0) //
					 * ||(userInfo.getMssReqMaterials()!=null &&
					 * userInfo.getMssReqMaterials().size()>0) ||
					 */userInfo.getUserId().longValue() == 1
						|| userInfo == new CommonOperation().getLoginUserInfo(request).getSysUser()) {
					userInfo.setDelFlag("N");
				} else {
					userInfo.setDelFlag("Y");
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			UserInfo user = (UserInfo) list.get(i);
			String roleName = "";
			Set userPropertySet = user.getRole().getUserProperties();
			if (userPropertySet != null && userPropertySet.size() > 0) {
				Iterator it = userPropertySet.iterator();
				while (it.hasNext()) {
					UserProperty userProperty = (UserProperty) it.next();
					roleName = userProperty.getRole().getRoleName();
				}
			}
			// user.setRoleName(roleName);
		}

		map.put("loginUser", new CommonOperation().getLoginUserInfo(request).getSysUser().getUserId());

		map.put("userList", (List) userListResult.get(RequestNameConstants.RESULT_LIST));

		map.put("currentPage", String.valueOf(userListResult.get(RequestNameConstants.CURRENT_PAGE)));

		map.put("totalCount", String.valueOf(userListResult.get(RequestNameConstants.TOTAL_COUNT)));

		map.put("totalPage", String.valueOf(userListResult.get(RequestNameConstants.TOTAL_PAGE)));

		return new ModelAndView("/mss/jsp/sysManage/userList.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 查看指定用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView viewUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		// 获取并传递查询条件
		UserManageSearchForm searchForm = strucQueryParamForm(request);

		map.put("searchForm", searchForm);

		String userId = request.getParameter("userId");

		UserInfo userInfo = userManageBO.getUserById(new Long(userId));

		// 查看用户等于当前用户或是查看管理员信息时，设置为不可以编辑

		// 要查看的用户的userId
		long queryUserId = userInfo.getUserId().longValue();
		// 当前登陆用户的userId
		long currUserId = new CommonOperation().getLoginUserInfo(request).getSysUser().getUserId().longValue();

		if (currUserId != 1 && (queryUserId == currUserId || queryUserId == 1)) {
			map.put("avialableFlag", "false");
		} else {
			map.put("avialableFlag", "true");
		}

		Role role = new Role();

		// 组织用户所拥有的菜单
		List menuList = menuBO.querySubMenu();

		List selctMenuList = new ArrayList();

		Set userPropertySet = userInfo.getRole().getUserProperties();
		Iterator it = userPropertySet.iterator();
		while (it.hasNext()) {
			UserProperty userProperty = (UserProperty) it.next();
			role = userProperty.getRole();
			if (userProperty.getMenu() != null && userProperty.getMenu().getMenuLevel().longValue() == 2) {
				selctMenuList.add(userProperty.getMenu());
			}
		}

		// List ownerMenuList = menuBO.querySysMenu(role.getRoleId().toString(),
		// null);

		map.put("userRole", role.getRoleId());

		map.put("menuList", menuList);
		// map.put("ownerMenuList", ownerMenuList);

		map.put("selctMenuList", selctMenuList);

		map.put("userInfo", userInfo);

		return new ModelAndView("/mss/jsp/sysManage/userInfo.jsp", RequestNameConstants.INFORMATION, map);
	}

	/*
	 * 保存用户信息
	 */
	public ModelAndView saveUser(final HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);

		// 获取并传递查询条件
		final String filter = strucQueryParams(request);

		// 用户ID
		final String userId = request.getParameter("userId");
		// 部门id
		final String orgId = request.getParameter("orgId");
		// 工号
		final String userNum = request.getParameter("userNum") != null ? request.getParameter("userNum").trim() : null;
		// 姓名
		final String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim()
				: null;
		// 登录名
		final String loginName = request.getParameter("loginName") != null ? request.getParameter("loginName").trim()
				: null;
		// 密码
		final String loginPwd = request.getParameter("loginPwd") != null ? request.getParameter("loginPwd").trim()
				: null;
		// 员工类别
		final String userType = request.getParameter("userType");
		// 传真
		final String fax = request.getParameter("fax") != null ? request.getParameter("fax").trim() : null;
		// 员工状态
		final String userState = request.getParameter("userState");
		// 可访问菜单
		final String[] menus = request.getParameterValues("userMenu");
		// 用户权限
		// final String[] roles = request.getParameterValues("userRole");
		final String roleId = request.getParameter("userRole");
		// 员工Tel
		final String userTel = request.getParameter("userTel") != null ? request.getParameter("userTel").trim() : null;
		// 员工状态
		// final String contrImportFlag =
		// request.getParameter("contrImportFlag");
		// 员工状态
		// final String payImportFlag = request.getParameter("payImportFlag");

		try {
			transTemplate.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {

					UserInfo userInfo = null;
					if (userId != null && !userId.equals("")) {
						userInfo = userManageBO.getUserById(new Long(userId));
					}
					// 新增用户时设置密码123456
					else {
						userInfo = new UserInfo();
						userInfo.setLoginPwd(loginPwd == null || loginPwd.equals("") ? "123456" : loginPwd);
					}
					if (orgId != null && !orgId.equals("")) {
						userInfo.setOrgnization(orgnizationBO.findById(new Long(orgId)));
					}
					userInfo.setUserNum(userNum == null || userNum.equals("") ? null : new Long(userNum));
					userInfo.setUserName(userName);
					userInfo.setLoginName(loginName);
					userInfo.setUserType(userType != null && !userType.equals("") ? new Long(userType) : null);
					userInfo.setFax(fax);
					userInfo.setTel(userTel);
					userInfo.setUserState(userState);

					if (roleId != null && !roleId.equals("")) {
						Role role = roleBO.findRoleById(new Long(roleId));
						userInfo.setRole(role);
					}
					userInfo.setUserState(userState == null || userState.equals("") ? "A" : userState);
					// userInfo.setContrImportFlag(contrImportFlag);
					// userInfo.setPayImportFlag(payImportFlag);
					userManageBO.saveUserInfo(userInfo);

					// 修改时先删除原有权限关系
					// if(userId!=null && !userId.equals("")){
					// userPropertyBO.delUserPropertyByUserIds(userId);
					// }
					// if(roles.length>0){
					// for(int i=0;i<roles.length;i++){
					// if(roles[i]!=null && !roles[i].equals("")){
					// Role role = roleBO.findRoleById(new Long(roles[i]));
					// role.getUserProperties();
					// List menuList = new ArrayList();
					// if(menus!=null && menus.length>0){
					// //组织菜单ID
					// for(int j=0;j<menus.length;j++){
					// if(menus[j]!=null && !menus[j].equals("")){
					// Menu menu = menuBO.getMenuById(new Long(menus[j]));
					// if(!menuList.contains(menu.getMenu())){
					// menuList.add(menu.getMenu());
					// }
					// menuList.add(menu);
					// }
					// }
					//            						
					// for(int j=0;j<menuList.size();j++){
					// if(menuList.get(j)!=null &&
					// !menuList.get(j).toString().equals("")){
					// Menu menu = (Menu)menuList.get(j);
					// UserProperty userProperty = new UserProperty();
					// // userProperty.setUserInfo(userInfo);
					// userProperty.setRole(role);
					// userProperty.setMenu(menu);
					// userProperty.setState("A");
					// userPropertyBO.saveUserProperty(userProperty);
					// }
					// }
					// }else{
					// UserProperty userProperty = new UserProperty();
					// // userProperty.setUserInfo(userInfo);
					// userProperty.setRole(role);
					// userProperty.setState("A");
					// userPropertyBO.saveUserProperty(userProperty);
					// }
					// }
					// }
					// }

					if (userId != null && !userId.equals("")) {
						resultInfos.add(new ResultInfo(ResultConstants.USER_MOD_SUCCESS));
						new SysOperLog().saveOperLog(request, new Long(2), "mss_user_info", userInfo.getUserId(),
								new Long(9), "修改用户信息");
					} else {
						resultInfos.add(new ResultInfo(ResultConstants.USER_ADD_SUCCESS));
						new SysOperLog().saveOperLog(request, new Long(1), "mss_user_info", userInfo.getUserId(),
								new Long(9), "新增用户信息");
					}

					resultInfos.setGotoUrl("/mss/jsp/sysManage/userManageController.do?method=queryUserList" + filter);
				}
			});

			resultInfos.setIsAlert(true);
			resultInfos.setIsRedirect(true);
		} catch (Exception ex) {
			resultInfos.setGotoUrl(null);
			log.error("用户信息保存失败！");
			if (userId != null && !userId.equals("")) {
				resultInfos.add(new ResultInfo(ResultConstants.USER_MOD_FAILED));
			} else {
				resultInfos.add(new ResultInfo(ResultConstants.USER_ADD_FAILED));
			}
			ex.printStackTrace();
		}
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 删除用户信息
	 */
	public ModelAndView delUserInfo(final HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);

		// 获取并传递查询条件
		final String filter = strucQueryParams(request);

		// 用户Id
		final String userIdStr = request.getParameter("userIdStr");

		try {
			// transTemplate.execute(new TransactionCallbackWithoutResult() {
			// protected void doInTransactionWithoutResult(TransactionStatus
			// status) {

			boolean delFlag = true;

			// if(userIdStr.length()>0){
			// String[] userId = userIdStr.split(",");;
			// for(int i=0;i<userId.length; i++){
			// UserInfo userInfo = userManageBO.getUserById(new
			// Long(userId[i]));
			// if((userInfo.getAuditingContractsForApplyUser()!=null &&
			// userInfo.getAuditingContractsForApplyUser().size()>0)
			// || (userInfo.getAuditingContractsForLinkMan()!=null &&
			// userInfo.getAuditingContractsForLinkMan().size()>0)
			// || (userInfo.getExpenseAccounts()!=null &&
			// userInfo.getExpenseAccounts().size()>0)
			// || (userInfo.getOrdersInfos()!=null &&
			// userInfo.getOrdersInfos().size()>0)
			// || (userInfo.getReqInfos()!=null &&
			// userInfo.getReqInfos().size()>0)){
			// delFlag = false;
			// break;
			// }
			// }
			// }
			if (delFlag) {
				// userPropertyBO.delUserPropertyByUserIds(userIdStr);
				userManageBO.delUsers(userIdStr);
				resultInfos.add(new ResultInfo(ResultConstants.USER_DEL_SUCCESS));
				if (userIdStr.length() > 0) {
					String[] userId = userIdStr.split(",");
					;
					for (int i = 0; i < userId.length; i++) {
						new SysOperLog().saveOperLog(request, new Long(3), "mss_user_info", new Long(userId[i]),
								new Long(9), "删除用户信息");
					}
				}
			} else {
				resultInfos.add(new ResultInfo(ResultConstants.USER_DEL_HASREF));
			}

			resultInfos.setGotoUrl("/mss/jsp/sysManage/userManageController.do?method=queryUserList" + filter);
			// }
			// });

			resultInfos.setIsAlert(true);
			resultInfos.setIsRedirect(true);
		} catch (Exception ex) {
			resultInfos.setGotoUrl(null);
			log.error("用户信息删除失败！");
			resultInfos.add(new ResultInfo(ResultConstants.USER_DEL_FAILED));
			ex.printStackTrace();
		}
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView modPassword(final HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);

		// 用户Id
		final UserInfo userInfo = userManageBO.getUserById(new CommonOperation().getLoginUserInfo(request).getSysUser()
				.getUserId());

		// 密码
		final String loginPwd = request.getParameter("newPassword");

		try {
			transTemplate.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {

					userInfo.setLoginPwd(loginPwd);
					userManageBO.saveUserInfo(userInfo);

					new SysOperLog().saveOperLog(request, new Long(2), "mss_user_info", userInfo.getUserId(), new Long(
							9), "用户修改密码");

					resultInfos.add(new ResultInfo(ResultConstants.USER_PWD_MOD_SUCCESS));

					resultInfos.setGotoUrl("/mss/jsp/sysManage/password_mod.jsp");
				}
			});

			resultInfos.setIsAlert(true);
			resultInfos.setIsRedirect(true);
		} catch (Exception ex) {
			resultInfos.setGotoUrl(null);
			log.error("密码修改失败！");
			resultInfos.add(new ResultInfo(ResultConstants.USER_PWD_MOD_FAILED));
			ex.printStackTrace();
		}
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 恢复用户初始密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView revertPassword(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);

		// 用户Id
		final String userId = request.getParameter("userId");

		// 获取并传递查询条件
		final String filter = strucQueryParams(request);

		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			response.setLocale(Locale.CHINA);
			final PrintWriter writer = response.getWriter();

			try {
				transTemplate.execute(new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(TransactionStatus status) {

						UserInfo userInfo = userManageBO.getUserById(new Long(userId));
						userInfo.setLoginPwd("123456");
						userManageBO.saveUserInfo(userInfo);

						new SysOperLog().saveOperLog(request, new Long(2), "mss_user_info", new Long(userId), new Long(
								9), "系统恢复用户初始密码");

						writer.write("true");
					}
				});
			} catch (Exception ex) {
				resultInfos.setGotoUrl(null);
				log.error("系统恢复用户初始密码失败！");
				resultInfos.add(new ResultInfo(ResultConstants.USER_PWD_MOD_FAILED));
				writer.write("false");
				ex.printStackTrace();
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查工号或是登陆名是否唯存在
	 * 
	 * @param request
	 * @param response
	 * @return boolean 不存在：false,存在:true
	 */
	public void checkNoOrNameExist(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {

		String userNum = request.getParameter("userNum");
		String loginName = request.getParameter("loginName");
		String userId = request.getParameter("userId");
		boolean existflag = false;
		;
		List list = userManageBO.checkNoOrNameExist(userNum, loginName, userId);
		if (list != null && list.size() > 0 && ((Long) list.get(0)).intValue() > 0) {
			existflag = true;
		}

		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			response.setLocale(Locale.CHINA);
			PrintWriter writer = response.getWriter();
			writer.write(String.valueOf(existflag));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查密码是否正确
	 * 
	 * @param request
	 * @param response
	 * @return boolean 存在：true,不存在:false
	 */
	public void checkOldPwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {

		String oldPwd = request.getParameter("oldPwd");
		boolean flag = false;
		UserInfo userInfo = userManageBO.getUserById(new CommonOperation().getLoginUserInfo(request).getSysUser()
				.getUserId());
		if (userInfo.getLoginPwd() != null && userInfo.getLoginPwd().equals(oldPwd)) {
			flag = true;
		}

		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			response.setLocale(Locale.CHINA);
			PrintWriter writer = response.getWriter();
			writer.write(String.valueOf(flag));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据request取得相应的查询条件，并组织成List返回
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param MultipartHttpServletRequest
	 *            multiRequest
	 * @return List list.get(0)是PayQueryForm,
	 *         list.get(1)是所有不未空的参数组成的urlParamString
	 */
	private UserManageSearchForm strucQueryParamForm(HttpServletRequest request) {
		// 工号
		String userNum = "";
		// 用户名
		String userName = "";
		// 部门
		String userDept = "";
		// 返回行号
		String indexNO = "";
		// 返回表单
		String returnForm = "";
		// 返回名字文本框
		String returnNameInput = "";
		// 返回id文本框
		String returnIdInput = "";
		// 返回电话文本框
		String returnTelInput = "";
		// 用户权限
		String userRole = "";
		// 当前页数
		String currentPage = "";
		// 操作方式
		String viewOrEdit = "";
		// 用户状态
		String userState = "";

		userNum = request.getParameter("quserNum") == null ? "" : request.getParameter("quserNum").trim();
		userName = request.getParameter("quserName") == null ? "" : request.getParameter("quserName").trim();
		userDept = request.getParameter("quserDept") == null ? "" : request.getParameter("quserDept").trim();
		userRole = request.getParameter("quserRole") == null ? "" : request.getParameter("quserRole").trim();
		indexNO = request.getParameter("indexNO") == null ? "" : request.getParameter("indexNO").trim();
		returnForm = request.getParameter("returnForm") == null ? "" : request.getParameter("returnForm").trim();
		returnNameInput = request.getParameter("returnNameInput") == null ? "" : request
				.getParameter("returnNameInput").trim();
		returnIdInput = request.getParameter("returnIdInput") == null ? "" : request.getParameter("returnIdInput")
				.trim();
		returnTelInput = request.getParameter("returnTelInput") == null ? "" : request.getParameter("returnTelInput")
				.trim();
		currentPage = request.getParameter("currentPage") == null ? "" : request.getParameter("currentPage").trim();
		viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		userState = request.getParameter("userState") == null ? "" : request.getParameter("userState").trim();

		// 查询formbean
		UserManageSearchForm form = new UserManageSearchForm();
		form.setUserNum(userNum);
		form.setUserName(userName);
		form.setUserDept(userDept);
		form.setUserRole(userRole);
		form.setIndexNO(indexNO);
		form.setReturnForm(returnForm);
		form.setReturnIdInput(returnIdInput);
		form.setReturnNameInput(returnNameInput);
		form.setReturnTelInput(returnTelInput);
		form.setCurrentPage(currentPage == null || currentPage.equals("") ? "1" : currentPage);
		form.setViewOrEdit(viewOrEdit == null || viewOrEdit.equals("") ? "view" : viewOrEdit);
		form.setUserState(userState);

		return form;
	}

	/**
	 * 根据request取得相应的查询条件，并组织成List返回
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param MultipartHttpServletRequest
	 *            multiRequest
	 * @return List list.get(0)是PayQueryForm,
	 *         list.get(1)是所有不未空的参数组成的urlParamString
	 */
	private String strucQueryParams(HttpServletRequest request) {
		StringBuffer filter = new StringBuffer();
		// 工号
		String userNum = "";
		// 用户名
		String userName = "";
		// 部门
		String userDept = "";
		// 用户权限
		String userRole = "";
		// 当前页数
		String currentPage = "";
		// 操作方式
		String viewOrEdit = "";
		String indexNO = "";
		String returnForm = "";
		// 返回名字文本框
		String returnNameInput = "";
		// 返回id文本框
		String returnIdInput = "";
		// 返回电话文本框
		String returnTelInput = "";

		userNum = request.getParameter("quserNum") == null ? "" : request.getParameter("quserNum").trim();
		userName = request.getParameter("quserName") == null ? "" : request.getParameter("quserName").trim();
		;
		userDept = request.getParameter("quserDept") == null ? "" : request.getParameter("quserDept").trim();
		userRole = request.getParameter("quserRole") == null ? "" : request.getParameter("quserRole").trim();
		currentPage = request.getParameter("currentPage") == null ? "" : request.getParameter("currentPage").trim();
		viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		indexNO = request.getParameter("indexNO") == null ? "" : request.getParameter("indexNO").trim();
		returnForm = request.getParameter("returnForm") == null ? "" : request.getParameter("returnForm").trim();
		returnNameInput = request.getParameter("returnNameInput") == null ? "" : request
				.getParameter("returnNameInput").trim();
		returnIdInput = request.getParameter("returnIdInput") == null ? "" : request.getParameter("returnIdInput")
				.trim();
		returnTelInput = request.getParameter("returnTelInput") == null ? "" : request.getParameter("returnTelInput")
				.trim();

		// 查询formbean
		if (userNum != null && !userNum.equals("")) {
			filter.append("&quserNum=" + userNum);
		}
		if (userName != null && !userName.equals("")) {
			filter.append("&quserName=" + userName);
		}
		if (userDept != null && !userDept.equals("")) {
			filter.append("&quserDept=" + userDept);
		}
		if (userRole != null && !userRole.equals("")) {
			filter.append("&quserRole=" + userRole);
		}
		if (currentPage != null && !currentPage.equals("")) {
			filter.append("&currentPage=" + currentPage);
		}
		if (viewOrEdit != null && !viewOrEdit.equals("")) {
			filter.append("&viewOrEdit=" + viewOrEdit);
		}
		if (indexNO != null && !indexNO.equals("")) {
			filter.append("&indexNO=" + indexNO);
		}
		if (returnForm != null && !returnForm.equals("")) {
			filter.append("&returnForm=" + returnForm);
		}
		if (returnNameInput != null && !returnNameInput.equals("")) {
			filter.append("&returnNameInput=" + returnNameInput);
		}
		if (returnIdInput != null && !returnIdInput.equals("")) {
			filter.append("&returnIdInput=" + returnIdInput);
		}
		if (returnTelInput != null && !returnTelInput.equals("")) {
			filter.append("&returnTelInput=" + returnTelInput);
		}

		return filter.toString();
	}

	/**
	 * 设置修改记录授权密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView setAuthorizPassword(final HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		// 用户Id
		final UserInfo userInfo = userManageBO.getUserById(new CommonOperation().getLoginUserInfo(request).getSysUser()
				.getUserId());

		// 密码
		final String loginPwd = request.getParameter("oldPassword");

		
		try {
			transTemplate.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					MpmsAuthorization mpmsAuthorization = authorizationBO.findOnlyOne();
					if(mpmsAuthorization==null){
						mpmsAuthorization = new MpmsAuthorization();
					}
					mpmsAuthorization.setPassword(loginPwd);
					mpmsAuthorization.setUpdateTime(DateUtils.getChar12());
					mpmsAuthorization.setStatus("A");
					authorizationBO.save(mpmsAuthorization);

					new SysOperLog().saveOperLog(request, new Long(2), "MPMS_AUTHORIZATION", userInfo.getUserId(), new Long(
							7), "管理员修改授权码");

					resultInfos.add(new ResultInfo(ResultConstants.USER_PWD_MOD_SUCCESS));

					resultInfos.setGotoUrl("/mss/jsp/sysManage/userManageController.do?method=queryAuthorizPassword&id="+mpmsAuthorization.getId());
				}
			});

			resultInfos.setIsAlert(true);
			resultInfos.setIsRedirect(true);
			map.put("loginPwd", loginPwd);
		} catch (Exception ex) {
			resultInfos.setGotoUrl("/mss/jsp/business/setAuthorizationCode.jsp");
			log.error("修改授权码失败！");
			resultInfos.add(new ResultInfo(ResultConstants.USER_PWD_MOD_FAILED));
			ex.printStackTrace();
		}
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 查询修改记录授权密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryAuthorizPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		ResultInfos resultInfos = new ResultInfos();
		
		String id = request.getParameter("id");
		MpmsAuthorization mpmsAuthorization = null;
		if(id!=null && !"".equals(id)){
			mpmsAuthorization = authorizationBO.findById(new Long(id));
		}
		map.put("mpmsAuthorization",mpmsAuthorization.getPassword());
		return new ModelAndView("/mss/jsp/business/setAuthorizationCode.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView checkAuthorizPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String authorizCode = request.getParameter("authorCode");
		MpmsAuthorization mpmsAuthorization = authorizationBO.findOnlyOne();
		response.setContentType("text/html; charset=utf-8");
		try {
			if(authorizCode.equals(mpmsAuthorization.getPassword())){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
