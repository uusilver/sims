<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>日志分析系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>

		<script type="text/javascript">
				/*
				 * 页面上的复选框的全选或反全选
				 * @param checkName 要全选的复选框的名字
				 * @param disFlag 是否屏蔽已disabled的checkbox:true:屏蔽,false:不屏蔽
				*/
			function checkAll(checkName,disFlag)
			{
				var checkNameArray=document.getElementsByName(checkName);
				if(document.getElementsByName(checkName)[0].checked == true)
				{
					for(var i=1;i<checkNameArray.length;i++)
					{
						if((disFlag!=null&&disFlag=='true'&&checkNameArray[i].disabled!=true) || (disFlag==null || disFlag=="" || disFlag=='false')){
							checkNameArray[i].checked = true;
						}
					}
				}	
				else
				{
					for(var i=1;i<checkNameArray.length;i++)
					{
						checkNameArray[i].checked = false;
					}
				}
			}
		</script>
	</head>

	<body>

		<form name="orgnizationSearchForm"
			action="${contextPath}/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=${mapResult.flag}"
			method="post">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table" style="margin:15px;">
				<tr>
					<td class="qinggoudan_title01_td1"
						style="border-bottom:solid 1px #CCCCCC;padding-bottom: 3px;">
						组织信息查询
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table" style="margin:5px;">
				<tr>
					<td colspan="13" class="qinggoudan_table_td5" align="center">
						查询条件
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						组织名称：
						<input name="fuzzyOrgnizationName" type="text" class="qinggoudan_input02"
							size="20" value="${mapResult.orgnizationSearchForm.fuzzyOrgnizationName}">
					&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="submit" class="anniu_out" value=" 查 询 " onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'" />
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table" style="margin:0px;">
				<tr>
					<td colspan="14" class="qinggoudan_table_td5" align="center">
						结果列表
					</td>
				</tr>
				<tr>
					<td width="11%" class="qinggoudan_table_title">

					</td>
					
					<td width="15%" class="qinggoudan_table_title">
						组织名称
					</td>
					<td width="15%" class="qinggoudan_table_title">
						上级组织名称
					</td>
					<td width="15%" class="qinggoudan_table_title">
						备注
					</td>
					<td width="12%" class="qinggoudan_table_title">
						组织级别
					</td>
					<td width="12%" class="qinggoudan_table_title">
						状态
					</td>
					<td width="5%" class="qinggoudan_table_title">
						<c:if test="${mapResult.flag=='edit' }">
							编辑
						</c:if>
						<c:if test="${mapResult.flag=='view' }">
							查看
						</c:if>
					</td>
				</tr>
				<c:forEach var="orgInfoList" items="${mapResult.orgInfoList}"
					varStatus="status">
					<tr>
						<td class="qinggoudan_table_td2">
							&nbsp;
							<c:if test="${mapResult.flag=='edit' }">
							<input type="checkbox" name="che_delOrg" class="qinggoudan_input011" 
								value="${orgInfoList.orgId}"  
								<c:if test="${orgInfoList.state=='U'}">
								
								disabled="disabled"
								</c:if> 
							
						  	<c:forEach var="userInfoss" items="${orgInfoList.userInfos}">
								<c:if test="${userInfoss.role.roleId=='1'}">
									
									disabled="disabled"
								</c:if>
								</c:forEach> 
								
								<c:if test="${fn:length(orgInfoList.orgnizations)>0}">
								
									disabled="disabled"
								</c:if>>
							</c:if>
						</td>
						
						<input type="hidden" name="hiddenOrgId" value="${orgInfoList.orgId}"/>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(orgInfoList.orgName)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(orgInfoList.orgnization.orgName)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(orgInfoList.description)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(orgInfoList.orgLevel)} 级
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;
							<c:choose>
								<c:when test="${orgInfoList.state=='A'}">
								有效
								</c:when>
								<c:otherwise>
								无效								
								</c:otherwise>
							</c:choose>

						</td>
						<td class="qinggoudan_table_td2">
							<a href="javascript:viewOrg('${orgInfoList.orgId}','${mapResult.flag}')"><img
									src="${contextPath}/mss/image/see.gif" width="18" height="20" border="0">
							</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<c:if test="${mapResult.flag=='edit' }">
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="center">
						<input type="button" class="anniu_out" value=" 新 增  "
							onclick="addOrg()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 删 除  "
							onclick="delOrg()" onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>
			</c:if>

			<br>

			<pub:page formName="orgnizationSearchForm"
				currentPage="${mapResult.currentPage}"
				totalCount="${mapResult.totalCount}"
				totalPage="${mapResult.totalPage}" />

		</form>

	</body>
</html>

<script type="text/javascript">
	function addOrg(){
		window.location = "${contextPath}/mss/jsp/sysManage/orgnizationAdd.jsp";
	}
		
	function viewOrg(orgId,flag){
		window.location = "${contextPath}/mss/jsp/OrgnizationOperation.do?method=viewOrg&flag="+flag+"&orgId=" + orgId;
	}
	
	function delOrg(){
		var orgChk = document.getElementsByName("che_delOrg");
		var orgIdStr = "";
		for(var i=1;i<orgChk.length; i++){
			if(orgChk[i].checked == true){
				orgIdStr += orgChk[i].value + ",";
			}
		}
		if(orgIdStr=="")
		{
			alert("请选择要删除的部门！");
			return;
		}
		else if(confirm("您确定要删除选中的部门吗？"))
		{
			window.location = "${contextPath}/mss/jsp/OrgnizationOperation.do?method=delOrg&orgIdList=" + orgIdStr;
		}
	}
	
</script>
