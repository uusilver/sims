
var Global_XMLHttpReq = false;

function createXMLHttpRequest() {
	if(window.XMLHttpRequest) { 
		Global_XMLHttpReq = new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {
		try {
			Global_XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				Global_XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
}

function getReturnDataByXMLHttp()
{
	var url = arguments[0];
	var filter = arguments[1];
	createXMLHttpRequest();
	Global_XMLHttpReq.onreadystatechange = process;
	Global_XMLHttpReq.open("POST", url, false);
	Global_XMLHttpReq.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
	Global_XMLHttpReq.send(filter);
	var returnData = Global_XMLHttpReq.responseText;
	return returnData;
}

function process()
{
	if(Global_XMLHttpReq.readyState == 4)
	{
		if(Global_XMLHttpReq.status == 200)
		{
			return Global_XMLHttpReq.responseText;
		}
	}
}


//将指定form的元素提交，不包括button类型
function formToRequestString(form_obj){
	var query_string='';
	var and='';
	for (i=0;i<form_obj.length ;i++ ){
		e=form_obj[i];
		if (e.name!='' && e.type!='button'){
			if (e.type=='select-one'){
				element_value=e.options[e.selectedIndex].value;
			}
			else if (e.type=='checkbox' || e.type=='radio'){
				if (e.checked==false){
					break;	
				}
				element_value=e.value;
			} else {
				element_value=e.value;
			}
			query_string+=and+e.name+'='+element_value.replace(/\&/g,"%26");
			and="&"
		}
	}
	return query_string;
}


/**
 * AJAX检查某表某字段值是否存在。
 * @param node 页面节点对象
 * @param ownerVal 对象所属值。（用于更新功能，填入该对象所属值。新增功能，填入""即可。） 
 * @param tableName 所需要查询的表名
 * @author weizhen
 */
function checkIsExist(node, ownerVal, tableName) {
	
	var _infoDivSuffix = "Div";   //提示信息div的统一后缀
	var nodeId = node.id;         //获取节点id
	var retrunValue = "true";

	if(trim(node.value) == ""){
		showInfo(nodeId + _infoDivSuffix, "");
		return false;
	} else if(trim(node.value) == ownerVal) {
		showInfo(nodeId + _infoDivSuffix, retrunValue);
		return retrunValue;
	} else {
		var url = document.getElementsByName("checkUrl")[0].value;

		var filter = "tableName=" + encodeURIComponent(tableName) + 	// 表名
					"&colName=" + encodeURIComponent(node.id) + 	// 字段名
                 	"&colVal=" + encodeURIComponent(node.value) +	// 字段值
                 	"&ownerVal=" + encodeURIComponent(ownerVal);	// 对象所属值
		
		retrunValue = getReturnDataByXMLHttp(url,filter);
	    showInfo(nodeId + _infoDivSuffix, retrunValue);
	    
	    return retrunValue;
	}
}

/**
 * 显示服务器反馈信息
 * @param infoDivId 显示对象ID
 * @param text 显示文本信息
 * @author weizhen
 */
function showInfo(infoDivId, text) {
    var infoDiv = document.getElementById(infoDivId);  //获取显示信息的div
    var infoName = infoDiv.parentNode.parentNode.cells[0].innerText;//得到第一个td的内容名称
    infoName = infoName.replace(" * ", "");
    
    var status = text;      //反馈信息
    
    if (status == "true") {
        infoDiv.className = "ok";       //检查结果正常
        infoDiv.innerHTML = "该 " + infoName + " 可以正常使用"; //写回详细信息
    } else if (status == "false")  {
        infoDiv.className = "warning";  //检查结果需要用户修改
        infoDiv.innerHTML = "该 " + infoName + " 已经存在，请修改"; //写回详细信息
    } else if (status == "")  {
        infoDiv.className = "warning";  //检查结果需要用户修改
        infoDiv.innerHTML = "该 " + infoName + " 不能为空，请输入"; //写回详细信息
    } else { // 异常信息
    	infoDiv.className = "warning";  //检查结果需要用户修改
        infoDiv.innerHTML = "ajax出现异常,请排查" + status; //写回详细信息
    }
    
}
