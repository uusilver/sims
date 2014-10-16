  /**
   * 精度
   */
  function querye_floatRound(val,c)
  {
    val = "" + Math.round(val * Math.pow(10,c));
    var n = c - val.length + 1;
    for(var i=0;i<n;i++)
      val = "0" + val;
    val = val.substring(0,val.length - c) + "." + val.substring(val.length - c);

    return val;
  }

  /**
   * 全选复选框全选("all")模式
   * obj:全选复选框对象
   * chkname:要处理的复选框元素名称
   * remark:忽略被禁用的复选框
   */
 function querye_checkAll_all(obj,chkname)
 {
    var frm = obj.form;
    var coll = null;
    if(frm != null)
      coll = frm.elements(chkname);
    else
      coll = document.getElementsByName(chkname);

    if(coll == null)
    {
      return;
    }
    if (coll.length == null) {
      if(!coll.disabled)
      {
        coll.checked = obj.checked;
        coll.fireEvent("onclick");
      }
    } else
    {
      for(var i=0;i<coll.length;i++)
      {
        if(!coll[i].disabled)
        {
          coll[i].checked = obj.checked;
          coll[i].fireEvent("onclick");
        }
      }
    }
 }
  /**
   * 取得特定标签的最近的顶层对象
   * obj:源对象


   * tagname:顶层对象标签
   */
  function querye_getNearestTop(obj,tagname)
  {
    var o = obj.parentNode;
    while(o != null)
    {
      if(o.tagName.toLowerCase() == tagname.toLowerCase())
        break;
      o = o.parentNode;
    }

    return o;
  }

  /**
   * 统计被选中的行
   * obj:复选框对象
   * 变长参数:[列序号(从0开始),统计方法]
   */
  function querye_tostat(obj)
  {
    var a = arguments;
    var frm = obj.form;
    var table = document.getElementById("query_table_" + frm.sequenceid.value);
    var tListBody = table.tBodies["query_listbody_" + frm.sequenceid.value];
    var tStatBody = table.tBodies["query_statbody_" + frm.sequenceid.value];
    var statRow = tStatBody.rows[0];

    for(var i=1;i<a.length;i+=2)
    {
      var col = parseInt(a[i]);
      var method = a[i+1];

      var ret;
      if(method.toLowerCase() == "sum")
      {
        var n = 0;
        var max = 0,idx;
        var coll = frm.elements(obj.name);
        for(var j=0;j<coll.length;j++)
        {
          if(coll[j].checked)
          {
            var row = querye_getNearestTop(coll[j],"tr");
            var val = row.cells[col].innerText;
            if(val.length > 0)
            {
              var x = parseFloat(val);
              if(!isNaN(x))
              {
                n += parseFloat(val);
                idx = val.indexOf(".");
                if(idx != -1)
                {
                  idx = val.length - (idx + 1);
                  if(idx > max) max = idx;
                }
              }
            }
          }
        }

        statRow.cells[col].innerText = querye_floatRound(n,max) + " ";
      }
      else if(method.toLowerCase() == "count")
      {
        var n = 0;
        var coll = frm.elements(obj.name);
        for(var j=0;j<coll.length;j++)
        {
          if(coll[j].checked)
            n++;
        }
        statRow.cells[col].innerText = n + " ";
      }
      else
        statRow.cells[col].innerText = "";
    }
  }

  /**
   * 跳到指定的页面,并提交表单


   * frmname:表单名称
   * pageno:页码
   */
  function querye_gopage(frmname,pageno)
  {
    var frm = document.forms[frmname];
    frm.elements[frm.query_name.value + "_query_pageno"].value = pageno;
    frm.submit();
  }

  /**
   * 点击查询按钮查询
   * frmname:表单名称
   */
  function querye_toquery(frmname)
  {
    querye_gopage(frmname,1);
  }

  /**
   * 进行排序
   * frmname:表单名称
   * colnum:列号,从1开始


   */
  function querye_tosort(frmname,colnum)
  {
    var frm = document.forms[frmname];
    var oldcol = frm.query_sortcol.value;
    var olddir = frm.query_sortdir.value;

    if(oldcol != "" + colnum)
    {
      frm.query_sortcol.value = colnum;
      frm.query_sortdir.value = "asc";
    }
    else
    {
      if(olddir == "asc")
        frm.query_sortdir.value = "desc";
      else if(olddir == "desc")
        frm.query_sortcol.value = "";
    }

    frm.submit();
  }

  /**
   * 跳到用户指定的页
   * frmname:表单名称
   * obj:页码输入文本框


   */
  function querye_jumppage(frmname,obj,pagecount)
  {
    var frm = document.forms[frmname];
    if(obj.value.length == 0)
    {
      alert("请输入要跳转的页数");
      obj.focus();
      return false;
    }

    var n = parseInt(obj.value);
    if(isNaN(n) || n < 1)
    {
      alert("输入的跳转页数不正确");
      obj.select();
      obj.focus();
      return false;
    }

    if(pagecount >= 0)
    {
      if(n > pagecount)
      {
        alert("所输入的跳转页数不正确,最大可以跳转的页数为" + pagecount);
        obj.select();
        obj.focus();
        return false;
      }
    }

    querye_gopage(frmname,n);
  }

/**
 * 在表单或页面范围内查找指定名称的选中的复选框总数
 */
function querye_getCheckCount(frm,chkname)
{
  coll = frm.elements(chkname);

  var n = 0;
  for(var i=0;i<coll.length;i++)
  {
    if(coll[i].checked) n++;
  }
  return n;
}

/**
 * 删除前后小于等于20的字符


 */
function querye_trimAll(src)
{
  if(src == null)
    return "";

  src = "" + src;
  for(var i=0;i<src.length;i++)
  {
    if(src.charAt(i) > " ")
      break;
  }

  for(var j=src.length - 1;j>=0;j--)
  {
    if(src.charAt(j) > " ")
      break;
  }

  if(i > j)
    return "";

  return src.substring(i,j + 1);
}
