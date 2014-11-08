package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ServerInfoForm;
import com.xwtech.mss.pub.dao.business.OperDNSServerDAO;
import com.xwtech.mss.pub.po.OperDNSServer;

public class DnsServerBO {

	private static final Log log = LogFactory.getLog(DnsServerBO.class);
	
	private OperDNSServerDAO operDNSServerDAO;

	
	public void setOperDNSServerDAO(OperDNSServerDAO operDNSServerDAO) {
		this.operDNSServerDAO = operDNSServerDAO;
	}

	/**
	 * 保存或者更新服务器信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(OperDNSServer transientInstance) {
		this.operDNSServerDAO.save(transientInstance);
	}
	
	/**
	 * 根据服务器记录id查询服务器信息
	 * @param id
	 * @return
	 */
	public OperDNSServer findById(java.lang.Integer id) {
		return this.operDNSServerDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询服务器信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryDnsServerList(ServerInfoForm searchForm, String perPageCount){
		return this.operDNSServerDAO.queryDnsServerList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的服务器记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delServerInfo(String ServerNumStr) {
		return this.operDNSServerDAO.delServerInfo(ServerNumStr);
	}
	
	/**
	 * 查询所有服务器信息
	 * @param ServerId
	 * @return
	 */
	public List queryAllServer(){
		return this.operDNSServerDAO.queryAllServer();
	}
	
	/**
	 * 根据服务器id查询服务器所属服务器组
	 * @param serverId
	 * @return
	 */
//	public List queryServerGroup(String serverId){
//		return this.operDNSServerDAO.queryServerGroup(serverId);
//	}
	
	/**
	 * 查询不在该服务器分组的所有服务器
	 * @return List 服务器列表
	 */
//	@SuppressWarnings("unchecked")
//	public List<ServerInfoForm> queryUnGroupedServer(String groupId,Boolean isLoadGroupServer) {
//		List list = this.operDNSServerDAO.queryUnGroupedServer(groupId,isLoadGroupServer);
//		List<ServerInfoForm> resultList = null;
//		if(list!=null&&!list.isEmpty()){
//			resultList = new ArrayList();
//			ServerInfoForm serverInfoForm=null;
//			for(int i=0;i<list.size();i++){
//				ListOrderedMap server = (ListOrderedMap)(list.get(i));
//				serverInfoForm = new ServerInfoForm();
//				serverInfoForm.setServerId(server.get("serverId").toString());
//				serverInfoForm.setServerTag(server.get("serverName").toString());
//				resultList.add(serverInfoForm);
//			}
//		}
//		return resultList;
//	}
}
