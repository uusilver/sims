package com.xwtech.mss.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xwtech.framework.pub.tools.DividePageByList;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.OperLogSearForm;
import com.xwtech.mss.pub.web.SysOperLog;
/**
 * 日志管理
 * @author new
 *
 */
public class OperLogController extends MultiActionController
{
	private OperLogBO operLogBO;
	public OperLogController() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public OperLogBO getOperLogBO() {
		return operLogBO;
	}



	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}
	
	
/**
 * 日志查询
 * @param httpServletRequest
 * @param httpServletResponse
 * @param operLogSearForm
 * @return
 */	
	
	public ModelAndView listAll(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,OperLogSearForm operLogSearForm)
	{

		HttpSession httpSession=httpServletRequest.getSession();
		
		Map operLogListMap = new HashMap();
		
		String loginName = httpServletRequest.getParameter("loginName")==null?null:httpServletRequest.getParameter("loginName").trim();

		String doType = httpServletRequest.getParameter("doType")==null?null:httpServletRequest.getParameter("doType").trim();

		String objType = httpServletRequest.getParameter("objType")==null?null:httpServletRequest.getParameter("objType").trim();

		String currentPage = httpServletRequest.getParameter("currentPage")==null?null:httpServletRequest.getParameter("currentPage").trim();

		String logDesc = httpServletRequest.getParameter("logDesc")==null?null:httpServletRequest.getParameter("logDesc").trim();
		
		operLogSearForm.setLoginName(loginName);
		
		operLogSearForm.setDoType(doType==null||doType.equals("")?null:new Long(doType));
		
		operLogSearForm.setObjType(objType==null||objType.equals("")?null:new Long(objType));
		
		operLogSearForm.setDescription(logDesc);

		operLogSearForm.setCurrentPage(currentPage);
		
		httpServletRequest.setAttribute("command", operLogSearForm);
		
		httpSession.setAttribute("searchForm", operLogSearForm);
		
		HashMap operLogList = operLogBO.operLogSear(operLogSearForm);
		
		operLogListMap.put("searchForm", operLogSearForm);
		
		operLogListMap.put("list",(List)operLogList.get(RequestNameConstants.RESULT_LIST));
		
		operLogListMap.put("currentPage",String.valueOf(operLogList.get(RequestNameConstants.CURRENT_PAGE)));

		operLogListMap.put("recordTotalNum",String.valueOf(operLogList.get(RequestNameConstants.TOTAL_COUNT)));

		operLogListMap.put("totalPage",String.valueOf(operLogList.get(RequestNameConstants.TOTAL_PAGE)));
		
		return new ModelAndView("/mss/jsp/log/operLogSearch.jsp","operLogListMap",operLogListMap);

	}


}
