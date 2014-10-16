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
import com.xwtech.mss.formBean.GoodsRecordForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.GoodsRecord;

/**
 * A data access object (DAO) providing persistence and search support for
 * GoodsRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xwtech.mss.pub.dao.GoodsRecord
 * @author MyEclipse Persistence Tools
 */

public class GoodsRecordDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(GoodsRecordDAO.class);
	// property constants
	public static final String GOODS_NAME = "goodsName";
	public static final String GOODS_NUM = "goodsNum";
	public static final String GOODS_TYPE = "goodsType";
	public static final String TYPE_NUM = "typeNum";
	public static final String GOODS_COUNT = "goodsCount";
	public static final String SALE_PRICE = "salePrice";
	public static final String CLIENT_NUM = "clientNum";
	public static final String CREATE_TIME = "createTime";
	public static final String RECORD_TYPE = "recordType";
	public static final String RECORD_STATE = "recordState";

	protected void initDao() {
		// do nothing
	}

	public void save(GoodsRecord transientInstance) {
		log.debug("saving GoodsRecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GoodsRecord persistentInstance) {
		log.debug("deleting GoodsRecord instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GoodsRecord findById(java.lang.Long id) {
		log.debug("getting GoodsRecord instance with id: " + id);
		try {
			GoodsRecord instance = (GoodsRecord) getHibernateTemplate().get(
					"com.xwtech.mss.pub.po.GoodsRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GoodsRecord instance) {
		log.debug("finding GoodsRecord instance by example");
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
		log.debug("finding GoodsRecord instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GoodsRecord as model where model."
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

	public List findByGoodsNum(Object goodsNum) {
		return findByProperty(GOODS_NUM, goodsNum);
	}

	public List findByGoodsType(Object goodsType) {
		return findByProperty(GOODS_TYPE, goodsType);
	}

	public List findByTypeNum(Object typeNum) {
		return findByProperty(TYPE_NUM, typeNum);
	}

	public List findByGoodsCount(Object goodsCount) {
		return findByProperty(GOODS_COUNT, goodsCount);
	}

	public List findBySalePrice(Object salePrice) {
		return findByProperty(SALE_PRICE, salePrice);
	}

	public List findByClientNum(Object clientNum) {
		return findByProperty(CLIENT_NUM, clientNum);
	}

	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List findByRecordType(Object recordType) {
		return findByProperty(RECORD_TYPE, recordType);
	}

	public List findByRecordState(Object recordState) {
		return findByProperty(RECORD_STATE, recordState);
	}

	public List findAll() {
		log.debug("finding all GoodsRecord instances");
		try {
			String queryString = "from GoodsRecord";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GoodsRecord merge(GoodsRecord detachedInstance) {
		log.debug("merging GoodsRecord instance");
		try {
			GoodsRecord result = (GoodsRecord) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GoodsRecord instance) {
		log.debug("attaching dirty GoodsRecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GoodsRecord instance) {
		log.debug("attaching clean GoodsRecord instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GoodsRecordDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (GoodsRecordDAO) ctx.getBean("GoodsRecordDAO");
	}
	
	/**
	 * 根据查询条件查询物品流水信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryGoodsRecordList(GoodsRecordForm searchForm, String perPageCount){
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsRecord from GoodsRecord goodsRecord ");

		// 查询条数
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(goodsRecord.recordNum) from GoodsRecord goodsRecord ");

		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//流水类型
		if (searchForm.getQueryRecordType() != null && !"".equals(searchForm.getQueryRecordType())) {
			filterHql.append(" and goodsRecord.recordType = ?");
			paramList.add(new Long(searchForm.getQueryRecordType()));
		}
		//流水状态
		if (searchForm.getQueryRecordState() != null && !"".equals(searchForm.getQueryRecordState())) {
			filterHql.append(" and goodsRecord.recordState = ?");
			paramList.add(new String(searchForm.getQueryRecordState()));
		}

		//物品类型
		if (searchForm.getQueryGoodsType() != null && !"".equals(searchForm.getQueryGoodsType())) {
			filterHql.append(" and goodsRecord.typeNum like ?");
			paramList.add((Object) ("%" + ","+searchForm.getQueryGoodsTypeStr()+"," + "%"));
		}

		//物品名称
		if (searchForm.getQueryGoodsName() != null && !"".equals(searchForm.getQueryGoodsName())) {
			filterHql.append(" and goodsRecord.goodsName like ?");
			paramList.add((Object) ("%"+searchForm.getQueryGoodsName() + "%"));
		}

		//客户昵称
		if (searchForm.getQueryClientNick() != null && !"".equals(searchForm.getQueryClientNick())) {
			filterHql.append(" and goodsRecord.clientInfo.clientNick like ?");
			paramList.add((Object) ("%"+searchForm.getQueryClientNick() + "%"));
		}

		//起始时间
		if (searchForm.getQueryStartTime() != null && !"".equals(searchForm.getQueryStartTime())) {
//			filterHql.append(" and subStr(goodsRecord.createTime,0,8) >= ?");
			filterHql.append(" and subStr(goodsRecord.createTime,1,8) >= ?");
			paramList.add((Object) (searchForm.getQueryStartTime().replaceAll("-", "")));
		}

		//截止时间
		if (searchForm.getQueryEndTime() != null && !"".equals(searchForm.getQueryEndTime())) {
//			filterHql.append(" and subStr(goodsRecord.createTime,0,8) <= ?");
			filterHql.append(" and subStr(goodsRecord.createTime,1,8) <= ?");
			paramList.add((Object) (searchForm.getQueryEndTime().replaceAll("-", "")));
		}

		//客户id
		if (searchForm.getQueryClientInfo() != null && !"".equals(searchForm.getQueryClientInfo())) {
			filterHql.append(" and goodsRecord.clientInfo.clientNum = ?");
			paramList.add(new Long(searchForm.getQueryClientInfo()));
		}

		//操作人id
		if (searchForm.getQueryOperator() != null && !"".equals(searchForm.getQueryOperator())) {
			filterHql.append(" and goodsRecord.operator = ?");
			paramList.add(new Long(searchForm.getQueryOperator()));
		}

		//修改人id
		if (searchForm.getQueryModifier() != null && !"".equals(searchForm.getQueryModifier())) {
			filterHql.append(" and goodsRecord.modifier = ?");
			paramList.add(new Long(searchForm.getQueryModifier()));
		}

		//客户是否确认
		if (searchForm.getQueryClientConfirm() != null && "Y".equals(searchForm.getQueryClientConfirm())) {
			filterHql.append(" and goodsRecord.clientConfirm = ?");
			paramList.add(new String(searchForm.getQueryClientConfirm()));
		}

		//客户是否确认
		if (searchForm.getQueryClientConfirm() != null && "N".equals(searchForm.getQueryClientConfirm())) {
			filterHql.append(" and (goodsRecord.clientConfirm = ? or goodsRecord.clientConfirm is null)");
			paramList.add(new String(searchForm.getQueryClientConfirm()));
		}
		
		//按创建时间倒叙和流水类型升序排列
		listHql.append(filterHql + "  order by goodsRecord.recordState asc,goodsRecord.createTime desc ,goodsRecord.recordType asc ");
		countHql.append(filterHql);

		HashMap map = queryResultCount(listHql.toString(), countHql.toString(), paramList, searchForm.getCurrentPage(),
				perPageCount);
		return map;
	}
	
	
	/**
	 * 查询指定条件的物品流水信息
	 * @param goodsNum
	 * @param recordType
	 * @return
	 */
	public List queryRecordListByGoodsAndState(String goodsNum, Long recordType){
		Object[] paramList = new Object[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsRecord from GoodsRecord goodsRecord ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//物品编码
		if (goodsNum != null && !"".equals(goodsNum)) {
			filterHql.append(" and goodsRecord.goodsNum = ?");
			paramList[0] = new Long(goodsNum);
		}
		//流水类型
		if (recordType != null) {
			filterHql.append(" and goodsRecord.recordType = ?");
			paramList[1] = recordType;
		}

		List<GoodsRecord> list = getHibernateTemplate().find(listHql.append(filterHql).toString(), paramList);
		return list;
	}
	
	
	/**
	 * 根据查询条件统计日结算报表
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List statisticGoodsRecord(GoodsRecordForm searchForm, String perPageCount){
		List paramList = new ArrayList();
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsRecord.goodsName,goodsRecord.goodsType,goodsRecord.recordType,"
				+ "goodsRecord.recordState,goodsRecord.operator,userInfo.userName,"
				+ "sum(goodsRecord.goodsCount),sum(goodsRecord.goodsProfit),"
				+ "sum(goodsRecord.finalProfit),sum(goodsRecord.goodsCount*goodsRecord.salePrice)" +
				" from GoodsRecord goodsRecord,UserInfo userInfo ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where goodsRecord.operator = userInfo.userId ");

		//起始时间
		if (searchForm.getQueryStartTime() != null && !"".equals(searchForm.getQueryStartTime())) {
//			filterHql.append(" and subStr(goodsRecord.createTime,0,8) >= ?");
			filterHql.append(" and subStr(goodsRecord.createTime,1,8) >= ?");
			paramList.add((Object) (searchForm.getQueryStartTime().replaceAll("-", "")));
		}

		//截止时间
		if (searchForm.getQueryEndTime() != null && !"".equals(searchForm.getQueryEndTime())) {
//			filterHql.append(" and subStr(goodsRecord.createTime,0,8) <= ?");
			filterHql.append(" and subStr(goodsRecord.createTime,1,8) <= ?");
			paramList.add((Object) (searchForm.getQueryEndTime().replaceAll("-", "")));
		}

		//客户付款起始时间
		if (searchForm.getQueryPayStartTime() != null && !"".equals(searchForm.getQueryPayStartTime())) {
//			filterHql.append(" and subStr(goodsRecord.payTime,0,4)||subStr(goodsRecord.payTime,6,2)||subStr(goodsRecord.payTime,9,2) >= ?");
			filterHql.append(" and CONCAT(subStr(goodsRecord.payTime,1,4),subStr(goodsRecord.payTime,6,2),subStr(goodsRecord.payTime,9,2)) >= ?");
			paramList.add((Object) (searchForm.getQueryPayStartTime().replaceAll("-", "")));
		}

		//客户付款截止时间
		if (searchForm.getQueryPayEndTime() != null && !"".equals(searchForm.getQueryPayEndTime())) {
//			filterHql.append(" and subStr(goodsRecord.payTime,0,4)||subStr(goodsRecord.payTime,6,2)||subStr(goodsRecord.payTime,9,2) <= ?");
			filterHql.append(" and CONCAT(subStr(goodsRecord.payTime,1,4),subStr(goodsRecord.payTime,6,2),subStr(goodsRecord.payTime,9,2)) >= ?");
			paramList.add((Object) (searchForm.getQueryPayEndTime().replaceAll("-", "")));
		}
		
		//物品类型
		if (searchForm.getQueryGoodsType() != null && !"".equals(searchForm.getQueryGoodsType())) {
			filterHql.append(" and goodsRecord.typeNum like ?");
			paramList.add((Object) ("%" + ","+searchForm.getQueryGoodsTypeStr()+"," + "%"));
		}

		//物品名称
		if (searchForm.getQueryGoodsName() != null && !"".equals(searchForm.getQueryGoodsName())) {
			filterHql.append(" and goodsRecord.goodsName like ?");
			paramList.add((Object) ("%"+searchForm.getQueryGoodsName() + "%"));
		}

		//客户昵称
		if (searchForm.getQueryClientNick() != null && !"".equals(searchForm.getQueryClientNick())) {
			filterHql.append(" and goodsRecord.clientInfo.clientNick like ?");
			paramList.add((Object) ("%"+searchForm.getQueryClientNick() + "%"));
		}

		//操作人id
		if (searchForm.getQueryOperator() != null && !"".equals(searchForm.getQueryOperator())) {
			filterHql.append(" and goodsRecord.operator = ?");
			paramList.add(new Long(searchForm.getQueryOperator()));
		}

		//流水类型
		if (searchForm.getQueryRecordType() != null && !"".equals(searchForm.getQueryRecordType())) {
			filterHql.append(" and goodsRecord.recordType = ?");
			paramList.add(new Long(searchForm.getQueryRecordType()));
		}
		
//		filterHql.append(" and goodsRecord.recordState != 2");
		
		//按创建时间倒叙和流水类型升序排列
		listHql.append(filterHql + "  group by goodsRecord.goodsName,goodsRecord.goodsType,goodsRecord.recordType,goodsRecord.recordState,goodsRecord.operator,userInfo.userName");
		listHql.append(" order by goodsRecord.operator,goodsRecord.goodsName,goodsRecord.goodsType,goodsRecord.recordState asc ");

		Object[] paramArray = null;
		if(!paramList.isEmpty()){
			paramArray = new Object[paramList.size()];
			for (int i = 0; i < paramList.size(); i++) {
				paramArray[i] = paramList.get(i);
			}
		}
		List<Object[]> resultList = getHibernateTemplate().find(listHql.toString(),paramArray);
		return resultList;
	}
	
	/**
	 * 统计未付款记录数
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List statisticUnpayedCount(GoodsRecordForm searchForm){
		String[] paramList = new String[1];
		// 查询列表sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select count(goodsRecord.recordNum)" +
				" from GoodsRecord goodsRecord ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");
		filterHql.append(" and goodsRecord.recordState = ?");
		paramList[0] = searchForm.getQueryRecordState();
		
		listHql.append(filterHql);

		List<Object[]> resultList = getHibernateTemplate().find(listHql.toString(),paramList);
		return resultList;
	}
	
	/**
	 * 查询指定流水Id的流水信息
	 * @param goodsNum
	 * @param recordType
	 * @return
	 */
	public int updateRecordStateByRecordId(String recordNumStr){
		// 拼装SQL
		StringBuffer sbSql = new StringBuffer("UPDATE goods_record gr SET gr.record_state = ");
		sbSql.append("'" + SpmsConstants.RECORD_PAYED + "'"); 
		sbSql.append(" WHERE gr.record_num IN (");
		sbSql.append(recordNumStr);
		sbSql.append(")");

		return FrameworkApplication.baseJdbcDAO.update(sbSql.toString());
	}
	
	/**
	 * 根据流水id查询流水信息
	 * @param clientNumStr
	 * @return
	 */
	public List queryRecords(String recordNumStr){
		// 查询条数sql
		StringBuffer listHql = new StringBuffer();
		listHql.append("select goodsRecord from GoodsRecord goodsRecord ");


		StringBuffer filterHql = new StringBuffer();
			
		filterHql.append(" where 1=1 ");

		//流水Id
		if (recordNumStr != null && !"".equals(recordNumStr)) {
			filterHql.append(" and goodsRecord.recordNum in ("+recordNumStr+")");
		}

		
		List list = getHibernateTemplate().find((listHql.toString()+filterHql.toString()));
		return list;
	}
}