<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.tmind.mss.pub.constants.SpmsConstants" %>
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
			var correctIcon = "<img src=\"${contextPath}/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
			
			var menuLevel = document.getElementsByName("menuLevel");
			var levelVal=1;
			for ( var i = 0; i < menuLevel.length; i++) {
				  if (menuLevel[i].checked==true) {
					  levelVal = menuLevel[i].value;
					  break;
				   }
			}
			if(levelVal==2){
				var parentMenu = document.getElementsByName("parentMenuId")[0];
				var index = parentMenu.selectedIndex;
				var selected = parentMenu[index].value;
				var parentMenuDiv = document.getElementById("parent_menuDiv");
				if(selected==null||selected==''){
					parentMenuDiv.className = "warning";
					parentMenuDiv.innerHTML = "请选择上级菜单！";
					document.getElementsByName("parentMenuId")[0].focus();
					passed = "1";
					return;
				}
				parentMenuDiv.innerHTML = correctIcon;
			}
			
			var menuNameDiv = document.getElementById("menu_nameDiv");
			if(trim(document.getElementsByName("resourceName")[0].value)==""){
				//alert("请输入菜单名称！");
				menuNameDiv.className = "warning";
				menuNameDiv.innerHTML = "请输入菜单名称！";
				document.getElementsByName("resourceName")[0].focus();
				passed = "1";
				return;
			}
		
			if(trim(document.getElementsByName("resourceName")[0].value).length>20){
				//alert("菜单名称过长！");
				menuNameDiv.className = "warning";
				menuNameDiv.innerHTML = "菜单名称过长，不能超过20个字符，请修改！";
				document.getElementsByName("resourceName")[0].focus();
				passed = "1";
				return;
			}
			var chkUrl = document.getElementsByName("checkUrl")[0];
			chkUrl.value += "&menuLevel="+levelVal;
			if(checkIsExist(document.getElementsByName("resourceName")[0], '', 'frame_menu') == "false") {
				//alert("姓名已经存在，请修改！");
			infoDiv.className = "warning";
			infoDiv.innerHTML = "菜单已经存在，请修改！";
			document.getElementsByName("resourceName")[0].focus();
			return false;
		}
			menuNameDiv.innerHTML = correctIcon;

			var menuOrderDiv = document.getElementById("menu_orderDiv");
			if(trim(document.getElementsByName("menuOrder")[0].value)==""){
				//alert("请输入菜单顺序！");
				menuOrderDiv.className = "warning";
				menuOrderDiv.innerHTML = "请输入菜单顺序！";
				document.getElementsByName("menuOrder")[0].focus();
				passed = "1";
				return;
			}


			if(document.getElementsByName("menuOrder")[0].value!="" && !checkIsNum(trim(document.getElementsByName("menuOrder")[0].value))){
				//alert("菜单顺序必须为数字，请重新输入！");
				menuOrderDiv.className = "warning";
				menuOrderDiv.innerHTML = "菜单顺序必须为数字，请重新输入！";
				document.getElementsByName("menuOrder")[0].focus();
				passed = "1";
				return;
			}
			
			if(trim(document.getElementsByName("menuOrder")[0].value).length>2){
				//alert("菜单顺序必须为2位数字！");
				menuOrderDiv.className = "warning";
				menuOrderDiv.innerHTML = "菜单顺序必须为2位数字！";
				document.getElementsByName("menuOrder")[0].focus();
				passed = "1";
				return;
			}
			menuOrderDiv.innerHTML = correctIcon;
			

			var menuUrlDiv = document.getElementById("menu_urlDiv");
			if(trim(document.getElementsByName("resourceUrl")[0].value)==""){
				//alert("请输入菜单链接！");
				menuUrlDiv.className = "warning";
				menuUrlDiv.innerHTML = "请输入菜单链接！";
				document.getElementsByName("resourceUrl")[0].focus();
				passed = "1";
				return;
			}
			
			if(trim(document.getElementsByName("resourceUrl")[0].value).length > 200){
				//alert("菜单链接过长！");
				menuUrlDiv.className = "warning";
				menuUrlDiv.innerHTML = "菜单链接过长，不能超过200个字符，请修改！";
				document.getElementsByName("resourceUrl")[0].focus();
				passed = "1";
				return;
			}
			menuUrlDiv.innerHTML = correctIcon;

			if(passed=="0"){
				window.confirm("您确定要添加菜单信息吗？","OK()","Cancel()");
			}
		}
		
		function OK(){
			document.menuAddForm.action = "${contextPath}/mss/jsp/menuController.do?method=saveMenuInfo";
			document.menuAddForm.submit();
		}
		
		function Cancel(){
			return false;
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
			<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist&stateColName=menu_state" />
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
						<span id="parent_menuDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
						菜单名称
						<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="text" name="resourceName" id="menu_name" class="qinggoudan_input02" maxlength="16" size="20">
						<span id="menu_nameDiv"></span>
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
						<span id="menu_orderDiv"></span>
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
						<span id="menu_urlDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="15%" class="qinggoudan_table_title">
					</td>
				
					<td width="70%" class="qinggoudan_table_td1">
						&nbsp;
						<span class="OK">如果没有链接地址，请输入‘#’</span>
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
