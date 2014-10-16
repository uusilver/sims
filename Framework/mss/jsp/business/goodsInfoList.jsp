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
			var isStatistic = document.getElementById("isStatistic");
			
			var accessValue = document.getElementsByName("accessType")[0].value;
			document.getElementsByName("currentPage")[0].value = 1;
			if(accessValue=='menu'){
				document.getElementsByName("accessType")[0].value="";
			}
			if(isStatistic!=null&&isStatistic.checked == true){
				document.goodsListForm.action = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList&isStatistic=Y";
				document.goodsListForm.submit();
			}else{
				document.goodsListForm.action = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList";
				document.goodsListForm.submit();
			}
		}
		
		function selectGoodsType(){
			var url = "${contextPath }/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&accessType=selectTypeByTree&perPageCount=0";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		function addGoodsInfo(){
		window.location = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&viewOrEdit=add&accessType=addGoodsByTree&perPageCount=0&loadNextNode=Y";
	}
		
	function viewGoodsInfo(goodsNum){
		var url = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoById&viewOrEdit=edit&goodsNum=" + goodsNum;
		window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
	}
		
	function delGoodsInfo(){
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
			window.location = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=delGoodsInfo&goodsNumStr=" + userIdStr;
		}
	}
	
	function selectGoods(openerRowId){
		var goodsChk = document.getElementsByName("goodsChk");
		var goodsNum = "";
		var goodsName = "";
		var typeName = "";
		var typeNum = "";
		for(var i=0;i<goodsChk.length; i++){
			if(goodsChk[i].checked == true){
				goodsNum = goodsChk[i].value;
				goodsName = document.getElementById("goodsName"+goodsChk[i].value).value ;
				typeNum = document.getElementById("typeNum"+goodsChk[i].value).value ;
				typeName = document.getElementById("typeName"+goodsChk[i].value).value ;
				break;
			}
		}
		if(goodsNum==""){
			alert("请选择所需物品！");
			return;
		}else if(confirm("您确定选择此物品吗？")){
			var tdsObj = window.opener.document.getElementById(openerRowId).children;
			var goods_name = tdsObj[0].children;
			goods_name[0].value = goodsName;
			goods_name[1].value = goodsNum;
			var goods_type = tdsObj[1].children;
			goods_type[0].value = typeName;
			goods_type[1].value = typeNum;
			window.close();
		}
	}
	
	function selectGoodsForRecord(){
		var goodsChk = document.getElementsByName("goodsChk");
		var goodsNum = "";
		var goodsName = "";
		var goodsCode = "";
		var typeName = "";
		var typeNum = "";
		var goodsCount = "";
		var goodsPrice = "";
		for(var i=0;i<goodsChk.length; i++){
			if(goodsChk[i].checked == true){
				goodsNum = goodsChk[i].value;
				goodsName = document.getElementById("goodsName"+goodsChk[i].value).value ;
				typeNum = document.getElementById("typeNum"+goodsChk[i].value).value ;
				typeName = document.getElementById("typeName"+goodsChk[i].value).value ;
				goodsCount = document.getElementById("goodsCount"+goodsChk[i].value).value ;
				goodsPrice = document.getElementById("goodsPrice"+goodsChk[i].value).value ;
				goodsCode = document.getElementById("goodsCode"+goodsChk[i].value).value ;
				break;
			}
		}
		if(goodsNum==""){
			alert("请选择所需物品！");
			return;
		}else if(confirm("您确定选择此物品吗？")){
			window.opener.document.getElementById("goodsNum").value=goodsNum;
			window.opener.document.getElementById("goods_name").value=goodsName;
			window.opener.document.getElementById("goods_code").value=goodsCode;
			window.opener.document.getElementById("typeNum").value=typeNum;
			window.opener.document.getElementById("goods_type").value=typeName;
			window.opener.document.getElementById("left_count").value=goodsCount;
			window.opener.document.getElementById("goodsPrice").value=goodsPrice;
			window.close();
		}
	}
	</script>
	</head>

	<body>

		<form name="goodsListForm" method="post" action="${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品信息查询
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"  style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1" width="18%">
						物品名称:
						<input name="queryGoodsName" type="text" class="qinggoudan_input02" size="40"
							value="${information.searchForm.queryGoodsName}" maxlength="50" />
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_td1" width="15%">
							状&nbsp;&nbsp;态:
							<pub:link sql="<%=SpmsConstants.QUERY_ROLE_STATE%>" num="1" title="---请选择---" next="false" name="queryGoodsState"
								mvalue="${information.searchForm.queryGoodsState}" />
						</td>
					</c:if>
					<td class="qinggoudan_table_td1">
						所属类别:
						<input name="queryGoodsType" id="goods_type" type="text" class="qinggoudan_input023" size="40"
							value="${information.searchForm.queryGoodsType}" maxlength="50" readonly="readonly"/>
							&nbsp;
							<input type="hidden" name="goodsTypeStr" id="goodsTypeStr" value="${information.searchForm.goodsTypeStr}"/>
						<input type="button" class="anniu_s_out" value=" 选择 " onMouseOver="className='anniu_s_over'"
							onMouseOut="className='anniu_s_out'" onclick="selectGoodsType()">
					</td>
					</tr>
					<tr>
					<td class="qinggoudan_table_td1" width="15%">
						起始日期:<pub:dtp format="yyyy-MM-dd" name="queryStartTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryStartTime}"/>
					</td>
					<td class="qinggoudan_table_td1" width="20%">
						截至日期:<pub:dtp format="yyyy-MM-dd" name="queryEndTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryEndTime}"/>
					</td>
					<td class="qinggoudan_table_td1">
						<c:if test="${information.accessType eq 'menu'||information.accessType eq ''}">
							<input type="checkbox" id="isStatistic" class="qinggoudan_input011"/>统计金额&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
						<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('goodsListForm')" class="anniu_out" value=" 重 填 "
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="5%" class="qinggoudan_table_title">
						序号
					</td>
					<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
						<td width="5%" class="qinggoudan_table_title">选择
						</td>
					</c:if>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td width="5%" class="qinggoudan_table_title">
							<input type="checkbox" class="qinggoudan_input011" name="userChk" onclick="javaScript:checkAll('userChk','true');" />全选
						</td>
					</c:if>
					<td class="qinggoudan_table_title" width="10%">
						物品名称
					</td>
					<td class="qinggoudan_table_title" width="10%">
						物品编号
					</td>
					<td class="qinggoudan_table_title" width="10%">
						所属类别
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_title" width="10%">
							物品单价
						</td>
					</c:if>
					<td class="qinggoudan_table_title" width="10%">
						当前数量
					</td>
					<td class="qinggoudan_table_title" width="10%">
						备注
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_title" width="15%">
							创建时间
						</td>
						<td class="qinggoudan_table_title" width="10%">
							状态
						</td>
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
							<td width="50" class="qinggoudan_table_title">
								详情
							</td>
						</c:if>
					</c:if>
				</tr>
				<c:set var="totalMoney" value="0"></c:set>
				<c:forEach var="goodsInfo" items="${information.goodsInfoList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
							<td class="qinggoudan_table_td2">
								<input type="radio" class="qinggoudan_input011" name="goodsChk" value="${goodsInfo.goodsNum}" />
							</td>
						</c:if>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								<input type="checkbox" class="qinggoudan_input011" name="userChk" value="${goodsInfo.goodsNum}"
										<c:if test="${goodsInfo.goodsState=='U'}">disabled="disabled"</c:if> />
							</td>
						</c:if>
						<td class="qinggoudan_table_td2" width="15%" >
							&nbsp;${fn:escapeXml(goodsInfo.goodsName)}
							<input type="hidden" id="goodsName${goodsInfo.goodsNum}" value="${fn:escapeXml(goodsInfo.goodsName)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsInfo.goodsCode}
							<input type="hidden" id="goodsCode${goodsInfo.goodsNum}" value="${fn:escapeXml(goodsInfo.goodsCode)}" />
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(goodsInfo.typeName)}
							<input type="hidden" id="typeNum${goodsInfo.goodsNum}" value="${fn:escapeXml(goodsInfo.goodsType)}" />
							<input type="hidden" id="typeName${goodsInfo.goodsNum}" value="${fn:escapeXml(goodsInfo.typeName)}" />
						</td>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								&nbsp;${goodsInfo.goodsPrice}
							</td>
						</c:if>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsInfo.goodsCount}
							<input type="hidden" id="goodsCount${goodsInfo.goodsNum}" value="${fn:escapeXml(goodsInfo.goodsCount)}" />
							<input type="hidden" id="goodsPrice${goodsInfo.goodsNum}" value="${fn:escapeXml(goodsInfo.goodsPrice)}" />
						</td>
						<td class="qinggoudan_table_td2">
							<textarea rows="2" cols="20" >${goodsInfo.goodsComm}</textarea>
						</td>
						<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
							<td class="qinggoudan_table_td2">
								&nbsp;<pub:FormatDate  dateNumber="${goodsInfo.createTime}"/>
							</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsInfo.goodsState == 'A' ? '有效' : '无效'}
						</td>
						<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
							<td class="qinggoudan_table_td2">
								<a href="javascript:viewGoodsInfo('${goodsInfo.goodsNum}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>
						</c:if>
						</c:if>
					</tr>
						<c:set var="totalMoney" value="${totalMoney+goodsInfo.goodsPrice*goodsInfo.goodsCount}"></c:set>
				</c:forEach>
				<c:if test="${information.isStatistic eq 'Y'&& information.selectType ==''}">
					<tr>
						<td class="qinggoudan_table_td2" colspan="10">总资产：<fmt:formatNumber  value="${totalMoney}"  pattern="#,#00.00#"/>&nbsp;元</td>
					</tr>
				</c:if>
			</table>
			<pub:page formName="goodsListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value=" 新 增  " onclick="addGoodsInfo()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<c:if test="${information.roleId==1}">
							<input type="button" class="anniu_out" value=" 删 除  " onclick="delGoodsInfo()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							</c:if>
						</td>
					</tr>
				</c:if>
				<c:if test="${information.accessType!=null&&information.accessType=='sel'}">
					<tr>
						<td align="center">
							<c:if test="${information.selectType==null||information.selectType==''}">
								<input type="button" class="anniu_out" value=" 确定  " onclick="selectGoods(${information.rowId})" onMouseOver="className='anniu_over'"
									onMouseOut="className='anniu_out'">
							</c:if>
							<c:if test="${information.selectType!=null&&information.selectType=='outRecord'}">
								<input type="button" class="anniu_out" value=" 确定  " onclick="selectGoodsForRecord()" onMouseOver="className='anniu_over'"
									onMouseOut="className='anniu_out'">
							</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value=" 取消  " onclick="window.close();" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</c:if>
			</table>
			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="indexNO" value="${information.searchForm.indexNO}" />
			<input type="hidden" name="accessType" value="${information.accessType}" />
			<input type="hidden" name="queryTypeState" value="${information.queryTypeState}" />
			<input type="hidden" name="selectType" value="${information.selectType}" />

		</form>

	</body>
</html>
