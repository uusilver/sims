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
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.ServerInfoForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.OperDNSServer;

/**
 * A data access object (DAO) providing persistence and search support for
 * OperDNSServer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.OperDNSServer
 * @author MyEclipse Persistence Tools
 */
public class OperDNSServerDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(OperDNSServerDAO.class);
	// property constants
	public static final String SERVERIP = "serverip";
	public static final String SERVERPORT = "serverport";
	public static final String SERVERTYPE = "servertype";
	public static final String STATUS = "status";

	public void save(OperDNSServer transientInstance) {
		log.debug("saving OperDNSServer instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(OperDNSServer persistentInstance) {
		log.debug("deleting OperDNSServer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public OperDNSServer findById(java.lang.Integer id) {
		log.debug("getting OperDNSServer instance with id: " + id);
		try {
			OperDNSServer instance = (OperDNSServer) getSession().get(
					"com.xwtech.mss.pub.po.OperDNSServer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(OperDNSServer instance) {
		log.debug("finding OperDNSServer instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.po.OperDNSServer")
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
		log.debug("finding OperDNSServer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from OperDNSServer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByServerip(Object serverip) {
		return findByProperty(SERVERIP, serverip);
	}

	public List findByServerport(Object serverport) {
		return findByProperty(SERVERPORT, serverport);
	}

	public List findByServertype(Object servertype) {
		return findByProperty(SERVERTYPE, servertype);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all OperDNSServer instances");
		try {
			String queryString = "from OperDNSServer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public OperDNSServer merge(OperDNSServer detachedInstance) {
		log.debug("merging OperDNSServer instance");
		try {
			OperDNSServer result = (OperDNSServer) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(OperDNSServer instance) {
		log.debug("attaching dirty OperDNSServer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(OperDNSServer instance) {
		log.debug("attaching clean OperDNSServer instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * 根据查询条件查询服务器信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap queryDnsServerList(ServerInfoForm searchForm, String perPageCount){
		Object[] paramArray = null;
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer fromHql = new StringBuffer();
		listHql.append("select ods.dnsserverid,cb_type.text as serverType,ods.serverip,ods.serverport");
		
		fromHql.append(" from oper_dns_server ods ,code_book cb_type "
				+ " where ods.servertype = cb_type.value"
				+ " and cb_type.tag = '"+MssConstants.DNS_SERVER_TYPE+"'");
		
		listHql.append(fromHql);
		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(ods.dnsserverid) ");
		countHql.append(fromHql);

		StringBuffer filterHql = new StringBuffer();
			
//		filterHql.append(" where 1=1 ");

		//服务器类型
		if (searchForm.getQueryServerType() != null && !"".equals(searchForm.getQueryServerType())) {
			filterHql.append(" and ods.servertype = ?");
			paramList.add(new Integer (searchForm.getQueryServerType()));
		}

		//有效期起始时间
		if (searchForm.getQueryStartTime() != null && !"".equals(searchForm.getQueryStartTime())) {
//			filterHql.append(" and subStr(goodsInfo.createTime,0,8) >= ?");
			filterHql.append(" and ods.invalidtime >= ?");
			paramList.add((Object)DateUtils.formatDate(searchForm.getQueryStartTime(),"yyyy-MM-dd HH:mm:ss"));
		}

		//有效期截至时间
		if (searchForm.getQueryEndTime() != null && !"".equals(searchForm.getQueryEndTime())) {
//			filterHql.append(" and subStr(goodsInfo.createTime,0,8) <= ?");
			filterHql.append(" and ods.invalidtime <= ?");
			paramList.add((Object)DateUtils.formatDate(searchForm.getQueryEndTime(),"yyyy-MM-dd HH:mm:ss"));
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by ods.servertype asc ");
		countHql.append(filterHql);

		HashMap map = null;
		if(paramList.size()>0){
			paramArray = new Object[paramList.size()];
			for(int i=0;i<paramList.size();i++){
				paramArray[i]=paramList.get(i);
			}
		}
		
		map = queryCommonSqlResultCount(listHql.toString(), countHql.toString(),paramArray, searchForm.getCurrentPage(),perPageCount);
		return map;
	}
	
	/**
	 * 批量删除选中的服务器记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delServerInfo(String serverIds) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE OPER_DNS_SERVER TS SET TS.STATUS = ");
		sbSql.append("'" + MssConstants.STATE_D + "'"); // 状态设置为删除
		sbSql.append(" WHERE TS.DNSSERVERID IN (");
		sbSql.append(serverIds);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
	

	
	/**
	 * 查询所有服务器信息
	 * @return
	 */
	public List queryAllServer(){
		String[] paramList = new String[1];
		// 查询条数sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select operDNSServer from OperDNSServer operDNSServer ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" order by  operDNSServer.servertype ");

		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		return list;
	}

	
	/**
	 * 根据服务器id查询服务器所属服务器组
	 * @param serverId
	 * @return
	 */
//	public List queryServerGroup(String serverId){
//		Integer[] paramList = new Integer[1];
//		// 查询条数sql
//		StringBuffer listHql = new StringBuffer();
//		listHql.append("select sgMapping from ServerGroupMapping sgMapping ");
//
//		StringBuffer filterHql = new StringBuffer();
//			
//		filterHql.append(" where 1=1 ");
//
//		//服务器Id
//		if (serverId != null && !"".equals(serverId)) {
//			filterHql.append(" and sgMapping.serverid = ?");
//			paramList[0] = new Integer(serverId);
//		}
//
//		
//		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()),paramList);
//		return list;
//	}
	
	/**
	 * 查询不在该服务器分组的所有服务器
	 * @param groupId
	 * @param isLoadGroupServer
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public List<Object[]> queryUnGroupedServer(String groupId,Boolean isLoadGroupServer) {
//		List<Object[]> list = null;
//		Object[] paramList = new Object[1];
//		// 查询列表sql
//		StringBuffer listHql = new StringBuffer();
//		StringBuffer fromHql = new StringBuffer();
//		listHql.append("select ts.serverid as serverId,"
//				+"CONCAT(ts.serverip,' - ',cb_type.text,' - [',c.countryname,'/',prov.provincename,'/',t.cityname,']',' - ',r.regionname,"
//				+ "' - ',CASE WHEN sGroup.servergroupname IS NULL THEN '未分组' ELSE sGroup.servergroupname END) as serverName ");
//		
//		fromHql.append(" from transit_server ts left join server_group_mapping sgMapping on ts.serverid=sgMapping.serverid"
//				+ " left join server_group sGroup on sGroup.servergroupid = sgMapping.servergroupid,"
//				+ " code_book cb_type,code_book cb_status,country c,province prov,city t,region r "
//				+ " where ts.countryid = c.countryid"
//				+ " and ts.provinceid = prov.provinceid"
//				+ " and ts.cityid = t.cityid"
//				+ " and ts.regionid = r.regionid"
//				+ " and ts.servertype = cb_type.value"
//				+ " and cb_type.tag = '"+MssConstants.SERVER_TYPE+"'"
//				+ " and ts.serverstatus = cb_status.value"
//				+ " and cb_status.tag = '"+MssConstants.SERVER_STATUS+"'");
//		
//		listHql.append(fromHql);
//
//		StringBuffer filterHql = new StringBuffer();
//			
//
//		
//		if (groupId != null && !"".equals(groupId)) {
//			//查询该分组中的服务器记录
//			if(isLoadGroupServer){
//				filterHql.append(" and sGroup.servergroupid = ? ");
//				paramList[0]=new Integer (groupId);
//			}
//			//查询未分组和不属于该分组的服务器
//			else{
//				filterHql.append(" and (sGroup.servergroupname is NULL or sGroup.servergroupid != ?)");
//				paramList[0]=new Integer (groupId);
//			}
//		}
//		
//		//按服务器类别和名称排序
//		listHql.append(filterHql + "  order by ts.serverid asc ");
//		log.info("SQL:"+listHql.toString());
//		if(paramList[0]==null){
//			list = FrameworkApplication.baseJdbcDAO.queryForList(listHql.toString());
//		}else{
//			list = FrameworkApplication.baseJdbcDAO.queryForList(listHql.toString(),paramList);
//		}
//		
//		return list;
//	}
	
}