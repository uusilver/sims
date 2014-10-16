/**
 * 
 */
package com.xwtech.mss.bo.system.property;

import com.xwtech.mss.pub.dao.system.UserPropertyDAO;
import com.xwtech.mss.pub.po.UserProperty;


/**
 * 
 * @author gu_daping
 * 
 */
public class UserPropertyBO {


	/**
	 * 注入DAO
	 */
	private UserPropertyDAO userPropertyDAO;

	public UserPropertyDAO getUserPropertyDAO() {
		return userPropertyDAO;
	}

	public void setUserPropertyDAO(UserPropertyDAO userPropertyDAO) {
		this.userPropertyDAO = userPropertyDAO;
	}

	/**
	 * 根据用户权限标识查找用户权限信息
	 */
	public UserProperty findUserPropertyById(Long id){
		return userPropertyDAO.findById(id);
	}

	/**
	 * 保存用户权限信息
	 */
	public void saveUserProperty(UserProperty userProperty){
		userPropertyDAO.save(userProperty);
	}
	
	/**
	 * 根据用户信息删除用户权限信息
	 */
	public void delUserPropertyByUserIds(String userIds){
		userPropertyDAO.delRecords(userIds);
	}
	
	/**
	 * 根据角色ID删除用户权限信息
	 * @param roleIds
	 * @author weizhen
	 */
	public void delUserPropertyByRoleIds(String strRoleIds){
		userPropertyDAO.delUserPropertyByRoleIds(strRoleIds);
	}
	
	/**
	 * 保存
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	public int save(Long roleId, String menuIds){
		return userPropertyDAO.save(roleId, menuIds);
	}
	
}
