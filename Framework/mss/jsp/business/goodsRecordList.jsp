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
			if(isStatistic!=null&&isStatistic.checked == true){
				document.goodsRecordListForm.action = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&isStatistic=Y&currentPage=1";
				document.goodsRecordListForm.submit();
			}else{
				document.goodsRecordListForm.action = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList&currentPage=1";
				document.goodsRecordListForm.submit();
			}
		}
		
		function selectGoodsType(){
			var url = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&accessType=selectTypeByTree&perPageCount=0";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		
		function addGoodsInRecord(){
			window.location = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&viewOrEdit=add&accessType=addGoodsByTree&perPageCount=0";
			//window.location = "${contextPath}/mss/jsp/business/goodsBatchInRecord.jsp?viewOrEdit='add'";
		}
		function addGoodsOutRecord(){
			window.location = "${contextPath}/mss/jsp/business/goodsOutRecord.jsp?viewOrEdit=add";
		}
		
		function addGoodsBatchOutRecord(){
			window.location = "${contextPath}/mss/jsp/business/goodsBatchOutRecord.jsp?viewOrEdit=add";
		}
		
		function addGoodsBackRecord(){
			window.location = "${contextPath}/mss/jsp/business/goodsBackRecord.jsp?viewOrEdit=add";
		}
		
		function viewGoodsRecord(recordNum,recordType){
			//window.location = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordById&viewOrEdit=edit&recordNum=" + recordNum+"&recordType="+recordType;
			var url = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordById&viewOrEdit=edit&recordNum=" + recordNum+"&recordType="+recordType;
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
	
		}
		
		function printGoodsBill(){
			window.location = "${contextPath}/mss/jsp/business/printGoodsBill.jsp";
		}
		
		function updateRecordState(){
			var userChk = document.getElementsByName("userChk");
			var recordIdStr = "";
			for(var i=1;i<userChk.length; i++){
				if(userChk[i].checked == true){
					recordIdStr += userChk[i].value + ",";
				}
			}
			if(recordIdStr==""){
				alert("请选择要修改为【已付款】的记录！");
				return;
			}else if(confirm("您确定要修改选中的物品流水状态吗？")){
				window.location = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=updateRecordState&recordNumStr=" + recordIdStr;
			}
		}
	</script>
	</head>

	<body>

		<form name="goodsRecordListForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品流水信息查询
					</td>
				</tr>
			</table>

			<table width="95%" style="border-style:dashed;border-width:1px; border-color:#999999;" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"  style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_td1">
						流水类型:
						<select name="queryRecordType">
						<c:if test="${information.searchForm.queryRecordType!='1'&&information.searchForm.queryRecordType!='2'&&information.searchForm.queryRecordType!='3'}">
							<option value="">...请选择...</option>
							<option value="1">入库</option>
							<option value="2">出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='1'}">
							<option value="">...请选择...</option>
							<option value="1" selected>入库</option>
							<option value="2">出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='2'}">
							<option value="">...请选择...</option>
							<option value="1">入库</option>
							<option value="2" selected>出库</option>
							<option value="3">退库</option>
						</c:if>
						<c:if test="${information.searchForm.queryRecordType=='3'}">
							<option value="">...请选择...</option>
							<option value="1">入库</option>
							<option value="2">出库</option>
							<option value="3" selected>退库</option>
						</c:if>
						</select>
					</td>
					<c:if test="${information.accessType==null||information.accessType==''||information.accessType=='menu'}">
						<td class="qinggoudan_table_td1" width="13%">
							流水状态:
							<select name="queryRecordState">
								<c:if test="${information.searchForm.queryRecordState!='0'&&information.searchForm.queryRecordState!='1'&&information.searchForm.queryRecordState!='2'}">
										<option value="">...请选择...</option>
										<option value="0">未付款</option>
										<option value="1">已付款</option>
										<option value="2">已退库</option>
								</c:if>
								<c:if test="${information.searchForm.queryRecordState=='0'}">
										<option value="">...请选择...</option>
										<option value="0" selected>未付款</option>
										<option value="1">已付款</option>
										<option value="2">已退库</option>
								</c:if>
								<c:if test="${information.searchForm.queryRecordState=='1'}">
										<option value="">...请选择...</option>
										<option value="0">未付款</option>
										<option value="1" selected>已付款</option>
										<option value="2">已退库</option>
								</c:if>
								<c:if test="${information.searchForm.queryRecordState=='2'}">
										<option value="">...请选择...</option>
										<option value="0">未付款</option>
										<option value="1">已付款</option>
										<option value="2" selected>已退库</option>
								</c:if>
							</select>
						</td>
					</c:if>
					<td class="qinggoudan_table_td1" width="10%">
						物品名称:
						<input name="queryGoodsName" type="text" class="qinggoudan_input02" size="10"
							value="${information.searchForm.queryGoodsName}" maxlength="50" />
					</td>
					<td class="qinggoudan_table_td1">
						客户昵称:
						<input name="queryClientNick" type="text" class="qinggoudan_input02" size="10"
							value="${information.searchForm.queryClientNick}" maxlength="50" />
					</td>
					<td class="qinggoudan_table_td1" width="34%" colspan="2">
						所属类别:
						<input name="queryGoodsType" id="goods_type" type="text" class="qinggoudan_input023" size="35"
							value="${information.searchForm.queryGoodsType}" maxlength="50" readonly="readonly"/>
							&nbsp;
							<input type="hidden" name="goodsTypeStr" id="goodsTypeStr" value="${information.searchForm.queryGoodsTypeStr}"/>
						<input type="button" class="anniu_s_out" value="选  择" onMouseOver="className='anniu_s_over'"
							onMouseOut="className='anniu_s_out'" onclick="selectGoodsType()">
					</td>
					</tr>
					<tr>
					<td class="qinggoudan_table_td1" width="12%">
						操 作 人:
						<pub:link sql="<%=SpmsConstants.QUERY_USER_INFO%>" num="1" title="---请选择---"
							next="false" name="queryOperator" mvalue="${information.searchForm.queryOperator}" />
					</td>
					<td class="qinggoudan_table_td1" width="12%">
						修 改 人:
						<pub:link sql="<%=SpmsConstants.QUERY_USER_INFO%>" num="1" title="---请选择---"
							next="false" name="queryModifier" mvalue="${information.searchForm.queryOperator}" />
					</td>
					<td class="qinggoudan_table_td1" width="12%">
						起始日期:<pub:dtp format="yyyy-MM-dd" name="queryStartTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryStartTime}"/>
					</td>
					<td class="qinggoudan_table_td1" width="12%">
						截至日期:<pub:dtp format="yyyy-MM-dd" name="queryEndTime" size="10" styleClass="qinggoudan_input02" value="${information.searchForm.queryEndTime}"/>
					</td>
					<td class="qinggoudan_table_td1" width="8%">
					<input type="checkbox" id="isStatistic" class="qinggoudan_input011"/>统计金额
					</td>
					<td class="qinggoudan_table_td1" width="13%">
							客户确认付款状态:
							<select name="queryClientConfirm">
								<c:if test="${information.searchForm.queryClientConfirm!='Y'&&information.searchForm.queryClientConfirm!='N'}">
										<option value="">...请选择...</option>
										<option value="N">未确认付款</option>
										<option value="Y">已确认付款</option>
								</c:if>
								<c:if test="${information.searchForm.queryClientConfirm=='N'}">
										<option value="">...请选择...</option>
										<option value="N" selected>未确认付款</option>
										<option value="Y">已确认付款</option>
								</c:if>
								<c:if test="${information.searchForm.queryClientConfirm=='Y'}">
										<option value="">...请选择...</option>
										<option value="N">未确认付款</option>
										<option value="Y" selected>已确认付款</option>
								</c:if>
							</select>
						</td>
				</tr>
				<tr align="center">
					<td class="qinggoudan_table_td01" colspan="6">
						<input type="button" class="anniu_s_out" value=" 搜 索 " onMouseOver="className='anniu_s_out'"
							onMouseOut="className='anniu_s_out'" onclick="query()">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="resetQuery('goodsRecordListForm')" class="anniu_s_out" value=" 重 填 "
							onMouseOver="className='anniu_s_out'" onMouseOut="className='anniu_s_out'">
					</td>
				</tr>
			</table>
		
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="5%" class="qinggoudan_table_title">
						序号
					</td>
					<td width="5%" class="qinggoudan_table_title">
						<input type="checkbox" class="qinggoudan_input011" name="userChk" onclick="javaScript:checkAll('userChk','true');" />全选
					</td>
					<td class="qinggoudan_table_title" width="8%">
						物品名称
					</td>
					<td class="qinggoudan_table_title" width="8%">
						物品编号
					</td>
					<td class="qinggoudan_table_title" width="8%">
						所属类别
					</td>
					<td class="qinggoudan_table_title" width="7%">
						销售单价
					</td>
					<td class="qinggoudan_table_title" width="6%">
						操作数量
					</td>
					<td class="qinggoudan_table_title" width="7%">
						客户昵称
					</td>
					<td class="qinggoudan_table_title" width="6%">
						流水类型
					</td>
					<td class="qinggoudan_table_title" width="6%">
						流水状态
					</td>
					<td class="qinggoudan_table_title" width="5%">
						创建人
					</td>
					<td class="qinggoudan_table_title" width="5%">
						修改人
					</td>
					<td class="qinggoudan_table_title" width="8%">
						备注
					</td>
					<td class="qinggoudan_table_title" width="8%">
						创建时间
					</td>
						<td class="qinggoudan_table_title">
							详情
						</td>
				</tr>
					<c:set var="totalMoney" value="0"></c:set>
				<c:forEach var="goodsRecord" items="${information.goodsRecordList}" varStatus="status">
						<!-- <tr <c:if test="${goodsRecord.recordType eq 2 && (goodsRecord.clientConfirm=='N'||goodsRecord.clientConfirm==null)}">bgcolor="#ffbf74"</c:if>>-->
						<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							&nbsp;${status.index+1}
						</td>
						<td class="qinggoudan_table_td2">
							<input type="checkbox" class="qinggoudan_input011" name="userChk" value="${goodsRecord.recordNum}"
										<c:if test="${goodsRecord.recordState!=0}">disabled="disabled"</c:if> />
						</td>
						<td class="qinggoudan_table_td2" width="10%" >
							&nbsp;${fn:escapeXml(goodsRecord.goodsName)}
						</td>
						<td class="qinggoudan_table_td2" width="8%" >
							&nbsp;${fn:escapeXml(goodsRecord.goodsCode)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.goodsType}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.salePrice}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.goodsCount}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.clientInfo.clientNick}
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${goodsRecord.recordType eq 1}">
								入库
							<c:set var="totalMoney" value="${totalMoney-goodsRecord.salePrice*goodsRecord.goodsCount}"></c:set>
							</c:if>
							<c:if test="${goodsRecord.recordType eq 2}">
								出库
							<c:set var="totalMoney" value="${totalMoney+goodsRecord.salePrice*goodsRecord.goodsCount}"></c:set>
							</c:if>
							<c:if test="${goodsRecord.recordType eq 3}">
								退库
							<c:set var="totalMoney" value="${totalMoney-goodsRecord.salePrice*goodsRecord.goodsCount}"></c:set>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							<c:if test="${goodsRecord.recordState=='0'}">
								<p style="color: #FF1C1C;">未付款</p>
							</c:if>
							<c:if test="${goodsRecord.recordState=='1'}">
								已付款
							</c:if>
							<c:if test="${goodsRecord.recordState!='0'&&goodsRecord.recordState!='1'}">
								完成
							</c:if>
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.userName}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${goodsRecord.modifyUserName}
						</td>
						<td class="qinggoudan_table_td2">
							<textarea cols="15" rows="2">${goodsRecord.recordComm}</textarea> 
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;<pub:FormatDate  dateNumber="${goodsRecord.createTime}"/>
						</td>
							<td class="qinggoudan_table_td2">
								<a href="javascript:viewGoodsRecord('${goodsRecord.recordNum}','${goodsRecord.recordType}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
										border="0"> </a>
							</td>
					</tr>
				</c:forEach>
				<c:if test="${information.isStatistic eq 'Y'}">
					<tr>
						<td class="qinggoudan_table_td2" colspan="10">总收入：<fmt:formatNumber  value="${totalMoney}"  pattern="#,#00.00#"/>&nbsp;元</td>
					</tr>
				</c:if>
			</table>
			<pub:page formName="goodsRecordListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value="物品入库 " onclick="addGoodsInRecord()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value="物品出库 / 退库" onclick="addGoodsOutRecord()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value="物品批量出库" onclick="addGoodsBatchOutRecord()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value="打印账单" onclick="printGoodsBill()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value="设置为已付款" onclick="updateRecordState()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
			</table>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />

		</form>

	</body>
</html>
