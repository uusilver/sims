<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/framework/jsp/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物资管理系统</title>
<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css">	
<link rel="stylesheet" type="text/css" href="${contextPath}/mss/css/dhtmlxtree.css">
<script type="text/javascript" src="${contextPath}/mss/js/dhtmlxcommon.js"></script>
<script type="text/javascript" src="${contextPath}/mss/js/dhtmlxtree.js"></script>
<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
<script type="text/javascript">
	
	function fixImage(id){
				switch(tree.getLevel(id)){
				case 1:
				tree.setItemImage2(id,'folderClosed.gif','folderOpen.gif','folderClosed.gif');
					break;
				case 2:
				tree.setItemImage2(id,'folderClosed.gif','folderOpen.gif','folderClosed.gif');			
					break;
				case 3:
				tree.setItemImage2(id,'folderClosed.gif','folderOpen.gif','folderClosed.gif');			
					break;			
				default:
				tree.setItemImage2(id,'leaf.gif','folderClosed.gif','folderOpen.gif');			
					break;
				}
			}
			
			
	function checkIsExist(tableName,fieldName,fieldValue,ownerVal,stateColName){
		var url = "${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist";

		var filter = "tableName=" + tableName + 	// 表名
					"&colName=" + fieldName + 	// 字段名
                 	"&colVal=" + fieldValue +	// 字段值
                 	"&ownerVal=" + ownerVal+	// 对象所属值
                 	"&stateColName=" + stateColName;	// 对象所属值
                
		returnValue = getReturnDataByXMLHttp(url,filter);
	    return returnValue;
	}
	
	//保存类别信息到数据库
	function saveGoodsType(typeName,shortName,fatherId,itemId){
		var url = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=saveGoodsType";
		var filter = "typeName=" + typeName + 	// 类别名称
					"&shortName=" + shortName + 	// 缩写
                 	"&fatherType=" + fatherId + // 父类别Id
                 	"&saveFlag=1" +
                 	"&itemId="+itemId +
                 	"&typeState=A";	
                 	
		returnValue = getReturnDataByXMLHttp(url,filter);
	    return returnValue;
	}
	
	//更新类别信息到数据库
	function updateGoodsType(itemId,typeName,shortName){
		var url = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=saveGoodsType";
		var filter = "typeNum=" + itemId + // 节点编号
                 	"&typeName=" + typeName + 	// 类别名称
					"&shortName=" + shortName + 	// 缩写
                 	"&saveFlag=1&typeState=A";	
                 	
		returnValue = getReturnDataByXMLHttp(url,filter);
	    return returnValue;
	}
	
	//删除类别信息
	function deleteGoodsType(itemId){
		var url = "${contextPath}/mss/jsp/business/goodsTypeController.do?method=deleteGoodsTypeByItemId";
		var filter = "itemId=" + itemId; 	// 类别id	
                 	
		returnValue = getReturnDataByXMLHttp(url,filter);
	    return returnValue;
	}
	
	function checkItemText(typeName){
		var condition = /^[\u4E00-\u9FA5,0-9,a-z,A-Z]+$/; //校验是否汉字、字母组合
	
		if (typeName.value == "") {
	        alert("物品类别名称不能为空！");
			return false;
		}
	
		if(typeName.value.length>80){
	        alert("物品类别名称不能超过80个字符！");
			return false;
		}
		
		//if(!condition.test(trim(typeName.value))) {
	    //    alert("物品类别名称必须是汉字或者字母或者二者组合！");
		//	return false;
		//}
		
		if(typeName.value.indexOf('<')!=-1) {
	        alert("物品类别名称不允许含有[<]字符，请修改！");
			return false;
		}
	
		if (checkIsExist("goods_type","type_name", typeName.value, "","type_state") == "false") {
	        alert("物品类别名称已经存在，请修改！");
			return false;
		}
		return true;
	}

	function checkShortName(shortName){
		var condition = /^[0-9,a-z,A-Z]+$/; //校验是否是字母组合
	
		if (shortName.value == "") {
			alert("类别简称不能为空");
			return false;
		}
		
		if(shortName.value.length>10)
		{
			alert("类别简称不能超过10个字符");
			return false;
		}
		
		if(!condition.test(trim(shortName.value))) {
			alert("类别简称必须是字母或者字母组合，请修改！");
			return false;
		}
		
		return true;
	}
	
</script>
</head>

