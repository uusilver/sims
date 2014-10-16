<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.xwtech.framework.pub.po.FrameMenu"%>
<%@ page import="com.xwtech.framework.pub.web.RequestNameConstants"%>
<%@ page import="com.xwtech.mss.pub.constants.SpmsConstants" %>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>动感地带二期统计系统</title>
		<link href="/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/roleManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>

		<script type="text/javascript">
		// 初始化页面，将所属菜单值加入"用户菜单"下拉框中
		function roleInfoInit() {
			var sel_dest = document.getElementsByName("menuId")[0];
			<%
				HashMap map = (HashMap)request.getAttribute(RequestNameConstants.INFORMATION);
				ArrayList ownerMenuList = (ArrayList)map.get("ownerMenuList");
				for (int i = 0; i < ownerMenuList.size(); i++) {
					FrameMenu ownerMenu = (FrameMenu)ownerMenuList.get(i);
					String menuId = ownerMenu.getMenuId().toString();
					String menuName = ownerMenu.getMenuName();%>
					var newOption = document.createElement("option"); 
					newOption.text = "<%=menuName%>"; 
					newOption.value = "<%=menuId%>"; 
					sel_dest.add(newOption);
				<%}
			%>
		}
		</script>
	</head>

	<body onLoad="roleInfoInit()">
		<form name="roleInfoForm" method="post" action="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=updateRoleInfo">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						角色详细信息
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2">
						<hr size="1" noshade />
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						角色名称
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="roleName" id="role_name" type="text" class="qinggoudan_input02" value="${information.role.roleName}"
							maxlength="50" onchange="checkIsExist(this, '${information.role.roleName}', 'frame_role','${contextPath }')">
						<span id="role_nameDiv"></span>
						<input type="hidden" name="hiddenRoleName" value="${information.role.roleName}">
					</td>

					<input type="hidden" name="roleId" value="${information.role.roleId}">
				</tr>
				<tr height="30">
					<td align="center" class="qinggoudan_table_title">
						角色描述
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea rows="3" cols="50" name="roleDesc">${information.role.description}</textarea>
					</td>
				</tr>
				<tr height="30">
					<td align="center" class="qinggoudan_table_title">
						角色状态
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" title="" next="false" name="roleState"
							mvalue="${information.role.state}" />

					</td>
				</tr>

				<tr>
					<td align="center" class="qinggoudan_table_title">
						菜单信息
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:two align="center" arl="${information.unOwnerMenuList }" selectItem="系统菜单" waitItem="用户菜单" leftId="menuId"
							leftValue="menuName" rightId="menuId" rightValue="menuName" rightName="menuId" selectStyle="two_select"
							itemStyle="item_two_select" moveStyle="button_select" size="15" />

						<input type="hidden" name="hiddenMenuId" value="">

					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<%--<c:if test="${information.isSysRole}">--%>
						<input type="button" class="anniu_out" value=" 确 定 " onclick="updateRoleInfo()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%--</c:if>--%>
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="javaScript:window.history.go(-1)">
					</td>
				</tr>
			</table>

		</form>

	</body>
</html>
