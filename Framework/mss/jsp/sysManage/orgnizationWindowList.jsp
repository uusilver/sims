<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>群发辅助系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>

		<script type="text/javascript">

			
			function returnValue(flag)
			{
				var upOrgIds = document.getElementsByName("upOrgId");
				var upOrgNames = document.getElementsByName("upOrgName");
				var upOrgId = "";
				var upOrgName = "";

				
				for(var i=0; i<upOrgIds.length; i++)
				{
					if(upOrgIds[i].checked)
					{
						upOrgId = upOrgIds[i].value;
						upOrgName = upOrgNames[i].value;
						upOrgIds[i].checked="";
						break;
					}
				}
			
				if(flag=="update" && (opener.document.getElementsByName("orgId")[0].value == upOrgId))
				{
					alert("不能选择当前部门作为上级部门！");
					return;
				}
				opener.document.getElementsByName("upOrgId")[0].value = upOrgId;
				opener.document.getElementsByName("upOrgName")[0].value = upOrgName;
				
				self.window.close();
				
			}
		</script>
	</head>

	<body>

		<form name="orgnizationWindowForm"
			action="${contextPath}/mss/jsp/OrgnizationOperation.do?method=selectOrg&flag=${mapResult.flag}"
			method="post">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table" style="margin:15px;">
				<tr>
					<td class="qinggoudan_title01_td1"
						style="border-bottom:solid 1px #CCCCCC;padding-bottom: 3px;">
						选择部门
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table" style="margin:5px;">
				<tr>
					<td colspan="5" class="qinggoudan_table_td5" align="center">
						查询条件
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1" width="50%">
						部门名称：
						<input name="fuzzyOrgnizationName" type="text"
							class="qinggoudan_input02" size="20"
							value="${mapResult.orgnizationSearchForm.fuzzyOrgnizationName}">
					</td>
					<td class="qinggoudan_table_td1" width="10%">
						&nbsp;
						<input type="submit" class="anniu_out" value=" 查 询 "
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
					</td><%--
					<td class="qinggoudan_table_td1" width="10%">
						&nbsp;
						<input type="button" class="anniu_out" value=" 确 定 "
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="javaScript:returnValue();" />
					</td>
				--%></tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table" style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td5" align="center">
						选择
					</td>
					<td class="qinggoudan_table_td5" align="center">
						部门名称
					</td>
					<td class="qinggoudan_table_td5" align="center">
						组织级别
					</td>
					<td class="qinggoudan_table_td5" align="center">
						备注
					</td>
				</tr>


				<c:forEach var="orgInfo" items="${mapResult.orgInfoList}"
					varStatus="status">
					<tr>
						<td class="qinggoudan_table_td1" align="center">
							&nbsp;<input type="radio" name="upOrgId" value="${orgInfo.orgId}" onclick="returnValue('${mapResult.flag}')">
							<input type="hidden" name="upOrgName" value="${orgInfo.orgName}">
						</td>
						<td class="qinggoudan_table_td1" align="center">
							&nbsp;${fn:escapeXml(orgInfo.orgName)}
						</td>
						<td class="qinggoudan_table_td1" align="center">
							&nbsp;${orgInfo.orgLevel}级
						</td>
						<td class="qinggoudan_table_td1" align="center">
							&nbsp;${fn:escapeXml(orgInfo.description)}
						</td>
					</tr>
				</c:forEach>
			</table>
			<br>

			<br>

			<pub:page formName="orgnizationWindowForm" currentPage="${mapResult.currentPage}" totalCount="${mapResult.totalCount}"
				totalPage="${mapResult.totalPage}" />

		
		</form>

	</body>
</html>
