<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.xwtech.mss.pub.constants.MssConstants" %>
<%@include file="/framework/jsp/taglibs.jsp"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物资管理系统-客户端信息创建</title>
		<link href="${contextPath }/framework/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${contextPath}/mss/js/tools.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/ajax.js"></script>
		<script type="text/javascript" src="${contextPath}/mss/js/jquery-1.9.1.min.js"></script> 
		<script type="text/javascript">
		// 初始化页面，将所属客户端信息加入"已分配客户端"下拉框中
		$(function(){
			var cgMappingJsonObj = $.parseJSON('${information.cgMappingResult}');
			var clientId;
			var clientName;
			var sel_dest = $("select[name=clientId]");
			for(var i=0;i<cgMappingJsonObj.length;i++){
					clientId = cgMappingJsonObj[i].clientId;
					clientName = cgMappingJsonObj[i].clientTag;
					sel_dest.append("<option value='"+clientId+"'>"+clientName+"</option>");
			}
			
			
			var csMappingJsonObj = $.parseJSON('${information.csMappingResult}');
			var serverId;
			var serverName;
			var sel_dest = $("select[name=serverId]");
			for(var i=0;i<csMappingJsonObj.length;i++){
					serverId = csMappingJsonObj[i].serverId;
					serverName = csMappingJsonObj[i].serverTag;
					sel_dest.append("<option value='"+serverId+"'>"+serverName+"</option>");
			}
		});
		
		</script>
		<script type="text/javascript">

		function saveClientGroup(){
			var viewOrEdit = $("input[name=viewOrEdit]").val();
			var alertMessage = "您确定要创建该客户端组么？";
			
			if(checkGroupName()&&checkComment()&&checkSelectedClient()){
				if(viewOrEdit!=null&&viewOrEdit=="edit"){
					alertMessage="您确定要修改该客户端组么？";
				}
				if(confirm(alertMessage)){
					var clientOptions = $("select[name=clientId]").find("option");
					var clientIds = "";
					for(var i=0;i < clientOptions.length; i++){
						clientIds += clientOptions[i].value + ",";
					}
					clientIds = clientIds.substring(0, clientIds.length - 1);
			
					$("input[name=hiddenClientIds]").attr("value",clientIds);


					var serverOptions = $("select[name=serverId]").find("option");
					var serverIds = "";
					for(var i=0;i < serverOptions.length; i++){
						serverIds += serverOptions[i].value + ",";
					}
					serverIds = serverIds.substring(0, serverIds.length - 1);
			
					$("input[name=hiddenServerIds]").attr("value",serverIds);
					
					document.clientGroupAddForm.submit();
				}
			}else{
				alert("请根据提示修改相应内容！");
			}
		}
		
		//校验
		function checkGroupName() {
			var groupName = document.getElementById("clientgroupname");
			
			var infoDiv = document.getElementById("clientgroupnameDiv");
			var editFlag = $("input[name='viewOrEdit']").val();
			
			if(groupName.value==""){
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "【客户端组名】不能为空，请修改！";
		        groupName.focus();
				return false;
			}
			
			if(groupName.value.length>20)
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "【客户端组名】不能超过20个字符，请修改！";
		        groupName.focus();
				return false;
			}
			
			if(groupName.value.indexOf("[")!=-1 || groupName.value.indexOf("]")!=-1 )
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "【客户端组名】不能包含字符'['或者']'，请修改！";
		        groupName.focus();
				return false;
			}

			if((editFlag==null||editFlag==''||editFlag!='edit')&&checkIsExist(groupName, '', 'client_group') == "false") {
		        groupName.focus();
				return false;
			}
			
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "符合要求";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		
		//校验备注
		function checkComment() {
			var comment = document.getElementById("client_comment");
			
			if(comment.value==""){
				return true;
			}

			var infoDiv = document.getElementById("client_commDiv");
			if(comment.value.length>100)
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "备注不能超过100个字符，请修改！";
		        comment.focus();
				return false;
			}

			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		function checkSelectedClient(){
			var clientList = $("select[name=clientId]").find("option");
			
			var infoDiv = document.getElementById("client_listDiv");
			
			if(clientList.length==0){
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "请选择至少一个客户端添加到右边列表框中！";
				return false;
			}

			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		</script>
	 
	</head>
	<body>
		<form name="clientGroupAddForm" method="post" action="${contextPath}/mss/jsp/client/clientGroupController.do?method=saveClientGroup">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist&stateColName=status" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新客户端分组
					</td>
				</tr>
			</table>

			<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						客户端组名
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="clientGroupName" id="clientgroupname" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.clientGroup.clientgroupname}"
							onchange="checkGroupName()">
						<input type="hidden" name="clientGroupId" id="client_group_id" value="${information.clientGroup.clientgroupid }" />
						<span id="clientgroupnameDiv"></span>
					</td>
				</tr>
				<tr height="100">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="clientComment" id="client_comment" rows="5" cols="30" onchange="checkComment()">${information.clientGroup.note}</textarea>
					<span id="client_commDiv"></span>
					</td>
				</tr>
				<tr>
					<td align="center" class="qinggoudan_table_title">
						客户端列表
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:two align="left" arl="${information.clientList }" selectItem="客户端列表" waitItem="已分配客户端列表" leftId="clientId"
							leftValue="clientTag" rightId="clientId" rightValue="clientTag" rightName="clientId" selectStyle="two_select"
							itemStyle="item_two_select" moveStyle="button_select" size="15" width="80%" contextPath="${contextPath}" />

						<input type="hidden" name="hiddenClientIds" value="">
						<span id="client_listDiv"></span>
					</td>
				</tr>
				<tr>
					<td align="center" class="qinggoudan_table_title">
						可访问服务器
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:two align="left" arl="${information.serverList }" selectItem="服务器列表" waitItem="可访问服务器列表" leftId="serverId"
							leftValue="serverTag" rightId="serverId" rightValue="serverTag" rightName="serverId" selectStyle="two_select"
							itemStyle="item_two_select" moveStyle="button_select" size="15" width="80%" contextPath="${contextPath}" />
						<input type="hidden" name="hiddenServerIds" value=""/>
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveClientGroup()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/client/clientGroupController.do?method=queryClientGroupList','viewOrEdit,currentPage,queryClientGroupName,queryNote,queryStatus')">
					</td>
				</tr>
			</table>
				<input type="hidden" name="viewOrEdit" value="${information.searchForm.viewOrEdit}" />
				<input type="hidden" name="currentPage" value="${information.searchForm.currentPage}" />
				<input type="hidden" name="queryClientGroupName" value="${information.searchForm.queryClientGroupName}" />
				<input type="hidden" name="queryNote" value="${information.searchForm.queryNote}" />
				<input type="hidden" name="queryStatus" value="${information.searchForm.queryStatus}" />
				
		</form>
	</body>

</html>
