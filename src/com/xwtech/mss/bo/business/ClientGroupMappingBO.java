package com.xwtech.mss.bo.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ClientInfoForm;
import com.xwtech.mss.pub.dao.business.ClientGroupMappingDAO;
import com.xwtech.mss.pub.po.ClientGroupMapping;

public class ClientGroupMappingBO {

	private static final Log log = LogFactory.getLog(ClientGroupMappingBO.class);
	
	private ClientGroupMappingDAO clientGroupMappingDAO;

	
	public void setClientGroupMappingDAO(ClientGroupMappingDAO clientGroupMappingDAO) {
		this.clientGroupMappingDAO = clientGroupMappingDAO;
	}

	/**
	 * 保存或者更新服务器与分组关系信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(ClientGroupMapping transientInstance) {
		this.clientGroupMappingDAO.save(transientInstance);
	}
	
	/**
	 * 根据物品记录id查询服务器分组信息
	 * @param id
	 * @return
	 */
	public ClientGroupMapping findById(Integer id) {
		return this.clientGroupMappingDAO.findById(id);
	}
	
	/**
	 * 保存服务器和分组的对应关系
	 * @param serverGroupId
	 * @param serverIds,以‘,’隔开
	 */
	public int saveClientGroupLink(Integer serverGroupId, String serverIds) {
		return this.clientGroupMappingDAO.saveClientGroupLink(serverGroupId,serverIds);
	}
	
	/**
	 * 根据Id删除记录
	 * 
	 * @param idStr,以‘,’隔开
	 */
	public int delMappingRecords(String clientGroupIdStr) {
		return this.clientGroupMappingDAO.delMappingRecords(clientGroupIdStr);
	}
	
	/**
	 * 根据服务器组ID查询该组中服务器信息,用于服务器分组选择列表展示
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List<?> queryServerTextByGroupId(String groupId){
		List list = this.clientGroupMappingDAO.queryClientTextByGroupId(groupId);
		List<ClientInfoForm> resultList = null;
		if(list!=null&&!list.isEmpty()){
			resultList = new ArrayList();
			ClientInfoForm clientInfoForm=null;
			for(int i=0;i<list.size();i++){
				Object[] client = (Object[])(list.get(i));
				clientInfoForm = new ClientInfoForm();
				clientInfoForm.setClientId(client[0].toString());
				clientInfoForm.setClientTag(client[1].toString());
				resultList.add(clientInfoForm);
			}
		}
		return resultList;
	}
}
