<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-客户端信息创建</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		<script type="text/javascript">
		// 初始化页面，将所属服务器信息加入"已分配服务器"下拉框中
		$(function(){
			var csMappingJsonObj = $.parseJSON('${information.csMappingResult}');
			var serverId;
			var serverName;
			var sel_dest = $("select[name=serverId]");
			for(var i=0;i<csMappingJsonObj.length;i++){
					serverId = csMappingJsonObj[i].serverId;
					serverName = csMappingJsonObj[i].serverTag;
					sel_dest.append("<option value='"+serverId+"'>"+serverName+"</option>");
			}
		});
		
		</script>
		<script type="text/javascript">

		function saveClientInfo(){
			if(checkUserName()&&checkPassword()&&checkModifyPass()&&checkAuthType()&&checkDisableFlag()
					&&checkUserType()&&checkTelePhone()&&checkMobilePhone()&&checkClientComment()){
				if(confirm("您确定要保存该客户信息么？")){
					var serverOptions = $("select[name=serverId]").find("option");
					var serverIds = "";
					for(var i=0;i < serverOptions.length; i++){
						serverIds += serverOptions[i].value + ",";
					}
					serverIds = serverIds.substring(0, serverIds.length - 1);
			
					$("input[name=hiddenServerIds]").attr("value",serverIds);
					
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
			
			if(trim(userName.value).length>20)
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "用户名不能超过20个字符，请修改";
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
		
		/**
		 * 校验固定电话
		 */
		function checkTelePhone(){
			var clientTel = document.getElementById("tele_phone").value;
			var numLength = clientTel.length;
			if(numLength==0){
				var infoDiv = document.getElementById("tele_phoneDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "固定电话不能为空，请填写！";
				return false;
			}
			if(numLength>20){
				var infoDiv = document.getElementById("tele_phoneDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "号码长度不能超过20位，请修改！";
				return false;
			}
			if(!isPhoneRegex(clientTel)){
				var infoDiv = document.getElementById("tele_phoneDiv");
				infoDiv.className = "warning";
			    infoDiv.innerHTML = "固定电话只能为数字,()或者-，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("tele_phoneDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "固定电话格式符合要求";
		    return true;
		}
		
		/**
		 * 校验移动电话
		 */
		function checkMobilePhone(){
			var mobilePhone = document.getElementById("mobile_phone").value;
			var numLength = mobilePhone.length;
			if(numLength==0){
				var infoDiv = document.getElementById("mobile_phoneDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "移动电话不能为空，请填写！";
				return false;
			}
			if(numLength>20){
				var infoDiv = document.getElementById("mobile_phoneDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "移动电话号码长度不能超过20位，请修改！";
				return false;
			}
			if(!VerifyMobilePhone(mobilePhone)){
				var infoDiv = document.getElementById("mobile_phoneDiv");
				infoDiv.className = "warning";
			    infoDiv.innerHTML = "移动电话只能为数字，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("mobile_phoneDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "移动电话格式符合要求";
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
						创建新客户端档案
					</td>
				</tr>
			</table>

			<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
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
						固定电话
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="telePhone" id="tele_phone" type="text" class="qinggoudan_input023" size="20" maxlength="20"
							value="${information.clientInfo.telephone}"
							onchange="checkTelePhone();">
						<span id="tele_phoneDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						移动电话
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="mobilePhone" id="mobile_phone" type="text" class="qinggoudan_input023" size="20" maxlength="20"
							value="${information.clientInfo.mobilephone}"
							onchange="checkMobilePhone();">
						<span id="mobile_phoneDiv"></span>
					</td>
				</tr>
				<tr>
					<td align="center" class="qinggoudan_table_title">
						可访问服务器
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:two align="left" arl="${information.serverList }" selectItem="服务器列表" waitItem="可访问服务器列表" leftId="serverId"
							leftValue="serverTag" rightId="serverId" rightValue="serverTag" rightName="serverId" selectStyle="two_select"
							itemStyle="item_two_select" moveStyle="button_select" size="15" contextPath="${contextPath}" />
						<input type="hidden" name="hiddenServerIds" value=""/>
					</td>
				</tr>
				<tr height="167">
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

