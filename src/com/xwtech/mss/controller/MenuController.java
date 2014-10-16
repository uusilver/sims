package com.xwtech.mss.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.system.menu.MenuBO;
import com.xwtech.mss.bo.system.property.RoleBO;
import com.xwtech.mss.bo.system.property.UserPropertyBO;
import com.xwtech.mss.formBean.BaseForm;
import com.xwtech.mss.formBean.MenuForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.Menu;
import com.xwtech.mss.pub.po.Role;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.po.UserProperty;
import com.xwtech.mss.pub.tools.CommonOperation;
import com.xwtech.mss.pub.tools.StructForm;

/**
 * <p>
 * Title:菜单的查询及维护
 * </p>
 * 
 * @author gu_daping
 */
public class MenuController extends MultiActionController {

	private static final Log log = LogFactory.getLog(MenuController.class);

	/**
	 * 注入菜单BO
	 */
	private MenuBO menuBO;

	private UserPropertyBO userPropertyBO;

	private RoleBO roleBO;

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;

	public MenuBO getMenuBO() {
		return menuBO;
	}

	public void setMenuBO(MenuBO menuBO) {
		this.menuBO = menuBO;
	}

	public TransactionTemplate getTransTemplate() {
		return transTemplate;
	}

	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	/**
	 * 查询出所有菜单
	 */
	public ModelAndView queryMenuList(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		MenuForm menuForm = new MenuForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			menuForm.setQueryMenuState(SpmsConstants.STATE_A);
			SessionUtils.setObjectAttribute(request, "menuFormSession", menuForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			menuForm = (MenuForm) SessionUtils.getObjectAttribute(request, "menuFormSession");
		} else {
			String currentPage = request.getParameter("currentPage");
			String queryResourceName = request.getParameter("queryResourceName");
			String queryRoleId = request.getParameter("queryRoleId");
			String queryMenuLevel = request.getParameter("queryMenuLevel");
			String queryMenuState = request.getParameter("queryMenuState");

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}

			menuForm.setCurrentPage(currentPage);
			menuForm.setQueryMenuLevel(queryMenuLevel);
			menuForm.setQueryResourceName(queryResourceName);
			menuForm.setQueryRoleId(queryRoleId);
			menuForm.setQueryMenuState(queryMenuState);

			SessionUtils.setObjectAttribute(request, "menuFormSession", menuForm);
		}

		HashMap menuResult = menuBO.queryMenuList(menuForm);

		map.put("menuList", (List) menuResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, menuResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, menuResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, menuResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", menuForm);

		return new ModelAndView("/mss/jsp/sysManage/menuList.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 查看指定菜单信息
	 */
	public ModelAndView queryMenuInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {

		HashMap map = new HashMap();
		ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);

		String currPage = request.getParameter("currPage");

		String resourceId = request.getParameter("resourceId");
		Menu menu = new Menu();
		if (resourceId != null && !resourceId.equals("")) {
			menu = menuBO.getMenuById(new Long(resourceId));
		}

		String oprType = request.getParameter("oprType");
		if (oprType == null || oprType.equals("")) {
			oprType = "view";
		}

		map.put("menuInfo", menu);
		map.put("oprType", oprType);

		return new ModelAndView("/mss/jsp/sysManage/menuInfo.jsp?currPage=" + currPage,
				RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 保存菜单信息
	 */
	public ModelAndView saveMenuInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {

		final String resourceName = request.getParameter("resourceName");
		final String resourceUrl = request.getParameter("resourceUrl");
		final String menuState = request.getParameter("menuState");
		final String menuOrder = request.getParameter("menuOrder");
		final String menuLevel = request.getParameter("menuLevel");

		final String resourceId = request.getParameter("resourceId");
		final String parentMenuId = request.getParameter("parentMenuId");

		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 1.添加菜单信息
				Menu menu = null;

				if (null == resourceId || "".equals(resourceId)) { // 新增
					menu = new Menu();
					menu.setMenuState(SpmsConstants.STATE_A);
					// 2.为系统管理员配置此菜单(每次添加菜单，自动配置)
					UserProperty userProperty = new UserProperty();

					userProperty.setMenu(menu);
					userProperty.setRole(roleBO.findRoleById(new Long(SpmsConstants.ROLE_ADMIN)));
					userProperty.setState(SpmsConstants.STATE_A);

					userPropertyBO.saveUserProperty(userProperty);
				} else { // 更新
					menu = menuBO.getMenuById(new Long(resourceId));
					menu.setMenuState(menuState);
				}

				// 二级菜单,需要设置上级菜单
				if ("2".equals(menuLevel)) {
					Menu menuFirst = menuBO.getMenuById(new Long(parentMenuId));
					menu.setMenu(menuFirst);
				}
				// 一级菜单
				else if ("1".equals(menuLevel)) {
				}

				menu.setMenuName(resourceName);
				menu.setMenuOrder(new Long(menuOrder));
				menu.setMenuUrl(resourceUrl);

				menu.setMenuLevel(new Long(menuLevel));

				menuBO.save(menu);

			}
		});

		return new ModelAndView("/mss/jsp/menuController.do?method=queryMenuList&ifSession=yes", RequestNameConstants.INFORMATION, null);
	}

	/**
	 * 删除菜单
	 */
	public ModelAndView delMenuInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {

		String[] resourceId = request.getParameterValues("resourceId");

		String currentPage = request.getParameter("currentPage");
		if (null == currentPage || "".equals(currentPage)) {
			currentPage = "1";
		}

		if (resourceId != null) {
			for (int i = 0; i < resourceId.length; i++) {
				Menu menu = menuBO.getMenuById(new Long(resourceId[i]));
				if (menu == null && menu.getMenuId() == null) {
					continue;
				}
				menu.setMenuState(SpmsConstants.STATE_U); // 设为无效
				menuBO.save(menu);
			}
		}

//		try {
//			response.sendRedirect("/mss/jsp/menuController.do?method=queryMenuList&currentPage=" + currentPage);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return new ModelAndView("/mss/jsp/menuController.do?method=queryMenuList&currentPage=" + currentPage, RequestNameConstants.INFORMATION, null);

	}

	/**
	 * 根据登陆用户取得用户菜单 按照一级菜单、二级菜单的顺序排列，各级菜单按照顺序排列
	 */
	public ModelAndView queryTopMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		HashMap map = new HashMap();
		ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);

		// 取得用户信息
		UserInfo userInfo = new CommonOperation().getLoginUserInfo(request).getSysUser();

		List menuList = new ArrayList();
		menuList = menuBO.querySysMenuByUser(userInfo);

		map.put("menuList", menuList);

		return new ModelAndView("/mss/jsp/menu.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * @return the userPropertyBO
	 */
	public UserPropertyBO getUserPropertyBO() {
		return userPropertyBO;
	}

	/**
	 * @param userPropertyBO
	 *            the userPropertyBO to set
	 */
	public void setUserPropertyBO(UserPropertyBO userPropertyBO) {
		this.userPropertyBO = userPropertyBO;
	}

	/**
	 * @return the roleBO
	 */
	public RoleBO getRoleBO() {
		return roleBO;
	}

	/**
	 * @param roleBO
	 *            the roleBO to set
	 */
	public void setRoleBO(RoleBO roleBO) {
		this.roleBO = roleBO;
	}

}
