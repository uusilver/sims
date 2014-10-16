/* added start by weizhen, 2008.10.8, reason:翻页时保存每一页复选框的选中记录 */
// 复选框全选
function checkAllNoDisThis(checkName,mateConfigList)
{
	var checkNameArray=document.getElementsByName(checkName);
	if(document.getElementsByName(checkName)[0].checked ==true)
	{
		for(var i=1;i<checkNameArray.length;i++)
		{
			if (checkNameArray[i].disabled==false)
			{
				checkNameArray[i].checked = true;
				if(mateConfigList!='') {
					mateConfigList += ',';
				}
				mateConfigList += document.getElementsByName('configId'+i)[0].value;
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
				tempId = document.getElementsByName('configId'+i)[0].value;
				mateConfigList = replaceAll(mateConfigList,(tempId+','),'');
				mateConfigList = mateConfigList.replace(tempId,'');
				mateConfigList = filterLastComma(mateConfigList);
			}
		}
	}
	document.getElementsByName('collect')[0].value = mateConfigList;
}

// 复选框单个选
function appendConfigId(mateConfigList) //记录选中的checkbox mateConfigList为已选中列表
{
	var materialNameList = document.getElementsByName("mateChoice");  //复选框
	var tempId ='';
	for(i = 1; i < materialNameList.length; i++)
	{
		if (materialNameList[i].checked) //将选中的值集合
		{
			if(mateConfigList!='') {
				mateConfigList += ',';
			}
			mateConfigList += document.getElementsByName('configId'+i)[0].value;
		}
		else //去掉未选中的
		{
			tempId = document.getElementsByName('configId'+i)[0].value;
			mateConfigList=replaceAll(mateConfigList,(tempId+','),'');
			mateConfigList=mateConfigList.replace(tempId,'');
			mateConfigList=filterLastComma(mateConfigList);
		}
	}
	document.getElementsByName('collect')[0].value = mateConfigList;  //把选中的集合赋入表单
}

// 字符串替换
function replaceAll(str,strDel,strRep)
{
	while(str.indexOf(strDel)!=-1)
	{
		str=str.replace(strDel,strRep);
	}
	return str;
}

// 过滤字符串尾部的逗号
function filterLastComma(mateConfigList) {
	var length = mateConfigList.length;
	// 如果字符串最后一个字符为“,”，则删除
	if (mateConfigList.substr(length - 1, 1) == ',') {
		return mateConfigList.substring(0, length - 1);
	}
	return mateConfigList;
}
/* added end by weizhen, 2008.10.8 */

/* added start by weizhen, 2008.10.16, reason：计算费用总和 */
//计算费用总和
function calcGrossTotal() {
	var moneys = 0.0;
	var reqCount = document.getElementsByName("reqCount");
	var grossPrice = document.getElementsByName("grossPrice");
	var length = grossPrice.length;
	
	for(var i = 0; i < length-1; i++) {
		if(!reqCount[i].value == "" && !checkIsFloat(trim(reqCount[i].value), 10, 6)) {
			alert("数量必须为浮点数，且整数部分长度不得大于10位，小数部分不得大于6位，请重新输入！");
			reqCount[i].focus();
			return;
		}
		if(!grossPrice[i].value == "" && !checkIsFloat(trim(grossPrice[i].value), 10, 4)) {
			alert("预估单价必须为浮点数，且整数部分长度不得大于10位，小数部分不得大于4位，请重新输入！");
			grossPrice[i].focus();
			return;
		}
		// 如果数量和单价都为为空，作计算。
		if(!reqCount[i].value == "" && !grossPrice[i].value == "") {
			moneys += parseFloat(reqCount[i].value) * parseFloat(grossPrice[i].value);
		}
	}
	
	document.getElementsByName("grossTotal")[0].value = forRound(moneys, 2);
}
/* added end by weizhen, 2008.10.16 */






