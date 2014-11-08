package com.xwtech.mss.bo.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.formBean.ServerInfoForm;
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
	
	/**
	 * 查询不在该服务器分组的所有服务器
	 * @return List 服务器列表
	 */
	@SuppressWarnings("unchecked")
	public List<ClientInfoForm> queryUnGroupedClient(String groupId,Boolean isLoadGroupClient) {
		List list = this.clientDAO.queryUnGroupedClient(groupId,isLoadGroupClient);
		List<ClientInfoForm> resultList = null;
		if(list!=null&&!list.isEmpty()){
			resultList = new ArrayList();
			ClientInfoForm clientInfoForm=null;
			for(int i=0;i<list.size();i++){
				ListOrderedMap client = (ListOrderedMap)(list.get(i));
				clientInfoForm = new ClientInfoForm();
				clientInfoForm.setClientId(client.get("clientId").toString());
				clientInfoForm.setClientTag(client.get("clientName").toString());
				resultList.add(clientInfoForm);
			}
		}
		return resultList;
	}
	
	/**
	 * 查询该客户端不能访问的所有服务器
	 * @return List 服务器列表
	 */
	@SuppressWarnings("unchecked")
	public List<ServerInfoForm> queryUnAccessedServer(String clientId,Boolean isLoadGroupServer) {
		List list = this.clientDAO.queryUnAccessedServer(clientId,isLoadGroupServer);
		List<ServerInfoForm> resultList = null;
		if(list!=null&&!list.isEmpty()){
			resultList = new ArrayList();
			ServerInfoForm serverInfoForm=null;
			for(int i=0;i<list.size();i++){
				ListOrderedMap server = (ListOrderedMap)(list.get(i));
				serverInfoForm = new ServerInfoForm();
				serverInfoForm.setServerId(server.get("serverId").toString());
				serverInfoForm.setServerTag(server.get("serverName").toString());
				resultList.add(serverInfoForm);
			}
		}
		return resultList;
	}
	
}
