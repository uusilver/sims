<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.tmind.mss.pub.constants.MssConstants" %>
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
		// 初始化页面，将所属服务器信息加入"已分配服务器"下拉框中
		$(function(){
			var sgMappingJsonObj = $.parseJSON('${information.sgMappingResult}');
			var serverId;
			var serverName;
			var sel_dest = $("select[name=serverId]");
			for(var i=0;i<sgMappingJsonObj.length;i++){
					serverId = sgMappingJsonObj[i].serverId;
					serverName = sgMappingJsonObj[i].serverTag;
					sel_dest.append("<option value='"+serverId+"'>"+serverName+"</option>");
			}
		});
		
		</script>
		<script type="text/javascript">

		function saveServerGroup(){
			var viewOrEdit = $("input[name=viewOrEdit]").val();
			var alertMessage = "您确定要创建该服务器组么？";
			
			if(checkGroupName()&&checkComment()&&checkSelectedServer()){
				if(viewOrEdit!=null&&viewOrEdit=="edit"){
					alertMessage="您确定要修改该服务器组么？";
				}
				if(confirm(alertMessage)){
					var serverOptions = $("select[name=serverId]").find("option");
					var serverIds = "";
					for(var i=0;i < serverOptions.length; i++){
						serverIds += serverOptions[i].value + ",";
					}
					serverIds = serverIds.substring(0, serverIds.length - 1);
			
					$("input[name=hiddenServerIds]").attr("value",serverIds);
					//alert($("input[name=hiddenServerIds]").val());
					document.serverGroupAddForm.submit();
				}
			}else{
				alert("请根据提示修改相应内容！");
			}
		}
		
		//校验
		function checkGroupName() {
			var groupName = document.getElementById("servergroupname");
			
			var infoDiv = document.getElementById("servergroupnameDiv");
			var editFlag = $("input[name='viewOrEdit']").val();
			
			if(groupName.value==""){
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "【服务器组名】不能为空，请修改！";
		        groupName.focus();
				return false;
			}
			
			if(groupName.value.length>20)
			{
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "【服务器组名】不能超过20个字符，请修改！";
		        groupName.focus();
				return false;
			}

			if((editFlag==null||editFlag==''||editFlag!='edit')&&checkIsExist(groupName, '', 'server_group') == "false") {
		        groupName.focus();
				return false;
			}
			
			//infoDiv.className = "OK";
		    //infoDiv.innerHTML = "";
		    infoDiv.innerHTML = "<img src=\"${contextPath }/mss/image/correct.png\" width=\"25\" heigth=\"25\"/>";
		    return true;
		}
		
		
		
		//校验备注
		function checkComment() {
			var comment = document.getElementById("server_comment");
			
			if(comment.value==""){
				return true;
			}

			var infoDiv = document.getElementById("server_commDiv");
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
		
		function checkSelectedServer(){
			var serverList = $("select[name=serverId]").find("option");
			
			var infoDiv = document.getElementById("server_listDiv");
			
			if(serverList.length==0){
				infoDiv.className = "warning";
		        infoDiv.innerHTML = "请选择至少一个服务器添加到右边列表框中！";
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
		<form name="serverGroupAddForm" method="post" action="${contextPath}/mss/jsp/server/serverGroupController.do?method=saveServerGroup">
		<input type="hidden" name="checkUrl" value="${contextPath}/mss/jsp/sysManage/roleManageController.do?method=checkIsExist&stateColName=status" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table"
				style="margin:0px;">
				<tr>
					<td class="qinggoudan_title01_td1">
						创建新服务器分组
					</td>
				</tr>
			</table>

			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						服务器组名
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="serverGroupName" id="servergroupname" type="text" class="qinggoudan_input023" size="20" maxlength="50"
							value="${information.serverGroup.servergroupname}"
							onchange="checkGroupName()">
						<input type="hidden" name="serverGroupId" id="server_group_id" value="${information.serverGroup.servergroupid }" />
						<span id="servergroupnameDiv"></span>
					</td>
				</tr>
				<tr height="20">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="serverComment" id="server_comment" rows="5" cols="30" onchange="checkComment()">${information.serverGroup.note}</textarea>
					<span id="server_commDiv"></span>
					</td>
				</tr>
				<tr>
					<td align="center" class="qinggoudan_table_title">
						服务器信息
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<pub:two align="left" arl="${information.serverList }" selectItem="服务器列表" waitItem="已分配服务器列表" leftId="serverId"
							leftValue="serverTag" rightId="serverId" rightValue="serverTag" rightName="serverId" selectStyle="two_select"
							itemStyle="item_two_select" moveStyle="button_select" size="15" contextPath="${contextPath}" />

						<input type="hidden" name="hiddenServerIds" value="">
						<span id="server_listDiv"></span>
					</td>
				</tr>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						&nbsp;
						<input type="button" class="anniu_out" name="saveBtn" value=" 确 定 " onclick="saveServerGroup()"
							onMouseOver="className='anniu_over'" onMouseOut="className='anniu_out'">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="anniu_out" value=" 返 回 " onMouseOver="className='anniu_over'"
							onMouseOut="className='anniu_out'"
							onclick="goList('${contextPath }/mss/jsp/server/serverGroupController.do?method=queryServerGroupList','viewOrEdit,currentPage,queryServerGroupName,queryNote,queryStatus')">
					</td>
				</tr>
			</table>
				<input type="hidden" name="viewOrEdit" value=<%=request.getParameter("viewOrEdit") == null ? "" : request.getParameter("viewOrEdit")%> />
				<input type="hidden" name="currentPage" value="${information.searchForm.currentPage}" />
				<input type="hidden" name="queryServerGroupName" value="${information.searchForm.queryServerGroupName}" />
				<input type="hidden" name="queryNote" value="${information.searchForm.queryNote}" />
				<input type="hidden" name="queryStatus" value="${information.searchForm.queryStatus}" />
				
		</form>
	</body>

</html>
