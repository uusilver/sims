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
import com.xwtech.mss.formBean.GoodsInfoForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.GoodsInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.GoodsInfo
 * @author MyEclipse Persistence Tools
 */

public class GoodsInfoDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(GoodsInfoDAO.class);
	// property constants
	public static final String GOODS_NAME = "goodsName";
	public static final String GOODS_CODE = "goodsCode";
	public static final String GOODS_TYPE = "goodsType";
	public static final String GOODS_COUNT = "goodsCount";
	public static final String GOODS_PRICE = "goodsPrice";
	public static final String CREATE_TIME = "createTime";
	public static final String MODIFY_TIME = "modifyTime";
	public static final String GOODS_STATE = "goodsState";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsInfo transientInstance) {
		log.debug("saving GoodsInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GoodsInfo persistentInstance) {
		log.debug("deleting GoodsInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsInfo findById(java.lang.Long id) {
		log.debug("getting GoodsInfo instance with id: " + id);
		try {
			GoodsInfo instance = (GoodsInfo) getHibernateTemplate().get(
					"com.xwtech.mss.pub.po.GoodsInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsInfo instance) {
		log.debug("finding GoodsInfo instance by example");
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
		log.debug("finding GoodsInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GoodsInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByGoodsName(Object goodsName) {
		return findByProperty(GOODS_NAME, goodsName);
	}

	public List findByGoodsCode(Object goodsCode) {
		return findByProperty(GOODS_CODE, goodsCode);
	}

	public List findByGoodsType(Object goodsType) {
		return findByProperty(GOODS_TYPE, goodsType);
	}

	public List findByGoodsCount(Object goodsCount) {
		return findByProperty(GOODS_COUNT, goodsCount);
	}

	public List findByGoodsPrice(Object goodsPrice) {
		return findByProperty(GOODS_PRICE, goodsPrice);
	}

	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List findByModifyTime(Object modifyTime) {
		return findByProperty(MODIFY_TIME, modifyTime);
	}

	public List findByGoodsState(Object goodsState) {
		return findByProperty(GOODS_STATE, goodsState);
	}

	public List findAll() {
		log.debug("finding all GoodsInfo instances");
		try {
			String queryString = "from GoodsInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsInfo merge(GoodsInfo detachedInstance) {
		log.debug("merging GoodsInfo instance");
		try {
			GoodsInfo result = (GoodsInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsInfo instance) {
		log.debug("attaching dirty GoodsInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsInfo instance) {
		log.debug("attaching clean GoodsInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsInfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GoodsInfoDAO) ctx.getBean("GoodsInfoDAO");
	}
	
	/**
	 * 根据查询条件查询物品信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryGoodsInfoList(GoodsInfoForm searchForm, String perPageCount){
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsInfo from GoodsInfo goodsInfo ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(goodsInfo.goodsNum) from GoodsInfo goodsInfo ");

		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//物品名称
		if (searchForm.getQueryGoodsName() != null && !"".equals(searchForm.getQueryGoodsName())) {
			filterHql.append(" and goodsInfo.goodsName like ?");
			paramList.add((Object) ("%" + searchForm.getQueryGoodsName() + "%"));
		}
		//物品状态
		if (searchForm.getQueryGoodsState() != null && !"".equals(searchForm.getQueryGoodsState())) {
			filterHql.append(" and goodsInfo.goodsState = ?");
			paramList.add(new String(searchForm.getQueryGoodsState()));
		}else{
			filterHql.append(" and goodsInfo.goodsState in ('A','U')");
		}

		//物品类型
		if (searchForm.getQueryGoodsType() != null && !"".equals(searchForm.getQueryGoodsType())) {
			filterHql.append(" and goodsInfo.goodsType like ?");
			paramList.add((Object) ("%" + ","+searchForm.getGoodsTypeStr()+"," + "%"));
		}

		//起始时间
		if (searchForm.getQueryStartTime() != null && !"".equals(searchForm.getQueryStartTime())) {
//			filterHql.append(" and subStr(goodsInfo.createTime,0,8) >= ?");
			filterHql.append(" and subStr(goodsInfo.createTime,1,8) >= ?");
			paramList.add((Object) (searchForm.getQueryStartTime().replaceAll("-", "")));
		}

		//截止时间
		if (searchForm.getQueryEndTime() != null && !"".equals(searchForm.getQueryEndTime())) {
//			filterHql.append(" and subStr(goodsInfo.createTime,0,8) <= ?");
			filterHql.append(" and subStr(goodsInfo.createTime,1,8) <= ?");
			paramList.add((Object) (searchForm.getQueryEndTime().replaceAll("-", "")));
		}
		
		//按物品类别和名称排序
		listHql.append(filterHql + "  order by goodsInfo.goodsType ,goodsInfo.goodsName asc ");
		countHql.append(filterHql);

		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	/**
	 * 根据查询条件查询同类别物品信息个数
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List querySameGoodsCount(GoodsInfoForm searchForm){
		String[] paramList = new String[1];
		// 查询条数sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select count(goodsInfo.goodsNum) from GoodsInfo goodsInfo ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//物品类型
		if (searchForm.getGoodsTypeStr() != null && !"".equals(searchForm.getGoodsTypeStr())) {
			filterHql.append(" and goodsInfo.typeName like ?");
			paramList[0] = searchForm.getGoodsTypeStr()+"%";
		}

		
		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()),paramList);
		return list;
	}
	
	/**
	 * 批量删除选中的物品记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delGoodsInfo(String goodsNumStr) {
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE goods_info fr SET fr.goods_state = ");
		sbSql.append("'" + SpmsConstants.STATE_D + "'"); // 角色状态设置为无效
		sbSql.append(" WHERE fr.goods_num IN (");
		sbSql.append(goodsNumStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
	

	
	/**
	 * 根据物品id查询物品名称
	 * @param goodsNumStr
	 * @return
	 */
	public List queryGoodsName(String goodsNumStr){
		String[] paramList = new String[1];
		// 查询条数sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsInfo from GoodsInfo goodsInfo ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//物品Id
		if (goodsNumStr != null && !"".equals(goodsNumStr)) {
			filterHql.append(" and goodsInfo.goodsNum in ("+goodsNumStr+")");
		}

		
		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		return list;
	}
}