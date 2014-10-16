package com.xwtech.mss.formBean;

public class MenuForm
{
	private String currentPage;
	
	private String queryResourceName;
	
	private String queryRoleId;
	
	private String queryMenuLevel;
	
	private String queryMenuState;

	/**
	 * @return the queryMenuState
	 */
	public String getQueryMenuState() {
		return queryMenuState;
	}

	/**
	 * @param queryMenuState the queryMenuState to set
	 */
	public void setQueryMenuState(String queryMenuState) {
		this.queryMenuState = queryMenuState;
	}

	public String getQueryMenuLevel()
	{
		return queryMenuLevel;
	}

	public void setQueryMenuLevel(String queryMenuLevel)
	{
		this.queryMenuLevel = queryMenuLevel;
	}

	public String getQueryResourceName()
	{
		return queryResourceName;
	}

	public void setQueryResourceName(String queryResourceName)
	{
		this.queryResourceName = queryResourceName;
	}

	public String getQueryRoleId()
	{
		return queryRoleId;
	}

	public void setQueryRoleId(String queryRoleId)
	{
		this.queryRoleId = queryRoleId;
	}

	public String getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(String currentPage)
	{
		this.currentPage = currentPage;
	}
}
