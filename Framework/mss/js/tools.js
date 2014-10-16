function goList(url, fliterParams){
	window.location.href = url + constructParams(fliterParams);
}

function gotopage(pageValue,formName){
	document.all.currPage.value=pageValue;
	document.getElementsByName(formName)[0].submit();
}

function keyDown(formName){
  var ieKey= window.event.keyCode;
  if(ieKey == 13){
  	event.returnValue=false;
  	var passed = "0";
  	if(document.all.jumpToPage.value == ""){
  		alert("请输入跳转页码！");
  		passed = "1";
  		return;
  	}
  	if(!checkIsNum(document.all.jumpToPage.value)){
  		alert('跳转页数应为数字！')
  		passed = "1";
  		return;
  	}
  	if(parseInt(document.all.jumpToPage.value,10) <= parseInt(0,10) 
  			|| parseInt(document.all.jumpToPage.value,10) > parseInt(document.all.totalPage.value,10)){
  		alert("跳转页数超出范围！");
  		passed = "1";
  		return;
  	}
  	if(passed == "0"){
	  	gotopage(document.all.jumpToPage.value,formName);
	}
  }
}

/*
 * 页面上所有的按钮灰掉或是使能
 * @param oprType:操作类型,true:全部disable，false:全部enable
*/
function checkAllButton(oprType)
{
	var inputs = document.getElementsByTagName("input");
	if(inputs!=null && inputs.length>0) {
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].type=='button'){
				inputs[i].disabled = oprType;
			}
		}
	}
}

//检查输入值是否为浮点数，当整数部分长度、小数部分长度指定的时候，检查是否满足指定条件
function checkIsFloat(floatValue, intLen, decLen){
	var returnValue = true;
	if(floatValue.indexOf('.')!=-1 && floatValue.indexOf('.')!=floatValue.lastIndexOf('.')){
		returnValue = false;
	}else{
		var condition = /^[0-9]{1,20}$/;
	  	if(!condition.exec(floatValue.substring(0,floatValue.indexOf('.'))+floatValue.substring(floatValue.indexOf('.')+1,floatValue.length))){
			returnValue = false;
	  	}
	  	if(returnValue){
	  		var indexPos = floatValue.indexOf('.')==-1?floatValue.length:floatValue.indexOf('.');
	  		if(intLen!=null && intLen!="" && parseInt(indexPos,10)>parseInt(intLen,10)){
	  			returnValue = false;
	  		}
	  		if(intLen!=null && intLen!="" && parseInt(floatValue.length-(indexPos+1),10)>parseInt(decLen,10)){
	  			returnValue = false;
	  		}
	  	}
	}
  	return returnValue;
}
function checkIsNum(numValue){
	//alert("numValue : "+numValue);
	//var condition = /^[0-9]{1,9}$/;
	//数字超过9个就检验不正确。修改by :liuxiang
	var condition = /^[0-9]+$/;
  	if(!condition.exec(numValue)){
  		return false;
  	}
  	return true;
}

/**
 * 校验是否为数字，如果指定长度，则做长度的校验
 * @paream numValue 被校验的值
 * @paream numLength 规定数字长度
 */
function checkNumAndLen(numValue,numLength){
	//alert(numValue);
	var condition = "^[0-9]$";
	if(numLength!='')
		condition = "^[0-9]{1,"+numLength+"}$";
	//alert(condition);
	var re = new RegExp(condition,'ig');
	//alert(re);
  	if(!re.exec(numValue)){
  		return false;
  	}
  	return true;
}

/**
 * 校验是否为正确的mail名称，不包括@.com的校验
 *
**/
function checkMailName(nameValue){
	return /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,19}$/.test(nameValue);
} 

