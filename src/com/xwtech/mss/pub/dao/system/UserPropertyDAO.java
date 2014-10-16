package com.xwtech.mss.pub.dao.system;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.pub.po.UserProperty;

/**
 * Data access object (DAO) for domain model class UserProperty.
 * 
 * @see com.xwtech.mss.pub.po.UserProperty
 * @author MyEclipse - Hibernate Tools
 */
public class UserPropertyDAO extends BaseDao {

	private static final Log log = LogFactory.getLog(UserPropertyDAO.class);

	// property constants
	public static final String STATE = "state";

	protected void initDao() {
		// do nothing
	}

	public void save(UserProperty transientInstance) {
		log.debug("saving UserProperty instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserProperty persistentInstance) {
		log.debug("deleting UserProperty instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserProperty findById(java.lang.Long id) {
		log.debug("getting UserProperty instance with id: " + id);
		try {
			UserProperty instance = (UserProperty) getHibernateTemplate().get("com.xwtech.mss.pub.po.UserProperty", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserProperty instance) {
		log.debug("finding UserProperty instance by example");
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
		log.debug("finding UserProperty instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from UserProperty as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public UserProperty merge(UserProperty detachedInstance) {
		log.debug("merging UserProperty instance");
		try {
			UserProperty result = (UserProperty) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserProperty instance) {
		log.debug("attaching dirty UserProperty instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserProperty instance) {
		log.debug("attaching clean UserProperty instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserPropertyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserPropertyDAO) ctx.getBean("UserPropertyDAO");
	}

	/**
	 * 根据Id删除记录
	 * 
	 * @param idStr,以‘,’隔开
	 */
	public void delRecords(String idStr) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from mss_user_property t where t.user_id in (");
		sql.append(idStr.lastIndexOf(",") > 0 ? (idStr.substring(0, idStr.lastIndexOf(","))) : idStr.trim());
		sql.append(" ) ");
		executeCommonSql(sql.toString());
	}

	/**
	 * 根据角色ID删除角色可访问URL信息
	 * 
	 * @param strRoleIds,以‘,’隔开
	 * @author weizhen
	 */
	public void delUserPropertyByRoleIds(String strRoleIds) {
		if (StringUtils.isEmpty(strRoleIds)) {
			return;
		}

		try {
			// 拼装SQL
			StringBuffer sbSql = new StringBuffer();
			sbSql.append("delete from frame_user_property t where t.role_id in (");
			sbSql.append(strRoleIds);
			sbSql.append(")");

			log.info("del UserProperty By RoleIds successed:" + sbSql.toString());
			FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
		} catch (RuntimeException re) {
			log.error("del UserProperty By RoleIds failed", re);
			throw re;
		}
	}

	/**
	 * 保存角色可访问URL信息
	 * 
	 * @param idStr,以‘,’隔开
	 * @author weizhen
	 */
	public int save(Long roleId, String strMenuIds) {
		if (StringUtils.isEmpty(strMenuIds)) {
			return 0;
		}

		try {
			// 拼装SQL
			StringBuffer sbSql = new StringBuffer("INSERT INTO frame_user_property (property_id, role_id, menu_id)");
			sbSql.append(" SELECT frame_seq_property.NEXTVAL, " + roleId + " AS  role_id, t.menu_id FROM");
			// 二级菜单
			sbSql.append(" (SELECT fm.menu_id from frame_menu fm WHERE fm.menu_id IN (" + strMenuIds + ")");
			sbSql.append(" UNION ");
			// 一级菜单
			sbSql.append(" SELECT DISTINCT fm.up_menu_id from frame_menu fm WHERE fm.menu_id IN (" + strMenuIds
					+ ")) t");

			log.info(sbSql.toString());

			return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}

	}

}