
package com.xwtech.mss.bo.business;

import java.util.HashMap;
import java.util.List;

import com.xwtech.mss.formBean.ChatSearchForm;
import com.xwtech.mss.pub.dao.business.MpmsBoardMsgDAO;
import com.xwtech.mss.pub.dao.business.MpmsBoardMsgRespDAO;
import com.xwtech.mss.pub.po.MpmsBoardMsg;
import com.xwtech.mss.pub.po.MpmsBoardMsgResp;




/**
 * 
 * @author HX
 * 
 */
public class ChatBoardBO {


	/**
	 * 注入DAO
	 */
	private MpmsBoardMsgDAO mpmsBoardMsgDAO;
	
	
	private MpmsBoardMsgRespDAO mpmsBoardMsgRespDAO;
	
	public MpmsBoardMsgDAO getMpmsBoardMsgDAO() {
		return mpmsBoardMsgDAO;
	}

	public void setMpmsBoardMsgDAO(MpmsBoardMsgDAO mpmsBoardMsgDAO) {
		this.mpmsBoardMsgDAO = mpmsBoardMsgDAO;
	}
	public MpmsBoardMsgRespDAO getMpmsBoardMsgRespDAO() {
		return mpmsBoardMsgRespDAO;
	}

	public void setMpmsBoardMsgRespDAO(MpmsBoardMsgRespDAO mpmsBoardMsgRespDAO) {
		this.mpmsBoardMsgRespDAO = mpmsBoardMsgRespDAO;
	}
	/**
	 * 根据留言查询信息查找相应的留言信息
	 */
	public HashMap queryContractList(ChatSearchForm searchForm)
	{
		HashMap chatListResult = mpmsBoardMsgDAO.queryChatList(searchForm);
		return chatListResult;
	}
	



	/**
	 * 根据留言查询信息查找相应的留言信息
	 */
	public HashMap chatSearchList(ChatSearchForm form)
	{
		
		
		HashMap map=mpmsBoardMsgDAO.chatSearchList(form);
		return map;
	}

	public List rechatSearchList() {

		List list=mpmsBoardMsgRespDAO.rechatSearchList();
		return list;
	}

	/**
	 * 添加新的留言
	 */
	public void activityAdd(MpmsBoardMsgResp mpmsBoardMsgResp)
	{
		
		mpmsBoardMsgRespDAO.save(mpmsBoardMsgResp);
		
	}
	/**
	 * 根据留言信息ID查找留言
	 * @param id
	 * @return
	 */
	public MpmsBoardMsg findById(Long id){
		
		return mpmsBoardMsgDAO.findById(id);
		
	}
	/**
	 * 根据回复信息ID查找留言
	 * @param id
	 * @return
	 */
	public MpmsBoardMsgResp findByreId(Long id){
		
		return mpmsBoardMsgRespDAO.findById(id);
		
	}
	/**
	 * 删除回复信息
	 * @param id
	 * @return
	 */
	public void delReId(Long id){
		
		 mpmsBoardMsgRespDAO.delReId(id);
		 //MpmsBoardMsgResp mpmsBoardMsgResp=mpmsBoardMsgRespDAO.findById(id);
		 //mpmsBoardMsgRespDAO.delete(mpmsBoardMsgResp) ;
		 
	}
	/**
	 * 删除留言的回复信息
	 * @param id
	 * @return
	 */
	public void delRebychatId(Long chatId) {
		
		 mpmsBoardMsgRespDAO.delRebychatId(chatId) ;
		
	}
	/**
	 * 根据留言信息ID删除留言
	 * @param id
	 * @return
	 */
	public  void  delById(Long id){
		
		 mpmsBoardMsgDAO.delById(id);
		
	}
	
	/**
	 * 根据留言信息ID查找回复信息
	 * @param id
	 * @return
	 */
	public  List  ChatIdRebychatId(Long msgid){
		
		
		return mpmsBoardMsgRespDAO.ChatIdRebychatId(msgid);
		
	}
}
