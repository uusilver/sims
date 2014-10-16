<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品出库</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/goodsRecord.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript">
		function selectGoods(){
			var url = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList&accessType=sel&queryTypeState=A&selectType=outRecord";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=400,top=100,left=100');
		}
		
		function selectClient(){
			var url = "${contextPath}/mss/jsp/business/clientInfoController.do?method=queryClientInfoList&accessType=sel&queryTypeState=A";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=400,top=100,left=100');
		}
		
		function reverseSet(flag){
			if(flag==1){
				document.getElementById("sc").disabled = false; 
				document.getElementById("client_name").value = "";
				document.getElementById("client_nameDiv").innerHTML = "";
				document.getElementById("client_name").readOnly = true;
				document.getElementById("client_nick").value = "";
				document.getElementById("client_nickDiv").innerHTML = "";
				document.getElementById("client_nick").readOnly = true;
				document.getElementById("client_addr").value = "";
				document.getElementById("client_addrDiv").innerHTML = "";
				document.getElementById("client_addr").readOnly = true;
				document.getElementById("client_tel").value = "";
				document.getElementById("client_telDiv").innerHTML = "";
				document.getElementById("client_tel").readOnly = true;
				document.getElementById("zip_code").value = "";
				document.getElementById("zip_codeDiv").innerHTML = "";
				document.getElementById("zip_code").readOnly = true;
				document.getElementById("e_mail").value = "";
				document.getElementById("e_mailDiv").innerHTML = "";
				document.getElementById("e_mail").readOnly = true;
			}
			if(flag==2){
				document.getElementById("sc").disabled = true; 
				document.getElementById("client_num").value = "";
				document.getElementById("client_name").readOnly = false;
				document.getElementById("client_name").value = "";
				document.getElementById("client_nick").readOnly = false;
				document.getElementById("client_nick").value = "";
				document.getElementById("client_addr").readOnly = false;
				document.getElementById("client_addr").value = "";
				document.getElementById("client_tel").readOnly = false;
				document.getElementById("client_tel").value = "";
				document.getElementById("zip_code").readOnly = false;
				document.getElementById("zip_code").value = "";
				document.getElementById("e_mail").readOnly = false;
				document.getElementById("e_mail").value = "";
			}
		}
		
		function changeTitle(recordType){
			if(recordType==2){
				document.getElementById("goodsCountTd").innerHTML = "出库数量";
				document.getElementById("salePriceTd").innerHTML = "销售单价";
				document.getElementById("clientTd").innerHTML = "购买人";
				
			}
			if(recordType==3){
				document.getElementById("goodsCountTd").innerHTML = "退库数量";
				document.getElementById("salePriceTd").innerHTML = "退还单价";
				document.getElementById("clientTd").innerHTML = "退货人";
			}
		}
		
		function releasePrice(flag){
			var payTimeTr = document.getElementById('payTimeTr');
			var finalPayPriceTr = document.getElementById('finalPayPriceTr');
			var payTime = document.getElementsByName('payTime')[0];
			var finalPayPrice = document.getElementById('final_pay_price');
			if(flag=='1'){
				payTimeTr.style.display = "";
				finalPayPriceTr.style.display = "";
			}
			if(flag=='0'){
				payTimeTr.style.display = "none";
				finalPayPriceTr.style.display = "none";
				payTime.value="";
				finalPayPrice.value="";
			}
		}
		
		function outRecordToBack(){
			document.getElementById('outToBack').value = 'Y';
			document.getElementById('record_type').value = '3';
			saveOutRecord();
		}
		</script>
	</head>

	<body>
		<form name="goodsOutRecordForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=saveGoodsOutOrBackRecord">&nbsp; 
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
		<input type="hidden" name="pathPrefix" id="pathPrefix" value="${contextPath}">
		<input type="hidden" name="editOrView" id="editOrView" value="${information.viewOrEdit}" />
		<input type="hidden" name="outToBack" id="outToBack" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						物品出库
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
			<input type="hidden" name="recordNum" value="${information.goodsRecord.recordNum }"/>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						单据类型
					</td>
					<td align="left" class="qinggoudan_table_td1">
					<c:if test="${information.goodsRecord.recordType==2||information.goodsRecord.recordType==null}">
						<input name="recordType" id="record_type" type="radio" class="qinggoudan_input011" value="2"
						checked onclick="changeTitle(2)">
						出库单
						<input name="recordType" id="record_type" type="radio" class="qinggoudan_input011" value="3"
						onclick="changeTitle(3)">
						退库单
					</c:if>
					<c:if test="${information.goodsRecord.recordType==3}">
						<!-- input name="recordType" id="record_type" type="radio" class="qinggoudan_input011" value="2"
						onclick="changeTitle(2)">
						出库单-->
						<input name="recordType" id="record_type" type="radio" class="qinggoudan_input011" value="3"
						checked onclick="changeTitle(3)">
						退库单
						<c:if test="${information.goodsRecord.fromRecordNum !=null}">
							&nbsp;&nbsp;(来自于【出库单：<a target="_blank" href="${contextPath}/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordById&viewOrEdit=edit&recordNum=${information.goodsRecord.fromRecordNum}&recordType=2">${information.goodsRecord.fromRecordNum}</a>】) 
						</c:if>
					</c:if>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品名称
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsName" id="goods_name" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.goodsName}">
						<input type="hidden" name="goodsNum" id="goodsNum" value="${information.goodsRecord.goodsNum }"/>
						<input type="hidden" name="goodsCode" id="goods_code" value="${information.goodsRecord.goodsCode }"/>
						<input type="button" class="anniu_s_out" value=" 选择 " onMouseOver="className='anniu_s_over'"
								onMouseOut="className='anniu_s_out'" onclick="selectGoods()">
						<span id="goods_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属类别
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsType" id="goods_type" type="text" class="qinggoudan_input023" size="20" maxlength="50" readonly="readonly"
							value="${information.goodsRecord.goodsType}">
							<input type="hidden" name="typeNum" id="typeNum" value="${information.goodsRecord.typeNum}"/>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						库存数量
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="leftCount" id="left_count" type="text" value="${information.goodsRecord.currentCount}" class="qinggoudan_input02" size="20" maxlength="50" readonly="readonly"/>
						<input type="hidden" name="goodsPrice" id="goodsPrice" value=""/>
						此数值为出库或者退库时的库存数量
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" id="salePriceTd" class="qinggoudan_table_title">
						销售单价
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="salePrice" id="sale_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsRecord.salePrice}" onchange="checkGoodsPrice()">&nbsp;&nbsp;元
						<span id="sale_priceDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" id="goodsCountTd" class="qinggoudan_table_title">
						出库数量
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsCount" id="goods_count" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsRecord.goodsCount}" onchange="checkGoodsCount()" />
						<input type="hidden" name="oldGoodsCount" value="${information.goodsRecord.goodsCount}">
						<span id="goods_countDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						流水状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<c:if test="${information.goodsRecord.recordState=='0'}">
								<select name="recordState" id="record_state">
									<option value="">...请选择...</option>
									<option value="0" selected>未付款</option>
									<option value="1">已付款</option>
								</select>
							</c:if>
							<c:if test="${information.goodsRecord.recordState=='1'}">
								<select name="recordState" id="record_state">
									<option value="">...请选择...</option>
									<option value="0">未付款</option>
									<option value="1" selected>已付款</option>
								</select>
							</c:if>
							<c:if test="${information.goodsRecord.recordState!='0'&&information.goodsRecord.recordState!='1'}">
								<select name="recordState" id="record_state">
									<option value="">...请选择...</option>
									<option value="0">未付款</option>
									<option value="1">已付款</option>
								</select>
							</c:if>
							<c:if test="${information.goodsRecord.recordState=='2'}">
								<select name="recordState" id="record_state">
									<option value="2">已退库</option>
								</select>
							</c:if>
						<span id="record_stateDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" id="clientTd"  class="qinggoudan_table_title">
						购买人
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientName" id="client_name" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientName}" onchange="checkClientName()"/>
							<input type="hidden" name="client_num" id="client_num" value="${information.goodsRecord.clientInfo.clientNum }" />
						<input type="radio" class="qinggoudan_input011" name="selectType" id="selectType1" value="1"  onclick="reverseSet(1)" checked />
						<input type="button" class="anniu_s_out" id="sc" value=" 选择 " onMouseOver="className='anniu_s_over'"
								onMouseOut="className='anniu_s_out'" onclick="selectClient()">
						&nbsp;&nbsp;
						<input type="radio" name="selectType" id="selectType2" value="2" class="qinggoudan_input011" onclick="reverseSet(2)" />
						新增
						<span id="client_nameDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						昵称
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientNick" id="client_nick" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientNick}" onchange="checkClientNick()">
						<span id="client_nickDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						联系电话
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientTel" id="client_tel" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientTel}" onchange="checkClientTel()">
						<span id="client_telDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						收件地址
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientAddr" id="client_addr" type="text" class="qinggoudan_input023" size="20" maxlength="80" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientAddr}" onchange="checkClientAddr()">
					<span id="client_addrDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						邮政编码
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="zipCode" id="zip_code" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.zipCode}" onchange="checkZipCode()">
					<span id="zip_codeDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						Email
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="eMail" id="e_mail" type="text" class="qinggoudan_input023" size="20" maxlength="80" readonly="readonly"
							value="${information.goodsRecord.clientInfo.eMail}" onchange="checkEMail()">
					<span id="e_mailDiv"></span>
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						操作人
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;${information.goodsRecord.userName}
					</td>
				</tr>
				
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						创建时间
					</td>
					<td align="left" class="qinggoudan_table_td1">
							&nbsp;<pub:FormatDate  dateNumber="${information.goodsRecord.createTime}"/>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="recordComment" id="recordComment" rows="10" cols="30" onchange="checkRecordComment()">${information.goodsRecord.recordComm}</textarea>
					<span id="record_commDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户确认：
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<c:if test="${information.goodsRecord.clientConfirm eq 'Y'}">
							<input name="clientConfirm" id="client_confirm" type="radio" class="qinggoudan_input011" value="Y"
							checked onclick="releasePrice('1')">
							是
							<input name="clientConfirm" id="client_confirm" type="radio" class="qinggoudan_input011" value="N"
							onclick="releasePrice('0')">
							否
						</c:if>
						<c:if test="${information.goodsRecord.clientConfirm eq 'N'||information.goodsRecord.clientConfirm == null}">
							<input name="clientConfirm" id="client_confirm" type="radio" class="qinggoudan_input011" value="Y"
							onclick="releasePrice('1')">
							是
							<input name="clientConfirm" id="client_confirm" type="radio" class="qinggoudan_input011" value="N"
							checked onclick="releasePrice('0')">
							否
						</c:if>
						<span id="record_stateDiv"></span>
					</td>
				</tr>
				<c:if test="${information.goodsRecord.clientConfirm eq 'N' || information.goodsRecord.clientConfirm ==null}">
					<tr height="30" id="payTimeTr" style="display:none;">
							<td width="20%" align="center" class="qinggoudan_table_title">
								确认时间：
							</td>
							<td align="left" class="qinggoudan_table_td1">
									<pub:dtp format="yyyy-MM-dd HH:mm" name="payTime" size="10" styleClass="qinggoudan_input023" value="${information.goodsRecord.payTime}"/>
									<span id="final_payTimeDiv"></span>
							</td>
					</tr>
					<tr height="30" id="finalPayPriceTr" style="display:none;">
							<td width="20%" align="center" class="qinggoudan_table_title">
								成交单价：
							</td>
							<td align="left" class="qinggoudan_table_td1">
								<input name="finalPayPrice" id="final_pay_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
									value="${information.goodsRecord.finalPayPrice}" onchange="checkFinalPayPrice()" />&nbsp;&nbsp;元
								<span id="final_payPriceDiv"></span>
							</td>
					</tr>
				</c:if>
				<c:if test="${information.goodsRecord.clientConfirm eq 'Y'}">
					<tr height="30" id="payTimeTr">
							<td width="20%" align="center" class="qinggoudan_table_title">
								确认时间：
							</td>
							<td align="left" class="qinggoudan_table_td1">
									<pub:dtp format="yyyy-MM-dd HH:mm" name="payTime" size="10" styleClass="qinggoudan_input023" value="${information.goodsRecord.payTime}"/>
									<span id="final_payTimeDiv"></span>
							</td>
					</tr>
					<tr height="30" id="finalPayPriceTr">
							<td width="20%" align="center" class="qinggoudan_table_title">
								成交单价：
							</td>
							<td align="left" class="qinggoudan_table_td1">
								<input name="finalPayPrice" id="final_pay_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
									value="${information.goodsRecord.finalPayPrice}" onchange="checkFinalPayPrice()" />&nbsp;&nbsp;元
								<span id="final_payPriceDiv"></span>
							</td>
					</tr>
				</c:if>
				<c:if test="${information.viewOrEdit eq 'edit'}">
					<tr height="30">
						<td width="20%" align="center" class="qinggoudan_table_title">
							授权码
							<font color="red">*</font>
						</td>
						<td align="left" class="qinggoudan_table_td1">
							<input name="authorizeCode" id="author_code" type="text" class="qinggoudan_input02" size="20" maxlength="50"
								value="" onchange="checkAuthorCode('${contextPath }');">
							<span id="author_codeDiv"></span>
							<font color="blue">修改物品信息时需要填写【授权码】方可进行修改操作</font>
						</td>
					</tr>
				</c:if>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<c:if test="${information.goodsRecord.recordState!='2' && information.goodsRecord.clientConfirm!='Y'}">
							<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveOutRecord()"
								onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<!-- input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'"
								onclick="goList('${contextPath }/mss/jsp/business/goodsRecordController.do?method=queryGoodsRecordList','viewOrEdit')"-->
							
							<c:if test="${information.goodsRecord.recordType==2 && information.goodsRecord.clientConfirm!='Y'}">
								<input type="button" class="anniu_out" value=" 转 【退库单】" onMouseOver="className='anniu_over'"
									onMouseOut="className='anniu_out'"
									onclick="outRecordToBack();">
								&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
						</c:if>
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="history.go(-1);">
					</td>
				</tr>
			</table>
				<input type="hidden" id="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				
		</form>
	</body>

</html>

