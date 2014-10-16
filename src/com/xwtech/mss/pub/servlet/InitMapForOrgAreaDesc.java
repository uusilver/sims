package com.xwtech.mss.pub.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.xwtech.framework.pub.web.FrameworkApplication;
//import com.xwtech.mss.pub.dao.system.COrganizationNjDAO;
import com.xwtech.mss.pub.db.DbCollection;

public class InitMapForOrgAreaDesc extends HttpServlet
{

	/**
	 * 系统启动时候加载此类,作用如下:
	 * 将营业厅的orgId---营业厅的对象(COrganizationNj)
	 * 区域的COrganizationTypeId(对应type和functionId)---区域对象COrganizationType
	 * 以key---value的形式保存在静态MAP中
	 * 在程序中只需从map中即可找到营业厅id对应的名称或者区域的type和functionId来寻找区域的名称
	 */
	public void init() throws ServletException
	{
		super.init();
		
		DbCollection dbCollection = FrameworkApplication.dbCollection;
	
//		COrganizationNjDAO ciOrganizationNjDAO = dbCollection.getCiOrganizationNjDAO();

//		ciOrganizationNjDAO.putAllOrgAndAreaIntoMap();
	}
	
}
