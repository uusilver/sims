package com.xwtech.mss.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xwtech.framework.pub.result.ResultConstants;
import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.system.orgnization.OrgnizationBO;
import com.xwtech.mss.formBean.OrgnizationSearchForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.tools.StructForm;
import com.xwtech.mss.pub.web.SysOperLog;

/**
 * 09-02-09
 * @author jzy
 * 组织操作控制
 * 
 */
public class OrgnizationOperation extends MultiActionController
{
	private static final Log log = LogFactory.getLog(MenuController.class);

	//组织业务操作
	private OrgnizationBO orgnizationBO;

	//日志操作
	private SysOperLog sysOperLog;

	public OrgnizationBO getOrgnizationBO()
	{
		return orgnizationBO;
	}

	public void setOrgnizationBO(OrgnizationBO orgnizationBO)
	{
		this.orgnizationBO = orgnizationBO;
	}

	public SysOperLog getSysOperLog()
	{
		return sysOperLog;
	}

	public void setSysOperLog(SysOperLog sysOperLog)
	{
		this.sysOperLog = sysOperLog;
	}

	/**
	 * add by jzy 09-02-09
	 * title:查询组织信息（管理/查询）
	 * trigger:组织信息（管理/查询） 菜单
	 * operation:获得组织信息列表 传递至组织信息（管理/查询）页面
	 * return:map
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView enterInterface(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException
	{
		log.info("查询组织信息");
		//返回集合
		HashMap mapResult = new HashMap();

		try
		{
			//当前页
			String currentPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");
			//标识 查询(view)还是编辑(edit)
			String flag = request.getParameter("flag");
			//从request中获得参数填充form
			OrgnizationSearchForm orgnizationSearchForm = (OrgnizationSearchForm) StructForm.strucReqInfoForm(request, new OrgnizationSearchForm());
			//需要分页
			orgnizationSearchForm.setIsPaging("Y");

			Map mapOrgInfo = orgnizationBO.getOrgnizationListByForm(orgnizationSearchForm, currentPage, String
					.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));

			//传递组织信息列表
			mapResult.put("orgInfoList", (List) mapOrgInfo.get(RequestNameConstants.RESULT_LIST));
			//传递总记录数
			mapResult.put(RequestNameConstants.TOTAL_COUNT, mapOrgInfo.get(RequestNameConstants.TOTAL_COUNT));
			//传递总页数
			mapResult.put(RequestNameConstants.TOTAL_PAGE, mapOrgInfo.get(RequestNameConstants.TOTAL_PAGE));
			//传递当前页数
			mapResult.put(RequestNameConstants.CURRENT_PAGE, mapOrgInfo.get(RequestNameConstants.CURRENT_PAGE));
			//传递form
			mapResult.put("orgnizationSearchForm", orgnizationSearchForm);
			//标识
			mapResult.put("flag", flag);

//			request.getContextPath();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ModelAndView("/mss/jsp/sysManage/orgnizationList.jsp", "mapResult", mapResult);
	}

	/**
	 * add by jzy 09-02-10
	 * title:查询部门信息，用于弹出框选择部门
	 * trigger：新增部门，选择上级部门
	 * operation:获得组织信息列表 传递至弹出选择页面
	 * return:map
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView selectOrg(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException
	{
		//返回集合
		HashMap mapResult = new HashMap();

		try
		{
			mapResult.put("flag", request.getParameter("flag") == null ? "" : request.getParameter("flag").trim());

			//当前页
			String currentPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");

			//从request中获得参数填充form
			OrgnizationSearchForm orgnizationSearchForm = (OrgnizationSearchForm) StructForm.strucReqInfoForm(request, new OrgnizationSearchForm());

			//需要分页
			orgnizationSearchForm.setIsPaging("Y");

			Map mapOrgInfo = orgnizationBO.getOrgnizationListByForm(orgnizationSearchForm, currentPage, String
					.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));

			//传递组织信息列表
			mapResult.put("orgInfoList", (List) mapOrgInfo.get(RequestNameConstants.RESULT_LIST));
			//传递总记录数
			mapResult.put(RequestNameConstants.TOTAL_COUNT, mapOrgInfo.get(RequestNameConstants.TOTAL_COUNT));
			//传递总页数
			mapResult.put(RequestNameConstants.TOTAL_PAGE, mapOrgInfo.get(RequestNameConstants.TOTAL_PAGE));
			//传递当前页数
			mapResult.put(RequestNameConstants.CURRENT_PAGE, mapOrgInfo.get(RequestNameConstants.CURRENT_PAGE));
			//传递form
			mapResult.put("orgnizationSearchForm", orgnizationSearchForm);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ModelAndView("/mss/jsp/sysManage/orgnizationWindowList.jsp", "mapResult", mapResult);
	}

	/**
	 * title:检查部门名称是否存在
	 * trigger:新增部门信息，名称唯一性校验
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView checkOrgNameExist(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException
	{
		//得到兴趣分类名称
		String orgName = request.getParameter("orgName").trim();

		//需要过滤的部门id 如果没有则不需要过滤
		String notExistOrgId = request.getParameter("orgId") == null ? "" : request.getParameter("orgId").trim();

		boolean isExist = true;

		try
		{
			isExist = orgnizationBO.checkOrgNameExist(orgName, notExistOrgId);

			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			response.setLocale(Locale.CHINA);
			PrintWriter writer = response.getWriter();
			writer.write(String.valueOf(isExist));
			writer.flush();
		}
		catch (Exception e)
		{
			log.error("校验部门名称唯一性失败");
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * add by jzy
	 * title:检查部门名称是否存在
	 * trigger:新增部门信息，名称唯一性校验
	 * operation:保存部门信息和地市关系信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ModelAndView orgnizationAdd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("新增部门");
		//返回集合
		HashMap mapResult = new HashMap();
		final ResultInfos resultInfos;

		//从request中获得参数填充form
		OrgnizationSearchForm orgnizationSearchForm = (OrgnizationSearchForm) StructForm.strucReqInfoForm(request, new OrgnizationSearchForm());

		try
		{
			orgnizationBO.orgnizationAdd(orgnizationSearchForm, request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//生成失败响应信息对象
			resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_ADD_FAILED),
					"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
			mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);
			return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
		}

		//生成成功响应信息对象
		resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_ADD_SUCCESS),
				"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
		mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
	}

	/**
	 * 修改部门信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView orgnizationUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("更新部门");
		//返回集合
		HashMap mapResult = new HashMap();
		ResultInfos resultInfos;

		//从request中获得参数填充form
		try
		{
			OrgnizationSearchForm orgnizationSearchForm = (OrgnizationSearchForm) StructForm.strucReqInfoForm(request, new OrgnizationSearchForm());

			//返回上级是否正确
			boolean isUpOrgRight= orgnizationBO.orgnizationUpdate(orgnizationSearchForm, request);
			if(isUpOrgRight == false)
			{
				log.info("更新部门失败-上级部门包含自己");
				resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_UPORG_WRONG),
				"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
				mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);
				return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
			}
		}
		catch (Exception e)
		{
			log.info("更新部门失败");
			e.printStackTrace();
			resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_UPDATE_FAILED),
					"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
			mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);
			return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
		}

		resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_UPDATE_SUCCESS),
				"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
		mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	public ModelAndView viewOrg(HttpServletRequest request, HttpServletResponse response)
	{
		//返回集合
		HashMap mapResult = new HashMap();

		//传递处理标识 view查看/edit编辑
		mapResult.put("flag", request.getParameter("flag"));

		//得到部门机构对象
		mapResult.put("orgnization", orgnizationBO.findById(Long.valueOf(request.getParameter("orgId"))));

		return new ModelAndView("/mss/jsp/sysManage/orgnizationInfo.jsp", "mapResult", mapResult);
	}

	public ModelAndView delOrg(HttpServletRequest request, HttpServletResponse response)
	{
		log.info("删除部门");
		//返回集合
		HashMap mapResult = new HashMap();
		final ResultInfos resultInfos;
		
		try
		{
			//获得要删除的部门id列表
			String[] delOrgList = request.getParameter("orgIdList").split(",");

			if (delOrgList != null)
			{
				orgnizationBO.delOrgByIdArray(delOrgList, request);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_DEL_FAILED),
			"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
			mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);
			return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
		}
		
		resultInfos = new ResultInfos(true, true, new ResultInfo(ResultConstants.ORG_DEL_SUCCESS),
		"/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit");
		mapResult.put(RequestNameConstants.RESULTINFOS, resultInfos);
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, mapResult);
	}

}
