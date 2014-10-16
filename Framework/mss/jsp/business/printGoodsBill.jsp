<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		
		function query(){
			var recordType = document.getElementById("queryRecordType").value;
			if(recordType==""){
				alert("请选择单据类型！");
				return false;
			}
			document.goodsRecordBillForm.action = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&isStatistic=Y&accessType=bill";
			document.goodsRecordBillForm.submit();
		}
		
	</script>
	</head>

	<body>

		<form name="goodsRecordBillForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						客户账单
					</td>
				</tr>
			</table>

			<table width="95%" style="border-style:dashed;border-width:1px; border-color:#999999;" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"  style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1" width="18%">
						单据类型:
						<select name="queryRecordType" id="queryRecordType">
						<c:if test="${information.searchForm.queryRecordType!='1'&&information.searchForm.queryRecordType!='2'&&information.searchForm.queryRecordType!='3'}">
							<option value="2" selected>出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='2'}">
							<option value="2" selected>出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='3'}">
							<option value="2">出库</option>
							<option value="3" selected>退库</option>
						</c:if>
						</select>
					</td>
						<td class="qinggoudan_table_td1" width="15%">
							流水状态:
							<select name="queryRecordState">
								<c:if test="${information.searchForm.queryRecordState!='0'&&information.searchForm.queryRecordState!='1'}">
										<option value="">...请选择...</option>
										<option value="0">未付款</option>
										<option value="1">已付款</option>
								</c:if>
								<c:if test="${information.searchForm.queryRecordState=='0'}">
										<option value="">...请选择...</option>
										<option value="0" selected>未付款</option>
										<option value="1">已付款</option>
								</c:if>
								<c:if test="${information.searchForm.queryRecordState=='1'}">
										<option value="">...请选择...</option>
										<option value="0">未付款</option>
										<option value="1" selected>已付款</option>
								</c:if>
							</select>
						</td>
					<td class="qinggoudan_table_td1">
						购买人:
						<pub:link sql="<%=SpmsConstants.QUERY_CLIENT_INFO_WITH_NICK%>" num="1" title="---请选择---" 
							next="false" name="queryClientInfo" mvalue="${information.searchForm.queryClientInfo}" />
					</td>
					</tr>
					<tr>
					<td class="qinggoudan_table_td01" width="15%">
						起始日期:<pub:dtp format="yyyy-MM-dd" name="queryStartTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryStartTime}"/>
					</td>
					<td class="qinggoudan_table_td01" width="20%">
						截至日期:<pub:dtp format="yyyy-MM-dd" name="queryEndTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryEndTime}"/>
					</td>
					<td class="qinggoudan_table_td01" colspan="3">
						<input type="button" class="anniu_s_out" value="搜  索 " onMouseOver="className='anniu_s_out'"
							onMouseOut="className='anniu_s_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('goodsRecordBillForm')" class="anniu_s_out" value="重  填 "
							onMouseOver="className='anniu_s_out'" onMouseOut="className='anniu_s_out'">
					</td>
				</tr>
			</table>
			

			<br><br>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户姓名:
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.clientInfo.clientName }
					</td>
					<td width="20%" align="center" class="qinggoudan_table_title">
						联系电话:
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.clientInfo.clientTel }
					</td>
				</tr>
				<tr>
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户昵称:
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.clientInfo.clientNick }
					</td>
					<td width="20%" align="center" class="qinggoudan_table_title">
						邮政编码:
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.clientInfo.zipCode }
					</td>
				</tr>
				<tr>
					<td width="20%" align="center" class="qinggoudan_table_title">
						收货地址:
					</td>
					<td align="left" class="qinggoudan_table_td1" colspan="3">
						&nbsp;${information.clientInfo.clientAddr }
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<br><br>
				<tr>
					<td width="5%" class="qinggoudan_table_title">
						序号
					</td>
					<td class="qinggoudan_table_title" width="12%">
						物品名称
					</td>
					<td class="qinggoudan_table_title" width="8%">
						物品编号
					</td>
					<td class="qinggoudan_table_title" width="8%">
						销售单价
					</td>
					<td class="qinggoudan_table_title" width="10%">
						数量
					</td>
					<td class="qinggoudan_table_title" width="10%">
						流水类型
					</td>
					<td class="qinggoudan_table_title" width="10%">
						客户昵称
					</td>
					<td class="qinggoudan_table_title" width="10%">
						状态
					</td>
					<td class="qinggoudan_table_title" width="10%">
						时间
					</td>
					<td class="qinggoudan_table_title" width="10%">
						小计
					</td>
				</tr>
					<c:set var="totalMoney" value="0"></c:set>
				<c:forEach var="goodsRecord" items="${information.goodsRecordList}" varStatus="status">
					<tr <c:if test="${goodsRecord.recordState=='2'}">bgcolor="#fff546"</c:if> >
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<td class="qinggoudan_table_td2" width="15%" >
							&nbsp;${fn:escapeXml(goodsRecord.goodsName)}
						</td>
						<td class="qinggoudan_table_td2" width="8%" >
							&nbsp;${fn:escapeXml(goodsRecord.goodsCode)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.salePrice}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.goodsCount}
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${goodsRecord.recordType eq 2}">
								购买
								<c:if test="${goodsRecord.recordState!='2'}">
									<c:set var="totalMoney" value="${totalMoney+goodsRecord.salePrice*goodsRecord.goodsCount}"></c:set>
								</c:if>
							</c:if>
							<c:if test="${goodsRecord.recordType eq 3}">
								退货
							<c:set var="totalMoney" value="${totalMoney+goodsRecord.salePrice*goodsRecord.goodsCount}"></c:set>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.clientInfo.clientNick}
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${goodsRecord.recordState=='0'}">
								未付款
							</c:if>
							<c:if test="${goodsRecord.recordState=='1'}">
								已付款
							</c:if>
							<c:if test="${goodsRecord.recordState=='2'}">
								<a style="color:red;">已退货</a>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;<pub:FormatDate  dateNumber="${goodsRecord.createTime}"/>
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;<fmt:formatNumber  value="${goodsRecord.goodsCount*goodsRecord.salePrice}"  pattern="#,#00.00#"/>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="qinggoudan_table_td1" align="right" colspan="10">总金额：<fmt:formatNumber  value="${totalMoney}"  pattern="#,#00.00#"/>&nbsp;元</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1" align="left" colspan="10">
					经办人：_________________&nbsp;&nbsp;&nbsp;&nbsp;检验人：_________________&nbsp;&nbsp;&nbsp;&nbsp;包装人：_________________</td>
				</tr>
			</table>
			<br><br>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value="打印账单" onclick="window.print();" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<!-- input type="button" class="anniu_out" value="返回 " onclick="goList('${contextPath }/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList','viewOrEdit')"
							 onMouseOver="className='anniu_over'"	onMouseOut="className='anniu_out'"-->
							<input type="button" class="anniu_out" value="返回 " onclick="history.go(-1);"
							 onMouseOver="className='anniu_over'"	onMouseOut="className='anniu_out'">
						</td>
					</tr>
			</table>

		</form>

	</body>
</html>
