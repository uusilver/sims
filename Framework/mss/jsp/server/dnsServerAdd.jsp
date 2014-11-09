<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-DNS服务器管理</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		
		<script type="text/javascript">

		function saveServerInfo(){
			if(checkServerIP()&&checkServerPort()&&checkServerType()&&checkServerComment()){
				if(confirm("您确定要保存该DNS服务器信息么？")){
					document.serverInfoAddForm.submit();
				}
			}else{
				alert("请根据提示修改相应内容！");
			}
		}
		
		
		function checkServerIP() {
			//校验IP地址格式
			var condition = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/; 
			var serverIP = document.getElementById("server_IP");
			
			if (serverIP.value == "") {
				var infoDiv = document.getElementById("server_IpDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器IP地址不能为空，请填写！";
		        serverIP.focus();
				return false;
			}
			
			if(!condition.test(trim(serverIP.value))) {
				var infoDiv = document.getElementById("server_IpDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器IP地址格式不正确，请修改！例如：192.54.6.20";
		        serverIP.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_IpDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器IP地址符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkServerPort() {
			
			var serverPort = document.getElementById("server_port");
			
			if (serverPort.value == "") {
				var infoDiv = document.getElementById("server_portDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器端口不能为空，请选择！";
		        serverPort.focus();
				return false;
			}
			
			if (!checkIsNum(serverPort.value)) {
				var infoDiv = document.getElementById("server_portDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器端口只能为数字，请选择！";
		        serverPort.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_portDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器端口符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkServerType() {
			
			var serverType = document.getElementsByName("serverType")[0];
			
			if (serverType.value == "") {
				var infoDiv = document.getElementById("server_typeDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器类型不能为空，请选择！";
		        serverType.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_typeDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器类型符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		//校验备注
		function checkServerComment() {
			var comment = document.getElementById("server_comment");
			
			if(comment.value==""){
				return true;
			}
			
			if(comment.value.length>100)
			{
				var infoDiv = document.getElementById("server_commDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "备注不能超过100个字符，请修改！";
		        comment.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_commDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		</script>
	 
	</head>
	<body>
		<form name="serverInfoAddForm" method="post" action="${contextPath}/mss/jsp/server/operDnsServerController.do?method=saveServerInfo">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新服务器档案
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器IP
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="serverIP" id="server_IP" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.operDNSServer.serverip}"
							onchange="checkServerIP()">
						<input type="hidden" name="serverId" id="server_ID" value="${information.transitServer.serverid }" />
						<span id="server_IpDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器端口
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="serverPort" id="server_port" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.operDNSServer.serverport}"
							onchange="checkServerPort()">
						<span id="server_portDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器类型
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=MssConstants.QUERY_DNS_SERVER_TYPE_SQL%>" num="1" selectSize="20"
							title="---请选择服务器类型---" next="false" name="serverType" mvalue="${information.operDNSServer.servertype}" />
					<span id="server_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="serverComment" id="server_comment" rows="10" cols="30" onchange="checkServerComment()">${information.operDNSServer.note}</textarea>
					<span id="server_commDiv"></span>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<c:if test="${information.searchForm.showHeader==null || information.searchForm.showHeader=='yes' }">
							&nbsp;
							<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveServerInfo()"
								onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/server/operDnsServerController.do?method=queryServerInfoList','viewOrEdit,showHeader,currentPage,queryServerType')">
					</td>
				</tr>
			</table>
				<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}"/>
				<input type="hidden" name="currentPage" value="${information.searchForm.currentPage}" />
				<input type="hidden" name="queryServerType" value="${information.searchForm.queryServerType}" />
				<input type="hidden" name="showHeader" value="${information.searchForm.showHeader}" />
				
		</form>
	</body>

</html>
