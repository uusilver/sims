
function submitGoodsInfo()
{
	checkAllButton(true);
	
	var goodsNames = document.getElementsByName('goodsName');
	var goodsTypes = document.getElementsByName('goodsType');
	var salePrices = document.getElementsByName('salePrice');
	var goodsCounts = document.getElementsByName('goodsCount');
	var goodsComments = document.getElementsByName('recordComment');
	
	if(goodsNames.length == 1)
	{
		alert("请至少添加一种物品!");
		checkAllButton(false);
		return false;
	}
	
	for(i = 1; i < goodsNames.length; i++)
	{
		if(trim(goodsNames[i].value) == "")
		{
			alert("第"+i+"行的物品名称为空，请选择物品！");
			goodsNames[i].value = "";
			checkAllButton(false);
			goodsNames[i].focus();
			return false;
		}
		
		for(j = i+1; j<goodsNames.length; j++)
		{	
			if(goodsNames[i].value == goodsNames[j].value )
			{
				alert("第"+j+"行的物品名称与第"+i+"行的相同，请合并！");
				checkAllButton(false);
				return false;
			}
		}
		
		if((trim(salePrices[i].value) == ""))
		{
			alert("第"+i+"行的物品单价不能为空，请填写");
			checkAllButton(false);
			salePrices[i].focus();
			return false;
		}
		
		if((salePrices[i].value != "") && (!checkIsFloat(salePrices[i].value,10,2)))
		{
			alert("第"+i+"行的物品单价超长或者不是数字，请修改！");
			checkAllButton(false);
			salePrices[i].focus();
			return false;
		}	
		
		if((trim(goodsCounts[i].value) == ""))
		{
			alert("第"+i+"行的物品数量不能为空，请填写");
			checkAllButton(false);
			goodsCounts[i].focus();
			return false;
		}
		
		if((goodsCounts[i].value != "") && (!checkIsNum(goodsCounts[i].value)))
		{
			alert("第"+i+"行的物品数量只能为数字，请修改");
			checkAllButton(false);
			goodsCounts[i].focus();
			return false;
		}
		
		if((goodsComments[i].value != "") && (goodsComments[i].value.length>50))
		{
			alert("第"+i+"行的备注超过50个字符，请修改");
			checkAllButton(false);
			goodsComments[i].focus();
			return false;
		}
	}

	if(confirm("您确定要将所填物品入库吗？")){
		document.goodsInRecordForm.submit();
	}
	else
	{
		checkAllButton(false);
	}
}


function saveOutRecord(){
	checkAllButton(true);
	var pathPrefix = document.getElementById('pathPrefix').value;
	if(checkGoodsName() && checkGoodsPrice() && checkGoodsCount() && checkRecordState() && checkClientName()&& checkClientNick()&&
			checkClientTel() && checkClientAddr()&& checkZipCode()&& checkEMail()&& checkRecordComment()
			&& checkAuthorCode(pathPrefix) && checkFinalPayTime() && checkFinalPayPrice() ){

		if(confirm("您确定要保存该流水信息吗？")){
			document.goodsOutRecordForm.submit();
		}
		else
		{
			checkAllButton(false);
		}
	}else{
		alert("请根据提示修改相应内容");
		checkAllButton(false);
		
		if(document.getElementById("selectType2").checked == true){
			document.getElementById("sc").disabled = true; 
		}
	}
}


/**
 * 校验物品名称不能为空
 * @return
 */
