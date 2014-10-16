package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.GoodsTypeForm;
import com.xwtech.mss.pub.dao.business.GoodsTypeDAO;
import com.xwtech.mss.pub.po.GoodsType;

public class GoodsTypeBO {
 
	private static final Log log = LogFactory.getLog(GoodsTypeBO.class);
	
	private GoodsTypeDAO goodsTypeDAO;

	public void setGoodsTypeDAO(GoodsTypeDAO goodsTypeDAO) {
		this.goodsTypeDAO = goodsTypeDAO;
	}
	

	/**
	 * 保存物品类别信息
	 * @param transientInstance
	 */
	public void save(GoodsType transientInstance) {
		this.goodsTypeDAO.save(transientInstance);
	}
	
	/**
	 * 根据id查询物品类别信息
	 * @param id
	 * @return
	 */
	public GoodsType findById(Long id) {
		return this.goodsTypeDAO.findById(id);
	}
	
	/**
	 * 根据类别名称查询类别记录
	 * @param typeName
	 * @return
	 */
	public List<GoodsType> findByTypeName(String typeName) {
		return this.goodsTypeDAO.findByTypeName(typeName);
	}
	
	/**
	 * 根据字段和值查询记录
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<GoodsType> findByProperty(String propertyName, Object value) {
		return this.goodsTypeDAO.findByProperty(propertyName, value);
	}
	
	/**
	 * 根据查询条件查询物品类别信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryGoodsTypeList(GoodsTypeForm searchForm, String perPageCount){
		return this.goodsTypeDAO.queryGoodsTypeList(searchForm, perPageCount);
		
	}
	
	/**
	 * 根据父类别id查询子类别
	 * @param fatherId
	 * @return
	 */
	public List querySubGoodsType(String fatherId){
		return this.goodsTypeDAO.querySubGoodsType(fatherId);
	}
	
	/**
	 * 批量删除选中的物品类别记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delGoodsType(String typeNumStr) {
		return this.goodsTypeDAO.delGoodsType(typeNumStr);
	}
	
	/**
	 * 批量删除选中的物品类别记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delGoodsTypeByItemId(String stypeNumStr) {
		return this.goodsTypeDAO.delGoodsTypeByItemId(stypeNumStr);
	}
	/**
	 * 根据指定的物品类别id查询物品类别信息
	 * @param typeNumStr
	 * @return
	 */
	public List queryGoodsTypeByIds(String typeNumStr){
		return this.goodsTypeDAO.queryGoodsTypeByIds(typeNumStr);
	}
}
