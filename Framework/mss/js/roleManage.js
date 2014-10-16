// 更新
function updateRoleInfo(){
	checkAllButton(true);
	var hiddenRoleName = document.getElementsByName("hiddenRoleName")[0].value;
	
	setMenuId();
	
	if(checkForm(hiddenRoleName) == true && confirm("您确定要更新用户信息吗？")) {
		document.roleInfoForm.submit();
	} else {
		checkAllButton(false);
	}
}

// 保存
function saveRoleInfo(){
	checkAllButton(true);
	
	setMenuId();
	
	if(checkForm("") == true && confirm("您确定要保存用户信息吗？")){
		document.roleAddForm.submit();
	} else {
		checkAllButton(false);
	}
}

// 保存将下拉框值至隐藏表单元素，用于后台程序添加/更新数据库
function setMenuId(){
	checkAllButton(true);
	
	var menuId = document.getElementsByName("menuId")[0];
	var menuIds = "";
	
	for(i = 0;i < menuId.options.length; i++){
		menuIds += menuId.options[i].value + ",";
		//if(menuId.options[i].selected) alert(o.options[i].text);
	}
	menuIds = menuIds.substring(0, menuIds.length - 1);
	
	document.getElementsByName("hiddenMenuId")[0].value = menuIds;

}

// 根据页面不同的事件，检查页面规则
function checkForm() {
	var hiddenRoleName = arguments[0];
	var roleId = document.getElementById("role_name");
	var roleName = document.getElementsByName("roleName")[0];
	var roleDesc = document.getElementsByName("roleDesc")[0];
	
	if(trim(roleName.value) == ""){
		alert("角色名称不能为空，请修改！");
		roleName.focus();
		checkAllButton(false);
		return false;
	}
	else if(trim(roleName.value).length > 50){
		alert("角色名称的字符长度不能大于50，请修改！");
		roleName.focus();
		checkAllButton(false);
		return false;
	}
	
	if(checkIsExist(roleId, hiddenRoleName, 'frame_role') == "false") {
		alert("角色名称已经存在，请修改！");
		roleName.focus();
		checkAllButton(false);
		return false;
	}
	
	if(trim(roleDesc.value) == ""){
		alert("角色描述不能为空，请修改！");
		roleDesc.focus();
		checkAllButton(false);
		return false;
	}
	else if(trim(roleDesc.value).length > 300){
		alert("角色描述的字符长度不能大于300，请修改！");
		roleDesc.focus();
		checkAllButton(false);
		return false;
	}
	
	return true;
}
