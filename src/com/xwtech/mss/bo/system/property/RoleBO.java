/**
 * 
 */
package com.xwtech.mss.bo.system.property;

import java.util.HashMap;

import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.BaseForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.dao.system.RoleDAO;
import com.xwtech.mss.pub.po.Role;

/**
 * 
 * @author gu_daping
 * 
 */
public class RoleBO {

	/**
	 * 注入DAO
	 */
	private RoleDAO roleDAO;

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * 根据权限标识查找权限信息
	 */
	public Role findRoleById(Long id) {
		return roleDAO.findById(id);
	}

	/**
	 * 查询角色列表
	 * 
	 * @param roleForm
	 * @return
	 * @author weizhen
	 */
	public HashMap queryRoleList(BaseForm roleForm) {
		HashMap userListResult = roleDAO.queryRoleList(roleForm);
		return userListResult;
	}

	/**
	 * 保存角色信息
	 * 
	 * @param roleInfo
	 * @author weizhen
	 */
	public void saveRoleInfo(Role roleInfo) {
		roleDAO.save(roleInfo);
	}

	/**
	 * 保存角色信息，并返回此次保存的主键
	 * 
	 * @param roleForm
	 * @return
	 * @author weizhen
	 */
	public long saveRoleInfo(BaseForm roleForm) {
		// 拼装SQL
		String strSql = "INSERT INTO frame_role fr VALUES (FRAME_SEQ_ROLE.NEXTVAL, ? , ? , ?)";
		Object[] param = new Object[3];
		param[0] = roleForm.getRoleName();
		param[1] = roleForm.getRoleDesc();
		param[2] = roleForm.getRoleState();
		// 1.保存信息
		roleDAO.save(strSql, param);
		// 2.获得新增记录的主键
		String querySql = "SELECT MAX(t.role_id) FROM frame_role t";
		long roleId = FrameworkApplication.baseJdbcDAO.queryForLong(querySql);

		return roleId;

	}

	/**
	 * 更新角色信息
	 * 
	 * @param roleInfo
	 * @author weizhen
	 */
	public void updateRoleInfo(Role roleInfo) {
		roleDAO.update(roleInfo);
	}

	/**
	 * 更新角色信息
	 * 
	 * @param roleForm
	 * @author weizhen
	 */
	public void updateRoleInfo(BaseForm roleForm) {
		// 拼装SQL
		String strSql = "UPDATE frame_role fr SET fr.role_name = ?, fr.description =?, fr.state =? WHERE fr.role_id = ?";
		Object[] param = new Object[4];
		param[0] = roleForm.getRoleName();
		param[1] = roleForm.getRoleDesc();
		param[2] = roleForm.getRoleState();
		param[3] = roleForm.getRoleId();
		// 1.保存信息
		roleDAO.save(strSql, param);

	}

	/**
	 * 新增角色可访问URL信息
	 * 
	 * @param searchForm
	 * @return
	 * @author weizhen
	 */
	public void saveFrameUrlInfo(Long roleId, BaseForm roleForm) {
		String[] frameUrl = SpmsConstants.FRAME_URL_VALUES;
		for (int i = 0; i < frameUrl.length; i++) {
			// 拼装SQL
			String strSql = "INSERT INTO frame_url VALUES (frame_seq_url.NEXTVAL, ?, ?, ?, ?, ?)";
			Object[] param = new Object[5];
			param[0] = roleId;
			param[1] = frameUrl[i];
			// 目前先这样定义：角色名称 + "访问资源"
			param[2] = roleForm.getRoleName() + "访问资源";
			param[3] = roleForm.getRoleName() + "访问资源";
			param[4] = "A";
			// 1.保存信息
			roleDAO.save(strSql, param);
		}

	}

	/**
	 * 保存/更新角色信息
	 * 
	 * @param roleInfo
	 * @author weizhen
	 */
	public void saveOrUpdateRoleInfo(Role roleInfo) {
		roleDAO.saveOrUpdate(roleInfo);
	}

	/**
	 * 删除角色信息
	 * 
	 * @param roleIdStr
	 * @return
	 * @author weizhen
	 */
	public int delRoleInfo(String roleIdStr) {
		return roleDAO.delRoleInfo(roleIdStr);
	}

}
