<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>
	<head>
		<title>增加菜单</title>
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
		
			if(trim(document.getElementsByName("resourceName")[0].value).length>60){
				alert("菜单名称过长！");
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
			
			if(trim(document.getElementsByName("menuOrder")[0].value).length>2){
				alert("菜单顺序必须为2位数字！");
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
			
			if(trim(document.getElementsByName("resourceUrl")[0].value).length > 200){
				alert("菜单链接过长！");
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
			document.getElementById("parentMenuIdTd").style.visibility = 'hidden';
		}

	</script>
	</head>

	<body onload="init();">
		<br />

		<form name="menuAddForm" method="post">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新菜单
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:left;">
						<hr size="1" noshade />
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">

				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
						菜单等级
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						一级菜单:
						<input type="radio" name="menuLevel" class="qinggoudan_input011" value="1" checked="checked"
							onclick="checkLevel('1');">
						&nbsp;&nbsp;二级菜单:
						<input type="radio" name="menuLevel" class="qinggoudan_input011" value="2" onclick="checkLevel('2');">
					</td>
				</tr>

				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
						上级菜单
					</td>
					<td class="qinggoudan_table_td1" id="parentMenuIdTd">
						<pub:link sql="<%=SpmsConstants.QUERY_MENUINFO_FIRST%>" num="1" id="t.role_id" valueName="t.role_name"
							title="请选择上级菜单" next="false" name="parentMenuId" mvalue="" />
					</td>
				</tr>
				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
						菜单名称
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="text" name="resourceName" class="qinggoudan_input02" maxlength="16" size="20">
					</td>
				</tr>
				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
						菜单顺序
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="text" name="menuOrder" class="qinggoudan_input02" maxlength="3" size="10">
					</td>
				</tr>
				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
						对应链接
						<font color="red">*</font>
					</td>
					<td width="70%" class="qinggoudan_table_td1">
						&nbsp;
						<%--<input type="text" name="resourceUrl" class="qinggoudan_input02" size="40">--%>
						<textarea rows="3" cols="50" name="resourceUrl"></textarea>
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
