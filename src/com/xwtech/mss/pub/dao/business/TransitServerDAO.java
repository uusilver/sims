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
import com.xwtech.mss.pub.po.TransitServer;

/**
 * A data access object (DAO) providing persistence and search support for
 * TransitServer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.business.TransitServer
 * @author MyEclipse Persistence Tools
 */
public class TransitServerDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TransitServerDAO.class);
	// property constants
	public static final String SERVERIP = "serverip";
	public static final String COUNTRYID = "countryid";
	public static final String PROVINCEID = "provinceid";
	public static final String CITYID = "cityid";
	public static final String SERVERTYPE = "servertype";
	public static final String SERVERSTATUS = "serverstatus";
	public static final String LIMITATION = "limitation";
	public static final String REGIONID = "regionid";
	public static final String NOTE = "note";
	public static final String STATUS = "status";

	public void save(TransitServer transientInstance) {
		log.debug("saving TransitServer instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TransitServer persistentInstance) {
		log.debug("deleting TransitServer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TransitServer findById(java.lang.Integer id) {
		log.debug("getting TransitServer instance with id: " + id);
		try {
			TransitServer instance = (TransitServer) getSession().get(
					"com.xwtech.mss.pub.po.TransitServer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TransitServer instance) {
		log.debug("finding TransitServer instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.xwtech.mss.pub.po.TransitServer")
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
		log.debug("finding TransitServer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TransitServer as model where model."
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

	public List findByCountryid(Object countryid) {
		return findByProperty(COUNTRYID, countryid);
	}

	public List findByProvinceid(Object provinceid) {
		return findByProperty(PROVINCEID, provinceid);
	}

	public List findByCityid(Object cityid) {
		return findByProperty(CITYID, cityid);
	}

	public List findByServertype(Object servertype) {
		return findByProperty(SERVERTYPE, servertype);
	}

	public List findByServerstatus(Object serverstatus) {
		return findByProperty(SERVERSTATUS, serverstatus);
	}

	public List findByLimitation(Object limitation) {
		return findByProperty(LIMITATION, limitation);
	}

	public List findByRegionid(Object regionid) {
		return findByProperty(REGIONID, regionid);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all TransitServer instances");
		try {
			String queryString = "from TransitServer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TransitServer merge(TransitServer detachedInstance) {
		log.debug("merging TransitServer instance");
		try {
			TransitServer result = (TransitServer) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TransitServer instance) {
		log.debug("attaching dirty TransitServer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TransitServer instance) {
		log.debug("attaching clean TransitServer instance");
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
	public HashMap queryServerInfoList(ServerInfoForm searchForm, String perPageCount){
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer fromHql = new StringBuffer();
		listHql.append("select ts.serverid,cb_type.text,ts.serverip,"
				+" sGroup.servergroupname,region.regionname,country.countryname,prov.provincename,city.cityname,cb_status.text ");
		
		fromHql.append(" from TransitServer ts left join ts.serverid ServerGroupMapping sgMapping left join sgMapping.servergroupid ServerGroup sGroup ,"
				+ " CodeBook cb_type,CodeBook cb_status,Country country,Province prov,City city,Region region "
				+ " where ts.countryid = country.countryid"
				+ " and ts.provinceid = prov.provinceid"
				+ " and ts.cityid = city.cityid"
				+ " and ts.serverid = sgMapping.serverid"
				+ " and sgMapping.servergroupid = sGroup.servergroupid"
				+ " and ts.regionid = region.regionid"
				+ " and ts.servertype = cb_type.value"
				+ " and cb_type.tag = '"+MssConstants.SERVER_TYPE+"'"
				+ " and ts.serverstatus = cb_status.value"
				+ " and cb_status.tag = '"+MssConstants.SERVER_STATUS+"'");
		
		listHql.append(fromHql);
		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(ts.serverid) ");
		countHql.append(fromHql);

		StringBuffer filterHql = new StringBuffer();
			
//		filterHql.append(" where 1=1 ");

		//服务器类型
		if (searchForm.getQueryServerType() != null && !"".equals(searchForm.getQueryServerType())) {
			filterHql.append(" and ts.servertype = ?");
			paramList.add(new Integer (searchForm.getQueryServerType()));
		}
		//服务器状态
		if (searchForm.getQueryServerStatus() != null && !"".equals(searchForm.getQueryServerStatus())) {
			filterHql.append(" and ts.serverstatus = ?");
			paramList.add(new Integer(searchForm.getQueryServerStatus()));
		}else{
			filterHql.append(" and ts.serverstatus in (0,1)");
		}

		//服务器所在国家
		if (searchForm.getQueryCountryId() != null && !"".equals(searchForm.getQueryCountryId()) && searchForm.getQueryCountryId().indexOf("-")==-1) {
			filterHql.append(" and ts.countryid = ?");
			paramList.add(new Integer (searchForm.getQueryCountryId()));
		}

		//服务器所在省（州）
		if (searchForm.getQueryProvinceId() != null && !"".equals(searchForm.getQueryProvinceId()) && searchForm.getQueryProvinceId().indexOf("-")==-1) {
			filterHql.append(" and ts.provinceid = ?");
			paramList.add(new Integer (searchForm.getQueryProvinceId()));
		}

		//服务器所在城市
		if (searchForm.getQueryCityId() != null && !"".equals(searchForm.getQueryCityId()) && searchForm.getQueryCityId().indexOf("-")==-1) {
			filterHql.append(" and ts.cityid = ?");
			paramList.add(new Integer (searchForm.getQueryCityId()));
		}

		//服务器服务区域
		if (searchForm.getQueryRegionId() != null && !"".equals(searchForm.getQueryRegionId())) {
			filterHql.append(" and ts.regionid = ?");
			paramList.add(new Integer (searchForm.getQueryRegionId()));
		}

		//有效期起始时间
		if (searchForm.getQueryStartTime() != null && !"".equals(searchForm.getQueryStartTime())) {
//			filterHql.append(" and subStr(goodsInfo.createTime,0,8) >= ?");
			filterHql.append(" and ts.invalidtime >= ?");
			paramList.add((Object)DateUtils.formatDate(searchForm.getQueryStartTime(),"yyyy-MM-dd HH:mm:ss"));
		}

		//有效期截至时间
		if (searchForm.getQueryEndTime() != null && !"".equals(searchForm.getQueryEndTime())) {
//			filterHql.append(" and subStr(goodsInfo.createTime,0,8) <= ?");
			filterHql.append(" and ts.invalidtime <= ?");
			paramList.add((Object)DateUtils.formatDate(searchForm.getQueryEndTime(),"yyyy-MM-dd HH:mm:ss"));
		}

		//服务器所在省（州）
		if (searchForm.getQueryStatus() != null && !"".equals(searchForm.getQueryStatus())) {
			filterHql.append(" and ts.status = ?");
			paramList.add(searchForm.getQueryStatus());
		}

		//服务器分组ID
		if (searchForm.getQueryServerGroup() != null && !"".equals(searchForm.getQueryServerGroup())) {
			filterHql.append(" and sGroup.servergroupid = ?");
			paramList.add(new Integer (searchForm.getQueryServerGroup()));
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by ts.servertype ,ts.regionid asc ");
		countHql.append(filterHql);

		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	/**
	 * 批量删除选中的服务器记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delServerInfo(String serverIds) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE TRANSIT_SERVER TS SET TS.STATUS = ");
		sbSql.append("'" + MssConstants.STATE_D + "'"); // 状态设置为删除
		sbSql.append(" WHERE TS.SERVERID IN (");
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
		listHql.append("select transitServer from TransitServer transitServer ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" order by  transitServer.countryid,transitServer.provinceid,transitServer.cityid ");

		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		return list;
	}

	
	/**
	 * 根据服务器id查询服务器所属服务器组
	 * @param serverId
	 * @return
	 */
	public List queryServerGroup(String serverId){
		Integer[] paramList = new Integer[1];
		// 查询条数sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select sgMapping from ServerGroupMapping sgMapping ");

		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//服务器Id
		if (serverId != null && !"".equals(serverId)) {
			filterHql.append(" and sgMapping.serverid = ?");
			paramList[0] = new Integer(serverId);
		}

		
		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()),paramList);
		return list;
	}
}