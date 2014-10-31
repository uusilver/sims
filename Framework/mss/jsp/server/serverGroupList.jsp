<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>服务器分组管理</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/jquery.multiDialog.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/jquery.multiDialog.plain.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		<script type="text/javascript" src="${contextPath}/mss/html/js/lib/jquery-ui-1.9.2.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery.multiDialog.js"></script>
		
		<script type="text/javascript">
		document.onkeydown = keyDown;
		function keyDown(){
			var ieKey= window.event.keyCode;
			if(ieKey == 13){
				query();
			}
		}
		
		function query(){
			document.serverGroupListForm.action = "${contextPath}/mss/jsp/server/serverGroupController.do?method=queryServerGroupList"
				+ constructParams("queryServerGroupName,queryNote,queryStatus,currentPage,viewOrEdit,returnForm,indexNO");
			document.serverListForm.submit();
		}
		
		$(function() {
			// make instance
			api = $.fn.MultiDialog();
			api.options.dialog.enabled = true;
			api.options.dialog.width="1100",
			api.options.dialog.height="500",
			
			$("#viewServer img").click( function(){
				var groupId = $(this).parent().parent().find("input[name=groupId]").val();
				var groupName = $(this).parent().parent().find("input[name=groupName]").val();
				api.options.dialog.title = "服务器信息列表 - "+groupName;
				api.openLink({
					href: "${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoList&showHeader=no&queryServerGroup=" + groupId,
					type: "iframe"
				});
			});
		});
		
	</script>
	</head>

	<body>

		<form name="serverGroupForm" method="post" action="${contextPath}/mss/jsp/server/serverGroupController.do?method=queryServerGroupList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						服务器分组信息查询
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
						服务器分组名称:
						<input type="text" name="queryServerGroupName" class="qinggoudan_input02" size="30"
							value="${fn:escapeXml(information.searchForm.queryServerGroupName)}" />
					</td>
					<td class="qinggoudan_table_td1">
						服务器组状态：
						<pub:link sql="<%=MssConstants.QUERY_ROLE_STATE%>" num="1" id="t.role_id" valueName="t.role_name"
							title="---请选择---" next="false" name="queryStatus" mvalue="${information.searchForm.queryStatus}" />
					</td>
					<td class="qinggoudan_table_td1">
						备注:
						<input type="text" name="queryNote" class="qinggoudan_input02" size="40"
							value="${fn:escapeXml(information.searchForm.queryNote)}" />
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('serverGroupForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="30" class="qinggoudan_table_title">
							<input type="checkbox" class="qinggoudan_input011" name="serverChk"
								onclick="javaScript:checkAll('serverChk','true');" />全选
					</td>
					<td width="30" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">序号</c:if>
					</td>
					<td class="qinggoudan_table_title">
						服务器分组名称
					</td>
					<td class="qinggoudan_table_title">
						备注
					</td>
					<td class="qinggoudan_table_title">
						状态
					</td>
					<td class="qinggoudan_table_title">
						服务器
					</td>
					<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
						<td width="50" class="qinggoudan_table_title">
							详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="serverGroup" items="${information.serverGroupList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2" width="10%">
							<input type="checkbox" class="qinggoudan_input011" name="serverChk" value="${serverGroup.servergroupid}"/>
						</td>
						<td class="qinggoudan_table_td2" width="8%" >
								${status.index+1}
						</td>
						<td class="qinggoudan_table_td2" width="15%" >
							&nbsp;${fn:escapeXml(serverGroup.servergroupname)}
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(serverGroup.note)}
						</td>
						<td class="qinggoudan_table_td2">
								${serverGroup.status == "A" ? "有效" : "无效"}
						</td>
						<td class="qinggoudan_table_td2" id="viewServer">
							<a href="#"> 
							<img src="${contextPath}/mss/image/server.png" width="18" height="20" border="0">
							</a>
							<input type="hidden" name="groupId" value="${serverGroup.servergroupid}">
							<input type="hidden" name="groupName" value="${serverGroup.servergroupname}">
						</td>
						<td class="qinggoudan_table_td2">
							<a href="javascript:viewServerGroup('${serverGroup.servergroupid}')">
							<img src="${contextPath}/mss/image/see.gif" width="18" height="20" border="0"> </a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="serverGroupForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  " onclick="addServerGroup()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  " onclick="delServerGroup()" onMouseOver="className='anniu_over'"
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
	function addServerGroup(){
		window.location = "${contextPath}/mss/jsp/server/serverGroupController.do?method=queryServerGroupById" 
				+ constructParams('queryServerGroupName,queryNote,queryStatus,currentPage,viewOrEdit');
	}
		
	function viewServerGroup(serverGroupId){
		window.location = "${contextPath}/mss/jsp/server/serverGroupController.do?method=queryServerGroupById&serverGroupId=" + serverGroupId 
			+ constructParams('queryServerGroupName,queryNote,queryStatus,currentPage,viewOrEdit');
	}
		
	function delServerGroup(){
		var serverChk = document.getElementsByName("serverChk");
		var serverIdStr = "";
		for(var i=1;i<serverChk.length; i++){
			if(serverChk[i].checked == true){
				serverIdStr += serverChk[i].value + ",";
			}
		}
		if(serverIdStr==""){
			alert("请选择要删除的服务器组！");
			return;
		}else if(confirm("您确定要删除选中的服务器分组吗？")){
			window.location = "${contextPath}/mss/jsp/server/serverGroupController.do?method=delServerGroups&serverIdStr=" + serverIdStr 
				+ constructParams('queryServerGroupName,queryNote,queryStatus,currentPage,viewOrEdit');
		}
	}
</script>