function checkGoodsName(){
	var goodsName = document.getElementById("goods_name").value;
	if(goodsName==""){
		var infoDiv = document.getElementById("goods_nameDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "物品名称不能为空，请选择！";
	    return false;
	}
	var infoDiv = document.getElementById("goods_nameDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "";
	return true;
}


/**
 * 校验物品单价
 * @return
 */
function checkGoodsPrice(){
	var salePrice = document.getElementById("sale_price").value;
	var goodsPrice = document.getElementById("goodsPrice").value;
	var recordTypes = document.getElementsByName("recordType");
	var recordType;
	for(i=0;i<recordTypes.length;i++){
		if(recordTypes[i].checked==true){
			recordType = recordTypes[i].value;
			break;
		}
	}
	
	
	if(salePrice==""){
		var infoDiv = document.getElementById("sale_priceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "销售价格不能为空，请填写";
		return false;
	}
	if (checkIsFloat(salePrice,10,2) == false) {
		var infoDiv = document.getElementById("sale_priceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "销售价格不符合要求，请修改";
		return false;
	}
	if(recordType==2){
		if(parseFloat(salePrice)<parseFloat(goodsPrice)){
			var infoDiv = document.getElementById("sale_priceDiv");
			infoDiv.className = "warning";
		    infoDiv.innerHTML = "销售价格低于成本价格，请修改";
			return false;
		}
	}
	var infoDiv = document.getElementById("sale_priceDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "价格格式符合要求";
    return true;
}

/**
 * 校验最终成交单价
 * @return
 */
function checkFinalPayPrice(){
	var finalPayPrice = document.getElementById("final_pay_price").value;
	var goodsPrice = document.getElementById("goodsPrice").value;
	var recordTypes = document.getElementsByName("recordType");
	var clientConfirms = document.getElementsByName("clientConfirm");
	var recordType;
	var clientConfirm;
	for(i=0;i<recordTypes.length;i++){
		if(recordTypes[i].checked==true){
			recordType = recordTypes[i].value;
			break;
		}
	}
	
	for(i=0;i<clientConfirms.length;i++){
		if(clientConfirms[i].checked==true){
			clientConfirm = clientConfirms[i].value;
			break;
		}
	}
	
	if(clientConfirm=='N'){
		return true;
	}
	if(finalPayPrice==""){
		var infoDiv = document.getElementById("final_payPriceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "最终成交价格不能为空，请填写";
		return false;
	}
	
	if (checkIsFloat(finalPayPrice,10,2) == false) {
		var infoDiv = document.getElementById("final_payPriceDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "最终成交价格不符合要求，请修改";
		return false;
	}
	if(recordType==2){
		if(parseFloat(finalPayPrice)<parseFloat(goodsPrice)){
			var infoDiv = document.getElementById("final_payPriceDiv");
			infoDiv.className = "warning";
		    infoDiv.innerHTML = "最终成交价格低于成本价格，请修改";
			return false;
		}
	}
	var infoDiv = document.getElementById("final_payPriceDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "价格格式符合要求";
    return true;
}

/**
 * 校验最终成交时间
 * @return
 */
function checkFinalPayTime(){
	var payTime = document.getElementsByName("payTime");
	var clientConfirms = document.getElementsByName("clientConfirm");
	var recordStatus = document.getElementById('record_state');
	var clientConfirm;
	for(i=0;i<clientConfirms.length;i++){
		if(clientConfirms[i].checked==true){
			clientConfirm = clientConfirms[i].value;
			break;
		}
	}
	
	if(clientConfirm=='N'){
		return true;
	}
	
	if(payTime!=null && payTime.length >0 && payTime[0].value==""){
		var infoDiv = document.getElementById("final_payTimeDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "最终成交时间不能为空，请填写";
		return false;
	}
	
	if(recordStatus!=null && recordStatus.value!='1' ){
		var infoDiv = document.getElementById("record_stateDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "请将【流水状态】设置为‘已付款’！";
	    recordStatus.focus();
		return false;
	}
	
	var infoDiv = document.getElementById("final_payTimeDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "时间格式符合要求";
    return true;
}


/**
 * 校验出库数量
 * @return
 */
function checkGoodsCount(){
	var leftCount = document.getElementById("left_count").value;
	var goodsCount = document.getElementById("goods_count").value;
	var recordTypes = document.getElementsByName("recordType");
	var recordType;
	for(i=0;i<recordTypes.length;i++){
		if(recordTypes[i].checked==true){
			recordType = recordTypes[i].value;
			break;
		}
	}
	if((trim(goodsCount) == ""))
	{
		var infoDiv = document.getElementById("goods_countDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "出库数量不能为空，请填写";
		return false;
	}
	
	if(goodsCount != "" && !checkIsNum(goodsCount))
	{
		var infoDiv = document.getElementById("goods_countDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "出库数量只能为数字，请修改";
		return false;
	}
	if(recordType==2&&parseInt(leftCount)<parseInt(goodsCount)){
		var infoDiv = document.getElementById("goods_countDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "当前库存量低于出库量，请修改";
		return false;
	}
	var infoDiv = document.getElementById("goods_countDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "出库数量符合要求";
    return true;
}

/**
 * 校验流水状态
 * @return
 */
function checkRecordState(){
	var recordState = document.getElementById("record_state").value;
	
	if(recordState == ""){
		var infoDiv = document.getElementById("record_stateDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "请选择流水状态";
		return false;
	}
	if(recordState == "1"){
		var clientConfirms = document.getElementsByName("clientConfirm");
		var clientConfirm;
		
		for(i=0;i<clientConfirms.length;i++){
			if(clientConfirms[i].checked==true){
				clientConfirm = clientConfirms[i].value;
				break;
			}
		}
		if(clientConfirm=='N'){
			var infoDiv = document.getElementById("record_stateDiv");
			infoDiv.className = "warning";
		    infoDiv.innerHTML = "请选择【客户确认】为‘是’，并填相关信息！";
			return false;
		}
	}
	var infoDiv = document.getElementById("record_stateDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "";
    return true;
}

/**
 * 校验客户姓名
 * @return
 */
function checkClientName() {
	var condition = /^[\u4E00-\u9FA5,a-z,A-Z]+$/; //校验是否汉字或者字母
	var clientName = document.getElementById("client_name");
	
	if (clientName.value == "") {
		var infoDiv = document.getElementById("client_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "客户姓名不能为空，请填写！";
		return false;
	}
	
	if(clientName.value.length>5)
	{
		var infoDiv = document.getElementById("client_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "客户姓名不能超过5个字符，请修改";
		return false;
	}
	if(!condition.test(trim(clientName.value))) {
		var infoDiv = document.getElementById("client_nameDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "客户姓名必须是汉字或者字母，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("client_nameDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "客户姓名符合要求";
    return true;
}


function checkClientNick() {
	var condition = /^[\u4E00-\u9FA5,a-z,A-Z,0-9]+$/; //校验是否汉字或者字母或者数字
	var clientNick = document.getElementById("client_nick");
	
	if (clientNick.value == "") {
		var infoDiv = document.getElementById("client_nickDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "客户昵称不能为空，请填写！";
		return false;
	}
	
	if(clientNick.value.length>30)
	{
		var infoDiv = document.getElementById("client_nickDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "客户昵称不能超过30个字符，请修改";
		return false;
	}
	//if(!condition.test(trim(clientNick.value))) {
	//	var infoDiv = document.getElementById("client_nickDiv");
	//	infoDiv.className = "warning";
    //    infoDiv.innerHTML = "客户昵称必须是汉字或者字母或者数字，请修改！";
	//	return false;
	//}
	
	var infoDiv = document.getElementById("client_nickDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "客户昵称符合要求";
    return true;
}


/**
 * 校验是否超长或者是否为数字
 */
function checkClientTel(){
	var condition = /^[0-9]+$/;
	var clientTel = document.getElementById("client_tel").value;
	var numLength = clientTel.length;
	if(numLength==0){
		var infoDiv = document.getElementById("client_telDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "联系电话不能为空，请填写！";
		return false;
	}
	if(numLength>20){
		var infoDiv = document.getElementById("client_telDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "号码长度不能超过20位，请修改！";
		return false;
	}
	if(!condition.test(clientTel)){
		var infoDiv = document.getElementById("client_telDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "联系电话只能为数字，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("client_telDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "联系电话格式符合要求";
    return true;
}

//校验客户地址
function checkClientAddr(){
	var condition = /^[\u4E00-\u9FA5,0-9,a-z,A-Z,(,),（,）]+$/; //校验是否汉字或者字母
	var clientAddr = document.getElementById("client_addr");
	
	if(clientAddr.value==""){
		return true;
	}
	
	if(clientAddr.value.length>80)
	{
		var infoDiv = document.getElementById("client_addrDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "收件地址不能超过80个字符，请修改";
		return false;
	}
	
	if(!condition.test(trim(clientAddr.value))) {
		var infoDiv = document.getElementById("client_addrDiv");
		infoDiv.className = "warning";
       infoDiv.innerHTML = "收件地址必须是汉字、数字、字母、括号或者四者组合，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("client_addrDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "收件地址符合要求";
    return true;
}

//校验客户邮编
function checkZipCode(){
	var condition = /^[0-9]+$/;
	var zipCode = document.getElementById("zip_code").value;
	
	if(zipCode==""){
		return true;
	}
	
	if(zipCode.length>10){
		var infoDiv = document.getElementById("zip_codeDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "邮政编码不能超过10位，请修改！";
		return false;
	}
	if(!condition.test(zipCode)){
		var infoDiv = document.getElementById("zip_codeDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "邮政编码只能为数字，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("zip_codeDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "邮政编码格式符合要求";
    return true;
}

function checkEMail(){
	var condition = /^([-_A-Za-z0-9\.]+)@+([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	var eMail = document.getElementById("e_mail").value;
	
	if(eMail==""){
		return true;
	}
	
	if(eMail.length>80){
		var infoDiv = document.getElementById("e_mailDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "Email长度不能超过80位，请修改！";
		return false;
	}
	if(!condition.test(eMail)){
		var infoDiv = document.getElementById("e_mailDiv");
		infoDiv.className = "warning";
	    infoDiv.innerHTML = "不是有效的Email格式，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("e_mailDiv");
	infoDiv.className = "OK";
    infoDiv.innerHTML = "Email格式符合要求";
    return true;
}


//校验备注
function checkRecordComment() {
	var recordComment = document.getElementById("recordComment");
	
	if(recordComment.value==""){
		return true;
	}
	
	if(recordComment.value.length>50)
	{
		var infoDiv = document.getElementById("record_commDiv");
		infoDiv.className = "warning";
        infoDiv.innerHTML = "备注不能超过50个字符，请修改！";
		return false;
	}
	
	var infoDiv = document.getElementById("record_commDiv");
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
	var editFlag = document.getElementById('editOrView').value;
	if(editFlag!='edit'){
		return true;
	}
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