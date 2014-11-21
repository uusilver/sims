package com.tmind.mss.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

import com.tmind.framework.pub.result.ResultInfo;
import com.tmind.framework.pub.result.ResultInfos;
import com.tmind.framework.pub.utils.SessionUtils;
import com.tmind.framework.pub.utils.StringUtils;
import com.tmind.framework.pub.web.FrameworkApplication;
import com.tmind.framework.pub.web.RequestNameConstants;
import com.tmind.mss.bo.system.menu.MenuBO;
import com.tmind.mss.bo.system.orgnization.OrgnizationBO;
import com.tmind.mss.bo.system.property.RoleBO;
import com.tmind.mss.bo.system.property.UserPropertyBO;
import com.tmind.mss.bo.system.user.UserManageBO;
import com.tmind.mss.formBean.BaseForm;
import com.tmind.mss.pub.constants.SpmsConstants;
import com.tmind.mss.pub.po.Role;
import com.tmind.mss.pub.result.ResultConstants;
import com.tmind.mss.pub.tools.CommonOperation;
import com.tmind.mss.pub.tools.StructForm;

public class RoleManageController extends MultiActionController {

	private static final Log log = LogFactory.getLog(RoleManageController.class);

	private UserManageBO userManageBO;

	private OrgnizationBO orgnizationBO;

	private MenuBO menuBO;

	private RoleBO roleBO;

	private UserPropertyBO userPropertyBO;

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

