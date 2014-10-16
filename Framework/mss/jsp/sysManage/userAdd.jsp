<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
	</head>

	<body>
		<form name="userForm" method="post" action="${contextPath}/mss/jsp/sysManage/userManageController.do?method=saveUser">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新用户
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:center;">
						<hr size="1" noshade />
						<font style="color:red;">注：所有用户的初始密码均为123456。</font>
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
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						手机号码<font color="red">*</font>
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
						<pub:select sql="<%=SpmsConstants.QUERY_ROLE_INFO%>" title="---请选择---" next="true" name="userRole"
							sonName="menuName" relationSql="<%=SpmsConstants.QUERY_MENUINFO_CASCADE%>" mvalue="${information.userRole}" />
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
						&nbsp;
						<input type="button" class="anniu_out" value=" 确 定 " onclick="saveUser('add')"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/sysManage/userManageController.do?method=queryUserList','quserNum,quserName,quserDept,quserRole,currentPage,viewOrEdit')">
					</td>
				</tr>
			</table>

			<input type="hidden" name="quserNum"
				value="<%=request.getParameter("quserNum") == null ? "" : request.getParameter("quserNum")%>" />
			<input type="hidden" name="quserName"
				value="<%=request.getParameter("quserName") == null ? "" : request.getParameter("quserName")%>" />
			<input type="hidden" name="quserDept"
				value="<%=request.getParameter("quserDept") == null ? "" : request.getParameter("quserDept")%>" />
			<input type="hidden" name="quserRole"
				value="<%=request.getParameter("quserRole") == null ? "" : request.getParameter("quserRole")%>" />
			<input type="hidden" name="currentPage"
				value="<%=request.getParameter("currentPage") == null ? "" : request.getParameter("currentPage")%>" />
			<input type="hidden" name="viewOrEdit"
				value="<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%>" />
		</form>
	</body>

</html>

