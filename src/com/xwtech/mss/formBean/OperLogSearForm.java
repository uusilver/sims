package com.xwtech.mss.formBean;

public class OperLogSearForm
{
	
    private Long logId;
    private String loginName;
    private Long doType;
    private Long doObject;
    private Long objType;
    private String tableName;
    private String description;
    private String doTime;
    private String currentPage;

    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDoObject() {
		return doObject;
	}

	public void setDoObject(Long doObject) {
		this.doObject = doObject;
	}

	public String getDoTime() {
		return doTime;
	}

	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}

	public Long getDoType() {
		return doType;
	}

	public void setDoType(Long doType) {
		this.doType = doType;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getObjType() {
		return objType;
	}

	public void setObjType(Long objType) {
		this.objType = objType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public OperLogSearForm()
	{
		// TODO Auto-generated constructor stub
	}

	
	
	
}
