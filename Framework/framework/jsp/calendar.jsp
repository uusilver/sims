<%@ page contentType="text/html; charset=utf-8"	import="java.util.*,java.text.*" %>
<%@include file="/framework/jsp/taglibs.jsp"%>


<%
    //禁止客户端浏览器缓存页面
    response.addHeader("Cache-control","private");
    response.addHeader("Cache-control","no-cache");
%>

<%!Calendar source_date; %>
<%!String ubound,lbound,init_time,init_date,format; %>
<%!boolean readonly,showtime,canempty;%>

<%!
private int str2int(String src, int def)
{
  if(src == null || src.length() == 0)
    return def;
  else
  {
    try
    {
      return Integer.parseInt(src);
    }
    catch(NumberFormatException ex)
    {
      return def;
    }
  }
}
%>
<%
  SimpleDateFormat formtter;
  Calendar cale;
  String text;

  //取得返回日期的格式

  format = request.getParameter("format");

  //检查是否需要显示时间

  if(format.indexOf('H') != -1 || format.indexOf('m') != -1 || format.indexOf('s') != -1)
    showtime = true;
  else
    showtime = false;

  //读取源日期,默认为当前日期

  init_time = "";
  String source_text = request.getParameter("value");
  if(source_text == null || source_text.equals(""))
    source_date = Calendar.getInstance();
  else
  {
    formtter = new SimpleDateFormat(format);
    try {
      source_date = Calendar.getInstance();
      source_date.setTime(formtter.parse(source_text));
      //如果需要显示时间且源日期文本中含有时间,则取得初始化时间
      if(showtime)
      {
        formtter = new SimpleDateFormat("HH:mm:ss");
        init_time = formtter.format(source_date.getTime());
      }
    }
    //源日期文本和源日期文本格式不匹配
    catch (ParseException ex) {
      source_date = Calendar.getInstance();
    }
  }
  formtter = new SimpleDateFormat("yyyy-MM-dd");
  init_date = formtter.format(source_date.getTime());

  //读取可选择日期的上限和下限,格式固定为"yyyyMMdd"
  formtter = new SimpleDateFormat("yyyyMMdd");
  ubound = request.getParameter("ubound");
  //如果没有设置绝对上限
  if(ubound == null || ubound.length() != 8)
  {
    //查找是否设置相对上限
    int n = str2int(request.getParameter("relubound"),10);

    cale = Calendar.getInstance();
    cale.add(Calendar.YEAR,n);
    ubound = formtter.format(cale.getTime());
  }

  lbound = request.getParameter("lbound");
  //如果没有设置绝对下限
  if(lbound == null || lbound.length() != 8)
  {
    //查找是否设置相对下限
    int n = str2int(request.getParameter("rellbound"),-10);

    cale = Calendar.getInstance();
    cale.add(Calendar.YEAR,n);
    lbound = formtter.format(cale.getTime());
  }

  //是否为只读,默认为非只读
  text = request.getParameter("readonly");
  if(text != null && text.equals("1"))
    readonly = true;
  else
    readonly = false;

  //日期是否允许为空,默认允许为空
  text = request.getParameter("canempty");
  if(text != null && text.equals("0"))
    canempty = false;
  else
    canempty = true;
%>
<html>

<head>
<title><%=(showtime?"日期时间选择":"日期选择") %></title>
<link rel='stylesheet' type='text/css' href='${contextPath }/framework/css/style.css'>

<script language="JavaScript" src="${contextPath }/framework/js/calendar.js" type="text/javascript"></script>

<script language="JavaScript" type="text/javascript">
var sel_date = "<%=init_date%>";

/*
 *选择日期时间
 */
function selectDate()
{
  var day = event.srcElement.innerText;
  var year = document.all.ele_year.value;
  var month = document.all.ele_month.value;
  if(year == "" || month == "")
  {
    alert("请选择一个日期");
    return;
  }

  <%//在仅选择日期模式下

  if(!showtime)
  {%>
  window.returnValue = dtp_formatDate$(year,month,day,"<%=format%>");
  window.close();
  <%}else{		//还选择时间的模式	%>
  sel_date = dtp_formatDate$(year,month,day,"yyyy-MM-dd");
  document.all.ele_curdate.innerText = sel_date;
  <%}%>
}

