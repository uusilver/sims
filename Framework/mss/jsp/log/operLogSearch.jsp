<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp" %>
<%@ taglib uri="/springForm" prefix="springForm"%>
<%@ taglib uri="/pub/dividePagingDis.tld" prefix="dividePagingDis"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>操作日志查询</title>
	<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${contextPath}/mss/js/operLog.js"></script>
	<script type="text/javascript" src="${contextPath}/mss/js/commonWxy.js">	</script>
	<script type="text/javascript" src="${contextPath}/mss/js/admsCommonWxy.js">	</script>
	<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>

</head>

<body>
	<springForm:form   id="searchForm"  action="${contextPath}/mss/jsp/log/operLog.do?method=listAll" method="post">		

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
		  <tr>
		    <td class="qinggoudan_title01_td1">操作日志查询</td>
		  </tr>
		  <tr>
		    <td class="qinggoudan_title01_td2"><hr size="1" noshade>    </td>
		  </tr>
		</table>
		
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
		  
		  <tr>
			    <td class="qinggoudan_table_td1">
			    	操作人:
			    	<pub:link sql="<%=SpmsConstants.QUERY_USER_INFO%>" num="1" title="---请选择---"
							next="false" name="loginName" mvalue="${operLogListMap.searchForm.loginName}" />
			    </td>
			    <td class="qinggoudan_table_td1">
			    	操作类型:
			        <springForm:select id="doType" path="doType">
						<springForm:option value="">请选择操作类型</springForm:option>
						<springForm:option value="1" label="新增"></springForm:option>
						<springForm:option value="2" label="修改"></springForm:option>
						<springForm:option value="3" label="删除"></springForm:option>
					</springForm:select>
			    </td>
			    <td class="qinggoudan_table_td1">
			    	操作对象:
			        <springForm:select id="objType" path="objType">
						<springForm:option value="">请选择操作对象</springForm:option>
						<springForm:option value="1" label="入库单"></springForm:option>
				    	<springForm:option value="2" label="出库单"></springForm:option>
				    	<springForm:option value="3" label="退库单"></springForm:option>
				    	<springForm:option value="6" label="物品类型"></springForm:option>
				    	<springForm:option value="7" label="出库单转退库单"></springForm:option>
				    	<springForm:option value="4" label="物品信息"></springForm:option>
				    	<springForm:option value="5" label="客户信息"></springForm:option>
				    	<springForm:option value="9" label="系统用户信息"></springForm:option>
					</springForm:select>
			    <td class="qinggoudan_table_td1">
			    	描述:
			        <input name="logDesc" type="text" class="qinggoudan_input023" size="10"
							value="${operLogListMap.searchForm.description}" maxlength="50" />
			    </td>
			    </td>
			    
			    <td class="qinggoudan_table_td1" width="28%"><input  type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'" onclick="sear()">
		      	&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="resetQuery('searchForm')" class="anniu_out" value=" 重 填 " onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
		    </td>
		  </tr>
		</table>
	
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
	  <tr>
	    <td  class="qinggoudan_table_title" width="5%">序号</td>
	    <td  class="qinggoudan_table_title" width="10%">操作对象</td>
	    <td  class="qinggoudan_table_title">操作类型</td>
	    <td  class="qinggoudan_table_title" width="55%">描述</td>
	    <td  class="qinggoudan_table_title">操作时间</td>
	    <td  class="qinggoudan_table_title">操作人</td>
	  </tr>
	  
	  <c:forEach var="operLogListIns" items="${operLogListMap.list}" varStatus="status">
		  <tr>
		   	<td class="qinggoudan_table_td2">${status.index+1}</td>
		    <td class="qinggoudan_table_td2">&nbsp;
		    	<c:if test="${operLogListIns.objType==1}">入库单</c:if>
		    	<c:if test="${operLogListIns.objType==2}">出库单</c:if>
		    	<c:if test="${operLogListIns.objType==3}">退库单</c:if>
		    	<c:if test="${operLogListIns.objType==4}">物品信息</c:if>
		    	<c:if test="${operLogListIns.objType==5}">客户信息</c:if>
		    	<c:if test="${operLogListIns.objType==6}">物品类型</c:if>
		    	<c:if test="${operLogListIns.objType==7}">出库单转退库单</c:if>
		    	<c:if test="${operLogListIns.objType==9}">系统用户信息</c:if>
		    </td>
		    <td class="qinggoudan_table_td2">&nbsp;
		    	<c:if test="${operLogListIns.doType==1}">新增</c:if>
		    	<c:if test="${operLogListIns.doType==2}">修改</c:if>
		    	<c:if test="${operLogListIns.doType==3}">删除</c:if>
		    </td>
			<td class="qinggoudan_table_td2">&nbsp;${operLogListIns.description}</td>
			<td class="qinggoudan_table_td2">&nbsp;<pub:FormatDate  dateNumber="${operLogListIns.doTime}"/></td>
		    <td class="qinggoudan_table_td2">&nbsp;${operLogListIns.loginName}</td>
		  </tr>
	  </c:forEach>
	  
	</table>
	
	<pub:page formName="searchForm" currentPage="${operLogListMap.currentPage}" totalCount="${operLogListMap.recordTotalNum}" totalPage="${operLogListMap.totalPage}"/>	

	</springForm:form>
</body>
</html>
