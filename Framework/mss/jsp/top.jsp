<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp"%>
<jsp:directive.page import="com.xwtech.framework.pub.utils.DateUtils"/>
<jsp:directive.page import="com.xwtech.mss.pub.tools.CommonOperation"/>
<jsp:directive.page import="com.xwtech.mss.pub.po.UserInfo"/>
<jsp:directive.page import="com.xwtech.mss.pub.constants.DmsConstants"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${contextPath }/mss/css/top.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
function toExpand()
{
	var frm=window.parent.document.getElementById("frameset_body");
	if(frm.cols=="0,*")
		frm.cols="160,*";
	else
		frm.cols="0,*";
}
</script>
</head>

<%
	CommonOperation commonOperation = new CommonOperation();
	UserInfo userInfo = commonOperation.getLoginUserInfo(request).getSysUser();
	String currYear = DateUtils.getChar12().substring(0,4);
	String currMonth = DateUtils.getChar12().substring(4,6);
	String currDate = DateUtils.getChar12().substring(6,8);
	request.setAttribute("userInfo", userInfo);
%>

<body style="margin: 0px">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="326"><img src="${contextPath }/mss/image/top_logo_01.jpg" width="326" height="64"></td>
  <td class="top_bg1">&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="160" align="right" valign="bottom" class="top_bg4"><img src="${contextPath }/mss/image/menu_close.gif" width="38" height="21" align="absbottom" id="image1" style="cursor:pointer" onClick="toExpand();this.style.display='none';image2.style.display=''"><img src="${contextPath }/mss/image/menu_open.gif" align="absbottom" id="image2" style="cursor:pointer;display:none" onClick="toExpand();this.style.display='none';image1.style.display=''">&nbsp;&nbsp;&nbsp;</td>
  <td class="top_bg3">
  	欢迎 ${fn:escapeXml(userInfo.userName)} 登陆，现在是 <%=currYear%>年<%=currMonth%>月<%=currDate%>号
    <a href="${contextPath }/mss/jsp/sysManage/password_mod.jsp" target="mainFrame" class="password">修改密码</a></td>
    <td class="top_bg2">
    <a href="${contextPath }/mss/jsp/logout.jsp" target="_parent" class="top">退出系统</a> 
    | <a href="${contextPath }/mss/jsp/main.jsp" target="_parent" class="top">系统首页</a></td>
  </tr>
</table>

</body>
</html>
