package com.xwtech.mss.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.xwtech.mss.bo.business.ClientInfoBO;
import com.xwtech.mss.bo.business.GoodsInfoBO;
import com.xwtech.mss.bo.business.GoodsRecordBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.formBean.GoodsRecordForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.po.ClientInfo;
import com.xwtech.mss.pub.po.GoodsInfo;
import com.xwtech.mss.pub.po.GoodsRecord;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.result.ResultConstants;
import com.xwtech.mss.pub.tools.ChineseSpellingToPinYin;
import com.xwtech.mss.pub.tools.CommonOperation;

public class GoodsRecordController extends MultiActionController {

	private static final Log log = LogFactory.getLog(GoodsRecordController.class);
	/**
	 * 注入回滚事务
	 */
	private TransactionTemplate transTemplate;
	
	private GoodsRecordBO goodsRecordBO;
	
	private GoodsInfoBO goodsInfoBO;
	
	private ClientInfoBO clientInfoBO;
	
	private OperLogBO operLogBO;

	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public void setGoodsRecordBO(GoodsRecordBO goodsRecordBO) {
		this.goodsRecordBO = goodsRecordBO;
	}
	
	public void setGoodsInfoBO(GoodsInfoBO goodsInfoBO) {
		this.goodsInfoBO = goodsInfoBO;
	}

	public void setClientInfoBO(ClientInfoBO clientInfoBO) {
		this.clientInfoBO = clientInfoBO;
	}

