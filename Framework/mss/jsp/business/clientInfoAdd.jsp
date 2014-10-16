<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
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
		function selectGoodsType(){
			var url = "/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&accessType=sel&queryTypeState=A";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}

		function saveClientInfo(){
			var clientAddr = document.getElementById("client_addr");
			var zipCode = document.getElementById("zip_code");
			//if(clientAddr.value!=""){
			//	
			//}
			if(checkClientName()&&checkClientNick()&&checkClientTel()&&checkClientAddr()&&checkZipCode()&&checkEMail()&&checkClientComment()){
				if(confirm("您确定要保存该客户信息么？")){
					document.clientInfoAddForm.submit();
				}
			}else{
				alert("请根据提示修改相应内容！");
			}
		}
		
		
		
		function checkClientName() {
			var condition = /^[\u4E00-\u9FA5,a-z,A-Z]+$/; //校验是否汉字或者字母
			var clientName = document.getElementById("client_name");
			
			if (clientName.value == "") {
				var infoDiv = document.getElementById("client_nameDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户姓名不能为空，请填写！";
				return false;
			}
			
			if(clientName.value.length>5)
			{
				var infoDiv = document.getElementById("client_nameDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户姓名不能超过5个字符，请修改";
				return false;
			}
			if(!condition.test(trim(clientName.value))) {
				var infoDiv = document.getElementById("client_nameDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户姓名必须是汉字或者字母，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("client_nameDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "客户姓名符合要求";
		    return true;
		}
		
		
		function checkClientNick() {
			var condition = /^[\u4E00-\u9FA5,a-z,A-Z,0-9]+$/; //校验是否汉字或者字母或者数字
			var clientNick = document.getElementById("client_nick");
			
			if (clientNick.value == "") {
				var infoDiv = document.getElementById("client_nickDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户昵称不能为空，请填写！";
				return false;
			}
			
			if(clientNick.value.length>30)
			{
				var infoDiv = document.getElementById("client_nickDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "客户昵称不能超过30个字符，请修改";
				return false;
			}
			//if(!condition.test(trim(clientNick.value))) {
			//	var infoDiv = document.getElementById("client_nickDiv");
			//	infoDiv.className = "warning";
		    //    infoDiv.innerHTML = "客户昵称必须是汉字或者字母或者数字，请修改！";
			//	return false;
			//}
			
			var infoDiv = document.getElementById("client_nickDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "客户昵称符合要求";
		    return true;
		}
		
		
		/**
		 * 校验是否超长或者是否为数字
		 */
		function checkClientTel(){
			var condition = /^[0-9]+$/g;
			var clientTel = document.getElementById("client_tel").value;
			var numLength = clientTel.length;
			if(numLength==0){
				var infoDiv = document.getElementById("client_telDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "联系电话不能为空，请填写！";
				return false;
			}
			if(numLength>20){
				var infoDiv = document.getElementById("client_telDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "号码长度不能超过20位，请修改！";
				return false;
			}
			if(!condition.test(clientTel)){
				var infoDiv = document.getElementById("client_telDiv");
				infoDiv.className = "warning";
			    infoDiv.innerHTML = "联系电话只能为数字，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("client_telDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "联系电话格式符合要求";
		    return true;
		}
		
		//校验客户地址
		function checkClientAddr(){
			var condition = /^[\u4E00-\u9FA5,0-9,a-z,A-Z,(,),（,）]+$/; //校验是否汉字或者字母
			var clientAddr = document.getElementById("client_addr");
			
			if(clientAddr.value==""){
				return true;
			}
			
			if(clientAddr.value.length>80)
			{
				var infoDiv = document.getElementById("client_addrDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "收件地址不能超过80个字符，请修改";
				return false;
			}
			
			if(!condition.test(trim(clientAddr.value))) {
				var infoDiv = document.getElementById("client_addrDiv");
				infoDiv.className = "warning";
		       infoDiv.innerHTML = "收件地址必须是汉字、数字、字母、括号或者四者组合，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("client_addrDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "收件地址符合要求";
		    return true;
		}
		
		//校验客户邮编
		function checkZipCode(){
			var condition = /^[0-9]+$/;
			var zipCode = document.getElementById("zip_code").value;
			
			if(zipCode==""){
				return true;
			}
			
			if(zipCode.length>10){
				var infoDiv = document.getElementById("zip_codeDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "邮政编码不能超过10位，请修改！";
				return false;
			}
			if(!condition.test(zipCode)){
				var infoDiv = document.getElementById("zip_codeDiv");
				infoDiv.className = "warning";
			    infoDiv.innerHTML = "邮政编码只能为数字，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("zip_codeDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "邮政编码格式符合要求";
		    return true;
		}
		
		//校验Email
		function checkEMail(){
			var condition = /^([-_A-Za-z0-9\.]+)@+([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
			var eMail = document.getElementById("e_mail").value;
			
			if(eMail==""){
				return true;
			}
			
			if(eMail.length>80){
				var infoDiv = document.getElementById("e_mailDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "Email长度不能超过80位，请修改！";
				return false;
			}
			if(!condition.test(eMail)){
				var infoDiv = document.getElementById("e_mailDiv");
				infoDiv.className = "warning";
			    infoDiv.innerHTML = "不是有效的Email格式，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("e_mailDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "Email格式符合要求";
		    return true;
		}
		
		//校验备注
		function checkClientComment() {
			var clientComment = document.getElementById("client_comment");
			
			if(clientComment.value==""){
				return true;
			}
			
			if(clientComment.value.length>50)
			{
				var infoDiv = document.getElementById("client_commDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "备注不能超过50个字符，请修改！";
				return false;
			}
			
			var infoDiv = document.getElementById("client_commDiv");
			infoDiv.className = "OK";
		    infoDiv.innerHTML = "";
		    return true;
		}
		</script>
	</head>

	<body>
		<form name="clientInfoAddForm" method="post" action="${contextPath}/mss/jsp/business/clientInfoController.do?method=saveClientInfo">
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
						客户名称
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="clientName" id="client_name" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.clientInfo.clientName}"
							onchange="checkClientName()">
						<input type="hidden" name="clientNum" id="client_num" value="${information.clientInfo.clientNum }" />
						<span id="client_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户昵称
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="clientNick" id="client_nick" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.clientInfo.clientNick}"
							onchange="checkClientNick()">
						<span id="client_nickDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						联系电话
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="clientTel" id="client_tel" type="text" class="qinggoudan_input023" size="20" maxlength="20"
							value="${information.clientInfo.clientTel}"
							onchange="checkClientTel();">
						<span id="client_telDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						收件地址
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="clientAddr" id="client_addr" type="text" class="qinggoudan_input023" size="20" maxlength="80"
							value="${information.clientInfo.clientAddr}"
							onchange="checkClientAddr()">
					<span id="client_addrDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						邮政编码
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="zipCode" id="zip_code" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.clientInfo.zipCode}"
							onchange="checkZipCode()">
					<span id="zip_codeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						Email
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="eMail" id="e_mail" type="text" class="qinggoudan_input023" size="50" maxlength="80"
							value="${information.clientInfo.eMail}"
							onchange="checkEMail()">
					<span id="e_mailDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户类型
					</td>
					<td align="left" class="qinggoudan_table_td1" id="client_type">
						<select name="clientType">
							<c:if test="${information.clientInfo.clientType==null}">
							<option value="1">购买人</option>
							<option value="2">询价人</option>
							</c:if>
							<c:if test="${information.clientInfo.clientType=='1'}">
							<option value="1" selected="selected">购买人</option>
							<option value="2">询价人</option>
							</c:if>
							<c:if test="${information.clientInfo.clientType=='2'}">
							<option value="1">购买人</option>
							<option value="2" selected="selected">询价人</option>
							</c:if>
						</select>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="clientComment" id="client_comment" rows="10" cols="30" onchange="checkClientComment()">${information.clientInfo.clientComm}</textarea>
					<span id="client_commDiv"></span>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveClientInfo()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/clientInfoController.do?method=queryClientInfoList','viewOrEdit')"-->
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="history.go(-1);">
					</td>
				</tr>
			</table>
				<input type="hidden" id="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				
		</form>
	</body>

</html>

