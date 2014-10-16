package com.xwtech.mss.pub.dao.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.MenuForm;
import com.xwtech.mss.pub.po.Menu;
import com.xwtech.mss.pub.po.UserInfo;

/**
 * Data access object (DAO) for domain model class Menu.
 * 
 * @see com.xwtech.mss.pub.po.Menu
 * @author MyEclipse - Hibernate Tools
 */
public class MenuDAO extends BaseDao {

	private static final Log log = LogFactory.getLog(MenuDAO.class);

	// property constants
	public static final String MENU_NAME = "menuName";

	public static final String MENU_LEVEL = "menuLevel";

	public static final String MENU_URL = "menuUrl";

	public static final String COMMENT = "comment";

	public static final String MENU_STATE = "menuState";

	protected void initDao() {
		// do nothing
	}

	public void save(Menu transientInstance) {
		log.debug("saving Menu instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Menu persistentInstance) {
		log.debug("deleting Menu instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Menu findById(java.lang.Long id) {
		log.debug("getting Menu instance with id: " + id);
		try {
			Menu instance = (Menu) getHibernateTemplate().get("com.xwtech.mss.pub.po.Menu", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Menu instance) {
		log.debug("finding Menu instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Menu instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Menu as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMenuName(Object menuName) {
		return findByProperty(MENU_NAME, menuName);
	}

	public List findByMenuLevel(Object menuLevel) {
		return findByProperty(MENU_LEVEL, menuLevel);
	}

	public List findByMenuUrl(Object menuUrl) {
		return findByProperty(MENU_URL, menuUrl);
	}

	public List findByComment(Object comment) {
		return findByProperty(COMMENT, comment);
	}

	public List findByMenuState(Object menuState) {
		return findByProperty(MENU_STATE, menuState);
	}

	public Menu merge(Menu detachedInstance) {
		log.debug("merging Menu instance");
		try {
			Menu result = (Menu) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Menu instance) {
		log.debug("attaching dirty Menu instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Menu instance) {
		log.debug("attaching clean Menu instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MenuDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MenuDAO) ctx.getBean("MenuDAO");
	}

	/**
	 * 查找所有菜单信息
	 * 
	 * @author gu_daping
	 */
	public List queryMenu() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select menu from Menu menu " + "order by menu.menuLevel, menu.pmsMenu.menuId ");
		return getHibernateTemplate().find(hql.toString());
	}

	/**
	 * 查找所有二级菜单信息 过滤掉系统管理菜单及物品类别当月修改,订单厂商修改
	 * 
	 * @author gu_daping
	 */
	public List querySubMenu() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select menu from Menu menu "
				+ " where menu.menuLevel='2' and menu.menuId not in ('40','41') and menu.menu.menuId!='9' "
				+ "order by menu.menu.menuId,menu.menuName ");
		return getHibernateTemplate().find(hql.toString());
	}

	/**
	 * 根据用户查找用户菜单，过滤掉系统维护菜单
	 * 
	 * @param PmsUser
	 *            userInfo
	 * @return List 菜单列表
	 */
	public List querySysMenuByUser(UserInfo userInfo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select prop.menu from UserProperty prop where prop.role.roleId=" + userInfo.getRole().getRoleId()
				+ " and prop.menu.menuState='A' and prop.role.state='A'");
		hql.append(" order by prop.menu.menuLevel, prop.menu.menuOrder, prop.menu.menuId");
		return getHibernateTemplate().find(hql.toString());
	}

	/**
	 * 查询系统菜单
	 * 
	 * @param roleId
	 *            查询所属菜单
	 * @param roleId2
	 *            查询非所属菜单
	 * @return List 菜单列表：一级菜单 + 二级菜单
	 * @author weizhen
	 */
	public List querySysMenu(String roleId, String roleId2) {
		StringBuffer sbSql = new StringBuffer(
				"SELECT fm1.menu_id, CONCAT(fm2.menu_name,' - ',fm1.menu_name) as menu_name");
		sbSql.append(" FROM frame_menu fm1");
		if (!StringUtils.isEmpty(roleId)) {
			sbSql.append(" LEFT OUTER JOIN frame_user_property fup ON fup.menu_id = fm1.menu_id");
		}
		sbSql.append(", frame_menu fm2");
		sbSql.append(" WHERE fm1.up_menu_id = fm2.menu_id");
		sbSql.append(" AND fm1.up_menu_id IS NOT NULL");
		sbSql.append(" AND fm2.up_menu_id IS NULL");
		sbSql.append(" AND fm1.menu_state = 'A'");
		sbSql.append(" AND fm2.menu_state = 'A'");
		// 根据角色ID查询所属菜单
		if (!StringUtils.isEmpty(roleId)) {
			sbSql.append(" AND fup.role_id = " + roleId);
		}
		// 根据角色ID查询非所属菜单
		if (!StringUtils.isEmpty(roleId2)) {
			sbSql.append(" AND NOT EXISTS (SELECT 0 FROM frame_user_property fup");
			sbSql.append(" WHERE fup.menu_id = fm1.menu_id AND fup.role_id = " + roleId2 + ")");
		}
		sbSql.append(" ORDER BY fm1.menu_id");

		log.info("查询系统菜单SQL: " + sbSql.toString());

		return FrameworkApplication.baseJdbcDAO.queryForList(sbSql.toString());
	}

	/**
	 * 查找所有菜单信息
	 * 
	 * @author gu_daping
	 */
	public HashMap queryMenuList(MenuForm searchForm, String perPageCount) {

		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select menu from Menu menu ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(menu.menuId) from Menu menu ");

		StringBuffer filterHql = new StringBuffer();
		if (searchForm.getQueryRoleId() != null && !"".equals(searchForm.getQueryRoleId())) {
			filterHql.append(" left join menu.userProperties as userProperty  ");
		}
			
		filterHql.append(" where 1=1 ");

		if (searchForm.getQueryResourceName() != null && !"".equals(searchForm.getQueryResourceName())) {
			filterHql.append(" and menu.menuName like ?");
			paramList.add((Object) ("%" + searchForm.getQueryResourceName() + "%"));
		}
		if (searchForm.getQueryMenuLevel() != null && !"".equals(searchForm.getQueryMenuLevel())) {
			filterHql.append(" and menu.menuLevel = ?");
			paramList.add(new Long(searchForm.getQueryMenuLevel()));
		}
		if (searchForm.getQueryMenuState() != null && !"".equals(searchForm.getQueryMenuState())) {
			filterHql.append(" and menu.menuState = ?");
			paramList.add(new String(searchForm.getQueryMenuState()));
		}
		if (searchForm.getQueryRoleId() != null && !"".equals(searchForm.getQueryRoleId())) {
			filterHql.append(" and userProperty.role.roleId = ?");
			paramList.add(new Long(searchForm.getQueryRoleId()));
		}

		listHql.append(filterHql + "  order by menu.menuLevel desc,menu.menuOrder asc ");
		countHql.append(filterHql);

		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;

	}

}