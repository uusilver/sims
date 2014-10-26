<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>服务器信息管理</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		document.onkeydown = keyDown;
		function keyDown(){
			var ieKey= window.event.keyCode;
			if(ieKey == 13){
				query();
			}
		}
		
		function query(){
			var passed = "1";
			if(passed == "1"){
				document.userListForm.action = "${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoList"
					+ constructParams("queryServerType,queryCountryId,queryProvinceId,queryCityId,quserServerGroup,quserServerStatus,currentPage,viewOrEdit,returnForm,indexNO");
				document.userListForm.submit();
			}
		}
		
		
	</script>
	</head>

	<body>

		<form name="serverListForm" method="post" action="${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						服务器查询
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:center;">
						<hr size="1" noshade>
						
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1">
						服务类型:
						<pub:link sql="<%=MssConstants.QUERY_SERVER_TYPE_SQL%>" num="1" selectSize="20"
							title="---请选择服务器类型---" next="false" name="queryServerType" mvalue="${information.searchForm.queryServerType}" />
					</td>
					<td class="qinggoudan_table_td1">
						所属国家:
						<pub:link sql="<%=MssConstants.QUERY_COUNTRY_INFO_SQL%>" num="1" id="C.COUNTRYID" valueName="C.COUNTRYNAME" selectSize="20"
							title="---请选择国家---" next="true" name="queryCountryId" mvalue="${information.searchForm.queryCountryId}" />
					</td>
					<td class="qinggoudan_table_td1">
						所属省（州）:
						<pub:link sql="<%=MssConstants.QUERY_PROVINCE_INFO_SQL%>" num="2" id="P.PROVINCEID" valueName="P.PROVINCENAME" selectSize="20"
							fatherName="queryCountryId" title="---请选择省（州）---" next="true" name="queryProvinceId" mvalue="${information.searchForm.queryProvinceId}" />
						
					</td>
					<td class="qinggoudan_table_td1">
						所属城市:
						<pub:link sql="<%=MssConstants.QUERY_CITY_INFO_SQL%>" num="3" id="T.CITYID" valueName="T.CITYNAME" selectSize="20"
							fatherName="queryProvinceId" title="---请选择城市---" next="false" name="queryCityId" mvalue="${information.searchForm.queryProvinceId}" />
					</td>
					<td class="qinggoudan_table_td1">
						服务器分组:
						<pub:link sql="<%=MssConstants.QUERY_SERVE_GROUP_INFO_SQL%>" num="1" title="---请选择---"
							next="false" name="quserServerGroup" mvalue="${information.searchForm.queryServerGroup}" />
					</td>
					<td class="qinggoudan_table_td1">
						服务器状态:
						<pub:link sql="<%=MssConstants.QUERY_SERVER_STATUS_SQL%>" num="1" title="---请选择---" next="false" 
						name="quserServerStatus" mvalue="${information.searchForm.queryServerStatus}" />
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('serverListForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="50" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
							<input type="checkbox" class="qinggoudan_input011" name="serverChk"
								onclick="javaScript:checkAll('serverChk','true');" />全选
				 </c:if>
						<c:if test="${information.searchForm.viewOrEdit!='edit'}">
							<c:if test="${information.searchForm.indexNO!=null && information.searchForm.indexNO!=''}">选择</c:if>
							<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">序号</c:if>
						</c:if>
					</td>
					<td class="qinggoudan_table_title">
						服务器类型
					</td>
					<td class="qinggoudan_table_title">
						IP地址
					</td>
					<td class="qinggoudan_table_title">
						所属组名
					</td>
					<td class="qinggoudan_table_title">
						服务地域
					</td>
					<td class="qinggoudan_table_title">
						所属国家
					</td>
					<td class="qinggoudan_table_title">
						所属省（州）
					</td>
					<td class="qinggoudan_table_title">
						所属城市
					</td>
					<td class="qinggoudan_table_title">
						服务器状态
					</td>
					<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
						<td width="50" class="qinggoudan_table_title">
							详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="serverInfo" items="${information.serverInfoList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
								<input type="checkbox" class="qinggoudan_input011" name="serverChk" value="${serverInfo[0]}"/>
							</c:if>
							<c:if test="${information.searchForm.viewOrEdit!='edit'}">
								<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
									${status.index+1}
								</c:if>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(serverInfo[1])}
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(serverInfo[2])}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${serverInfo[3]}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(serverInfo[4])}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(serverInfo[5])}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(serverInfo[6])}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(serverInfo[7])}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(serverInfo[8])}
						</td>
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
							<td class="qinggoudan_table_td2">
								<a href="javascript:viewServer('${serverInfo[0]}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="serverListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"
				style="display:${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'?"block":"none"}">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  " onclick="addServer()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  " onclick="delServer()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="indexNO" value="${information.searchForm.indexNO}" />

		</form>

	</body>
</html>

<script type="text/javascript">
	function addServer(){
		window.location = "${contextPath}/mss/jsp/server/serverInfoAdd.jsp?" + constructParams('queryServerType,queryCountryId,queryProvinceId,queryCityId,quserServerGroup,quserServerStatus,currentPage,viewOrEdit');
	}
		
	function viewUser(serverId){
		window.location = "${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoById&serverId=" + serverId 
			+ constructParams('queryServerType,queryCountryId,queryProvinceId,queryCityId,quserServerGroup,quserServerStatus,currentPage,viewOrEdit');
	}
		
	function delServer(){
		var serverChk = document.getElementsByName("serverChk");
		var serverIdStr = "";
		for(var i=1;i<serverChk.length; i++){
			if(serverChk[i].checked == true){
				serverIdStr += serverChk[i].value + ",";
			}
		}
		if(serverIdStr==""){
			alert("请选择要删除的服务器！");
			return;
		}else if(confirm("您确定要删除选中的服务器信息吗？")){
			window.location = "${contextPath}/mss/jsp/server/serverInfoController.do?method=delServerInfo&serverIdStr=" + serverIdStr 
				+ constructParams('queryServerType,queryCountryId,queryProvinceId,queryCityId,quserServerGroup,quserServerStatus,currentPage,viewOrEdit');
		}
	}
</script>
