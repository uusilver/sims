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
import com.xwtech.mss.formBean.BaseForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.Role;

/**
 * Data access object (DAO) for domain model class Role.
 * 
 * @see com.xwtech.mss.pub.dao.system.Role
 * @author MyEclipse - Hibernate Tools
 */
public class RoleDAO extends BaseDao {

	private static final Log log = LogFactory.getLog(RoleDAO.class);

	// property constants
	public static final String ROLE_NAME = "roleName";

	public static final String COMMENT = "comment";

	public static final String STATE = "state";

	protected void initDao() {
		// do nothing
	}

	public void save(Role transientInstance) {
		log.debug("saving Role instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(Role transientInstance) {
		log.debug("update Role instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(Role transientInstance) {
		log.debug("save or update Role instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save or update successful");
		} catch (RuntimeException re) {
			log.error("save or update failed", re);
			throw re;
		}
	}

	public void delete(Role persistentInstance) {
		log.debug("deleting Role instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Role findById(java.lang.Long id) {
		log.debug("getting Role instance with id: " + id);
		try {
			Role instance = (Role) getHibernateTemplate().get("com.xwtech.mss.pub.po.Role", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Role instance) {
		log.debug("finding Role instance by example");
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
		log.debug("finding Role instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Role as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRoleName(Object roleName) {
		return findByProperty(ROLE_NAME, roleName);
	}

	public List findByComment(Object comment) {
		return findByProperty(COMMENT, comment);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public Role merge(Role detachedInstance) {
		log.debug("merging Role instance");
		try {
			Role result = (Role) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Role instance) {
		log.debug("attaching dirty Role instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Role instance) {
		log.debug("attaching clean Role instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RoleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RoleDAO) ctx.getBean("RoleDAO");
	}

	/**
	 * 查询角色列表
	 * 
	 * @param roleForm
	 * @return
	 * @author weizhen
	 */
	public HashMap queryRoleList(BaseForm roleForm) {
		// 初始化查询HQL
		StringBuffer sbListHql = new StringBuffer();
		String sbCountHql = null;

		// 查询参数
		List listParamers = new ArrayList();
		String roleName = roleForm.getRoleName();
		String roleState = roleForm.getRoleState();

		// 拼装HQL
		sbListHql.append("from Role as role where 1=1");

		// 角色名称
		if (!StringUtils.isEmpty(roleName)) {
			sbListHql.append(" and role.roleName like ? ");
			listParamers.add("%" + roleName + "%");
		}
		// 角色状态
		if (!StringUtils.isEmpty(roleState)) {
			sbListHql.append(" and role.state = ?");
			listParamers.add(roleState);
		}

		sbCountHql = "select count(*) " + sbListHql.substring(sbListHql.indexOf("from"));
		return queryResultCount(sbListHql.toString(), sbCountHql, listParamers, roleForm.getCurrentPage(), null);

	}

	/**
	 * 保存角色信息
	 * 
	 * @param strSql
	 * @param param
	 * @author weizhen
	 */
	public void save(String strSql, Object[] param) {
		log.debug("saving Role: " + strSql);
		try {
			// 保存
			FrameworkApplication.baseJdbcDAO.update(strSql, param);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * 删除角色信息
	 * 
	 * @param roleIdStr
	 * @return
	 * @author weizhen
	 */
	public int delRoleInfo(String roleIdStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE frame_role fr SET fr.state = ");
		sbSql.append("'" + SpmsConstants.STATE_U + "'"); // 角色状态设置为无效
		sbSql.append(" WHERE fr.role_id IN (");
		sbSql.append(roleIdStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
}