<%if(showtime && !readonly){%>
/**
*确定选择日期时间
*/
function selectDateTime()
{
  if(sel_date == "")
  {
    alert("请选择一个日期");
    return;
  }

  var type;
  if(document.all.ele_timetype[0].checked)
    type = document.all.ele_timetype[0].value;
  else
    type = document.all.ele_timetype[1].value;

  if(type == 2)
  {
    if(document.all.ele_time.value == "")
    {
      alert("请输入时间");
      document.all.ele_time.focus();
      return;
    }
    else if(!checkUserInputTime(document.all.ele_time.value))
    {
      document.all.ele_time.select();
      document.all.ele_time.focus();
      return;
    }
  }

  var year,month,day,hour,minute,second;
  var re,arr;

  //获取日期
  re = /^(\d{1,4})-(\d{1,2})-(\d{1,2})$/;
  arr = re.exec(sel_date);
  year = dtp_parseInt$(arr[1]);
  month = dtp_parseInt$(arr[2]);
  day = dtp_parseInt$(arr[3]);

  //获取时间
  if(type == 1)
  {
    var dt = new Date();
    hour = dt.getHours();
    minute = dt.getMinutes();
    second = dt.getSeconds();
  }
  else
  {
    re = /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    arr = re.exec(document.all.ele_time.value);
    hour = dtp_parseInt$(arr[1]);
    minute = dtp_parseInt$(arr[2]);
    second = dtp_parseInt$(arr[3]);
  }

  var text = dtp_formatDateTime$(year,month,day,hour,minute,second,"<%=format%>");
  window.returnValue = text;
  window.close();
}
<%}%>

/*
 *清空
 */
function selectEmpty()
{
  <%if(canempty){%>
  window.returnValue = "blank";
  window.close();
  <%}%>
}

/*
 *显示系统的当前日期

 */
function buildTimeText()
{
  document.all.ele_systime.innerText = dtp_formatTime$(new Date(),"HH:mm:ss");
}

/*
 *显示当前月份的日历

 */
function buildCalendar()
{
  var year = document.all.ele_year.value;
  var month = document.all.ele_month.value;

  //该月第一天是星期几

  var dt = new Date(year,month - 1,1);
  var idx = dt.getDay() + 1;

  //将1号前的日期文本清空



  for(var i=1;i<idx;i++)
    document.all["cell_" + i].innerText = "";

  //生成当月每天的日期文本

  for(;;i++)
  {
    var day = i - idx + 1;
    dt = new Date(year,month - 1,day);
    //如果到了下个月,就跳出

    if(dt.getMonth() != month - 1)
      break;

    document.all["cell_" + i].innerText = day;
    //取得该天的日期文本

    var curdate = dtp_formatDate$(year,month,day,"yyyyMMdd");

  <%if(!readonly){%>
    if(curdate < "<%=lbound%>" || curdate > "<%=ubound%>")
      document.all["cell_" + i].removeAttribute("href");
    else
    {
      document.all["cell_" + i].href = "#";
      document.all["cell_" + i].onclick = selectDate;
    }
  <%}%>
  }

  //判断是否有空行,有则隐藏
  if(i <= 36)
    document.all["row_6"].style.display = 'none';
  else
    document.all["row_6"].style.display = '';

  //将剩余的天数清空
  for(;i<=42;i++)
    document.all["cell_" + i].innerText = "";
}

</script>

</head>
<BODY text=black vLink=black aLink=black link=black leftMargin=5 topMargin=0 scroll=no rightMargin=5>

<%--如果不显示选择时间的话,则不显示当前选择内容--%>
<%if(showtime){%>
<table width="290" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="25" background="${contextPath }/framework/image/contactor_detail_r12_c5.jpg">
      <TABLE width="260" height=20 border="0" align="center" cellpadding="0" cellspacing="0">
      <TBODY>
        <TR style="HEIGHT: 22px">
          <TD><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="font_white">当前选择的日期:</td>
                <td align="center" bgcolor="#FFFFFF" id="ele_curdate"><%=init_date%></td>
              </tr>
          </table></TD>
          <TD align=right>
            <%if(!readonly){ %><input type="button" class="button_search" value="确定" onclick="javascript:selectDateTime();"><%}%>&nbsp;</TD>
        </TR>
      </TBODY>
    </TABLE></td>
  </tr>
</table>
<%}%>


<table width="290"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><TABLE width="100%"
  border=0 cellPadding=0 cellSpacing=0 class=table_dropshadow>
      <TBODY>
        <TR>
          <TD>
            <TABLE width="100%" border="0" align=center cellpadding="0" cellspacing="0">
              <TBODY>
                <TR bgcolor="#FFFFFF" style="HEIGHT: 22px">
                  <TD width="35" align="right">年份&nbsp;</TD>
                  <TD width="60"><select name="ele_year" class="select_1"  onchange="javascipt:buildCalendar();">
        <%
          for(int i=Integer.parseInt(lbound.substring(0,4));i<=Integer.parseInt(ubound.substring(0,4));i++)
          {
            out.println("<option value=\"" + i + "\">" + i + "</option>");
          }
        %>
        </select></TD>
                  <TD width="27">月份</TD>
                  <TD width="70" height="30"><select name="ele_month" class="select_1"  onchange="javascipt:buildCalendar();">
          <option value="1">一月</option>
          <option value="2">二月</option>
          <option value="3">三月</option>
          <option value="4">四月</option>
          <option value="5">五月</option>
          <option value="6">六月</option>
          <option value="7">七月</option>
          <option value="8">八月</option>
          <option value="9">九月</option>
          <option value="10">十月</option>
          <option value="11">十一月</option>
          <option value="12">十二月</option>
        </select></TD>
                  <TD>
