
function saveGoodsInfo(pathPrefix){
	checkAllButton(true);
	var isEdit = document.getElementById("viewOrEdit").value;
	var goodsName=document.getElementById('goods_name');
	var goodsNameValue = trim(goodsName.value);
	if(checkGoodsName(goodsName,goodsNameValue,"goods_info")&&checkGoodsType()
			&&checkGoodsPrice()&&checkPrice()&&checkGoodsCount()&&checkGoodsComment()){
		if(isEdit=='add' && confirm("您确定要保存此物品信息吗？"))
		{
			document.getElementsByName("saveBtn")[0].disabled = true;
			document.getElementById('goodsInfoManageForm').submit();
		}
		else if(isEdit=='edit' && checkGoodsPlusCount() && checkAuthorCode(pathPrefix) && confirm("您确定要修改物品信息吗？"))
		{
			document.getElementsByName("saveBtn")[0].disabled = true;
			document.getElementById('goodsInfoManageForm').action = pathPrefix+"/mss/jsp/business/goodsInfoController.do?method=updateGoodsInfo";
			document.getElementById('goodsInfoManageForm').submit();
		}else{
			checkAllButton(false);
		}
	}else{
		alert("请根据提示修改相应的内容！");
		checkAllButton(false);
	}
}


/**
 * 校验物品名称
 * @param node
 * @param ownerVal
 * @param tableName
 * @return
 */
function checkGoodsName(node, ownerVal, tableName) {
	var condition = /^[\u4E00-\u9FA5,0-9,a-z,A-Z]+$/; //校验是否汉字、字母组合
	var goodsName = document.getElementById("goods_name");
	
	if (goodsName.value == "") {
		var infoDiv = document.getElementById("goods_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "物品名称不能为空";
        passCheck = false;
		return false;
	}
	
	if(goodsName.value.length>100)
	{
		var infoDiv = document.getElementById("goods_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "物品名称不能超过100个字符";
        passCheck = false;
		return false;
	}
	
	//if(!condition.test(trim(goodsName.value))) {
	//	var infoDiv = document.getElementById("goods_nameDiv");
	//	infoDiv.className = "warning";
    //    infoDiv.innerHTML = "物品名称必须是汉字或者字母或者二者组合";
    //    passCheck = false;
	//	return false;
	//}

	if (checkIsExist(node, ownerVal, tableName) == "false") {
        passCheck = false;
		return false;
	}
	
	var infoDiv = document.getElementById("goods_nameDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "物品名称合法";
    passCheck = true;
    return true;
}

/**
 * 校验物品所属类别不能为空
 * @return
 */
function checkGoodsType(){
	var goodsTypeValue=trim(document.getElementById('goods_type').value);
	var goodsPriceValue=trim(document.getElementById('goods_price').value);
	if(trim(goodsTypeValue).length<=0)
	{
		var infoDiv = document.getElementById("goods_typeDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "所属类别不能为空，请选择！";
        passCheck = false;
		return false;
	}
	return true;
}

/**
 * 校验物品单价
 * @return
 */
function checkGoodsPrice(){
	var goodsPrice = document.getElementById("goods_price").value;
	if(goodsPrice==""){
		var infoDiv = document.getElementById("goods_priceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "物品价格不能为空，请填写";
		return false;
	}
	if (checkIsFloat(goodsPrice, 10, 2) == false) {
		var infoDiv = document.getElementById("goods_priceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "物品价格不符合要求，请修改";
		return false;
	}
	var infoDiv = document.getElementById("goods_priceDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "物品价格格式符合要求";
    return true;
}

/**
 * 校验物品单价
 * @return
 */
function checkPrice(){
	var goodsPrice = document.getElementById("wish_price").value;
	if(goodsPrice==""){
		var infoDiv = document.getElementById("wish_priceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "物品价格不能为空，请填写";
		return false;
	}
	if (checkIsFloat(goodsPrice, 10, 2) == false) {
		var infoDiv = document.getElementById("wish_priceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "物品价格不符合要求，请修改";
		return false;
	}
	var infoDiv = document.getElementById("wish_priceDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "物品价格格式符合要求";
    return true;
}

/**
 * 校验物品克重
 * @return
 */
function checkWeight(){
	var goodsWeight = document.getElementById("goods_weight").value;
	alert(goodsWeight);
	if(goodsWeight==""){
		var infoDiv = document.getElementById("goods_weightDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "产品克重不能为空，请填写";
		return false;
	}
	if (checkIsFloat(goodsWeight, 5, 2) == false) {
		var infoDiv = document.getElementById("goods_weightDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "产品克重不符合要求，请修改";
		return false;
	}
	var infoDiv = document.getElementById("goods_weightDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "产品克重格式符合要求";
    return true;
}

/**
 * 校验物品入库数量
 * @return
 */
function checkGoodsCount(){
	var goodsCount = document.getElementById("goods_count").value;
	if(goodsCount==""){
		var infoDiv = document.getElementById('goods_countDiv');
		infoDiv.className = 'warning'; 
		infoDiv.innerHTML = '数量不能为空，请填写！';
		return false;
	}
	
	if(!checkIsNum(goodsCount)){
		var infoDiv = document.getElementById('goods_countDiv');
		infoDiv.className = 'warning'; 
		infoDiv.innerHTML = '数量必须为数字';
		return false;
	}
	var infoDiv = document.getElementById('goods_countDiv');
	infoDiv.className = 'OK'; 
	infoDiv.innerHTML = '';
	return true;
}

/**
 * 校验物品补录数量
 * @return
 */
function checkGoodsPlusCount(){
	var goodsPlusCount = document.getElementById("goods_count_plus").value;
	
	if(!checkIsNum(goodsPlusCount)){
		var infoDiv = document.getElementById('goods_count_plusDiv');
		infoDiv.className = 'warning'; 
		infoDiv.innerHTML = '数量必须为数字';
		return false;
	}
	var infoDiv = document.getElementById('goods_count_plusDiv');
	infoDiv.className = 'OK'; 
	infoDiv.innerHTML = '';
	return true;
}

/**
 * 校验物品备注
 * @return
 */
function checkGoodsComment(){
	var goodsComment = document.getElementById("goodsComment");
	if(goodsComment.value==""){
		return true;
	}
	
	if(goodsComment.value.length>50)
	{
		var infoDiv = document.getElementById("goods_commDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "备注不能超过50个字符";
		return false;
	}
	var infoDiv = document.getElementById("goods_commDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "";
    return true;
	
}

/**
 * 校验授权码
 * @param prefixPath
 * @param authorCode
 * @return
 */
function checkAuthorCode(prefixPath){
	var url = prefixPath+"/mss/jsp/sysManage/userManageController.do?method=checkAuthorizPassword";
	var authorCode = document.getElementById("author_code").value;
	if(authorCode==''){
		var infoDiv = document.getElementById('author_codeDiv');
		infoDiv.className = 'warning'; 
		infoDiv.innerHTML = '请填写授权码！';
		return false;
	}
	var filter = "authorCode=" + encodeURI(authorCode); 	// 授权码
	returnValue = getReturnDataByXMLHttp(url,filter);
	if(returnValue=='1'){
		var infoDiv = document.getElementById("author_codeDiv");
		infoDiv.className = "OK";
	    infoDiv.innerHTML = "";
		return true;
	}else{
		var infoDiv = document.getElementById('author_codeDiv');
		infoDiv.className = 'warning'; 
		infoDiv.innerHTML = '授权码不正确！';
		return false;
	}
}