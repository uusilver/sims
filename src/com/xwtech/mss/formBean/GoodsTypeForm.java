package com.xwtech.mss.formBean;

public class GoodsTypeForm {

	private String currentPage;
	
	private String queryTypeName;
	
	private String queryTypeState;
	
	private String viewOrEdit;
	
	private String indexNO;
	
	private String loadNextNode;

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getQueryTypeName() {
		return queryTypeName;
	}

	public void setQueryTypeName(String queryTypeName) {
		this.queryTypeName = queryTypeName;
	}

	public String getQueryTypeState() {
		return queryTypeState;
	}

	public void setQueryTypeState(String queryTypeState) {
		this.queryTypeState = queryTypeState;
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

	public String getLoadNextNode() {
		return loadNextNode;
	}

	public void setLoadNextNode(String loadNextNode) {
		this.loadNextNode = loadNextNode;
	}
	
}