<%if(!readonly && canempty){%><input type="button" class="button_normal" value="清空" onclick="javascript:selectEmpty();"><%}%></TD>
                <TD width="7">&nbsp;</TD>
                </TR>
              </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="100%" borderColorLight=#8ea525>
              <TBODY>
                <TR align=middleheight=20>
                  <TD width="14%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>日</B></TD>
                  <TD width="14%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>一</B></TD>
                  <TD width="14%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>二</B></TD>
                  <TD width="14%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>三</B></TD>
                  <TD width="14%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>四</B></TD>
                  <TD width="14%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>五</B></TD>
                  <TD width="16%" align=middle bgcolor="#FFFFFF" class="td_search_title"><B>六</B></TD>
                </TR>
                <SCRIPT language=JavaScript>
      function init_cells()
      {
        for(var i=1;i<=6;i++)
        {
          document.write("<tr id=\"row_" + i + "\">");
          for(var j=1;j<=7;j++)
          {
            document.write("<td width=\"14%\" bgcolor=\"#F4F7EA\">");
            document.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"16\">");
            document.write("	<tr class=\"td_date_height\">");
            document.write("		<td class=\"font_dropshadow\">");
            document.write("		<div align=\"center\" class=\"table_head_font\">");
            document.write("			<font size=\"2\" face=\"Arial\">");
            document.write("			<a class=\"link_date\" id=\"cell_" + ((i-1)*7+j) + "\">" );
            document.write("			</a></font></div>");
            document.write("		</td>");
            document.write("	</tr>");
            document.write("</table>");
            document.write("</td>");
          }
          document.write("</tr>");
        }
      }
      init_cells();
      </SCRIPT>
              </TBODY>
          </TABLE></TD>
        </TR>
      </TBODY>
    </TABLE></td>
  </tr>
</table>

<%--如果不显示选择时间的话,则不显示时间选择--%>
<%if(showtime){%>

<table width="290" id="table_timearea" height="32"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" bgcolor="#FFFFFF"><TABLE border="0" cellpadding="0" cellspacing="0" id=table_timearea>
      <TBODY>
        <TR>
          <TD>选择时间:&nbsp;</TD>
          <TD width="30" align="center">
            <input type="radio" name="ele_timetype" <%=(init_time.equals("")?"checked":"")%> value="1">
          </TD>
          <TD>系统时间</TD>
          <TD width="30" align="center"><input type="radio" name="ele_timetype" <%=(!init_time.equals("")?"checked":"")%> value="2" onclick="javascript:document.all.ele_time.select();document.all.ele_time.focus();"></TD>
            <TD align="right">自定义&nbsp;</TD>
            <TD align="right">
              <input type="text" class="input_date"  name="ele_time" value="<%=init_time%>" title="请以&quot;时:分:秒&quot;的格式输入" size="8" onchange="javascript:if(this.value.length > 0)document.all.ele_timetype[1].checked=true;">
            </TD>
            <!--<SPAN
              id=ele_systime></SPAN>-->
        </TR>
      </TBODY>
</TABLE></td>
</table>
<%} %>


<script language="JavaScript" type="text/javascript">
//设置该年,月



document.all.ele_year.value = <%=source_date.get(Calendar.YEAR)%>;
if(document.all.ele_year.value == <%=source_date.get(Calendar.YEAR)%>)
  document.all.ele_month.value = <%=source_date.get(Calendar.MONTH)+1%>;
else
{
  if(document.all.ele_year.options.length > 0)
    document.all.ele_year.options[0].selected = true;
  <%if(showtime){%>
  sel_date = "";
  document.all.ele_curdate.innerText = sel_date;
  <%}%>
}

//生成该月日历
buildCalendar();

<%if(showtime){%>
//如果是只读



<%if(readonly){%>

  //禁止选择和输入日期



  document.all.ele_timetype[0].disabled = true;
  document.all.ele_timetype[1].disabled = true;
  document.all.ele_time.readOnly = true;
<%}%>

//设置系统时间
<%--
buildTimeText();
window.setInterval("buildTimeText()", 1000);
--%>
<%}%>
</script>
</body>

</html>
