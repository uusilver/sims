package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.GoodsInfoForm;
import com.xwtech.mss.pub.dao.business.GoodsInfoDAO;
import com.xwtech.mss.pub.po.GoodsInfo;

public class GoodsInfoBO {

	private static final Log log = LogFactory.getLog(GoodsInfoBO.class);
	
	private GoodsInfoDAO goodsInfoDAO;

	public void setGoodsInfoDAO(GoodsInfoDAO goodsInfoDAO) {
		this.goodsInfoDAO = goodsInfoDAO;
	}
	
	/**
	 * 保存或者更新物品信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(GoodsInfo transientInstance) {
		this.goodsInfoDAO.save(transientInstance);
	}
	
	/**
	 * 根据物品记录id查询物品信息
	 * @param id
	 * @return
	 */
	public GoodsInfo findById(java.lang.Long id) {
		return this.goodsInfoDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询物品信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryGoodsInfoList(GoodsInfoForm searchForm, String perPageCount){
		return this.goodsInfoDAO.queryGoodsInfoList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的物品记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delGoodsInfo(String goodsNumStr) {
		return this.goodsInfoDAO.delGoodsInfo(goodsNumStr);
	}
	
	/**
	 * 根据查询条件查询同类别物品信息个数
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List querySameGoodsCount(GoodsInfoForm searchForm){
		return this.goodsInfoDAO.querySameGoodsCount(searchForm);
	}
	
	/**
	 * 根据物品id查询物品名称
	 * @param goodsNumStr
	 * @return
	 */
	public List queryGoodsName(String goodsNumStr){
		return this.goodsInfoDAO.queryGoodsName(goodsNumStr);
	}
}
