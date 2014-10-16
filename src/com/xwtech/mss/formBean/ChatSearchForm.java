package com.xwtech.mss.formBean;

public class ChatSearchForm {
	
	private String createTime1;//起始时间
	private String createTime2;//截止时间
	private Long msgId;//留言ID
	private String userName;//用户名字
	private String msgInfo;//留言内容
	
    //当前页数
	String currentPage = "";
    //操作方式
    String viewOrEdit = "";
	public String getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}
	public String getCreateTime2() {
		return createTime2;
	}
	public void setCreateTime2(String createTime2) {
		this.createTime2 = createTime2;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getMsgInfo() {
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	public String getViewOrEdit() {
		return viewOrEdit;
	}
	public void setViewOrEdit(String viewOrEdit) {
		this.viewOrEdit = viewOrEdit;
	}
	
}
