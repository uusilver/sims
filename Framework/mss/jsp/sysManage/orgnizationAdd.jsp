<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>业务运营分析系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
	</head>

	<body>
		<form name="orgnizationForm" method="post"
			action="${contextPath}/mss/jsp/OrgnizationOperation.do?method=orgnizationAdd">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table">
				<tr>
					<td class="qinggoudan_title01_td1">
						新增部门机构信息
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2">
						<hr size="1" noshade>
					</td>
				</tr>
			</table>
			<table width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table">
				<tr>
					<td class="qinggoudan_table_td1">
						部门名称
						<font color="red">*</font>:
					</td>
					<td class="qinggoudan_table_td1">
						<input name="orgName" id="orgName" type="text"
							class="qinggoudan_input02" maxlength="20" size="20" style=""
							onchange="checkOrgNameExist()" >
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="checkState" id="checkState" type="text"
							class="qinggoudan_input02" maxlength="100" size="100"
							style="border-style:none;width:80%">
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						上级部门:
					</td>
					<td class="qinggoudan_table_td1">
						<input name="upOrgName" id="upOrgName" " type="text"
							class="qinggoudan_input02" readonly />
						<input name="upOrgId" id="upOrgId" " type="hidden" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value="选择上级部门"
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onClick="javaScript:chooseOrg()">
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						地市
						<font color="red">*</font>:
					</td>
					<td class="qinggoudan_table_td1">
						<input name="selectAll" name="selectAll"
							class="qinggoudan_input011" type="checkbox"
							onclick="chooseAll('city')">
						&nbsp;全选
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td2" colspan="2">

						&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="nj" class="qinggoudan_input011" type="checkbox"
							value="nj">
						&nbsp;南京 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="wx" class="qinggoudan_input011" type="checkbox"
							value="wx">
						&nbsp;无锡 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="xz" class="qinggoudan_input011" type="checkbox"
							value="xz">
						&nbsp;徐州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="cz" class="qinggoudan_input011" type="checkbox"
							value="cz">
						&nbsp;常州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="sz" class="qinggoudan_input011" type="checkbox"
							value="sz">
						&nbsp;苏州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="nt" class="qinggoudan_input011" type="checkbox"
							value="nt">
						&nbsp;南通
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1" colspan="2" align="center">

						&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="lyg" class="qinggoudan_input011" type="checkbox"
							value="lyg">
						&nbsp;连云港 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="ha" class="qinggoudan_input011" type="checkbox"
							value="ha">
						&nbsp;淮安 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="yc" class="qinggoudan_input011" type="checkbox"
							value="yc">
						&nbsp;盐城 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="yz" class="qinggoudan_input011" type="checkbox"
							value="yz">
						&nbsp;扬州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="zj" class="qinggoudan_input011" type="checkbox"
							value="zj">
						&nbsp;镇江 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="tz" class="qinggoudan_input011" type="checkbox"
							value="tz">
						&nbsp;泰州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="sq" class="qinggoudan_input011" type="checkbox"
							value="sq">
						&nbsp;宿迁


					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						备注:
					</td>
					<td class="qinggoudan_table_td1">
						<textarea rows="5" cols="20" name="description"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="8" align="center" class="qinggoudan_table_td1">
						&nbsp;
						<input name="saveBtn" type="button" class="anniu_out"
							value=" 确 定 " onclick="saveOrg()"
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'" disabled>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 "
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath}/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=edit')">
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
	//选择上级部门
	//打开一个窗口 展示部门信息
	function chooseOrg()
	{
		window.open("${contextPath}/mss/jsp/OrgnizationOperation.do?method=selectOrg","","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=670, height=450");
	}
	
		function chooseAll(checkName)
			{
				var checkNameArray=document.getElementsByName(checkName);
				if(document.getElementsByName("selectAll")[0].checked == true)
				{

						document.getElementsByName("nj")[0].checked = true;
						document.getElementsByName("wx")[0].checked = true;
						document.getElementsByName("xz")[0].checked = true;
						document.getElementsByName("cz")[0].checked = true;
						document.getElementsByName("sz")[0].checked = true;
						document.getElementsByName("nt")[0].checked = true;
						document.getElementsByName("lyg")[0].checked = true;
						document.getElementsByName("ha")[0].checked = true;
						document.getElementsByName("yc")[0].checked = true;
						document.getElementsByName("yz")[0].checked = true;
						document.getElementsByName("zj")[0].checked = true;
						document.getElementsByName("tz")[0].checked = true;
						document.getElementsByName("sq")[0].checked = true;
					
				}	
				else
				{
						document.getElementsByName("nj")[0].checked = false;
						document.getElementsByName("wx")[0].checked = false;
						document.getElementsByName("xz")[0].checked = false;
						document.getElementsByName("cz")[0].checked = false;
						document.getElementsByName("sz")[0].checked = false;
						document.getElementsByName("nt")[0].checked = false;
						document.getElementsByName("lyg")[0].checked = false;
						document.getElementsByName("ha")[0].checked = false;
						document.getElementsByName("yc")[0].checked = false;
						document.getElementsByName("yz")[0].checked = false;
						document.getElementsByName("zj")[0].checked = false;
						document.getElementsByName("tz")[0].checked = false;
						document.getElementsByName("sq")[0].checked = false;
				}
			}
			
			
	function saveOrg(){
		checkAllButton(true);
		var passed = "0";
		var orgName = document.getElementsByName("orgName")[0].value;
		
		var description = document.getElementsByName("description")[0].value;
	
		if(orgName == ""){
			alert("请输入部门名称！");
			document.getElementsByName("orgName")[0].focus();
			passed = "1";
			checkAllButton(false);
			return;
		}
		
		if(description.length>100)
		{
			alert("备注过长!");
			document.getElementsByName("description")[0].focus();
			passed = "1";
			checkAllButton(false);
		}
		
		
		if(document.getElementsByName("nj")[0].checked == false &&
						document.getElementsByName("wx")[0].checked == false &&
						document.getElementsByName("xz")[0].checked == false &&
						document.getElementsByName("cz")[0].checked == false &&
						document.getElementsByName("sz")[0].checked == false &&
						document.getElementsByName("nt")[0].checked == false &&
						document.getElementsByName("lyg")[0].checked == false &&
						document.getElementsByName("ha")[0].checked == false &&
						document.getElementsByName("yc")[0].checked == false &&
						document.getElementsByName("yz")[0].checked == false &&
						document.getElementsByName("zj")[0].checked == false &&
						document.getElementsByName("tz")[0].checked == false &&
						document.getElementsByName("sq")[0].checked == false)
		{
			alert("请选择地市！");
			passed = "1";
			checkAllButton(false);
		}
		
		
		
		if(passed == "0" && confirm("您确定要添加部门机构信息吗？")){
			document.getElementsByName("saveBtn")[0].disabled = true;
			document.orgnizationForm.submit();
		}else{
			checkAllButton(false);
		}
	}
	
	var Global_XMLHttpReq;
	//检查厂家名称是否唯一
	function checkOrgNameExist(){
		document.getElementById("saveBtn").disabled = true;
		document.getElementById("checkState").innerText="校验中...";
		var orgName = document.getElementById("orgName").value;
		
		if(Trim(orgName)=="")
		{
			document.getElementById("checkState").innerText="请输入部门名称";    
	        return;  
		}
		
		if(!/^[\s\w\u4e00-\u9fa5]{1,20}$/.test(orgName))     
	    {    
	       document.getElementById("checkState").innerText="部门名称只能由数字、字母、汉字、下划线组合而成";    
	        return;    
	    } 
		
		Global_XMLHttpReq =  getXMLHttpRequest();
		
		Global_XMLHttpReq.onreadystatechange = process;
		var url="${contextPath}/mss/jsp/OrgnizationOperation.do?method=checkOrgNameExist&orgName="+encodeURI(orgName);
		Global_XMLHttpReq.open("GET",url,true);
		Global_XMLHttpReq.send(null);
	}
	
	function Trim(str) {
return str.replace(/\s+$|^\s+/g,"");
}

	function process()
	{
		if(Global_XMLHttpReq.readyState == 4)
		{
			if(Global_XMLHttpReq.status == 200)
			{
				if(Global_XMLHttpReq.responseText == "true")
				{
					document.getElementsByName("saveBtn")[0].disabled = true;
					document.getElementById("checkState").innerText="该名称已经存在，请重新输入！";
					document.getElementById("orgName").focus();
				}
				else if(Global_XMLHttpReq.responseText == "false")
				{
					document.getElementById("checkState").innerText="该名称可以使用！";
					document.getElementsByName("saveBtn")[0].disabled = false;
				}
			}
		}
	}
	
	function getXMLHttpRequest() {
	
		var Global_XMLHttpReq = false;
		if (navigator.userAgent.indexOf("Opera")>=0)
		{
		    alert("This program doesn't work in Opera") ;
		    return null;
		} else if (navigator.userAgent.indexOf("MSIE")>=0)
	    {
		    var strName="Msxml2.XMLHTTP";
		    if (navigator.appVersion.indexOf("MSIE 5.5")>=0)
		      strName="Microsoft.XMLHTTP";
		    try
		    {
		      Global_XMLHttpReq=new ActiveXObject(strName);
		    }
		    catch(e)
		    {
		      alert("Error. Scripting for ActiveX might be disabled");
		      return null;
		    }
		} else if (navigator.userAgent.indexOf("Mozilla")>=0){
			Global_XMLHttpReq = new XMLHttpRequest();
		}
		return Global_XMLHttpReq
}


</script>

</html>

