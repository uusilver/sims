package com.xwtech.mss.pub.dao.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.ServerGroupForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.ServerGroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServerGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ServerGroup
 * @author MyEclipse Persistence Tools
 */
public class ServerGroupDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ServerGroupDAO.class);
	// property constants
	public static final String SERVERGROUPNAME = "servergroupname";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void save(ServerGroup transientInstance) {
		log.debug("saving ServerGroup instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ServerGroup persistentInstance) {
		log.debug("deleting ServerGroup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ServerGroup findById(java.lang.Integer id) {
		log.debug("getting ServerGroup instance with id: " + id);
		try {
			ServerGroup instance = (ServerGroup) getSession().get(
					"com.xwtech.mss.pub.po.ServerGroup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServerGroup instance) {
		log.debug("finding ServerGroup instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ServerGroup")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ServerGroup instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ServerGroup as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServergroupname(Object servergroupname) {
		return findByProperty(SERVERGROUPNAME, servergroupname);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ServerGroup instances");
		try {
			String queryString = "from ServerGroup";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServerGroup merge(ServerGroup detachedInstance) {
		log.debug("merging ServerGroup instance");
		try {
			ServerGroup result = (ServerGroup) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServerGroup instance) {
		log.debug("attaching dirty ServerGroup instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServerGroup instance) {
		log.debug("attaching clean ServerGroup instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 根据查询条件查询服务器分组信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap<?, ?> queryServerGroupList(ServerGroupForm searchForm, String perPageCount){
		List<String> paramList = new ArrayList<String>();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select serverGroup from ServerGroup serverGroup where 1=1 ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(serverGroup.servergroupid) from  ServerGroup serverGroup where 1=1 ");

		StringBuffer filterHql = new StringBuffer();
			
		//服务器组名
		if (searchForm.getQueryServerGroupName() != null && !"".equals(searchForm.getQueryServerGroupName())) {
			filterHql.append(" and serverGroup.servergroupname = ?");
			paramList.add(searchForm.getQueryServerGroupName());
		}
		
		//服务器组状态
		if (searchForm.getQueryStatus() != null && !"".equals(searchForm.getQueryStatus())) {
			filterHql.append(" and serverGroup.status = ?");
			paramList.add(searchForm.getQueryStatus());
		}else{
			filterHql.append(" and serverGroup.status in ('A','U')");
		}
		
		//服务器组名
		if (searchForm.getQueryNote() != null && !"".equals(searchForm.getQueryNote())) {
			filterHql.append(" and serverGroup.note like ?");
			paramList.add("%"+searchForm.getQueryNote()+"%");
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by serverGroup.servergroupname asc ");
		countHql.append(filterHql);

		HashMap<?, ?> map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	/**
	 * 批量删除选中的服务器分组记录（逻辑删除）
	 * @param groupIdStr
	 * @return
	 */
	public int delServerGroup(String groupIdStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE SERVER_GROUP SG SET SG.STATUS = ");
		sbSql.append("'" + MssConstants.STATE_D + "'"); // 状态设置为删除
		sbSql.append(" WHERE SG.SERVERGROUPID IN (");
		sbSql.append(groupIdStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
}