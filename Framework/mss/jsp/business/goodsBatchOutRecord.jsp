<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品批量出库</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/goodsRecord.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery.min.js"></script>
		<script type="text/javascript">
		function selectGoods(trId){
			var url = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList&accessType=sel&queryTypeState=A&trId="+trId;
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		
		function selectGoods(){
			var url = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList&accessType=sel&queryTypeState=A&selectType=outRecord";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		
		function selectClient(){
			var url = "${contextPath}/mss/jsp/business/clientInfoController.do?method=queryClientInfoList&accessType=sel&queryTypeState=A";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
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
		</script>
	</head>

	<body>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table" style="margin:0px;">
			<tr>
				<td class="qinggoudan_title01_td1">
					物品批量出库
				</td>
			</tr>
		</table>
		
			<br><br>
			<form name="goodsOutRecordForm" method="post" action="${contextPath}/mss/jsp/business/goodsRecordController.do?method=saveGoodsBatchOutRecord">
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户姓名:
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
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户昵称:
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientNick" id="client_nick" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientNick}" onchange="checkClientNick()">
						<span id="client_nickDiv"></span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="center" class="qinggoudan_table_title">
						Email:
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="eMail" id="e_mail" type="text" class="qinggoudan_input023" size="20" maxlength="80" readonly="readonly"
							value="${information.goodsRecord.clientInfo.eMail}" onchange="checkEMail()">
					<span id="e_mailDiv"></span>
					</td>
					<td width="20%" align="center" class="qinggoudan_table_title">
						联系电话:
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientTel" id="client_tel" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientTel}" onchange="checkClientTel()">
						<span id="client_telDiv"></span>
					</td>
				</tr>
				<tr>
					<td width="20%" align="center" class="qinggoudan_table_title">
						收货地址:
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="clientAddr" id="client_addr" type="text" class="qinggoudan_input023" size="20" maxlength="80" readonly="readonly"
							value="${information.goodsRecord.clientInfo.clientAddr}" onchange="checkClientAddr()">
					<span id="client_addrDiv"></span>
					</td>
					<td width="20%" align="center" class="qinggoudan_table_title">
						邮政编码:
					</td>
					<td align="left" class="qinggoudan_table_td1">
							<input name="zipCode" id="zip_code" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsRecord.clientInfo.zipCode}" onchange="checkZipCode()">
					<span id="zip_codeDiv"></span>
					</td>
				</tr>
			</table>
			<br><br>
			<table id="outRecords" width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table" style="margin:0px;">
				<tr>
					<td class="qinggoudan_table_title" width="5%">
						序号
					</td>
					<td class="qinggoudan_table_title" width="8%">
						物品名称
					</td>
					<td class="qinggoudan_table_title" width="6%">
						货号
					</td>
					<td class="qinggoudan_table_title" width="12%">
						所属类型
					</td>
					<td class="qinggoudan_table_title" width="8%">
						库存数量
					</td>
					<td class="qinggoudan_table_title" width="10%">
						销售单价</br>（预期 / 实际）
					</td>
					<td class="qinggoudan_table_title" width="10%">
						出库数量
					</td>
					<td class="qinggoudan_table_title" width="10%">
						流水状态
					</td>
					<td class="qinggoudan_table_title" width="10%">
						备注
					</td>
					<td class="qinggoudan_table_title" width="10%">
						小计（预期/实际）
					</td>
				</tr>
				<tr id="tempTr" style="display:none;">
					<td class="qinggoudan_table_td2" id="indexTemp"></td>
					<td class="qinggoudan_table_td2" width="8%" id="goodsNameTemp"></td>
					<td class="qinggoudan_table_td2" width="6%" id="goodsCodeTemp"></td>
					<td class="qinggoudan_table_td2" width="10%" id="goodsTypeTemp"></td>
					<td class="qinggoudan_table_td2" id="leftCountTemp"></td>
					<td class="qinggoudan_table_td2" id="salePriceTemp"></td>
					<td class="qinggoudan_table_td2" id="goodsCountTemp"></td>
					<td class="qinggoudan_table_td2" id="recordTypeTemp"></td>
					<td class="qinggoudan_table_td2" id="recordCommTemp"></td>
					<td class="qinggoudan_table_td2" id="countTemp"></td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1" align="right" colspan="10" id="totalMoney"></td>
				</tr>
			</table>
			<br><br>
		
			<table id="goodsList" width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td style="display:none">
						<input type="radio" name="recordType" id="record_type" checked value="2" />
					</td>
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
						<span id="record_stateDiv"></span>
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
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveGoodsRecord()"
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
<script type="text/javascript">

	var totalMoney = 0.0;
	var totalMoneyFinal = 0.0;
	function saveGoodsRecord(){
	
		checkAllButton(true);
		if(checkGoodsName() && checkGoodsPrice() && checkGoodsCount() && checkRecordState() && checkClientName()&&
				checkClientTel() && checkClientAddr()&& checkZipCode()&& checkEMail()&& checkRecordComment()&&checkFinalPayTime()&&checkFinalPayPrice()){
	
			if(confirm("您确定要保存该流水信息吗？")){
				var url = "${contextPath}/mss/jsp/business/goodsRecordController.do?method=saveGoodsBatchOutRecord";
				var recordType = document.getElementById("record_type").value;
				var goodsNum = document.getElementById("goodsNum").value;
				var goodsName = document.getElementById("goods_name").value;
				var goodsCode = document.getElementById("goods_code").value;
				var goodsType = document.getElementById("goods_type").value;
				var typeNum = document.getElementById("typeNum").value;
				var goodsPrice = document.getElementById("goodsPrice").value;
				var salePrice = document.getElementById("sale_price").value;
				var goodsCount = document.getElementById("goods_count").value;
				var leftCount = document.getElementById("left_count").value;
				var recordComment = document.getElementById("recordComment").value;
				var recordStateArry = document.getElementsByName("recordState");
				var finalPayPrice = document.getElementById("final_pay_price").value;
				var payTime = document.getElementsByName("payTime");
				var clientConfirms = document.getElementsByName("clientConfirm");
				var clientConfirm;
				var recordState;
				for(i=0;i<recordStateArry.length;i++){
					if(recordStateArry[i].selected = true){
						recordState = recordStateArry[i].value;
					}
				}
	
				for(i=0;i<clientConfirms.length;i++){
					if(clientConfirms[i].checked==true){
						clientConfirm = clientConfirms[i].value;
						break;
					}
				}
				
				if(clientConfirm!=null&&clientConfirm=='N'){
					finalPayPrice = 0.0;
				}
				
				var client_num = document.getElementById("client_num").value;
				var clientName = document.getElementById("client_name").value;
				var clientNick = document.getElementById("client_nick").value;
				var clientAddr = document.getElementById("client_addr").value;
				var clientTel = document.getElementById("client_tel").value;
				var zipCode = document.getElementById("zip_code").value;
				var eMail = document.getElementById("e_mail").value;
				
				var trCount = $("#outRecords").find("tr").length;
				$("#indexTemp").text((trCount-2));
				$("#goodsNameTemp").text(goodsName);
				$("#goodsCodeTemp").text(goodsCode);
				$("#goodsTypeTemp").text(goodsType);
				$("#leftCountTemp").text(leftCount);
				$("#salePriceTemp").text(salePrice+' / '+finalPayPrice);
				$("#goodsCountTemp").text(goodsCount);
				if(recordState==0){
					$("#recordTypeTemp").text("未付款");
				}
				if(recordState==1){
					$("#recordTypeTemp").text("已付款");
				}
				$("#recordCommTemp").text((recordComment!=null&&recordComment!="")?recordComment:"无");
				var countMoney = (parseFloat(salePrice)*parseInt(goodsCount));
				var finalCountMoney= (parseFloat(finalPayPrice)*parseInt(goodsCount));
				$("#countTemp").text(countMoney+'/'+finalCountMoney);
				
				totalMoney += countMoney;
				totalMoneyFinal += finalCountMoney;
				var filter = "recordType=" + recordType + 	// 流水类型
							"&goodsNum=" + encodeURI(goodsNum) + 	// 物品ID
							"&goodsName=" + encodeURI(goodsName) + 	// 物品名称
		                 	"&goodsCode=" + goodsCode +	// 物品编码
		                 	"&goodsType=" + encodeURI(goodsType)+	// 物品所属类型
		                 	"&typeNum=" + typeNum+ // 类型编码
							"&goodsPrice=" + goodsPrice + 	// 物品单价
		                 	"&salePrice=" + salePrice +	// 销售价格
		                 	"&goodsCount=" + goodsCount+	// 出库数量
		                 	"&leftCount=" + leftCount+ //当前库存量
							"&recordComment=" + encodeURI(recordComment) + 	// 出库备注
		                 	"&recordState=" + recordState +	// 流水状态
		                 	"&client_num=" + client_num+	// 客户id
		                 	"&clientName=" + encodeURI(clientName)+ //客户姓名
		                 	"&clientNick=" + encodeURI(clientNick)+ //客户昵称
							"&clientAddr=" + encodeURI(clientAddr) + 	// 收件地址
		                 	"&clientTel=" + clientTel +	// 联系电话
		                 	"&zipCode=" + zipCode+	// 邮政编码
		                 	"&eMail=" + eMail+	// email
		                 	"&clientConfirm=" + clientConfirm+	// 买家是否确认付款
		                 	"&payTime=" + (payTime!=null?payTime[0].value:'')+	// 买家付款时间
		                 	"&finalPayPrice=" + finalPayPrice;	// 买家最终付款单价
		        //alert(filter);
				returnValue = getReturnDataByXMLHttp(url,filter);
				var resultArr = returnValue.split('\|');
				document.getElementById("client_num").value = resultArr[0];
				alert(resultArr[2]);
				if(resultArr[1]=="1"){
					appendRecord();
					$("#totalMoney").text("总金额："+parseFloat(totalMoney)+"/"+parseFloat(totalMoneyFinal)+" 元 " );
				}
				clearRecord();
				checkAllButton(false);
			}
			else
			{
				checkAllButton(false);
			}
		}else{
			alert("请根据提示修改相应内容");
			checkAllButton(false);
		}
	}
	
	function appendRecord(){
		var copyTr = $("#tempTr").clone();
		copyTr.removeAttr("id"); 
		copyTr.children().removeAttr("id");
		$("#tempTr").before(copyTr);
		copyTr.show(); 
	}
	
	function clearRecord(){
		document.getElementById("goodsNum").value="";
		document.getElementById("goods_name").value="";
		document.getElementById("goods_code").value="";
		document.getElementById("typeNum").value="";
		document.getElementById("goods_type").value="";
		document.getElementById("goods_count").value="";
		document.getElementById("left_count").value="";
		document.getElementById("sale_price").value="";
		document.getElementById("recordComment").value="";
		document.getElementsByName("payTime")[0].value="";
		document.getElementById("finalPayPrice").value="";
	}
	
</script>
