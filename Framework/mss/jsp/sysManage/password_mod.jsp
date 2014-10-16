<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>物资管理系统</title>
	<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
	<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
</head>

<body>

<form name="modPwdForm" action="${contextPath}/mss/jsp/sysManage/userManageController.do?method=modPassword" method="post">

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
		<tr>
			<td class="qinggoudan_title01_td1">密码修改</td>
		</tr>
		<tr>
			<td class="qinggoudan_title01_td2"><hr size="1" noshade></td>
		</tr>
	</table>
	<table width="50%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
		<tr>
			<td align="center" class="qinggoudan_table_td1">请输入旧的密码:
				<input name="oldPassword" type="password" class="qinggoudan_input02" size="30">
			</td>
		</tr>
		<tr>
			<td align="center" class="qinggoudan_table_td1">请输入新的密码:
				<input name="newPassword" type="password" class="qinggoudan_input02" size="30"></td>
		</tr>
		<tr>
			<td align="center" class="qinggoudan_table_td1">再次输入新密码:
				<input name="newPasswordAgain" type="password" class="qinggoudan_input02" size="30"></td>
		</tr>
		<tr>
			<td align="center" class="qinggoudan_table_td1">&nbsp;
				<input name="button" type="button" class="anniu_out" value=" 确 定 " onclick="subPwd()" onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="reset" onclick="resetQuery('modPwdForm')" type="reset" class="anniu_out" value=" 重 填 " onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'"></td>
		  </tr>
	</table>
</form>

</body>
</html>

<script type="text/javascript" language="javascript">
	function subPwd(){
		checkAllButton(true);
		
		var passed = "0";		
		var oldPassword = document.getElementsByName("oldPassword")[0];
		var newPassword = document.getElementsByName("newPassword")[0];
		var newPasswordAgain = document.getElementsByName("newPasswordAgain")[0];
		
		if(oldPassword.value == ""){
			alert("请输入旧的密码！");
			passed = "1";
			oldPassword.focus();
			checkAllButton(false);
			return;
		}
		if(newPassword.value == ""){
			alert("请输入新的密码！");
			passed = "1";
			newPassword.focus();
			checkAllButton(false);
			return;
		}
		if(newPasswordAgain.value == ""){
			alert("请再次输入新的密码！");
			passed = "1";
			newPasswordAgain.focus();
			checkAllButton(false);
			return;
		}
		if(newPassword.value != newPasswordAgain.value){
			alert("两次输入的密码不一样，请修改！");
			passed = "1";
			newPassword.focus();
			checkAllButton(false);
			return;
		}
		
		var oldPwdRightFlag = true;
		var url="/mss/jsp/sysManage/userManageController.do?method=checkOldPwd";
		var filter = "";
		filter="oldPwd="+oldPassword.value;
	    var retrunValue = getReturnDataByXMLHttp(url,filter);
	    if(retrunValue == "false") {
	    	alert("您输入的密码不正确，请重新输入！")
			passed = "1";
			oldPassword.focus();
			checkAllButton(false);
			return;
	    }
	    
	    if(passed == "0"){
	    	document.modPwdForm.submit();
	    }else{
			checkAllButton(false);
		}
	}
</script>
