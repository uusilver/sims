<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/framework/jsp/taglibs.jsp"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎页面</title>
<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
      <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="background:url(/mss/image/bg_1.jpg);">
      	<tr>
        	<td align="center">
        		<a class="qinggoudan_title01_td1" href="${contextPath }/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&queryRecordState=0">未付款记录数：${information.resultList[0] }条</a>
        	</td>
        </tr>
      </table>
</body>
</html>