<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品类别创建</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/GoodsManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		function selectGoodsType(){
			var url = "/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&accessType=sel&queryTypeState=A";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		</script>
	</head>

	<body>
		<form name="goodsInfoManageForm" method="post" action="${contextPath}/mss/jsp/business/goodsInfoController.do?method=saveGoodsInfo">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新物品信息
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<input type="hidden" name="goodsNum" value="${information.goodsInfo.goodsNum }" />
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品名称
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsName" id="goods_name" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.goodsInfo.goodsName}"
							onchange="checkGoodsName(this, '${information.goodsInfo.goodsName}', 'goods_info')">
						<span id="goods_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属类别
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsType" id="goods_type" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.typeNameStr}"
							onchange="checkGoodsType();">
						<input type="hidden" name="goodsTypeStr" id="goodsTypeStr" value="${information.typeNumStr}"/>
							<input type="button" class="anniu_s_out" value=" 选择 " onMouseOver="className='anniu_s_over'"
							onMouseOut="className='anniu_s_out'" onclick="selectGoodsType()">
						<span id="goods_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsPrice" id="goods_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsPrice}"
							onchange="checkGoodsPrice()">
							元&nbsp;(请按000.00格式填写)
					<span id="goods_priceDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品数量
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsCount" id="goods_count" type="text" class="qinggoudan_input02" size="20" maxlength="50" readonly="readonly"
							value="${information.goodsInfo.goodsCount}"
							onchange="checkGoodsCount(this, '${information.goodsInfo.goodsCount}', 'goods_count')">
					<span id="goods_countDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select name="goodsState">
						<c:if test="${information.goodsInfo.goodsState==null}">
						<option value="A">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsInfo.goodsState=='A'}">
						<option value="A" selected="selected">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsInfo.goodsState=='U'}">
						<option value="A">有效</option>
						<option value="U" selected="selected">无效</option>
						</c:if>
						</select>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="goodsComment" id="goodsComment" rows="10" cols="30" onchange="checkGoodsComment()">${information.goodsInfo.goodsComm}</textarea>
					<span id="goods_commDiv"></span>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveGoodsInfo()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList','viewOrEdit')">
					</td>
				</tr>
			</table>
				<input type="hidden" id="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				
		</form>
	</body>

</html>