	public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	/**
	 * 查询物品流水信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryGoodsRecordList(HttpServletRequest request, HttpServletResponse response)
													throws ServletRequestBindingException {
		
		HashMap map = new HashMap();
		GoodsRecordForm goodsRecordForm = new GoodsRecordForm();
		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		// ifSession只在修改页面跳转至查询页面时有值
		String ifSession = request.getParameter("ifSession");
		
		//是否统计金额
		String isStatistic = request.getParameter("isStatistic");
		String clientId = "";
		ClientInfo clientInfo = null;
		if (accessType != null && accessType.equals("menu")) {// 菜单首次访问，默认查询状态有效的信息
			goodsRecordForm.setViewOrEdit(viewOrEdit);
			SessionUtils.setObjectAttribute(request, "goodsRecordFormSession", goodsRecordForm);
		} else if (ifSession != null && ifSession.equals("yes")) {
			goodsRecordForm = (GoodsRecordForm) SessionUtils.getObjectAttribute(request, "goodsRecordFormSession");
		} else {
			String currentPage = request.getParameter("currentPage");
			String queryRecordType = request.getParameter("queryRecordType");
			String queryRecordState = request.getParameter("queryRecordState");
			String queryGoodsType = request.getParameter("queryGoodsType");
			String queryGoodsName = request.getParameter("queryGoodsName");
			String queryClientNick = request.getParameter("queryClientNick");
			String queryGoodsTypeStr = request.getParameter("goodsTypeStr");
			String queryStartTime = request.getParameter("queryStartTime");
			String queryEndTime = request.getParameter("queryEndTime");
			String queryClientInfo = request.getParameter("queryClientInfo");
			String queryOperator = request.getParameter("queryOperator");
			String queryModifier = request.getParameter("queryModifier");
			String queryClientConfirm = request.getParameter("queryClientConfirm");
			

			if (null == currentPage || "".equals(currentPage)) {
				currentPage = "1";
			}

			goodsRecordForm.setCurrentPage(currentPage);
			goodsRecordForm.setQueryRecordType(queryRecordType);
			goodsRecordForm.setQueryRecordState(queryRecordState);
			goodsRecordForm.setQueryGoodsType(queryGoodsType);
			goodsRecordForm.setQueryGoodsName(queryGoodsName);
			goodsRecordForm.setQueryClientNick(queryClientNick);
			goodsRecordForm.setQueryGoodsTypeStr(queryGoodsTypeStr);
			goodsRecordForm.setQueryStartTime(queryStartTime);
			goodsRecordForm.setQueryEndTime(queryEndTime);
			goodsRecordForm.setViewOrEdit(viewOrEdit);
			goodsRecordForm.setQueryClientInfo(queryClientInfo);
			goodsRecordForm.setQueryOperator(queryOperator);
			goodsRecordForm.setQueryModifier(queryModifier);
			goodsRecordForm.setQueryClientConfirm(queryClientConfirm);
			clientId = queryClientInfo;
			SessionUtils.setObjectAttribute(request, "goodsRecordFormSession", goodsRecordForm);
		}
		HashMap goodsTypeResult = null;
		if(isStatistic!=null&&isStatistic.equals("Y")){
			goodsTypeResult = goodsRecordBO.queryGoodsRecordList(goodsRecordForm,"0");
		}else{
			goodsTypeResult = goodsRecordBO.queryGoodsRecordList(goodsRecordForm,String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));
		}

		String gotoUrl = "/mss/jsp/business/goodsRecordList.jsp";
		if(accessType!=null&&accessType.equals("bill")){
			gotoUrl = "/mss/jsp/business/printGoodsBill.jsp";
			if(clientId!=null&&!clientId.equals("")){
				clientInfo = clientInfoBO.findById(new Long(clientId));
			}
		}
		
		map.put("goodsRecordList", (List) goodsTypeResult.get(RequestNameConstants.RESULT_LIST));
		map.put(RequestNameConstants.TOTAL_COUNT, goodsTypeResult.get(RequestNameConstants.TOTAL_COUNT));
		map.put(RequestNameConstants.TOTAL_PAGE, goodsTypeResult.get(RequestNameConstants.TOTAL_PAGE));
		map.put(RequestNameConstants.CURRENT_PAGE, goodsTypeResult.get(RequestNameConstants.CURRENT_PAGE));
		map.put("searchForm", goodsRecordForm);
		map.put("accessType", accessType);
		map.put("isStatistic", isStatistic);
		map.put("clientInfo", clientInfo);

		return new ModelAndView(gotoUrl, RequestNameConstants.INFORMATION, map);
		
	}
	
	
	/**
	 * 保存物品入库流水信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveGoodsInRecord(HttpServletRequest request, HttpServletResponse response)
													throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//入库流水ID
//		final String recordNum = request.getParameter("recordNum");
		
		//物品id
		final String[] goodsNums = request.getParameterValues("goodsNum");
		
		//物品名称
		final String[] goodsNames = request.getParameterValues("goodsName");
		
		//物品所属类别名称
		final String[] typeNames = request.getParameterValues("typeName");
		
		//物品所属类别序号,记录商品类别，格式如下：1,2,3  ;其中1,2,3分别为商品类别的记录序号
		final String[] goodsTypeNums = request.getParameterValues("typeNum");
		
		//物品销售单价
		final String[] salePrices = request.getParameterValues("salePrice");
		
		//物品数量
		final String[] goodsCounts = request.getParameterValues("goodsCount");
		
		//备注
		final String[] recordComments = request.getParameterValues("recordComment");
		
		//流水状态 1：未付款，2：已付款
		//final String recordState = request.getParameter("recordState");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final Long userId = sysUser.getUserId();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				try{
					
					OperationLog oper = new OperationLog();
					oper.setDescription("物品入库");
					oper.setDoObject(new Long(1));
					oper.setDoTime(DateUtils.getChar12());
					oper.setDoType(new Long(1));
					oper.setLoginName(userName);
					oper.setObjType(new Long(1));
					oper.setTableName("goodsRecord");
					
					// 批量添加物品入库记录信息
					GoodsRecord goodsRecord = null;
					GoodsInfo goodsInfo = null;
					Long goodsNum = null;
					for (int i = 1; i < goodsNames.length; i++) {
						//创建流水实例，设置值后保存流水记录
						goodsRecord = new GoodsRecord();
						goodsRecord.setGoodsName(goodsNames[i]);
						goodsNum = new Long(goodsNums[i]);
						goodsRecord.setGoodsNum(goodsNum);
						goodsRecord.setGoodsType(typeNames[i]);
						goodsRecord.setTypeNum(goodsTypeNums[i]);
						goodsRecord.setRecordType(new Long(SpmsConstants.IN_RECORD));
						goodsRecord.setRecordState(SpmsConstants.RECORD_FINISHED);
						goodsRecord.setSalePrice(new BigDecimal(salePrices[i]));
						goodsRecord.setGoodsCount(new Long(goodsCounts[i]));
						goodsRecord.setGoodsProfit(new BigDecimal(salePrices[i]).multiply(new BigDecimal(goodsCounts[i])));
						goodsRecord.setRecordComm(recordComments[i]);
						goodsRecord.setOperator(userId);
						goodsRecord.setUserName(userName);
						goodsRecord.setCreateTime(DateUtils.getChar12());
						goodsRecordBO.saveOrUpdate(goodsRecord);
						
						//更新该物品的库存量
						goodsInfo = goodsInfoBO.findById(goodsNum);
						if(goodsInfo.getGoodsCount()==null){
							goodsInfo.setGoodsCount(new Long(goodsCounts[i]));
						}else{
							goodsInfo.setGoodsCount(goodsInfo.getGoodsCount()+new Long(goodsCounts[i]));
						}
						goodsInfo.setModifyTime(DateUtils.getChar12());
						goodsInfoBO.saveOrUpdate(goodsInfo);
					}
					resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_IN_RECORD_SUCCESS));
				}catch(Exception e){
					resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_IN_RECORD_FAILED));
					status.setRollbackOnly();
				}
				resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
				resultInfos.setIsAlert(true);
				resultInfos.setIsRedirect(true);
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
	}
	
	/**
	 * 保存物品出库流水信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveGoodsOutOrBackRecord(HttpServletRequest request, HttpServletResponse response)
													throws ServletRequestBindingException {

		HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//单据类型 出库单:2；退库单:3
		final String recordType = request.getParameter("recordType");
		
		//流水Id
		final String recordNum = request.getParameter("recordNum");
		
		//物品id
		final String goodsNum = request.getParameter("goodsNum");
		
		//物品名称
		final String goodsName = request.getParameter("goodsName");
		
		//物品编码
		final String goodsCode = request.getParameter("goodsCode");
		
		//物品所属类别名称
		final String typeName = request.getParameter("goodsType");
		
		//物品所属类别序号,记录商品类别，格式如下：1,2,3  ;其中1,2,3分别为商品类别的记录序号
		final String goodsTypeNum = request.getParameter("typeNum");
		
		//物品单价
		final String goodsPrice = request.getParameter("goodsPrice");
		
		//物品销售单价
		final String salePrice = request.getParameter("salePrice");
		
		//出库数量
		final String goodsCount = request.getParameter("goodsCount");
		
		//出库转退库标识
		final String isOutToBack = request.getParameter("outToBack");
		
		//原始出库或者退库数量
		final String oldGoodsCount = ((isOutToBack!=null&&"Y".equals(isOutToBack))||(request.getParameter("oldGoodsCount")==null||request.getParameter("oldGoodsCount").equals("")))?"0":request.getParameter("oldGoodsCount");
		
		//当前库存数量
		final String currentCount = request.getParameter("leftCount");
		
		//备注
		final String recordComment = request.getParameter("recordComment");
		
		//流水状态 0：未付款，1：已付款，2：已退库
		final String recordState = request.getParameter("recordState");
		
		//购买人id
		final String clientNum = request.getParameter("client_num");
		
		//购买人姓名
		final String clientName = request.getParameter("clientName");
		
		//购买人昵称
		final String clientNick = request.getParameter("clientNick");
		
		//购买人收件地址
		final String clientAddr = request.getParameter("clientAddr");
		
		//购买人联系电话
		final String clientTel = request.getParameter("clientTel");
		
		//邮政编码
		final String zipCode = request.getParameter("zipCode");
		
		//Email
		final String eMail = request.getParameter("eMail");
		
		//客户是否确认 Y/N
		final String clientConfirm = request.getParameter("clientConfirm");
		
		//确认付款时间
		final String payTime = request.getParameter("payTime");
		
		//最终付款单价
		final String finalPayPrice = request.getParameter("finalPayPrice");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final Long userId = sysUser.getUserId();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				//是否回滚
				boolean isRollback = false;
				GoodsRecord goodsRecord = null;
				if((isOutToBack!=null&&"Y".equals(isOutToBack))||(recordNum==null||recordNum.equals(""))){
					goodsRecord = new GoodsRecord();
					goodsRecord.setCreateTime(DateUtils.getChar12());
					//记录创建人id
					goodsRecord.setOperator(userId);
					goodsRecord.setUserName(userName);
				}else{
					goodsRecord = goodsRecordBO.findById(new Long(recordNum));
					
					//记录修改人id
					goodsRecord.setModifier(userId);
					
					//记录修改时间
					goodsRecord.setModifyTime(DateUtils.getChar12());
					goodsRecord.setModifyUserName(userName);
				}
				try{
					GoodsInfo goodsInfo = null;
					
					//1.获取物品的库存量
					goodsInfo = goodsInfoBO.findById(new Long(goodsNum));
					
					OperationLog oper = new OperationLog();
					oper.setDoObject(new Long(1));
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName("goodsRecord");
					
					/**
					 * 物品出库   判断物品库存量是否足够
					 */
					if(recordType.equals(SpmsConstants.OUT_RECORD)){
						oper.setObjType(new Long(2));
						
						//新增出库单
						oper.setDoType(new Long(1));
						
						int counts = 0;
						//当出库量>原出库量，库存量 - 差值
						if(new Integer(goodsCount) > new Integer(oldGoodsCount)){
							counts = new Integer(goodsCount).intValue()-new Integer(oldGoodsCount).intValue();
							oper.setDescription("物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】出库"+counts+"个");
							if(goodsInfo.getGoodsCount()==null||goodsInfo.getGoodsCount().intValue() < counts){
								resultInfos.add(new ResultInfo(ResultConstants.GOODS_COUNT_NOT_ENOUGH));
								log.error("物品库存量不足,出库失败！");
								isRollback = true;
								status.setRollbackOnly();
							}else{
								//计算结余库存
								goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() - counts);
								//计算利润
								goodsRecord.setGoodsProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(salePrice).subtract(goodsInfo.getGoodsPrice())));
							}
						}
						//当出库量<原出库量，库存量 + 差值
						else{
							counts = new Integer(oldGoodsCount).intValue() - new Integer(goodsCount).intValue();
							oper.setDescription("修改原物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】出库单-【"+goodsRecord.getRecordNum()+"】，减少出库量，即退库差量："+counts+"个");
							
							//修改出库单
							oper.setDoType(new Long(2));
							//计算结余库存
							goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() + counts);
							//计算利润
							goodsRecord.setGoodsProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(salePrice).subtract(goodsInfo.getGoodsPrice())));

						}
							goodsInfo.setModifyTime(DateUtils.getChar12());

							
							//根据用户最终付款单价计算销售利润
							if(clientConfirm!=null&&"Y".equals(clientConfirm)){
								
								goodsRecord.setClientConfirm("Y");
								
								//计算最终利润
								goodsRecord.setFinalProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(finalPayPrice).subtract(goodsInfo.getGoodsPrice())));
								
								//确认付款时间
								goodsRecord.setPayTime(payTime);
								
								//最终付款单价
								goodsRecord.setFinalPayPrice(new BigDecimal(finalPayPrice));
							}else{
								
								//用户未付款
								goodsRecord.setClientConfirm("N");
								
								//计算最终利润
								goodsRecord.setFinalProfit(new BigDecimal(0));
								
								//最终付款单价
								goodsRecord.setFinalPayPrice(new BigDecimal(0));
							}
					}
					
					/**
					 * 物品退库
					 */
					//更新库存量
					if(recordType.equals(SpmsConstants.BACK_RECORD)){
						
						//退库
						oper.setObjType(new Long(3));
						
						//新增 退库单
						oper.setDoType(new Long(1));
						
						if(isOutToBack!=null&&"Y".equals(isOutToBack)){
							//出库单转退库单
							oper.setObjType(new Long(7));
						}
						
						int counts = 0;
						//当退库量>原出库量，库存量 + 差值
						if(new Integer(goodsCount) > new Integer(oldGoodsCount)){
							counts = new Integer(goodsCount).intValue()-new Integer(oldGoodsCount).intValue();
							oper.setDescription("物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】退库"+counts+"个");
							if(goodsInfo.getGoodsCount()==null){
								goodsInfo.setGoodsCount(new Long(counts));
							}else{
								goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() + counts);
							}
						}
						//当退库量<原出库量，库存量 - 差值
						else{
							counts = new Integer(oldGoodsCount).intValue() - new Integer(goodsCount).intValue();
							
							oper.setDescription("修改原物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】退库单-【"+goodsRecord.getRecordNum()+"】，减少退库量，即出库差量："+counts+"个");
							
							//修改退库单
							oper.setDoType(new Long(2));
							
							if(goodsInfo.getGoodsCount()==null||goodsInfo.getGoodsCount().intValue() < counts){
								resultInfos.add(new ResultInfo(ResultConstants.GOODS_COUNT_NOT_ENOUGH));
								log.error("物品库存量不足,出库(减少退库量)失败！");
								isRollback = true;
								status.setRollbackOnly();
							}else{
								//计算结余库存
								goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() - counts);
							}
						}
						//计算退款金额(负利润)
						goodsRecord.setGoodsProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(salePrice).subtract(goodsInfo.getGoodsPrice())));

						//根据用户最终付款单价计算销售利润
						if(clientConfirm!=null&&"Y".equals(clientConfirm)){
							
							goodsRecord.setClientConfirm("Y");
							
							//计算最终利润
							goodsRecord.setFinalProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(finalPayPrice).subtract(goodsInfo.getGoodsPrice())));
							
							//确认付款时间
							goodsRecord.setPayTime(payTime);
							
							//最终付款单价
							goodsRecord.setFinalPayPrice(new BigDecimal(finalPayPrice));
						}else{
							
							//用户未付款
							goodsRecord.setClientConfirm("N");
							
							//计算最终利润
							goodsRecord.setFinalProfit(new BigDecimal(0));
							
							//最终付款单价
							goodsRecord.setFinalPayPrice(new BigDecimal(0));
						}
						
					}

					if(!isRollback){
						
						//保存操作记录
						operLogBO.save(oper);
						
						//2.创建流水实例，设置值后保存流水记录
						goodsRecord.setGoodsName(goodsName);
						goodsRecord.setRecordType(new Long(recordType));
						goodsRecord.setGoodsNum(new Long(goodsNum));
						goodsRecord.setGoodsType(typeName);
						goodsRecord.setGoodsCode(goodsCode);
						goodsRecord.setTypeNum(goodsTypeNum);
						goodsRecord.setRecordState(recordState);
						goodsRecord.setSalePrice(new BigDecimal(salePrice));
						goodsRecord.setGoodsCount(new Long(goodsCount));
						goodsRecord.setCurrentCount(new Integer(currentCount));
						goodsRecord.setRecordComm(recordComment);
						
						//3.如果购买人信息不存在，则保存购买人信息
						if(clientNum==null||clientNum.equals("")){
							ClientInfo clientInfo = new ClientInfo();
							clientInfo.setClientName(clientName);
							clientInfo.setClientNick(clientNick);
							clientInfo.setClientAddr(clientAddr);
							clientInfo.setClientTel(clientTel);
							clientInfo.setZipCode(zipCode);
							clientInfo.seteMail(eMail);
							clientInfo.setClientState(SpmsConstants.STATE_A);
							clientInfo.setClientType("1");
					      	ChineseSpellingToPinYin py2 = new ChineseSpellingToPinYin();
							clientInfo.setFirstLetterName(py2.getFirstletterByName(clientName));
							clientInfo.setFirstLetterNick(py2.getFirstletterByName(clientNick));
							clientInfoBO.saveOrUpdate(clientInfo);
							goodsRecord.setClientInfo(clientInfo);
						}else{
							goodsRecord.setClientInfo(clientInfoBO.findById(new Long(clientNum)));
						}

						//如果是由退库单转成的退库单，则记录原出库单ID
						if(isOutToBack!=null&&"Y".equals(isOutToBack)){
							goodsRecord.setFromRecordNum(new Long(recordNum));
						}
						
						//4.保存流水记录
						goodsRecordBO.saveOrUpdate(goodsRecord);

						
						//将原出库单状态改为“已退库”
						if(isOutToBack!=null&&"Y".equals(isOutToBack)){
							//获取原出库单
							GoodsRecord originalGoodsOutRecord = goodsRecordBO.findById(new Long(recordNum));
							//已退库
							originalGoodsOutRecord.setRecordState(SpmsConstants.RECORD_OUT_TO_BACK);
							goodsRecordBO.saveOrUpdate(originalGoodsOutRecord);
						}
						
						//5.更新库存量
						goodsInfoBO.saveOrUpdate(goodsInfo);

						if(recordType.equals(SpmsConstants.OUT_RECORD)){
							resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_OUT_RECORD_SUCCESS));
						}else{
							resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_BACK_RECORD_SUCCESS));
						}
					}
					
				}catch(Exception e){
					if(recordType.equals(SpmsConstants.OUT_RECORD)){
						log.error("操作异常，物品"+goodsName+":"+goodsNum+":"+goodsCount+":"+salePrice+"出库失败！");
						resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_OUT_RECORD_FAILED));
					}
					if(recordType.equals(SpmsConstants.BACK_RECORD)){
						log.error("操作异常，物品"+goodsName+":"+goodsNum+":"+goodsCount+":"+salePrice+"退库失败！");
						resultInfos.add(new ResultInfo(ResultConstants.ADD_GOODS_BACK_RECORD_FAILED));
					}
					resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
					status.setRollbackOnly();
					e.printStackTrace();
				}
				resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
				resultInfos.setIsAlert(true);
				resultInfos.setIsRedirect(true);
			}
		});

		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION,map );
	}
	

	
	/**
	 * 保存物品批量出库流水信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView saveGoodsBatchOutRecord(HttpServletRequest request, HttpServletResponse response)
													throws ServletRequestBindingException {

		final HashMap map = new HashMap();
		final ResultInfos resultInfos = new ResultInfos();
//		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		
		//单据类型 出库单:2；退库单:3
		final String recordType = request.getParameter("recordType");
		
		//流水Id
		final String recordNum = request.getParameter("recordNum");
		
		//物品id
		final String goodsNum = request.getParameter("goodsNum");
		
		//物品名称
		final String goodsName = request.getParameter("goodsName");
		
		//物品编码
		final String goodsCode = request.getParameter("goodsCode");
		
		//物品所属类别名称
		final String typeName = request.getParameter("goodsType");
		
		//物品所属类别序号,记录商品类别，格式如下：1,2,3  ;其中1,2,3分别为商品类别的记录序号
		final String goodsTypeNum = request.getParameter("typeNum");
		
		//物品单价
		final String goodsPrice = request.getParameter("goodsPrice");
		
		//物品销售单价
		final String salePrice = request.getParameter("salePrice");
		
		//出库数量
		final String goodsCount = request.getParameter("goodsCount");
		
		//原始出库或者退库数量
		final String oldGoodsCount = (request.getParameter("oldGoodsCount")==null||request.getParameter("oldGoodsCount").equals(""))?"0":request.getParameter("oldGoodsCount");
		
		//当前库存数量
		final String currentCount = request.getParameter("leftCount");
		
		//备注
		final String recordComment = request.getParameter("recordComment");
		
		//流水状态 0：未付款，1：已付款
		final String recordState = request.getParameter("recordState");
		
		//购买人id
		final String clientNum = request.getParameter("client_num");
		
		//购买人姓名
		final String clientName = request.getParameter("clientName");
		
		//购买人昵称
		final String clientNick = request.getParameter("clientNick");
		
		//购买人收件地址
		final String clientAddr = request.getParameter("clientAddr");
		
		//购买人联系电话
		final String clientTel = request.getParameter("clientTel");
		
		//邮政编码
		final String zipCode = request.getParameter("zipCode");
		
		//Email
		final String eMail = request.getParameter("eMail");
		
		//客户是否确认 Y/N
		final String clientConfirm = request.getParameter("clientConfirm");
		
		//确认付款时间
		final String payTime = request.getParameter("payTime");
		
		//最终付款单价
		final String finalPayPrice = request.getParameter("finalPayPrice");
		
		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final Long userId = sysUser.getUserId();
		final String userName = sysUser.getUserName();

		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				//是否回滚
				boolean isRollback = false;
				GoodsRecord goodsRecord = null;
				if(recordNum==null||recordNum.equals("")){
					goodsRecord = new GoodsRecord();
					goodsRecord.setCreateTime(DateUtils.getChar12());
					//记录创建人id
					goodsRecord.setOperator(userId);
					goodsRecord.setUserName(userName);
				}else{
					goodsRecord = goodsRecordBO.findById(new Long(recordNum));
					
					//记录修改人id
					goodsRecord.setModifier(userId);
					
					//记录修改时间
					goodsRecord.setModifyTime(DateUtils.getChar12());
					goodsRecord.setModifyUserName(userName);
				}
				try{
					GoodsInfo goodsInfo = null;
					
					//1.获取物品的库存量
					goodsInfo = goodsInfoBO.findById(new Long(goodsNum));
					
					OperationLog oper = new OperationLog();
					oper.setDoObject(new Long(1));
					oper.setDoTime(DateUtils.getChar12());
					oper.setLoginName(userName);
					oper.setTableName("goodsRecord");
					
					/**
					 * 物品出库   判断物品库存量是否足够
					 */
					if(recordType.equals(SpmsConstants.OUT_RECORD)){
						oper.setObjType(new Long(2));
						
						//新增出库单
						oper.setDoType(new Long(1));
						
						int counts = 0;
						//当出库量>原出库量，库存量 - 差值
						if(new Integer(goodsCount) > new Integer(oldGoodsCount)){
							counts = new Integer(goodsCount).intValue()-new Integer(oldGoodsCount).intValue();
							oper.setDescription("物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】出库"+counts+"个");
							if(goodsInfo.getGoodsCount()==null||goodsInfo.getGoodsCount().intValue() < counts){
								map.put("resultInfo","0|物品库存量不足,出库失败,操作回滚！");
								log.error("物品库存量不足,出库失败,操作回滚！");
								isRollback = true;
								status.setRollbackOnly();
							}else{
								//计算结余库存
								goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() - counts);
								//计算利润
								goodsRecord.setGoodsProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(salePrice).subtract(goodsInfo.getGoodsPrice())));
							}
						}
						//当出库量<原出库量，库存量 + 差值
						else{
							counts = new Integer(oldGoodsCount).intValue() - new Integer(goodsCount).intValue();
							oper.setDescription("修改原物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】出库单-【"+goodsRecord.getRecordNum()+"】，减少出库量，即退库差量："+counts+"个");
							
							//修改出库单
							oper.setDoType(new Long(2));
							//计算结余库存
							goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() + counts);
							//计算利润
							goodsRecord.setGoodsProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(salePrice).subtract(goodsInfo.getGoodsPrice())));
						}
							goodsInfo.setModifyTime(DateUtils.getChar12());
							
							//根据用户最终付款单价计算销售利润
							if(clientConfirm!=null&&"Y".equals(clientConfirm)){
								
								//用户确认付款
								goodsRecord.setClientConfirm("Y");
								
								//计算最终利润
								goodsRecord.setFinalProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(finalPayPrice).subtract(goodsInfo.getGoodsPrice())));
								
								//确认付款时间
								goodsRecord.setPayTime(payTime);
								
								//最终付款单价
								goodsRecord.setFinalPayPrice(new BigDecimal(finalPayPrice));
							}else{
								
								//用户未付款
								goodsRecord.setClientConfirm("N");
								
								//计算最终利润
								goodsRecord.setFinalProfit(new BigDecimal(0));
								
								//最终付款单价
								goodsRecord.setFinalPayPrice(new BigDecimal(0));
							}
					}
					
					/**
					 * 物品退库
					 */
					//更新库存量
					if(recordType.equals(SpmsConstants.BACK_RECORD)){
						
						//退库
						oper.setObjType(new Long(3));
						
						//新增退库单
						oper.setDoType(new Long(1));
						
						int counts = 0;
						//当退库量>原出库量，库存量 + 差值
						if(new Integer(goodsCount) > new Integer(oldGoodsCount)){
							counts = new Integer(goodsCount).intValue()-new Integer(oldGoodsCount).intValue();
							oper.setDescription("物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】退库"+counts+"个");
							if(goodsInfo.getGoodsCount()==null){
								goodsInfo.setGoodsCount(new Long(counts));
							}else{
								goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() + counts);
							}
						}
						//当退库量<原出库量，库存量 - 差值
						else{
							counts = new Integer(oldGoodsCount).intValue() - new Integer(goodsCount).intValue();
							
							oper.setDescription("修改原物品【"+goodsName+" : "+goodsInfo.getGoodsCode()+"】退库单-【"+goodsRecord.getRecordNum()+"】，减少退库量，即出库差量："+counts+"个");
							
							//修改退库单
							oper.setDoType(new Long(2));
							
							if(goodsInfo.getGoodsCount()==null||goodsInfo.getGoodsCount().intValue() < counts){
								map.put("resultInfo","0|物品库存量不足,出库失败,操作回滚！");
								log.error("物品库存量不足,出库(减少退库量)失败,操作回滚！");
								isRollback = true;
								status.setRollbackOnly();
							}else{
								//计算结余库存
								goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() - counts);
							}
						}
						//计算退款金额
						goodsRecord.setGoodsProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(salePrice).subtract(goodsInfo.getGoodsPrice())));
						
						//根据用户最终付款单价计算销售利润
						if(clientConfirm!=null&&"Y".equals(clientConfirm)){
							
							goodsRecord.setClientConfirm("Y");
							
							//计算最终利润
							goodsRecord.setFinalProfit(new BigDecimal(goodsCount).multiply(new BigDecimal(finalPayPrice).subtract(goodsInfo.getGoodsPrice())));
							
							//确认付款时间
							goodsRecord.setPayTime(payTime);
							
							//最终付款单价
							goodsRecord.setFinalPayPrice(new BigDecimal(finalPayPrice));
						}else{
							
							//用户未付款
							goodsRecord.setClientConfirm("N");
							
							//计算最终利润
							goodsRecord.setFinalProfit(new BigDecimal(0));
							
							//最终付款单价
							goodsRecord.setFinalPayPrice(new BigDecimal(0));
						}
					}

					if(!isRollback){
						
						//保存操作记录
						operLogBO.save(oper);
						
						//2.创建流水实例，设置值后保存流水记录
						goodsRecord.setGoodsName(goodsName);
						goodsRecord.setRecordType(new Long(recordType));
						goodsRecord.setGoodsNum(new Long(goodsNum));
						goodsRecord.setGoodsType(typeName);
						goodsRecord.setGoodsCode(goodsCode);
						goodsRecord.setTypeNum(goodsTypeNum);
						goodsRecord.setRecordState(recordState);
						goodsRecord.setSalePrice(new BigDecimal(salePrice));
						goodsRecord.setGoodsCount(new Long(goodsCount));
						goodsRecord.setCurrentCount(new Integer(currentCount));
						goodsRecord.setRecordComm(recordComment);
						
						//3.如果购买人信息不存在，则保存购买人信息
						if(clientNum==null||clientNum.equals("")){
							ClientInfo clientInfo = new ClientInfo();
							clientInfo.setClientName(clientName);
							clientInfo.setClientNick(clientNick);
							clientInfo.setClientAddr(clientAddr);
							clientInfo.setClientTel(clientTel);
							clientInfo.setZipCode(zipCode);
							clientInfo.seteMail(eMail);
							clientInfo.setClientState(SpmsConstants.STATE_A);
							clientInfo.setClientType("1");
					      	ChineseSpellingToPinYin py2 = new ChineseSpellingToPinYin();
							clientInfo.setFirstLetterName(py2.getFirstletterByName(clientName));
							clientInfo.setFirstLetterNick(py2.getFirstletterByName(clientNick));
							clientInfoBO.saveOrUpdate(clientInfo);
							goodsRecord.setClientInfo(clientInfo);
							map.put("clientNum", String.valueOf(clientInfo.getClientNum().longValue()));
						}else{
							goodsRecord.setClientInfo(clientInfoBO.findById(new Long(clientNum)));
							map.put("clientNum", clientNum);
						}
						
						//4.保存流水记录
						goodsRecordBO.saveOrUpdate(goodsRecord);
						
						//5.更新库存量
						goodsInfoBO.saveOrUpdate(goodsInfo);

						if(recordType.equals(SpmsConstants.OUT_RECORD)){
							map.put("resultInfo","1|保存物品出库记录成功！");
						}else{
							map.put("resultInfo","1|保存物品退库记录成功！");
						}
					}
					
				}catch(Exception e){
					if(recordType.equals(SpmsConstants.OUT_RECORD)){
						log.error("操作异常，物品"+goodsName+":"+goodsNum+":"+goodsCount+":"+salePrice+"出库失败！");
						map.put("resultInfo","0|保存物品出库记录失败,操作回滚！");
					}
					if(recordType.equals(SpmsConstants.BACK_RECORD)){
						log.error("操作异常，物品"+goodsName+":"+goodsNum+":"+goodsCount+":"+salePrice+"退库失败！");
						map.put("resultInfo","0|保存物品退库记录失败,操作回滚！");
					}
					//resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&addOrView='edit'");
					status.setRollbackOnly();
					e.printStackTrace();
				}
			}
		});

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().print(map.get("clientNum")+"|"+map.get("resultInfo"));
		} catch (IOException e) {
			logger.error("write to response failed", e);
		}
		return null;
	}
	
	
	/**
	 * 根据流水ID查询流水详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView queryGoodsRecordById(HttpServletRequest request, HttpServletResponse response)
															throws ServletRequestBindingException {
		HashMap map = new HashMap();
		
		//流水记录id
		String recordNum = request.getParameter("recordNum");
		
		//流水类型，1：入库，2：出库，3：退库
		String recordType = request.getParameter("recordType");
		
		String viewOrEdit = request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit").trim();
		
		String gotoUrl = "/mss/jsp/business/goodsRecordInfoView.jsp?viewOrEdit="+viewOrEdit;
		GoodsRecord goodsRecord = null;
		if(recordNum!=null&&!recordNum.equals("")){
			goodsRecord = goodsRecordBO.findById(new Long(recordNum));
			map.put("goodsRecord", goodsRecord);
		}
		if(recordType.equals("2")||recordType.equals("3")){
//			gotoUrl = "/mss/jsp/business/goodsOutOrBackRecordView.jsp?viewOrEdit="+viewOrEdit;
			gotoUrl = "/mss/jsp/business/goodsOutRecord.jsp?viewOrEdit="+viewOrEdit;
			map.put("viewOrEdit", viewOrEdit);
		}
		return new ModelAndView(gotoUrl, RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 统计进销存日结算报表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView statisticGoodsRecord(HttpServletRequest request, HttpServletResponse response)
												throws ServletRequestBindingException {
		HashMap map = new HashMap();

		// 页面首次访问，即由菜单点击访问
		String accessType = request.getParameter("accessType");
		
		if(accessType==null||accessType.equals("")){
			//结算起始日期
			String statistciStartDate = request.getParameter("queryStartTime");
			
			//结算日期
			String statistciEndDate = request.getParameter("queryEndTime");
			
			//客户付款起始日期
			String payStartDate = request.getParameter("queryPayStartTime");
			
			//客户付款结算日期
			String payEndDate = request.getParameter("queryPayEndTime");

			//物品类别
			String queryGoodsType = request.getParameter("queryGoodsType");
			
			//物品类别编码
			String queryGoodsTypeStr = request.getParameter("goodsTypeStr");

			//物品名称
			String queryGoodsName = request.getParameter("queryGoodsName");

			//操作人
			String queryOperator = request.getParameter("queryOperator");

			//客户昵称
			String queryClientNick = request.getParameter("queryClientNick");
			
			//流水类型
			String queryRecordType = request.getParameter("queryRecordType");

			GoodsRecordForm goodsRecordForm = new GoodsRecordForm();
			goodsRecordForm.setQueryStartTime(statistciStartDate);
			goodsRecordForm.setQueryEndTime(statistciEndDate);
			goodsRecordForm.setQueryGoodsType(queryGoodsType);
			goodsRecordForm.setQueryGoodsTypeStr(queryGoodsTypeStr);
			goodsRecordForm.setQueryGoodsName(queryGoodsName);
			goodsRecordForm.setQueryOperator(queryOperator);
			goodsRecordForm.setQueryClientNick(queryClientNick);
			goodsRecordForm.setQueryRecordType(queryRecordType);
			goodsRecordForm.setQueryPayStartTime(payStartDate);
			goodsRecordForm.setQueryPayEndTime(payEndDate);
			
			List resultList = goodsRecordBO.statisticGoodsRecord(goodsRecordForm, String.valueOf(SpmsConstants.COUNT_FOR_EVERY_PAGE));
			map.put("resultList", resultList);
			map.put("searchForm", goodsRecordForm);
		}
		return new ModelAndView("/mss/jsp/business/goodsRecordStatistic.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	/**
	 * 统计未付款记录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView statisticUnpayedCount(HttpServletRequest request, HttpServletResponse response)
												throws ServletRequestBindingException {
		HashMap map = new HashMap();
		
		//流水类型
		String recordState = request.getParameter("recordState");

		GoodsRecordForm goodsRecordForm = new GoodsRecordForm();
		goodsRecordForm.setQueryRecordState(recordState);
		List resultList = goodsRecordBO.statisticUnpayedCount(goodsRecordForm);
		map.put("resultList", resultList);
		return new ModelAndView("/mss/jsp/welcome.jsp", RequestNameConstants.INFORMATION, map);
	}
	
	
	/**
	 * 更新用户选择的流水的状态（由“未付款”修改为“已付款”）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateRecordState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		final ResultInfos resultInfos = new ResultInfos();
		map.put(RequestNameConstants.RESULTINFOS, resultInfos);
		resultInfos.setIsAlert(true);
		resultInfos.setIsRedirect(true);

		CommonOperation commonOpera = new CommonOperation();
		UserInfo sysUser = commonOpera.getLoginUserInfo(request).getSysUser();
		final String userName = sysUser.getUserName();
		
		
		// 获得页面表单信息
		final String recordNumStr = request.getParameter("recordNumStr");
		
		transTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//更新操作返回值
				int result = 0;
				List goodsRecordList = null;
				String goodsNameStr = "";
				try {
					if(recordNumStr!=null&&!recordNumStr.equals("")){
						goodsRecordList = goodsRecordBO.queryRecords(recordNumStr.substring(0, recordNumStr.length()-1));
						// 根据流水ID更新流水状态
						goodsRecordBO.updateRecordStateByRecordId(recordNumStr.substring(0, recordNumStr.length()-1));
					}
					
					if(goodsRecordList!=null&&goodsRecordList.size()>0){
						for (int i = 0; i < goodsRecordList.size(); i++) {
							GoodsRecord goodsRecord = (GoodsRecord)goodsRecordList.get(i);
							//更新流水操作日志
							OperationLog oper = new OperationLog();
							oper.setDoObject(goodsRecord.getRecordType());
							
							//5：客户信息
							oper.setObjType(goodsRecord.getRecordType());
							
							//2:更新
							oper.setDoType(new Long(2));
							oper.setDoTime(DateUtils.getChar12());
							oper.setLoginName(userName);
							oper.setTableName("goodsRecord");
							oper.setDescription("修改付款状态为已付款，流水ID：【"+goodsRecord.getRecordNum()+"】，物品名称：【"+goodsRecord.getGoodsName()+" : "+goodsRecord.getGoodsCode()+"】，物品类别：【"+goodsRecord.getGoodsType()+"】");
							operLogBO.save(oper);
						}
					}
					
					resultInfos.add(new ResultInfo(ResultConstants.UPDATE_RECORD_STATE_SUCCESS));
					resultInfos.setGotoUrl("/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&queryRecordState=0");
				} catch (Exception ex) {
					resultInfos.setGotoUrl(null);
					log.error("修改付款状态为已付款失败！");
					resultInfos.add(new ResultInfo(ResultConstants.UPDATE_RECORD_STATE_FAILED));
					ex.printStackTrace();
				}
		}});
			
		return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
	}
}
