package com.xwtech.mss.bo.business;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.dao.business.ClientDAO;
import com.xwtech.mss.pub.po.Client;
import com.xwtech.mss.pub.po.ClientInfo;

public class ClientInfoBO {
	private static final Log log = LogFactory.getLog(ClientInfoBO.class);
	
	private ClientDAO clientDAO;

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	public void saveOrUpdate(Client transientInstance) {
		this.clientDAO.save(transientInstance);
	}
	
	public Client findById(Integer id) {
		return this.clientDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询客户信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryClientInfoList(ClientInfoForm searchForm, String perPageCount){
		return this.clientDAO.queryClientInfoList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的客户记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delClientInfo(String clientNumStr) {
		return this.clientDAO.delClientInfo(clientNumStr);
	}
	
}