	/**
	 * 查询角色列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryRoleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获得页面表单信息
		BaseForm roleForm = new BaseForm();

		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");

		
		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			roleForm.setRoleState(SpmsConstants.STATE_A);
			SessionUtils.setObjectAttribute(request, "roleFormSession", roleForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			roleForm = (BaseForm) SessionUtils.getObjectAttribute(request, "roleFormSession");
		} else {
			roleForm = (BaseForm) new StructForm().strucReqInfoSearchForm(request, null);
			SessionUtils.setObjectAttribute(request, "roleFormSession", roleForm);
		}
		
		roleForm.setRoleState(SpmsConstants.STATE_A);
		
		// 调用BO查询，得到结果集
		HashMap roleList = roleBO.queryRoleList(roleForm);

		roleList.put("roleForm", roleForm);

		return new ModelAndView("/mss/jsp/sysManage/roleList.jsp", RequestNameConstants.INFORMATION, roleList);
	}

	/**
	 * 查看指定角色详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryRoleInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		//
		CommonOperation commonOperation = new CommonOperation();
		commonOperation.getLoginUserRole(request);
		// Long roleId = commonOperation.getLoginUserRole(request).getRoleId();
		// 获得页面表单信息
		BaseForm roleForm = (BaseForm) new StructForm().strucReqInfoSearchForm(request, null);
		// 调用BO查询，得到结果集
		Role role = roleBO.findRoleById(new Long(roleForm.getRoleId()));
		String roleId = String.valueOf(role.getRoleId());

		// 调用BO查询菜单列表，返回形式为：一级菜单 + 二级菜单，放入集合中
		List unOwnerMenuList = menuBO.querySysMenu(null, roleId);
		List ownerMenuList = menuBO.querySysMenu(roleId, null);

		// 暂认为系统管理员角色ID为1，系统管理员不能修改自己的角色信息。（如有需要，以后再扩充。）
		map.put("isSysRole", new Boolean(!roleForm.getRoleId().equals("1")));
		map.put("role", role);
		map.put("unOwnerMenuList", unOwnerMenuList);
		map.put("ownerMenuList", ownerMenuList);

		return new ModelAndView("/mss/jsp/sysManage/roleInfo.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 进入新增角色信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView preSaveRoleInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		// 调用BO查询菜单列表，返回形式为：一级菜单 + 二级菜单，放入集合中
		map.put("menuList", menuBO.querySysMenu(null, null));

		return new ModelAndView("/mss/jsp/sysManage/roleAdd.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 新增角色信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveRoleInfo(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);

		// 获得页面表单信息
		final BaseForm roleForm = (BaseForm) new StructForm().strucReqInfoSearchForm(request, null);

		// 页面选择的菜单信息
		final String menuIds = request.getParameter("hiddenMenuId");
		log.info("menuIds:" + menuIds);

		try {
			transTemplate.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// 1.新增角色信息
					long roleId = roleBO.saveRoleInfo(roleForm);
					log.info("roleId: " + roleId);

					// 2.新增用户权限
					if (!StringUtils.isEmpty(menuIds)) {
						userPropertyBO.save(new Long(roleId), menuIds);
					}

					// 3.新增角色可访问URL信息
					roleBO.saveFrameUrlInfo(new Long(roleId), roleForm);
				}
			});

			resultInfos.add(new ResultInfo(ResultConstants.ROLEINFO_ADD_SUCCESS));
			resultInfos.setGotoUrl("/mss/jsp/sysManage/roleManageController.do?method=queryRoleList");
		} catch (Exception ex) {
			resultInfos.setGotoUrl("/mss/jsp/sysManage/roleManageController.do?method=queryRoleList");
			log.error("角色信息保存失败！");
			resultInfos.add(new ResultInfo(ResultConstants.ROLEINFO_ADD_FAILED));
			ex.printStackTrace();
		}

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 更新角色信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateRoleInfo(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);

		// 获得页面表单信息
		final BaseForm roleForm = (BaseForm) new StructForm().strucReqInfoSearchForm(request, null);
		final String roleId = roleForm.getRoleId();
		log.info("roleId: " + roleId);

		// 选择的菜单信息
		final String menuIds = request.getParameter("hiddenMenuId");
		log.info("menuIds:" + menuIds);

		try {
			transTemplate.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// 1.新增角色信息
					roleBO.updateRoleInfo(roleForm);

					// 2.操作用户权限表
					// 2.1.删除原用户权限
					userPropertyBO.delUserPropertyByRoleIds(roleId);
					// 2.2.新增用户权限
					userPropertyBO.save(new Long(roleId), menuIds);
				}
			});

			resultInfos.add(new ResultInfo(ResultConstants.ROLEINFO_MOD_SUCCESS));
			resultInfos
					.setGotoUrl("/mss/jsp/sysManage/roleManageController.do?method=queryRoleList" + "&ifSession=yes");
		} catch (Exception ex) {
			resultInfos.setGotoUrl(null);
			log.error("角色信息更新失败！");
			resultInfos.add(new ResultInfo(ResultConstants.ROLEINFO_MOD_FAILED));
			ex.printStackTrace();
		}

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 删除角色信息(逻辑删除)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView delRoleInfo(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);

		try {
			// 获得页面表单信息
			String roleIdStr = request.getParameter("roleIdStr");
			// 根据权限ID删除相关权限信息
			roleBO.delRoleInfo(roleIdStr);

			resultInfos.add(new ResultInfo(ResultConstants.ROLEINFO_DEL_SUCCESS));
			resultInfos.setGotoUrl("/mss/jsp/sysManage/roleManageController.do?method=queryRoleList" + "&ifSession=yes");
		} catch (Exception ex) {
			resultInfos.setGotoUrl(null);
			log.error("角色信息删除失败！");
			resultInfos.add(new ResultInfo(ResultConstants.ROLEINFO_DEL_FAILED));
			ex.printStackTrace();
		}

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}

	/**
	 * 用于AJAX检查某表某字段值是否存在。（有时间加入共通方法类）
	 * @param request
	 * @param response
	 * @return
	 */
	public void checkIsExist(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		// 表名
		String tableName = request.getParameter("tableName");
		// 字段名
		String colName = request.getParameter("colName");
		// 字段值
		String colVal = request.getParameter("colVal");
		// 该信息所属值
		String ownerVal = request.getParameter("ownerVal");
		
		// 记录状态字段名
		String stateColName = request.getParameter("stateColName");

		String[] tableNameAndServerType =null;
		
		// 服务器类型，0：跳转服务器，1：运维服务器，2：DNS服务器
		String serverType = "";
		try {
			StringBuffer sbSql = new StringBuffer("SELECT COUNT(0) FROM ");
			
			if(tableName.indexOf(",")!=-1){
				tableNameAndServerType = tableName.split(",");
				serverType = tableNameAndServerType[1];
			}
			
			if(tableNameAndServerType!=null){
				sbSql.append(tableNameAndServerType[0]);
			}else{
				sbSql.append(tableName);
			}

			sbSql.append(" WHERE ");
			sbSql.append(colName + " = '" + colVal.trim() + "'");
			sbSql.append(" and "+stateColName+" != "+"'D'");
			
			if(serverType.equals("0")||serverType.equals("2")){
				sbSql.append(" and servertype=1 ");
			}
			
			if(serverType.equals("1")){
				sbSql.append(" and servertype=2 ");
			}

			log.info("检查是否存在SQL：" + sbSql.toString());
			long rowCount = FrameworkApplication.baseJdbcDAO.queryForLong(sbSql.toString());

			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			// response.setLocale(Locale.SIMPLIFIED_CHINESE);
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();

			writer.write(rowCount == 0 ? "true" : "false");

			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
