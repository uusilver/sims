package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.dao.business.ClientInfoDAO;
import com.xwtech.mss.pub.po.ClientInfo;

public class ClientInfoBO {
	private static final Log log = LogFactory.getLog(ClientInfoBO.class);
	
	private ClientInfoDAO clientInfoDAO;

	public void setClientInfoDAO(ClientInfoDAO clientInfoDAO) {
		this.clientInfoDAO = clientInfoDAO;
	}
	
	public void saveOrUpdate(ClientInfo transientInstance) {
		this.clientInfoDAO.save(transientInstance);
	}
	
	public ClientInfo findById(java.lang.Long id) {
		return this.clientInfoDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询客户信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryClientInfoList(ClientInfoForm searchForm, String perPageCount){
		return this.clientInfoDAO.queryClientInfoList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的客户记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delClientInfo(String clientNumStr) {
		return this.clientInfoDAO.delClientInfo(clientNumStr);
	}
	
	/**
	 * 根据客户id查询客户信息
	 * @param clientNumStr
	 * @return
	 */
	public List queryGoodsName(String clientNumStr){
		return this.clientInfoDAO.queryClientsName(clientNumStr);
	}
}