/**
 * 校验是否为正确的mail名称，不包括@.com的校验
 *
**/
function checkMail(mailValue){
	var flag = /^([-_A-Za-z0-9\.]+)@+([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/.test(mailValue);
	return flag;
} 

/**
 * 将选择框的值拷贝到文本框中
 * @param selectObj select对象
 * @param textId 文本框对象的id
 */
function copyToText(selectObj,textId){
   	//alert(selectObj + ":" + textId);
	var textObj = document.getElementById(textId);
	var selectValue = selectObj.options[selectObj.selectedIndex].value;
	//alert(textObj);
	//alert(selectObj.options[selectObj.selectedIndex].value);
	//if(selectValue==""){
	//alert('请选择相应的时间！');
	//	return false;
	//}
	//else
		textObj.value = selectValue;
}

	// 删除字符串的左边空格
   function leftTrim (str) {
      if (typeof(str) != 'string')
         return '';
      var tmp = str;
      var i = 0;
      for (i = 0; i < str.length; i++) {
         if (tmp.substring(0,1) == ' ')
            tmp = tmp.substring(1,tmp.length);
         else
            return tmp;
      }
      return tmp;
   }

   // 删除字符串的右边空格
   function rightTrim (str) {
      if (typeof(str) != 'string')
         return '';
      var tmp = str;
      var i = 0;
      for (i = str.length - 1; i >= 0; i--) {
         if (tmp.substring(tmp.length - 1,tmp.length) == ' ')
            tmp = tmp.substring(0,tmp.length - 1);
         else
            return tmp;
      }
      return tmp;
   }

   // 删除字符串的两边空格
   function trim (str) {
      if (typeof(str) != 'string')
         return '';
      var tmp = leftTrim(str);
      return rightTrim(tmp);
   }

	function  trimByRegexp(str)
	{
	  var   RegExp1=new   RegExp("[   \n\r]","g");
	   return   str.replace(RegExp1,"");
	}

/**
 * 校验数字和字母组合的字符串
 */
function VerifyNumMixLetter(pwd){
  var condition = /^[a-zA-Z0-9]{1,}$/;
  if(!condition.exec(pwd)){
  return false;
  }
  return true;
}

/**
 * 根据指定的小数位数将数字进行四舍五入
 * @param num 目标数值
 * @param digit 要保留的小数位数
 */
function forRound(num,digit)
{
	//alert("-----forRound---------");
	num=Math.round(num*Math.pow(10,digit))/Math.pow(10,digit);
	return num;
}

/**
 * 根据制定的长度将字符串进行换行
 * @ param str 需要进行转换的字符串
 * @ param len 进行换行的长度
*/
function newLineByLnegth(str,len){
	var returnStr = "";
	for(var i=0; i<str.length; i++){
		if(str.length < len){
			returnStr += str.substring(len*i + 0,str.length) + "\r";
		}else{
			returnStr += str.substring(len*i + 0,len*i + len) + "\r";
		}
	}
	return returnStr;
}

/*
 * 页面上的复选框的全选或反全选
 * @param checkName 要全选的复选框的名字
 * @param disFlag 是否屏蔽已disabled的checkbox:true:屏蔽,false:不屏蔽
*/
function checkAll(checkName,disFlag)
{
	var checkNameArray=document.getElementsByName(checkName);
	if(document.getElementsByName(checkName)[0].checked == true)
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			if((disFlag!=null&&disFlag=='true'&&checkNameArray[i].disabled!=true) || (disFlag==null || disFlag=="" || disFlag=='false')){
				checkNameArray[i].checked = true;
			}
		}
	}	
	else
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			checkNameArray[i].checked = false;
		}
	}
}
function checkAll(obj, checkName,disFlag)
{
	var checkNameArray=document.getElementsByName(checkName);
	if(obj.checked == true)
	{
		for(var i=0;i<checkNameArray.length;i++)
		{
			if((disFlag!=null&&disFlag=='true'&&checkNameArray[i].disabled!=true) || (disFlag==null || disFlag=="" || disFlag=='false')){
				checkNameArray[i].checked = true;
			}
		}
	}	
	else
	{
		for(var i=0;i<checkNameArray.length;i++)
		{
			checkNameArray[i].checked = false;
		}
	}
}
/*
 * 页面上的复选框的全选或反全选，不包括disabled=true
 * @param checkName 要全选的复选框的名字
*/
function checkAllNoDis(checkName)
{
	var checkNameArray=document.getElementsByName(checkName);
	if(document.getElementsByName(checkName)[0].checked ==true)
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			if (checkNameArray[i].disabled==false)
			{
				checkNameArray[i].checked = true;
			}
		}
	}
	else
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			if (checkNameArray[i].disabled==false)
			{
				checkNameArray[i].checked = false;
			}
		}
	}
}
/*
 * 返回字符串的长度，双字节的占两个单位长度
 * 
 * @author liuxiang
 */
