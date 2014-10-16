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
				document.userListForm.action = "${contextPath}/mss/jsp/sysManage/userManageController.do?method=queryUserList"
					+ constructParams("userNum,userName,userDept,userRole,currentPage,viewOrEdit,returnForm,indexNO");
				document.userListForm.submit();
			}
		}
		
		function revertPwd(userId){
			var url="${contextPath}/mss/jsp/sysManage/userManageController.do?method=revertPassword";
			var filter = "";
			filter="userId="+userId;
		    var retrunValue = getReturnDataByXMLHttp(url,filter);
		    if(retrunValue == "true") {
		    	alert("用户密码初始成功，初始密码为：123456！");
		    }else{
		    	alert("用户密码初始失败！");
		    }
		}
	</script>
	</head>

	<body>

		<form name="userListForm" method="post" action="${contextPath}/mss/jsp/sysManage/userManageController.do?method=queryUserList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						系统用户查询
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:center;">
						<hr size="1" noshade>
						<c:if test="${information.searchForm.viewOrEdit=='edit'}">
							<font style="color:red;">注：所有用户的初始密码均为123456。</font>
						</c:if>
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1">
						姓名:
						<input name="quserName" type="text" class="qinggoudan_input02" size="20"
							value="${information.searchForm.userName}" maxlength="50" />
					</td>
					<td class="qinggoudan_table_td1">
						用户角色:
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_INFO%>" num="1" title="---请选择---"
							next="false" name="quserRole" mvalue="${information.searchForm.userRole}" />
					</td>
					<td class="qinggoudan_table_td1">
						状态:
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" title="---请选择---" next="false" name="userState"
							mvalue="${information.searchForm.userState}" />
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('userListForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="50" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
							<input type="checkbox" class="qinggoudan_input011" name="userChk"
								onclick="javaScript:checkAll('userChk','true');" />全选
				 </c:if>
						<c:if test="${information.searchForm.viewOrEdit!='edit'}">
							<c:if test="${information.searchForm.indexNO!=null && information.searchForm.indexNO!=''}">选择</c:if>
							<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">序号</c:if>
						</c:if>
					</td>
					<td class="qinggoudan_table_title">
						姓名
					</td>
					<td class="qinggoudan_table_title">
						登陆名
					</td>
					<td class="qinggoudan_table_title">
						联系电话
					</td>
					<td class="qinggoudan_table_title">
						角色
					</td>
					<td class="qinggoudan_table_title">
						状态
					</td>
					<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
						<td class="qinggoudan_table_title">
							恢复初始密码
						</td>
					</c:if>
					<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
						<td width="50" class="qinggoudan_table_title">
							详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="userInfo" items="${information.userList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
								<input type="checkbox" class="qinggoudan_input011" name="userChk" value="${userInfo.userId}"
									<c:if test="${userInfo.delFlag=='N'}">disabled="disabled"</c:if> />
							</c:if>
							<c:if test="${information.searchForm.viewOrEdit!='edit'}">
								<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
							${status.index+1}</c:if>
								<c:if test="${information.searchForm.indexNO!=null && information.searchForm.indexNO!=''}">
									<input type="radio" class="qinggoudan_input011" name="userId" value="${userInfo.userId}"
										onclick="returnUser('${information.searchForm.returnForm}','${information.searchForm.indexNO}','${userInfo.userId}','${userInfo.userName}','${userInfo.tel}')" />
								</c:if>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(userInfo.userName)}
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(userInfo.loginName)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${userInfo.tel}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(userInfo.role.roleName)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${userInfo.userState == 'A' ? '有效' : '无效'}
						</td>
						<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
							<td class="qinggoudan_table_td2">
								<a href="javascript:revertPwd('${userInfo.userId}')" style="color:red;">初始密码</a>
							</td>
						</c:if>
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
							<td class="qinggoudan_table_td2">
								<a href="javascript:viewUser('${userInfo.userId}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="userListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"
				style="display:${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'?"block":"none"}">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  " onclick="addUser()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  " onclick="delUser()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="indexNO" value="${information.searchForm.indexNO}" />
			<input type="hidden" name="returnForm" value="${information.searchForm.returnForm}" />
			<input type="hidden" name="returnNameInput" value="${information.searchForm.returnNameInput}" />
			<input type="hidden" name="returnIdInput" value="${information.searchForm.returnIdInput}" />
			<input type="hidden" name="returnTelInput" value="${information.searchForm.returnTelInput}" />

		</form>

	</body>
</html>

<script type="text/javascript">
	function addUser(){
		window.location = "${contextPath}/mss/jsp/sysManage/userAdd.jsp?" + constructParams('quserNum,quserName,quserDept,quserRole,currentPage,viewOrEdit');
	}
		
	function viewUser(userId){
		window.location = "${contextPath}/mss/jsp/sysManage/userManageController.do?method=viewUser&userId=" + userId 
			+ constructParams('quserNum,quserName,quserDept,quserRole,currentPage,viewOrEdit');
	}
		
	function queryUser(){
		
	}
		
	function delUser(){
		var userChk = document.getElementsByName("userChk");
		var userIdStr = "";
		for(var i=1;i<userChk.length; i++){
			if(userChk[i].checked == true){
				userIdStr += userChk[i].value + ",";
			}
		}
		if(userIdStr==""){
			alert("请选择要删除的用户！");
			return;
		}else if(confirm("您确定要删除选中的用户信息吗？")){
			window.location = "${contextPath}/mss/jsp/sysManage/userManageController.do?method=delUserInfo&userIdStr=" + userIdStr 
				+ constructParams('quserNum,quserName,quserDept,quserRole,currentPage,viewOrEdit');
		}
	}
	
	function returnUser(returnForm,indexNO,userId,userName,telNum){
		if(returnForm=='reqForm'){
			opener.document.getElementsByName("${information.searchForm.returnNameInput}")[indexNO].value = userName;
			opener.document.getElementsByName("${information.searchForm.returnIdInput}")[indexNO].value = userId;
			opener.document.getElementsByName("${information.searchForm.returnTelInput}")[indexNO].value = telNum;
		}else{
			opener.document.getElementsByName("linkManName")[indexNO].value = userName;
			opener.document.getElementsByName("linkMan")[indexNO].value = userId;
			opener.document.getElementsByName("telNum")[indexNO].value = telNum;
		}
		self.window.close();
	}
</script>
