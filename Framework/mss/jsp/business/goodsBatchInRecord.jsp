<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品入库</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/goodsRecord.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		function selectGoods(trId){
			var url = "/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList&accessType=sel&queryTypeState=A&trId="+trId;
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		
		</script>
	</head>

	<body>
		<form name="goodsInRecordForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=saveGoodsInRecord">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品入库
					</td>
				</tr>
			</table>

			<table id="goodsList" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr>
					<td class="qinggoudan_table_title" width="20%">
						物品名称<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_title" width="15%">
						所属类别<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_title" width="10%">
						物品单价<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_title" width="10%">
						入库数量<font color="red">*</font>
					</td>
					<td class="qinggoudan_table_title" width="20%">
						备注
					</td>
					<td class="qinggoudan_table_title" width="10%">
						删除
						&nbsp;&nbsp;
						<input name="addBtn" type="button" class="wuliaoruku_anniu1"  onclick="addNewRow('goodsList','goodsInfo');">	    
					</td>
				</tr>
				<tr id="goodsInfo" style="display:none">
						<td class="qinggoudan_table_td2" width="20%">
							<input name="goodsName" id="goods_name" type="text" class="qinggoudan_input02" size="10" maxlength="20" readonly="readonly"
								value=""/>
							<input type="hidden" name="goodsNum" id="goodsNum" value=""/>
								<input type="button" class="anniu_s_out" value=" 选择 " onMouseOver="className='anniu_s_over'"
								onMouseOut="className='anniu_s_out'" onclick="selectGoods(this.parentNode.parentNode.id)">
						</td>
						<td class="qinggoudan_table_td2" width="15%">
							<input name="typeName" id="type_name" type="text" class="qinggoudan_input02" size="10" maxlength="20" readonly="readonly"
								value=""/>
							<input type="hidden" name="typeNum" id="typeNum" value=""/>
						</td>
						<td class="qinggoudan_table_td2" width="10%">
							<input name="salePrice" id="sale_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
								value="">元
						</td>
						<td class="qinggoudan_table_td2" width="10%">
							<input name="goodsCount" id="goods_count" type="text" class="qinggoudan_input02" size="20" maxlength="50"
								value="">
						</td>
						<td class="qinggoudan_table_td2" width="20%">
							<textarea name="recordComment" id="recordComment" rows="2" cols="20"></textarea>
						</td>
						<td class="qinggoudan_table_td2" width="10%">
							<input name="button" type="button" class="wuliaoruku_anniu2" onClick="delOneRow(this)">
						</td>
					</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="submitGoodsInfo()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList','viewOrEdit')">
					</td>
				</tr>
			</table>
				<input type="hidden" id="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				
		</form>
	</body>

</html>