<body>
      <table>
		<tr>
			<td valign="top"> 
				<div id="treeboxbox_tree" style="width:320px; height:730px;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"></div>
				<div id="loadBranch" style="position:absolute;visibility:hidden;background:gray;filter:alpha(opacity=30);z-index:1000;left:12;top:74;width:320px;height:430px;">
					<table height="100%" align="center"  cellpadding="0" cellspacing="0">
						<tr>
							<td valign="middle"><img src="${contextPath}/mss/image/ajax-loader.gif" alt="" /><a><font style="color:red;">数据加载中......</font></a></td>
						</tr>
					</table>
				</div>
			</td>
			<td></td>
			<td class="qinggoudan_title01_font01" bgcolor="#C1E0FF" valign="top">
			类&nbsp;别&nbsp;名&nbsp;称:<input type="text" class="qinggoudan_input023" value="" id="type_name">
			&nbsp;&nbsp;&nbsp;&nbsp;
			缩写:
			<input type="text" class="qinggoudan_input023" value="" id="type_name_short">
			<input type="checkbox" id="rootItem" class="qinggoudan_input011"/>(添加根目录)
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="anniu_s_out" name="saveBtn" value="添加新类别" onMouseOver="className='anniu_s_over'" onMouseOut="className='anniu_s_out'" onClick="addNextItem();"/>
			<br><br>
			子类别名称:
			<input type="text" class="qinggoudan_input023" value="" id="type_name_child">
			&nbsp;&nbsp;&nbsp;&nbsp;
			缩写:
			<input type="text" class="qinggoudan_input023" value="" id="type_name_child_short">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="anniu_s_out" name="saveBtn" value="添加子类别" onMouseOver="className='anniu_s_out'" onMouseOut="className='anniu_s_out'" onClick="addChildItem();"/>
			
			<br><br>
			类&nbsp;别&nbsp;名&nbsp;称:<input type="text" class="qinggoudan_input023" value="" id="type_name_edit">
			&nbsp;&nbsp;&nbsp;&nbsp;
			缩写:
			<input type="text" class="qinggoudan_input023" value="" id="type_name_edit_short">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="anniu_s_out" name="saveBtn" value="编辑类别" onMouseOver="className='anniu_s_out'" onMouseOut="className='anniu_s_out'" onClick="updateItem();"/>
					
			<br><br>
			
			<input type="button" class="anniu_s_out" name="delBtn" value="删除节点" onMouseOver="className='anniu_s_out'" onMouseOut="className='anniu_s_out'" onClick="deleteItem();"/>
			<p>(请选中要删除的节点)</p>
			<!-- a href="javascript:void(0);" onClick="deleteItem();">删除节点&nbsp;&nbsp;&nbsp;&nbsp;(请选中要删除的节点)</a> -->
			
			</td>
		</tr>
		<br><br>
	</table>
</body>
</html>
<script type="text/javascript">

	function doubleClick(){
		var itemId = tree.getSelectedItemId();
		//alert(itemId);
		var value = tree.getSelectedItemText();
		document.getElementById("type_name_edit").value = convertBack(value);
		document.getElementById("type_name_edit_short").value = tree.getUserData(itemId,"shortName"+itemId);
	}
	
	function addNextItem(){
		var itemText = document.getElementById('type_name');
		var itemUserData = document.getElementById('type_name_short');
		var rootItem = document.getElementById("rootItem");
		if(checkItemText(itemText)&&checkShortName(itemUserData)){
			var d=new Date();
			var itemId = d.valueOf();
			var selectedItemId = tree.getSelectedItemId(); 
			if(selectedItemId==""||rootItem.checked==true){
				tree.insertNewChild("0",itemId,itemText.value,0,0,0,0,'SELECT');
			}else{
				tree.insertNewNext(selectedItemId,itemId,itemText.value,0,0,0,0,'SELECT'); 
			}
			fixImage(itemId);
			
			//保存类别名称、缩写信息到数据库
			var fatherId = tree.getParentId(itemId);
			var newItemId = saveGoodsType(itemText.value,itemUserData.value,fatherId,itemId);
			tree.changeItemId(itemId,newItemId);
			tree.setUserData(newItemId,"shortName"+newItemId,itemUserData.value);
		}
	}
	
	function addChildItem(){
		var itemText = document.getElementById('type_name_child');
		var itemUserData = document.getElementById('type_name_child_short');
		if(tree.getSelectedItemId()==""){
			alert("请先选择一个父类别再进行添加！");
			return false;
		}
		if(checkItemText(itemText)&&checkShortName(itemUserData)){
			var d=new Date(); 
			var itemId = d.valueOf();
			tree.insertNewChild(tree.getSelectedItemId(),itemId,itemText.value,0,0,0,0,'SELECT'); 
			fixImage(itemId);
			//保存类别名称、缩写信息到数据库
			var fatherId = tree.getParentId(itemId);
			var newItemId = saveGoodsType(itemText.value,itemUserData.value,fatherId,itemId);
			tree.changeItemId(itemId,newItemId);
			tree.setUserData(newItemId,"shortName"+newItemId,itemUserData.value);
		}
	}
	
	//更新节点信息
	function updateItem(){
		var newItemText = document.getElementById("type_name_edit").value;
		var newUserData = document.getElementById("type_name_edit_short").value;
		var itemId = tree.getSelectedItemId();
		tree.setItemText(itemId,newItemText,"");
		tree.setUserData(itemId,"shortName"+itemId,newUserData);
		
		//更新节点信息到数据库
		updateGoodsType(itemId,newItemText,newUserData);
	}
	
	function deleteItem(){
		var flag = (tree.getAllSubItems(tree.getSelectedItemId())+"").length;
		if(flag>0){
			alert("请先依次删除此节点下的子节点再进行此删除操作！");
			return false;
		}
		var id = tree.getSelectedItemId();
		tree.deleteItem(id,true);
		//删除数据库中的数据
		deleteGoodsType(id);
	}
	
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
	tree.setOnDblClickHandler(doubleClick);
	tree.setOnOpenHandler(show);
	
</script>
<c:forEach var="goodsType" items="${information.goodsTypeList}">
	<c:if test="${goodsType.fatherType==null}">
		<script type="text/javascript">
			tree.insertNewChild("0","${goodsType.typeNum}","${fn:escapeXml(goodsType.typeName)}",0,0,0,0,'');
			tree.setUserData("${goodsType.typeNum}","shortName${goodsType.typeNum}","${goodsType.typeShort}");
			tree.insertNewChild("${goodsType.typeNum}","temp${goodsType.typeNum}","",0,0,0,0,'TOP');
		</script>
	</c:if>
</c:forEach>
<script type="text/javascript">
	tree.closeAllItems(0);
</script>



