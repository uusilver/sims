<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.xwtech.framework.pub.web.RequestNameConstants"%>
<%@ page import="com.xwtech.framework.pub.result.ResultInfos"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.xwtech.framework.pub.result.ResultInfo"%>
<%@ page import="com.xwtech.framework.pub.utils.SessionUtils" %>
<%@ page import="com.xwtech.framework.pub.web.SessionNameConstants" %>
<%@ include file="/framework/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"  alt=""/>
<title>服务器信息管理系统（SIMS）－用户登陆</title>
<link rel="stylesheet" type="text/css" href="${contextPath }/mss/css/login.css" />
<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js" />
 
<script language="javaScript" type="text/javascript">

     function VerifyUserName(loginName){
    	 var loginName = document.getElementsByName("loginName")[0];
       	 var condition = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,31}$/;
       	 if(loginName.value==''){
       		 alert('请输入用户名！');
       		 loginName.focus();
       		 return false;
       	 }
		
       	 if(!condition.exec(loginName.value)){
       		alert("用户名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确！");
       		loginName.focus();
       		return false;
       	 }
       		
       	 return true;
     }

     function VerifyPassword(){
       var condition = /^[a-zA-Z0-9]{1,64}$/;
  	   var loginPwd = document.getElementsByName("loginPwd")[0];
       if(loginPwd.value==''){
       	loginPwd.focus();
       	alert('请输入密码！');
       	return false;
       }
	
       if(!condition.exec(loginPwd.value)){
    	   loginPwd.focus();
    	   alert('请输入正确的密码！');
    	   return false;
       }
       return true;
     }
     
     document.onkeydown = keyDown;
     
     function enterToTable()
     {
     	if (window.event.keyCode==13) window.event.keyCode=9;
     }

     
</script>

<!--背景图自适应-->
<script type="text/javascript">

	function fullBg(bgImgObj){
		var bgImg = bgImgObj;
		bgImg.addClass('fullBg');
		function resizeImg() {
		var imgwidth = bgImg.width();
		var imgheight = bgImg.height();
		var winwidth = $(window).width();
		var winheight = $(window).height();
		var widthratio = winwidth/imgwidth;
		var heightratio = winheight/imgheight;
		var widthdiff = heightratio*imgwidth;
		var heightdiff = widthratio*imgheight;
		if(heightdiff>winheight) {
			bgImg.css({
				width: winwidth+'px',
				height: heightdiff+'px'
			});
		} else {
			bgImg.css({
			width: widthdiff+'px',
			height: winheight+'px'
			});
			}
		}
		resizeImg();
		$(window).resize(function() {
			resizeImg();
		});
	}
	
	function VerifyUserName(){
		 var loginName = $("#login_name");
       	 var condition = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,31}$/;
       	 if(loginName.val()==''){
       		 alert('请输入用户名！');
       		 loginName.focus();
       		 return false;
       	 }
		
       	 if(!condition.exec(loginName.val())){
       		alert("用户名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确！");
       		loginName.focus();
       		return false;
       	 }
       		
       	 return true;
	}
	
	function VerifyPassword(){
	       var condition = /^[a-zA-Z0-9]{1,64}$/;
			 var loginPwd = $("#login_pwd");
	       if(loginPwd.val()==''){
	       	loginPwd.focus();
	       	alert('请输入密码！');
	       	return false;
	       }
		
	       if(!condition.exec(loginPwd.val())){
	    	   loginPwd.focus();
	    	   alert('请输入正确的密码！');
	    	   return false;
	       }
	       return true;
	     }


<!--背景图自适应-->
$(window).load(function(){
	fullBg($("#background"));
});

$(function() {
$(".login_btn_b").click(function(){
	if(VerifyUserName()&&VerifyPassword()){
        $(".userLoginForm").submit();
    }
});
});

</script>
<%
	HashMap map = (HashMap)request.getAttribute(RequestNameConstants.INFORMATION);
	String resultInfoStr = "";
	if(map!=null){
	ResultInfos resultInfos = (ResultInfos)map.get(RequestNameConstants.RESULTINFOS);
	 if(resultInfos != null){
	    Iterator it = resultInfos.iterator();
	    while(it.hasNext()){
	      ResultInfo resultInfo = (ResultInfo)it.next();
	      if(resultInfo != null){
	        resultInfoStr += resultInfo.getResultInfo();
	      }
	    }
	    }
	    }
	
		SessionUtils.removeObjectAttribute(session,"selectRequeireName");
		SessionUtils.removeObjectAttribute(session,SessionNameConstants.LOGIN_TOKEN);
	    
	//清除系统cookie
	Cookie[] cookies = request.getCookies();
	//清除掉系统cookie
	for (int i = 0; cookies != null && i < cookies.length; i++)
	{
		if (SessionNameConstants.DMS_COOKIE.equals(cookies[i].getName()) || SessionNameConstants.MPMS_COOKIE.equals(cookies[i].getName())){
			 cookies[i] = new Cookie(cookies[i].getName(), null);
			 cookies[i].setMaxAge(SessionNameConstants.iCookieForCurrentPage);
			 cookies[i].setPath("/");
			 //添加至response
			 response.addCookie(cookies[i]);
			 break;
		 }
	}
 %>

</head>

<body style="margin:0px; padding:0px;">
<img src="${contextPath }/mss/image/login_bg_new.jpg" alt="" id="background" />
<img src="${contextPath }/mss/image/logo.png" style="position:absolute; margin-left:50%; left:-119px; top:100px;" />

<form name="UserLogin" action="${contextPath}/mss/jsp/userLoginController.do" method="post" class="userLoginForm">
   <div style="position:absolute; width:450px; top:260px; margin-left:50%; left:-220px;">
       <span class="login_span">用户名</span>
       <input class="login_input" name="loginName" id="login_name" onkeydown="enterToTable();" type="text" />
   </div>
   
   <div style="position:absolute; width:450px; top:330px; margin-left:50%; left:-220px;">
       <span class="login_span">密　码</span>
       <input class="login_input" name="loginPwd" id="login_pwd" type="password" />
   </div>
   
   <div style="position:absolute; width:450px; top:410px; margin-left:50%; left:-220px;">
       <input name="" value="重新输入" type="reset" class="login_btn_a" />
       <input name=""  value="登　　录" type="button" class="login_btn_b" />
   </div>
</form>


<iframe 
name="subFrame" height="0" src="about:blank" frameborder="0" style="display: none;"></iframe> 

<script language="javaScript" type="text/javascript">
document.UserLogin.loginName.focus();
</script>

</body>

</html>