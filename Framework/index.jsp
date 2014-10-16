<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.xwtech.framework.pub.web.RequestNameConstants"%>
<%@ page import="com.xwtech.framework.pub.result.ResultInfos"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.xwtech.framework.pub.result.ResultInfo"%>
<%@ page import="com.xwtech.framework.pub.utils.SessionUtils" %>
<%@ page import="com.xwtech.framework.pub.web.SessionNameConstants" %>
<%@ include file="/framework/jsp/taglibs.jsp"%>

<html>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物资管理系统－用户登陆</title>
<link href="${contextPath }/mss/css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${contextPath }/mss/js/userLogin.js"></script>
<script type="text/javascript" language="javaScript">
     document.onkeydown = keyDown;
</script>
<script type="text/javascript" language="javaScript">
     document.onkeydown = keyDown;
     
     function enterToTable()
     {
     	if (window.event.keyCode==13) window.event.keyCode=9;
     }
     
</script>
</head>

<body>
<form name="UserLogin" action="${contextPath }/mss/jsp/userLoginController.do" method="POST">
	<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	      <tr>
	        <td class="td1_bg"><table border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td colspan="3"><img src="${contextPath }/mss/image/login_01_01.jpg" width="277" height="80"></td>
	          </tr>
	          <tr>
	          
	            <td width="15"><img src="${contextPath }/mss/image/login_02.jpg" width="15" height="119"></td>
	            <td width="247" class="td2_bg"><table width="247" border="0" align="center" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="69" class="td3">用户名:</td>
	                <td width="171" class="td4"><input type="text" name="loginName" maxlength="20" class="input"  onkeydown="enterToTable();"></td>
	              </tr>
	              <tr>
	                <td class="td3">密&nbsp;&nbsp;&nbsp;&nbsp;码:</td>
	                <td class="td4"><input type="password" name="loginPwd" maxlength="20" class="input"></td>
	              </tr>
	              <tr align="center" valign="bottom">
	                <td height="40" colspan="2">
	                	<input type="button" class="out" value=" 登 陆 " onclick="javaScript:VerifyUserInfo();" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
						&nbsp;&nbsp;&nbsp;
	     	 			<input type="reset" class="out" value=" 重 填 " onMouseOver="this.className='over'" onMouseOut="this.className='out'"></td>
	              </tr>
	            </table></td>
	            <td width="15"><img src="${contextPath }/mss/image/login_03.jpg" width="15" height="119"></td>
	          </tr>
	          <tr>
	            <td colspan="3"><img src="${contextPath }/mss/image/login_04.jpg" width="277" height="30"></td>
	          </tr>
	        </table></td>
	      </tr>
	    </table>
	    <br>
	    <br>
	    <br>
	    <br>
	    <br></td>
	  </tr>
	</table>
	
</form>

<iframe name="subFrame" frameborder="0" height="0" style="display:none;"></iframe>

</body>
</html>
<script type="text/javascript" language="javaScript">
     document.UserLogin.loginName.focus();
</script>