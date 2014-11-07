package com.xwtech.mss.bo.business;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ClientGroupForm;
import com.xwtech.mss.pub.dao.business.ClientGroupDAO;
import com.xwtech.mss.pub.po.ClientGroup;

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
}
