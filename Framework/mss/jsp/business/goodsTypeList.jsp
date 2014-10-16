<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/userManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		
		function query(){
			var passed = "1";
			if(passed == "1"){
				document.typeListForm.action = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList"
					+ constructParams("typeName,currentPage,viewOrEdit,returnForm,indexNO");
				document.typeListForm.submit();
			}
		}
		
	</script>
	</head>

	<body>

		<form name="typeListForm" method="post" action="${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList">
			<input type="hidden" name="accessType" value="${information.accessType }">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品类别查询
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1">
						类别名称:
						<input name="queryTypeName" type="text" class="qinggoudan_input02" size="20"
							value="${information.searchForm.queryTypeName}" maxlength="50" />
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_td1">
							状态:
							<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" title="---请选择---" next="false" name="queryTypeState"
								mvalue="${information.searchForm.queryTypeState}" />
						</td>
					</c:if>
					<td class="qinggoudan_table_td1">
						&nbsp;
						<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('typeListForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="50" class="qinggoudan_table_title">
						序号
					</td>
					<td width="50" class="qinggoudan_table_title">
						<input type="checkbox" class="qinggoudan_input011" name="userChk" onclick="javaScript:checkAll('userChk','true');" />全选
					</td>
					<td class="qinggoudan_table_title">
						类别名称
					</td>
					<td class="qinggoudan_table_title">
						所属大类
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_title">
							创建时间
						</td>
						<td class="qinggoudan_table_title">
							状态
						</td>
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
							<td width="50" class="qinggoudan_table_title">
								详情
							</td>
						</c:if>
					</c:if>
				</tr>
				<c:forEach var="goodsType" items="${information.goodsTypeList}" varStatus="status">
					<tr>
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<td class="qinggoudan_table_td2">
							<input type="checkbox" class="qinggoudan_input011" name="userChk" value="${goodsType.typeNum}"
							<c:if test="${goodsType.typeState=='U'}">disabled="disabled"</c:if> />
						</td>
						<td class="qinggoudan_table_td2" width="20%" >
							&nbsp;${fn:escapeXml(goodsType.typeName)}
							<input type="hidden" id="typeName${goodsType.typeNum}" value="${fn:escapeXml(goodsType.typeName)}" />
						</td>
						<td class="qinggoudan_table_td2">
						<c:if test="${goodsType.fatherType!=null}">
							&nbsp;${goodsType.fatherType.typeName}
						</c:if>
						<c:if test="${goodsType.fatherType==null}">
							&nbsp;无
						</c:if>
						</td>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								&nbsp;<pub:FormatDate  dateNumber="${goodsType.createTime}"/>
							</td>
							<td class="qinggoudan_table_td2">
								&nbsp;${goodsType.typeState == 'A' ? '有效' : '无效'}
							</td>
							<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
								<td class="qinggoudan_table_td2">
									<a href="javascript:viewGoodsType('${goodsType.typeNum}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
											border="0"> </a>
								</td>
							</c:if>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="typeListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value=" 新 增  " onclick="addGoodsType()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value=" 删 除  " onclick="delGoodsType()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</c:if>
				<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value=" 确定  " onclick="selectGoodsType()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value=" 取消  " onclick="window.close();" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</c:if>
			</table>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="indexNO" value="${information.searchForm.indexNO}" />

		</form>

	</body>
</html>

<script type="text/javascript">
	function addGoodsType(){
		window.location = "${contextPath}/mss/jsp/business/goodsTypeManage.jsp?viewOrEdit='add'";
	}
		
	function viewGoodsType(typeNum){
		window.location = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeById&viewOrEdit='edit'&typeNum=" + typeNum;
	}
		
	function delGoodsType(){
		var userChk = document.getElementsByName("userChk");
		var userIdStr = "";
		for(var i=1;i<userChk.length; i++){
			if(userChk[i].checked == true){
				userIdStr += userChk[i].value + ",";
			}
		}
		if(userIdStr==""){
			alert("请选择要删除的记录！");
			return;
		}else if(confirm("您确定要删除选中的物品类别信息吗？")){
			window.location = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=delGoodsType&typeNumStr=" + userIdStr;
		}
	}
	
	function selectGoodsType(){
		var userChk = document.getElementsByName("userChk");
		var userIdStr = "";
		var typeNameStr = "";
		for(var i=1;i<userChk.length; i++){
			if(userChk[i].checked == true){
				userIdStr += userChk[i].value + ",";
				typeNameStr += document.getElementById("typeName"+userChk[i].value).value + ",";
			}
		}
		if(userIdStr==""){
			alert("请选择所需类别！");
			return;
		}else if(confirm("您确定选择此物品类别吗？")){
			window.opener.document.getElementById("goods_type").value = typeNameStr.substring(0,typeNameStr.length-1);
			window.opener.document.getElementById("goodsTypeStr").value = userIdStr.substring(0,userIdStr.length-1);
			window.close();
		}
	}
</script>
