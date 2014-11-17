package com.tmind.mss.bo.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmind.mss.formBean.ServerInfoForm;
import com.tmind.mss.pub.dao.business.ServerGroupMappingDAO;
import com.tmind.mss.pub.po.ServerGroupMapping;

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
	 * 保存服务器和分组的对应关系
	 * @param serverGroupId
	 * @param serverIds,以‘,’隔开
	 */
	public int saveServerGroupLink(Integer serverGroupId, String serverIds) {
		return this.serverGroupMappingDAO.saveServerGroupLink(serverGroupId,serverIds);
	}
	
	/**
	 * 根据Id删除记录
	 * 
	 * @param idStr,以‘,’隔开
	 */
	public int delMappingRecords(String serverIdStr,String groupId) {
		return this.serverGroupMappingDAO.delMappingRecords(serverIdStr,groupId);
	}
	
	/**
	 * 根据服务器组ID查询该组中服务器信息,用于服务器分组选择列表展示
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List<?> queryServerTextByGroupId(String groupId){
		List list = this.serverGroupMappingDAO.queryServerTextByGroupId(groupId);
		List<ServerInfoForm> resultList = null;
		if(list!=null&&!list.isEmpty()){
			resultList = new ArrayList();
			ServerInfoForm serverInfoForm=null;
			for(int i=0;i<list.size();i++){
				Object[] server = (Object[])(list.get(i));
				serverInfoForm = new ServerInfoForm();
				serverInfoForm.setServerId(server[0].toString());
				serverInfoForm.setServerTag(server[1].toString());
				resultList.add(serverInfoForm);
			}
		}
		return resultList;
	}
}
