<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品流水详细信息</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript">
		
		</script>
	</head>

	<body>
		<form name="goodsInfoManageForm" method="post" action="${contextPath}/mss/jsp/business/goodsInfoController.do?method=saveGoodsInfo">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品流水详细信息
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<input type="hidden" name="recordNum" value="${information.goodsRecord.recordNum }" />
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品名称
					</td>
					<td align="left" class="qinggoudan_table_td1">
						${information.goodsRecord.goodsName}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品编号
					</td>
					<td align="left" class="qinggoudan_table_td1">
						${information.goodsRecord.goodsCode}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属类别
					</td>
					<td align="left" class="qinggoudan_table_td1">
						${information.goodsRecord.goodsType}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品单价
					</td>
					<td align="left" class="qinggoudan_table_td1">
						${information.goodsRecord.salePrice}&nbsp;&nbsp;元
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品数量
					</td>
					<td align="left" class="qinggoudan_table_td1">
						${information.goodsRecord.goodsCount}
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						流水类型
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<c:if test="${information.goodsRecord.recordType eq 1}">
								入库
							</c:if>
							<c:if test="${information.goodsRecord.recordType eq 2}">
								出库
							</c:if>
							<c:if test="${information.goodsRecord.recordType eq 3}">
								退库
							</c:if>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						流水状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<c:if test="${information.goodsRecord.recordState=='0'}">
								已付款
							</c:if>
							<c:if test="${information.goodsRecord.recordState=='1'}">
								未付款
							</c:if>
							<c:if test="${information.goodsRecord.recordState!='0'&&goodsRecord.recordState!='1'}">
								完成
							</c:if>
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
						<textarea name="recordComment" id="recordComment" rows="10" cols="30" readonly="readonly">${information.goodsRecord.recordComm}</textarea>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<!-- input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList','viewOrEdit')"-->
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="history.go(-1);">
					</td>
				</tr>
			</table>
				<input type="hidden" id="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				
		</form>
	</body>

</html>

