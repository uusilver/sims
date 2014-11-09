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
import com.xwtech.mss.formBean.ClientGroupForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.ClientGroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * ClientGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.ClientGroup
 * @author MyEclipse Persistence Tools
 */
public class ClientGroupDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ClientGroupDAO.class);
	// property constants
	public static final String CLIENTGROUPNAME = "clientgroupname";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void saveOrUpdate(ClientGroup transientInstance) {
		log.debug("saving ClientGroup instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClientGroup persistentInstance) {
		log.debug("deleting ClientGroup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClientGroup findById(java.lang.Integer id) {
		log.debug("getting ClientGroup instance with id: " + id);
		try {
			ClientGroup instance = (ClientGroup) getSession().get(
					"com.xwtech.mss.pub.po.ClientGroup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClientGroup instance) {
		log.debug("finding ClientGroup instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.po.ClientGroup")
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
		log.debug("finding ClientGroup instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ClientGroup as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClientgroupname(Object clientgroupname) {
		return findByProperty(CLIENTGROUPNAME, clientgroupname);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ClientGroup instances");
		try {
			String queryString = "from ClientGroup";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClientGroup merge(ClientGroup detachedInstance) {
		log.debug("merging ClientGroup instance");
		try {
			ClientGroup result = (ClientGroup) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClientGroup instance) {
		log.debug("attaching dirty ClientGroup instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClientGroup instance) {
		log.debug("attaching clean ClientGroup instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 根据查询条件查询客户端分组信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryClientGroupList(ClientGroupForm searchForm, String perPageCount){
		List<String> paramList = new ArrayList<String>();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select clientGroup from ClientGroup clientGroup where 1=1 ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(clientGroup.clientgroupid) from  ClientGroup clientGroup where 1=1 ");

		StringBuffer filterHql = new StringBuffer();
			
		//服务器组名
		if (searchForm.getQueryClientGroupName() != null && !"".equals(searchForm.getQueryClientGroupName())) {
			filterHql.append(" and clientGroup.clientgroupname like ?");
			paramList.add("%"+searchForm.getQueryClientGroupName()+"%");
		}
		
		//服务器组状态
		if (searchForm.getQueryStatus() != null && !"".equals(searchForm.getQueryStatus())) {
			filterHql.append(" and clientGroup.status = ?");
			paramList.add(searchForm.getQueryStatus());
		}else{
			filterHql.append(" and clientGroup.status in ('A','U')");
		}
		
		//服务器组名
		if (searchForm.getQueryNote() != null && !"".equals(searchForm.getQueryNote())) {
			filterHql.append(" and clientGroup.note like ?");
			paramList.add("%"+searchForm.getQueryNote()+"%");
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by clientGroup.clientgroupname asc ");
		countHql.append(filterHql);

		HashMap<?, ?> map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	/**
	 * 批量删除选中的客户端分组记录（物除删除）
	 * @param groupIdStr
	 * @return
	 */
	public int delClientGroup(String groupIdStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("DELETE CG FROM CLIENT_GROUP CG ");
		sbSql.append(" WHERE CG.CLIENTGROUPID IN (");
		sbSql.append(groupIdStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
	
	/**
	 * 根据客户端组ID查询该组中的客户端信息
	 * @param groupId
	 * @return
	 */
	public List<?> queryClientsByGroupId(String groupId){
		
		List<?> resultList = null;
		
		List<Integer> paramList = new ArrayList<Integer>();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select client from Client client,ClientGroup cGroup,ClientGroupMapping cgMapping "
				+ " where client.clientid = cgMapping.clientid"
				+ " and cGroup.clientgroupid = cgMapping.clientgroupid");

		StringBuffer filterHql = new StringBuffer();
			
		//客户端组ID
		if (groupId != null && !"".equals(groupId)) {
			filterHql.append(" and cgMapping.clientgroupid = ?");
			paramList.add(new Integer(groupId));
		}
		
		if(paramList.size()>0){
			Object[] paramObj = new Object[paramList.size()];
			for(int i=0;i<paramList.size();i++){
				paramObj[i] = paramList.get(i);
			}
			resultList = getHibernateTemplate().find(listHql.append(filterHql).toString(),paramObj);
		}else{
			resultList = getHibernateTemplate().find(listHql.append(filterHql).toString());
		}
		return resultList;
	}
	
}