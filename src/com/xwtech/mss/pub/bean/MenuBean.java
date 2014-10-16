package com.xwtech.mss.pub.bean;

/**
 * 用以组装菜单对象
 * @author benbenxiong
 *
 */

public class MenuBean {

	//菜单标识
	private Long menuId;
	
	//菜单名称
	private String menuName;

	//构造方法
	public MenuBean(Long menuId, String menuName){
		this.menuId = menuId;
		this.menuName = menuName;
	}
	
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	
}
