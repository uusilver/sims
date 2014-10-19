package com.xwtech.mss.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xwtech.mss.bo.business.ServerGroupMappingBO;
import com.xwtech.mss.bo.business.ServerInfoBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.ServerInfoForm;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.ServerGroupMapping;
import com.xwtech.mss.pub.po.TransitServer;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.CommonOperation;

public class ServerInfoController extends MultiActionController {
	private static final Log log = LogFactory.getLog(ServerInfoController.class);

	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private ServerInfoBO serverInfoBO;
	
	private ServerGroupMappingBO serverGroupMappingBO;
	
	private OperLogBO operLogBO;
	
	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}


	public void setServerInfoBO(ServerInfoBO serverInfoBO) {
		this.serverInfoBO = serverInfoBO;
	}


	public void setServerGroupMappingBO(ServerGroupMappingBO serverGroupMappingBO) {
		this.serverGroupMappingBO = serverGroupMappingBO;
	}


	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	/**
	 * 保存物品信息和流水记录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveGoodsInfo(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//服务器IP
		final String serverIP = request.getParameter("serverIP");
		
		//所属国家
		final String countryID = request.getParameter("countryID");
		
		//所属省（州）
		final String provinceID = request.getParameter("provinceID");
		
		//所属城市
		final String cityID = request.getParameter("cityID");
		
		//所属分组
		final String serverGroupID = request.getParameter("serverGroupID");
		
		//所属类别,0 - 浏览下载 ；1 - 渗透攻击
		final String serverType = request.getParameter("serverType");
		
		//服务器状态
		final String serverStatus = request.getParameter("serverStatus");
		
		//失效日期，默认值设定为2020年12月31日
		final String invalidTime = request.getParameter("invalidTime");
		
		//约束：
		//0 - 无
		//1 - 不能作为最后一跳
		//2 - 不能作为第一跳
		//3 - 只能作为最后一跳
		//4 – 只能作为第一跳；
		final String limit = request.getParameter("limit");
		
		//服务地域
		final String regionID = request.getParameter("regionID");
		
		//备注
		final String note = request.getParameter("note");

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final Long userId = sysUser.getUserId();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 1.添加物品信息
				//服务器信息
				TransitServer transitServer = new TransitServer();

				try{
					//保存物品信息
					transitServer.setServerip(serverIP);
					transitServer.setCountryid(new Integer(countryID));
					transitServer.setProvinceid(new Integer(provinceID));
					transitServer.setCityid(new Integer(cityID));
					transitServer.setServertype(new Integer(serverType));
					transitServer.setServerstatus(new Integer(serverStatus));
					transitServer.setInvalidtime(DateUtils.convertString2Date(invalidTime));
					transitServer.setLimitation(new Integer(limit));
					transitServer.setRegionid(new Integer(regionID));
					transitServer.setNote(note);
					transitServer.setStatus(MssConstants.STATE_A);
					transitServer.setAddWho(new Integer(userId.toString()));
					transitServer.setAddtime(DateUtils.convertString2Date(DateUtils.getChar14()));
					serverInfoBO.saveOrUpdate(transitServer);
					
					//保存服务器所属分组信息
					if(serverGroupID!=null&&!"".equals(serverGroupID)){
						ServerGroupMapping sgMapping = new ServerGroupMapping();
						sgMapping.setServergroupid(new Integer(serverGroupID));
						sgMapping.setServerid(transitServer.getServerid());
						serverGroupMappingBO.saveOrUpdate(sgMapping);
					}
					
					//用户操作日志
					//新增服务器信息
					OperationLog oper = new OperationLog();
					oper.setDoObject(new Long(4));
					
					//4：物品信息
					oper.setObjType(new Long(4));
					
					//1:新增
					oper.setDoType(new Long(1));
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName("transit_server");
					oper.setDescription("新增服务器信息：【"+transitServer.getServerid()+" : "+serverIP+"】");
					operLogBO.save(oper);
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_SUCCESS));
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_SERVER_INFO_FAILED));
					status.setRollbackOnly();
					e.printStackTrace();
				}
				//resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
				resultInfos.setGotoUrl("/mss/jsp/business/serverInfoController.do?method=queryServerInfoList&viewOrEdit=add&perPageCount=0");
				resultInfos.setIsAlert(true);
				resultInfos.setIsRedirect(true);
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
	}
	
	
//	/**
//	 * 更新物品信息和流水记录
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws ServletRequestBindingException
//	 */
//	public ModelAndView updateGoodsInfo(HttpServletRequest request, HttpServletResponse response)
//	throws ServletRequestBindingException {
//
//		HashMap map = new HashMap();
//		final ResultInfos resultInfos = new ResultInfos();
//		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
//		
//		//物品id
//		final String goodsNum = request.getParameter("goodsNum");
//		
//		//物品名称
//		final String goodsName = request.getParameter("goodsName");
//		
//		//所属类别名称
//		final String goodsCode = request.getParameter("goodsCode");
//		
//		//所属类别名称
//		final String goodsTypeName = request.getParameter("goodsType");
//		
//		//物品所属类别,记录商品类别，格式如下：1,2,3  ;其中1,2,3分别为商品类别的记录序号
//		final String goodsType = request.getParameter("goodsTypeStr");
//		
//		//物品单价
//		final String goodsPrice = request.getParameter("goodsPrice");
//		
//		//预估单价
//		final String wishPrice = request.getParameter("wishPrice");
//		
//		//物品克重
//		final String goodsWeight = request.getParameter("goodsWeight");
//		
//		//物品数量
//		final String goodsCount = request.getParameter("goodsCount");
//		
//		//物品数量
//		final String goodsCountPlus = request.getParameter("goodsCountPlus");
//		
//		//物品状态
//		final String goodsState = request.getParameter("goodsState");
//		
//		//备注
//		final String goodsComment = request.getParameter("goodsComment");
//
//		CommonOperation commonOpera = new CommonOperation();
//		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
//		final String userName = sysUser.getUserName();
//		final Long userId = sysUser.getUserId();
//
//		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		transTemplate.execute(new TransactionCallbackWithoutResult() {
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//
//				// 1.获取物品和流水信息
//				// 更新
//				GoodsInfo goodsInfo = goodsInfoBO.findById(new Long(goodsNum));
//
//				//入库流水
//				List<GoodsRecord> recordList = goodsRecordBO.queryRecordListByGoodsAndState(goodsNum, null);
//				String oldGoodsName = goodsInfo.getGoodsName();
//				//更新物品信息操作日志
//				OperationLog oper = new OperationLog();
//				
//				String oldGoodsCode = goodsInfo.getGoodsCode();
//				
//				oper.setDescription("更新物品信息，ID：【"+goodsNum+"】，名称：【"+goodsInfo.getGoodsName()+" : "+oldGoodsCode+"】 为 【"+goodsName+" : "+goodsCode.toLowerCase()+"】，成本单价：【"+goodsInfo.getGoodsPrice()+"】 为 【"+goodsPrice+"】，预估单价：【"+goodsInfo.getWishPrice()+"】 为 【"+wishPrice+"】");
//				
//				try{
//					//更新物品信息
//					goodsInfo.setGoodsName(goodsName);
//					goodsInfo.setTypeName(goodsTypeName);
//					if(!goodsType.startsWith(",")){
//						goodsInfo.setGoodsType(","+goodsType+",");
//					}else{
//						goodsInfo.setGoodsType(goodsType);
//					}
//					goodsInfo.setGoodsCode(goodsCode.toUpperCase());
//					goodsInfo.setGoodsWeight(new Double(goodsWeight));
//					goodsInfo.setGoodsPrice(new BigDecimal(goodsPrice));
//					goodsInfo.setWishPrice(new BigDecimal(wishPrice));
//					goodsInfo.setGoodsCount(new Long(goodsCount)+new Long(goodsCountPlus));
//					goodsInfo.setGoodsState(goodsState);
//					goodsInfo.setGoodsComm(goodsComment);
//					goodsInfo.setModifyTime(DateUtils.getChar12());
//					goodsInfoBO.saveOrUpdate(goodsInfo);
//					
//					
//					oper.setDoObject(new Long(4));
//					
//					//4：物品信息
//					oper.setObjType(new Long(4));
//					
//					//2:更新
//					oper.setDoType(new Long(2));
//					oper.setDoTime(DateUtils.getChar12());
//					oper.setLoginName(userName);
//					oper.setTableName("goodsInfo");
//					operLogBO.save(oper);
//					
//					//更新流水信息
//					GoodsRecord goodsRecord = null;
//					if(recordList!=null&&recordList.size()>0){
//						for (int i = 0; i < recordList.size(); i++) {
//							goodsRecord = recordList.get(i);
//							goodsRecord.setGoodsName(goodsName);
////							goodsInfo.setGoodsCode(goodsCode.toUpperCase());
//							goodsRecord.setGoodsType(goodsTypeName);
//							if(!goodsType.startsWith(",")){
//								goodsRecord.setTypeNum(","+goodsType+",");
//							}else{
//								goodsRecord.setTypeNum(goodsType);
//							}
//							goodsRecord.setGoodsCode(goodsCode.toUpperCase());
////							if(goodsRecord.getRecordType().intValue()==new Integer(SpmsConstants.IN_RECORD)){
////								goodsRecord.setSalePrice(new Double(goodsPrice));
////								goodsRecord.setGoodsCount(new Long(goodsCount));
////								goodsRecord.setGoodsProfit(goodsRecord.getGoodsCount()*new Double(goodsPrice));
////								goodsRecord.setRecordComm(goodsComment);
////							}
//							goodsRecord.setModifyTime(DateUtils.getChar12());
//							goodsRecordBO.saveOrUpdate(goodsRecord);
//
//							
//							
//							//更新流水信息
//							OperationLog operFlow = new OperationLog();
//							operFlow.setDoObject(goodsRecord.getRecordType());
//							
//							//4：流水类型
//							operFlow.setObjType(goodsRecord.getRecordType());
//							
//							//2:更新
//							operFlow.setDoType(new Long(2));
//							operFlow.setDoTime(DateUtils.getChar12());
//							operFlow.setLoginName(userName);
//							operFlow.setTableName("goodsRecord");
//							operFlow.setDescription("更新流水，ID:【"+goodsRecord.getRecordNum()+"】的物品名称【"+oldGoodsName+" : "+oldGoodsCode+"】 为：【"+goodsName+" ： "+goodsCode.toUpperCase()+"】");
//							operLogBO.save(operFlow);
//						}
//					}
//					
//
//					//如果“补录数量”不为空，则新增入库流水记录
//					if(goodsCountPlus!=null && !"0".equals(goodsCountPlus)){
//						GoodsRecord goodsRecordPlus = new GoodsRecord();
//						goodsRecordPlus.setGoodsName(goodsName);
//						goodsRecordPlus.setGoodsNum(goodsInfo.getGoodsNum());
//						goodsRecordPlus.setGoodsCode(goodsCode.toUpperCase());
//						goodsRecordPlus.setGoodsType(goodsTypeName);
//						goodsRecordPlus.setTypeNum(","+goodsType+",");
//						goodsRecordPlus.setRecordType(new Long(SpmsConstants.IN_RECORD));
//						goodsRecordPlus.setRecordState(SpmsConstants.RECORD_FINISHED);
//						goodsRecordPlus.setSalePrice(new BigDecimal(goodsPrice));
//						goodsRecordPlus.setGoodsCount(new Long(goodsCountPlus));
//						goodsRecordPlus.setGoodsProfit(new BigDecimal(goodsPrice).multiply(new BigDecimal(goodsCountPlus)));
//						goodsRecordPlus.setRecordComm(goodsComment);
//						goodsRecordPlus.setCreateTime(DateUtils.getChar12());
//						goodsRecordPlus.setOperator(userId);
//						goodsRecordPlus.setUserName(userName);
//						goodsRecordBO.saveOrUpdate(goodsRecordPlus);
//						
//						//新增物品信息
//						OperationLog operPlus = new OperationLog();
//						operPlus.setDoObject(new Long(4));
//						
//						//4：物品信息
//						operPlus.setObjType(new Long(4));
//						
//						//1:新增
//						operPlus.setDoType(new Long(1));
//						operPlus.setDoTime(DateUtils.getChar12());
//						operPlus.setLoginName(userName);
//						operPlus.setTableName("goodsInfo");
//						operPlus.setDescription("为物品【"+goodsName+" ： "+goodsCode.toUpperCase()+"】补录数量：【"+goodsCountPlus+"】");
//						operLogBO.save(operPlus);
//					}
//					
//					resultInfos.add(new ResultInfo(ResultConstants.UPDATE_GOODS_INFO_SUCCESS));
//				}catch(Exception e){
//					resultInfos.add(new ResultInfo(ResultConstants.UPDATE_GOODS_INFO_FAILED));
//					status.setRollbackOnly();
//				}
//				resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
//				resultInfos.setIsAlert(true);
//				resultInfos.setIsRedirect(true);
//			}
//		});
//
//		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
//	}
	
	
	/**
	 * 查询物品信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryServerInfoList(HttpServletRequest request, HttpServletResponse response)
	throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		ServerInfoForm serverInfoForm = new ServerInfoForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		// 进行物品选择查询时使用
		String rowId = request.getParameter("trId");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		Long roleId = sysUser.getRole().getRoleId();

		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			String currentPage = request.getParameter("currentPage");
			serverInfoForm.setQueryServerStatus(SpmsConstants.STATE_A);
			serverInfoForm.setViewOrEdit(viewOrEdit);
			serverInfoForm.setCurrentPage(currentPage);
			SessionUtils.setObjectAttribute(request, "serverInfoFormSession", serverInfoForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			serverInfoForm = (ServerInfoForm) SessionUtils.getObjectAttribute(request, "serverInfoFormSession");
		} else {
			String currentPage = request.getParameter("currentPage");
			String queryCountryId = request.getParameter("queryCountryId");
			String queryProvinceId = request.getParameter("queryProvinceId");
			String queryCityId = request.getParameter("queryCityId");
			String queryServerType = request.getParameter("queryServerType");
			String queryServerStatus = request.getParameter("queryServerStatus");
			String queryRegionId = request.getParameter("queryRegionId");
			String queryStartTime = request.getParameter("queryStartTime");
			String queryEndTime = request.getParameter("queryEndTime");

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}

			serverInfoForm.setCurrentPage(currentPage);
			serverInfoForm.setQueryCountryId(queryCountryId);
			serverInfoForm.setQueryProvinceId(queryProvinceId);
			serverInfoForm.setQueryCityId(queryCityId);
			serverInfoForm.setQueryServerType(queryServerType);
			serverInfoForm.setQueryServerStatus(queryServerStatus);
			serverInfoForm.setQueryRegionId(queryRegionId);
			serverInfoForm.setQueryStartTime(queryStartTime);
			serverInfoForm.setQueryEndTime(queryEndTime);
			serverInfoForm.setViewOrEdit(viewOrEdit);

			SessionUtils.setObjectAttribute(request, "serverInfoFormSession", serverInfoForm);
		}
		HashMap serverResult = null;
		serverResult = serverInfoBO.queryServerInfoList(serverInfoForm,String.valueOf(MssConstants.COUNT_FOR_EVERY_PAGE));

		map.put("serverInfoList", (List) serverResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, serverResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, serverResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, serverResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", serverInfoForm);
		map.put("accessType", accessType);
		map.put("rowId", rowId);
		map.put("roleId", roleId.intValue());

		return new ModelAndView("/mss/jsp/business/serverInfoList.jsp", RequestNameConstants.INFORMATION, map);
		
	}
	
//	/**
//	 * 根据物品类别ID查询物品类别详细信息
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws ServletRequestBindingException
//	 */
//	public ModelAndView queryGoodsInfoById(HttpServletRequest request, HttpServletResponse response)
//															throws ServletRequestBindingException {
//		HashMap map = new HashMap();
//		String goodsNum = request.getParameter("goodsNum");
//		
//		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
//		
//		GoodsInfo goodsInfo = null;
//		String typeNameStr = "";
//		String typeNumStr = "";
//		if(goodsNum!=null&&!goodsNum.equals("")){
//			goodsInfo = goodsInfoBO.findById(new Long(goodsNum));
//			String goodsTypeStr = goodsInfo.getGoodsType();
//			List<GoodsType> goodsTypeList = goodsTypeBO.queryGoodsTypeByIds(goodsTypeStr.substring(1,goodsTypeStr.length()-1));
//			
//			map.put("goodsInfo", goodsInfo);
//			
//			HashMap goodsTypeResult = goodsTypeBO.queryGoodsTypeList(new GoodsTypeForm(),"0");
//			map.put("goodsTypeList", (List) goodsTypeResult.get(RequestNameConstants.RESULT_LIST));
//			map.put("viewOrEdit", viewOrEdit);
//		}
//		return new ModelAndView("/mss/jsp/business/goodsInfoAddByTree.jsp?viewOrEdit="+viewOrEdit, RequestNameConstants.INFORMATION, map);
//	}
//	
//	/**
//	 * 根据查询条件查询同类别物品信息个数
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws ServletRequestBindingException
//	 */
//	public ModelAndView querySameGoodsCount(HttpServletRequest request, HttpServletResponse response)
//	throws ServletRequestBindingException {
//		HashMap map = new HashMap();
//		String typeName = request.getParameter("typeName");
//		GoodsInfoForm searchForm = new GoodsInfoForm(); 
//		List list = null;
//		if(typeName!=null&&!typeName.equals("")){
//			typeName = typeName.lastIndexOf(",")!=-1 ? typeName.substring(0,typeName.length()-1):typeName; 
//			searchForm.setGoodsTypeStr(typeName);
//			list = goodsInfoBO.querySameGoodsCount(searchForm);
//		}
//		response.setContentType("text/html; charset=utf-8");
//		try {
//			response.getWriter().print(list.get(0));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * 删除用户选择的记录（逻辑删除）
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ModelAndView delGoodsInfo(final HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HashMap map = new HashMap();
//
//		final ResultInfos resultInfos = new ResultInfos();
//		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
//		resultInfos.setIsAlert(true);
//		resultInfos.setIsRedirect(true);
//		
//		CommonOperation commonOpera = new CommonOperation();
//		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
//		final String userName = sysUser.getUserName();
//
//		
//		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		transTemplate.execute(new TransactionCallbackWithoutResult() {
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//				
//				List goodsNameList = null;
//				String goodsNameStr = "";
//				
//				//删除操作返回值
//				int result = 0;
//				try {
//					// 获得页面表单信息
//					String goodsNumStr = request.getParameter("goodsNumStr");
//					if(goodsNumStr!=null&&!goodsNumStr.equals("")){
//						goodsNameList = goodsInfoBO.queryGoodsName(goodsNumStr.substring(0, goodsNumStr.length()-1));
//						// 根据权限ID删除相关权限信息
//						goodsInfoBO.delGoodsInfo(goodsNumStr.substring(0, goodsNumStr.length()-1));
//					}
//					
//					if(goodsNameList!=null&&goodsNameList.size()>0){
//						for (int i = 0; i < goodsNameList.size(); i++) {
//							goodsNameStr += ((GoodsInfo)goodsNameList.get(i)).getGoodsName();
//							if(i< goodsNameList.size()-1){
//								goodsNameStr += " | ";
//							}
//						}
//					}
//
//					
//					//保存物品删除记录
//					if(!"".equals(goodsNameStr)){
//						OperationLog oper = new OperationLog();
//						
//						oper.setDoObject(new Long(4));
//						
//						//4：物品信息
//						oper.setObjType(new Long(4));
//						
//						//删除物品信息
//						oper.setDoType(new Long(3));
//						oper.setDoTime(DateUtils.getChar12());
//						oper.setLoginName(userName);
//						oper.setTableName("goodsInfo");
//						oper.setDescription("删除物品信息，ID【"+goodsNumStr+"】，物品名称：【"+goodsNameStr+"】");
//						operLogBO.save(oper);
//					}
//					
//					resultInfos.add(new ResultInfo(ResultConstants.DEL_GOODS_INFO_SUCCESS));
//					resultInfos.setGotoUrl("/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList" + "&ifSession=yes");
//				} catch (Exception ex) {
//					resultInfos.setGotoUrl(null);
//					log.error("删除物品信息失败！");
//					resultInfos.add(new ResultInfo(ResultConstants.DEL_GOODS_INFO_FAILED));
//					resultInfos.setGotoUrl("/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList" + "&ifSession=yes");
//					ex.printStackTrace();
//				}
//			}
//		});
//
//		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
//	}
}
