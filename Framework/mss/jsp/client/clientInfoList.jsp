<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.tmind.mss.pub.constants.MssConstants" %>
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
			var accessType = document.getElementById("accessType");
			if(accessType!=null && accessType.value== 'menu'){
				document.getElementById("accessType").value = "";
			}
			document.clientListForm.action = "${contextPath}/mss/jsp/client/clientInfoController.do?method=queryClientInfoList&currentPage=1";
			document.clientListForm.submit();
		}
		
		function addClientInfo(){
			window.location = "${contextPath}/mss/jsp/client/clientInfoController.do?method=queryClientInfoById&viewOrEdit=add"
			+ constructParams('queryClientGroup,quserStatus,currentPage,showHeader');
		}
		
		function viewClientInfo(clientNum){
			window.location = "${contextPath}/mss/jsp/client/clientInfoController.do?method=queryClientInfoById&clientNum=" + clientNum
			+ constructParams('queryClientGroup,quserStatus,currentPage,viewOrEdit,showHeader');
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
			}else {
				window.confirm("您确定要删除选中的客户端吗？","OK()","Cancel()");
			}
		}
		
		function OK(){
			var userChk = document.getElementsByName("userChk");
			var userIdStr = "";
			for(var i=1;i<userChk.length; i++){
				if(userChk[i].checked == true){
					userIdStr += userChk[i].value + ",";
				}
			}
			window.location = "${contextPath}/mss/jsp/client/clientInfoController.do?method=delClientInfo&clientNumStr=" + userIdStr;
		}
		
		function Cancel(){
			return false;
		}
	</script>
	</head>

	<body>

		<form name="clientListForm" method="post" action="${contextPath}/mss/jsp/client/clientInfoController.do?method=queryClientInfoList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						客户端信息查询
					</td>
				</tr>
			</table>
			<c:if test="${information.searchForm.showHeader=='yes' }">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"  style="margin:0px;">
					<tr>
						<td class="qinggoudan_table_td1" width="20%">
							客户端用户名:
							<input name="queryClientName" type="text" class="qinggoudan_input02" size="40"
								value="${information.searchForm.queryClientName}" maxlength="50" />
						</td>
						<!-- td class="qinggoudan_table_td1" width="15%">
							认证类型:
							<pub:link sql="" num="1" selectSize="20"
								title="------请选择认证类型------" next="false" name="queryAuthType" mvalue="${information.searchForm.queryAuthType}" />
						</td> -->
						<td class="qinggoudan_table_td1" width="20%">
							客户端状态:
							<pub:link sql="<%=MssConstants.QUERY_CLIENT_DISABLE_FLAG_SQL%>" num="1" selectSize="20"
								title="请选择是否禁用" next="false" name="queryDisableFlag" mvalue="${information.searchForm.queryDisableFlag}" />
						</td>
						<td class="qinggoudan_table_td1" width="20%">
							客户类型:
							<pub:link sql="<%=MssConstants.QUERY_CLIENT_USER_TYPE_SQL%>" num="1" selectSize="20"
								title="请选择客户类型" next="false" name="queryUserType" mvalue="${information.searchForm.queryUserType}" />
						</td>
						<td class="qinggoudan_table_td1" width="20%" style="display:none">
							状态:
								<pub:link sql="<%=MssConstants.QUERY_ROLE_STATE%>" num="1" title="---请选择---" next="false" 
								name="quserStatus" mvalue="${information.searchForm.queryStatus}" />
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
			</c:if>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
						<td width="5%" class="qinggoudan_table_title">选择
						</td>
					</c:if>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td width="7%" class="qinggoudan_table_title">
							<input type="checkbox" class="qinggoudan_input011" name="userChk" onclick="javaScript:checkAll('userChk','true');" />全选
						</td>
					</c:if>
					<td width="5%" class="qinggoudan_table_title">
						序号
					</td>
					<td class="qinggoudan_table_title" width="10%">
						用户名
					</td>
					<td class="qinggoudan_table_title" width="10%">
						真是姓名
					</td>
					<td class="qinggoudan_table_title" width="10%">
						所属分组
					</td>
					<!-- td class="qinggoudan_table_title" width="10%">
						认证类型
					</td> -->
					<td class="qinggoudan_table_title" width="10%">
						客户端状态
					</td>
					<td class="qinggoudan_table_title" width="10%">
						客户端类型
					</td>
					<td class="qinggoudan_table_title" width="15%">
						备注
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_title" width="8%">
								详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="clientInfo" items="${information.clientInfoList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
							<td class="qinggoudan_table_td2">
								<input type="radio" class="qinggoudan_input011" name="clientChk" value="${clientInfo.clientid}" />
							</td>
						</c:if>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								<input type="checkbox" class="qinggoudan_input011" name="userChk" value="${clientInfo.clientid}"
										<c:if test="${clientInfo[7]=='U'}">disabled="disabled"</c:if> />
							</td>
						</c:if>
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.username)}
							<input type="hidden" id="clientName${clientInfo.clientid}" value="${fn:escapeXml(clientInfo.username)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.truename)}
							<input type="hidden" id="trueName${clientInfo.clientid}" value="${fn:escapeXml(clientInfo.truename)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${(clientInfo.clientgroupname==null||clientInfo.clientgroupname=="")?"未分组":clientInfo.clientgroupname}
							<input type="hidden" id="clientGroup${clientInfo.clientid}" value="${fn:escapeXml(clientInfo.clientgroupname)}" />
						</td>
						<!-- td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.authType)}
							<input type="hidden" id="authType${clientInfo.clientid}" value="${fn:escapeXml(clientInfo.authType)}" />
						</td> -->
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.disableFlag)}
							<input type="hidden" id="clientStatus${clientInfo.clientid}" value="${fn:escapeXml(clientInfo.disableFlag)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(clientInfo.userType)}
							<input type="hidden" id="userType${clientInfo.clientid}" value="${fn:escapeXml(clientInfo.userType)}" />
						</td>
						<td class="qinggoudan_table_td2">
							<textarea name="clientComment" id="client_comment" rows="1" cols="25">${fn:escapeXml(clientInfo.note)}</textarea>
						</td>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								<a href="javascript:viewClientInfo('${clientInfo.clientid}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="clientListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
				
			<c:if test="${information.searchForm.showHeader=='yes' }">
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
			</c:if>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="accessType" value="${information.accessType}" />
			<input type="hidden" name="showHeader" value="${information.searchForm.showHeader}" />
			<input type="hidden" name="queryClientGroup" value="${information.searchForm.queryClientGroup}" />

		</form>

	</body>
</html>
