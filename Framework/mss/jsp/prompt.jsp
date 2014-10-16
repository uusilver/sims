<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<jsp:directive.page import="com.xwtech.framework.pub.utils.DateUtils"/>
<%@ include file="/framework/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>南京移动物资采购信息系统</title>
<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.home_td1 {
	font-size: 9pt;
	color: #000000;
	height: 25px;
	padding-left: 5px;
	border-bottom-width: 1px;
	border-bottom-style: dashed;
	border-bottom-color: #C1D2E6;
}
.home_td2 {
	font-size: 9pt;
	color: #FF0000;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: dashed;
	border-bottom-color: #C1D4E5;
}
-->
</style>
</head>

<body>
<%
	String currYear = DateUtils.getChar8().substring(0,4);
	String currMonth = DateUtils.getChar8().substring(4,6);
	String currDate = DateUtils.getChar8().substring(6,8);
%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
  <tr>
    <td class="qinggoudan_title01_td1">待办事宜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="qinggoudan_title01_td2"><hr size="1" noshade>
    <%=currYear%>年<%=currMonth%>月<%=currDate%>日</td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/mss/image/home_01.jpg" width="600" height="38"></td>
  </tr>
  <tr>
    <td background="/mss/image/home_03.jpg"><table width="580" border="0" cellspacing="0" cellpadding="0">
        <%if(Integer.parseInt(currDate)>=25 && Integer.parseInt(currDate)<=31){%>
	        <tr>
          	  <td width="20" align="center"><img src="/mss/image/gif-0040.gif" width="8" height="8"></td>
	          <td width="450" class="home_td1">当月营业厅预算确认</td>
	          <td width="110" class="home_td2" style="text-align:right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          	<a href="/mss/jsp/budget/budgetController.do?method=queryQdlxList"><img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop"></a></td>
	        </tr>
        <%}%>
        <tr>
          <td width="20" align="center"><img src="/mss/image/gif-0040.gif" width="8" height="8"></td>
          <td width="450" class="home_td1">营业厅订单未到货确认</td>
          <td width="110" class="home_td2" style="text-align:right">${information.receiveCount}条&nbsp;&nbsp;
          	<c:if test="${information.receiveCount>0}">
          		<a href="/mss/jsp/orderInfo/mpmsBusOrderController.do?method=queryOrderList&qreceive=A"><img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop"></a>
          	</c:if>
          	<c:if test="${information.receiveCount<=0}">
          		<img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop">
          	</c:if>
          	</td>
        </tr>
        <tr>
          <td align="center"><img src="/mss/image/gif-0040.gif" width="8" height="8"></td>
          <td class="home_td1">当月预算可用额度小于5%的营业厅</td>
          <td class="home_td2" style="text-align:right">${information.unreachableCount}条&nbsp;&nbsp;
          	<c:if test="${information.unreachableCount>0}">
          		<a href="/mss/jsp/orderInfo/mpmsBusOrderController.do?method=queryShortBusList"><img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop"></a>
          	</c:if>
          	<c:if test="${information.unreachableCount<=0}">
          		<img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop">
          	</c:if>
          	</td>
        </tr>
        <tr>
          <td align="center"><img src="/mss/image/gif-0040.gif" width="8" height="8"></td>
          <td class="home_td1">待处理的订单反馈意见</td>
          <td class="home_td2" style="text-align:right">${information.feedbackCount}条&nbsp;&nbsp;
          	<c:if test="${information.feedbackCount>0}">
          		<a href="/mss/jsp/orderInfo/mpmsBusOrderController.do?method=queryOrderList&qorderState=1"><img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop"></a>
          	</c:if>
          	<c:if test="${information.feedbackCount<=0}">
          		<img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop">
          	</c:if>
          </td>
        </tr>
        <c:if test="${information.mailLastModTime!=null && information.mailLastModTime!=''}">
	        <tr>
	          <td align="center"><img src="/mss/image/gif-0040.gif" width="8" height="8"></td>
	          <td class="home_td1">邮箱密码最后一次修改时间<font color="red">（邮箱密码需三个月修改一次，请注意及时修改！同时，请修改OA系统邮箱密码！）</font></td>
	          <td class="home_td2" style="text-align:right">${information.mailLastModTime}&nbsp;&nbsp;
	          	<a href="/mss/jsp/budget/orderNumController.do?method=viewRemindTimeLimit"><img src="/mss/image/edit.gif" width="14" height="14" border="0" align="texttop"></a>
	          </td>
	        </tr>
		</c:if>
    </table></td>
  </tr>
  <tr>
    <td><img src="/mss/image/home_02.jpg" width="600" height="12"></td>
  </tr>
</table>
</body>
</html>
