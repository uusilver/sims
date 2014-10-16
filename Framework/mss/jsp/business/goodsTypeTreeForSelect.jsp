<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

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
	</head>

	<body>
		<form name="goodsInfoManageForm" method="post" action="${contextPath}/mss/jsp/business/goodsInfoController.do?method=saveGoodsInfo">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist" />
		
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						选择物品属性
					</td>
				</tr>
			</table>
			<br><br>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr>
					<td valign="top" rowspan="9" width="40%">
						<div id="treeboxbox_tree" style="width:360px; height:418px;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"></div>
						<div id="loadBranch" style="position:absolute;visibility:hidden;background:gray;filter:alpha(opacity=30);z-index:1000;left:10;top:72;width:360px;height:418px;">
							<table height="100%" align="center"  cellpadding="0" cellspacing="0">
								<tr>
								<td valign="middle"><img src="${contextPath}/mss/image/ajax-loader.gif" alt="" /><a><font style="color:red;">数据加载中......</font></a></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="combineGoodsType()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 取 消 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="window.close();">
					</td>
				</tr>
			</table>
				<input type="hidden" id="accessType" value=<%=request.getParameter("accessType") == null ? "" : request.getParameter("accessType")%> />
				
		</form>
	</body>

</html>
<script type="text/javascript">

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
	//tree.enableThreeStateCheckboxes(true);
	tree.setOnOpenHandler(show);
	
	function combineGoodsType(){
		var checkedItemIds = tree.getAllChecked();
		//var checkedItemIds = tree.getAllCheckedBranches();
		var itemIdArray = checkedItemIds.split('\,');
		var goodsName = "";
		var goodsType = "";
		len = itemIdArray.length;
		for(i=0;i<len;i++){
			//if(itemIdArray[i].length>=4&&itemIdArray[i].substring(0,4)=='temp'){
			//	continue;
			//}
			itemText = convertBack(tree.getItemText(itemIdArray[i]));
			shortName = tree.getUserData(itemIdArray[i],'shortName'+itemIdArray[i]);
			goodsName += itemText;
			goodsType += itemText;
			if(i<len-1){
				goodsType += ',';
			}
		}
		window.opener.document.getElementById("goods_type").value = goodsType;
		window.opener.document.getElementById("goodsTypeStr").value = checkedItemIds;
		window.close();
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
