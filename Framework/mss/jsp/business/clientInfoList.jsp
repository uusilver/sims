<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		
		function query(){
			var accessType = document.getElementById("accessType").value;
			if(accessType == 'menu'){
				document.getElementById("accessType").value = "";
			}
			document.clientListForm.action = "${contextPath}/mss/jsp/business/clientInfoController.do?method=queryClientInfoList&currentPage=1";
			document.clientListForm.submit();
		}
		
		function addClientInfo(){
			window.location = "${contextPath}/mss/jsp/business/clientInfoAdd.jsp?viewOrEdit='add'";
		}
		
		function viewClientInfo(clientNum){
			window.location = "${contextPath}/mss/jsp/business/clientInfoController.do?method=queryClientInfoById&viewOrEdit='edit'&clientNum=" + clientNum;
		}
		
		function delClientInfo(){
			var userChk = document.getElementsByName("userChk");
			var userIdStr = "";
			for(var i=1;i<userChk.length; i++){
				if(userChk[i].checked == true){
					userIdStr += userChk[i].value + ",";
				}
			}
			if(userIdStr==""){
				alert("请选择要删除的记录！");
				return;
			}else if(confirm("您确定要删除选中的物品类别信息吗？")){
				window.location = "${contextPath}/mss/jsp/business/clientInfoController.do?method=delClientInfo&clientNumStr=" + userIdStr;
			}
		}
	
		function selectClient(){
			var clientChk = document.getElementsByName("clientChk");
			var clientNum = "";
			var clientName = "";
			var clientNick = "";
			var clientTel = "";
			var clientAddr = "";
			var zipCode = "";
			var eMail = "";
			for(var i=0;i<clientChk.length; i++){
				if(clientChk[i].checked == true){
					clientNum = clientChk[i].value;
					clientName = document.getElementById("clientName"+clientNum).value ;
					clientNick = document.getElementById("clientNick"+clientNum).value ;
					clientTel = document.getElementById("clientTel"+clientNum).value ;
					clientAddr = document.getElementById("clientAddr"+clientNum).value ;
					zipCode = document.getElementById("zipCode"+clientNum).value ;
					eMail = document.getElementById("eMail"+clientNum).value ;
					break;
				}
			}
			if(clientNum==""){
				alert("请选择所需客户！");
				return;
			}else if(confirm("您确定选择此客户吗？")){
				window.opener.document.getElementById("client_num").value = clientNum;
				window.opener.document.getElementById("client_name").value = clientName;
				window.opener.document.getElementById("client_nick").value = clientNick;
				window.opener.document.getElementById("client_addr").value = clientAddr;
				window.opener.document.getElementById("client_tel").value = clientTel;
				window.opener.document.getElementById("zip_code").value = zipCode;
				window.opener.document.getElementById("e_mail").value = eMail;
				window.close();
			}
		}
	</script>
	</head>

	<body>

		<form name="clientListForm" method="post" action="${contextPath}/mss/jsp/business/clientInfoController.do?method=queryClientInfoList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						客户信息查询
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"  style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1" width="15%">
						客户姓名:
						<input name="queryClientName" type="text" class="qinggoudan_input02" size="40"
							value="${information.searchForm.queryClientName}" maxlength="50" />
					</td>
					<td class="qinggoudan_table_td1" width="15%">
						客户昵称:
						<input name="queryClientNick" type="text" class="qinggoudan_input02" size="40"
							value="${information.searchForm.queryClientNick}" maxlength="50" />
					</td>
					<td class="qinggoudan_table_td1" width="18%">
						客户类型:
						<c:if test="${information.searchForm.queryClientType != '1'&&information.searchForm.queryClientType != '2'}">
							<select name="queryClientType">
								<option value="">...请选择...</option>
								<option value="1">购买人</option>
								<option value="2">询价人</option>
							</select>
						</c:if>
						<c:if test="${information.searchForm.queryClientType eq '1'}">
							<select name="queryClientType">
								<option value="">...请选择...</option>
								<option value="1" selected="selected">购买人</option>
								<option value="2">询价人</option>
							</select>
						</c:if>
						<c:if test="${information.searchForm.queryClientType eq '2'}">
							<select name="queryClientType">
								<option value="">...请选择...</option>
								<option value="1">购买人</option>
								<option value="2" selected="selected">询价人</option>
							</select>
						</c:if>
					</td>
					<td class="qinggoudan_table_td1" width="18%">
						状态:
						<c:if test="${information.searchForm.queryClientState != 'A' && information.searchForm.queryClientState != 'U'}">
							<select name="queryClientState">
								<option value="">...请选择...</option>
								<option value="A">有效</option>
								<option value="U">无效</option>
							</select>
						</c:if>
						<c:if test="${information.searchForm.queryClientState == 'A'}">
							<select name="queryClientState">
								<option value="">...请选择...</option>
								<option value="A" selected="selected">有效</option>
								<option value="U">无效</option>
							</select>
						</c:if>
						<c:if test="${information.searchForm.queryClientState == 'U'}">
							<select name="queryClientState">
								<option value="">...请选择...</option>
								<option value="A">有效</option>
								<option value="U" selected="selected">无效</option>
							</select>
						</c:if>
					</td>
					<td class="qinggoudan_table_td1">
						<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="query()" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('clientListForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'" />
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="5%" class="qinggoudan_table_title">
						序号
					</td>
					<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
						<td width="5%" class="qinggoudan_table_title">选择
						</td>
					</c:if>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td width="7%" class="qinggoudan_table_title">
							<input type="checkbox" class="qinggoudan_input011" name="userChk" onclick="javaScript:checkAll('userChk','true');" />全选
						</td>
					</c:if>
					<td class="qinggoudan_table_title" width="10%">
						客户姓名
					</td>
					<td class="qinggoudan_table_title" width="10%">
						客户昵称
					</td>
					<td class="qinggoudan_table_title" width="10%">
						联系电话
					</td>
					<td class="qinggoudan_table_title" width="20%">
						收件地址
					</td>
					<td class="qinggoudan_table_title" width="10%">
						邮政编码
					</td>
					<td class="qinggoudan_table_title" width="10%">
						客户类型
					</td>
					<td class="qinggoudan_table_title" width="8%">
						备注
					</td>
					<td class="qinggoudan_table_title" width="8%">
						状态
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_title">
								详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="clientInfo" items="${information.clientInfoList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
							<td class="qinggoudan_table_td2">
								<input type="radio" class="qinggoudan_input011" name="clientChk" value="${clientInfo.clientNum}" />
							</td>
						</c:if>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								<input type="checkbox" class="qinggoudan_input011" name="userChk" value="${clientInfo.clientNum}"
										<c:if test="${clientInfo.clientState=='U'}">disabled="disabled"</c:if> />
							</td>
						</c:if>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.clientName)}
							<input type="hidden" id="clientName${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.clientName)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.clientNick)}
							<input type="hidden" id="clientNick${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.clientNick)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${clientInfo.clientTel}
							<input type="hidden" id="clientTel${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.clientTel)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.clientAddr)}
							<input type="hidden" id="clientAddr${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.clientAddr)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.zipCode)}
							<input type="hidden" id="zipCode${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.zipCode)}" />
							<input type="hidden" id="eMail${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.eMail)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${clientInfo.clientType == '1' ? '购买人' : '询价人'}
							<input type="hidden" id="clientType${clientInfo.clientNum}" value="${fn:escapeXml(clientInfo.clientType)}" />
						</td>
						<td class="qinggoudan_table_td2">
							<textarea name="clientComment" id="client_comment" rows="3" cols="15">${clientInfo.clientComm}</textarea>
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${clientInfo.clientState == 'A' ? '有效' : '无效'}
						</td>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								<a href="javascript:viewClientInfo('${clientInfo.clientNum}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="clientListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value=" 新 增  " onclick="addClientInfo()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value=" 删 除  " onclick="delClientInfo()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</c:if>
				<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value=" 确定  " onclick="selectClient()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value=" 取消  " onclick="window.close();" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</c:if>
			</table>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />

			<input type="hidden" name="accessType" value="${information.accessType}" />

		</form>

	</body>
</html>
