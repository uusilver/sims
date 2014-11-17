package com.tmind.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmind.mss.formBean.ClientGroupForm;
import com.tmind.mss.pub.dao.business.ClientGroupDAO;
import com.tmind.mss.pub.po.ClientGroup;

public class ClientGroupBO {

	private static final Log log = LogFactory.getLog(ClientGroupBO.class);
	
	private ClientGroupDAO clientGroupDAO;

	
	public void setClientGroupDAO(ClientGroupDAO clientGroupDAO) {
		this.clientGroupDAO = clientGroupDAO;
	}

	/**
	 * 保存或者更新客户端分组信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(ClientGroup transientInstance) {
		this.clientGroupDAO.saveOrUpdate(transientInstance);
	}
	
	/**
	 * 根据客户端分组id查询客户端分组信息
	 * @param id
	 * @return
	 */
	public ClientGroup findById(Integer id) {
		return this.clientGroupDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询客户端分组信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryClientGroupList(ClientGroupForm searchForm, String perPageCount){
		return this.clientGroupDAO.queryClientGroupList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的客户端分组记录（逻辑删除）
	 * @param groupIdStr
	 * @return
	 */
	public int delClientGroup(String groupIdStr) {
		return this.clientGroupDAO.delClientGroup(groupIdStr);
	}
	
	/**
	 * 根据客户端组ID查询该组中的客户端信息
	 * @param groupId
	 * @return
	 */
	public List<?> queryClientsByGroupId(String groupId){
		return this.clientGroupDAO.queryClientsByGroupId(groupId);
	}
}
