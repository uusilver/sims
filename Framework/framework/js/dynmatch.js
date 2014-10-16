

/**
 * 删除字符串左右的空格和Tab字符
 */
function dynmatch_trimAll$(src)
{
  if(src == null)
    return "";

  src = "" + src;
  for(var i=0;i<src.length;i++)
  {
    if(src.charAt(i) != " " && src.charAt(i) != "\t")
      break;
  }

  for(var j=src.length - 1;j>=0;j--)
  {
    if(src.charAt(j) != " " && src.charAt(j) != "\t")
      break;
  }

  if(i > j)
    return "";

  return src.substring(i,j + 1);
}

var dynmatch_HideElementTemp = new Array();
window.setTimeout(dynmatch_onbodyload,1000);

//流水序号
var dynmatch_sequenceID = 0;

/**
 * 检查元素的序号，如果该元素没有ID，则设置新的ID
 * return:元素的（新）ID
 */
function dynmatch_checkObjectID(obj)
{
  if(obj.id == null || obj.id == "")
  {
    dynmatch_sequenceID ++;
    if(arguments.length == 1)
      obj.id = "dynmatch_" + (new Date()).getTime() + "X" + dynmatch_sequenceID;
    else
      obj.id = arguments[1];
  }

  return obj.id;
}

/**
 * 页面初始化
 */