String.prototype.len = function()
{
	return this.replace(/[^\x00-\xff]/g,"**").length;
}

/*
 * 检验是否为电话号码
 * 包含格式：025-12345678、（025）-12345678、(025)-12345678、12345678、013812345678、13812345678
 * 电话号码可以从3到8位
 *
 *@author liuxiang
 */
function isPhoneRegex(phone)
{
	return /(^[0-9]{3,4}\-{0,1}[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^(\(|（)[0-9]{3,4}(\)|）)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/.test(phone);
}

/*
 * 检验是否为电话号码（匹配放宽）
 * 只要包含数字或"-"或"()"或"（）"即可
 *
 * @author liuxiang
 */
function isPhoneCall(phone)
{
	var str = new String(phone);
	var phoneStr = new String("0123456789-()（）");
	for(var i = 0; i < str.length; i++){
		if (phoneStr.indexOf(str.charAt(i)) < 0)
			return false;
	}
	return true;
}

/**
* 针对textarea,实现类似input的maxLength属性
**/
function checkTextareaLen(obj, len){
	if(trim(obj.value).length>=len){
	alert(trim(obj.value).substring(0,128));
		obj.value = trim(obj.value).substring(0,len);
		obj.style.readOnly = true;
	}else{
		obj.style.readOnly = false;
	}
}	

/**
 * 根据传过来的参数组合返回的urlFilterStr
**/
function constructParams(filterStr)
{
	var returnParamStr = "";
	if(filterStr!=null && filterStr!=''){
		var fliterArr = filterStr.split(',');
		for(var i=0; i<fliterArr.length; i++){
			if(document.getElementsByName(fliterArr[i]).length>0 && document.getElementsByName(fliterArr[i])[0].value!=""){
				returnParamStr += "&" + fliterArr[i] + "=" + encodeURI(document.getElementsByName(fliterArr[i])[0].value);
			}
		}
	}
	//alert(returnParamStr);
	return returnParamStr;
}

/*
 * 将人民币转换为大写
*/
function convertCurrency(currencyDigits) { 
    var MAXIMUM_NUMBER = 99999999999.99; 
    //常量
    var CN_ZERO = "零"; 
    var CN_ONE = "壹"; 
    var CN_TWO = "贰"; 
    var CN_THREE = "叁"; 
    var CN_FOUR = "肆"; 
    var CN_FIVE = "伍"; 
    var CN_SIX = "陆"; 
    var CN_SEVEN = "柒"; 
    var CN_EIGHT = "捌"; 
    var CN_NINE = "玖"; 
    var CN_TEN = "拾"; 
    var CN_HUNDRED = "佰"; 
    var CN_THOUSAND = "仟"; 
    var CN_TEN_THOUSAND = "万"; 
    var CN_HUNDRED_MILLION = "亿"; 
    var CN_DOLLAR = "圆"; 
    var CN_TEN_CENT = "角"; 
    var CN_CENT = "分"; 
    var CN_INTEGER = "整"; 
     
	//变量
    var integral;	//分角，小数部分 
    var decimal;    //圆，整数部分
    var outputCharacters;    //转换后的结果
    var parts; 
    var digits, radices, bigRadices, decimals; 
    var zeroCount; 
    var i, p, d; 
    var quotient, modulus; 
     
	//首先校验输入值是否正确
    currencyDigits = currencyDigits.toString(); 
    if (currencyDigits == "") { 
        alert("Empty input!"); 
        return ""; 
    } 
    if (currencyDigits.match(/[^,.\d]/) != null) { 
        alert("Invalid characters in the input string!"); 
        return ""; 
    } 
    if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) { 
        alert("Illegal format of digit number!"); 
        return ""; 
    } 
     
	// Normalize the format of input digits: 
    currencyDigits = currencyDigits.replace(/,/g, "");    // Remove comma delimiters. 
    currencyDigits = currencyDigits.replace(/^0+/, "");    // Trim zeros at the beginning. 
    // Assert the number is not greater than the maximum number. 
    if (Number(currencyDigits) > MAXIMUM_NUMBER) { 
        alert("Too large a number to convert!"); 
        return ""; 
    } 
     
	// Process the coversion from currency digits to characters: 
    // Separate integral and decimal parts before processing coversion: 
    parts = currencyDigits.split("."); 
    if (parts.length > 1) { 
        integral = parts[0]; 
        decimal = parts[1]; 
        // Cut down redundant decimal digits that are after the second. 
        decimal = decimal.substr(0, 2); 
    } 
    else { 
        integral = parts[0]; 
        decimal = ""; 
    } 
    // Prepare the characters corresponding to the digits: 
    digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE); 
    radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND); 
    bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION); 
    decimals = new Array(CN_TEN_CENT, CN_CENT); 
    // Start processing: 
    outputCharacters = ""; 
    // Process integral part if it is larger than 0: 
    if (Number(integral) > 0) { 
        zeroCount = 0; 
        for (i = 0; i < integral.length; i++) { 
            p = integral.length - i - 1;
            d = integral.substr(i, 1); 
            quotient = p / 4; 
            modulus = p % 4; 
            if (d == "0") {
                zeroCount++; 
            }
            else { 
                if (zeroCount > 0) 
                { 
                    outputCharacters += digits[0]; 
                } 
                zeroCount = 0; 
                outputCharacters += digits[Number(d)] + radices[modulus]; 
            } 
            if (modulus == 0 && zeroCount < 4) { 
                outputCharacters += bigRadices[quotient]; 
            } 
        } 
        outputCharacters += CN_DOLLAR; 
    } 
    // Process decimal part if there is: 
    if (decimal != "") { 
        for (i = 0; i < decimal.length; i++) { 
            d = decimal.substr(i, 1); 
            if (d != "0") { 
                outputCharacters += digits[Number(d)] + decimals[i]; 
            } 
        } 
    } 
    // Confirm and return the final output string: 
    if (outputCharacters == "") { 
        outputCharacters = CN_ZERO + CN_DOLLAR; 
    } 
    if (decimal == "") { 
        outputCharacters += CN_INTEGER; 
    } 
    return outputCharacters; 
} 

