<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-服务器信息管理</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		
		<script type="text/javascript">  

		function changeProvince(countryId,pId){
			var url="${contextPath}/mss/html/locationController.do?method=queryProvinceByCountryId";
	    	$.post(url,{"countryId":countryId},function(json){
	    		//清空省（州）下拉框 
				$(".province").find("option").remove();
				$(".province").append("<option value=''>---请选择省（州）---</option>");
				for(var i in json){  
				//添加一个省（州）
					$(".province").append("<option value='"+json[i].provinceid+"'>"+json[i].provincename+"</option>");  
				}
				//用于编辑页面选项回填
				var flag = pId+"".substr(0,1);
				if(pId!="" && pId!=null && flag!="-"){
					$(".province").val(pId);
				}
			},'json');
	    	
			//注册省（州）下拉框事件
			$(".province").change(function(){  
				changeCity($(this).val(),"");  
			}); 
		}
	 	
	 	function changeCity(provinceId,cId){
			var url="${contextPath}/mss/html/locationController.do?method=queryCityByProvinceId";
	    	$.post(url,{"provinceId":provinceId},function(json){
	    		//清空城市下拉框 
				$(".city").find("option").remove();
				$(".city").append("<option value=''>---请选择城市---</option>");
				//alert(json);
				for(var i in json){  
				//添加一个城市
					$(".city").append("<option value='"+json[i].cityid+"'>"+json[i].cityname+"</option>");  
				}
				//用于编辑页面选项回填
				var flag = cId+"".substr(0,1);
				if(cId!="" && cId!=null && flag!="-"){
					$(".city").val(cId);
				}
			},'json');  
			
		}
		
	 	$(function(){
			var editFlag = $("input[name='viewOrEdit']").val();
			var cid=${information.transitServer.countryid==null?"-10":information.transitServer.countryid };
			var pid=${information.transitServer.provinceid==null?"-20":information.transitServer.provinceid };
			var tid=${information.transitServer.cityid==null?"-30":information.transitServer.cityid };
			
			//初始化国家下拉框  
			var url="${contextPath}/mss/html/locationController.do?method=queryAllCountries";
			$.post(url,null,function(json){
				$(".country").find("option").remove();
				$(".country").append("<option value=''>---请选择国家---</option>");
			 	for(var i in json){  
					//添加一个国家  
					$(".country").append("<option value='"+json[i].countryid+"'>"+json[i].countryname+"</option>");  
			 	}
				//用于编辑页面选项回填
				if(editFlag=="edit"){
					//选中指定国家
					$(".country").val(cid);
					//选中指定省
					changeProvince(cid,pid);
					//选中指定市
					changeCity(pid,tid);
				}
		 	},'json');
			
			//注册国家下拉框事件
			$(".country").change(function(){  
				changeProvince($(this).val(),"");  
			}); 
		});
	</script>
		
		
		<script type="text/javascript">

		function saveServerInfo(){
			if(checkServerIP()&&checkCountry()&&checkProvince()&&checkCity()&&checkServerType()&&checkServerStatus()
				&&checkInvalidTime()&&checkServerLimit()&&checkServeRegion()&&checkServerComment()){
				if(confirm("您确定要保存该服务器信息么？")){
					document.serverInfoAddForm.submit();
				}
			}else{
				alert("请根据提示修改相应内容！");
			}
		}
		
		
		function checkServerIP() {
			//校验IP地址格式
			var condition = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/; 
			var serverIP = document.getElementById("server_IP");
			
			if (serverIP.value == "") {
				var infoDiv = document.getElementById("server_IpDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器IP地址不能为空，请填写！";
		        serverIP.focus();
				return false;
			}
			
			if(!condition.test(trim(serverIP.value))) {
				var infoDiv = document.getElementById("server_IpDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器IP地址格式不正确，请修改！例如：192.54.6.20";
		        serverIP.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_IpDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器IP地址符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkCountry() {
			
			var country = document.getElementById("countryId");
			
			if (country.value == "") {
				var infoDiv = document.getElementById("server_countryDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器所在国家不能为空，请选择！";
		        country.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_countryDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器所在国家符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkProvince() {
			
			var province = document.getElementById("provinceId");
			
			if (province.value == "") {
				var infoDiv = document.getElementById("server_provinceDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器所在省（州）不能为空，请选择！";
		        province.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_provinceDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器所在省（州）符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkCity() {
			
			var city = document.getElementById("cityId");
			
			if (city.value == "") {
				var infoDiv = document.getElementById("server_cityDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器所在城市不能为空，请选择！";
		        city.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_cityDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器所在城市符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkServerType() {
			
			var serverType = document.getElementsByName("serverType")[0];
			
			if (serverType.value == "") {
				var infoDiv = document.getElementById("server_typeDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器类型不能为空，请选择！";
		        serverType.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_typeDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器类型符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkServerStatus() {
			
			var serverStatus = document.getElementsByName("serverStatus")[0];
			
			if (serverStatus.value == "") {
				var infoDiv = document.getElementById("server_statusDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器状态不能为空，请选择！";
		        serverStatus.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_statusDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器状态符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		function checkInvalidTime() {
			
			var invalidTime = document.getElementsByName("invalidTime")[0];
			
			if (invalidTime.value == "") {
				var infoDiv = document.getElementById("invalid_timeDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器失效日期不能为空，请选择！";
		        invalidTime.focus();
				return false;
			}
			var invalidTime_C = Date.parse(invalidTime.value.replace(/\-/g,"/"));
			var currentDateTime_C = Date.parse(getCurrentDateTime().replace(/\-/g,"/"));
			
			if (invalidTime_C<currentDateTime_C) {
				var infoDiv = document.getElementById("invalid_timeDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器失效日期不能小于或等于当前日期，请修改！";
		        invalidTime.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("invalid_timeDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器失效日期合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		function checkServerLimit() {
			
			var serverLimit = document.getElementsByName("limit")[0];
			
			if (serverLimit.value == "") {
				var infoDiv = document.getElementById("limitDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器约束不能为空，请选择！";
		        serverLimit.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("limitDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器约束信息符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		function checkServeRegion() {
			
			var serveRegion = document.getElementsByName("serveRegion")[0];
			
			if (serveRegion.value == "") {
				var infoDiv = document.getElementById("server_regionDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "服务器服务地域不能为空，请选择！";
		        serveRegion.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_regionDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "服务器服务地域符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		//校验备注
		function checkServerComment() {
			var comment = document.getElementById("server_comment");
			
			if(comment.value==""){
				return true;
			}
			
			if(comment.value.length>100)
			{
				var infoDiv = document.getElementById("server_commDiv");
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "备注不能超过100个字符，请修改！";
		        comment.focus();
				return false;
			}
			
			var infoDiv = document.getElementById("server_commDiv");
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		</script>
	 
	</head>
	<body>
		<form name="serverInfoAddForm" method="post" action="${contextPath}/mss/jsp/server/serverInfoController.do?method=saveServerInfo">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新服务器档案
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器IP
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="serverIP" id="server_IP" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.transitServer.serverip}"
							onchange="checkServerIP()">
						<input type="hidden" name="serverId" id="server_ID" value="${information.transitServer.serverid }" />
						<span id="server_IpDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属国家
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select class="country" id="countryId" name="country_id">
						<option value="">---请选择国家---</option>
						</select>
						<span id="server_countryDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属省/州
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select class="province" id="provinceId" name="province_id">
						<option value="">---请选择省（州）---</option>
						</select>
						<span id="server_provinceDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属城市
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select class="city" id="cityId" name="city_id">
						<option value="">---请选择城市---</option>
						</select>
						<span id="server_cityDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器类型
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:link sql="<%=MssConstants.QUERY_SERVER_TYPE_SQL%>" num="1" selectSize="20"
							title="---请选择服务器类型---" next="false" name="serverType" mvalue="${information.transitServer.servertype}" />
					<span id="server_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器状态
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
					<pub:link sql="<%=MssConstants.QUERY_SERVER_STATUS_SQL%>" num="1" selectSize="20"
							title="请选择服务器状态" next="false" name="serverStatus" mvalue="${information.transitServer.serverstatus}" />
					<span id="server_statusDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						失效日期
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:dtp format="yyyy-MM-dd HH:mm:ss" name="invalidTime" size="10" styleClass="qinggoudan_input023" 
						value="${information.transitServer.invalidtime}"/>
					<span id="invalid_timeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						约束
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
					<pub:link sql="<%=MssConstants.QUERY_SERVER_LIMIT_SQL%>" num="1" selectSize="20"
							title="---请选择服务器约束---" next="false" name="limit" mvalue="${information.transitServer.limitation}" />
					<span id="limitDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务地域
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
					<pub:link sql="<%=MssConstants.QUERY_SERVE_REGION_INFO_SQL%>" num="1" selectSize="20"
							title="---请选择地域信息---" next="false" name="serveRegion" mvalue="${information.transitServer.regionid}" />
					<span id="server_regionDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器所属分组
					</td>
					<td align="left" class="qinggoudan_table_td1">
					<pub:link sql="<%=MssConstants.QUERY_SERVE_GROUP_INFO_SQL%>" num="1" selectSize="20"
							title="---请选择服务器分组---" next="false" name="serveGroup" mvalue="${information.serverGroupId}" />
					<span id="server_groupDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="serverComment" id="server_comment" rows="10" cols="30" onchange="checkServerComment()">${information.transitServer.note}</textarea>
					<span id="server_commDiv"></span>
					</td>
				</tr>

			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<c:if test="${information.searchForm.showHeader==null || information.searchForm.showHeader=='yes' }">
							&nbsp;
							<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveServerInfo()"
								onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/server/serverInfoController.do?method=queryServerInfoList','viewOrEdit,showHeader,currentPage,queryCountryId,queryProvinceId,queryCityId,queryServerType,queryServerStatus,queryServerGroup')">
					</td>
				</tr>
			</table>
				<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}"/>
				<input type="hidden" name="currentPage" value="${information.searchForm.currentPage}" />
				<input type="hidden" name="queryCountryId" value="${information.searchForm.queryCountryId}" />
				<input type="hidden" name="queryProvinceId" value="${information.searchForm.queryProvinceId}" />
				<input type="hidden" name="queryCityId" value="${information.searchForm.queryCityId}" />
				<input type="hidden" name="queryServerType" value="${information.searchForm.queryServerType}" />
				<input type="hidden" name="queryServerStatus" value="${information.searchForm.queryServerStatus}" />
				<input type="hidden" name="queryServerGroup" value="${information.searchForm.queryServerGroup}" />
				<input type="hidden" name="showHeader" value="${information.searchForm.showHeader}" />
				
		</form>
	</body>

</html>
