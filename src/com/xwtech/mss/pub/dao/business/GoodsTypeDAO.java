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
import com.xwtech.mss.formBean.GoodsTypeForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.GoodsType;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.GoodsType
 * @author MyEclipse Persistence Tools
 */

public class GoodsTypeDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(GoodsTypeDAO.class);
	// property constants
	public static final String TYPE_NAME = "typeName";
	public static final String TYPE_SHORT = "typeShort";
	public static final String _FTYPE_NUM = "FTypeNum";
	public static final String CREATE_TIME = "createTime";
	public static final String MODIFY_TIME = "modifyTime";
	public static final String TYPE_STATE = "typeState";
	public static final String TYPE_COMM = "typeComm";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsType transientInstance) throws RuntimeException {
		log.debug("saving GoodsType instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GoodsType persistentInstance) {
		log.debug("deleting GoodsType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsType findById(java.lang.Long id) {
		log.debug("getting GoodsType instance with id: " + id);
		try {
			GoodsType instance = (GoodsType) getHibernateTemplate().get(
					"com.xwtech.mss.pub.po.GoodsType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsType instance) {
		log.debug("finding GoodsType instance by example");
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

	public List<GoodsType> findByProperty(String propertyName, Object value) {
		log.debug("finding GoodsType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GoodsType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<GoodsType> findByTypeName(Object typeName) {
		return findByProperty(TYPE_NAME, typeName);
	}

	public List findByTypeShort(Object typeShort) {
		return findByProperty(TYPE_SHORT, typeShort);
	}

	public List findByFTypeNum(Object FTypeNum) {
		return findByProperty(_FTYPE_NUM, FTypeNum);
	}

	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List findByModifyTime(Object modifyTime) {
		return findByProperty(MODIFY_TIME, modifyTime);
	}

	public List findByTypeState(Object typeState) {
		return findByProperty(TYPE_STATE, typeState);
	}

	public List findByTypeComm(Object typeComm) {
		return findByProperty(TYPE_COMM, typeComm);
	}

	public List findAll() {
		log.debug("finding all GoodsType instances");
		try {
			String queryString = "from GoodsType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsType merge(GoodsType detachedInstance) {
		log.debug("merging GoodsType instance");
		try {
			GoodsType result = (GoodsType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsType instance) {
		log.debug("attaching dirty GoodsType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsType instance) {
		log.debug("attaching clean GoodsType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsTypeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GoodsTypeDAO) ctx.getBean("GoodsTypeDAO");
	}
	
	
	/**
	 * 根据查询条件查询物品类别信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryGoodsTypeList(GoodsTypeForm searchForm, String perPageCount){
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsType from GoodsType goodsType ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(goodsType.typeNum) from GoodsType goodsType ");

		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		if (searchForm.getLoadNextNode() != null && "Y".equals(searchForm.getLoadNextNode())) {
			filterHql.append(" and goodsType.fatherType is null ");
		}

		if (searchForm.getQueryTypeName() != null && !"".equals(searchForm.getQueryTypeName())) {
			filterHql.append(" and goodsType.typeName like ?");
			paramList.add((Object) ("%" + searchForm.getQueryTypeName() + "%"));
		}
		if (searchForm.getQueryTypeState() != null && !"".equals(searchForm.getQueryTypeState())) {
			filterHql.append(" and goodsType.typeState = ?");
			paramList.add(new String(searchForm.getQueryTypeState()));
		}else{
			filterHql.append(" and goodsType.typeState in ('A','U')");
		}

		listHql.append(filterHql + "  order by goodsType.typeNum asc ");
		countHql.append(filterHql);

		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	
	/**
	 * 根据父类别id查询子类别
	 * @param fatherId
	 * @return
	 */
	public List querySubGoodsType(String fatherId){
		List paramList = new ArrayList();
		Object[] params = new Object[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsType.typeNum,goodsType.typeName,goodsType.typeShort from GoodsType goodsType ");
		listHql.append("where goodsType.fatherType.typeNum = ? ");
		listHql.append(" and goodsType.typeState = 'A'");
		listHql.append(" order by goodsType.typeNum");
		params[0] = new Long(fatherId);
		List list =  getHibernateTemplate().find(listHql.toString(), params);
		return list;
	}
	
	/**
	 * 批量删除选中的物品类别记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delGoodsType(String typeNumStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE goods_type fr SET fr.type_state = ");
		sbSql.append("'" + SpmsConstants.STATE_D + "'"); // 角色状态设置为无效
		sbSql.append(" WHERE fr.type_num IN (");
		sbSql.append(typeNumStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}

	
	/**
	 * 批量删除选中的物品类别记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delGoodsTypeByItemId(String stypeNumStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE goods_type fr SET fr.type_state = ");
		sbSql.append("'" + SpmsConstants.STATE_D + "'"); // 角色状态设置为无效
		sbSql.append(" WHERE fr.type_num IN (");
		sbSql.append(stypeNumStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
	
	/**
	 * 根据指定的物品类别id查询物品类别信息
	 * @param typeNumStr
	 * @return
	 */
	public List queryGoodsTypeByIds(String typeNumStr){
		List paramList = new ArrayList();
		String[] params = new String[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsType from GoodsType goodsType ");
		listHql.append("where goodsType.typeNum in (  "+typeNumStr);
		listHql.append(")");
		params[0] = typeNumStr;
		List list =  getHibernateTemplate().find(listHql.toString());
		return list;
	}
}