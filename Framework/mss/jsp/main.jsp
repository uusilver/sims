<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/framework/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>服务器信息管理系统V1.0</title>
<link href="${contextPath }/mss/css/top.css" rel="stylesheet" type="text/css">
</head>

<frameset rows="91,*" cols="*" frameborder="NO" border="0" framespacing="0">
  <frame src="${contextPath }/mss/jsp/top.jsp" name="topFrame" scrolling="NO" noresize>
  <frameset cols="160,*" frameborder="NO" border="0" framespacing="0" id="frameset_body">
    <frame src="${contextPath }/mss/jsp/menuController.do?method=queryTopMenu" name="leftFrame" scrolling="yes" noresize>
    <!-- frame src="/mss/jsp/promptControllor.do?method=queryBudgetList" name="mainFrame" scrolling="AUTO"> -->
    <frame src="${contextPath }/mss/html/server-static-info.html" name="mainFrame" scrolling="AUTO">
  </frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>