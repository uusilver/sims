package com.xwtech.mss.formBean;

public class ClientInfoForm {
	private String currentPage;
	
	private String queryClientName;
	
	private String queryClientNick;
	
	private String queryClientType;
		
	private String queryClientState;
	
	private String viewOrEdit;

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getQueryClientName() {
		return queryClientName;
	}

	public void setQueryClientName(String queryClientName) {
		this.queryClientName = queryClientName;
	}

	public String getQueryClientNick() {
		return queryClientNick;
	}

	public void setQueryClientNick(String queryClientNick) {
		this.queryClientNick = queryClientNick;
	}

	public String getQueryClientType() {
		return queryClientType;
	}

	public void setQueryClientType(String queryClientType) {
		this.queryClientType = queryClientType;
	}

	public String getQueryClientState() {
		return queryClientState;
	}

	public void setQueryClientState(String queryClientState) {
		this.queryClientState = queryClientState;
	}

	public String getViewOrEdit() {
		return viewOrEdit;
	}

	public void setViewOrEdit(String viewOrEdit) {
		this.viewOrEdit = viewOrEdit;
	}
	
	
}
