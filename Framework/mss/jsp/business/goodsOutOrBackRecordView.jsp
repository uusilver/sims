<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品出库</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		
	</head>

	<body>
		<form name="goodsOutRecordForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=saveGoodsOutOrBackRecord">&nbsp; 
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品出库
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						单据类型
					</td>
					<td align="left" class="qinggoudan_table_td1">
					<c:if test="${information.goodsRecord.recordType==2&&information.goodsRecord.recordType!=null}">
						出库单
					</c:if>
					<c:if test="${information.goodsRecord.recordType==3&&information.goodsRecord.recordType!=null}">
						退库单
					</c:if>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品名称
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.goodsRecord.goodsName}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属类别
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.goodsRecord.goodsType}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						库存数量
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.goodsRecord.currentCount}
						&nbsp;&nbsp;（此数值为出库或者退库时的库存数量）
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" id="salePriceTd" class="qinggoudan_table_title">
						销售单价
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.goodsRecord.salePrice}元
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" id="goodsCountTd" class="qinggoudan_table_title">
						出库数量
					</td>
					<td align="left" class="qinggoudan_table_td1">
						&nbsp;${information.goodsRecord.goodsCount}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						流水状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<c:if test="${information.goodsRecord.recordState=='0'}">
								未付款
							</c:if>
							<c:if test="${information.goodsRecord.recordState=='1'}">
								已付款
							</c:if>
						<span id="record_stateDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						购买人
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.clientInfo.clientName}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						联系电话
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.clientInfo.clientTel}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						收件地址
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.clientInfo.clientAddr}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						邮政编码
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.clientInfo.zipCode}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						Email
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.clientInfo.eMail}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						操作人
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.userName}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						创建时间
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;<pub:FormatDate  dateNumber="${information.goodsRecord.createTime}"/>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="recordComment" id="recordComment" rows="10" cols="30" readonly="readonly" >${information.goodsRecord.recordComm}</textarea>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 打印本页 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"	onclick="window.print();">
							&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList','viewOrEdit')">
					</td>
				</tr>
			</table>
				
		</form>
	</body>

</html>

