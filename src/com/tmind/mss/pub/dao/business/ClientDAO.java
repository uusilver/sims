package com.tmind.mss.pub.dao.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.tmind.framework.pub.dao.BaseDao;
import com.tmind.framework.pub.utils.DateUtils;
import com.tmind.framework.pub.web.FrameworkApplication;
import com.tmind.mss.formBean.ClientInfoForm;
import com.tmind.mss.pub.constants.MssConstants;
import com.tmind.mss.pub.constants.SpmsConstants;
import com.tmind.mss.pub.po.Client;

/**
 * A data access object (DAO) providing persistence and search support for
 * Client entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.tmind.mss.pub.dao.business.Client
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
					"com.tmind.mss.pub.po.Client", id);
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
					.createCriteria("com.tmind.mss.pub.po.Client")
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
//	public HashMap queryClientInfoList(ClientInfoForm searchForm, String perPageCount){
//		List paramList = new ArrayList();
//		// 查询列表sql
//		StringBuffer listHql = new StringBuffer();
//		listHql.append("select clientInfo.clientid,clientInfo.username,clientInfo.truename,cb_authType.text,"
//				+ " cb_disable.text,cb_userType.text,clientInfo.note,clientInfo.status"
//				+ " from client clientInfo,code_book cb_authType,code_book cb_disable,code_book cb_userType"
//				+ " where clientInfo.authenticationtype = cb_authType.value"
//				+ " and clientInfo.disable = cb_disable.value"
//				+ " and clientInfo.usertype = cb_userType.value"
//				+ " and cb_authType.tag = '"+MssConstants.AUTH_TYPE+"'"
//				+ " and cb_disable.tag = '"+MssConstants.DISABLE_FLAG+"'"
//				+ " and cb_userType.tag = '"+MssConstants.USER_TYPE+"'");
//
//		// 查询条数
//		StringBuffer countHql = new StringBuffer();
//		countHql.append("select count(clientInfo.clientid) from Client clientInfo where 1=1 ");
//
//		StringBuffer filterHql = new StringBuffer();
//			
//		//客户名称
//		if (searchForm.getQueryClientName() != null && !"".equals(searchForm.getQueryClientName())) {
//			filterHql.append(" and clientInfo.truename like ?");
//			paramList.add((Object) ("%" + searchForm.getQueryClientName() + "%"));
//		}
//		//客户状态
//		if (searchForm.getQueryStatus() != null && !"".equals(searchForm.getQueryStatus())) {
//			filterHql.append(" and clientInfo.status = ?");
//			paramList.add(searchForm.getQueryStatus());
//		}else{
//			filterHql.append(" and clientInfo.status in ('A','U')");
//		}
//
//		//客户类型
//		if (searchForm.getQueryUserType() != null && !"".equals(searchForm.getQueryUserType())) {
//			filterHql.append(" and clientInfo.usertype = ?");
//			paramList.add(new Integer(searchForm.getQueryUserType()));
//		}
//
//		//客户端是否被禁用 0 – 未禁用，1 – 已禁用
//		if (searchForm.getQueryDisableFlag() != null && !"".equals(searchForm.getQueryDisableFlag())) {
//			filterHql.append(" and clientInfo.disable = ?");
//			paramList.add(new Integer(searchForm.getQueryDisableFlag()));
//		}
//
//		//客户端认证类型
//		if (searchForm.getQueryAuthType() != null && !"".equals(searchForm.getQueryAuthType())) {
//			filterHql.append(" and clientInfo.authenticationtype = ?");
//			paramList.add(new Integer(searchForm.getQueryAuthType()));
//		}
//
//		//按客户类别和名称排序
//		listHql.append(filterHql + "  order by clientInfo.usertype ,clientInfo.truename asc ");
//		countHql.append(filterHql);
//
//		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
//				perPageCount);
//		return map;
//	}
	
	/**
	 * 根据查询条件查询客户信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryClientInfoList(ClientInfoForm searchForm, String perPageCount){
		Object[] paramArray = null;
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer fromHql = new StringBuffer();
		listHql.append("select c.clientid,c.username,c.truename,cGroup.clientgroupname,"
//				+ " cb_authType.text as authType,"
				+ " cb_disable.text as disableFlag,cb_userType.text as userType,c.note,c.status");
		
		fromHql.append(" from client c left join client_group_mapping cgMapping on c.clientid=cgMapping.clientid"
				+ " left join client_group cGroup on cGroup.clientgroupid = cgMapping.clientgroupid,"
//				+ " code_book cb_authType,"
				+ " code_book cb_disable,code_book cb_userType "
				+ " where c.disable = cb_disable.value"
				+ " and c.usertype = cb_userType.value"
//				+ " and c.authenticationtype = cb_authType.value"
//				+ " and cb_authType.tag = '"+MssConstants.AUTH_TYPE+"'"
				+ " and cb_disable.tag = '"+MssConstants.DISABLE_FLAG+"'"
				+ " and cb_userType.tag = '"+MssConstants.USER_TYPE+"'");
		
		listHql.append(fromHql);
		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(c.clientid) ");
		
		countHql.append(fromHql);

		StringBuffer filterHql = new StringBuffer();
			
//		filterHql.append(" where 1=1 ");

		//客户端用户名
		if (searchForm.getQueryClientName() != null && !"".equals(searchForm.getQueryClientName())) {
			filterHql.append(" and c.username like ?");
			paramList.add((Object) ("%" + searchForm.getQueryClientName() + "%"));
		}
		//客户状态
		if (searchForm.getQueryStatus() != null && !"".equals(searchForm.getQueryStatus())) {
			filterHql.append(" and c.status = ?");
			paramList.add(searchForm.getQueryStatus());
		}else{
			filterHql.append(" and c.status in ('A','U')");
		}

		//客户类型
		if (searchForm.getQueryUserType() != null && !"".equals(searchForm.getQueryUserType())) {
			filterHql.append(" and c.usertype = ?");
			paramList.add(new Integer(searchForm.getQueryUserType()));
		}

		//客户端是否被禁用 0 – 未禁用，1 – 已禁用
		if (searchForm.getQueryDisableFlag() != null && !"".equals(searchForm.getQueryDisableFlag())) {
			filterHql.append(" and c.disable = ?");
			paramList.add(new Integer(searchForm.getQueryDisableFlag()));
		}

		//客户端认证类型
		if (searchForm.getQueryAuthType() != null && !"".equals(searchForm.getQueryAuthType())) {
			filterHql.append(" and c.authenticationtype = ?");
			paramList.add(new Integer(searchForm.getQueryAuthType()));
		}

		//客户端分组ID
		if (searchForm.getQueryClientGroup() != null && !"".equals(searchForm.getQueryClientGroup())) {
			if("0".equals(searchForm.getQueryClientGroup())){
				filterHql.append(" and cGroup.clientgroupname is NULL ");
			}else{
				filterHql.append(" and cGroup.clientgroupid = ?");
				paramList.add(new Integer (searchForm.getQueryClientGroup()));
			}
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by c.clientid,c.username asc ");
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
	
	/**
	 * 查询不在该服务器分组的所有客户端
	 * @param groupId
	 * @param isLoadGroupClient
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryUnGroupedClient(String groupId,Boolean isLoadGroupClient) {
		List<Object[]> list = null;
		Object[] paramList = new Object[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer fromHql = new StringBuffer();
		listHql.append("select c.clientid as clientId,"
				+"CONCAT(c.username,' - [',cb_userType.text,' / ',cb_disable.text,']',"
				+ "' - ',CASE WHEN cGroup.clientgroupname IS NULL THEN '未分组' ELSE cGroup.clientgroupname END) as clientName ");
		
		fromHql.append(" from client c left join client_group_mapping cgMapping on c.clientid=cgMapping.clientid"
				+ " left join client_group cGroup on cGroup.clientgroupid = cgMapping.clientgroupid,"
				+" code_book cb_modPass,"
//				+ "code_book cb_authType,"
				+ "code_book cb_disable,code_book cb_userType"
				+ " where c.modifypass = cb_modPass.value"
				+" and cb_modPass.tag = '"+MssConstants.MODIFY_PASS+"'"
//				+" and c.authenticationtype = cb_authType.value"
//				+" and cb_authType.tag = '"+MssConstants.AUTH_TYPE+"'"
				+" and c.disable = cb_disable.value"
				+" and cb_disable.tag = '"+MssConstants.DISABLE_FLAG+"'"
				+" and c.usertype = cb_userType.value"
				+" and cb_userType.tag = '"+MssConstants.USER_TYPE+"'");
		
		listHql.append(fromHql);

		StringBuffer filterHql = new StringBuffer();
			

		
		if (groupId != null && !"".equals(groupId)) {
			//查询该分组中的服务器记录
			if(isLoadGroupClient){
				filterHql.append(" and cGroup.clientgroupid = ? ");
				paramList[0]=new Integer (groupId);
			}
			//查询未分组和不属于该分组的服务器
			else{
				filterHql.append(" and (cGroup.clientgroupname is NULL or cGroup.clientgroupid != ?)");
				paramList[0]=new Integer (groupId);
			}
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by c.clientid asc ");
		log.info("SQL:"+listHql.toString());
		if(paramList[0]==null){
			list = FrameworkApplication.baseJdbcDAO.queryForList(listHql.toString());
		}else{
			list = FrameworkApplication.baseJdbcDAO.queryForList(listHql.toString(),paramList);
		}
		
		return list;
	}
	
	/**
	 * 查询该客户端不能访问的所有服务器
	 * @param clientId
	 * @param isLoadAccessedServer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryUnAccessedServer(String clientId,Boolean isLoadAccessedServer) {
		List<Object[]> list = null;
		Object[] paramList = new Object[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		StringBuffer fromHql = new StringBuffer();
		StringBuffer whereHql =  new StringBuffer();
		StringBuffer notExistsHql = new StringBuffer();
		listHql.append("select ts.serverid as serverId,"
				+"CONCAT(ts.serverip,' - ',cb_type.text,' - [',c.countryname,'/',prov.provincename,'/',t.cityname,']',"
				+ "' - ',r.regionname) as serverName ");
		
		fromHql.append(" from transit_server ts,code_book cb_type,code_book cb_status,country c,province prov,city t,region r ");
		
		whereHql.append(" where ts.countryid = c.countryid"
				+ " and ts.provinceid = prov.provinceid"
				+ " and ts.cityid = t.cityid"
				+ " and ts.regionid = r.regionid"
				+ " and ts.servertype = cb_type.value"
				+ " and cb_type.tag = '"+MssConstants.SERVER_TYPE+"'"
				+ " and ts.serverstatus = cb_status.value"
				+ " and cb_status.tag = '"+MssConstants.SERVER_STATUS+"'");
		
		notExistsHql.append(" and not exists ( "
				+ " select csMapping.serverid from client cl,client_server_mapping csMapping"
				+" where cl.clientid = csMapping.clientid"
				+" and ts.serverid = csMapping.serverid");
		
		listHql.append(fromHql);

		StringBuffer filterHql = new StringBuffer();
			

		
		if (clientId != null && !"".equals(clientId)) {
			//查询客户端可以访问的服务器
			if(isLoadAccessedServer){
				filterHql.append(",client cl,client_server_mapping csMapping");
				filterHql.append(whereHql);
				filterHql.append(" and cl.clientid = csMapping.clientid "
								+" and ts.serverid = csMapping.serverid"
								+" and cl.clientid in("+clientId+")");
			}
			//查询所有该客户端不能访问的服务器
			else{
				filterHql.append(whereHql);
				filterHql.append(notExistsHql);
				filterHql.append(" and cl.clientid in ("+clientId+"))");
			}
		}
		//查询所有不能访问的服务器
		else{
			filterHql.append(whereHql);
		}
		
		//按服务器类别和名称排序
		listHql.append(filterHql + "  order by ts.serverid asc ");
		log.info("SQL:"+listHql.toString());
		
		list = FrameworkApplication.baseJdbcDAO.queryForList(listHql.toString());
		
		return list;
	}
}