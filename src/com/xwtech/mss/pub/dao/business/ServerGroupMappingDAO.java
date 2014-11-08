package com.xwtech.mss.pub.dao.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.ServerGroupMapping;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServerGroupMapping entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ServerGroupMapping
 * @author MyEclipse Persistence Tools
 */
public class ServerGroupMappingDAO extends BaseDao {
	private static final Log log = LogFactory
			.getLog(ServerGroupMappingDAO.class);
	// property constants
	public static final String SERVERID = "serverid";
	public static final String SERVERGROUPID = "servergroupid";

	public void save(ServerGroupMapping transientInstance) {
		log.debug("saving ServerGroupMapping instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ServerGroupMapping persistentInstance) {
		log.debug("deleting ServerGroupMapping instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ServerGroupMapping findById(java.lang.Integer id) {
		log.debug("getting ServerGroupMapping instance with id: " + id);
		try {
			ServerGroupMapping instance = (ServerGroupMapping) getSession()
					.get("com.xwtech.mss.pub.dao.business.ServerGroupMapping",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServerGroupMapping instance) {
		log.debug("finding ServerGroupMapping instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.dao.business.ServerGroupMapping")
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
		log.debug("finding ServerGroupMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ServerGroupMapping as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServerid(Object serverid) {
		return findByProperty(SERVERID, serverid);
	}

	public List findByServergroupid(Object servergroupid) {
		return findByProperty(SERVERGROUPID, servergroupid);
	}

	public List findAll() {
		log.debug("finding all ServerGroupMapping instances");
		try {
			String queryString = "from ServerGroupMapping";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServerGroupMapping merge(ServerGroupMapping detachedInstance) {
		log.debug("merging ServerGroupMapping instance");
		try {
			ServerGroupMapping result = (ServerGroupMapping) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServerGroupMapping instance) {
		log.debug("attaching dirty ServerGroupMapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServerGroupMapping instance) {
		log.debug("attaching clean ServerGroupMapping instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 保存服务器和分组的对应关系
	 * @param serverGroupId
	 * @param serverIds,以‘,’隔开
	 */
	public int saveServerGroupLink(Integer serverGroupId, String serverIds) {
		if (StringUtils.isEmpty(serverIds)) {
			return 0;
		}

		try {
			// 拼装SQL
			StringBuffer sbSql = new StringBuffer("INSERT INTO SERVER_GROUP_MAPPING ( SERVERID, SERVERGROUPID)");
			sbSql.append(" SELECT t.SERVERID AS SERVER_ID," + serverGroupId + " AS  GROUP_ID  FROM");
			// 服务器ID
			sbSql.append(" (SELECT TS.SERVERID from TRANSIT_SERVER TS WHERE TS.SERVERID IN (" + serverIds + ")) t");

			log.info(sbSql.toString());

			return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
		} catch (RuntimeException re) {
			log.error("保存服务器和分组的对应关系", re);
			throw re;
		}

	}
	
	/**
	 * 根据服务器组ID查询该组中服务器信息,用于服务器分组选择列表展示
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List<?> queryServerTextByGroupId(String groupId){
		List<?> list = null;
		Object[] paramList = new Object[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer filterHql = new StringBuffer();
		listHql.append("select transitServer.serverid as serverId,"
				+"CONCAT(transitServer.serverip,'-',cb_type.text,'-[',c.countryname,'/',prov.provincename,'/',t.cityname,']','-',r.regionname) as serverName"
				+" from TransitServer transitServer,ServerGroupMapping sgMapping,ServerGroup sGroup,"
				+"Country c,Province prov,City t,Region r,CodeBook cb_type"
				+" where transitServer.countryid = c.countryid"
				+" and transitServer.provinceid = prov.provinceid"
				+" and transitServer.cityid = t.cityid"
				+" and transitServer.serverid = sgMapping.serverid"
				+" and sgMapping.servergroupid = sGroup.servergroupid"
				+" and transitServer.regionid = r.regionid"
				+" and transitServer.servertype = cb_type.value"
				+" and cb_type.tag = '"+MssConstants.SERVER_TYPE+"'");


		//服务器服务区域
		if (groupId != null && !"".equals(groupId)) {
			filterHql.append(" and sGroup.servergroupid = ?");
			paramList[0]=new Integer (groupId);
		}
		
		if(paramList[0]==null){
			list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		}else{
			list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()),paramList);
		}
		
		return list;
	}
	
	/**
	 * 根据Id删除记录
	 * 
	 * @param idStr,以‘,’隔开
	 */
	public int delMappingRecords(String serverIdStr,String groupId) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete sgm from server_group_mapping sgm where sgm.serverid in (");
		sql.append(serverIdStr.trim());
		sql.append(" ) ");
		sql.append(" or sgm.servergroupid = "+groupId);
		result = executeCommonSql(sql.toString());
		return result;
	}
}