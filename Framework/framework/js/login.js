function login(formObj)
{
  if(formObj.loginName.value.length==0) {alert('请输入用户名!');return false;}
  if(formObj.loginPwd.value.length!=6) {alert('请输入正确的密码!');return false;}
  if(formObj.checkCode.value.length!=4){alert('请输入验证码!');return false;}
  return true;
}
