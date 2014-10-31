package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.formBean.ServerInfoForm;
import com.xwtech.mss.pub.dao.business.TransitServerDAO;
import com.xwtech.mss.pub.po.TransitServer;

public class ServerInfoBO {

	private static final Log log = LogFactory.getLog(ServerInfoBO.class);
	
	private TransitServerDAO transitServerDAO;

	
	public void setTransitServerDAO(TransitServerDAO transitServerDAO) {
		this.transitServerDAO = transitServerDAO;
	}

	/**
	 * 保存或者更新服务器信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(TransitServer transientInstance) {
		this.transitServerDAO.save(transientInstance);
	}
	
	/**
	 * 根据服务器记录id查询服务器信息
	 * @param id
	 * @return
	 */
	public TransitServer findById(java.lang.Integer id) {
		return this.transitServerDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询服务器信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public HashMap queryServerInfoList(ServerInfoForm searchForm, String perPageCount){
		return this.transitServerDAO.queryServerInfoList(searchForm, perPageCount);
	}
	
	/**
	 * 批量删除选中的服务器记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	public int delServerInfo(String ServerNumStr) {
		return this.transitServerDAO.delServerInfo(ServerNumStr);
	}
	
	/**
	 * 查询所有服务器信息
	 * @param ServerId
	 * @return
	 */
	public List queryAllServer(){
		return this.transitServerDAO.queryAllServer();
	}
	
	/**
	 * 根据服务器id查询服务器所属服务器组
	 * @param serverId
	 * @return
	 */
	public List queryServerGroup(String serverId){
		return this.transitServerDAO.queryServerGroup(serverId);
	}
}
