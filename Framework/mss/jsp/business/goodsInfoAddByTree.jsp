<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-物品类别创建</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${contextPath}/mss/css/dhtmlxtree.css">
		<script type="text/javascript" src="${contextPath}/mss/js/GoodsManage.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/dhtmlxtree.js"></script>
		<script type="text/javascript">
		function selectGoodsType(){
			var url = "/mss/jsp/business/goodsTypeController.do?method=queryGoodsTypeList&accessType=sel&queryTypeState=A";
			window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=600, height=400,top=100,left=100');
		}
		
		//获取当前同名物品总记录数
		function getSameGoodsCount(typeName){
			var url = "${contextPath}/mss/jsp/business/goodsInfoController.do?method=querySameGoodsCount";
			var filter = "typeName=" + encodeURI(typeName); 	// 类别名称
			returnValue = getReturnDataByXMLHttp(url,filter);
		    return returnValue;
		}
		
		
		</script>
	</head>

	<body>
		<form id="goodsInfoManageForm" method="post" action="${contextPath}/mss/jsp/business/goodsInfoController.do?method=saveGoodsInfo">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
		
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新物品信息
					</td>
				</tr>
			</table>
			<br><br>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr>
					<td valign="top" rowspan="9" width="40%">
						<div id="treeboxbox_tree" style="width:360px; height:418px;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;">
						</div>
						<div id="loadBranch" style="position:absolute;visibility:hidden;background:gray;filter:alpha(opacity=30);z-index:1000;left:10;top:72;width:360px;height:418px;">
							<table height="100%" align="center"  cellpadding="0" cellspacing="0">
								<tr>
								<td valign="middle"><img src="${contextPath}/mss/image/ajax-loader.gif" alt="" /><a><font style="color:red;">数据加载中......</font></a></td>
								</tr>
							</table>
						</div>
					</td>
					<td valign="middle" rowspan="9" width="5%">
						<img alt="" style="cursor:pointer;" src="${contextPath}/mss/image/016.jpg" onclick="combineGoodsType();"/>
					</td>
					<td align="center">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
					<input type="hidden" name="goodsNum" value="${information.goodsInfo.goodsNum }" />
					<tr height="30">
						<td width="20%" align="center" class="qinggoudan_table_title">
							物品名称
							<font color="red">*</font>
						</td>
						<td align="left" class="qinggoudan_table_td1">
							<input name="goodsName" id="goods_name" type="text" class="qinggoudan_input023" size="20" maxlength="50"
								value="${information.goodsInfo.goodsName}"/>
							<span id="goods_nameDiv"></span>
						</td>
					</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品编号
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsCode" id="goods_code" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.goodsInfo.goodsCode}"/>
						<span id="goods_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属类别
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsType" id="goods_type" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsInfo.typeName}"/>
							<input type="hidden" name="goodsTypeStr" id="goods_type_str" value="${information.goodsInfo.goodsType}" />
						<span id="goods_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						成本单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsPrice" id="goods_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsPrice}"
							onchange="checkGoodsPrice();">
							元&nbsp;(请按000.00格式填写)
					<span id="goods_priceDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						预估单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="wishPrice" id="wish_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.wishPrice}"
							onchange="checkPrice('wish_price');">
							元&nbsp;(请按000.00格式填写)
					<span id="wish_priceDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						产品克重
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsWeight" id="goods_weight" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsWeight}"
							onchange="checkWeight();">
							元&nbsp;(请按000.00格式填写)
					<span id="goods_weightDiv"></span>
					</td>
				</tr>
				<!-- tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						销售单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="salePrice" id="sale_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsPrice}"
							onchange="checkGoodsPrice()">
							元&nbsp;(请按000.00格式填写)
					<span id="goods_priceDiv"></span>
					</td>
				</tr> -->
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品数量
							<font color="red">*</font>
					</td>
					<c:if test="${information.searchForm.viewOrEdit eq 'add'}">
						<td align="left" class="qinggoudan_table_td1">
							<input name="goodsCount" id="goods_count" type="text" class="qinggoudan_input02" size="20" maxlength="50"
								value=""
								onchange="checkGoodsCount();">
						<span id="goods_countDiv"></span>
						</td>
					</c:if>
					<c:if test="${information.viewOrEdit eq 'edit'}">
						<td align="left" class="qinggoudan_table_td1">
							<input name="goodsCount" id="goods_count" type="text" readonly="readonly" class="qinggoudan_input02" size="20" maxlength="50"
								value="${information.goodsInfo.goodsCount}"
								onchange="checkGoodsCount();">
						<span id="goods_countDiv"></span>
						</td>
					</c:if>
				</tr>
				<c:if test="${information.viewOrEdit eq 'edit'}">
					<tr height="30">
						<td width="20%" align="center" class="qinggoudan_table_title">
							补入数量&nbsp;&nbsp;&nbsp;
						</td>
						<td align="left" class="qinggoudan_table_td1">
							<input name="goodsCountPlus" id="goods_count_plus" type="text" class="qinggoudan_input02" size="20" maxlength="50"
								value=""
								onchange="checkGoodsPlusCount();">
						<span id="goods_count_plusDiv"></span>
						</td>
					</tr>
				</c:if>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select name="goodsState">
						<c:if test="${information.goodsInfo.goodsState==null}">
						<option value="A">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsInfo.goodsState=='A'}">
						<option value="A" selected="selected">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsInfo.goodsState=='U'}">
						<option value="A">有效</option>
						<option value="U" selected="selected">无效</option>
						</c:if>
						</select>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="goodsComment" id="goodsComment" rows="10" cols="30" onchange="checkGoodsComment()">${information.goodsInfo.goodsComm}</textarea>
					<span id="goods_commDiv"></span>
					</td>
				</tr>
				<c:if test="${information.viewOrEdit eq 'edit'}">
					<tr height="30">
						<td width="20%" align="center" class="qinggoudan_table_title">
							授权码
							<font color="red">*</font>
						</td>
						<td align="left" class="qinggoudan_table_td1">
							<input name="authorizeCode" id="author_code" type="text" class="qinggoudan_input02" size="20" maxlength="50"
								value=""
								onchange="checkAuthorCode('${contextPath }');">
						<span id="author_codeDiv"></span>
						<font color="blue">修改物品信息时需要填写【授权码】方可进行修改操作</font>
						</td>
					</tr>
				</c:if>
			</table>
					</td>
				</tr>
			</table>
			

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<c:if test="${information.searchForm.viewOrEdit eq 'add'}">
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveGoodsInfo('${contextPath }')"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						</c:if>
						<c:if test="${information.viewOrEdit eq 'edit'}">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 修 改 " onclick="saveGoodsInfo('${contextPath }')"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						</c:if>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/business/goodsInfoController.do?method=queryGoodsInfoList','viewOrEdit')"-->
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
<script type="text/javascript">
	
	//var start;
	function show(id,mode){
		//var date = new Date();
		//start = date.valueOf();
		if(mode<0){
			document.getElementById("loadBranch").style.visibility = 'visible';
		}
		setTimeout("loadTree('"+id+"','"+mode+"')",1);
		return true;
	}

	function hiddeDiv(){
		document.getElementById("loadBranch").style.visibility = 'hidden';
		//var date1 = new Date();
		//var end = date1.valueOf();
		//alert("cost : "+(end-start)+"ms");
	}
	
	function loadTree(id,mode){
		if(mode<0){
			var url = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=queryGoodsSonTypeById";
			var filter = "typeNum=" + id ; 	// 表名
			returnValue = getReturnDataByXMLHttp(url,filter);
		   	var obj =  eval(returnValue); //由JSON字符串转换为JSON对象 
		   		
			if(obj!='-1'){
				var branchArray = (""+obj).split(",");
				var len = branchArray.length;
				//alert(len);
				tree.deleteChildItems(id);
				var arr = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>";
				arr += "<tree id=\""+id+"\">";
				for(i=0;i<len;i+=3){
					arr += "<item id=\""+branchArray[i]+"\" text=\""+convert(branchArray[i+1])+"\">";
					arr += "<userdata name=\"shortName"+branchArray[i]+"\">"+branchArray[i+2]+"</userdata>";
					arr += "<item id=\"temp"+branchArray[i]+"\"></item></item>";
				}
				arr += "</tree>"
				tree.loadXMLString(arr,hiddeDiv);
			}else{
			    //alert('无子节点');
			    tree.deleteChildItems(id);
			    tree.closeItem(id);
				hiddeDiv();
			}
		}
			
		if(mode>0){
			tree.closeItem(id);
		}
	}
		
	tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
	tree.setSkin('dhx_skyblue');
	tree.setImagePath("${contextPath}/mss/image/dhtml/csh_bluebooks/");
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);
	tree.setOnOpenHandler(show);
	
	
		
	function combineGoodsType(){
		//var checkedItemIds = tree.getAllChecked();
		var checkedItemIds = tree.getAllCheckedBranches();
		if(checkedItemIds.length==0){
			alert("请选择右侧的物品属性！");
			return false;
		}
		var itemIdArray = checkedItemIds.split('\,');
		var goodsName = "";
		var goodsType = "";
		var goodsCode = "";
		var goodsTypeCheck = "";
		len = itemIdArray.length;
		for(i=0;i<len;i++){
			if(itemIdArray[i].length>=4&&itemIdArray[i].substring(0,4)=='temp'){
				continue;
			}
			itemText = convertBack(tree.getItemText(itemIdArray[i]));
			shortName = tree.getUserData(itemIdArray[i],'shortName'+itemIdArray[i]);
			goodsName += itemText;
			goodsTypeCheck += itemText;
			if(i<(len-1)){
				goodsTypeCheck += ",";
			}
			goodsCode += shortName;
			goodsType += itemText;
			if(i<(len-1)){
				goodsType += ',';
			}
		}
		alert(goodsType + '----'+goodsTypeCheck);
		var codeNum = getSameGoodsCount(goodsTypeCheck);
		var prefix = "";
		if(codeNum.length<5){
			for(j=0;j<4-codeNum.length;j++){
				prefix += "0";
			}
			codeNum = prefix+(parseInt(codeNum)+1);
		}
		document.getElementById("goods_name").value = goodsName;
		document.getElementById("goods_type").value = goodsType;
		document.getElementById("goods_code").value = (goodsCode+codeNum).toUpperCase();
		document.getElementById("goods_type_str").value = checkedItemIds;
	}
	
</script>
<c:forEach var="goodsType" items="${information.goodsTypeList}">
	<c:if test="${goodsType.fatherType==null}">
		<script type="text/javascript">
			tree.insertNewChild("0","${goodsType.typeNum}","${goodsType.typeName}",0,0,0,0,'SELECT');
			tree.setUserData("${goodsType.typeNum}","shortName${goodsType.typeNum}","${goodsType.typeShort}");
			tree.insertNewChild("${goodsType.typeNum}","temp${goodsType.typeNum}","",0,0,0,0,'TOP');
		</script>
	</c:if>
</c:forEach>
<script type="text/javascript">
	tree.closeAllItems(0);
</script>
