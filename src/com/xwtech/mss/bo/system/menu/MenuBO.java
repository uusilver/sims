/**
 * 
 */
package com.xwtech.mss.bo.system.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.xwtech.framework.pub.po.FrameMenu;
import com.xwtech.mss.formBean.MenuForm;
import com.xwtech.mss.pub.constants.DmsConstants;
import com.xwtech.mss.pub.dao.system.MenuDAO;
import com.xwtech.mss.pub.po.Menu;
import com.xwtech.mss.pub.po.UserInfo;

/**
 * <p>
 * Title:菜单的查询及维护
 * </p>
 * 
 * @author gu_daping
 * 
 */
public class MenuBO {

	/**
	 * 注入DAO
	 */
	private MenuDAO menuDAO;

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	/**
	 * 查找所有菜单信息
	 * 
	 * @param
	 * @return List 所有菜单列表
	 */
	public List queryMenu() {
		return menuDAO.queryMenu();
	}

	/**
	 * 查找所有菜单信息
	 * 
	 * @param
	 * @return List 所有菜单列表
	 */
	public HashMap queryMenuList(MenuForm form) {
		String perPageCount = new Integer(DmsConstants.COUNT_FOR_EVERY_PAGE).toString();

		return menuDAO.queryMenuList(form, perPageCount);
	}

	/**
	 * 查找所有的一级菜单
	 * 
	 * @param
	 * @return List 所有一级菜单列表
	 */
	public List queryTopMenu() {
		return menuDAO.findByMenuLevel("1");
	}

	/**
	 * 查找所有的二级菜单并按照起上级菜单进行排序
	 * 
	 * @param
	 * @return List 所有二级菜单列表
	 */
	public List querySubMenu() {
		return menuDAO.querySubMenu();
	}

	/**
	 * 根据菜单id查找指定菜单
	 * 
	 * @param Long
	 *            id 要查找的菜单的标识
	 * @return PmsMenu 指定的菜单对象
	 */
	public Menu getMenuById(Long id) {
		return menuDAO.findById(id);
	}
	
	
	public List findByProperty(String propertyName, Object value) {
		return menuDAO.findByProperty(propertyName, value);
	}

	
	/**
	 * 保存菜单对象
	 * 
	 * @param PmsMenu
	 *            menu 要保存的菜单对象
	 * @return
	 */
	public Menu save(Menu menu) {
		menuDAO.save(menu);
		return menu;
	}

	/**
	 * 删除菜单对象
	 * 
	 * @param PmsMenu
	 *            menu 要删除的菜单对象
	 */
	public void delete(Menu menu) {
		menuDAO.delete(menu);
	}

	/**
	 * 根据用户查找用户菜单
	 * 
	 * @param PmsUser
	 *            userInfo
	 * @return List 菜单列表
	 */
	public List querySysMenuByUser(UserInfo userInfo) {
		return menuDAO.querySysMenuByUser(userInfo);
	}

	/**
	 * 查询系统菜单
	 * 
	 * @param roleId
	 * @param roleId2
	 * @return List 菜单列表：一级菜单 + 二级菜单
	 * @author weizhen
	 */
	public List querySysMenu(String roleId, String roleId2) {
		ArrayList aList = new ArrayList();
		List menuList = menuDAO.querySysMenu(roleId, roleId2);

		// 为了便于页面标签的数据传入，再将值保存至FrameMenu对象中
		FrameMenu menu;
		ListOrderedMap listMap;
		for (int i = 0; i < menuList.size(); i++) {
			menu = new FrameMenu();
			// 取出数据库查询值
			listMap = (ListOrderedMap) menuList.get(i);
			Integer menuId = (Integer)(listMap.get("menu_id"));
			String menuName = (String)(listMap.get("menu_name"));
			menu.setMenuId(menuId);
			menu.setMenuName(menuName);

			// 将FrameMenu对象加入集合中
			aList.add(menu);
		}

		return aList;
	}
}
