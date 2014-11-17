package com.tmind.mss.pub.dao.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.tmind.framework.pub.dao.BaseDao;
import com.tmind.framework.pub.utils.StringUtils;
import com.tmind.framework.pub.web.FrameworkApplication;
import com.tmind.mss.pub.constants.MssConstants;
import com.tmind.mss.pub.po.ClientGroupMapping;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientGroupMapping entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.tmind.mss.pub.dao.business.ClientGroupMapping
 * @author MyEclipse Persistence Tools
 */
public class ClientGroupMappingDAO extends BaseDao {
	private static final Log log = LogFactory
			.getLog(ClientGroupMappingDAO.class);
	// property constants
	public static final String CLIENTID = "clientid";
	public static final String CLIENTGROUPID = "clientgroupid";

	public void save(ClientGroupMapping transientInstance) {
		log.debug("saving ClientGroupMapping instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientGroupMapping persistentInstance) {
		log.debug("deleting ClientGroupMapping instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientGroupMapping findById(java.lang.Integer id) {
		log.debug("getting ClientGroupMapping instance with id: " + id);
		try {
			ClientGroupMapping instance = (ClientGroupMapping) getSession()
					.get("com.tmind.mss.pub.dao.business.ClientGroupMapping",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientGroupMapping instance) {
		log.debug("finding ClientGroupMapping instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.tmind.mss.pub.dao.business.ClientGroupMapping")
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
		log.debug("finding ClientGroupMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClientGroupMapping as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClientid(Object clientid) {
		return findByProperty(CLIENTID, clientid);
	}

	public List findByClientgroupid(Object clientgroupid) {
		return findByProperty(CLIENTGROUPID, clientgroupid);
	}

	public List findAll() {
		log.debug("finding all ClientGroupMapping instances");
		try {
			String queryString = "from ClientGroupMapping";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientGroupMapping merge(ClientGroupMapping detachedInstance) {
		log.debug("merging ClientGroupMapping instance");
		try {
			ClientGroupMapping result = (ClientGroupMapping) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientGroupMapping instance) {
		log.debug("attaching dirty ClientGroupMapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientGroupMapping instance) {
		log.debug("attaching clean ClientGroupMapping instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 保存客户端和分组的对应关系
	 * @param clientGroupId
	 * @param clientIds,以‘,’隔开
	 */
	public int saveClientGroupLink(Integer clientGroupId, String clientIds) {
		if (StringUtils.isEmpty(clientIds)) {
			return 0;
		}

		try {
			// 拼装SQL
			StringBuffer sbSql = new StringBuffer("INSERT INTO CLIENT_GROUP_MAPPING ( CLIENTID, CLIENTGROUPID)");
			sbSql.append(" SELECT t.CLIENTID AS CLIENT_ID," + clientGroupId + " AS  GROUP_ID  FROM");
			// 客户端ID
			sbSql.append(" (SELECT C.CLIENTID from CLIENT C WHERE C.CLIENTID IN (" + clientIds + ")) t");

			log.info(sbSql.toString());

			return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
		} catch (RuntimeException re) {
			log.error("保存客户端和分组的对应关系", re);
			throw re;
		}

	}
	
	/**
	 * 根据客户端组ID查询该组中客户端信息,用于客户端分组选择列表展示
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List<?> queryClientTextByGroupId(String groupId){
		List<?> list = null;
		Object[] paramList = new Object[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer filterHql = new StringBuffer();
		listHql.append("select client.clientid as clientId,"
				+"CONCAT(client.username,' - [',cb_userType.text,' / ',cb_authType.text,'/',cb_disable.text,' ]') as clientName"
				+" from Client client,ClientGroupMapping cgMapping,ClientGroup cGroup,"
				+"CodeBook cb_modPass,CodeBook cb_authType,CodeBook cb_disable,CodeBook cb_userType"
				+" where client.clientid = cgMapping.clientid"
				+" and cgMapping.clientgroupid = cGroup.clientgroupid"
				+" and client.modifypass = cb_modPass.value"
				+" and cb_modPass.tag = '"+MssConstants.MODIFY_PASS+"'"
				+" and client.authenticationtype = cb_authType.value"
				+" and cb_authType.tag = '"+MssConstants.AUTH_TYPE+"'"
				+" and client.disable = cb_disable.value"
				+" and cb_disable.tag = '"+MssConstants.DISABLE_FLAG+"'"
				+" and client.usertype = cb_userType.value"
				+" and cb_userType.tag = '"+MssConstants.USER_TYPE+"'");


		//客户端服务区域
		if (groupId != null && !"".equals(groupId)) {
			filterHql.append(" and cGroup.clientgroupid = ?");
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
	public int delMappingRecords(String clientGroupIdStr) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete cgm from client_group_mapping cgm where cgm.clientgroupid in (");
		sql.append(clientGroupIdStr.lastIndexOf(",") > 0 ? (clientGroupIdStr.substring(0, clientGroupIdStr.lastIndexOf(","))) : clientGroupIdStr.trim());
		sql.append(" ) ");
		result = executeCommonSql(sql.toString());
		return result;
	}
}