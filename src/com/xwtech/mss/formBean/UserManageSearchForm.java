package com.xwtech.mss.formBean;

public class UserManageSearchForm 
{
	private String userNum;
	private String userName;
	private String userDept;
	private String userRole; 
	private String indexNO; 
	private String returnForm; 
	private String returnNameInput;
	private String returnIdInput;
	private String returnTelInput;
	private String userState;
	
    //当前页数
    String currentPage = "";  
    //操作方式
    String viewOrEdit = "";  
	
	public String getUserDept() {
		return userDept;
	}
	
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserNum() {
		return userNum;
	}
	
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	
	public String getUserRole() {
		return userRole;
	}
	
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
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

	public String getReturnForm() {
		return returnForm;
	}

	public void setReturnForm(String returnForm) {
		this.returnForm = returnForm;
	}

	public String getReturnIdInput() {
		return returnIdInput;
	}

	public void setReturnIdInput(String returnIdInput) {
		this.returnIdInput = returnIdInput;
	}

	public String getReturnNameInput() {
		return returnNameInput;
	}

	public void setReturnNameInput(String returnNameInput) {
		this.returnNameInput = returnNameInput;
	}

	public String getReturnTelInput() {
		return returnTelInput;
	}

	public void setReturnTelInput(String returnTelInput) {
		this.returnTelInput = returnTelInput;
	}

	/**
	 * @return the userState
	 */
	public String getUserState() {
		return userState;
	}

	/**
	 * @param userState the userState to set
	 */
	public void setUserState(String userState) {
		this.userState = userState;
	}


}