//用层显示信息处理状态
//显示信息：Status.showInfo("订单发送成功！");
//隐藏层：window.setTimeout("Status.setStatusShow(false)",1);时间可以自己设置
var Status=new function(){
    this.statusDiv=null;
	this.init=function() {
	    if (this.statusDiv!=null) {
	        return;
	    }
		var body = document.getElementsByTagName("body")[0];
		var div = document.createElement("div");
		div.style.position = "relative";
		div.style.top = "50%";
		div.style.left = "50%";
		div.style.width = "280px";
		div.style.margin = "-50px 0 0 -100px";		
		div.style.padding = "15px";
		div.style.backgroundColor = "#353555";
		div.style.border = "1px solid #CFCFFF";
		div.style.color = "#CFCFFF";
		div.style.fontSize = "14px";
		div.style.textAlign = "center";
		div.id = "status";
		body.appendChild(div);
		div.style.display="none";
		this.statusDiv=document.getElementById("status");
	}
	this.showInfo=function(_message) {	  
	    if (this.statusDiv==null) {
	        this.init();
	    }  
	    this.setStatusShow(true);
	    this.statusDiv.innerHTML = _message;	    
	}
	this.setStatusShow=function(_show) {	  
	    if (this.statusDiv==null) {
	        this.init();
	    } 
	    if (_show) {
	        this.statusDiv.style.display="";
	    }
	    else {
	        this.statusDiv.innerHTML="";
	        this.statusDiv.style.display="none";
	    }
	}
}

//======================================动态添加行============================//
/**
  * 例子：
  *	<form name="form1">
  * 	<table id="editTab">
  *			<tr class="clsTitle">
  * 			<td>物品名称</td>
  * 			<td><input type="button" value="添加" obnclick="addNewRow('editTab','copiedTr')"></td>
  * 		</tr>
  *			<tr class="cls">
  * 			<td><input type="text" name="in"></td>
  * 			<td><input type="button" value="删除" obnclick="delOneRow('editTab')"></td>
  * 		</tr>
  * 	</table>
  *	</form>
  *	<form name="form2">
  * 	<table>
  *			<tr id="copiedTr" class="cls">
  * 			<td><input type="text" name="in"></td>
  * 			<td><input type="button" value="删除" obnclick="delOneRow('editTab')"></td>
  * 		</tr>
  * 	</table>
  *	</form>
  *
  *	说明：1.这种方式可以很方便的将copiedTr内的元素复制到editTab中，实现动态增减行，避免了手动的方式对新增行的每个td设值，只要做好相应的设置即可。
  * 	 2.不将copiedTr放到form1中的原因是，避免提交form1的时候提交一个空行copiedTr，导致保存结果的时候报nullpoiter。
  **/
