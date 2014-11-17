<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.tmind.framework.pub.result.ResultInfo" %>
<%@ page import="com.tmind.framework.pub.result.ResultInfos" %>
<%@ page import="com.tmind.framework.pub.web.RequestNameConstants" %>
<%@ include file="/framework/jsp/taglibs.jsp"%>
<link href="${contextPath}/mss/css/main.css" rel="stylesheet" type="text/css" />
<%
  HashMap map = (HashMap)request.getAttribute(RequestNameConstants.INFORMATION);
  ResultInfos resultInfos = (ResultInfos)map.get(RequestNameConstants.RESULTINFOS);
  String handleResult = (String)map.get("handleResult");
  String flag = (String)map.get("flag");
	%>
		<script language="javascript">
			if("<%=handleResult%>" != "null" && "<%=handleResult%>"!= ""){
				alert("<%=handleResult%>");
			}
		</script>
	<% 
  if(resultInfos != null){
    Iterator it = resultInfos.iterator();
    String resultInfoStr = "";
    while(it.hasNext()){
      ResultInfo resultInfo = (ResultInfo)it.next();
      if(resultInfo != null){
        resultInfoStr += resultInfo.getResultInfo();
      }
    }  
	%>
	<script language="javascript">
		if("<%=resultInfos.getIsAlert()%>" == "true" && "<%=resultInfoStr%>"!="登陆成功，欢迎您！"){
			alert("<%=resultInfoStr%>");
			if("<%=resultInfoStr%>"=="请购单成功提交到OA！" && confirm("您需要跳转到OA系统进行请购单的下一步处理吗？")){
				window.open("http://emis.js.cmcc/","","toolbar=yes, location=yes, directories=yes, status=yes, menubar=yes, scrollbars=yes, resizable=yes,width=800, height=600");
				//window.open("/mss/jsp/budget/modifyBudget.jsp?&model=all&budgetId="+id+"&budgetAccount="+value,"","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=300, height=200");
			}
		}
		if("<%=resultInfos.getIsRefreshParentWindow()%>"=="true"){
			parent.window.location.href = parent.window.location.href;
		}
		if("<%=resultInfos.getIsRefreshOpenerWindow()%>"=="true"){
			opener.window.location.href = opener.window.location.href;
		}
	</script>
	<%  
	if(resultInfos.getGotoUrl()!=null) {
     	//当处理成功时判断页面是否直接跳转
     	if(resultInfos.getIsRedirect()) {
	    %>
		<script language="javascript">
			if(parent.window.location!=top.window.location){
				parent.window.location = encodeURI("${contextPath}<%=resultInfos.getGotoUrl()%>");
			}else{
				window.location = encodeURI("${contextPath}<%=resultInfos.getGotoUrl()%>");
			}
		</script>
		<%
	    }else if(flag!=null&&flag.equals("s")){
		   out.println("<table width=\"35%\" height=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
		   out.println("<tr align=\"center\">");
	       out.println("<td><img id=\"info_alert\" width=\"150\" height=\"150\"></td>");
		   out.println("<td>");
		   out.println("<a class=\"qinggoudan_title01_td1\">" + resultInfoStr + "</a>");
		   out.println("<br><br>");
		   out.println("<a href=\"#\" onclick=\"sendR('${contextPath}/" + resultInfos.getGotoUrl() + "')\"  class=\"qinggoudan_title01_td1\">自动跳转到相关页面</a>");
		   out.println("</td>");
		   out.println("</tr>");
		   out.println("</table>");
%>
<script language="javascript" type="text/javascript">

document.getElementById("info_alert").setAttribute("src", "${contextPath}/mss/image/info-alert.png");

function sendR(){
	parent.location = "${contextPath}/login.jsp";
	
}
var t = setTimeout("sendR()", 4000);
</script>
<%
		}else{
		   out.println("<table width=\"35%\" height=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
		   out.println("<tr align=\"center\">");
	       out.println("<td><img src=\"../../mss/image/info-alert.png\" width=\"150\" height=\"150\"></td>");
		   out.println("<td>");
		   out.println("<a class=\"xuqiu_title\">" + resultInfoStr + "</a>");
		   out.println("<br>");
		   out.println("<a href=\"${contextPath}"+resultInfos.getGotoUrl()+"\"  class=\"xuqiu_title\">自动跳转到相关页面</a>");
		   out.println("<meta http-equiv=\"refresh\" content=\"4;URL=${contextPath}/"+resultInfos.getGotoUrl()+"\">");		   out.println("</td>");
		   out.println("</tr>");
		   out.println("</table>");
		}
  	}
  	
	if(resultInfos.getIsCloseWindow()==true) {
	%>
		<script language="javascript">
			window.close();
		</script>
	<%
	}
  }
%>