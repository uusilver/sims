package com.xwtech.mss.bo.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.dao.business.ClientServerMappingDAO;
import com.xwtech.mss.pub.po.ClientServerMapping;

public class ClientServerMappingBO {

	private static final Log log = LogFactory.getLog(ClientServerMappingBO.class);
	
	private ClientServerMappingDAO clientServerMappingDAO;

	
	public void setClientServerMappingDAO(ClientServerMappingDAO clientServerMappingDAO) {
		this.clientServerMappingDAO = clientServerMappingDAO;
	}

	/**
	 * 保存或者更新客户端与服务器关系信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(ClientServerMapping transientInstance) {
		this.clientServerMappingDAO.save(transientInstance);
	}
	
	/**
	 * 根据物品记录id查询服务器分组信息
	 * @param id
	 * @return
	 */
	public ClientServerMapping findById(Integer id) {
		return this.clientServerMappingDAO.findById(id);
	}
	
	/**
	 * 保存服务器和分组的对应关系
	 * @param clientId
	 * @param serverIds,以‘,’隔开
	 */
	public int saveClientServerLink(String clientIds, String serverIds) {
		return this.clientServerMappingDAO.saveClientServerLink(clientIds,serverIds);
	}
	
	/**
	 * 根据Id删除记录
	 * 
	 * @param idStr,以‘,’隔开
	 */
	public int delMappingRecords(String serverIdStr,String clientId) {
		return this.clientServerMappingDAO.delMappingRecords(serverIdStr,clientId);
	}
	
}
