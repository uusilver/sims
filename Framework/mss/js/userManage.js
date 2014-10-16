function saveUser(isEdit){
	checkAllButton(true);
	
	if(checkForm(isEdit) == true && confirm("您确定要保存用户信息吗？")){
		document.userForm.submit();
	}else{
		checkAllButton(false);
	}

}

//检查表单元素
function checkForm(isEdit) {
	var hiddenUserNum = "";
	var hiddenUserName = "";
	var hiddenLoginName = "";
	var user_numId = document.getElementById("user_num");
	var user_nameId = document.getElementById("user_name");
	var login_nameId = document.getElementById("login_name");
	
	if (isEdit == "edit") {
		//hiddenUserNum = document.getElementsByName("hiddenUserNum")[0].value;
		hiddenUserName = document.getElementsByName("hiddenUserName")[0].value;
		hiddenLoginName = document.getElementsByName("hiddenLoginName")[0].value;
	}
	
	
	//var userNum = document.getElementsByName("userNum")[0];
	
	//if(trim(userNum.value) == ""){
	//	alert("工号不能为空，请输入！");
	//	checkAllButton(false);
	//	userNum.focus();
	//	return false;
	//} else if(checkIsExist(user_numId, hiddenUserNum, 'frame_user_info') == "false") {
	//	alert("工号已经存在，请修改！");
	//	userNum.focus();
	//	checkAllButton(false);
	//	return false;
	//}
	
	var userName = document.getElementsByName("userName")[0];

	var condition = /[^\u4E00-\u9FA5]/g; //校验是否汉字
	
	if(userName.value == ""){
		alert("姓名不能为空，请输入！");
		checkAllButton(false);
		userName.focus();
		return false;
	}
	else if(userName.value.length>5)
	{
		alert("姓名不能超过5个字符，请修改！");
		checkAllButton(false);
		userName.focus();
		return false;
	}
	 else if(condition.test(trim(userName.value)))
	{
		alert("姓名必须是汉字！");
		userName.focus();
		checkAllButton(false);
		return false;
	}
	 else if(checkIsExist(user_nameId, hiddenUserName, 'frame_user_info') == "false") {
		alert("姓名已经存在，请修改！");
		userName.focus();
		checkAllButton(false);
		return false;
	}
	
	var loginName = document.getElementsByName("loginName")[0];
	if(loginName.value == ""){
		alert("登陆名不能为空，请输入！");
		checkAllButton(false);
		loginName.focus();
		return false;
	} else if(verifyUserName(loginName) == false) {
		var infoDiv = document.getElementById("login_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "登陆名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确";
		return false;
	} else if(checkIsExist(login_nameId, hiddenLoginName, 'frame_user_info') == "false") {
		alert("登陆名已经存在，请修改！");
		loginName.focus();
		checkAllButton(false);
		return false;
	}
	
	var userRole = document.getElementsByName("userRole")[0];
	if(userRole.value == ""){
		alert("用户角色不能为空，请输入！");
		checkAllButton(false);
		userRole.focus();
		return false;
	}
	
	var userTel= document.getElementsByName("userTel")[0];
	if(userTel.value == ""){
		alert("手机号码不能为空，请输入！");
		checkAllButton(false);
		userTel.focus();
		return false;
	}
	
	//var fax = document.getElementsByName("fax")[0];
	//if(fax.value!="" && !checkFax(fax)){
	//	checkAllButton(false);
	//	fax.focus();
	//	return false;
	//}
	
	var userTel = document.getElementsByName("userTel")[0];
	if(userTel.value!="" && !checkTel()){
		checkAllButton(false);
		userTel.focus();
		return false;
	}
	
	return true;
}

function checkTel(){
	var userTel= document.getElementsByName("userTel")[0];
     if(false==VerifyMobilePhone(trim(userTel.value))){
     	alert("请输入有效的手机号码");
     	userTel.focus();
     	return false;		
     }
     return true;
}
  
function checkFax(fax){
	if(fax.value!="" && !isPhoneRegex(fax.value)){
		alert("请输入正确的传真号码！");
		fax.focus();
		return false;
	}else {
		return true;
	}
}

function checkLoginName(node, ownerVal, tableName) {
	var condition = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,31}$/;
	var loginName = document.getElementsByName("loginName")[0];
	
	if(!condition.exec(trim(loginName.value))) {
		var infoDiv = document.getElementById("login_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "登陆名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确";
		return false;
	}

	if (checkIsExist(node, ownerVal, tableName) == "false") {
		return false;
	}
}

function checkUserName(node, ownerVal, tableName) {
	var condition = /[^\u4E00-\u9FA5]/g; //校验是否汉字
	var userName = document.getElementsByName("userName")[0];
	
		if (userName.value == "") {
		var infoDiv = document.getElementById("user_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "用户名不能为空";
		return false;
	}
	
	else if(userName.value.length>5)
	{
		var infoDiv = document.getElementById("user_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "用户名不能超过5个字符";
		return false;
	}
	
	if(condition.test(trim(userName.value))) {
		var infoDiv = document.getElementById("user_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "用户名必须是汉字";
		return false;
	}
	else
	{
		var infoDiv = document.getElementById("user_nameDiv");
		infoDiv.className = "OK";
        infoDiv.innerHTML = "用户名合法";
	}
}

//以下方法暂不用，作为备份
function modUser(){
	var inputEle = document.getElementsByTagName("input");
	for(var i=0; i<inputEle.length; i++){
		if(inputEle[i].type == "text"){
			inputEle[i].readOnly = false;
		}
		if(inputEle[i].type == "checkbox"){
			inputEle[i].disabled = false;
		}
	}
	var selectEle = document.getElementsByTagName("select");
	for(var i=0; i<selectEle.length; i++){
		selectEle[i].disabled = false;
	}
	document.getElementById("modBtnDiv").style.display = "block";
	document.getElementById("viewBtnDiv").style.display = "none";
}

function checkUserNum(){
	var userNum = document.getElementsByName("userNum")[0];
	if(userNum.value!="" && !checkIsNum(userNum.value)){
		document.getElementById("userNumDiv").style.display = "none";
		document.getElementById("promptTR").style.display = document.getElementById("loginNameDiv").style.display;
		alert("工号必须为数字，请修改！");
		userNum.focus();
		return false;
	}
	if(!checkNoOrNameExist("userNum")){
		userNum.focus();
		return false;
	}
	return true;
}

function checkLoginName_bak(){
	var loginName = document.getElementsByName("loginName")[0];
	if(loginName.value!="" && !checkMailName(loginName.value)){
		alert("登陆名必须以字母开头，由数字、字母或下划线组成，请修改！");
		loginName.focus();
		document.getElementById("loginNameDiv").style.display = "none";
		document.getElementById("promptTR").style.display = document.getElementById("userNumDiv").style.display;
		return false;
	} 
	if(!checkNoOrNameExist("loginName")){
		loginName.focus();
		return false;
	}
	return true;
}


