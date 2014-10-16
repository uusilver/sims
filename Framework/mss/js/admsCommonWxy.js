function resetPage(formId)
{
	var target = document.getElementById(formId);
	var allElements=target.elements;

	for(var i=0; i < allElements.length; i++) 
	{
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
			*/
			if(ele.type=='select-one')
			{
				ele.value="";
			}
			if(ele.type=='textarea')
			{
				ele. value='';
			}
	}
	
}

/*
	edit(url,propertyName,propertyValue):用于编辑信息时，扭转到编辑信息页面
	@param url:扭转时，对应后台的方法的url
	@param propertyName:编辑时从list页面，向后台传的一个参数名,一般传的是某个记录的id
	@param propertyValue:和propertyName对应的值
*/



/*
	
	create(url):用于添加信息时，扭转到添加信息页面
	@param url:扭转时，对应后台的方法的url
*/

function create(url)
{
	document.URL=url;	
}



/*
	全选复选框
	@param checkNameOfClick:全选按钮
	@param checkName：点全选按钮后，所处发的check按钮
*/
function checkAll(checkNameOfClick,checkName)
{
	var checkNameOfClick=document.getElementById(checkNameOfClick);
	var checkNameArray=document.getElementsByName(checkName);
	if(checkNameOfClick.checked ==true)
	{
		for(var i=0;i<checkNameArray.length;i++)
		{
			if(checkNameArray[i].disabled==false)
			{
				checkNameArray[i].checked = true;
			}
		}
	}	
	else
	{
		for(var i=0;i<checkNameArray.length;i++)
		{
			if(checkNameArray[i].disabled==false)
			{
				checkNameArray[i].checked = false;
			}
		}
	}
}
 
 

/**
	subFormDel(formId,actionURL)用于点击删除按钮时，提交表单
	@param listOrMaint:当等于list时，表示在list页面，当等于maint时，表示在input页面
*/
function subFormDel(formId,actionURL)
{
	var isCheck=0;
	var target = document.getElementById(formId);
	var targetInput=target.getElementsByTagName("input");
	for(var i=0; i < targetInput.length; i++) 
	{
			
			if(targetInput[i].type=="checkbox"&&targetInput[i].checked==true&&targetInput[i].name!='checkAllName')
			{
				isCheck=isCheck+1;

			}
	}

	if(isCheck==0)
	{
		alert("请选择要删除的记录！");
		return;
	}
	if(isCheck>0)
	{
		if(window.confirm("确定要删除吗?"))
		{}
		else
		{
			return;
		}
	}

	var formGet=document.getElementById(formId);
	formGet.action=actionURL;
	formGet.submit();
}
 
 
/*
	subFormSear(formId,actionURL):List表单上点击查询按钮
*/
function subFormSear(formId,actionURL)
{
	var formGet=document.getElementById(formId);
	formGet.action=actionURL;
	formGet.submit();
}

/*
	subFormSear(formId,actionURL):报表导出
*/
function subFormToExcel(formId,actionURL)
{
	var formGet=document.getElementById(formId);
	formGet.action=actionURL;
	formGet.submit();
}

