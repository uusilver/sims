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
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.Client;

/**
 * A data access object (DAO) providing persistence and search support for
 * Client entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.Client
 * @author MyEclipse Persistence Tools
 */
public class ClientDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ClientDAO.class);
	// property constants
	public static final String KEYID = "keyid";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String MODIFYPASS = "modifypass";
	public static final String AUTHENTICATIONTYPE = "authenticationtype";
	public static final String DISABLE = "disable";
	public static final String USERTYPE = "usertype";
	public static final String TRUENAME = "truename";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void save(Client transientInstance) {
		log.debug("saving Client instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Client persistentInstance) {
		log.debug("deleting Client instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Client findById(java.lang.Integer id) {
		log.debug("getting Client instance with id: " + id);
		try {
			Client instance = (Client) getSession().get(
					"com.xwtech.mss.pub.po.Client", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Client instance) {
		log.debug("finding Client instance by example");
		try {
			List results = getSession()
					.createCriteria("com.xwtech.mss.pub.po.Client")
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
		log.debug("finding Client instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Client as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByKeyid(Object keyid) {
		return findByProperty(KEYID, keyid);
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByModifypass(Object modifypass) {
		return findByProperty(MODIFYPASS, modifypass);
	}

	public List findByAuthenticationtype(Object authenticationtype) {
		return findByProperty(AUTHENTICATIONTYPE, authenticationtype);
	}

	public List findByDisable(Object disable) {
		return findByProperty(DISABLE, disable);
	}

	public List findByUsertype(Object usertype) {
		return findByProperty(USERTYPE, usertype);
	}

	public List findByTruename(Object truename) {
		return findByProperty(TRUENAME, truename);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Client instances");
		try {
			String queryString = "from Client";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Client merge(Client detachedInstance) {
		log.debug("merging Client instance");
		try {
			Client result = (Client) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Client instance) {
		log.debug("attaching dirty Client instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Client instance) {
		log.debug("attaching clean Client instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
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
		listHql.append("select clientInfo from Client clientInfo ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(clientInfo.clientid) from Client clientInfo ");

		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//客户名称
		if (searchForm.getQueryClientName() != null && !"".equals(searchForm.getQueryClientName())) {
			filterHql.append(" and clientInfo.truename like ?");
			paramList.add((Object) ("%" + searchForm.getQueryClientName() + "%"));
		}
		//客户状态
		if (searchForm.getQueryClientState() != null && !"".equals(searchForm.getQueryClientState())) {
			filterHql.append(" and clientInfo.status = ?");
			paramList.add(searchForm.getQueryClientState());
		}else{
			filterHql.append(" and clientInfo.status in ('A','U')");
		}

		//客户类型
		if (searchForm.getQueryClientType() != null && !"".equals(searchForm.getQueryClientType())) {
			filterHql.append(" and clientInfo.usertype = ?");
			paramList.add(searchForm.getQueryClientType());
		}

		//客户是否被禁用 0 – 未禁用，1 – 已禁用
		if (searchForm.getQueryDisable() != null && !"".equals(searchForm.getQueryDisable())) {
			filterHql.append(" and clientInfo.disable = ?");
			paramList.add(searchForm.getQueryDisable());
		}

		//按客户类别和名称排序
		listHql.append(filterHql + "  order by clientInfo.usertype ,clientInfo.truename asc ");
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
		StringBuffer sbSql = new StringBuffer("UPDATE client fr SET fr.status = ");
		sbSql.append("'" + SpmsConstants.STATE_D + "'"); 
		sbSql.append(" WHERE fr.clientid IN (");
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
		listHql.append("select clientInfo from Client clientInfo ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//物品Id
		if (clientNumStr != null && !"".equals(clientNumStr)) {
			filterHql.append(" and clientInfo.clientid in ("+clientNumStr+")");
		}

		
		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		return list;
	}
}