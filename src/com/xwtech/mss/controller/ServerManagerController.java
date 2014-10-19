package com.xwtech.mss.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.system.user.UserManageBO;
import com.xwtech.mss.formBean.UserManageSearchForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.po.UserProperty;
import com.xwtech.mss.pub.tools.CommonOperation;

public class ServerManagerController extends MultiActionController {
	
	private static final Log log = LogFactory.getLog(ServerManagerController.class);
	
	
	public ModelAndView queryServerList(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();

		// 获取并传递查询条件
		UserManageSearchForm searchForm = strucQueryParamForm(request);
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");

		// 菜单首次访问，默认查询状态有效的信息
		return null;
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
}
