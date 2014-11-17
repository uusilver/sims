package com.tmind.mss.formBean;

public class ClientGroupForm {
	
	private String queryClientGroupId;
	
	private String queryClientGroupName;
	
	private String queryNote;
	
	private String currentPage;
	
	private String viewOrEdit;
	
	private String indexNO;
	
	private String queryStatus;

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getQueryClientGroupId() {
		return queryClientGroupId;
	}

	public void setQueryClientGroupId(String queryClientGroupId) {
		this.queryClientGroupId = queryClientGroupId;
	}

	public String getQueryClientGroupName() {
		return queryClientGroupName;
	}

	public void setQueryClientGroupName(String queryClientGroupName) {
		this.queryClientGroupName = queryClientGroupName;
	}

	public String getQueryNote() {
		return queryNote;
	}

	public void setQueryNote(String queryNote) {
		this.queryNote = queryNote;
	}

	public String getViewOrEdit() {
		return viewOrEdit;
	}

	public void setViewOrEdit(String viewOrEdit) {
		this.viewOrEdit = viewOrEdit;
	}

	public String getIndexNO() {
		return indexNO;
	}

	public void setIndexNO(String indexNO) {
		this.indexNO = indexNO;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}
	
	
}
