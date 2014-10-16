<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>动感地带二期统计系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		
	</script>
	</head>

	<body>
		<form name="userListForm" method="post" action="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=queryRoleList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						系统角色查询
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2">
						<hr size="1" noshade>
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1" width="30%">
						角色名称:
						<input name="roleName" type="text" class="qinggoudan_input02" size="20" value="${information.roleForm.roleName}"
							maxlength="50">
					</td>
					<td class="qinggoudan_table_td1" width="30%">
						角色状态:
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" title="---请选择---" next="false" name="roleState"
							mvalue="${information.roleForm.roleState}" />
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="submit" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('userListForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_title">
						<input type="checkbox" class="qinggoudan_input011" name="roleChk" onclick="javaScript:checkAll('roleChk');" />
						全选
					</td>

					<td class="qinggoudan_table_title">
						角色名称
					</td>
					<td class="qinggoudan_table_title" width="40%" >
						角色描述
					</td>
					<td class="qinggoudan_table_title">
						状态
					</td>

					<td class="qinggoudan_table_title">
						详情
					</td>
				</tr>
				<c:forEach var="roleInfo" items="${information.resultList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							<input type="checkbox" name="roleChk" class="qinggoudan_input011" value="${roleInfo.roleId}" ${roleInfo.state=='A' ? '' : 'disabled'}>
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(roleInfo.roleName)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(roleInfo.description)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${roleInfo.state == 'A' ? '有效' : '无效'}
						</td>
						<td class="qinggoudan_table_td2">
							<a href="javascript:viewRole('${roleInfo.roleId}')"> <img src="${contextPath }/mss/image/see.gif" width="18" height="20"
									border="0"> </a>
						</td>
					</tr>
				</c:forEach>
			</table>

			<pub:page formName="userListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  " onclick="addRoleInfo()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  " onclick="delRoleInfo()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

		</form>

	</body>
</html>

<script type="text/javascript">
	function addRoleInfo(){
		window.location = "${contextPath}/mss/jsp/sysManage/roleManageController.do?method=preSaveRoleInfo";
	}
		
	function viewRole(roleId){
	
		document.userListForm.action = "${contextPath}/mss/jsp/sysManage/roleManageController.do?method=queryRoleInfo&roleId=" + roleId;
		document.userListForm.submit();
	}
		
	function delRoleInfo(){
		var roleChk = document.getElementsByName("roleChk");
		var roleIdStr = "";
		for(var i = 1; i < roleChk.length; i++){
			if(roleChk[i].checked == true){
				roleIdStr += roleChk[i].value + ",";
			}
		}
		roleIdStr = roleIdStr.substring(0, roleIdStr.length - 1);
		
		if(roleIdStr == ""){
			alert("请选择要删除的角色！");
			return;
		} else if (confirm("您确定要删除选中的角色信息吗？")){
			window.location = "${contextPath}/mss/jsp/sysManage/roleManageController.do?method=delRoleInfo&roleIdStr=" + roleIdStr;
		}
	}
	
	
</script>
