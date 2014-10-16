package com.xwtech.mss.bo.system.user;

import java.util.HashMap;
import java.util.List;

import com.xwtech.mss.formBean.UserManageSearchForm;
import com.xwtech.mss.pub.dao.system.RoleDAO;
import com.xwtech.mss.pub.dao.system.UserInfoDAO;
import com.xwtech.mss.pub.po.UserInfo;

public class UserManageBO
{

	private UserInfoDAO userInfoDAO;
	
	private RoleDAO roleDAO;
	
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	/**
	 * 查找指定用户信息
	 * @author:gu_daping
	 */
	public UserInfo getUserById(Long userId)
	{
		return userInfoDAO.findById(userId);
	}

	/**
	 * 根据工号查找指定用户信息
	 * @author:gu_daping
	 */
	public UserInfo getUserByNum(String userNum)
	{
		UserInfo user = null;
		List list = userInfoDAO.findByUserNum(new Long(userNum));
		if(list!=null && list.size()>0){
			user = (UserInfo)list.get(0);
		}
		return user;
	}

	/**
	 * 根据部门查找指定用户列表
	 * @author:gu_daping
	 */
	public List getUserByDept(String deptId)
	{
		return userInfoDAO.getUserByDept(deptId);
	}

	/**
	 * 根据工号查找指定用户信息
	 * @author:gu_daping
	 */
	public List getUserByName(String userName)
	{
		return userInfoDAO.findByUserName(userName);
	}
	
	/**
	 * 根据指定条件查询用户列表
	 * @author:gu_daping
	 */
	public HashMap queryUserList(UserManageSearchForm searchForm)
	{
		HashMap userListResult = userInfoDAO.queryUserList(searchForm);
		return userListResult;
	}

	/**
	 * 保存用户信息
	 * @author:gu_daping
	 */
	public UserInfo saveUserInfo(UserInfo userInfo)
	{
		return userInfoDAO.save(userInfo);
	}
	
	/**
	 * 根据删除用户信息
	 */
	public void delUsers(String userIds){
		userInfoDAO.delRecords(userIds);
	}
	
	/**
	 * 校验工号或是登陆名是否唯一
	 */
	public List checkNoOrNameExist(String userNum, String loginName, String userId){
		return userInfoDAO.checkNoOrNameExist(userNum, loginName, userId);
	}
	/**
	 * 校验需要关联的userId是否存在
	 */
	public List checkUserIdExist( String userId){
		return userInfoDAO.checkUserIdExist(userId);
	}
}
