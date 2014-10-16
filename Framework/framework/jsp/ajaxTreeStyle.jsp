<%@ page contentType="text/xml; charset=UTF-8" %>
<%
StringBuffer buf = new StringBuffer();
    String sql = request.getParameter("sql");
    String pId = request.getParameter("pId");
    String idValue = "";
    if(pId.equals("0"))
    {
      idValue = " is null";
    }
    else
    {
      idValue = "="+pId;
    }
    try{
    	Long.parseLong(pId);
    }catch(Exception e){
       idValue = " is null";
    }
    try
    {
        com.xwtech.framework.pub.utils.MacroString macroSql = new com.xwtech.framework.pub.utils.MacroString(sql, "$[", "]$");
        macroSql.setMacroValue("keyId", idValue);
        sql = macroSql.getString(false);
        System.out.println("sql="+sql);

      java.util.List list = com.xwtech.framework.pub.web.FrameworkApplication.baseJdbcDAO.queryForList(sql);
          buf.append("<root>");
          for (int i=0;i<list.size();i++)
          {
            java.util.Iterator it = ((java.util.Map)list.get(i)).entrySet().iterator();
            java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
            String id = entry.getValue()==null?"":entry.getValue().toString();
            entry = (java.util.Map.Entry)it.next();
            String parentId = entry.getValue()==null?"":entry.getValue().toString();
            entry = (java.util.Map.Entry)it.next();
            String name = entry.getValue()==null?"":entry.getValue().toString();
            entry = (java.util.Map.Entry)it.next();
            String hasChild = entry.getValue()==null?"":entry.getValue().toString();
            hasChild = Integer.parseInt(hasChild)>0?"1":"0";
            buf.append("<node id=\""+id+"\" hasChildren=\""+hasChild+"\" parentId=\""+parentId+"\">"+name+"</node>");
          }
          buf.append("</root>");

      org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat.createPrettyPrint();
      org.dom4j.io.XMLWriter writer = new org.dom4j.io.XMLWriter(response.getWriter(), format);
      org.dom4j.Document doc = org.dom4j.DocumentHelper.parseText(buf.toString());
      System.out.println(doc.asXML());
      writer.write(doc);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
%>