//添加一行
function addNewRow(tabId, trId){
	indexs = document.getElementById(tabId).rows.length;
	newline = document.getElementById(tabId).insertRow(indexs);
	newline.className = document.getElementById(trId).className;
	newline.id = indexs;
	var tdObj = document.getElementById(trId).children;
	for(var i=0; i<tdObj.length; i++){
		var cell = newline.insertCell(i);
		cell.innerHTML = tdObj[i].innerHTML;
		cell.className = tdObj[i].className;
		
	}
}

//删除一行
function delOneRow(delTabRow){
	//获取要删除的tr对象
	var trObj = delTabRow.parentNode.parentNode;
	
	//获取该行所属的table对象
	var tabObj = trObj.parentNode;
	tabObj.deleteRow(trObj.rowIndex);
	
	//重编行Id
	var rowArray = tabObj.rows;
	var indexs = rowArray.length;
	for(var i=2; i<indexs; i++){
		rowArray[i].id = i;
	}
}

function VerifyMobilePhone(phoneNum){
  var condition = /^1[3,5]{1}[0-9]{9}$/;
  if(!condition.exec(phoneNum)){
	  return false;
  }else
  return true;
}

//集中校验电话号码是否为手机号码
//nameStr要校验字段的名称字符串，formName要校验的表单名称,alertStr要提示的信息, alertStr要校验字段的提示信息字符串
function checkAllMobilePhone(nameStr, alertNameStr, formName){
	var phoneArr = nameStr.split(',');
	var alertArr = alertNameStr.split(',');
	for(var i=0; i<phoneArr.length; i++){
		var phone = document.getElementById(formName).document.getElementsByName(phoneArr[i]);
		for(var j=0; j<phone.length; j++){
			if(phone[j].value!='' && !VerifyMobilePhone(phone[j].value)){
				alert("请输入有效的手机号码！");
				phone[j].focus();
				return false;
			}
		}
	}
	return true;
}

//集中校验电话号码是否正确
//nameStr要校验字段的名称字符串，formName要校验的表单名称,alertStr要提示的信息, alertStr要校验字段的提示信息字符串
function checkAllPhone(nameStr, alertNameStr, formName){
	var phoneArr = nameStr.split(',');
	var alertArr = alertNameStr.split(',');
	for(var i=0; i<phoneArr.length; i++){
		var phone = document.getElementById(formName).document.getElementsByName(phoneArr[i]);
		for(var j=0; j<phone.length; j++){
			if(phone[j].value!='' && !isPhoneRegex(phone[j].value)){
				alert("请输入正确格式的" + alertArr[i] + "！");
				phone[j].focus();
				return false;
			}
		}
	}
	return true;
}

//集中字符是否为浮点数
//intLen整数部分长度, decLen小数部分长度, nameStr要校验字段的名称字符串, alertStr要校验字段的提示信息字符串，formName要校验的表单名称
function checkAllFloat(intLen, decLen, nameStr, alertNameStr, formName){
	var floArr = nameStr.split(',');
	var alertArr = alertNameStr.split(',');
	for(var i=0; i<floArr.length; i++){
		var flo = document.getElementById(formName).document.getElementsByName(floArr[i]);
		for(var j=0; j<flo.length; j++){
			if(flo[j].value!='' && !checkIsFloat(flo[j].value, intLen, decLen)){
				alert((alertArr[i]==null?"数值":alertArr[i]) + "应该为数值，其中整数部分长度不能超过" + intLen + "，小数部分长度不能超过" + decLen + "，请修改！");
				flo[j].focus();
				return false;
			}
		}
	}
	return true;
}

