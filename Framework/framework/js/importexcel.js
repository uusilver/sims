
/**
 * 导入Excel
 * titles:标题列表,用逗号分隔.该标题必须存在于工作表的第一行中
 * importstart:开始导入事件
 * importing:正在导入一行事件
 * importend:导入结束事件
 */
function impx_ImportExcel(titles,importstart,importing,importend)
{
  if((importstart !=null && typeof(importstart) != 'string')
    || (importing !=null && typeof(importing) != 'string')
    || (importend !=null && typeof(importend) != 'string'))
  {
    alert("impx_ImportExcel方法期待字符串型参数");
    return;
  }

  var arr = titles.split(",");
  var titleList = "";
  for(var i=0;i<arr.length;i++)
      titleList += "&title=" + arr[i];

  var url = "/framework/jsp/importexcel.jsp?";
  url += "importstart=" + importstart;
  url += "&importing=" + importing;
  url += "&importend=" + importend;
  url += titleList;

  window.open(url,"_blank","status=1,height=220px,width=450px,help=no,left=" + (screen.availWidth-450)/2 + "px,top=" + (screen.availHeight-160)/2 + "px");
}

/**
 * 导入Excel
 * titles:标题列表,用逗号分隔.该标题必须存在于工作表的第一行中
 * importstart:开始导入事件
 * importing:正在导入一行事件
 * importend:导入结束事件
 */
function impx_ImportExcelByFile(fn,titles,importstart,importing,importend)
{
  //检查文件名
  var idx = fn.lastIndexOf(".");
  if(idx == -1)
  {
    alert("请选择要导入的Excel文件");
    return false;
  }
  var ext = fn.substring(idx).toLowerCase();
  if(ext != ".xls")
  {
    alert("只能导入后缀名为xls的Excel文件");
    return false;
  }

  //检查
  var excel = null;
  var bCloseOnExit = false;

  try
  {
    excel = new ActiveXObject("Excel.Application");

    if(excel == null)
    {
      alert("无法打开该文档\n\n您所选择的文件可能不是一个正确的Excel文件或者该文档可能已损坏");
      return false;
    }
    var workbook = excel.Workbooks.Open(fn);
    var i,j,k,sheet,rows,cols,range;
    var colmap;

    for(i=1;i<=excel.Worksheets.Count;i++)
    {
      sheet = excel.Worksheets(i);
      range = sheet.UsedRange;
      cols = range.Columns.Count;
      rows = range.Rows.Count;

      //没有任何行或列数太少
      if(rows == 0 || cols < titles.length)
        continue;

      //查找列映射
      colmap = new Array(titles.length + 1);
      colmap[titles.length] = 0;
      for(j=1;j<=cols;j++)
      {
        for(k=0;k<titles.length;k++)
        {
          if(range(1,j).Text.toLowerCase() == titles[k])
          {
            colmap[k] = j;
            colmap[titles.length] ++;
            break;
          }
        }
      }
      if(colmap[titles.length] != titles.length)
        continue;

      //开始导入
      if(importstart != null && importstart != "")
      {
        var ret = eval("window.opener." + importstart + "(" + (rows-1) + ")");
        //导入操作被取消
        if(typeof(ret) == 'boolean' && ret == false)
          break;
      }

      //导入行
      var data = new Array(titles.length);
      for(j=2;j<=rows;j++)
      {
        for(k=0;k<titles.length;k++)
          data[k] = range(j,colmap[k]).Text;

        if(data[0].toLowerCase() == "end.")
          break;

        if(importing != null && importing != "")
        {
          var ret = eval("window.opener." + importing + "(data)");
          //导入操作被取消
          if(typeof(ret) == 'boolean' && ret == false)
            break;
        }
      }

      //导入完全
      if(importend != null && importend != "")
        eval("window.opener." + importend + "()");

      bCloseOnExit = true;

      break;
    }

    if(i > excel.Worksheets.Count)
    {
      alert("所选择的导入文件中没有任何需要导入的工作表");
      return false;
    }
  }
  finally
  {
    if(excel != null)
      excel.Quit();
  }

  if(bCloseOnExit)
    window.close();
}
