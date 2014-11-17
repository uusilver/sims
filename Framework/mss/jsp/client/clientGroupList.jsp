<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.tmind.mss.pub.constants.MssConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客户端分组管理-客户端列表</title>
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
			document.clientGroupForm.action = "${contextPath}/mss/jsp/client/clientGroupController.do?method=queryClientGroupList"
				+ constructParams("queryClientGroupName,queryNote,queryStatus,currentPage,viewOrEdit,returnForm,indexNO");
			document.clientGroupForm.submit();
		}
		
		$(function() {
			// make instance
			api = $.fn.MultiDialog();
			api.options.dialog.enabled = true;
			api.options.dialog.width="1100",
			api.options.dialog.height="500",
			
			$("#viewClient img").click( function(){
				var groupId = $(this).parent().parent().find("input[name=groupId]").val();
				var groupName = $(this).parent().parent().find("input[name=groupName]").val();
				api.options.dialog.title = "客户端信息列表 - "+groupName;
				api.openLink({
					href: "${contextPath}/mss/jsp/client/clientInfoController.do?method=queryClientInfoList&showHeader=no&queryClientGroup=" + groupId,
					type: "iframe"
				});
			});
		});
		
	</script>
	</head>

	<body>

		<form name="clientGroupForm" method="post" action="${contextPath}/mss/jsp/client/clientGroupController.do?method=queryClientGroupList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						客户端分组信息查询
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
						客户端分组名称:
						<input type="text" name="queryClientGroupName" class="qinggoudan_input02" size="30"
							value="${fn:escapeXml(information.searchForm.queryClientGroupName)}" />
					</td>
					<td class="qinggoudan_table_td1" style="display:none">
						客户端组状态：
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
						<input type="button" onclick="resetQuery('clientGroupForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="10%" class="qinggoudan_table_title">
							<input type="checkbox" class="qinggoudan_input011" name="clientChk"
								onclick="javaScript:checkAll('clientChk','true');" />全选
					</td>
					<td width="8%" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">序号</c:if>
					</td>
					<td width="20%"  class="qinggoudan_table_title">
						客户端分组名称
					</td>
					<td width="25%"  class="qinggoudan_table_title">
						备注
					</td>
					<td class="qinggoudan_table_title">
						状态
					</td>
					<td class="qinggoudan_table_title">
						客户端
					</td>
					<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
						<td width="15%" class="qinggoudan_table_title">
							详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="clientGroup" items="${information.clientGroupList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2" width="10%">
							<input type="checkbox" class="qinggoudan_input011" name="clientChk" value="${clientGroup.clientgroupid}"/>
						</td>
						<td class="qinggoudan_table_td2" width="8%" >
								${status.index+1}
						</td>
						<td class="qinggoudan_table_td2" width="15%" >
							&nbsp;${fn:escapeXml(clientGroup.clientgroupname)}
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(clientGroup.note)}
						</td>
						<td class="qinggoudan_table_td2">
								${clientGroup.status == "A" ? "有效" : "无效"}
						</td>
						<td class="qinggoudan_table_td2" id="viewClient">
							<a href="#"> 
							<img src="${contextPath}/mss/image/client_group.jpg" width="18" height="20" border="0">
							</a>
							<input type="hidden" name="groupId" value="${clientGroup.clientgroupid}">
							<input type="hidden" name="groupName" value="${clientGroup.clientgroupname}">
						</td>
						<td class="qinggoudan_table_td2">
							<a href="javascript:viewClientGroup('${clientGroup.clientgroupid}')">
							<img src="${contextPath}/mss/image/see.gif" width="18" height="20" border="0"> </a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="clientGroupForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  " onclick="addClientGroup()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  " onclick="delClientGroup()" onMouseOver="className='anniu_over'"
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
	function addClientGroup(){
		$("input[name=viewOrEdit]").attr("value","add");
		window.location = "${contextPath}/mss/jsp/client/clientGroupController.do?method=queryClientGroupById" 
				+ constructParams('queryClientGroupName,queryNote,queryStatus,currentPage,viewOrEdit');
	}
		
	function viewClientGroup(clientGroupId){
		$("input[name=viewOrEdit]").attr("value","edit");
		window.location = "${contextPath}/mss/jsp/client/clientGroupController.do?method=queryClientGroupById&clientGroupId=" + clientGroupId 
			+ constructParams('queryClientGroupName,queryNote,queryStatus,currentPage,viewOrEdit');
	}
		
	function delClientGroup(){
		var clientChk = document.getElementsByName("clientChk");
		var clientIdStr = "";
		for(var i=1;i<clientChk.length; i++){
			if(clientChk[i].checked == true){
				clientIdStr += clientChk[i].value + ",";
			}
		}
		if(clientIdStr==""){
			alert("请选择要删除的客户端组！");
			return;
		}else if(confirm("您确定要删除选中的客户端分组吗？")){
			window.location = "${contextPath}/mss/jsp/client/clientGroupController.do?method=delClientGroups&clientIdStr=" + clientIdStr 
				+ constructParams('queryClientGroupName,queryNote,queryStatus,currentPage,viewOrEdit');
		}
	}
</script>
