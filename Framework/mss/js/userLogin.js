function VerifyUserName(UserLogin){
  var condition = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,31}$/;
  if(UserLogin.loginName.value==''){
  	alert('请输入用户名！');
  	UserLogin.loginName.focus();
  	return false;
  }else  if(!condition.exec(UserLogin.loginName.value)){
  	alert("用户名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确！");
  	UserLogin.loginName.focus();
  	return false;
  }
  return true;
}

function VerifyPassword(UserLogin){
  var condition = /^[a-zA-Z0-9]{1,64}$/;
  if(UserLogin.loginPwd.value==''){
  	UserLogin.loginPwd.focus();
  	alert('请输入密码！');
  	return false;
  }else if(!condition.exec(UserLogin.loginPwd.value)){
  	UserLogin.loginPwd.focus();
	alert('请输入正确的密码！');
	return false;
  }
  return true;
}

//function VerifyREPassword(diyUserRegister){
//  if(diyUserRegister.password.value!=diyUserRegister.repassword.value){
//  alert('确认密码错误！');
//  return false;
//  }
//  return true;
//}
//
//function VerifyEMail(diyUserRegister){
//  var conditon = /^\w{1,64}@(\w+\.)+\w{2,3}$/;
//  if(!conditon.exec(diyUserRegister.email.value)){
//  alert('请您输入正确格式的E-Mail！');
//  return false;
//  }
//  return true;
//}
//
//function VerifyQuestion(diyUserRegister){
//  if(diyUserRegister.passwordQuestion.value==""||diyUserRegister.passwordQuestion.value==null){
//  alert('请输入密码提示问题！');
//  return false;
//  }
//  return true;
//}
//
//function VerifyAnswer(diyUserRegister){
//  if(diyUserRegister.passwordAnswer.value==""||diyUserRegister.passwordAnswer.value==null){
//  alert('请输入密码提示问题的答案！');
//  return false;
//  }
//  return true;
//}
//
//function VerifyAgreeOrDisagree(diyUserRegister){
//  if(diyUserRegister.YorN.status==false){
//  alert('很遗憾,您不同意我们的条款，将不能注册为DIY用户！');
//  return false;
//  }
//  return true;
//}
//
//function VerifySex(diyUserRegister){
//   for (var i=0; i<diyUserRegister.sex.length;i++) {
//      if (diyUserRegister.sex[i].checked) {
//         return diyUserRegister.sex[i].value;
//      }
//   }
//   alert('请选择性别！');
//   return false;
//}

function VerifyUserInfo(){
    if(VerifyUserName(UserLogin)==false)
        return false;
    if(VerifyPassword(UserLogin)==false)
        return false;
//    document.UserLogin.action="/mss/jsp/userLoginController.do";
    document.UserLogin.submit();
}


function keyDown()
     {
       var ieKey= window.event.keyCode;
       if(ieKey == 13){
       		VerifyUserInfo(document.all.UserLogin);
       }
     }
