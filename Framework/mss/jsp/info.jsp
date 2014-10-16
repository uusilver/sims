<%@page contentType="text/html; charset=utf-8"%>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
	  if('${info}'!='')
	  {	
		alert('${info}');	
	  }
</script>

<%
	if(session.getAttribute("info")!=null)
	{
		session.removeAttribute("info");
	}
 %>