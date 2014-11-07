<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-客户信息管理</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/GoodsManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">

		function saveClientInfo(){
			if(checkUserName()&&checkPassword()&&checkModifyPass()&&checkAuthType()&&checkDisableFlag()
					&&checkUserType()&&checkClientComment()){
				if(confirm("您确定要保存该客户信息么？")){
					document.clientInfoAddForm.submit();
				}
			}else{
				alert("请根据提示修改相应内容！");
			}
		}
		
		function checkUserName() {
			var userName = document.getElementById("user_name");
			var condition = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,31}$/;
			var infoDiv = document.getElementById("user_nameDiv");
			
			if(trim(userName.value) == "") {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "用户名不能为空，请填写！";
		        userName.focus();
				return false;
			}
			
			if(trim(userName.value).length>16)
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "用户名不能超过16个字符，请修改";
		        userName.focus();
				return false;
			}
			if(!condition.test(trim(userName.value))) {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "用户名必须以字母开头，由数字、字母、'.'或是'_'组成，请修改！";
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "用户名符合要求";
		    return true;
		}
		
		function checkPassword(){
			var password = document.getElementById("password");
			var infoDiv = document.getElementById("client_pwdDiv");
			
			if(trim(password.value) == "") {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "密码不能为空，请填写！";
		        password.focus();
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "密码符合要求";
		    return true;
			
		}
		
		function checkModifyPass(){
			var modifyPass = document.getElementsByName("modifyPass")[0];
			var infoDiv = document.getElementById("modify_passDiv");
			if (modifyPass.value == "") {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "是否修改密码不能为空，请选择！";
		        modifyPass.focus();
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "符合要求";
		    return true;
		}
		
		function checkAuthType(){
			var authType = document.getElementsByName("authType")[0];
			var infoDiv = document.getElementById("auth_typeDiv");
			if (authType.value == "") {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "认证类型不能为空，请选择！";
		        authType.focus();
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "认证类型符合要求";
		    return true;
		}
		
		
		function checkDisableFlag(){
			var disableFlag = document.getElementsByName("disableFlag")[0];
			var infoDiv = document.getElementById("disable_flagDiv");
			if (disableFlag.value == "") {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户端状态不能为空，请选择！";
		        disableFlag.focus();
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "符合要求";
		    return true;
		}
		
		function checkUserType(){
			var userType = document.getElementsByName("userType")[0];
			var infoDiv = document.getElementById("user_typeDiv");
			if (userType.value == "") {
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户类型不能为空，请选择！";
		        userType.focus();
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "客户类型符合要求";
		    return true;
		}
		
		//校验备注
		function checkClientComment() {
			var clientComment = document.getElementById("client_comment");
			var infoDiv = document.getElementById("client_commDiv");
			
			if(clientComment.value==""){
				return true;
			}
			
			if(clientComment.value.length>50)
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "备注不能超过50个字符，请修改！";
				return false;
			}
			
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "";
		    return true;
		}
		</script>
	</head>

	<body>
		<form name="clientInfoAddForm" method="post" action="${contextPath}/mss/jsp/client/clientInfoController.do?method=saveClientInfo">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新客户档案
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						用户名
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="userName" id="user_name" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.clientInfo.username}"
							onchange="checkUserName()">
						<span id="user_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						密码
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="password" id="password" type="text" class="qinggoudan_input023" size="20" maxlength="20"
							value="${information.clientInfo.password}"
							onchange="checkPassword();">
						<span id="client_pwdDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户名称
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="clientName" id="client_name" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.clientInfo.truename}"
							onchange="checkClientName()">
						<input type="hidden" name="clientNum" id="client_num" value="${information.clientInfo.clientid }" />
						<span id="client_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						是否修改密码
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=MssConstants.QUERY_CLIENT_MODIFYPASS_SQL%>" num="1" selectSize="20"
							title="------请选择------" next="false" name="modifyPass" mvalue="${information.clientInfo.modifypass}" />
					<span id="modify_passDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						认证类型
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=MssConstants.QUERY_CLIENT_AUTH_TYPE_SQL%>" num="1" selectSize="20"
							title="------请选择认证类型------" next="false" name="authType" mvalue="${information.clientInfo.authenticationtype}" />
					<span id="auth_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户端状态
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=MssConstants.QUERY_CLIENT_DISABLE_FLAG_SQL%>" num="1" selectSize="20"
							title="请选择是否禁用" next="false" name="disableFlag" mvalue="${information.clientInfo.disable}" />
					<span id="disable_flagDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户类型
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=MssConstants.QUERY_CLIENT_USER_TYPE_SQL%>" num="1" selectSize="20"
							title="请选择客户类型" next="false" name="userType" mvalue="${information.clientInfo.usertype}" />
					<span id="user_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="clientComment" id="client_comment" rows="10" cols="30" onchange="checkClientComment()">${information.clientInfo.note}</textarea>
					<span id="client_commDiv"></span>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
					<c:if test="${information.searchForm.showHeader==null || information.searchForm.showHeader=='yes' }">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveClientInfo()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if>
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/client/clientInfoController.do?method=queryClientInfoList','viewOrEdit,currentPage,queryClientName,queryAuthType,queryDisableFlag,queryUserType,queryStatus,queryClientGroup,showHeader')">
					</td>
				</tr>
			</table>
				<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}"/>
				<input type="hidden" name="currentPage" value="${information.searchForm.currentPage}" />
				<input type="hidden" name="queryClientName" value="${information.searchForm.queryClientName}" />
				<input type="hidden" name="queryAuthType" value="${information.searchForm.queryAuthType}" />
				<input type="hidden" name="queryDisableFlag" value="${information.searchForm.queryDisableFlag}" />
				<input type="hidden" name="queryUserType" value="${information.searchForm.queryUserType}" />
				<input type="hidden" name="queryStatus" value="${information.searchForm.queryStatus}" />
				<input type="hidden" name="queryClientGroup" value="${information.searchForm.queryClientGroup}" />
				<input type="hidden" name="showHeader" value="${information.searchForm.showHeader}" />
				
		</form>
	</body>

</html>

