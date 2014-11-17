package com.tmind.mss.formBean;

public class ServerGroupForm {
	
	private String queryServerGroupId;
	
	private String queryServerGroupName;
	
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

	public String getQueryServerGroupId() {
		return queryServerGroupId;
	}

	public void setQueryServerGroupId(String queryServerGroupId) {
		this.queryServerGroupId = queryServerGroupId;
	}

	public String getQueryServerGroupName() {
		return queryServerGroupName;
	}

	public void setQueryServerGroupName(String queryServerGroupName) {
		this.queryServerGroupName = queryServerGroupName;
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
