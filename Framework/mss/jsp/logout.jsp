<%@ page language="java" errorPage="/framework/jsp/error.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.tmind.framework.pub.utils.SessionUtils" %>
<%@ page import="com.tmind.framework.pub.web.SessionNameConstants" %>
<%@ include file="/framework/jsp/taglibs.jsp"%>
<%
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>服务器信息管理系统（SIMS）－用户退出</title>
</head >
 <body onLoad='window.parent.location.href = "${contextPath }/login.jsp"'>
 </body>
</html>