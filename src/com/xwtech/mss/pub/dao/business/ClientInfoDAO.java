package com.xwtech.mss.pub.dao.business;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.ClientInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.ClientInfo
 * @author MyEclipse Persistence Tools
 */

public class ClientInfoDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ClientInfoDAO.class);
	// property constants
	public static final String CLIENT_NAME = "clientName";
	public static final String CLIENT_TEL = "clientTel";
	public static final String CLIENT_ADDR = "clientAddr";
	public static final String ZIP_CODE = "zipCode";
	public static final String CLIENT_STATE = "clientState";

	protected void initDao() {
		// do nothing
	}

	public void save(ClientInfo transientInstance) {
		log.debug("saving ClientInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientInfo persistentInstance) {
		log.debug("deleting ClientInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientInfo findById(java.lang.Long id) {
		log.debug("getting ClientInfo instance with id: " + id);
		try {
			ClientInfo instance = (ClientInfo) getHibernateTemplate().get(
					"com.xwtech.mss.pub.po.ClientInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientInfo instance) {
		log.debug("finding ClientInfo instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ClientInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ClientInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClientName(Object clientName) {
		return findByProperty(CLIENT_NAME, clientName);
	}

	public List findByClientTel(Object clientTel) {
		return findByProperty(CLIENT_TEL, clientTel);
	}

	public List findByClientAddr(Object clientAddr) {
		return findByProperty(CLIENT_ADDR, clientAddr);
	}

	public List findByZipCode(Object zipCode) {
		return findByProperty(ZIP_CODE, zipCode);
	}

	public List findByClientState(Object clientState) {
		return findByProperty(CLIENT_STATE, clientState);
	}

	public List findAll() {
		log.debug("finding all ClientInfo instances");
		try {
			String queryString = "from ClientInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientInfo merge(ClientInfo detachedInstance) {
		log.debug("merging ClientInfo instance");
		try {
			ClientInfo result = (ClientInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientInfo instance) {
		log.debug("attaching dirty ClientInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientInfo instance) {
		log.debug("attaching clean ClientInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ClientInfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ClientInfoDAO) ctx.getBean("ClientInfoDAO");
	}
	
	/**
	 * 根据查询条件查询客户信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryClientInfoList(ClientInfoForm searchForm, String perPageCount){
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select clientInfo from ClientInfo clientInfo ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(clientInfo.clientNum) from ClientInfo clientInfo ");

		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//客户名称
		if (searchForm.getQueryClientName() != null && !"".equals(searchForm.getQueryClientName())) {
			filterHql.append(" and clientInfo.clientName like ?");
			paramList.add((Object) ("%" + searchForm.getQueryClientName() + "%"));
		}
		//客户昵称
		if (searchForm.getQueryClientNick() != null && !"".equals(searchForm.getQueryClientNick())) {
			filterHql.append(" and clientInfo.clientNick like ?");
			paramList.add((Object) ("%" + searchForm.getQueryClientNick() + "%"));
		}
		//客户状态
		if (searchForm.getQueryClientState() != null && !"".equals(searchForm.getQueryClientState())) {
			filterHql.append(" and clientInfo.clientState = ?");
			paramList.add(searchForm.getQueryClientState());
		}else{
			filterHql.append(" and clientInfo.clientState in ('A','U')");
		}

		//客户类型
		if (searchForm.getQueryClientType() != null && !"".equals(searchForm.getQueryClientType())) {
			filterHql.append(" and clientInfo.clientType = ?");
			paramList.add(searchForm.getQueryClientType());
		}

		//按客户类别和名称排序
		listHql.append(filterHql + "  order by clientInfo.clientType ,clientInfo.clientName asc ");
		countHql.append(filterHql);

		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	
	/**
	 * 批量删除选中的客户记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delClientInfo(String clientNumStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE client_info fr SET fr.client_state = ");
		sbSql.append("'" + SpmsConstants.STATE_D + "'"); 
		sbSql.append(" WHERE fr.client_num IN (");
		sbSql.append(clientNumStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
	
	/**
	 * 根据客户id查询客户信息
	 * @param clientNumStr
	 * @return
	 */
	public List queryClientsName(String clientNumStr){
		// 查询条数sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select clientInfo from ClientInfo clientInfo ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//物品Id
		if (clientNumStr != null && !"".equals(clientNumStr)) {
			filterHql.append(" and clientInfo.clientNum in ("+clientNumStr+")");
		}

		
		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		return list;
	}
}