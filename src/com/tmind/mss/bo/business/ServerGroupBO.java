package com.tmind.mss.bo.business;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmind.mss.formBean.ServerGroupForm;
import com.tmind.mss.pub.dao.business.ServerGroupDAO;
import com.tmind.mss.pub.po.ServerGroup;

public class ServerGroupBO {

	private static final Log log = LogFactory.getLog(ServerInfoBO.class);
	
	private ServerGroupDAO serverGroupDAO;

	
	public void setServerGroupDAO(ServerGroupDAO serverGroupDAO) {
		this.serverGroupDAO = serverGroupDAO;
	}

	/**
	 * 保存或者更新服务器分组信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(ServerGroup transientInstance) {
		this.serverGroupDAO.save(transientInstance);
	}
	
	/**
	 * 根据服务器分组id查询服务器分组信息
	 * @param id
	 * @return
	 */
	public ServerGroup findById(Integer id) {
		return this.serverGroupDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询服务器分组信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryServerGroupList(ServerGroupForm searchForm, String perPageCount){
		return this.serverGroupDAO.queryServerGroupList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的服务器分组记录（逻辑删除）
	 * @param groupIdStr
	 * @return
	 */
	public int delServerGroup(String groupIdStr) {
		return this.serverGroupDAO.delServerGroup(groupIdStr);
	}
}
