package com.tmind.mss.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tmind.framework.pub.tools.DividePageByList;
import com.tmind.framework.pub.web.RequestNameConstants;
import com.tmind.mss.bo.system.operator.OperLogBO;
import com.tmind.mss.formBean.OperLogSearForm;
import com.tmind.mss.pub.web.SysOperLog;
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
 * @param request
 * @param response
 * @param operLogSearForm
 * @return
 */	
	
	public ModelAndView listAll(HttpServletRequest request,HttpServletResponse response,OperLogSearForm operLogSearForm)
	{

		HttpSession httpSession=request.getSession();
		
		Map operLogListMap = new HashMap();
		
		String loginName = request.getParameter("loginName")==null?null:request.getParameter("loginName").trim();

		String doType = request.getParameter("doType")==null?null:request.getParameter("doType").trim();

		String objType = request.getParameter("objType")==null?null:request.getParameter("objType").trim();

		String currentPage = request.getParameter("currentPage")==null?null:request.getParameter("currentPage").trim();

		String logDesc = request.getParameter("logDesc")==null?null:request.getParameter("logDesc").trim();
		
		operLogSearForm.setLoginName(loginName);
		
		operLogSearForm.setDoType(doType==null||doType.equals("")?null:new Long(doType));
		
		operLogSearForm.setObjType(objType==null||objType.equals("")?null:new Long(objType));
		
		operLogSearForm.setDescription(logDesc);

		operLogSearForm.setCurrentPage(currentPage);
		
		request.setAttribute("command", operLogSearForm);
		
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
