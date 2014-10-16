package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.GoodsRecordForm;
import com.xwtech.mss.pub.dao.business.GoodsRecordDAO;
import com.xwtech.mss.pub.po.GoodsRecord;

public class GoodsRecordBO {
	
	private static final Log log = LogFactory.getLog(GoodsRecordBO.class);
	
	private GoodsRecordDAO goodsRecordDAO;

	public void setGoodsRecordDAO(GoodsRecordDAO goodsRecordDAO) {
		this.goodsRecordDAO = goodsRecordDAO;
	}
	
	/**
	 * 保存或者更新流水记录
	 * @param transientInstance
	 */
	public void saveOrUpdate(GoodsRecord transientInstance) {
		this.goodsRecordDAO.save(transientInstance);
	}
	
	public GoodsRecord findById(java.lang.Long id) {
		return this.goodsRecordDAO.findById(id);
	}
	
	/**
	 * 根据指定的字段查询流水记录
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByProperty(String propertyName, Object value) {
		return this.goodsRecordDAO.findByProperty(propertyName, value);
	}
	
	/**
	 * 查询指定条件的物品流水信息
	 * @param goodsNum
	 * @param recordType
	 * @return
	 */
	public List<GoodsRecord> queryRecordListByGoodsAndState(String goodsNum, Long recordType){
		return this.goodsRecordDAO.queryRecordListByGoodsAndState(goodsNum, recordType);
	}
	
	/**
	 * 根据查询条件查询物品流水信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryGoodsRecordList(GoodsRecordForm searchForm, String perPageCount){
		return this.goodsRecordDAO.queryGoodsRecordList(searchForm, perPageCount);
	}
	
	/**
	 * 根据查询条件统计日结算报表
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List statisticGoodsRecord(GoodsRecordForm searchForm, String perPageCount){
		return this.goodsRecordDAO.statisticGoodsRecord(searchForm, perPageCount);
	}
	
	/**
	 * 统计未付款记录数
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List statisticUnpayedCount(GoodsRecordForm searchForm){
		return this.goodsRecordDAO.statisticUnpayedCount(searchForm);
	}
	
	/**
	 * 查询指定流水Id的流水信息
	 * @param goodsNum
	 * @param recordType
	 * @return
	 */
	public int updateRecordStateByRecordId(String recordNumStr){
		return this.goodsRecordDAO.updateRecordStateByRecordId(recordNumStr);
	}
	
	/**
	 * 根据流水id查询流水信息
	 * @param clientNumStr
	 * @return
	 */
	public List queryRecords(String recordNumStr){
		return this.goodsRecordDAO.queryRecords(recordNumStr);
	}
}
