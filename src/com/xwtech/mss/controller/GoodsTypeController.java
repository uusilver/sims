package com.xwtech.mss.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.mss.bo.business.GoodsTypeBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.GoodsTypeForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.ClientInfo;
import com.xwtech.mss.pub.po.GoodsType;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.CommonOperation;


public class GoodsTypeController extends MultiActionController {
	
	private static final Log log = LogFactory.getLog(GoodsTypeController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private GoodsTypeBO goodsTypeBO;
	
	private OperLogBO operLogBO;

	public TransactionTemplate getTransTemplate() {
		return transTemplate;
	}

	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public GoodsTypeBO getGoodsTypeBO() {
		return goodsTypeBO;
	}

	public void setGoodsTypeBO(GoodsTypeBO goodsTypeBO) {
		this.goodsTypeBO = goodsTypeBO;
	}
	
	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	/**
	 * 保存物品类别信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveGoodsType(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//类别编号
		final String typeNum = request.getParameter("typeNum");
		
		//类别名称
		final String typeName = request.getParameter("typeName");
		
		//类别代号
		final String shortName = request.getParameter("shortName");
		
		//父类别
		final String fatherType = request.getParameter("fatherType");
		
		//类别状态
		final String typeState = request.getParameter("typeState");
		
		//备注
		final String typeComment = request.getParameter("typeComment");
		
		//用来区分标准保存页面和树形保存页面的操作方式，标准：0，树形：1
		final String saveFlag = request.getParameter("saveFlag");
		
		//标示树节点的id
		final String itemId = request.getParameter("itemId");

		final String[] typeNumTemp = new String[1];

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 1.添加物品类别
				GoodsType goodsType = null;
				//新增客户信息
				OperationLog oper = new OperationLog();
				oper.setDoObject(new Long(6));
				//5：客户信息
				oper.setObjType(new Long(6));
				// 新增
				if (null == typeNum || "".equals(typeNum)) {
					goodsType = new GoodsType();
					goodsType.setCreateTime(DateUtils.getChar12());
					oper.setDoType(new Long(1));
					oper.setDescription("新增物品类型：【"+typeName+"】");
					
				}
				// 更新
				else { 
					goodsType = goodsTypeBO.findById(new Long(typeNum));
					goodsType.setModifyTime(DateUtils.getChar12());
					oper.setDoType(new Long(2));
					oper.setDescription("修改物品类型：【"+goodsType.getTypeName()+"】为 【"+typeName+"】，ID-【"+goodsType.getTypeNum()+"】");
				}
//				else{
//					goodsType = (goodsTypeBO.findByProperty("STypeNum", itemId)).get(0);
//					goodsType.setModifyTime(DateUtils.getChar12());
//				}

				try{
					goodsType.setTypeName(typeName);
					goodsType.setTypeShort(shortName);
					if(fatherType!=null&&!fatherType.equals("")){
						GoodsType fatherGoodsType = goodsTypeBO.findById(new Long(fatherType));
						goodsType.setFatherType(fatherGoodsType);
					}
					goodsType.setSTypeNum(itemId);
					goodsType.setTypeState(typeState);
					goodsType.setTypeComm(typeComment);
					goodsTypeBO.save(goodsType);
					typeNumTemp[0] = String.valueOf(goodsType.getTypeNum().intValue());
					resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_TYPE_SUCCESS));

					//保存操作日志
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName("goodsType");
					operLogBO.save(oper);
					
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_TYPE_FAILED));
					status.setRollbackOnly();
				}
				resultInfos.setGotoUrl("/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&addOrView='edit'");
				resultInfos.setIsAlert(true);
				resultInfos.setIsRedirect(true);
			}
		});
		if(saveFlag!=null&&saveFlag.equals("0")){
			return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
		}else{
			response.setContentType("text/html; charset=utf-8");
			try {
				response.getWriter().print(typeNumTemp[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 查询物品类别信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryGoodsTypeList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		GoodsTypeForm goodsTypeForm = new GoodsTypeForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		String selectType = request.getParameter("selectType");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		//每页记录数，0为取全部数据
		String perPageCount = request.getParameter("perPageCount");
		
		String loadNextNode = request.getParameter("loadNextNode");

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			goodsTypeForm.setQueryTypeState(SpmsConstants.STATE_A);
			goodsTypeForm.setViewOrEdit(viewOrEdit);
			SessionUtils.setObjectAttribute(request, "goodsTypeFormSession", goodsTypeForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			goodsTypeForm = (GoodsTypeForm) SessionUtils.getObjectAttribute(request, "goodsTypeFormSession");
		} else {
			String currentPage = request.getParameter("currentPage");
			String queryTypeName = request.getParameter("queryTypeName");
			String queryTypeState = request.getParameter("queryTypeState");

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}

			if(queryTypeState==null||queryTypeState.equals("")){
				queryTypeState = "A";
			}
			goodsTypeForm.setCurrentPage(currentPage);
			goodsTypeForm.setQueryTypeName(queryTypeName);
			goodsTypeForm.setQueryTypeState(queryTypeState);
			goodsTypeForm.setViewOrEdit(viewOrEdit);
			goodsTypeForm.setLoadNextNode(loadNextNode);

			SessionUtils.setObjectAttribute(request, "goodsTypeFormSession", goodsTypeForm);
		}
		if(perPageCount==null||perPageCount.equals("")){
			perPageCount = String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE);
		}
		HashMap goodsTypeResult = goodsTypeBO.queryGoodsTypeList(goodsTypeForm,perPageCount);

		map.put("goodsTypeList", (List) goodsTypeResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, goodsTypeResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, goodsTypeResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, goodsTypeResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", goodsTypeForm);
		map.put("accessType", accessType);
		String gotoUrl = "/mss/jsp/business/goodsTypeManage.jsp";
		if(accessType!=null&&accessType.equals("addGoodsByTree")){
			gotoUrl = "/mss/jsp/business/goodsInfoAddByTree.jsp";
		}
		if(accessType!=null&&accessType.equals("selectTypeByTree")){
			gotoUrl = "/mss/jsp/business/goodsTypeTreeForSelect.jsp";
		}
		return new ModelAndView(gotoUrl, RequestNameConstants.INFORMATION, map);
		
	}
	
	/**
	 * 根据物品类别ID查询物品类别详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryGoodsSonTypeById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		String typeNum = request.getParameter("typeNum");
		
		List subTypeList = goodsTypeBO.querySubGoodsType(typeNum);
		
		response.setContentType("text/html; charset=utf-8");
		if(subTypeList!=null&&subTypeList.size()!=0){
			
			JSONArray jsonArray = JSONArray.fromObject(subTypeList);
			try {
				response.getWriter().print(jsonArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("write to response failed", e);
			}
		}else{
			try {
				response.getWriter().print("-1");
				logger.error("没有子节点");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("write to response failed", e);
			}
		}
		return null;
	}
	
	
	/**
	 * 根据物品类别ID查询物品类别详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryGoodsTypeById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		String typeNum = request.getParameter("typeNum");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		GoodsType goodsType = null;
		if(typeNum!=null&&!typeNum.equals("")){
			goodsType = goodsTypeBO.findById(new Long(typeNum));
			map.put("goodsType", goodsType);
		}
		return new ModelAndView("/mss/jsp/business/goodsTypeAdd.jsp?viewOrEdit="+viewOrEdit, RequestNameConstants.INFORMATION, map);
	}
	
	
	/**
	 * 删除用户选择的记录（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delGoodsType(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();
		
		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//删除操作返回值
				int result = 0;
				List goodsTypeList = null;
				String goodsTypeStr = "";
				try {
					// 获得页面表单信息
					String typeNumStr = request.getParameter("typeNumStr");
					if(typeNumStr!=null&&!typeNumStr.equals("")){
						goodsTypeList = goodsTypeBO.queryGoodsTypeByIds(typeNumStr.substring(0, typeNumStr.length()-1));
						// 根据权限ID删除相关权限信息
						goodsTypeBO.delGoodsType(typeNumStr.substring(0, typeNumStr.length()-1));
					}
					if(goodsTypeList!=null&&goodsTypeList.size()>0){
						for (int i = 0; i < goodsTypeList.size(); i++) {
							goodsTypeStr += ((GoodsType)goodsTypeList.get(i)).getTypeName();
							if(i< goodsTypeList.size()-1){
								goodsTypeStr += " | ";
							}
						}
					}
					
					if(!"".equals(goodsTypeStr)){
						//删除物品类型信息
						OperationLog oper = new OperationLog();
						oper.setDoObject(new Long(6));
						
						//5：客户信息
						oper.setObjType(new Long(6));
						
						//2:删除
						oper.setDoType(new Long(3));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName("goodsType");
						oper.setDescription("删除物品类型，ID：【"+typeNumStr+"】，类型名称：【"+goodsTypeStr+"】");
						operLogBO.save(oper);
					}	
					
					resultInfos.add(new ResultInfo(ResultConstants.DEL_GOODS_TYPE_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList" + "&ifSession=yes");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("删除物品类别失败！");
					resultInfos.add(new ResultInfo(ResultConstants.DEL_GOODS_TYPE_FAILED));
					ex.printStackTrace();
				}
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 删除用户选择的记录（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteGoodsTypeByItemId(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();
		
		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				//删除操作返回值
				int result = 0;
				List goodsTypeList = null;
				String goodsTypeStr = "";
				try {
					// 获得页面表单信息
					String itemId = request.getParameter("itemId");
					if(itemId!=null&&!itemId.equals("")){
						goodsTypeList = goodsTypeBO.queryGoodsTypeByIds(itemId);
						// 根据itemIds删除相关权限信息
						goodsTypeBO.delGoodsTypeByItemId(itemId);
					}
					log.error("删除物品类别成功！");
					
					//记录操作日志
					if(goodsTypeList!=null&&goodsTypeList.size()>0){
						for (int i = 0; i < goodsTypeList.size(); i++) {
							goodsTypeStr += ((GoodsType)goodsTypeList.get(i)).getTypeName();
							if(i< goodsTypeList.size()-1){
								goodsTypeStr += " | ";
							}
						}
					}
					
					if(!"".equals(goodsTypeStr)){
						//删除物品类型信息
						OperationLog oper = new OperationLog();
						oper.setDoObject(new Long(6));
						
						//5：客户信息
						oper.setObjType(new Long(6));
						
						//2:删除
						oper.setDoType(new Long(3));
						oper.setDoTime(DateUtils.getChar12());
						oper.setLoginName(userName);
						oper.setTableName("goodsType");
						oper.setDescription("删除物品类型，ID：【"+itemId+"】，类型名称：【"+goodsTypeStr+"】");
						operLogBO.save(oper);
					}	
				} catch (Exception ex) {
					log.error("删除物品类别失败！");
					ex.printStackTrace();
				}
			}
		});
		return null;
	}
	
}
