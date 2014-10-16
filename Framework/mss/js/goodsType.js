
function saveGoodsType(){
	checkAllButton(true);
	var isEdit = document.getElementById("viewOrEdit").value;
	var typeName=document.getElementById('type_name');
	var typeNameValue = typeName.value;
	var fatherType = document.getElementsByName("fatherType")[0];
	var fatherTypeValue = fatherType.value;
	if(fatherTypeValue!=null&&fatherTypeValue!=""){
		fatherType.id = "f_type_name";
	}
	if(checkTypeName(typeName, typeNameValue, "goods_type")&&checkShortName()&&checkTypeComment()){
		
		if(isEdit=='add' && confirm("您确定要添加物品种类信息吗？"))
		{
			document.getElementsByName("saveBtn")[0].disabled = true;
			document.getElementById('goodsTypeManageForm').submit();
		}
		else if(isEdit=='edit' && confirm("您确定要修改物品种类信息吗？"))
		{
			document.getElementsByName("saveBtn")[0].disabled = true;
			document.getElementById('goodsTypeManageForm').submit();
			
		}else{
			checkAllButton(false);
		}
	}else{
		alert("请根据提示修改相应内容");	
		checkAllButton(false);
	}
}


/**
 * 校验类别名称
 * @param node
 * @param ownerVal
 * @param tableName
 * @return
 */
function checkTypeName(node, ownerVal, tableName) {
	var condition = /^[\u4E00-\u9FA5,0-9,a-z,A-Z]+$/; //校验是否汉字、字母组合
	var typeName = document.getElementById("type_name");
	
	if (typeName.value == "") {
		var infoDiv = document.getElementById("type_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "物品类别名称不能为空";
		return false;
	}
	
	if(typeName.value.length>6)
	{
		var infoDiv = document.getElementById("type_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "物品类别名称不能超过6个字符";
		return false;
	}
	
	if(!condition.test(trim(typeName.value))) {
		var infoDiv = document.getElementById("type_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "物品类别名称必须是汉字或者字母或者二者组合";
		return false;
	}

	if (checkIsExist(node, ownerVal, tableName) == "false") {
		return false;
	}
	
	var infoDiv = document.getElementById("type_nameDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "物品类别名称合法";
    return true;
}


function checkShortName(){
	var condition = /[^0-9,a-z,A-Z]/g; //校验是否是字母组合
	var shortName = document.getElementById("shortName");
	
	if (shortName.value == "") {
		var infoDiv = document.getElementById("short_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "类别简称不能为空";
		return false;
	}
	
	if(shortName.value.length>4)
	{
		var infoDiv = document.getElementById("short_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "类别简称不能超过4个字符";
		return false;
	}
	
	if(condition.test(trim(shortName.value))) {
		var infoDiv = document.getElementById("short_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "类别简称必须是字母或者字母组合，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("short_nameDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "类别简称合法";
    return true;
}


//校验备注
function checkTypeComment() {
	var typeComment = document.getElementById("typeComment");
	
	if(typeComment.value==""){
		return true;
	}
	
	if(typeComment.value.length>100)
	{
		var infoDiv = document.getElementById("comm_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "备注不能超过100个字符，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("comm_nameDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "";
    return true;
}







