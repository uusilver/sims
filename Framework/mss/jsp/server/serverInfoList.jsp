<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.tmind.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>服务器信息管理</title>
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		<script type="text/javascript">
		document.onkeydown = keyDown;
		function keyDown(){
			var ieKey= window.event.keyCode;
			if(ieKey == 13){
				query();
			}
		}
		
		function query(){
			document.serverListForm.action = "${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoList"
				+ constructParams("queryServerType,queryCountryId,queryProvinceId,queryCityId,queryServerGroup,queryServerStatus,currentPage,viewOrEdit,returnForm,indexNO");
			document.serverListForm.submit();
		}
		
		
	</script>
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
				if(pId!=""&&pId!=null){
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
				if(cId!=""&&cId!=null){
					$(".city").val(cId);
				}
			},'json');  
			
		}
		
	 	$(function(){
			var cid= ${information.searchForm.queryCountryId};
			var pid= ${information.searchForm.queryProvinceId};
			var tid= ${information.searchForm.queryCityId};
			
			//初始化国家下拉框  
			var url="${contextPath}/mss/html/locationController.do?method=queryAllCountries";
			$.post(url,null,function(json){
				$(".country").find("option").remove();
				$(".country").append("<option value=''>---请选择国家---</option>");
				//alert(json);
			 	for(var i in json){  
					//添加一个国家  
					$(".country").append("<option value='"+json[i].countryid+"'>"+json[i].countryname+"</option>");  
			 	}
				//用于编辑页面选项回填
				if(cid!=""&&cid!=null){
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
	</head>

	<body>

		<form name="serverListForm" method="post" action="${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoList">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						服务器信息查询
					</td>
				</tr>
				<tr>
					<td class="qinggoudan_title01_td2" style="text-align:center;">
						<hr size="1" noshade>
						
					</td>
				</tr>
			</table>
			<c:if test="${information.searchForm.showHeader=='yes' }">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table" style="margin:0px;">
					<tr>
						<td class="qinggoudan_table_td1">
							服务类型:
							<pub:link sql="<%=MssConstants.QUERY_SERVER_TYPE_SQL%>" num="1" selectSize="15"
								title="---服务器类型---" next="false" name="queryServerType" mvalue="${information.searchForm.queryServerType}" />
						</td>
						<td class="qinggoudan_table_td1">
							服务器分组:
							<pub:link sql="<%=MssConstants.QUERY_NO_SERVE_GROUP_INFO_SQL+MssConstants.SQL_UNION+MssConstants.QUERY_SERVE_GROUP_INFO_SQL%>" num="1" title="---请选择---"
								next="false" name="queryServerGroup" mvalue="${information.searchForm.queryServerGroup}" />
						</td>
						<td class="qinggoudan_table_td1">
							服务器状态:
							<pub:link sql="<%=MssConstants.QUERY_SERVER_STATUS_SQL%>" num="1" title="---请选择---" next="false" 
							name="queryServerStatus" mvalue="${information.searchForm.queryServerStatus}" />
						</td>
						<td class="qinggoudan_table_td1">
						</td>
						</tr>
						<tr>
						<td class="qinggoudan_table_td1">
							所属国家:
							<select class="country" id="countryId" name="queryCountryId">
							<option value="">---请选择国家---</option>
							</select>
						</td>
						<td class="qinggoudan_table_td1">
							所属省（州）:
							<select class="province" id="provinceId" name="queryProvinceId">
							<option value="">---请选择省（州）---</option>
							</select>
							
						</td>
						<td class="qinggoudan_table_td1">
							所属城市:
							<select class="city" id="cityId" name="queryCityId">
							<option value="">---请选择城市---</option>
							</select>
						</td>
						<td class="qinggoudan_table_td1">
							&nbsp;
							<input type="button" class="anniu_out" value=" 搜 索 " onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'" onclick="query()">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="resetQuery('serverListForm')" class="anniu_out" value=" 重 填 "
								onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</table>
			</c:if>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td width="50" class="qinggoudan_table_title">
						<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
							<input type="checkbox" class="qinggoudan_input011" name="serverChk"
								onclick="javaScript:checkAll('serverChk','true');" />全选
				 </c:if>
						<c:if test="${information.searchForm.viewOrEdit!='edit'}">
							<c:if test="${information.searchForm.indexNO!=null && information.searchForm.indexNO!=''}">选择</c:if>
							<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">序号</c:if>
						</c:if>
					</td>
					<td class="qinggoudan_table_title">
						服务器类型
					</td>
					<td class="qinggoudan_table_title">
						IP地址
					</td>
					<td class="qinggoudan_table_title">
						所属组名
					</td>
					<td class="qinggoudan_table_title">
						服务地域
					</td>
					<td class="qinggoudan_table_title">
						所属国家
					</td>
					<td class="qinggoudan_table_title">
						所属省（州）
					</td>
					<td class="qinggoudan_table_title">
						所属城市
					</td>
					<td class="qinggoudan_table_title">
						服务器状态
					</td>
					<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
						<td width="50" class="qinggoudan_table_title">
							详情
						</td>
					</c:if>
				</tr>
				<c:forEach var="serverInfo" items="${information.serverInfoList}" varStatus="status">
					<tr <c:if test="${(status.index+1)%2==0}"> bgcolor="#F0FFF0"</c:if>>
						<td class="qinggoudan_table_td2">
							<c:if test="${information.searchForm.viewOrEdit!=null&&information.searchForm.viewOrEdit=='edit'}">
								<input type="checkbox" class="qinggoudan_input011" name="serverChk" value="${serverInfo.serverid}"/>
							</c:if>
							<c:if test="${information.searchForm.viewOrEdit!='edit'}">
								<c:if test="${information.searchForm.indexNO==null || information.searchForm.indexNO==''}">
									${status.index+1}
								</c:if>
							</c:if>
						</td>
						<td class="qinggoudan_table_td2" width="10%" >
							&nbsp;${fn:escapeXml(serverInfo.serverType)}
						</td>
						<td class="qinggoudan_table_td2" width="12%" >
							&nbsp;${fn:escapeXml(serverInfo.serverip)}
						</td>
						<td class="qinggoudan_table_td2" width="12%" >
							&nbsp;${(serverInfo.servergroupname==null||serverInfo.servergroupname=="")?"未分组":serverInfo.servergroupname}
						</td>
						<td class="qinggoudan_table_td2" width="12%" >
							&nbsp;${fn:escapeXml(serverInfo.regionname)}
						</td>
						<td class="qinggoudan_table_td2" width="10%" >
							&nbsp;${fn:escapeXml(serverInfo.countryname)}
						</td>
						<td class="qinggoudan_table_td2" width="10%" >
							&nbsp;${fn:escapeXml(serverInfo.provincename)}
						</td>
						<td class="qinggoudan_table_td2" width="10%" >
							&nbsp;${fn:escapeXml(serverInfo.cityname)}
						</td>
						<td class="qinggoudan_table_td2">
							&nbsp;${fn:escapeXml(serverInfo.serverStatus)}
						</td>
						<td class="qinggoudan_table_td2">
							<a href="javascript:viewServer('${serverInfo.serverid}')"> <img src="${contextPath}/mss/image/see.gif" width="18" height="20"
									border="0"> </a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<pub:page formName="serverListForm" currentPage="${information.currentPage}" totalCount="${information.totalCount}"
				totalPage="${information.totalPage}" />
			<c:if test="${information.searchForm.showHeader=='yes' }">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center">
							<input type="button" class="anniu_out" value=" 新 增  " onclick="addServer()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="anniu_out" value=" 删 除  " onclick="delServer()" onMouseOver="className='anniu_over'"
								onMouseOut="className='anniu_out'">
						</td>
					</tr>
				</table>
			</c:if>

			<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
			<input type="hidden" name="indexNO" value="${information.searchForm.indexNO}" />
			<input type="hidden" name="showHeader" value="${information.searchForm.showHeader}" />
			<input type="hidden" name="queryServerGroup" value="${information.searchForm.queryServerGroup}" />
			

		</form>

	</body>
</html>

<script type="text/javascript">
	function addServer(){
		window.location = "${contextPath}/mss/jsp/server/serverInfoAdd.jsp?" + constructParams('queryServerType,queryCountryId,queryProvinceId,queryCityId,queryServerGroup,queryServerStatus,currentPage,viewOrEdit,showHeader');
	}
		
	function viewServer(serverId){
		window.location = "${contextPath}/mss/jsp/server/serverInfoController.do?method=queryServerInfoById&serverId=" + serverId 
			+ constructParams('queryServerType,queryCountryId,queryProvinceId,queryCityId,queryServerGroup,queryServerStatus,currentPage,viewOrEdit,showHeader');
	}
		
	function delServer(){
		var serverChk = document.getElementsByName("serverChk");
		var serverIdStr = "";
		for(var i=1;i<serverChk.length; i++){
			if(serverChk[i].checked == true){
				serverIdStr += serverChk[i].value + ",";
			}
		}
		if(serverIdStr==""){
			alert("请选择要删除的服务器！");
			return;
		}else{
			window.confirm("您确定要删除选中的服务器信息吗？","OK()","NO()");
		}
	}
	
	function OK(){
		var serverChk = document.getElementsByName("serverChk");
		var serverIdStr = "";
		for(var i=1;i<serverChk.length; i++){
			if(serverChk[i].checked == true){
				serverIdStr += serverChk[i].value + ",";
			}
		}
		window.location = "${contextPath}/mss/jsp/server/serverInfoController.do?method=delServerInfo&serverIdStr=" + serverIdStr 
		+ constructParams('queryServerType,queryCountryId,queryProvinceId,queryCityId,queryServerGroup,queryServerStatus,currentPage,viewOrEdit');
	}
	
	function Cancel(){
		return false;
	}
</script>
