<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/framework/jsp/taglibs.jsp"%>

<html>
	<head>
		<title>南京移动物业管理系统</title>
	    <link href="${contextPath }/mss/css/menu.css" rel="stylesheet" type="text/css" />
	    <script type="text/javascript">
	    	function tabit(prefix,idx) {
				for(var i=0;i<20;i++)
				{
					var tab = document.getElementById(prefix + i);
					if(tab != null && typeof(tab) != "undefined")
					{
						tab.className = "menu_out";
						document.getElementById(prefix + i + "_c").style.display = "none";
					}
				}
				if(document.getElementById(prefix + idx)!=null){
					document.getElementById(prefix + idx).className="menu_over";
					document.getElementById(prefix + idx + "_c").style.display="";
				}
			}
			
			function onBodyLoad()
			{
				tabit('tab',0);
			}
	    </script>
	</head>
	
	<body onLoad="onBodyLoad()">
	
	<table width="160" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="155" valign="top" bgcolor="#4989D3">
				<table width="155" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="${contextPath }/mss/image/menu_01.jpg" width="155" height="9"></td>
					</tr>
					<c:if test="${information.menuList!=null}">
						<c:forEach items="${information.menuList}" var="topMenu" varStatus="status">
							<c:if test="${topMenu.menuLevel==1}">
								<tr>
									<td class="menu_out" id="tab${status.index}"  onClick="tabit('tab','${status.index}')">·${topMenu.menuName}</td>
								</tr>
								<c:if test="${topMenu.menus!=null}">
									<tr id="tab${status.index}_c">
										<td><table width="155" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="menu_bg">
											<c:forEach items="${information.menuList}" var="subMenu">
												<c:if test="${subMenu.menu.menuId==topMenu.menuId}">
										        	<tr>
										        		<td class="menu_td1">
										        			<img src="${contextPath }/mss/image/dot.gif" align="absmiddle">&nbsp;&nbsp;&nbsp;
										        			<a href="${contextPath }${subMenu.menuUrl}" target="mainFrame" class="menu">${fn:escapeXml(subMenu.menuName)}</a>
										        		</td>
										        	</tr>
												</c:if>
											</c:forEach>
							        </table></td>
							      </tr>
								</c:if>
							</c:if>
						</c:forEach>
					</c:if>
					<tr>
						<td height="15" bgcolor="#1C61FD"></td>
					</tr>
				</table>
			</td>
			<td width="5" class="menu_bg_line">&nbsp;</td>
		</tr>
	</table>
		  
	</body>
</html>