//集中字符是否为整数
//nameStr要校验字段的名称字符串, alertStr要校验字段的提示信息字符串，formName要校验的表单名称
function checkAllNum(nameStr, alertNameStr, formName){
	var numArr = nameStr.split(',');
	var alertArr = alertNameStr.split(',');
	for(var i=0; i<numArr.length; i++){
		var num = document.getElementById(formName).document.getElementsByName(numArr[i]);
		for(var j=0; j<num.length; j++){
			if(num[j].value!='' && !checkIsNum(num[j].value)){
				alert(alertArr[i] + "必须为整数，请修改！");
				num[j].focus();
				return false;
			}
		}
	}
	return true;
}

//集中校验指定日期是否小于于当前日期
//compareType比较方式:1：大于当前日期，2：小于当前日期，3：大于等于当前日期，4：小于等于当前日期，5：等于当前日期,
//nameStr要校验字段的名称字符串, alertStr要校验字段的提示信息字符串，formName要校验的表单名称
function checkDate(compareType, nameStr, alertNameStr, formName){
	var numArr = nameStr.split(',');
	var alertArr = alertNameStr.split(',');
	var date = new Date();
	var dateValue = date.getYear() + "" + ((date.getMonth()+1) < 10 ? "0" + (date.getMonth()+1) : (date.getMonth()+1)) 
					+ "" + ((date.getDate()) < 10 ? "0" + (date.getDate()) : (date.getDate()));
	
	for(var i=0; i<numArr.length; i++){
		var num = document.getElementById(formName).document.getElementsByName(numArr[i]);
		for(var j=0; j<num.length; j++){
			if(num[j].value!=''){
				if(!checkIsValidDate(num[j].value,'yyyyMMdd')){
					alert(alertArr[i] + "的的格式应该为yyyyMMdd，请修改！");
					num[j].focus();
					return false;
				}
				
				if(compareType=='1' && parseInt(num[j].value, 10) > parseInt(dateValue, 10)) {
					alert(alertArr[i] + "不能大于当前日期，请修改！");
					num[j].focus();
					return false;
				}else if(compareType=='2' && parseInt(num[j].value, 10) < parseInt(dateValue, 10)){
					alert(alertArr[i] + "不能小于当前日期，请修改！");
					num[j].focus();
					return false;
				}else if(compareType=='3' && parseInt(num[j].value, 10) >= parseInt(dateValue, 10)){
					alert(alertArr[i] + "必须小于当前日期，请修改！");
					num[j].focus();
					return false;
				}else if(compareType=='4' && parseInt(num[j].value, 10) <= parseInt(dateValue, 10)){
					alert(alertArr[i] + "必须大于当前日期，请修改！");
					num[j].focus();
					return false;
				}else if(compareType=='5' && parseInt(num[j].value, 10) == parseInt(dateValue, 10)){
					alert(alertArr[i] + "不能等于当前日期，请修改！");
					num[j].focus();
					return false;
				}
			}
		}
	}
	return true;
}

function resetQuery(formName)
{
	var target = document.getElementsByName(formName)[0];
	var allElements=target.elements;
	for(var i=0; i < allElements.length; i++) {
		var ele=allElements[i];
		if(ele.type=='text')
		{
			ele. value='';
		}
		/*
		if(ele.type=="checkbox")
		{
		 	ele.disabled=true;
		}
		if(ele.type=='textarea')
		{
			ele. value='';
		}
		*/
		if(ele.type=='select-one')
		{
			ele.value="";
		}
	}
}

/**
 *截取指定长度的字符串，并以...显示剩余内容
 **/
function trimStr(str, len)
{
	if(str.length>len){
		document.write(str.substring(0,len) + "...");
	}else{
		document.write(str);
	}
}

/** 
 * 校验字符串是否为日期型 
 * 返回值： 
 * 如果为空，定义校验通过， 返回true 
 * 如果字符串为日期型，校验通过， 返回true 
 * 如果日期不合法， 返回false 参考提示信息：输入域的时间不合法！（格式包括yyyy-MM-dd,yyyyMMdd） 
 **/
