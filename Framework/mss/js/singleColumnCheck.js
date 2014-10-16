/**************************************************对表单上的某一个字段进行校验****************************************/

/*
	function getXMLHttpRequest():得到XMLHttpRequest
*/
function getXMLHttpRequest()
{
 	var xMLHttpRequest=false;
 	try
 	{
	 	if (window.XMLHttpRequest) // Non-IE browsers
	 	{
	 		  xMLHttpRequest= new XMLHttpRequest();  
	 	}
	 	else  if (window.ActiveXObject) // IE
	 	{
	 		  xMLHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	 	}
 	}
 	catch (failed) 
 	{
  		xMLHttpRequest = false;
	}
 	
 	return xMLHttpRequest;
}

/*
	得到字段被校验后的结果
*/
function getResultByChecked(xMLHttpRequest)
{
	if(xMLHttpRequest.readyState == 4)// 客户端Complete
	{
		if (xMLHttpRequest.status == 200) // 服务器端 OK response
		{			
			var result=xMLHttpRequest.responseText;		
			return result;
		}
		else
		{
			alert("返回错误！");
		}
	}

}


/*
	function singleColumnCheck(checkValue,checkUrl):此函数的返回结果为checkRes,checkRes为0表示有重复记录,不通过;checkRes为1表示通过
	checkValue:要校验的字段的值
	checkUrl:用URL指定到校验的方法，其中校验的方法里要得到的参数也要一块指定到这个URL里。
*/

function singleColumnCheck(checkValue,checkUrl,paramChain)
{
	var checkRes;//放校验后的结果：0表示有重复记录,不通过,1表示通过
	if(checkValue!='')
	{
	 	var xMLHttpRequest=getXMLHttpRequest();
		if(xMLHttpRequest)
		{
			xMLHttpRequest.onreadystatechange=function ()
			{
				checkRes=getResultByChecked(xMLHttpRequest);
				//return checkRes;放这里不对，这只能返回到回调函数里
			};
		}
				 
		//get方法
		/*
		var urlAll= checkUrl+paramChain+"&timeGet="+new Date().getTime();//去缓存
		xMLHttpRequest.open("get",urlAll,false);//这里函数是在提交表单时调用，所以要设为同步.
	    xMLHttpRequest.send(null);  */ 		
		
		
		//post方法
		
		checkUrl=checkUrl+"&timeGet="+new Date().getTime();
	    xMLHttpRequest.open("post",checkUrl,false);//这里函数是在提交表单时调用，所以要设为同步.
	    xMLHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
	    xMLHttpRequest.send(paramChain);  
	    
	    
		return checkRes;
	    
	}
	
}





