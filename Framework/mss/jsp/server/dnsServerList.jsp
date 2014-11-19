<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.tmind.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>辅助服务器信息管理</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		<script type="text/javascript">
		document.onkeydown = keyDown;
		function keyDown(){
			var ieKey= window.event.keyCode;
			if(ieKey == 13){
				query();
			}
		}
		
		function query(){
			document.serverListForm.action = "${contextPath}/mss/jsp/server/operDnsServerController.do?method=queryServerInfoList"
				+ constructParams("queryServerType,queryStatus,currentPage,viewOrEdit,returnForm,indexNO");
			document.serverListForm.submit();
		}
		
		
	</script>
	</head>

	<body>

		<form name="serverListForm" method="post" action="${contextPath}/mss/jsp/server/operDnsServerController.do?method=queryServerInfoList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						辅助服务器信息查询
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:center;">
						<hr size="1" noshade>
						<font style="color:red;">注意事项：只能有4个DNS服务器和一个运维中心。</font>
					</td>
				</tr>
			</table>
			<c:if test="${information.searchForm.showHeader=='yes' }">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table" style="margin:0px;">
					<tr>
						<td class="qinggoudan_table_td1">
							辅助服务器类型:
							<pub:link sql="<%=MssConstants.QUERY_DNS_SERVER_TYPE_SQL%>" num="1" selectSize="15"
								title="---服务器类型---" next="false" name="queryServerType" mvalue="${information.searchForm.queryServerType}" />
						</td>
						<!-- td class="qinggoudan_table_td1">
							DNS服务器状态：
							<pub:link sql="" num="1" id="t.role_id" valueName="t.role_name"
								title="---请选择---" next="false" name="queryStatus" mvalue="${information.searchForm.queryStatus}" />
						</td> -->
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
			</c:if>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="90" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
							<input type="checkbox" class="qinggoudan_input011" name="serverChk"
								onclick="javaScript:checkAll('serverChk','true');" />全选
				 		</c:if>
					</td>
					<td width="50" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">序号</c:if>
					</td>
					<td class="qinggoudan_table_title">
						服务器类型
					</td>
					<td class="qinggoudan_table_title">
						IP地址
					</td>
					<!-- td class="qinggoudan_table_title">
						服务器端口
					</td>
					<td class="qinggoudan_table_title">
						状态
					</td> -->
					<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
						<td width="50" class="qinggoudan_table_title">
							详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="serverInfo" items="${information.serverInfoList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2" width="8%" >
							<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
								<input type="checkbox" class="qinggoudan_input011" name="serverChk" value="${serverInfo.dnsserverid}"/>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2" width="10%" >
								${status.index+1}
						</td>
						<td class="qinggoudan_table_td2" width="25%" >
							&nbsp;${fn:escapeXml(serverInfo.serverType)}
						</td>
						<td class="qinggoudan_table_td2" width="30%" >
							&nbsp;${fn:escapeXml(serverInfo.serverip)}
						</td>
						<!-- td class="qinggoudan_table_td2" width="17%" >
							&nbsp;${fn:escapeXml(serverInfo.serverport)}
						</td>
						<td class="qinggoudan_table_td2" width="17%" >
								${serverInfo.status == "A" ? "有效" : "无效"}
						</td>-->
						<td class="qinggoudan_table_td2">
							<a href="javascript:viewServer('${serverInfo.dnsserverid}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
									border="0"> </a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="serverListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<c:if test="${information.searchForm.showHeader=='yes' }">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
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
			</c:if>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="indexNO" value="${information.searchForm.indexNO}" />
			<input type="hidden" name="showHeader" value="${information.searchForm.showHeader}" />
			

		</form>

	</body>
</html>

<script type="text/javascript">
	function addServer(){
		window.location = "${contextPath}/mss/jsp/server/dnsServerAdd.jsp?" + constructParams('queryServerType,queryStatus,currentPage,viewOrEdit,showHeader');
	}
		
	function viewServer(serverId){
		window.location = "${contextPath}/mss/jsp/server/operDnsServerController.do?method=queryServerInfoById&serverId=" + serverId 
			+ constructParams('queryServerType,queryStatus,currentPage,viewOrEdit,showHeader');
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
			alert("请选择要删除的DNS服务器！");
			return;
		}else{
			window.confirm("您确定要删除选中的辅助服务器信息吗？","OK()","NO()");
		}
	}

	
	function OK(){
		var serverChk = document.getElementsByName("serverChk");
		var serverIdStr = "";
		for(var i=1;i<serverChk.length; i++){
			if(serverChk[i].checked == true){
				serverIdStr += serverChk[i].value + ",";
			}
		}
		window.location = "${contextPath}/mss/jsp/server/operDnsServerController.do?method=delServerInfo&serverIdStr=" + serverIdStr 
		+ constructParams('queryServerType,queryStatus,currentPage,viewOrEdit');
	}
	
	function Cancel(){
		return false;
	}
</script>
