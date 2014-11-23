function saveUser(isEdit){
	//checkAllButton(true);
	
	if(checkForm(isEdit) == true){
		window.confirm("您确定要保存该用户信息吗？","OK()","Cancel()");
	}else{
		checkAllButton(false);
	}

}

function OK(){
	document.userForm.submit();
}

function Cancel(){
	checkAllButton(false);
	return false;
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
	var userNameDiv = document.getElementById("user_nameDiv");

	var condition = /[^\u4E00-\u9FA5]/g; //校验是否汉字
	
	if(userName.value == ""){
		//alert("姓名不能为空，请输入！");
		userNameDiv.className = "warning";
	    userNameDiv.innerHTML = "姓名不能为空，请输入！";
		checkAllButton(false);
		userName.focus();
		return false;
	}
	else if(userName.value.length>5)
	{
		//alert("姓名不能超过5个字符，请修改！");
		userNameDiv.className = "warning";
	    userNameDiv.innerHTML = "姓名不能超过5个字符，请修改！";
		checkAllButton(false);
		userName.focus();
		return false;
	}
	 else if(condition.test(trim(userName.value)))
	{
		//alert("姓名必须是汉字！");
		userNameDiv.className = "warning";
	    userNameDiv.innerHTML = "姓名必须是汉字！，请修改！";
		checkAllButton(false);
		userName.focus();
		return false;
	}
	 else if(checkIsExist(user_nameId, hiddenUserName, 'frame_user_info') == "false") {
		//alert("姓名已经存在，请修改！");
		userNameDiv.className = "warning";
	    userNameDiv.innerHTML = "姓名已经存在，请修改！";
		checkAllButton(false);
		userName.focus();
		return false;
	}
	
    userNameDiv.innerHTML = "<img src=\"../../image/correct.png\" width=\"25\" heigth=\"25\"/>";
    
	
	var loginName = document.getElementsByName("loginName")[0];
	var loginNameDiv = document.getElementById("login_nameDiv");
	if(loginName.value == ""){
		//alert("登录名不能为空，请输入！");
		loginNameDiv.className = "warning";
        loginNameDiv.innerHTML = "登录名不能为空，请输入！";
		checkAllButton(false);
		loginName.focus();
		return false;
	} else if(verifyUserName(loginName) == false) {
		loginNameDiv.className = "warning";
        loginNameDiv.innerHTML = "登录名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确";
		checkAllButton(false);
		loginName.focus();
		return false;
	} else if(checkIsExist(login_nameId, hiddenLoginName, 'frame_user_info') == "false") {
		//alert("登录名已经存在，请修改！");
		loginNameDiv.className = "warning";
        loginNameDiv.innerHTML = "登录名已经存在，请修改！";
		checkAllButton(false);
		loginName.focus();
		return false;
	}
	
	loginNameDiv.innerHTML = "<img src=\"../../image/correct.png\" width=\"25\" heigth=\"25\"/>";
	
	
    
	
	var userRole = document.getElementsByName("userRole")[0];
	var userRoleDiv = document.getElementById("user_roleDiv");
	if(userRole.value == ""){
		//alert("用户角色不能为空，请输入！");
		userRoleDiv.className = "warning";
		userRoleDiv.innerHTML = "用户角色不能为空，请输入！";
		checkAllButton(false);
		userRole.focus();
		return false;
	}
	
	userRoleDiv.innerHTML = "<img src=\"../../image/correct.png\" width=\"25\" heigth=\"25\"/>";
    
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
	var userTelDiv = document.getElementById("mobile_numDiv");
     if(false==VerifyMobilePhone(trim(userTel.value))){
     	//alert("请输入有效的手机号码");
     	userTelDiv.className = "warning";
     	userTelDiv.innerHTML = "请输入有效的手机号码";
     	userTel.focus();
     	return false;		
     }

     userTelDiv.innerHTML = "<img src=\"../../image/correct.png\" width=\"25\" heigth=\"25\"/>";
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
	var loginNameDiv = document.getElementById("login_nameDiv");
	
	if(!condition.exec(trim(loginName.value))) {
		loginNameDiv.className = "warning";
        loginNameDiv.innerHTML = "登录名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确";
        loginName.focus();
		return false;
	}

	if (checkIsExist(node, ownerVal, tableName) == "false") {
		loginNameDiv.className = "warning";
        loginNameDiv.innerHTML = "该登录名已经存在，请修改！";
        loginName.focus();
		return false;
	}

	loginNameDiv.innerHTML = "<img src=\"../../image/correct.png\" width=\"25\" heigth=\"25\"/>";
}

function checkUserName(node, ownerVal, tableName) {
	var condition = /[^\u4E00-\u9FA5]/g; //校验是否汉字
	var infoDiv = document.getElementById("user_nameDiv");
	var userName = document.getElementsByName("userName")[0];
	var hiddenUserNameValue = "";
	
	if (userName.value == "") {
		infoDiv.className = "warning";
        infoDiv.innerHTML = "用户名不能为空";
		userName.focus();
		return false;
	}
	
	if(userName.value.length>5)
	{
		infoDiv.className = "warning";
        infoDiv.innerHTML = "用户名不能超过5个字符";
		userName.focus();
		return false;
	}
	
	if(condition.test(trim(userName.value))) {
		infoDiv.className = "warning";
        infoDiv.innerHTML = "用户名必须是汉字";
		userName.focus();
		return false;
	}

	var hiddenUserName = document.getElementsByName("hiddenUserName")[0];
	if(hiddenUserName!=null&&hiddenUserName!='undefined'){
		hiddenUserNameValue = hiddenUserName.value;
	}
	if(checkIsExist(userName, hiddenUserNameValue, 'frame_user_info') == "false") {
			//alert("姓名已经存在，请修改！");
		infoDiv.className = "warning";
		infoDiv.innerHTML = "姓名已经存在，请修改！";
		checkAllButton(false);
		userName.focus();
		return false;
	}

	infoDiv.innerHTML = "<img src=\"../../image/correct.png\" width=\"25\" heigth=\"25\"/>";
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
		alert("登录名必须以字母开头，由数字、字母或下划线组成，请修改！");
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


