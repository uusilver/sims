<%@page contentType="text/html; charset=utf-8"%>
<jsp:directive.page import="java.util.Iterator" />
<%@page import="com.xwtech.mss.pub.constants.SpmsConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>
<%@page language="java" import="com.xwtech.mss.pub.po.Orgnization"%>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>业务运营分析系统</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
	</head>

	<%!public void getLowOrgnization(JspWriter out, Orgnization orgnization, int i)//第一次i=1
	{
		//System.out.println("i="+i);
		try
		{
			if (orgnization.getOrgnizations().size() > 0)
			{
				Iterator it = orgnization.getOrgnizations().iterator();
				while (it.hasNext())
				{
					Orgnization lowOrgnization = (Orgnization) it.next();
					String str = "";
					for (int j = 1; j < i; j++)
					{
						str += "&nbsp;&nbsp; | ";
					}
					out.println(str + lowOrgnization.getOrgName() + "<br>");
					if (lowOrgnization.getOrgnizations().size() > 1)
					{
						getLowOrgnization(out, lowOrgnization, i + 1);
					}
				}

			}
		}
		catch (Exception e)
		{
		}
	}%>


	<body>
		<form name="orgnizationForm" method="post"
			action="${contextPath}/mss/jsp/OrgnizationOperation.do?method=orgnizationUpdate">

			<c:forEach var="userInfo" items="${mapResult.orgnization.userInfos}">
				<c:if test="${userInfo.role.roleId==1}">
					<!-- 如果包含系统管理员 不能将状态设为无效 -->
					<c:set value="f1" scope="request" var="canUseless1"></c:set>
				</c:if>
			</c:forEach>
			<c:if test="${fn:length(mapResult.orgnization.orgnizations)>0}">
				<!-- 如果存在下级部门 不能将状态设为无效 -->
				<c:set value="f2" scope="request" var="canUseless2"></c:set>
			</c:if>


			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="qinggoudan_table">
				<tr>
					<td class="qinggoudan_title01_td1">
						<c:if test="${mapResult.flag=='view' }">
						查看部门机构信息
					</c:if>
						<c:if test="${mapResult.flag=='edit' }">
						编辑增部门机构信息
					</c:if>

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
							onchange="checkOrgNameExist()"
							value="${mapResult.orgnization.orgName }"
							<c:if test="${mapResult.flag=='view' }">
								readonly="readonly"
							</c:if>>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="checkState" id="checkState" type="text" width="100%"
							class="qinggoudan_input02" maxlength="100" size="100"
							style="border-style:none;width:80%">
						<input type="hidden" name="orgId" id="orgId"
							value="${mapResult.orgnization.orgId }">
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						状态:
					</td>
					<td class="qinggoudan_table_td1">
						<select name="state" disabled="disabled">
							<option value="<%=SpmsConstants.STATE_A%>"
								<c:if test="${mapResult.orgnization.state=='A'}">selected="selected"</c:if>>
								有效
							</option>
							<c:if test="${canUseless1!='f1' && canUseless2!='f2'}">
								<option value="<%=SpmsConstants.STATE_U%>"
									<c:if test="${mapResult.orgnization.state=='U'}">selected="selected"</c:if>>
									无效
								</option>
							</c:if>
						</select>
						<c:if test="${mapResult.flag!='view' }">
							<c:if test="${canUseless1=='f1' || canUseless2=='f2'}">
								<font color="red">不能刪除：</font>
							</c:if>
							<c:if test="${canUseless1=='f1'}">
								<font color="red">该部门存在系统管理员 </font>
							</c:if>
							<c:if test="${canUseless2=='f2'}">
								<font color="red">该部门存在下级部门 </font>
							</c:if>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						上级部门:
					</td>
					<td class="qinggoudan_table_td1">
						<input name="upOrgName" id="upOrgName" " type="text"
							class="qinggoudan_input02" readonly
							value="${mapResult.orgnization.orgnization.orgName}" />
						<input name="upOrgId" id="upOrgId" " type="hidden"
							value="${mapResult.orgnization.orgnization.orgId}" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value="选择上级部门"
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onClick="javaScript:chooseOrg()"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>>
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						下级部门:
					</td>
					<td class="qinggoudan_table_td1">
						<%
						getLowOrgnization(out, (Orgnization) ((java.util.HashMap) request.getAttribute("mapResult")).get("orgnization"), 1);
						%>
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
							onclick="chooseAll('city')"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>>
						&nbsp;全选
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td2" colspan="2">
						${mapReslut.orgnization.frameOrgCityRelations}
						<c:forEach var="relations"
							items="${mapResult.orgnization.frameOrgCityRelations}">
							<!-- 得到部门地市关系 -->
							<c:choose>
								<c:when test="${relations.cityId=='nj'}">
									<c:set var="nj" value="nj" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='wx'}">
									<c:set var="wx" value="wx" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='xz'}">
									<c:set var="xz" value="xz" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='cz'}">
									<c:set var="cz" value="cz" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='sz'}">
									<c:set var="sz" value="sz" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='nt'}">
									<c:set var="nt" value="nt" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='lyg'}">
									<c:set var="lyg" value="lyg" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='ha'}">
									<c:set var="ha" value="ha" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='yc'}">
									<c:set var="yc" value="yc" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='yz'}">
									<c:set var="yz" value="yz" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='zj'}">
									<c:set var="zj" value="zj" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='tz'}">
									<c:set var="tz" value="tz" scope="page" />
								</c:when>
								<c:when test="${relations.cityId=='sq'}">
									<c:set var="sq" value="sq" scope="page" />
								</c:when>

							</c:choose>

						</c:forEach>



						&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="nj" class="qinggoudan_input011" type="checkbox"
							value="nj"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${nj=='nj' }">
								checked="checked"
							</c:if>>
						&nbsp;南京 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="wx" class="qinggoudan_input011" type="checkbox"
							value="wx"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${wx=='wx' }">
								checked="checked"
							</c:if>>
						&nbsp;无锡 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="xz" class="qinggoudan_input011" type="checkbox"
							value="xz"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${xz=='xz' }">
								checked="checked"
							</c:if>>
						&nbsp;徐州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="cz" class="qinggoudan_input011" type="checkbox"
							value="cz"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${cz=='cz' }">
								checked="checked"
							</c:if>>
						&nbsp;常州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="sz" class="qinggoudan_input011" type="checkbox"
							value="sz"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${sz=='sz' }">
								checked="checked"
							</c:if>>
						&nbsp;苏州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="nt" class="qinggoudan_input011" type="checkbox"
							value="nt"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${nt=='nt' }">
								checked="checked"
							</c:if>>
						&nbsp;南通
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1" colspan="2" align="center">

						&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="lyg" class="qinggoudan_input011" type="checkbox"
							value="lyg"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${lyg=='lyg' }">
								checked="checked"
							</c:if>>
						&nbsp;连云港 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="ha" class="qinggoudan_input011" type="checkbox"
							value="ha"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${ha=='ha' }">
								checked="checked"
							</c:if>>
						&nbsp;淮安 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="yc" class="qinggoudan_input011" type="checkbox"
							value="yc"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${yc=='yc' }">
								checked="checked"
							</c:if>>
						&nbsp;盐城 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="yz" class="qinggoudan_input011" type="checkbox"
							value="yz"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${yz=='yz' }">
								checked="checked"
							</c:if>>
						&nbsp;扬州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="zj" class="qinggoudan_input011" type="checkbox"
							value="zj"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${zj=='zj' }">
								checked="checked"
							</c:if>>
						&nbsp;镇江 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="tz" class="qinggoudan_input011" type="checkbox"
							value="tz"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${tz=='tz' }">
								checked="checked"
							</c:if>>
						&nbsp;泰州 &nbsp;&nbsp;&nbsp;&nbsp;
						<input name="sq" class="qinggoudan_input011" type="checkbox"
							value="sq"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>
							<c:if test="${sq=='sq' }">
								checked="checked"
							</c:if>>
						&nbsp;宿迁


					</td>
				</tr>
				<tr>
					<td class="qinggoudan_table_td1">
						备注:
					</td>
					<td class="qinggoudan_table_td1">
						<textarea rows="5" cols="20" name="description"
							<c:if test="${mapResult.flag=='view' }">
								disabled="disabled"
							</c:if>>${mapResult.orgnization.description}</textarea>
					</td>
				</tr>
				<tr>

					<td colspan="8" align="center" class="qinggoudan_table_td1">
						&nbsp;
						<c:if test="${mapResult.flag=='edit' }">
							<input name="saveBtn" type="button" class="anniu_out"
								value=" 修 改 " onclick="updateOrg()"
								onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</c:if>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 "
							onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath}/mss/jsp/OrgnizationOperation.do?method=enterInterface&flag=${mapResult.flag}')">
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
		window.open("${contextPath}/mss/jsp/OrgnizationOperation.do?method=selectOrg&flag=update","","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=670, height=450");
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
			
			
	function updateOrg(){
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
		
		
		
		if(passed == "0" && confirm("您确定要修改部门机构信息吗？")){
			document.getElementsByName("saveBtn")[0].disabled = true;
			document.orgnizationForm.submit();
		}else{
			checkAllButton(false);
		}
	}
	
	var Global_XMLHttpReq;
	//检查厂家名称是否唯一
	function checkOrgNameExist(){
	
	var orgName = document.getElementsByName("orgName")[0].value;
	
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
	    
	if(orgName == "")
	{
					document.getElementsByName("saveBtn")[0].disabled = true;
					document.getElementById("checkState").innerText="请输入部门名称！";
					document.getElementById("orgName").focus();
					return;
	}
	
		document.getElementById("saveBtn").disabled = true;
		document.getElementById("checkState").innerText="校验中...";
		Global_XMLHttpReq =  getXMLHttpRequest();
		var orgName = document.getElementById("orgName").value;
		var orgId = document.getElementById("orgId").value;
		Global_XMLHttpReq.onreadystatechange = process;
		var url="${contextPath}/mss/jsp/OrgnizationOperation.do?method=checkOrgNameExist&orgName="+encodeURI(orgName)+"&orgId="+encodeURI(orgId);
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

