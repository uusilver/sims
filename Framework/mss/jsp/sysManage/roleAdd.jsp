<%@page contentType="text/html; charset=utf-8"%>
<jsp:directive.page import="com.xwtech.mss.bo.system.menu.MenuBO" />
<jsp:directive.page import="com.xwtech.framework.pub.web.ContextBeanUtils" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="com.xwtech.mss.pub.po.Menu" />
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>动感地带二期统计系统</title>
		<link href="/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/roleManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
	</head>


	<body>
		<form name="roleAddForm" method="post" action="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=saveRoleInfo">

		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新角色
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:left;">
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
						<input name="roleName" id="role_name" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							onchange="checkIsExist(this, '', 'frame_role')">
						<span id="role_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td align="center" class="qinggoudan_table_title">
						角色描述
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea rows="3" cols="50" name="roleDesc"></textarea>
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
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:two align="center" arl="${information.menuList }" selectItem="系统菜单" waitItem="用户菜单" leftId="menuId"
							leftValue="menuName" rightId="menuId" rightValue="menuName" rightName="menuId" selectStyle="two_select"
							itemStyle="item_two_select" moveStyle="button_select" size="15" />

						<input type="hidden" name="hiddenMenuId" value="">

					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="3" align="center">
						<input type="button" class="anniu_out" value=" 确 定 " onclick="saveRoleInfo()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="javaScript:window.history.go(-1)">
					</td>
				</tr>
			</table>

		</form>
	</body>

</html>