function dynmatch_onbodyload()
{
  if(document.body == null)
  {
    window.setTimeout(dynmatch_onbodyload,1000);
    return;
  }

  var html = ("<div id=\"dynmatch_data_container\" style=\"background-color:#FFFFFF;border-style:inset; border-width:2px; padding:0;overflow-y:auto;width:400px;height:150px;display:none;position:absolute;\" onscroll=\"javascript:document.getElementById(this.getAttribute('dynmatch_refid')).focus();\">");
  html += ("<table id=\"dynmatch_table_container\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#999999\" width=\"100%\">");
  html += ("<tr>");
  html += ("	<td>");
  html += ("	<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
  html += ("		<tr>");
  html += ("			<td bgcolor=\"#FFFFFF\">");
  html += ("				<table id=\"dynmatch_data_table\" bgcolor=\"#FFFFFF\" cellspacing=\"1\" cellpadding=\"3\" width=\"100%\" dynmatch_pos=\"-1\" onclick=\"javascript:document.getElementById(this.getAttribute('dynmatch_refid')).focus();\">");
  html += ("					<colgroup>");
  html += ("					</colgroup>");
  html += ("					<thead>");
  html += ("					</thead>");
  html += ("					<tbody>");
  html += ("					</tbody>");
  html += ("				</table>");
  html += ("			</td>");
  html += ("		</tr>");
  html += ("	</table>");
  html += ("	</td>");
  html += ("</tr>");
  html += ("</table>");
  html += ("</div>");
  document.body.insertAdjacentHTML("beforeEnd",html);

  document.body.attachEvent("onmousedown",function()
  {
    var container = document.getElementById("dynmatch_data_container");
    if(container.style.display != "none")
    {
      var id = container.getAttribute("dynmatch_refid");
      var obj = document.getElementById(id);

      if(event.srcElement.id == id)
        return;

      var e = event.srcElement;
      var bequal = false;
      while(e != null)
      {
        if(e.id == "dynmatch_data_container")
        {
          bequal = true;
          break;
        }
        e = e.parentNode;
      }

      if(!bequal)
        //隐藏掉
        dynmatch_onhide(id);
    }
  });
}


/**
 *隐藏列表容器
 *id:文本框id
 */
function dynmatch_onhide(id)
{
  var container = document.getElementById("dynmatch_data_container");
  var tbl = document.getElementById("dynmatch_data_table");
  if(container.style.display != 'none')
  {
    dynmatch_onfocusrow(tbl,-1);
    container.style.display = 'none';

    var tbody = tbl.tBodies[0];
    tbody.innerText = "";

    cal_ShowElement(dynmatch_HideElementTemp);
  }
}

/**
 *根据数据数组来创建数据列表
 *tbl:表
 *dynDataArray:数据数组
 *dynIndexArray:有效的序号数组
 */
function dynmatch_buildDataTable(tbl,dynDataArray,dynIndexArray)
{
  var tbody = tbl.tBodies[0];
  tbody.innerText = "";
  if(dynIndexArray == null)
  {
    for(var i=0;i<dynDataArray.length;i++)
    {
      var row = tbody.insertRow();
      row.onmouseover = function(){dynmatch_onfocusrow(this.parentNode.parentNode,this.sectionRowIndex);};
      row.onclick = function(){dynmatch_onselect(this.parentNode.parentNode,this.sectionRowIndex);};
      row.className = "dynmatch_text";

      for(var j=0;j<dynDataArray[0].length;j++)
        row.insertCell().innerText = dynDataArray[i][j];
    }
  }
  else
  {
    for(var i=0;i<dynIndexArray.length;i++)
    {
      var row = tbody.insertRow();
      row.onmouseover = function(){dynmatch_onfocusrow(this.parentNode.parentNode,this.sectionRowIndex);};
      row.onclick = function(){dynmatch_onselect(this.parentNode.parentNode,this.sectionRowIndex);};
      row.className = "dynmatch_text";

      for(var j=0;j<dynDataArray[0].length;j++)
        row.insertCell().innerText = dynDataArray[dynIndexArray[i]][j];
    }
  }
}

/**
 *根据格式数组来创建表头和格式
 *tbl:表
 *dynFmtArray:格式数组
 */
function dynmatch_buildHeadTable(tbl,dynFmtArray)
{
  var colgroup = tbl.getElementsByTagName("COLGROUP")[0];
  colgroup.innerText = "";
  tbl.tHead.innerText = "";

  if(dynFmtArray == null)
    return;

  var row = tbl.tHead.insertRow();
  for(var i=0;i<dynFmtArray.length;i++)
  {
    var fmts = dynFmtArray[i].length;
    var capt="",width="",align="",show=true;
    if(fmts>=1) capt = dynFmtArray[i][0];
    if(fmts>=2) width = dynFmtArray[i][1];
    if(fmts>=3) align = dynFmtArray[i][2];
    if(fmts>=4) show = dynFmtArray[i][3];

    var col = document.createElement("COL");
    if(!show)
      col.style.display = "none";
    if(align != "")
      col.align = align;
    colgroup.appendChild(col);

    var cell = row.insertCell();
    cell.innerText = capt;
    cell.width = width;
    cell.className = "dynmatch_tHeading";
  }
}

/**
 *处理本地数据过滤查询
 *obj:文本框对象
 *data:数组
 */
function dynmatch_dodefault_preparedata_localdata(obj,data)
{
  var text = dynmatch_trimAll$(obj.value);

  //全部
  if(text == "")
    return null;

  var c = new Array();
  var j = 0;
  for(var i=0;i<data.length;i++)
  {
    var src = "" + data[i][0];

    if(src.indexOf(text) != -1)
      c[j++]  = i;
  }

  return c;
}

/**
 *处理本地数据过滤查询
 *obj:文本框对象
 */
function dynmatch_proc_localdata(obj,id,tbl)
{
  var funcname = obj.getAttribute("dynmatch_ondataprepare");
  var dynDataArray = eval(obj.getAttribute("dynmatch_data"));
  var dynIndexArray = null;
  var dynFmtArray = eval(obj.getAttribute("dynmatch_data_format"));

  if(funcname != null && funcname != "")
    dynIndexArray = eval(funcname + "(obj,dynDataArray)");
  //默认处理,匹配第一元素
  else
    dynIndexArray = dynmatch_dodefault_preparedata_localdata(obj,dynDataArray);

  dynmatch_buildHeadTable(tbl,dynFmtArray);
  dynmatch_buildDataTable(tbl,dynDataArray,dynIndexArray);
}

/**
 * 本地组装SQL远程数据动态匹配
 * obj:文本框对象
 * id:文本框id
 * tbl:数据显示列表
 */
function dynmatch_proc_localsqldata(obj,id,tbl)
{
  var funcname = obj.getAttribute("dynmatch_onsqlprepare");
  var sql = eval(funcname + "(obj)");
  var dynDataArray = dynmatch_proc_getremote_data("localsql",sql);
  if(typeof(dynDataArray) != "object")
    throw "获取匹配数据源失败";

  var dynFmtArray = eval(obj.getAttribute("dynmatch_data_format"));
  dynmatch_buildHeadTable(tbl,dynFmtArray);
  dynmatch_buildDataTable(tbl,dynDataArray,null);
}

/**
 * 远程SQL数据动态匹配
 * obj:文本框对象
 * id:文本框id
 * tbl:数据显示列表
 */
function dynmatch_proc_remotesqldata(obj,id,tbl)
{
  var refsqlname = obj.getAttribute("dynmatch_refsqlname");
  var funcname = obj.getAttribute("dynmatch_onsqlargsprepare");

  var dynArgsArray = null;
  if(funcname != null && funcname != "")
    dynArgsArray = eval(funcname + "(obj)");

  var dynDataArray = dynmatch_proc_getremote_data("remotesql",refsqlname,dynArgsArray);
  if(typeof(dynDataArray) != "object")
    throw "获取匹配数据源失败";

  var dynFmtArray = eval(obj.getAttribute("dynmatch_data_format"));
  dynmatch_buildHeadTable(tbl,dynFmtArray);
  dynmatch_buildDataTable(tbl,dynDataArray,null);
}

/**
 * 根据类型取得远程数据列表
 * type:类型
 * 如果type="localsql",则第二个参数为SQL语句
 * 如果type="remotesql",则第二个参数为refsqlname,第三个参数为命名参数二维数组,[键,值]
 * 返回一个二维数组
 */
function dynmatch_proc_getremote_data(type)
{
  var url = "/framework/jsp/dynmatch.jsp?type=" + type;
  if(type == "localsql")
  {
    url += "&sql=" + encodeURIComponent(arguments[1]);
  }
  else if(type == "remotesql")
  {
    url += "&refsqlname=" + arguments[1];

    //组装参数
    var argstext = "",args = arguments[2];
    if(args != null)
    {
      for(var i=0;i<args.length;i++)
      {
        if(i > 0)
          argstext += "\u0002";
        argstext += args[i][0];
        argstext += "\u0001";
        argstext += args[i][1];
      }
    }

    url += "&args=" + encodeURIComponent(argstext);
  }

  //提交后台获取参数
  var text = "" + window.showModalDialog(url,null,"dialogWidth:200px;dialogHeight:100px;help:no;status:no;");
  alert(text);
  if(text.indexOf("+ok\n") == 0)
    text = text.substring(4);
  else
    text = "";
/*
  text = "010000\u0001农、林、牧、渔业\u0002"
  + "010100\u0001农业\u0002"
  + "010110\u0001种植业\u0002"
  + "010190\u0001其他农业\u0002"
  + "010200\u0001林业\u0002"
  + "010300\u0001畜牧业\u0002"
  + "010310\u0001牲畜饲养放牧业\u0002"
  + "010320\u0001家禽饲养业\u0002"
  + "010330\u0001狩猎业\u0002"
  + "010390\u0001其他畜牧业\u0002"
  + "010400\u0001渔业\u0002"
  + "010410\u0001海洋渔业\u0002"
  + "010411\u0001海水养殖业\u0002"
  + "010412\u0001海洋捕捞业\u0002"
  + "010420\u0001淡水渔业\u0002"
  + "010421\u0001淡水养殖业\u0002"
  + "010422\u0001淡水捕捞业\u0002"
  + "010500\u0001农、林、牧、渔服务业\u0002"
  + "010510\u0001农业服务业\u0002"
  + "010520\u0001林业服务业\u0002"
  + "010530\u0001畜牧兽医服务业\u0002"
  + "010540\u0001渔业服务业\u0002"
  + "010590\u0001其他农、林、牧、渔服务业";
*/
  //处理返回的结果
  var dynDataArray = new Array();
  if(typeof(text) == "string" && text != "")
  {
    dynDataArray = text.split("\u0002");
    for(var i=0;i<dynDataArray.length;i++)
      dynDataArray[i] = dynDataArray[i].split("\u0001");
  }

  return dynDataArray;
}

/**
 *文本框按键事件处理
 *obj:文本框对象
 *id:文本框id
 */
function dynmatch_onkeydown(obj)
{
  //第一次调用
  var id = obj.id;
  if(id == null || id == "")
    id = dynmatch_checkObjectID(obj);

  var code = obj.getAttribute("dynmatch_code_overflow");
  if(code != null && code.length > 0) eval(code);
  code = obj.getAttribute("dynmatch_code_width");
  if(code != null && code.length > 0) eval(code);
  code = obj.getAttribute("dynmatch_code_height");
  if(code != null && code.length > 0) eval(code);
  code = obj.getAttribute("dynmatch_code_tablewidth");
  if(code != null && code.length > 0) eval(code);

  var container = document.getElementById('dynmatch_data_container');
  container.setAttribute("dynmatch_refid",id);
  var tbl = document.getElementById('dynmatch_data_table');
  tbl.setAttribute("dynmatch_refid",id);
  var dynmatch_pos = parseInt(tbl.getAttribute("dynmatch_pos"));
  var type = obj.getAttribute("dynmatch_type");
  var tbody = tbl.tBodies[0];

  if(event.keyCode == 13)//\n
  {
    if(container.style.display == 'none')
    {
      //取得数据
      try
      {
        if(type == "localdata")
          dynmatch_proc_localdata(obj,id,tbl);
        else if(type == "localsql")
          dynmatch_proc_localsqldata(obj,id,tbl);
        else if(type == "remotesql")
          dynmatch_proc_remotesqldata(obj,id,tbl);
      }
      catch(e)
      {
        throw e;
        return;
      }

      if(tbody.rows.length == 0)
      {
        alert("没有匹配的数据");
        return;
      }

      var e = obj;
      var left = 0;
      var top = obj.clientHeight + 5;
      while(e != null)
      {
        left += e.offsetLeft;
        top += e.offsetTop;
        e = e.offsetParent;
      }
      container.style.left = left;
      container.style.top = top;

      dynmatch_pos = -1;
      dynmatch_onfocusrow(tbl,dynmatch_pos + 1,false);
      container.scrollTop = 0;

      container.style.display = "";
      container.scrollIntoView(false);

      cal_ShowElement(dynmatch_HideElementTemp);
      cal_hideElementAll(container,dynmatch_HideElementTemp);
    }
    //选择选定的项
    else
    {
      dynmatch_onselect(tbl,dynmatch_pos);

      //隐藏掉
      dynmatch_onhide(id);
    }
    return;
  }
  else if(event.keyCode == 27)//ESC
  {
    //隐藏掉
    dynmatch_onhide(id);
    event.returnValue = false;
    return;
  }
  else if(event.keyCode == 9)//Tab
  {
    //隐藏掉
    dynmatch_onhide(id);
    return;
  }

  if(container.style.display == 'none')
    return;

  if(event.keyCode == 40)//down
  {
    if(dynmatch_pos < tbody.rows.length - 1)
    {
      dynmatch_onfocusrow(tbl,dynmatch_pos + 1,false);
    }
  }
  else if(event.keyCode == 38)//up
  {
    if(dynmatch_pos > 0)
      dynmatch_onfocusrow(tbl,dynmatch_pos - 1,false);
  }
  else if(event.keyCode == 33)//pgup
  {

  }
  else if(event.keyCode == 34)//pgdown
  {
  }
  else
  {
    //隐藏掉
    dynmatch_onhide(id);
    return;
  }
}

/**
 *将列表中的某一项设为当前项
 *tbl:列表对象
 *pos:该项在列表中的位置
 */
function dynmatch_onfocusrow(tbl,pos)
{
  var dynmatch_pos = parseInt(tbl.getAttribute("dynmatch_pos"));
  var tbody = tbl.tBodies[0];

  if(dynmatch_pos != pos)
  {
    if(dynmatch_pos>=0 && dynmatch_pos<tbody.rows.length)
    {
      tbody.rows[dynmatch_pos].className = "dynmatch_text";
      dynmatch_pos = -1;
    }
  }

  if(pos>=0 && pos<tbody.rows.length)
  {
    dynmatch_pos = pos;
    tbody.rows[dynmatch_pos].className = "dynmatch_focustext";

    if(arguments.length == 3)
      tbody.rows[dynmatch_pos].scrollIntoView(arguments[2]);
  }

  tbl.setAttribute("dynmatch_pos",dynmatch_pos,0);
}

/**
 *选择当前项
 *tbl:列表对象
 *pos:该项在列表中的位置
 */
function dynmatch_onselect(tbl,pos)
{
  var id = tbl.getAttribute("dynmatch_refid");
  var obj = document.getElementById(id);
  var onafterquery = obj.getAttribute("dynmatch_onafterquery");

  //生成结果一维数组
  var result = new Array();
  var row = tbl.tBodies[0].rows[pos];
  for(var i=0;i<row.cells.length;i++)
    result[i] = row.cells[i].innerHTML;

  if(onafterquery != null && onafterquery != "")
    eval(onafterquery + "(obj,result)");
  else
    obj.value = result[0];

  //隐藏
  dynmatch_onhide(id);
}

//===========================================================================//

//点击菜单时，调用此的函数,菜单对象
function cal_hideElementAll(obj,HideElementTemp)
{
  cal_HideElement("IMG",obj,HideElementTemp);
  cal_HideElement("SELECT",obj,HideElementTemp);
  cal_HideElement("OBJECT",obj,HideElementTemp);
  cal_HideElement("IFRAME",obj,HideElementTemp);
}

function cal_HideElement(strElementTagName,obj,HideElementTemp)
{
  try
  {
    var showDivElement = obj;
    var calendarDiv = obj;
    var intDivLeft = cal_GetOffsetLeft(showDivElement);
    var intDivTop = cal_GetOffsetTop(showDivElement);

    for(i=0;i<window.document.all.tags(strElementTagName).length; i++)
    {
      var objTemp = window.document.all.tags(strElementTagName)[i];
      if(!objTemp||!objTemp.offsetParent)
        continue;
      var intObjLeft=cal_GetOffsetLeft(objTemp);
      var intObjTop=cal_GetOffsetTop(objTemp);
      if(((intObjLeft+objTemp.clientWidth)>intDivLeft)&&
        (intObjLeft<intDivLeft+calendarDiv.style.posWidth)&&
        (intObjTop+objTemp.clientHeight>intDivTop)&&
        (intObjTop<intDivTop+calendarDiv.style.posHeight)&&
        (objTemp.style.visibility!="hidden"))
      {
        HideElementTemp[HideElementTemp.length]=objTemp
        objTemp.style.visibility="hidden";
      }
    }
  }
  catch(e)
  {
    alert(e.message)
  }
}

function cal_ShowElement(HideElementTemp)
{
  var i;
  for(i=0;i<HideElementTemp.length; i++)
  {
    var objTemp = HideElementTemp[i]
    if(!objTemp||!objTemp.offsetParent)
      continue;
    objTemp.style.visibility='';
  }
  HideElementTemp=new Array();
}

function cal_GetOffsetLeft(src)
{
  var set=0;
  if(src)
  {
    if (src.offsetParent)
    {
           set+=src.offsetLeft+cal_GetOffsetLeft(src.offsetParent);
    }
    if(src.tagName.toUpperCase()!="BODY")
    {
      var x=parseInt(src.scrollLeft,10);
      if(!isNaN(x))
        set-=x;
    }
  }
    return set;
}

function cal_GetOffsetTop(src)
{
  var set=0;
  if(src)
  {
    if (src.offsetParent)
    {
      set+=src.offsetTop+cal_GetOffsetTop(src.offsetParent);
    }
    if(src.tagName.toUpperCase()!="BODY")
    {
      var y=parseInt(src.scrollTop,10);
      if(!isNaN(y))
        set-=y;
    }
  }
    return set;
}

