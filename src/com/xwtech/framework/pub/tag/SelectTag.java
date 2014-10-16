package com.xwtech.framework.pub.tag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

import com.xwtech.framework.pub.utils.SqlString;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;


public class SelectTag extends TagSupport  //在仓库汇总查询中显示物料种类的下拉框
{	
	private String relationSql; //级联条件
	private String sql;	
	private String title;		//默认名称
	private String name;		//选项名
	private String sonName;	//子选项名
	private String mvalue;		//选中的值
	private String next;       //下一层级
	private String fatherValue;//
	
	
	public String getFatherValue() {
		return fatherValue;
	}

	public void setFatherValue(String fatherValue) {
		this.fatherValue = fatherValue;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public int doEndTag() throws JspException
	{
	    return EVAL_PAGE;
	}
	
	public int doStartTag() throws JspException
	{
		try
		{
			pageContext.getOut().print(getHtml((HttpServletRequest)pageContext.getRequest()));			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	public String getHtml(HttpServletRequest request)
	{

		StringBuffer body = new StringBuffer();
 		
		DataSource ds = BaseFrameworkApplication.getBaseJdbcDAO().getDataSource();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{	        
	        conn = ds.getConnection();
	        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        
	        
	        if(sonName!=null&&!sonName.equals("")&&relationSql!=null&&!relationSql.equals("")) //存在子级联
	        {
	        	body=body.append("<script language='javascript'>\n");
	        	body=body.append("var sonSelectAll = new Array();");
	        	rs=stmt.executeQuery(relationSql);  //查询关联数据
	        	
	        	int i=0;
	        	while(rs.next())
		        {
	        		body = body.append("sonSelectAll["+i+"] = new Array('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(3)+"');\n");
	        		i++;
		        }
	        	

		        body=body.append("function change(id)");															body=body.append("\n");		        
		        body=body.append("{");																				body=body.append("\n");
		        body=body.append("  var j=0;");																		body=body.append("\n");
		        body=body.append("	var sonSelect = new Array();");													body=body.append("\n");
		        body=body.append("	var num = sonSelectAll.length;");												body=body.append("\n");	
		        body=body.append("	for(i = 0; i < num; i ++)");													body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		if(sonSelectAll[i][2]==id)");												body=body.append("\n");
		        body=body.append("		{");																		body=body.append("\n");
		        body=body.append("			sonSelect[j]=new Array(sonSelectAll[i][0],sonSelectAll[i][1]);");		body=body.append("\n");
		        body=body.append("			j++;");																	body=body.append("\n");
		        body=body.append("		}");																		body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
		        
		        body=body.append("  var ss = document.getElementsByName('"+sonName+"')[0];");						body=body.append("\n");
		        
		        body=body.append("	for (m = ss.options.length-1; m >= 0; m --)");									body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		ss.options[m] = null;");													body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
		        
		        body=body.append("	if(sonSelect.length>0)");														body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		ss.disabled=false;");														body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
		        
//		        body=body.append("	ss.options[0]=new Option('全部物品','');");								    body=body.append("\n");
//		        body=body.append("	ss.options[0].selected=true;");												body=body.append("\n");
    	        
		        body=body.append("  for(l=0;l<sonSelect.length;l++)");											    body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		ss.options[l]=new Option(sonSelect[l-0][1],sonSelect[l-0][0]);");				body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
		        body=body.append("}");																				body=body.append("\n");
		        body=body.append("</script>");																		body=body.append("\n");
		        
	        	body=body.append("<select name="+name+" onChange='change(this.value)'>\n");							body=body.append("\n");
	        	
	        }
	        else //子框
	        {
	        	body=body.append("<select name="+name+">\n");
	        }
	        if(!sql.equals(""))//子框sql为""
	        {
		        SqlString metadataSqlString = new SqlString(sql);
		        String metadatasql = metadataSqlString.getParseSql(request, null);
		        rs = stmt.executeQuery(metadatasql);
	        }

	        if(!StringUtils.isEmpty(title)) {
	        	body=body.append("<option value=''>"+title+"</option>\n");
	        }

	       	
	       	String value; //选项值
	       	String text;  //选项名称	       	
       	
	       	while(!sql.equals("")&&rs.next())  //父框
	        {
	        	value = rs.getString(1);
	        	text = rs.getString(2);	        	
	        	
	        	if(mvalue.equals(value)) //该项被选中
	        	{
	        		body=body.append("<option value="+value+" selected>"+text+"</option>\n");
	        	}
	        	else
	        	{
	        		body=body.append("<option value="+value+">"+text+"</option>\n");
	        	}
	        }
	       	
	        body=body.append("</select>");
	        
	        
	       	if(sql.equals("")&&fatherValue!=null&&!fatherValue.equals(""))  //第一次查询后子框的值
	       	{
	       		body=body.append("<script language='javascript'>");												    body=body.append("\n");
        		body=body.append("  var id="+fatherValue+";");														body=body.append("\n");
        		body=body.append("  var j=0;");																		body=body.append("\n");
		        body=body.append("	var sonSelect = new Array();");													body=body.append("\n");
																	body=body.append("\n");
		        
		        body=body.append("  var ss = document.getElementsByName('"+name+"')[0];");  						body=body.append("\n");
		        
		        body=body.append("	for (m = ss.options.length-1; m > 0; m --)");/*清空*/							body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		ss.options[m] = null;");													body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
		        
		        body=body.append("	if(id=='')");				/*父框未选择 子框为全部物品*/ 						body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        //body=body.append("		ss.options[0]=new Option('全部物品','');");								    body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
		        body=body.append("	else{");																		body=body.append("\n");
		        //body=body.append("		ss.options[0]=new Option('全部物品','');");									body=body.append("\n");
		        body=body.append("	var num = sonSelectAll.length;");												body=body.append("\n");	
		        body=body.append("	for(i = 0; i < num; i ++)");			/*根据上一级父框填充数组*/				body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		if(sonSelectAll[i][2]==id)");												body=body.append("\n");
		        body=body.append("		{");																		body=body.append("\n");
		        body=body.append("");																		        body=body.append("\n");
		        body=body.append("			sonSelect[j]=new Array(sonSelectAll[i][0],sonSelectAll[i][1]);");		body=body.append("\n");
		        body=body.append("			j++;");																	body=body.append("\n");
		        body=body.append("		}");																		body=body.append("\n");
		        body=body.append("	}");			        														body=body.append("\n");
		        body=body.append("	}");
		        
		        body=body.append("  for(l=1;l<=sonSelect.length;l++)");		/*填充子框的内容*/					    body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("		ss.options[l]=new Option(sonSelect[l-1][1],sonSelect[l-1][0]);");				body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
       		
	       		
	       		body=body.append("  var mvalue='"+mvalue+"';");														body=body.append("\n");
		        body=body.append("	for (m = ss.options.length-1; m > 0; m --)");/*将查询前的值赋回*/					body=body.append("\n");
		        body=body.append("	{");																			body=body.append("\n");
		        body=body.append("	if(ss.options[m].value==mvalue){	ss.options[m].selected=true;}");			body=body.append("\n");
		        body=body.append("	}");																			body=body.append("\n");
	       		body=body.append("</script>");
	       	}

	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
        {
			try
			{
	            if(rs != null)
	            {
	              rs.close();
	            }
	            if(stmt != null)
	            {
	              stmt.close();
	            }
	            if(conn != null && !conn.isClosed())
	            {
	              conn.close();
	            }
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
        }
		return body.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMvalue() {
		return mvalue;
	}

	public void setMvalue(String mvalue) {
		this.mvalue = mvalue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelationSql() {
		return relationSql;
	}

	public void setRelationSql(String relationSql) {
		this.relationSql = relationSql;
	}
}
