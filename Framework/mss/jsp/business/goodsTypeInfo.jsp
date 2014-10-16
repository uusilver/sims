<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.xwtech.framework.pub.po.FrameMenu"%>
<%@ page import="com.xwtech.framework.pub.web.RequestNameConstants"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统</title>
		<link href="/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
	</head>

	<body onLoad="change('${information.userRole}')">
		<form name="userForm" method="post" action="${contextPath}/mss/jsp/sysManage/userManageController.do?method=saveUser">
<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						用户详细信息
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
						姓名
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="userName" id="user_name" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.userInfo.userName}"
							onchange="checkUserName(this, '${information.userInfo.userName}', 'frame_user_info')">
						<span id="user_nameDiv"></span>
						<input type="hidden" name="hiddenUserName" value="${information.userInfo.userName}">
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						登陆名
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="loginName" id="login_name" type="text" class="qinggoudan_input02" size="20" maxlength="20"
							value="${information.userInfo.loginName}"
							onchange="checkLoginName(this, '${information.userInfo.loginName}', 'frame_user_info')">
						<span id="login_nameDiv"></span>
						<input type="hidden" name="hiddenLoginName" value="${information.userInfo.loginName}">
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						手机号码
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="userTel" type="text" size="20" maxlength="11" onchange="checkTel()" class="qinggoudan_input02"
							 onkeypress="checkNumber()" value="${information.userInfo.tel}">
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						用户状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" title="" next="false" name="userState"
							mvalue="${information.userInfo.userState}" />
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						用户角色
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:select sql="<%=SpmsConstants.QUERY_ROLE_INFO%>" title="---请选择---" next="true" name="userRole" sonName="menuName"
							relationSql="<%=SpmsConstants.QUERY_MENUINFO_CASCADE%>" mvalue="${information.userInfo.role.roleId}" />
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						可访问菜单
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select name="menuName" size=10 multiple="true">
						</select>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 确 定 " onclick="saveUser('edit')" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('/mss/jsp/sysManage/userManageController.do?method=queryUserList','quserNum,quserName,quserDept,quserRole,currentPage,viewOrEdit')">
					</td>
				</tr>

			</table>

			<input type="hidden" name="userId" value="${information.userInfo.userId}" />

			<input type="hidden" name="quserNum" value="${information.searchForm.userNum}" />
			<input type="hidden" name="quserName" value="${information.searchForm.userName}" />
			<input type="hidden" name="quserDept" value="${information.searchForm.userDept}" />
			<input type="hidden" name="quserRole" value="${information.searchForm.userRole}" />
			<input type="hidden" name="currentPage" value="${information.searchForm.currentPage}" />
			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
		</form>
	</body>
</html>
