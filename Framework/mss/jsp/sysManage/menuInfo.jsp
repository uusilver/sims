<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>
	<head>
		<title>修改菜单</title>
		<link href="${contextPath}/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		
		<script type="text/javascript">
		function saveMenu(){
			var passed = "0";
			if(trim(document.getElementsByName("resourceName")[0].value)==""){
				alert("请输入菜单名称！");
				document.getElementsByName("resourceName")[0].focus();
				passed = "1";
				return;
			}
			
			if(trim(document.getElementsByName("menuOrder")[0].value)==""){
				alert("请输入菜单顺序！");
				document.getElementsByName("menuOrder")[0].focus();
				passed = "1";
				return;
			}

			if(document.getElementsByName("menuOrder")[0].value!="" && !checkIsNum(trim(document.getElementsByName("menuOrder")[0].value))){
				alert("菜单顺序必须为数字，请重新输入！");
				document.getElementsByName("menuOrder")[0].focus();
				passed = "1";
				return;
			}
			
			if(trim(document.getElementsByName("resourceUrl")[0].value)==""){
				alert("请输入菜单链接！");
				document.getElementsByName("resourceUrl")[0].focus();
				passed = "1";
				return;
			}

			if(passed=="0" && confirm("您确定要添加菜单信息吗？")){
				document.menuAddForm.action = "${contextPath}/mss/jsp/menuController.do?method=saveMenuInfo";
				document.menuAddForm.submit();
			}
		}
		
		function checkLevel(level)
		{
			if('1' == level)
			{
				document.getElementById("parentMenuIdTd").style.visibility = 'hidden';
			}
			else if('2' == level)
			{
				document.getElementById("parentMenuIdTd").style.visibility = 'visible';
			}
		}
		
		function init()
		{
			<c:if test="${information.menuInfo.menuLevel == '1'}">
				document.getElementById("parentMenuIdTd").style.visibility = 'hidden';
			</c:if>
			<c:if test="${information.menuInfo.menuLevel == '2'}">
				document.getElementById("parentMenuIdTd").style.visibility = 'visible';
			</c:if>
		}

	</script>
	</head>
	<body onload="init();">

		<form name="menuAddForm" method="post">
			<input type="hidden" name="oprType" value="${information.oprType}" />

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						菜单详细信息
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2">
						<hr size="1" noshade />
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">

				<input type="hidden" value="${information.menuInfo.menuId}" name="resourceId" />

				<tr height="28">
					<td width="15%" class="qinggoudan_table_title">
						菜单等级
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						一级菜单:
						<input type="radio" name="menuLevel" class="qinggoudan_input011" value="1"
							<c:if test="${information.menuInfo.menuLevel == '1'}">checked="checked"</c:if> onclick="checkLevel('1');">
						&nbsp;&nbsp;二级菜单:
						<input type="radio" name="menuLevel" class="qinggoudan_input011" value="2"
							<c:if test="${information.menuInfo.menuLevel == '2'}">checked="checked"</c:if> onclick="checkLevel('2');">
					</td>
				</tr>

				<tr height="28">
					<td width="15%" class="qinggoudan_table_title">
						上级菜单&nbsp;
					</td>
					<td class="qinggoudan_table_td1" id="parentMenuIdTd">
						<pub:link sql="<%=SpmsConstants.QUERY_MENUINFO_FIRST%>" num="1" id="t.role_id" valueName="t.role_name"
							title="请选择上级菜单" next="false" name="parentMenuId" mvalue="${information.menuInfo.menu.menuId}" />

					</td>
				</tr>
				<tr height="28">
					<td width="15%" class="qinggoudan_table_title">
						菜单名称
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="text" name="resourceName" maxlength="16" size="20" class="qinggoudan_input02"
							value="${fn:escapeXml(information.menuInfo.menuName)}">
					</td>
				</tr>
				<tr height="28">
					<td width="15%" class="qinggoudan_table_title">
						菜单顺序
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="text" name="menuOrder" class="qinggoudan_input02" maxlength="3" size="10" value="${information.menuInfo.menuOrder}">
					</td>
				</tr>
				<tr height="28">
					<td width="15%" class="qinggoudan_table_title">
						对应链接
						<font color="red">*</font>
					</td>
					<td width="70%" class="qinggoudan_table_td1">
						&nbsp;
						<%--<input type="text" name="resourceUrl" class="qinggoudan_input02" size="40" value="${information.menuInfo.menuUrl}">--%>
						<textarea rows="3" cols="50" name="resourceUrl">${information.menuInfo.menuUrl}</textarea>
					</td>
				</tr>
				<tr height="28">
					<td width="15%" class="qinggoudan_table_title">
						菜单状态
						<font color="red">*</font>
					</td>
					<td width="70%" class="qinggoudan_table_td1">
						&nbsp;
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" id="t.role_id" valueName="t.role_name"
							title="" next="false" name="menuState" mvalue="${information.menuInfo.menuState}" />
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="3" align="center">
						<input type="button" class="anniu_out" value=" 确 定 " onclick="saveMenu()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="window.location='${contextPath}/mss/jsp/menuController.do?method=queryMenuList'">
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
