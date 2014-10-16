<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统 - 日结算报表</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		
		
		function selectGoodsType(){
			var url = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&accessType=selectTypeByTree&perPageCount=0";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		
		function query(){
			var statisticStartTime = document.getElementById("queryStartTime").value;
			var statisticEndTime = document.getElementById("queryEndTime").value;
			var payStartTime = document.getElementById("queryPayStartTime").value;
			var payEndTime = document.getElementById("queryPayEndTime").value;
			var date = new Date();
			var currentDate = date.getFullYear() + "" + ((date.getMonth()+1) < 10 ? "0" + (date.getMonth()+1) : (date.getMonth()+1)) 
					+ "" + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
			
			if(statisticEndTime=="" && statisticStartTime=="" && payEndTime=="" && payStartTime==""){
				alert("请选择【起始日期】和【截止日期】或者【付款起始日期】和【付款截止日期】以便进行结算统计！");
				return false;
			}
			if(statisticEndTime!=""&&(parseInt(currentDate)<parseInt(statisticEndTime.replace(/-/g, '')))){
				alert("所选【截止日期】大于当前日期，请重新选择！");
				return false;
			}
			if(statisticStartTime!=""&&(parseInt(currentDate)<parseInt(statisticStartTime.replace(/-/g, '')))){
				alert("所选【起始日期】大于当前日期，请重新选择！");
				return false;
			}
			if(payEndTime!=""&&(parseInt(currentDate)<parseInt(payEndTime.replace(/-/g, '')))){
				alert("所选【付款截止日期】大于当前日期，请重新选择！");
				return false;
			}
			if(payStartTime!=""&&(parseInt(currentDate)<parseInt(payStartTime.replace(/-/g, '')))){
				alert("所选【付款起始日期】大于当前日期，请重新选择！");
				return false;
			}
			document.goodsRecordStatisticForm.submit();
		}
		</script>
	</head>

	<body>

		<form name="goodsRecordStatisticForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=statisticGoodsRecord">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品进销存结算统计
					</td>
				</tr>
			</table>

			<table width="95%" style="border-style:dashed;border-width:1px; border-color:#999999;" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"  style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1" width="16%">
						起始日期:<pub:dtp format="yyyy-MM-dd" name="queryStartTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryStartTime}"/>
					</td>
					&nbsp;&nbsp;
					<td class="qinggoudan_table_td1" width="20%">
						付款起始日期:<pub:dtp format="yyyy-MM-dd" name="queryPayStartTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryPayStartTime}"/>
					</td>
					&nbsp;&nbsp;
					<td class="qinggoudan_table_td1">
						物品名称:
						<input name="queryGoodsName" type="text" class="qinggoudan_input023" size="10"
							value="${information.searchForm.queryGoodsName}" maxlength="50" />
					</td>
					<td class="qinggoudan_table_td1" width="16%">
						流水类型:
						<select name="queryRecordType">
						<c:if test="${information.searchForm.queryRecordType!='1'&&information.searchForm.queryRecordType!='2'&&information.searchForm.queryRecordType!='3'}">
							<option value="">...请选择...</option>
							<option value="1">入库</option>
							<option value="2">出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='1'}">
							<option value="">...请选择...</option>
							<option value="1" selected>入库</option>
							<option value="2">出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='2'}">
							<option value="">...请选择...</option>
							<option value="1">入库</option>
							<option value="2" selected>出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='3'}">
							<option value="">...请选择...</option>
							<option value="1">入库</option>
							<option value="2">出库</option>
							<option value="3" selected>退库</option>
						</c:if>
						</select>
					</td>
					<td class="qinggoudan_table_td1">
						客户昵称:
						<input name="queryClientNick" type="text" class="qinggoudan_input02" size="10"
							value="${information.searchForm.queryClientNick}" maxlength="50" />
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td01" width="16%">
						截止日期:<pub:dtp format="yyyy-MM-dd" name="queryEndTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryEndTime}"/>
					</td>
					&nbsp;&nbsp;
					<td class="qinggoudan_table_td01" width="18%">
						付款截止日期:<pub:dtp format="yyyy-MM-dd" name="queryPayEndTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryPayEndTime}"/>
					</td>
					<td class="qinggoudan_table_td01" width="30%">
						所属类别:
						<input name="queryGoodsType" id="goods_type" type="text" class="qinggoudan_input023" size="40"
							value="${information.searchForm.queryGoodsType}" maxlength="50" readonly="readonly"/>
							&nbsp;
							<input type="hidden" name="goodsTypeStr" id="goodsTypeStr" value="${information.searchForm.queryGoodsTypeStr}"/>
						<input type="button" class="anniu_s_out" value="选  择" onMouseOver="className='anniu_s_over'"
							onMouseOut="className='anniu_s_out'" onclick="selectGoodsType()">
					</td>
					<td class="qinggoudan_table_td01" width="9%">
						操 作 人:
						<pub:link sql="<%=SpmsConstants.QUERY_USER_INFO%>" num="1" title="---请选择---"
							next="false" name="queryOperator" mvalue="${information.searchForm.queryOperator}" />
					</td>
					<td class="qinggoudan_table_td01">
						<input type="button" class="anniu_s_out" value="搜   索 " onMouseOver="className='anniu_s_out'"
							onMouseOut="className='anniu_s_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('goodsRecordStatisticForm')" class="anniu_s_out" value="重   填 "
							onMouseOver="className='anniu_s_out'" onMouseOut="className='anniu_s_out'">
					</td>
				</tr>
			</table>

				<br/>
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td>
						<a style="color:red;">金额计算基本公式:盈利金额（毛利）= 出库金额  - 入库金额 - 退库金额</a>
					</td>
				</tr>
				<tr>
					<td>
						<a style="color:blue;">总金额: 操作数量*销售单价</a>
					</td>
				</tr>
				<tr>
					<td>
						<a style="color:orange;">预期净利润:操作数量*（销售单价 - 成本单价）</a>
					</td>
				</tr>
				<tr>
					<td>
						<a style="color:green;">实际净利润:操作数量*（顾客实际支付单价 - 成本单价）</a>
					</td>
				</tr>
				</table>
				<br/>
			<table width="95%" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="5%" class="qinggoudan_table_title">
						序号
					</td>
					<td class="qinggoudan_table_title" width="10%">
						物品名称
					</td>
					<td class="qinggoudan_table_title" width="10%">
						所属类别
					</td>
					<td class="qinggoudan_table_title" width="7%">
						流水类型
					</td>
					<td class="qinggoudan_table_title" width="7%">
						操作数量
					</td>
					<td class="qinggoudan_table_title" width="8%">
						付款状态
					</td>
					<td class="qinggoudan_table_title" width="8%">
						操作人
					</td>
					<td class="qinggoudan_table_title" width="12%">
						总金额(小计)
					</td>
					<td class="qinggoudan_table_title" width="12%">
						预期净利润(小计)
					</td>
					<td class="qinggoudan_table_title" width="12%">
						实际净利润(小计)
					</td>
				</tr>
				<c:set var="totalSalesVolume" value="0" scope="page"/>
				<c:set var="totalExpProfit" value="0" scope="page"/>
				<c:set var="totalRealProfit" value="0" scope="page"/>
				
				<c:set var="totalSalesVolumePayed" value="0" scope="page"/>
				<c:set var="totalSalesVolumeNotPayed" value="0" scope="page"/>
				<c:set var="totalExpProfitPayed" value="0" scope="page"/>
				<c:set var="totalExpProfitNotPayed" value="0" scope="page"/>
				<c:set var="totalRealProfitPayed" value="0" scope="page"/>
				<c:set var="totalRealProfitNotPayed" value="0" scope="page"/>
				
				<!--<c:set var="totalExportToBackFeeSalesVolume" value="0" scope="page"/>
				<c:set var="totalExportToBackFeeExpProfit" value="0" scope="page"/>
				<c:set var="totalExportToBackFeeRealProfit" value="0" scope="page"/>-->
				
				<c:forEach var="resultInfo" items="${information.resultList}" varStatus="status">
					<tr>
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<td class="qinggoudan_table_td2" width="15%" >
							${fn:escapeXml(resultInfo[0])}
						</td>
						<td class="qinggoudan_table_td2">
							${fn:escapeXml(resultInfo[1])}
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${fn:escapeXml(resultInfo[2])==1}">
								入库
							<c:set var="totalSalesVolume" value="${totalSalesVolume-resultInfo[9]}" scope="page"/>
							<c:set var="totalExpProfit" value="${totalExpProfit-resultInfo[7]}" scope="page"/>
							<c:set var="totalRealProfit" value="${totalRealProfit-resultInfo[8]}" scope="page"/>
							</c:if>
							<c:if test="${fn:escapeXml(resultInfo[2])==2}">
								出库
							<c:set var="totalSalesVolume" value="${totalSalesVolume+resultInfo[9]}" scope="page"/>
							<c:set var="totalExpProfit" value="${totalExpProfit+resultInfo[7]}" scope="page"/>
							<c:set var="totalRealProfit" value="${totalRealProfit+resultInfo[8]}" scope="page"/>
								<c:if test="${fn:escapeXml(resultInfo[3])==0}">
									<c:set var="totalSalesVolumeNotPayed" value="${totalSalesVolumeNotPayed+resultInfo[9]}" scope="page"/>
									<c:set var="totalExpProfitNotPayed" value="${totalExpProfitNotPayed+resultInfo[7]}" scope="page"/>
									<c:set var="totalRealProfitNotPayed" value="${totalRealProfitNotPayed+resultInfo[8]}" scope="page"/>
								</c:if>
								<c:if test="${fn:escapeXml(resultInfo[3])==1 || fn:escapeXml(resultInfo[3])==2}">
									<c:set var="totalSalesVolumePayed" value="${totalSalesVolumePayed+resultInfo[9]}" scope="page"/>
									<c:set var="totalExpProfitPayed" value="${totalExpProfitPayed+resultInfo[7]}" scope="page"/>
									<c:set var="totalRealProfitPayed" value="${totalRealProfitPayed+resultInfo[8]}" scope="page"/>
								</c:if>
							</c:if>
							<c:if test="${fn:escapeXml(resultInfo[2])==3}">
								退库
							<c:set var="totalSalesVolume" value="${totalSalesVolume-resultInfo[9]}" scope="page"/>
							<c:set var="totalExpProfit" value="${totalExpProfit-resultInfo[7]}" scope="page"/>
							<c:set var="totalRealProfit" value="${totalRealProfit-resultInfo[8]}" scope="page"/>
								<c:if test="${fn:escapeXml(resultInfo[3])==0}">
									<c:set var="totalSalesVolumeNotPayed" value="${totalSalesVolumeNotPayed-resultInfo[9]}" scope="page"/>
									<c:set var="totalExpProfitNotPayed" value="${totalExpProfitNotPayed-resultInfo[7]}" scope="page"/>
									<c:set var="totalRealProfitNotPayed" value="${totalRealProfitNotPayed-resultInfo[8]}" scope="page"/>
								</c:if>
								<c:if test="${fn:escapeXml(resultInfo[3])==1}">
									<c:set var="totalSalesVolumePayed" value="${totalSalesVolumePayed-resultInfo[9]}" scope="page"/>
									<c:set var="totalExpProfitPayed" value="${totalExpProfitPayed-resultInfo[7]}" scope="page"/>
									<c:set var="totalRealProfitPayed" value="${totalRealProfitPayed-resultInfo[8]}" scope="page"/>
								</c:if>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							${fn:escapeXml(resultInfo[6])}
						</td>
						<td class="qinggoudan_table_td4">
							<c:if test="${fn:escapeXml(resultInfo[3])==0}">
								<a style="color:red;">未付款</a>
							</c:if>
							<c:if test="${fn:escapeXml(resultInfo[3])==1}">
								已付款
							</c:if>
							<c:if test="${fn:escapeXml(resultInfo[2])==1 && fn:escapeXml(resultInfo[3])==9}">
								完成
							</c:if>
							<c:if test="${fn:escapeXml(resultInfo[2])==2 && fn:escapeXml(resultInfo[3])==2}">
								<a style="color:#0080FF;">已付款(出库转退库)</a>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							${fn:escapeXml(resultInfo[5])}
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${resultInfo[9] eq null}">
								￥<fmt:formatNumber  value="0"  pattern="#,#00.00#"/>
							</c:if>
							<c:if test="${resultInfo[9] != null}">
								￥<fmt:formatNumber  value="${resultInfo[9]}"  pattern="#,#00.00#"/>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${resultInfo[7] eq null}">
								￥<fmt:formatNumber  value="0"  pattern="#,#00.00#"/>
							</c:if>
							<c:if test="${resultInfo[7] != null}">
								￥<fmt:formatNumber  value="${resultInfo[7]}"  pattern="#,#00.00#"/>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${resultInfo[8] eq null}">
								￥<fmt:formatNumber  value="0"  pattern="#,#00.00#"/>
							</c:if>
							<c:if test="${resultInfo[8] != null}">
								￥<fmt:formatNumber  value="${resultInfo[8]}"  pattern="#,#00.00#"/>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
				<br/>
			<table width="95%" bgcolor="#ff7f50" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table" style="margin:0px;">
				<tr>
				<td class="qinggoudan_table_td2">合计</td>
					<td class="qinggoudan_table_td2">
							总金额:<fmt:formatNumber  value="${totalSalesVolume}"  pattern="#,#00.00#"/>&nbsp;元
							<table>
								<tr>
									<td>已付款:<fmt:formatNumber  value="${totalSalesVolumePayed}"  pattern="#,#00.00#"/></td>
								</tr>
								<tr>
									<td><a style="color:red;">未付款:<fmt:formatNumber  value="${totalSalesVolumeNotPayed}"  pattern="#,#00.00#"/></a></td>
								</tr>
							</table>
						</td>
						<td class="qinggoudan_table_td2">
							预期净利润总计:<fmt:formatNumber  value="${totalExpProfit}"  pattern="#,#00.00#"/>&nbsp;元
							<table>
								<tr>
									<td>已付款:<fmt:formatNumber  value="${totalExpProfitPayed}"  pattern="#,#00.00#"/></td>
								</tr>
								<tr>
									<td><a style="color:red;">未付款:<fmt:formatNumber  value="${totalExpProfitNotPayed}"  pattern="#,#00.00#"/></a></td>
								</tr>
							</table>
						</td>
						<td class="qinggoudan_table_td2">
							实际净利润总计:<fmt:formatNumber  value="${totalRealProfit}"  pattern="#,#00.00#"/>&nbsp;元
							<table>
								<tr>
									<td>已付款:<fmt:formatNumber  value="${totalRealProfitPayed}"  pattern="#,#00.00#"/></td>
								</tr>
								<tr>
									<td><a style="color:red;">未付款:<fmt:formatNumber  value="${totalRealProfitNotPayed}"  pattern="#,#00.00#"/></a></td>
								</tr>
							</table>
						</td>
				</tr>
			</table>
			<br><br>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value="打 印 报 表" onclick="window.print();" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
			</table>
		</form>

	</body>
</html>
