<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pub" uri="/WEB-INF/pub.tld"%>
<%@ page isELIgnored="false"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@ include file="/framework/jsp/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="expires" CONTENT="0">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">

		<title>菜单列表</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script src="${contextPath}/mss/js/tools.js"></script>

		<script type="text/javascript">
		function editInfo(id,oprType)
		{
			window.location = "${contextPath}/mss/jsp/menuController.do?method=queryMenuInfo&resourceId=" + id + "&currentPage=" + document.getElementsByName("currentPage")[0].value + "&oprType=" + oprType;
		}
		
		function addInfo()
		{
			window.location = "${contextPath}/mss/jsp/sysManage/menuAdd.jsp";
		}
		
		//查询信息
		function query()
		{
			checkAllButton(true);
			document.queryForm.action="${contextPath}/mss/jsp/menuController.do?method=queryMenuList";
			document.getElementsByName("currentPage")[0].value = "1";
			document.queryForm.submit();
		}
		
		function infoDel(tarId)
		{
			checkAllButton(true);
			var isCheck=0;
			var target = document.getElementById(tarId);
			var targetInput=target.getElementsByTagName("input");
			for(var i=0; i < targetInput.length; i++) 
			{
				if(targetInput[i].type=="checkbox"&&targetInput[i].checked==true&&targetInput[i].name!='checkboxAll')
				{
					isCheck=isCheck+1;
				}
			}
		
			if(isCheck==0)
			{
				alert("请选择要删除的记录！");
				checkAllButton(false);
				return;
			}
			if(isCheck>0)
			{
				if(window.confirm("确定要删除吗?"))
				{}
				else
				{
					checkAllButton(false);
					return;
				}
			}
			document.getElementById("queryForm").action="${contextPath}/mss/jsp/menuController.do?method=delMenuInfo";
			document.getElementById("queryForm").submit();
		}

		function chooseAllBox(tarId)
		{
			var isChoose = event.srcElement.checked;
			var target = document.getElementById(tarId);
			//alert(isChoose);
			if(isChoose)
			{
				var targetInput=target.getElementsByTagName("input");
				for(var i=0; i < targetInput.length; i++) 
				{
				//alert(i);
					if(targetInput[i].type=="checkbox"&&targetInput[i].disabled!=true)
					{
						targetInput[i].checked = true;
					}
				}
			}
			else
			{
				var targetInput=target.getElementsByTagName("input");
				for(var i=0; i < targetInput.length; i++) 
				{
					if(targetInput[i].type=="checkbox")
					{
						targetInput[i].checked = false;
					}
				}
			}
		}
		
	</script>
	</head>

	<body>
		<form name="queryForm" action="${contextPath}/mss/jsp/menuController.do?method=queryMenuList" method="post">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						菜单信息查询
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2">
						<hr size="1" noshade>
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1">
						菜单名称：
						<input type="text" name="queryResourceName" class="qinggoudan_input02" size="8" class="wuliaoconfig_add_input"
							value="${fn:escapeXml(information.searchForm.queryResourceName)}" />
						&nbsp;&nbsp;
					</td>

					<td class="qinggoudan_table_td1">
						菜单等级：
						<select name="queryMenuLevel">
							<option value="">
								请选择状态
							</option>
							<option value="1" <c:if test="${information.searchForm.queryMenuLevel == '1'}">selected</c:if>>
								一级菜单
							</option>
							<option value="2" <c:if test="${information.searchForm.queryMenuLevel == '2'}">selected</c:if>>
								二级菜单
							</option>
						</select>
					</td>
					<td class="qinggoudan_table_td1">
						菜单状态：
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" id="t.role_id" valueName="t.role_name"
							title="---请选择---" next="false" name="queryMenuState" mvalue="${information.searchForm.queryMenuState}" />
					</td>
					<td class="qinggoudan_table_td1">
						<%--
						所属角色：
						<pub:link sql="<%=SpmsConstants.QUERY_ROLE_INFO%>" num="1" id="t.role_id" valueName="t.role_name"
							title="---请选择---" next="false" name="queryRoleId" mvalue="${information.searchForm.queryRoleId}" />
					--%>
						<input type="hidden" name="queryRoleId" value="">
					</td>
				
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="button" class="anniu_out" value=" 搜 索 " onclick="query();" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('queryForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table id="menuList" width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_title">
						<input type="checkbox" name="checkboxAll" class="qinggoudan_input011" value="checkboxAll"
							onClick="javaScript:chooseAllBox('menuList');">
						全选
					</td>
					<td class="qinggoudan_table_title">
						菜单名称
					</td>
					<td class="qinggoudan_table_title">
						上级菜单名称
					</td>
					<td class="qinggoudan_table_title">
						菜单等级
					</td>
					<td class="qinggoudan_table_title">
						菜单序号
					</td>
					<td class="qinggoudan_table_title">
						菜单状态
					</td>
					<td class="qinggoudan_table_title">
						详情
					</td>

				</tr>

				<c:if test="${information.menuList != null}">
					<c:forEach items="${information.menuList }" var="info" varStatus="order">
						<tr <c:if test="${(order.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
							<td class="qinggoudan_table_td2">
								<input type="checkbox" name="resourceId" class="qinggoudan_input011" value="${info.menuId}" ${info.menuState=='A' ? '' : 'disabled'}>
							</td>
							<td class="qinggoudan_table_td2">
								${fn:escapeXml(info.menuName)}
							</td>
							<td class="qinggoudan_table_td2">
								${(info.menu.menuName !=null && info.menu.menuName !='')? info.menu.menuName: " 无" }
							</td>
							<td class="qinggoudan_table_td2">
								${info.menuLevel == "1" ? "一级" : "二级"}
							</td>
							<td class="qinggoudan_table_td2">
								${info.menuOrder}
							</td>
							<td class="qinggoudan_table_td2">
								${info.menuState == "A" ? "有效" : "无效"}
							</td>
							<td class="qinggoudan_table_td2">
								<a href="javascript:editInfo(${info.menuId },'edit');"><img src="${contextPath }/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>

						</tr>
					</c:forEach>
				</c:if>
			</table>

			<pub:page formName="queryForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  " onclick="addInfo();" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  " onclick="javaScript:infoDel('menuList');"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
