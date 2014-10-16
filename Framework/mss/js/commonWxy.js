/*
	gotopage(pageValue,diviPagingFormId,diviPagingFormActionURL):点 首页,上一页,下一页,尾页 这几个链接时用 
	@param currPage:当前第几页
	@param diviPagingFormId:分页表单的ID
	@param diviPagingFormActionURL:分页表单的action对应的URL
*/
function gotopage(currPage,diviPagingFormId,diviPagingFormActionURL)
{
	//alert("wangxy");
	document.all.currPage.value=currPage;
	var formGet=document.getElementById(diviPagingFormId);
	formGet.action=diviPagingFormActionURL;
	formGet.submit();
}

function keyDown(diviPagingFormId,diviPagingFormActionURL)
{
  var ieKey= window.event.keyCode;
  if(ieKey == 13)
  {
	  	if(document.all.jumpToPage.value == "")
	  	{
	  		alert("请输入跳转页码！");
	  		return false;
	  	} 
	  	else if(!checkIsNum(document.all.jumpToPage.value))
	  	{
	  		alert('跳转页数应为数字！');
	  		return false;
	  	}
	  	else if(parseInt(document.all.jumpToPage.value) <=0 || parseInt(document.all.jumpToPage.value) > parseInt(document.all.totalPage.value))
	  	{
	  		alert("跳转页数超出范围！");
	  		return false;
	  	} 
	  	else
	  	{
		  	//alert("wangxy");
		  	gotopage(document.all.jumpToPage.value,diviPagingFormId,diviPagingFormActionURL);
		}
   }
}

function checkIsNum(numValue)
{

	var condition = /^[0-9]{1,9}$/;
  	if(!condition.exec(numValue))
  	{
  		return false;
  	}
  	return true;
}


/*
	添加多行的上传
*/

function insRow(tableId,addOrEdit) 
{
	var rowIndex = document.getElementById(tableId).rows.length;//总行数

	var cell = new Array(2);
	var row = document.getElementById(tableId).insertRow(rowIndex);
	row.align="left";
	for (i = 0; i < 2; i++) 
	{
		cell[i] = row.insertCell(i);
	}
	
	if(addOrEdit=='add')
	{
		cell[0].innerHTML = "<input type='file' id='file"+rowIndex+"' name='file"+rowIndex+"' size='20'   onpaste='return false' onkeydown='return false' onchange='showPic("+rowIndex+")'/>  <div id='div"+rowIndex+"'/>";
	}
	else if(addOrEdit=='edit')
	{
		
		cell[0].innerHTML = "<input type='file' id='file"+rowIndex+"' name='file"+rowIndex+"' size='20'   onpaste='return false' onkeydown='return false' onchange='showPic("+rowIndex+")'/>  <div id='div"+rowIndex+"'/>";
	}
	
	cell[1].innerHTML = "<input  type='button'  class='anniu_del' onClick='delTaskTr1(this)'  value='删除附件'/>";	
	

}

function delTaskTr1(obj) 
{    
	var tr = obj.parentNode.parentNode;
	tr.removeNode(true);
}


/**
当为编辑状态时，删除已经上传过的文件，以及上传过的文件对应的图标
*/

function delEditTr(obj,hiddenId,idOfFileListUrl) 
{    
	
	//当用户点击删除按钮时，把删除按钮对应的这条记录的ID，放在这条记录对应的hidden里
	var hiddenId=document.getElementById(hiddenId);
	hiddenId.disabled=false;
	hiddenId.value=idOfFileListUrl;
	
	var tr = obj.parentNode.parentNode;
	var nextTr=tr.nextSibling;
	nextTr.removeNode(true);	
	
	tr.removeNode(true);
	
}

/*显示图片在一个层里*/
function showPic(rowIndex)
{
	//判断上传的类型
	var upId='file'+rowIndex;
	var upAllPath=document.getElementById(upId).value;
	var upSuffix=upAllPath.substring(upAllPath.lastIndexOf('.')+1).toLowerCase();
	//当上传一个空时,即在上传控件里什么都没选时,这时 upSuffix截取到的文件的后缀仍是个空字串,所以这时就会提示
	//用户上传图片 
	if(upSuffix!='gif'&&upSuffix!='jpg'&&upSuffix!='bmp'&&upSuffix!='jpeg'&&upSuffix!='tiff'&&upSuffix!='psd'&&upSuffix!='png'&&upSuffix!='swf')
	{
		alert('只能上传gif,jpg,bmp,jpeg,tiff,psd,png,swf格式的图片,请重新上传!');
		document.getElementById(upId).focus();
		return false;
		
	}

	var fileValue=document.getElementById(upId).value;
	document.getElementById('div'+rowIndex).innerHTML="<img src='"+fileValue+"' height='40' width='80'>";
}



/*
	去掉左右空格
*/
//去左空格
function ltrim(s)
{
	return s.replace( /^\s*/, "");
}
 //去右空格;
function rtrim(s)
{
	return s.replace( /\s*$/, "");
}
 //左右空格;
function trim(s)
{
	return rtrim(ltrim(s));
}

//座机
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

/*
	function getXMLHttpRequest():得到XMLHttpRequest
*/
function getXMLHttpRequest()
{
 	var xMLHttpRequest=null;
 	if (window.XMLHttpRequest) // Non-IE browsers
 	{
 		  xMLHttpRequest= new XMLHttpRequest();  
 	}
 	else  if (window.ActiveXObject) // IE
 	{
 		  xMLHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
 	}
 	
 	return xMLHttpRequest;
}



/*
	屏蔽回车键
*/
function  dealEnter()
{
	if(window.event.keyCode==13)
	{
		window.event.keyCode=9; //27 esc,32 is SPACE,only 9 cannot submit form
		
		return false;
	}

}











