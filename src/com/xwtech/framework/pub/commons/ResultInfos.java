package com.xwtech.framework.pub.commons;

/**
 * @title: 返回信息对象集合
 * @description:与information页面配合使用
 * 
 * @author: Yao
 * 
 */
public class ResultInfos
{

	// 是否进行跳转
	private boolean isRedirect = false;

	// 跳转到新的URL路径
	private String gotoUrlForward = null;

	// 是否刷新父窗口

	private boolean isRefreshParentWindow = false;

	// 是否刷新创始者窗口

	private boolean isRefreshOpenerWindow = false;

	// 关闭窗口
	private boolean isCloseWindow = false;

	//是否打开上层框架窗口
	private boolean isOpenTopWindow = false;

	//是否打开窗口
	private boolean isOpenWindow = false;

	// 是否提示
	private boolean isAlert = false;

	// 是否后退
	private boolean isBack = false;

	// 没有权限提示
	private boolean isPopedom = false;

	// 弹出提示信息
	private String alertInfo = null;

	// 页面显示信息
	private String promptInfo = null;

	// 是否超时
	private boolean isOverTime = false;

	public boolean getIsPopedom()
	{
		return isPopedom;
	}

	public void setIsPopedom(boolean isPopedom)
	{
		this.isPopedom = isPopedom;
	}

	public boolean getIsOverTime()
	{
		return isOverTime;
	}

	public void setIsOverTime(boolean isOverTime)
	{
		this.isOverTime = isOverTime;
	}

	public String getAlertInfo()
	{
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo)
	{
		this.alertInfo = alertInfo;
	}

	public String getGotoUrlForward()
	{
		return gotoUrlForward;
	}

	public void setGotoUrlForward(String gotoUrlForward)
	{
		this.gotoUrlForward = gotoUrlForward;
	}

	public boolean getIsAlert()
	{
		return isAlert;
	}

	public void setIsAlert(boolean isAlert)
	{
		this.isAlert = isAlert;
	}

	public boolean getIsBack()
	{
		return isBack;
	}

	public void setIsBack(boolean isBack)
	{
		this.isBack = isBack;
	}

	public boolean getIsCloseWindow()
	{
		return isCloseWindow;
	}

	public void setIsCloseWindow(boolean isCloseWindow)
	{
		this.isCloseWindow = isCloseWindow;
	}

	public boolean getIsRedirect()
	{
		return isRedirect;
	}

	public void setIsRedirect(boolean isRedirect)
	{
		this.isRedirect = isRedirect;
	}

	public boolean getIsRefreshOpenerWindow()
	{
		return isRefreshOpenerWindow;
	}

	public void setIsRefreshOpenerWindow(boolean isRefreshOpenerWindow)
	{
		this.isRefreshOpenerWindow = isRefreshOpenerWindow;
	}

	public boolean getIsRefreshParentWindow()
	{
		return isRefreshParentWindow;
	}

	public void setIsRefreshParentWindow(boolean isRefreshParentWindow)
	{
		this.isRefreshParentWindow = isRefreshParentWindow;
	}

	public String getPromptInfo()
	{
		return promptInfo;
	}

	public void setPromptInfo(String promptInfo)
	{
		this.promptInfo = promptInfo;
	}

	public boolean getIsOpenTopWindow()
	{
		return isOpenTopWindow;
	}

	public void setIsOpenTopWindow(boolean isOpenTopWindow)
	{
		this.isOpenTopWindow = isOpenTopWindow;
	}

	public boolean getIsOpenWindow()
	{
		return isOpenWindow;
	}

	public void setIsOpenWindow(boolean isOpenWindow)
	{
		this.isOpenWindow = isOpenWindow;
	}

}
