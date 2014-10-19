package com.xwtech.mss.bo.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.pub.dao.business.ServerGroupMappingDAO;
import com.xwtech.mss.pub.po.ServerGroupMapping;

public class ServerGroupMappingBO {

	private static final Log log = LogFactory.getLog(ServerGroupMappingBO.class);
	
	private ServerGroupMappingDAO serverGroupMappingDAO;

	
	public void setServerGroupMappingDAO(ServerGroupMappingDAO serverGroupMappingDAO) {
		this.serverGroupMappingDAO = serverGroupMappingDAO;
	}

	/**
	 * 保存或者更新服务器与分组关系信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(ServerGroupMapping transientInstance) {
		this.serverGroupMappingDAO.save(transientInstance);
	}
	
	/**
	 * 根据物品记录id查询服务器分组信息
	 * @param id
	 * @return
	 */
	public ServerGroupMapping findById(Integer id) {
		return this.serverGroupMappingDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询物品信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
//	public HashMap queryGoodsInfoList(GoodsInfoForm searchForm, String perPageCount){
//		return this.goodsInfoDAO.queryGoodsInfoList(searchForm, perPageCount);
//	}
	
	/**
	 * 批量删除选中的物品记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
//	public int delGoodsInfo(String goodsNumStr) {
//		return this.goodsInfoDAO.delGoodsInfo(goodsNumStr);
//	}
	
	/**
	 * 根据查询条件查询同类别物品信息个数
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
//	public List querySameGoodsCount(GoodsInfoForm searchForm){
//		return this.goodsInfoDAO.querySameGoodsCount(searchForm);
//	}
}
