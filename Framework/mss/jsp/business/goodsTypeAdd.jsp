<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品类别创建</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/goodsType.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
	</head>

	<body>
		<form name="goodsTypeManageForm" method="post" action="${contextPath}/mss/jsp/business/goodsTypeController.do?method=saveGoodsType">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新物品类别
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<input type="hidden" name="typeNum" value="${information.goodsType.typeNum }" />
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品类别
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="typeName" id="type_name" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsType.typeName}"
							onchange="checkTypeName(this, '${information.goodsType.typeName}', 'goods_type')">
						<span id="type_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						类别缩写
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="shortName" id="short_name" type="text" class="qinggoudan_input02" size="20" maxlength="20"
							value="${information.goodsType.typeShort}"
							onchange="checkShortName();"> 
						<span id="short_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						父类别：
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=SpmsConstants.QUERY_GOODSTYPE_INFO%>" num="1" title="...请选择..." next="false" name="fatherType"
							mvalue="${information.goodsType.fatherType.typeNum}" />
					<span id="f_type_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						类别状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select name="typeState">
						<c:if test="${information.goodsType.typeState==null}">
						<option value="A">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsType.typeState=='A'}">
						<option value="A" selected="selected">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsType.typeState=='U'}">
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
						<textarea name="typeComment" id="typeComment" rows="10" cols="30" onchange="checkTypeComment()">${information.goodsType.typeComm}</textarea>
					<span id="comm_nameDiv"></span>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveGoodsType()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList','viewOrEdit')">
					</td>
				</tr>
			</table>
				<input type="hidden" id="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				
		</form>
	</body>

</html>