function checkIsValidDate(str, datePattern) {
	if(str == "") {
		return true; 
	}
	var condition = "";
	if(datePattern=='yyyy-MM-dd'){
		if(/^(([0-9]{4})|([0-9]{2}))-([0-9]{1,2})-([0-9]{1,2})$/.test(str)){
				return true;	
		}else{
			return false;	
		}
	}else if(datePattern=='yyyyMMdd'){
		if(/^(([0-9]{4})|([0-9]{2}))([0-9]{1,2})([0-9]{1,2})$/.test(str)){
				return true;	
		}else{
			return false;	
		}
	}else{
		if(/^(([0-9]{4})|([0-9]{2}))-([0-9]{1,2})-([0-9]{1,2})$/.test(str)
			|| /^(([0-9]{4})|([0-9]{2}))([0-9]{1,2})([0-9]{1,2})$/.test(str)){
				return true;	
		}else{
			return false;	
		}
	}
}

/**
 * 打印页面，同时隐藏页面按钮
 **/
function printForm(){
	var buttons = document.getElementsByTagName("input");
	for(var i=0;i<buttons.length;i++){
		if(buttons[i].type=='button'){
			buttons[i].style.visibility = "hidden";
		}
	}
	window.print();
	for(var i=0;i<buttons.length;i++){
		if(buttons[i].type=='button'){
			buttons[i].style.visibility = "visible";
		}
	}
	
}

//选中分页复选框时，页数文本框可以输入，反选分页复选框时，页数文本框不可以输入
function changeDividPage(obj,formalCountPerPage){
	var countPerPage = document.getElementsByName("countPerPage")[0];
	if(obj.checked == true){
		countPerPage.disabled = false;
		countPerPage.value = formalCountPerPage;
		countPerPage.className = "qinggoudan_input02";
	}else if(obj.checked == false){
		countPerPage.disabled = true;
		countPerPage.value = "";
		countPerPage.className = "qinggoudan_input05";
	}
}

/*
 * 页面上所有的按钮灰掉或是使能
 * @param oprType:操作类型,true:全部disable，false:全部enable
*/
function checkAllButton(oprType)
{
	var inputs = document.getElementsByTagName("input");
	if(inputs!=null && inputs.length>0) {
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].type=='button'){
				inputs[i].disabled = oprType;
			}
		}
	}
}


/**
 * 仅允许输入数字
 * @param 
 * @author weizhen
 */
function checkNumber() {
    //判断输入字符的keyCode，数字在48到57之间，超出部分返回false
    if ((event.keyCode >= 48) && (event.keyCode <= 57)) {
        event.returnValue = true;
    } else {
        event.returnValue = false;
    }
}

/**
 * 复选框全选
 * @param checkName:复选框名称
 * @author weizhen
 */
function checkAll(checkName)
{
	var checkNameArray = document.getElementsByName(checkName);
	if(document.getElementsByName(checkName)[0].checked == true)
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			if(checkNameArray[i].disabled == false) { //过滤
				checkNameArray[i].checked = true;
			}
		}
	}
	else
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			if(checkNameArray[i].disabled == false) { //过滤
				checkNameArray[i].checked = false;
			}
		}
	}
}

/**
 * 检查用户名是否规范
 */
function verifyUserName(userLogin){
  var condition = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){0,31}$/;
  if(trim(userLogin.value)==''){
  	alert('请输入用户名！');
  	userLogin.focus();
  	return false;
  }else  if(!condition.exec(userLogin.value)){
  	alert("用户名必须以字母开头，由数字、字母、'.'或是'_'组成，请您检查您输入的用户名是否正确！");
  	userLogin.focus();
  	return false;
  }
  return true;
}



function convert(str){
	var value = trim(str);
	if(value.indexOf('<')!=-1){
		value = value.replace('<','&lt;');
	}
	if(value.indexOf('>')!=-1){
		value = value.replace('>','&gt;');
	}
	if(value.indexOf('?')!=-1){
		value = value.replace('?','&#63;');
	}
	if(value.indexOf('!')!=-1){
		value = value.replace('!','&#33;');
	}
	return value;
}

function convertBack(str){
	var value = trim(str);
	if(value.indexOf('&lt;')!=-1){
		value = value.replace('&lt;','<');
	}
	if(value.indexOf('&gt;')!=-1){
		value = value.replace('&gt;','>');
	}
	if(value.indexOf('&#63;')!=-1){
		value = value.replace('&#63;','?');
	}
	if(value.indexOf('&#33;')!=-1){
		value = value.replace('&#33;','&!');
	}
	return value;
}